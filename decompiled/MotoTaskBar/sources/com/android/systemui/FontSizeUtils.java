package com.android.systemui;

import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class FontSizeUtils {
    public static void updateFontSize(TextView textView, int i) {
        if (textView != null) {
            textView.setTextSize(0, textView.getResources().getDimensionPixelSize(i));
        }
    }
}
