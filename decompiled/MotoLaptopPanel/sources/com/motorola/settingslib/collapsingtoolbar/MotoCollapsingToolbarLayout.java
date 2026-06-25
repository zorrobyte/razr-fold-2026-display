package com.motorola.settingslib.collapsingtoolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toolbar;
import com.android.settingslib.collapsingtoolbar.R$drawable;
import com.android.settingslib.collapsingtoolbar.R$id;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/* JADX INFO: loaded from: classes.dex */
public class MotoCollapsingToolbarLayout extends CollapsingToolbarLayout {
    private ImageView icon;
    private boolean isFODToolbar;
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener;
    private Runnable onCollapse;
    private Runnable onExpand;

    class CollapseChangedListener extends OnCollapseChangedListener {
        final Toolbar toolbar;

        private CollapseChangedListener() {
            this.toolbar = (Toolbar) MotoCollapsingToolbarLayout.this.findViewById(R$id.action_bar);
        }

        @Override // com.motorola.settingslib.collapsingtoolbar.OnCollapseChangedListener
        public void onCollapse() {
            this.toolbar.setBackgroundResource(MotoCollapsingToolbarLayout.this.isFODToolbar ? R$drawable.toolbar_background_fod : R$drawable.toolbar_background);
            if (MotoCollapsingToolbarLayout.this.onCollapse != null) {
                MotoCollapsingToolbarLayout.this.onCollapse.run();
            }
        }

        @Override // com.motorola.settingslib.collapsingtoolbar.OnCollapseChangedListener
        public void onExpand() {
            this.toolbar.setBackground(null);
            if (MotoCollapsingToolbarLayout.this.onExpand != null) {
                MotoCollapsingToolbarLayout.this.onExpand.run();
            }
        }

        @Override // com.motorola.settingslib.collapsingtoolbar.OnCollapseChangedListener
        public void onScroll(int i, int i2) {
            if (MotoCollapsingToolbarLayout.this.icon != null) {
                updateViewAlpha(MotoCollapsingToolbarLayout.this.icon, 0.3f, 0.7f, i, i2);
            }
        }
    }

    public MotoCollapsingToolbarLayout(Context context) {
        super(context);
        this.isFODToolbar = false;
    }

    public MotoCollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isFODToolbar = false;
    }

    public MotoCollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isFODToolbar = false;
    }

    @Override // com.google.android.material.appbar.CollapsingToolbarLayout, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if ((parent instanceof AppBarLayout) && this.offsetChangedListener == null) {
            CollapseChangedListener collapseChangedListener = new CollapseChangedListener();
            this.offsetChangedListener = collapseChangedListener;
            ((AppBarLayout) parent).addOnOffsetChangedListener((AppBarLayout.OnOffsetChangedListener) collapseChangedListener);
        }
    }

    @Override // com.google.android.material.appbar.CollapsingToolbarLayout, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
        super.onDetachedFromWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof AppBarLayout) || (onOffsetChangedListener = this.offsetChangedListener) == null) {
            return;
        }
        ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
    }
}
