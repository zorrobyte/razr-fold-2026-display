package com.motorola.settingslib.collapsingtoolbar;

import android.os.Build;
import android.util.Log;
import android.view.View;
import com.google.android.material.appbar.AppBarLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnCollapseChangedListener.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class OnCollapseChangedListener implements AppBarLayout.OnOffsetChangedListener {
    public static final Companion Companion = new Companion(null);
    private static final boolean DEBUG = !Intrinsics.areEqual("user", Build.TYPE);
    private boolean isCollapsed;

    /* JADX INFO: compiled from: OnCollapseChangedListener.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract void onCollapse();

    public abstract void onExpand();

    @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        appBarLayout.getClass();
        onScroll(Math.abs(i), Math.abs(appBarLayout.getTotalScrollRange()));
        if (Math.abs(i) < appBarLayout.getTotalScrollRange() && this.isCollapsed) {
            if (DEBUG) {
                Log.d("OnCollapsedChangeListener", "Toolbar expanded");
            }
            this.isCollapsed = false;
            onExpand();
            return;
        }
        if (Math.abs(i) < appBarLayout.getTotalScrollRange() || this.isCollapsed) {
            return;
        }
        if (DEBUG) {
            Log.d("OnCollapsedChangeListener", "Toolbar collapsed");
        }
        this.isCollapsed = true;
        onCollapse();
    }

    public abstract void onScroll(int i, int i2);

    public final void updateViewAlpha(View view, float f, float f2, int i, int i2) {
        view.getClass();
        float f3 = i2;
        float f4 = f * f3;
        float f5 = f3 * f2;
        float f6 = i;
        if (f6 < f4) {
            view.setAlpha(1.0f);
        } else if (f6 >= f5) {
            view.setAlpha(0.0f);
        } else {
            view.setAlpha(1.0f - ((f6 - f4) / (f5 - f4)));
        }
    }
}
