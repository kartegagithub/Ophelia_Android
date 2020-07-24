package com.kartega.ophelia.ea_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_utilities.enums.LogType;
import com.kartega.ophelia.ea_utilities.interfaces.LogListener;
import com.kartega.ophelia.ea_utilities.tools.Calculator;
import com.kartega.ophelia.ea_utilities.views.CustomProgressBar;

import static com.kartega.ophelia.ea_utilities.tools.ViewTools.drawableColorChange;

/**
 * Created by Ahmet Kılıç on 15.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

@SuppressWarnings("unused")
public class EADialogBuilder {

    private Context context;
    private LogListener logListener;
    private Dialog dialog;
    @SuppressWarnings("all")
    private View dialogView;
    private int style;
    private LinearLayout dialogBody;
    private RelativeLayout topIconHolder;
    private RelativeLayout backIconHolder;
    @SuppressWarnings("all")
    private CustomProgressBar progressView;
    private ImageView dialogIcon;
    private ImageView backIcon;

    private TextView tvTitle;
    private android.widget.TextView tvMessage;

    /**
     * Dialog builder
     *
     * @param context context
     */
    public EADialogBuilder(Context context) {
        this.context = context;
        if (context instanceof LogListener)
            this.logListener = (LogListener) context;
        createDialog();
    }

    /**
     * Set message text gravity
     *
     * @param messageTextGravity gravity
     * @return builder
     */
    public EADialogBuilder setMessageTextGravity(int messageTextGravity) {
        tvMessage.setGravity(messageTextGravity);
        return this;
    }

    /**
     * Set the log listener manually.
     *
     * @param logListener log listener
     * @return builder
     */
    public EADialogBuilder setLogListener(LogListener logListener) {
        this.logListener = logListener;
        return this;
    }

    /**
     * Define the style of the dialog
     *
     * @param style style from enum
     * @return builder
     */
    public EADialogBuilder setStyle(@DialogStyle int style) {
        this.style = style;
        createDialog();
        return this;
    }

    public EADialogBuilder setProgress(int progress) {
        if (progressView != null) {
            progressView.setProgress(progress);
            //progressView.invalidate();
        }
        return this;
    }

    public EADialogBuilder setProgressMax(int max) {
        if (progressView != null)
            progressView.setMax(max);
        return this;
    }

    public EADialogBuilder setProgressColorText(int colorID) {
        progressView.setColorText(ResourcesCompat.getColor(context.getResources(), colorID, null));
        return this;
    }

    public EADialogBuilder setProgressColorBorder(int colorID) {
        progressView.setColorBorder(ResourcesCompat.getColor(context.getResources(), colorID, null));
        return this;
    }

    public EADialogBuilder setProgressColorProgress(int colorID) {
        progressView.setColorProgress(ResourcesCompat.getColor(context.getResources(), colorID, null));
        return this;
    }

    /**
     * Create the dialog
     */
    @SuppressWarnings("all")
    public void createDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(dialogBuilder.getContext()).inflate(R.layout.ea_dialog_builder, null, false);
        dialog = dialogBuilder.setView(dialogView).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        dialogBody = dialogView.findViewById(R.id.dialog_body);
        tvTitle = dialogView.findViewById(R.id.dialog_title);
        tvMessage = dialogView.findViewById(R.id.dialog_message);
        topIconHolder = dialogView.findViewById(R.id.colored_circle);

        backIconHolder = dialogView.findViewById(R.id.back);
        backIcon = (ImageView) LayoutInflater.from(context).inflate(R.layout.ea_dialog_image, dialogBody, false);
        backIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_info), PorterDuff.Mode.SRC_IN);
        backIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_arrow_back_blue_36dp, R.color.white));
        backIconHolder.addView(backIcon);
        backIconHolder.setOnClickListener(v -> hide());

        dialogIcon = (ImageView) LayoutInflater.from(context).inflate(R.layout.ea_dialog_image, dialogBody, false);

        if (style == DialogStyle.PROGRESS) {
            topIconHolder.removeAllViews();
            progressView = (CustomProgressBar) LayoutInflater.from(context).inflate(R.layout.ea_dialog_progress, topIconHolder, false);
            progressView.setMax(100);
            progressView.setProgress(0);
            topIconHolder.addView(progressView);
            dialogIcon = null;
        } else {
            switch (style) {
                case DialogStyle.SUCCESS:
                    topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_success), PorterDuff.Mode.SRC_IN);
                    dialogIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_dialog_success, R.color.white));
                    break;
                case DialogStyle.ERROR:
                    topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_error), PorterDuff.Mode.SRC_IN);
                    dialogIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_dialog_error, R.color.white));
                    break;
                case DialogStyle.INFO:
                    topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_info), PorterDuff.Mode.SRC_IN);
                    dialogIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_dialog_info, R.color.white));
                    break;
                case DialogStyle.NOTICE:
                    topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_notice), PorterDuff.Mode.SRC_IN);
                    dialogIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_dialog_notice, R.color.white));
                    break;
                case DialogStyle.WARNING:
                    topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.ea_dialog_background_color_warning), PorterDuff.Mode.SRC_IN);
                    dialogIcon.setImageDrawable(drawableColorChange(context, R.drawable.ic_dialog_warning, R.color.white));
                    break;
                default:
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dialogBody.getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    dialogBody.setLayoutParams(params);
                    dialogView.findViewById(R.id.dialog_top_circle).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.dialog_top_back).setVisibility(View.GONE);
                    LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
                    tvParams.setMargins(0, 0, 0, 0);
                    tvTitle.setLayoutParams(tvParams);
                    return;
            }
            topIconHolder.removeAllViews();
            if (dialogIcon != null)
                topIconHolder.addView(dialogIcon);
        }
    }

    /**
     * Set a title for the dialog. If not set,the title text view will remain gone.
     *
     * @param title title string
     * @return builder
     */
    public EADialogBuilder setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * Set a message for the dialog. If not set,the message text view will remain gone.
     *
     * @param message message string
     * @return builder
     */
    public EADialogBuilder setMessage(String message) {
        if (tvMessage != null) {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
        return this;
    }


    /**
     * Hide the title text view
     *
     * @return builder
     */
    public EADialogBuilder hideTitle() {
        if (tvTitle != null) {
            tvTitle.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvMessage.getLayoutParams();
            params.setMargins((int) Calculator.pxFromDp(context, 20), (int) Calculator.pxFromDp(context, 40), (int) Calculator.pxFromDp(context, 20), (int) Calculator.pxFromDp(context, 10));
            tvMessage.setLayoutParams(params);
        }
        return this;
    }

    /**
     * Hide the message text view
     *
     * @return builder
     */
    public EADialogBuilder hideMessage() {
        if (tvMessage != null) {
            tvMessage.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * Set cancellable
     *
     * @param cancelable is cancelable
     * @return builder
     */
    public EADialogBuilder setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * Set cancellable on touch outside
     *
     * @param cancelable is cancelable
     * @return builder
     */
    public EADialogBuilder setCancelableOnTouchOutside(boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * Set the color of the circle field
     *
     * @param color color id
     * @return builder
     */
    public EADialogBuilder setColoredCircle(int color) {
        if (topIconHolder != null) {
            topIconHolder.getBackground().setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    /**
     * Set the dialog icon and icon's color
     *
     * @param icon      icon id
     * @param iconColor color id
     * @return builder
     */
    public EADialogBuilder setDialogIconAndColor(int icon, int iconColor) {
        if (dialogIcon != null) {
            dialogIcon.setImageDrawable(drawableColorChange(context, icon, iconColor));
        }
        return this;
    }

    /**
     * Add a button to dialog
     *
     * @param buttonStyle button style from enum
     * @return builder
     */
    public EADialogBuilder addButton(@ButtonStyle int buttonStyle) {
        return addButton(buttonStyle, (View.OnClickListener) null);
    }

    /**
     * Add a button to dialog
     *
     * @param buttonStyle button style from enum
     * @param text        button text
     * @return builder
     */
    public EADialogBuilder addButton(@ButtonStyle int buttonStyle, String text) {
        return addButton(buttonStyle, text, null);
    }

    /**
     * Add a button to dialog
     *
     * @param buttonStyle   button style from enum
     * @param clickListener button's click listener
     * @return builder
     */
    public EADialogBuilder addButton(@ButtonStyle int buttonStyle, View.OnClickListener clickListener) {
        String text;
        switch (buttonStyle) {
            case ButtonStyle.POSITIVE:
                text = context.getString(R.string.yes);
                break;
            case ButtonStyle.NEGATIVE:
                text = context.getString(R.string.no);
                break;
            case ButtonStyle.NEUTRAL:
                text = context.getString(R.string.ok);
                break;
            default:
                text = context.getString(R.string.ok);
                break;
        }
        return addButton(buttonStyle, text, clickListener);
    }

    /**
     * Add a button to dialog
     *
     * @param buttonStyle   button style from enum
     * @param text          button text
     * @param clickListener button's click listener
     * @return builder
     */
    @SuppressWarnings("all")
    public EADialogBuilder addButton(@ButtonStyle int buttonStyle, String text, final View.OnClickListener clickListener) {
        switch (buttonStyle) {
            case ButtonStyle.POSITIVE:
                return addButton(text, R.color.ea_dialog_button_positive_background_color, R.color.ea_dialog_button_positive_text_color, clickListener);
            case ButtonStyle.NEGATIVE:
                return addButton(text, R.color.ea_dialog_button_negative_background_color, R.color.ea_dialog_button_negative_text_color, clickListener);
            case ButtonStyle.NEUTRAL:
                return addButton(text, R.color.ea_dialog_button_neutral_background_color, R.color.ea_dialog_button_neutral_text_color, clickListener);
            default:
                return this;
        }
    }

    /**
     * Add a button to dialog
     *
     * @param text          button text
     * @param bgColor       button back ground color
     * @param textColor     button text color
     * @param clickListener button's click listener
     * @return builder
     */
    @SuppressWarnings("all")
    public EADialogBuilder addButton(String text, int bgColor, int textColor, final View.OnClickListener clickListener) {
        Button button = (Button) LayoutInflater.from(context).inflate(R.layout.ea_dialog_button, dialogBody, false);
        button.getBackground().setColorFilter(ContextCompat.getColor(context, bgColor), PorterDuff.Mode.SRC_IN);
        button.setTextColor(ContextCompat.getColor(context, textColor));
        button.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (clickListener != null)
                    clickListener.onClick(v);
            }
        });
        dialogBody.addView(button);
        return this;
    }

    public EADialogBuilder addButton(Button button) {
        dialogBody.addView(button);
        return this;
    }


    /**
     * Show dialog
     *
     * @return dialog
     */
    public Dialog show() {
        try {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog.show();
                }
            } else {
                dialog.show();
            }

            if (dialogIcon != null) {
                Animation alertIcon = AnimationUtils.loadAnimation(context, R.anim.rubber_band);
                dialogIcon.startAnimation(alertIcon);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (logListener != null)
                logListener.onLogRequired(LogType.ERROR, "Error while showing the EADialog  -- >" + e.getLocalizedMessage());
        }

        return dialog;
    }

    /**
     * Hide dialog
     *
     * @return dialog
     */
    @SuppressWarnings("all")
    public Dialog hide() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            if (logListener != null)
                logListener.onLogRequired(LogType.ERROR, "Error while hiding the EADialog  -- >" + e.getLocalizedMessage());
        }
        dialog.dismiss();
        return dialog;
    }

    /**
     * check if the dialog is showing
     *
     * @return true if dialog is visible false otherwise
     */
    public boolean isShowing() {
        return dialog.isShowing();
    }

    /**
     * get dialog body view
     *
     * @return body linear layout
     */
    public LinearLayout getDialogBody() {
        return dialogBody;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public View getDialogView() {
        return dialogView;
    }
}
