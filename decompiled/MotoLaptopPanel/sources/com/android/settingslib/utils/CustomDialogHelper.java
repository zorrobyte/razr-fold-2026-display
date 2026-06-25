package com.android.settingslib.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.R$id;
import com.android.settingslib.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class CustomDialogHelper {
    private Button mBackButton;
    private Context mContext;
    private LinearLayout mCustomLayout;
    private Dialog mDialog;
    private View mDialogContent;
    private ImageView mDialogIcon;
    private TextView mDialogMessage;
    private TextView mDialogTitle;
    private LayoutInflater mLayoutInflater;
    private Button mNegativeButton;
    private Button mPositiveButton;

    public CustomDialogHelper(Context context) {
        this.mContext = context;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.mLayoutInflater = layoutInflaterFrom;
        View viewInflate = layoutInflaterFrom.inflate(R$layout.dialog_with_icon, (ViewGroup) null);
        this.mDialogContent = viewInflate;
        this.mDialogIcon = (ImageView) viewInflate.findViewById(R$id.dialog_with_icon_icon);
        this.mDialogTitle = (TextView) this.mDialogContent.findViewById(R$id.dialog_with_icon_title);
        this.mDialogMessage = (TextView) this.mDialogContent.findViewById(R$id.dialog_with_icon_message);
        this.mCustomLayout = (LinearLayout) this.mDialogContent.findViewById(R$id.custom_layout);
        this.mPositiveButton = (Button) this.mDialogContent.findViewById(R$id.button_ok);
        this.mNegativeButton = (Button) this.mDialogContent.findViewById(R$id.button_cancel);
        this.mBackButton = (Button) this.mDialogContent.findViewById(R$id.button_back);
        createDialog();
    }

    private void createDialog() {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext).setView(this.mDialogContent).setCancelable(true).create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.getWindow().setSoftInputMode(4);
    }

    private void setButton(int i, int i2, View.OnClickListener onClickListener) {
        if (i == 4) {
            this.mBackButton.setText(i2);
            this.mBackButton.setVisibility(0);
            this.mBackButton.setOnClickListener(onClickListener);
        } else if (i == 5) {
            this.mNegativeButton.setText(i2);
            this.mNegativeButton.setVisibility(0);
            this.mNegativeButton.setOnClickListener(onClickListener);
        } else {
            if (i != 6) {
                return;
            }
            this.mPositiveButton.setText(i2);
            this.mPositiveButton.setVisibility(0);
            this.mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    public CustomDialogHelper addCustomView(View view) {
        this.mCustomLayout.addView(view);
        return this;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public void requestFocusOnTitle() {
        this.mDialogTitle.requestFocus();
        this.mDialogTitle.sendAccessibilityEvent(8);
    }

    public CustomDialogHelper setButtonEnabled(boolean z) {
        if (z) {
            this.mPositiveButton.setAlpha(1.0f);
        } else {
            this.mPositiveButton.setAlpha(0.5f);
        }
        this.mPositiveButton.setEnabled(z);
        return this;
    }

    public CustomDialogHelper setIcon(Drawable drawable) {
        this.mDialogIcon.setImageDrawable(drawable);
        return this;
    }

    public CustomDialogHelper setMessage(int i) {
        this.mDialogMessage.setText(i);
        return this;
    }

    public CustomDialogHelper setMessagePadding(int i) {
        this.mDialogMessage.setPadding(i, i, i, i);
        return this;
    }

    public CustomDialogHelper setNegativeButton(int i, View.OnClickListener onClickListener) {
        setButton(5, i, onClickListener);
        return this;
    }

    public CustomDialogHelper setNegativeButtonText(int i) {
        this.mNegativeButton.setText(i);
        return this;
    }

    public CustomDialogHelper setPositiveButton(int i, View.OnClickListener onClickListener) {
        setButton(6, i, onClickListener);
        return this;
    }

    public CustomDialogHelper setPositiveButtonText(int i) {
        this.mPositiveButton.setText(i);
        return this;
    }

    public CustomDialogHelper setTitle(int i) {
        this.mDialogTitle.setText(i);
        return this;
    }

    public CustomDialogHelper setVisibility(int i, boolean z) {
        int i2 = z ? 0 : 8;
        if (i == 0) {
            this.mDialogIcon.setVisibility(i2);
            return this;
        }
        if (i == 1) {
            this.mDialogTitle.setVisibility(i2);
            return this;
        }
        if (i == 2) {
            this.mDialogMessage.setVisibility(i2);
            return this;
        }
        if (i == 4) {
            this.mBackButton.setVisibility(i2);
            return this;
        }
        if (i == 5) {
            this.mNegativeButton.setVisibility(i2);
            return this;
        }
        if (i != 6) {
            return this;
        }
        this.mPositiveButton.setVisibility(i2);
        return this;
    }
}
