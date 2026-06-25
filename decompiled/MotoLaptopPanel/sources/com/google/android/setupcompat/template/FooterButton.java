package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.logging.CustomEvent;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class FooterButton implements View.OnClickListener {
    static final String KEY_BUTTON_ON_CLICK_COUNT = "_onClickCount";
    static final String KEY_BUTTON_TEXT = "_text";
    static final String KEY_BUTTON_TEXT_RESOURCE_NAME = "_textResName";
    static final String KEY_BUTTON_TYPE = "_type";
    private OnButtonEventListener buttonListener;
    private final int buttonType;
    private View.OnClickListener onClickListener;
    private View.OnClickListener onClickListenerWhenDisabled;
    private CharSequence text;
    private String textResourceName;
    private int theme;
    private boolean enabled = true;
    private int visibility = 0;
    private int clickCount = 0;

    interface OnButtonEventListener {
    }

    public FooterButton(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterButton);
        this.text = typedArrayObtainStyledAttributes.getString(R$styleable.SucFooterButton_android_text);
        this.onClickListener = null;
        this.buttonType = getButtonTypeValue(typedArrayObtainStyledAttributes.getInt(R$styleable.SucFooterButton_sucButtonType, 0));
        this.theme = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SucFooterButton_android_theme, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private String getButtonTypeName() {
        switch (this.buttonType) {
            case 1:
                return "ADD_ANOTHER";
            case 2:
                return "CANCEL";
            case 3:
                return "CLEAR";
            case 4:
                return "DONE";
            case 5:
                return "NEXT";
            case 6:
                return "OPT_IN";
            case 7:
                return "SKIP";
            case 8:
                return "STOP";
            default:
                return "OTHER";
        }
    }

    private int getButtonTypeValue(int i) {
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Not a ButtonType");
        }
        return i;
    }

    static String getTextResourceName(Context context, int i) {
        return (context == null || context.getResources() == null || i == 0) ? "" : context.getResources().getResourceEntryName(i);
    }

    public int getButtonType() {
        return this.buttonType;
    }

    public PersistableBundle getMetrics(String str) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(str + KEY_BUTTON_TEXT, CustomEvent.trimsStringOverMaxLength(getText().toString()));
        persistableBundle.putString(str + KEY_BUTTON_TYPE, getButtonTypeName());
        persistableBundle.putInt(str + KEY_BUTTON_ON_CLICK_COUNT, this.clickCount);
        String str2 = this.textResourceName;
        if (str2 != null && !Objects.equals(str2, "")) {
            persistableBundle.putString(str + KEY_BUTTON_TEXT_RESOURCE_NAME, CustomEvent.trimsStringOverMaxLength(this.textResourceName));
        }
        return persistableBundle;
    }

    public View.OnClickListener getOnClickListenerWhenDisabled() {
        return this.onClickListenerWhenDisabled;
    }

    public CharSequence getText() {
        return this.text;
    }

    public int getTheme() {
        return this.theme;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        View.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            this.clickCount++;
            onClickListener.onClick(view);
        }
    }

    void setOnButtonEventListener(OnButtonEventListener onButtonEventListener) {
        if (onButtonEventListener == null) {
            throw new NullPointerException("Event listener of footer button may not be null.");
        }
        this.buttonListener = onButtonEventListener;
    }
}
