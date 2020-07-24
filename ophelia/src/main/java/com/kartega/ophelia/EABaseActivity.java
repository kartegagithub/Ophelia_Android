package com.kartega.ophelia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kartega.ophelia.ea_file_picker.EAPickerActivity;
import com.kartega.ophelia.ea_file_picker.EAPickerBuilder;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.interfaces.EAPickerResponseListener;
import com.kartega.ophelia.ea_file_picker.objects.SelectedItemsExtraObject;
import com.kartega.ophelia.ea_networking.download.EADownloadListener;
import com.kartega.ophelia.ea_networking.download.EAFileDownloadBuilder;
import com.kartega.ophelia.ea_progress.CrystalPreloader;
import com.kartega.ophelia.ea_progress.EAProgressDialog;
import com.kartega.ophelia.ea_utilities.enums.LogType;
import com.kartega.ophelia.ea_utilities.interfaces.FileClickListener;
import com.kartega.ophelia.ea_utilities.interfaces.LogListener;
import com.kartega.ophelia.ea_utilities.interfaces.PermissionListener;
import com.kartega.ophelia.ea_utilities.tools.FileUtils;
import com.kartega.ophelia.ea_utilities.tools.ProviderUtils;
import com.kartega.ophelia.ea_utilities.tools.StringUtils;
import com.kartega.ophelia.ea_utilities.views.CustomToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahmet Kılıç on 18.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
@SuppressWarnings("unused")
public abstract class EABaseActivity extends AppCompatActivity implements
        View.OnClickListener,
        FileClickListener,
        EADownloadListener,
        LogListener {

    private EAPickerResponseListener pickerResponseListener;

    private String TAG;
    private EAProgressDialog eaProgressDialog;
    private CustomToast customToast;

    private EAFileDownloadBuilder downloader;

    private int clickDisableDuration;

    private boolean isClickDisabled;
    private Handler clickHandler;
    private Runnable clickRunnable;

    private PermissionListener permissionListener;

    public static final int PERMISSION_REQUEST_CODE = 734;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        makeLog(LogType.CRITICAL_ERROR, "Low Memory Called!");
    }

    private void initVariables() {
        clickDisableDuration = 300;
        TAG = getClass().getSimpleName();
        eaProgressDialog = new EAProgressDialog(this);
        eaProgressDialog.setProgress(CrystalPreloader.Style.BALL_SPIN_FADE, CrystalPreloader.Size.SMALL,
                R.color.colorPrimary, R.color.colorPrimaryDark, 0);

        clickHandler = new Handler();
        clickRunnable = new Runnable() {
            @Override
            public void run() {
                isClickDisabled = false;
            }
        };
    }

    /**
     * Set progress dialog in your base activity to change progress
     *
     * @param eaProgressDialog your custom dialog
     */
    public void setEaProgressDialog(EAProgressDialog eaProgressDialog) {
        this.eaProgressDialog = eaProgressDialog;
    }

    /**
     * Show full screen progress dialog
     */
    public void showProgress() {
        if (!isFinishing())
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (eaProgressDialog != null && !eaProgressDialog.isShowing())
                        eaProgressDialog.show();
                }
            });
    }

    /**
     * Hide full screen progress dialog
     */
    public void hideProgress() {
        if (!isFinishing()) {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!EABaseActivity.this.isFinishing() && eaProgressDialog != null && eaProgressDialog.isShowing())
                            eaProgressDialog.dismiss();
                    }
                });
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    /**
     * Make log with activity name as tag if log is enabled
     */
    public void makeLog(@LogType int type, String message) {
        boolean useSeparator = getResources().getBoolean(R.bool.use_log_separator);
        makeLog(type, message, useSeparator);
    }

    /**
     * Make log with activity name as tag if log is enabled
     */
    public void makeLog(@LogType int type, String message, boolean useSeparator) {
        if (getResources().getBoolean(R.bool.disable_log))
            return;
        else if (!isDebugMode() && getResources().getBoolean(R.bool.disable_log_in_release_mode))
            return;

        if (TAG.length() > 21)
            TAG = TAG.substring(0, 21);

        String logTag = TAG + "_" + String.valueOf(type);

        if (useSeparator)
            doSeparatorLog(type, logTag);

        if (!isDebugMode() && getResources().getBoolean(R.bool.allow_only_critical_error_log_in_release_mode) && type == LogType.CRITICAL_ERROR) {
            Log.e(logTag, message);
            saveLogIfNeeded(type, message);
            if (useSeparator)
                doSeparatorLog(type, logTag);
            return;
        }

        if (!isDebugMode() && getResources().getBoolean(R.bool.allow_only_error_log_in_release_mode) && (type == LogType.ERROR || type == LogType.CRITICAL_ERROR)) {
            Log.e(logTag, message);
            saveLogIfNeeded(type, message);
            if (useSeparator)
                doSeparatorLog(type, logTag);
            return;
        }

        switch (type) {
            case LogType.CRITICAL_ERROR:
            case LogType.ERROR:
                Log.e(logTag, message);
                break;
            case LogType.INFORMATION:
                Log.i(logTag, message);
                break;
            case LogType.WARNING:
                Log.w(logTag, message);
                break;
            case LogType.DEBUG:
                if (isDebugMode()) {
                    Log.d(logTag, message);
                    saveLogIfNeeded(type, message);
                    if (useSeparator)
                        doSeparatorLog(type, logTag);
                }
                return;
            default:
                break;
        }

        if (useSeparator)
            doSeparatorLog(type, logTag);

        saveLogIfNeeded(type, message);
    }

    private void doSeparatorLog(@LogType int type, String logTag) {
        switch (type) {
            case LogType.CRITICAL_ERROR:
            case LogType.ERROR:
                Log.e(logTag, getString(R.string.log_separator_value));
                break;
            case LogType.INFORMATION:
                Log.i(logTag, getString(R.string.log_separator_value));
                break;
            case LogType.WARNING:
                Log.w(logTag, getString(R.string.log_separator_value));
                break;
            case LogType.DEBUG:
                if (isDebugMode()) {
                    Log.d(logTag, getString(R.string.log_separator_value));
                }
                return;
            default:
                break;
        }
    }

    /**
     * Open a file. If the file exists in device; just open it, else download and open the file.
     *
     * @param filePath     file path to open
     * @param dateModified last modified date of file
     */
    @Override
    public void onFileClicked(String filePath, @Nullable Date dateModified) {
        makeLog(LogType.INFORMATION, "ClickedFile : " + filePath);
        if (!StringUtils.isEmptyString(filePath)) {
            if (FileUtils.isFileExistsInDevice(filePath)) {
                ProviderUtils.openFileFromLocal(this, filePath);
            } else
                checkPermissionForDownloading(filePath, dateModified);
        } else
            showToast(getString(R.string.warning_invalid_url));
    }

    private void checkPermissionForDownloading(final String path, final Date dateModified) {
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionListener() {
            @Override
            public void onPermissionsGranted() {
                downloadFile(path, dateModified);
            }

            @Override
            public void onPermissionsDenied(List<String> deniedPermissions) {
                showToast(R.string.write_permission_required_to_download_file);
            }
        });
    }

    private void downloadFile(String path, Date dateModified) {
        showProgress();
        downloader = new EAFileDownloadBuilder(EABaseActivity.this)
                .setUrl(path)
                .setDateModified(dateModified)
                .setDownloadListener(EABaseActivity.this)
                .setSaveToDownloads(true);
        downloader.downloadWithManager();
    }

    @Override
    protected void onDestroy() {
        if (downloader != null)
            downloader.unregisterManager();
        super.onDestroy();
    }

    /**
     * Show custom toast from resource id
     */
    public void showToast(int message) {
        showToast(getString(message));
    }

    /**
     * Show custom toast with string
     */
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (customToast == null) {
                    customToast = new CustomToast(EABaseActivity.this);
                }
                customToast.showToast(message);
            }
        });
    }

    public boolean isClickDisabled() {
        return isClickDisabled;
    }

    public void enableClick() {
        clickHandler.removeCallbacks(clickRunnable);
        isClickDisabled = false;
    }

    public void disableClickTemporarily() {
        isClickDisabled = true;
        clickHandler.postDelayed(clickRunnable, clickDisableDuration);
    }

    public String getTAG() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        disableClickTemporarily();
    }

    @Override
    public void onLogRequired(@LogType int type, String message) {
        makeLog(type, message);
    }

    @Override
    public void onDownloadProgress(float progress) {

    }

    @Override
    public void onDownloadComplete(String filePath) {
        hideProgress();
        ProviderUtils.openFileFromLocal(this, filePath);
    }

    @Override
    public void onDownloadError(String error) {
        hideProgress();
        showToast(error);
    }

    /**
     * Override this in your base activity and use for crashlytics or smt else.
     *
     * @param type    Log type
     * @param message Log message
     */
    public void saveLogIfNeeded(@LogType int type, String message) {

    }

    public void checkPermission(String permissionCode, PermissionListener listener) {
        checkPermissions(Collections.singletonList(permissionCode), listener);
    }

    public void checkPermissions(List<String> permissionCodes, PermissionListener listener) {
        this.permissionListener = listener;
        String[] mArray = new String[permissionCodes.size()];
        mArray = permissionCodes.toArray(mArray);

        if (!hasPermissions(this, mArray))
            ActivityCompat.requestPermissions(this, mArray, PERMISSION_REQUEST_CODE);
        else
            listener.onPermissionsGranted();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null)
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

        return true;
    }

    public void showFilePicker(EAPickerBuilder builder) {
        this.pickerResponseListener = builder.getResponseListener();
        Intent intent = new Intent(this, EAPickerActivity.class);
        intent.putExtra("selected_items", new SelectedItemsExtraObject(builder.getSelectedItems()));
        intent.putExtra("picker_type", builder.getPickerType());
        intent.putExtra("limit", builder.getLimit());
        intent.putExtra("use_camera", builder.isUseCamera());
        intent.putExtra("suffix", builder.getSuffix());
        startActivityForResult(intent, EAPickerCodes.REQUEST_CODE_EA_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EAPickerCodes.REQUEST_CODE_EA_PICKER) {
            if (pickerResponseListener == null)
                return;
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    SelectedItemsExtraObject selectedItemsExtraObject = (SelectedItemsExtraObject) data.getSerializableExtra("selected_items");
                    if (selectedItemsExtraObject != null)
                        pickerResponseListener.onFilePickComplete(selectedItemsExtraObject.getSelectedItems());
                    else
                        pickerResponseListener.onFilePickComplete(null);
                } else
                    pickerResponseListener.onFilePickComplete(null);

            } else
                pickerResponseListener.onFilePickCancelled();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    deniedPermissions.add(permissions[i]);
            }
            if (permissionListener != null) {
                if (deniedPermissions.size() > 0)
                    permissionListener.onPermissionsDenied(deniedPermissions);
                else
                    permissionListener.onPermissionsGranted();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
