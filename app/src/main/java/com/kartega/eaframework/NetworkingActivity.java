package com.kartega.eaframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.ea_dialogs.DialogStyle;
import com.kartega.ophelia.ea_dialogs.EADialogBuilder;
import com.kartega.ophelia.ea_networking.EAErrorListener;
import com.kartega.ophelia.ea_networking.EAObjectResponseListener;
import com.kartega.ophelia.ea_networking.EARequestBuilder;
import com.kartega.ophelia.ea_networking.EAVolleyHelper;
import com.kartega.ophelia.ea_networking.Method;
import com.kartega.ophelia.ea_networking.download.EADownloadListener;
import com.kartega.ophelia.ea_networking.download.EAFileDownloadBuilder;
import com.kartega.ophelia.ea_networking.upload.EAUploadBuilder;
import com.kartega.ophelia.ea_networking.upload.EAUploadListener;
import com.kartega.ophelia.ea_utilities.enums.LogType;
import com.kartega.ophelia.ea_utilities.tools.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class NetworkingActivity extends EABaseActivity {

    private TextView tvResponse;
    private EAVolleyHelper helper;
    private boolean isDownloadingManager;
    private boolean isDownloadingCustom;
    private EAFileDownloadBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tvResponse = findViewById(R.id.tv_response);
        findViewById(R.id.btn_upload).setOnClickListener(this);
        findViewById(R.id.btn_request).setOnClickListener(this);
        findViewById(R.id.btn_download_manager).setOnClickListener(this);
        findViewById(R.id.btn_download_custom).setOnClickListener(this);
        findViewById(R.id.btn_delete_downloads).setOnClickListener(this);
        findViewById(R.id.btn_download_cache).setOnClickListener(this);
        findViewById(R.id.btn_list_cache).setOnClickListener(this);

        helper = new EAVolleyHelper(this);
        helper.setResponseLogEnabled(false);
        helper.setHeadersLogEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (isClickDisabled())
            return;
        disableClickTemporarily();
        switch (v.getId()) {
            case R.id.btn_request:
                requestTest();
                break;
            case R.id.btn_upload:
                uploadTest();
                break;
            case R.id.btn_download_custom:
                if (isDownloadingCustom)
                    builder.cancel();
                else
                    downloadTest(false);
                break;
            case R.id.btn_download_manager:
                if (isDownloadingManager)
                    builder.cancel();
                else
                    downloadTest(true);
                break;
            case R.id.btn_delete_downloads:
                FileUtils.deleteFileOrFolder(new File(FileUtils.getDownloadsPath() + File.separator + "test_file.jpg"));
                break;
            case R.id.btn_download_cache:
                downloadToCache();
                break;
            case R.id.btn_list_cache:
                listCache();
                break;
            default:
                enableClick();
                super.onClick(v);
                break;
        }
    }

    @Override
    public void saveLogIfNeeded(int type, String message) {

    }

    private void requestTest() {
        //String url = "https://enqolrgw1pyfj.x.pipedream.net/sample/test";
        String url = "https://jsonplaceholder.typicode.com/todos/1";
/*
        Car car = new Car("Audi");
        Human human = new Human("Ahmet", car);
        Gson gson = new Gson();

        JSONArray array = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject(gson.toJson(human));
            JSONObject obj2 = new JSONObject(gson.toJson(human));
            array.put(obj1);
            array.put(obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // String jsonBody = gson.toJson(human);
        String jsonBody = array.toString();
*/
        showProgress();
        EARequestBuilder requestBuilder = new EARequestBuilder();
        requestBuilder
                .setUrl(url)
                .setMethod(Method.GET)
                .setHelper(helper)
                .setShouldCache(false)
                .addJsonBody("test", "test değeri")
                //.addParams("oKey","HELLO")
                //.setMethod(Method.POST)
                //.setJsonParams(jsonBody)
                //.setResponseClass(String.class)
                //.setErrorClass(String.class)
                //.setMethod(Request.Method.GET)
                //.setDefaultRetryPolicy(...)
                //.setParams(...)
                //.setShouldCache(false)
                //.setTimeOutMillis(15000)
                //.setListResponseListener(...) // for List of items
                .setObjectResponseListener(new EAObjectResponseListener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgress();
                        makeLog(LogType.INFORMATION, response);
                        tvResponse.setText(response);
                    }
                })
                .setErrorListener(new EAErrorListener() {
                    @Override
                    public void onError(Object error) {
                        hideProgress();
                        makeLog(LogType.ERROR, error.toString());
                        tvResponse.setText(error.toString());
                    }
                })
                .fetch();
    }

    private void uploadTest() {
        String url = "https://crm.kartega.com/TestFile" + "?param2=p2FromUrl&param3=p3FromUrl";

        /*
        List<String> fileNames = new ArrayList<>();

        fileNames.add("file-item-1");
        fileNames.add("file-item-2");
        fileNames.add("file-item-3");

        List<File> files = new ArrayList<>();
        files.add(new File("/storage/emulated/0/DCIM/1544425059682.jpg"));
        files.add(new File("/storage/emulated/0/DCIM/1544427246052.jpg"));
        files.add(new File("/storage/emulated/0/DCIM/1544425059682.jpg"));

*/
        HashMap<String, String> headers = new HashMap<>();
        headers.put("UserName", "mUserName");
        headers.put("Password", "mPassword");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "My Param 1 From Form");
        parameters.put("param2", "My Param 2 From Form");
        parameters.put("StreetName", "Çatalca");
        parameters.put("NeighborhoodName", "BOZHANE");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Deneme Body", "sdlşfk0pomfşlesdö");
            jsonObject.put("test", "psoşdfkjpoşlwe");
            jsonObject.put("ıopreisasdşçxcö", "ğejfpsidmş");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new EAUploadBuilder(this)
                .setUrl(url)
                .setHeaders(headers)
                .setParameters(parameters)
                //.addFiles(files)
                .addBody("TestBody", jsonObject.toString())
                .setUploadListener(new EAUploadListener() {
                    @Override
                    public void onProgressChanged(float progress) {
                        String uploading = "EAUploading: " + progress;
                        runOnUiThread(() -> tvResponse.setText(uploading));
                    }

                    @Override
                    public void onUploadResponse(String response) {
                        runOnUiThread(() -> tvResponse.setText(response));
                    }

                    @Override
                    public void onUploadError(String e) {
                        runOnUiThread(() -> tvResponse.setText(e));
                    }
                })
                .upload();
    }

    private void downloadTest(boolean withManager) {
        isDownloadingManager = withManager;
        isDownloadingCustom = !withManager;
        String fileUrl = "http://s1.picswalls.com/wallpapers/2017/12/10/4k-gaming-wallpaper_11062893_312.jpg";
        //String fileUrl = "http://ipv4.download.thinkbroadband.com/5MB.zip";

        builder = new EAFileDownloadBuilder(this)
                .setUrl(fileUrl)
                .setSaveToDownloads(true)
                // .setSubFolder("MyCustomDownloadsFolder")
                .setFileName("test_file.jpg")
                //.setDateModified(new Date())
                //.setHeaders(...)
                .setDownloadListener(new EADownloadListener() {
                    @Override
                    public void onDownloadProgress(float progress) {
                        String downloading = "EADownloading: " + progress;
                        runOnUiThread(() -> tvResponse.setText(downloading));
                    }

                    @Override
                    public void onDownloadComplete(String filePath) {
                        runOnUiThread(() -> {
                            isDownloadingCustom = false;
                            isDownloadingManager = false;
                            ((Button) findViewById(R.id.btn_download_custom)).setText("Download Test Custom");
                            ((Button) findViewById(R.id.btn_download_manager)).setText("Download Test Manager");
                            ((TextView) findViewById(R.id.tv_response)).setText(filePath);
                            onFileClicked(filePath, null);
                        });
                    }

                    @Override
                    public void onDownloadError(String error) {
                        runOnUiThread(() -> {
                            tvResponse.setText(error);
                            isDownloadingCustom = false;
                            isDownloadingManager = false;
                            ((Button) findViewById(R.id.btn_download_custom)).setText("Download Test Custom");
                            ((Button) findViewById(R.id.btn_download_manager)).setText("Download Test Manager");
                        });
                    }
                });
        if (withManager)
            builder.downloadWithManager();
        else
            builder.download();
        if (isDownloadingCustom)
            ((Button) findViewById(R.id.btn_download_custom)).setText("Cancel Upload");
        if (isDownloadingManager)
            ((Button) findViewById(R.id.btn_download_manager)).setText("Cancel Upload");
    }

    private void downloadToCache() {
        String fileUrl = "http://s1.picswalls.com/wallpapers/2017/12/10/4k-gaming-wallpaper_11062893_312.jpg";
        new EAFileDownloadBuilder(this)
                .setUrl(fileUrl)
                .setSaveToDownloads(false)
                .setSubFolder("source_files")
                .setFileName("my_cached_image_m.jpg")
                .setDownloadListener(new EADownloadListener() {
                    @Override
                    public void onDownloadProgress(float progress) {
                        String downloading = "EADownloading: " + String.valueOf(progress);
                        runOnUiThread(() -> tvResponse.setText(downloading));
                    }

                    @Override
                    public void onDownloadComplete(String filePath) {
                        runOnUiThread(() -> {
                            isDownloadingCustom = false;
                            isDownloadingManager = false;
                            ((Button) findViewById(R.id.btn_download_cache)).setText("Cache Download Complete");
                            ((TextView) findViewById(R.id.tv_response)).setText(filePath);
                            onFileClicked(filePath, null);
                        });
                    }

                    @Override
                    public void onDownloadError(String error) {
                        runOnUiThread(() -> {
                            tvResponse.setText(error);
                            isDownloadingCustom = false;
                            isDownloadingManager = false;
                            ((Button) findViewById(R.id.btn_download_cache)).setText("Cache Download Error");
                        });
                    }
                })
                .download();
    }

    private void listCache() {
        List<File> resultList = listF(getCacheDir().getAbsolutePath());
        StringBuilder str = new StringBuilder();
        for (File file:resultList){
            str.append(file.getName());
            str.append("\n");
        }
        ((TextView) findViewById(R.id.tv_response)).setText(str.toString());
    }

    public static List<File> listF(String directoryName) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listF(file.getAbsolutePath()));
            }
        }
        return resultList;
    }
}
