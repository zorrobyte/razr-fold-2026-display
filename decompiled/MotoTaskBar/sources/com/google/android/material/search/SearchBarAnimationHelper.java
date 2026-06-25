package com.google.android.material.search;

import android.animation.Animator;
import android.view.View;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class SearchBarAnimationHelper {
    private Animator defaultCenterViewAnimator;
    private Animator secondaryViewAnimator;
    private final Set onLoadAnimationCallbacks = new LinkedHashSet();
    private final Set expandAnimationListeners = new LinkedHashSet();
    private final Set collapseAnimationListeners = new LinkedHashSet();
    private boolean onLoadAnimationFadeInEnabled = true;
    private Animator runningExpandOrCollapseAnimator = null;

    SearchBarAnimationHelper() {
    }

    void stopOnLoadAnimation(SearchBar searchBar) {
        Animator animator = this.secondaryViewAnimator;
        if (animator != null) {
            animator.end();
        }
        Animator animator2 = this.defaultCenterViewAnimator;
        if (animator2 != null) {
            animator2.end();
        }
        View centerView = searchBar.getCenterView();
        if (centerView != null) {
            centerView.setAlpha(0.0f);
        }
    }
}
