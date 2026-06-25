package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/* JADX INFO: loaded from: classes.dex */
final class HideBottomViewOnScrollDelegate extends HideViewOnScrollDelegate {
    HideBottomViewOnScrollDelegate() {
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams) {
        return view.getMeasuredHeight() + marginLayoutParams.bottomMargin;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    int getTargetTranslation() {
        return 0;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    int getViewEdge() {
        return 1;
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    ViewPropertyAnimator getViewTranslationAnimator(View view, int i) {
        return view.animate().translationY(i);
    }

    @Override // com.google.android.material.behavior.HideViewOnScrollDelegate
    void setViewTranslation(View view, int i) {
        view.setTranslationY(i);
    }
}
