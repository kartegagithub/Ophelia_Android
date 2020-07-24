package com.kartega.eaframework;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.ea_dialogs.ButtonStyle;
import com.kartega.ophelia.ea_dialogs.DialogStyle;
import com.kartega.ophelia.ea_dialogs.EADialogBuilder;
import com.kartega.ophelia.ea_utilities.enums.LogType;

public class DialogActivity extends EABaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.btn_dialog_success).setOnClickListener(this);
        findViewById(R.id.btn_dialog_error).setOnClickListener(this);
        findViewById(R.id.btn_dialog_warning).setOnClickListener(this);
        findViewById(R.id.btn_dialog_info).setOnClickListener(this);
        findViewById(R.id.btn_dialog_notice).setOnClickListener(this);
        findViewById(R.id.btn_dialog_progress).setOnClickListener(this);
        findViewById(R.id.btn_dialog_custom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isClickDisabled())
            return;
        disableClickTemporarily();
        switch (v.getId()) {
            case R.id.btn_dialog_success:
                showSuccessDialog();
                break;
            case R.id.btn_dialog_error:
                showErrorDialog();
                break;
            case R.id.btn_dialog_warning:
                showWarningDialog();
                break;
            case R.id.btn_dialog_info:
                showInfoDialog();
                break;
            case R.id.btn_dialog_notice:
                showNoticeDialog();
                break;
            case R.id.btn_dialog_progress:
                showProgressDialog();
                break;
            case R.id.btn_dialog_custom:
                showCustomDialog();
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

    private void showSuccessDialog() {
        new EADialogBuilder(this)
                .setStyle(DialogStyle.SUCCESS)
                .setMessageTextGravity(Gravity.END)
                .hideTitle()
                //.setTitle("Test Title.")
                .setMessage("Test message.")
                //.setColoredCircle(R.color.colorPrimary)
                // .setDialogIconAndColor(R.drawable.ic_dialog_error,R.color.dialogProgressBackgroundColor)
                .addButton(ButtonStyle.POSITIVE)
                .addButton(ButtonStyle.NEGATIVE, "Cancel")
                .addButton(ButtonStyle.NEUTRAL, v -> showToast("Neutral Clicked"))
                .show();
    }

    private void showErrorDialog() {
        new EADialogBuilder(this)
                .setStyle(DialogStyle.ERROR)
                .setTitle("Test Title.")
                .setMessage("Test message.")
                //.setColoredCircle(R.color.colorPrimary)
                // .setDialogIconAndColor(R.drawable.ic_dialog_error,R.color.dialogProgressBackgroundColor)
                .addButton(ButtonStyle.POSITIVE)
                .addButton(ButtonStyle.NEGATIVE, "Cancel")
                .show();
    }

    private void showInfoDialog() {
        new EADialogBuilder(this)
                .setStyle(DialogStyle.INFO)
                .setTitle("Test Title.")
                .setMessage("Test message.")
                //.setColoredCircle(R.color.colorPrimary)
                // .setDialogIconAndColor(R.drawable.ic_dialog_error,R.color.dialogProgressBackgroundColor)
                .addButton(ButtonStyle.NEUTRAL, v -> showToast("Neutral Clicked"))
                .show();
    }

    private void showWarningDialog() {
        new EADialogBuilder(this)
                .setStyle(DialogStyle.WARNING)
                .setTitle("Test Title.")
                .setMessage("Test message.")
                //.setColoredCircle(R.color.colorPrimary)
                // .setDialogIconAndColor(R.drawable.ic_dialog_error,R.color.dialogProgressBackgroundColor)
                .addButton(ButtonStyle.POSITIVE)
                .addButton(ButtonStyle.NEGATIVE, "Cancel")
                .addButton(ButtonStyle.NEUTRAL, v -> showToast("Neutral Clicked"))
                .show();
    }

    private void showNoticeDialog() {
        new EADialogBuilder(this)
                .setStyle(DialogStyle.NOTICE)
                .setTitle("Test Title.")
                .setMessage("Test message.")
                //.setColoredCircle(R.color.colorPrimary)
                // .setDialogIconAndColor(R.drawable.ic_dialog_error,R.color.dialogProgressBackgroundColor)
                .addButton(ButtonStyle.NEUTRAL, v -> showToast("Neutral Clicked"))
                .show();
    }

    private void showProgressDialog() {
        EADialogBuilder builder = new EADialogBuilder(this)
                .setStyle(DialogStyle.PROGRESS)
                .setTitle("Test Title.")
                .setMessage("Test message.")
                .setProgressColorText(R.color.black)
                .setProgressColorBorder(R.color.primaryTransparent)
                .setProgressColorProgress(R.color.colorPrimary)
                .setProgress(0)
                //.setColoredCircle(R.color.ea_picker_row_power_point)
                .addButton(ButtonStyle.NEGATIVE, "Cancel");

        builder.show();

        if(builder.isShowing())
            makeLog(LogType.DEBUG, "dialog_visible");

        updateProgress(builder, 10);
    }

    private void updateProgress(EADialogBuilder builder, int progress) {
        if (progress < 100)
            new Handler().postDelayed(() -> runOnUiThread(() -> {
                //builder.setProgress(0);
                //builder.setProgressMax(100);
                builder.setProgress(progress);
                updateProgress(builder, progress + 5);
            }), 500);
    }

    private void showCustomDialog() {
        new EADialogBuilder(this)
                .setTitle("Test Title")
                .setMessage("Test message")
                .addButton(ButtonStyle.NEUTRAL, v -> showToast("Neutral Clicked"))
                .show();
    }
}
