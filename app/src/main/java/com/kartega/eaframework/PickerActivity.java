package com.kartega.eaframework;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.ea_file_picker.EAPickerBuilder;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerType;
import com.kartega.ophelia.ea_file_picker.interfaces.EAPickerResponseListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_utilities.enums.LogType;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

import java.util.List;

public class PickerActivity extends EABaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        textView = findViewById(R.id.tv_selections);

        findViewById(R.id.btn_pick_image).setOnClickListener(this);
        findViewById(R.id.btn_pick_image_camera).setOnClickListener(this);
        findViewById(R.id.btn_pick_file).setOnClickListener(this);
        findViewById(R.id.btn_pick_audio).setOnClickListener(this);
        findViewById(R.id.btn_pick_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isClickDisabled())
            return;
        switch (v.getId()) {
            case R.id.btn_pick_image:
                disableClickTemporarily();
                pickImage();
                break;
            case R.id.btn_pick_image_camera:
                disableClickTemporarily();
                pickImageFromCamera();
                break;
            case R.id.btn_pick_file:
                disableClickTemporarily();
                pickFile();
                break;
            case R.id.btn_pick_audio:
                disableClickTemporarily();
                pickAudio();
                break;
            case R.id.btn_pick_video:
                disableClickTemporarily();
                pickVideo();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void pickImage() {
        EAPickerBuilder builder = new EAPickerBuilder()
                .setLimit(6)
                .setUseCamera(true)
                .setPickerType(EAPickerType.IMAGE)
                .setSelectedItems(null)
                .setResponseListener(new EAPickerResponseListener() {
                    @Override
                    public void onFilePickComplete(List<BaseFile> files) {
                        makeLog(LogType.DEBUG, "pick completed");
                        updateSelectionText(files);
                    }

                    @Override
                    public void onFilePickCancelled() {
                        makeLog(LogType.DEBUG, "pick cancelled");
                        updateSelectionText(null);
                    }
                });

        showFilePicker(builder);
    }

    private void pickImageFromCamera() {
        EAPickerBuilder builder = new EAPickerBuilder()
                .setPickerType(EAPickerType.IMAGE_FROM_CAMERA)
                .setResponseListener(new EAPickerResponseListener() {
                    @Override
                    public void onFilePickComplete(List<BaseFile> files) {
                        makeLog(LogType.DEBUG, "pick completed");
                        updateSelectionText(files);
                    }

                    @Override
                    public void onFilePickCancelled() {
                        makeLog(LogType.DEBUG, "pick cancelled");
                        updateSelectionText(null);
                    }
                });

        showFilePicker(builder);
    }

    private void pickFile() {
        EAPickerBuilder builder = new EAPickerBuilder()
                .setLimit(12)
                .setPickerType(EAPickerType.FILE)
                .setResponseListener(new EAPickerResponseListener() {
                    @Override
                    public void onFilePickComplete(List<BaseFile> files) {
                        makeLog(LogType.DEBUG, "pick completed");
                        updateSelectionText(files);
                    }

                    @Override
                    public void onFilePickCancelled() {
                        makeLog(LogType.DEBUG, "pick cancelled");
                        updateSelectionText(null);
                    }
                });

        showFilePicker(builder);
    }

    private void pickAudio() {
        EAPickerBuilder builder = new EAPickerBuilder()
                .setLimit(7)
                .setPickerType(EAPickerType.AUDIO)
                .setResponseListener(new EAPickerResponseListener() {
                    @Override
                    public void onFilePickComplete(List<BaseFile> files) {
                        makeLog(LogType.DEBUG, "pick completed");
                        updateSelectionText(files);
                    }

                    @Override
                    public void onFilePickCancelled() {
                        makeLog(LogType.DEBUG, "pick cancelled");
                        updateSelectionText(null);
                    }
                });

        showFilePicker(builder);
    }

    private void pickVideo() {
        EAPickerBuilder builder = new EAPickerBuilder()
                .setLimit(1)
                .setUseCamera(true)
                .setPickerType(EAPickerType.VIDEO)
                .setResponseListener(new EAPickerResponseListener() {
                    @Override
                    public void onFilePickComplete(List<BaseFile> files) {
                        makeLog(LogType.DEBUG, "pick completed");
                        updateSelectionText(files);
                    }

                    @Override
                    public void onFilePickCancelled() {
                        makeLog(LogType.DEBUG, "pick cancelled");
                        updateSelectionText(null);
                    }
                });

        showFilePicker(builder);
    }

    private void updateSelectionText(List<BaseFile> files) {
        StringBuilder fileNames = new StringBuilder();
        if (ObjectUtils.arrayIsNotEmpty(files))
            for (BaseFile baseFile : files) {
                fileNames.append(baseFile.getName());
                fileNames.append("\n");
                makeLog(LogType.DEBUG, baseFile.getName(), true);
            }
        textView.setText(fileNames.toString());
    }

    @Override
    public void saveLogIfNeeded(int type, String message) {

    }
}
