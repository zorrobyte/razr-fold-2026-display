package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/* JADX INFO: loaded from: classes.dex */
abstract class HideViewOnScrollDelegate {
    HideViewOnScrollDelegate() {
    }

    abstract int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams);

    abstract int getTargetTranslation();

    abstract int getViewEdge();

    abstract ViewPropertyAnimator getViewTranslationAnimator(View view, int i);

    abstract void setViewTranslation(View view, int i);
}
