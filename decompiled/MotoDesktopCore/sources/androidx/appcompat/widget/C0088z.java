package androidx.appcompat.widget;

import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ViewTreeObserverOnGlobalLayoutListenerC0062f;

/* JADX INFO: renamed from: androidx.appcompat.widget.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0088z implements PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener f1348a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ A f1349b;

    public C0088z(A a2, ViewTreeObserverOnGlobalLayoutListenerC0062f viewTreeObserverOnGlobalLayoutListenerC0062f) {
        this.f1349b = a2;
        this.f1348a = viewTreeObserverOnGlobalLayoutListenerC0062f;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        ViewTreeObserver viewTreeObserver = this.f1349b.f860F.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.f1348a);
        }
    }
}
