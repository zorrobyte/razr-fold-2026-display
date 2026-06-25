package androidx.appcompat.view.menu;

import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public final class r extends FrameLayout implements d.c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final CollapsibleActionView f836a;

    /* JADX WARN: Multi-variable type inference failed */
    public r(View view) {
        super(view.getContext());
        this.f836a = (CollapsibleActionView) view;
        addView(view);
    }

    @Override // d.c
    public final void onActionViewCollapsed() {
        this.f836a.onActionViewCollapsed();
    }

    @Override // d.c
    public final void onActionViewExpanded() {
        this.f836a.onActionViewExpanded();
    }
}
