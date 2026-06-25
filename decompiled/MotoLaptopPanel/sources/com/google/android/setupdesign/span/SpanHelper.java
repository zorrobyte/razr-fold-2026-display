package com.google.android.setupdesign.span;

import android.text.Spannable;

/* JADX INFO: loaded from: classes.dex */
public abstract class SpanHelper {
    public static void replaceSpan(Spannable spannable, Object obj, Object... objArr) {
        int spanStart = spannable.getSpanStart(obj);
        int spanEnd = spannable.getSpanEnd(obj);
        spannable.removeSpan(obj);
        for (Object obj2 : objArr) {
            spannable.setSpan(obj2, spanStart, spanEnd, 0);
        }
    }
}
