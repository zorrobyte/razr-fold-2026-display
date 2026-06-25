package androidx.fragment.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/* JADX INFO: renamed from: androidx.fragment.app.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0099k extends AbstractC0102n {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ FragmentActivity f1634f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0099k(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.f1634f = fragmentActivity;
    }

    @Override // androidx.fragment.app.v
    public final View b(int i2) {
        return this.f1634f.findViewById(i2);
    }

    @Override // androidx.fragment.app.v
    public final boolean c() {
        Window window = this.f1634f.getWindow();
        return (window == null || window.peekDecorView() == null) ? false : true;
    }

    @Override // androidx.fragment.app.AbstractC0102n
    public final void d(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, Intent intent, int i2, Bundle bundle) {
        FragmentActivity fragmentActivity = this.f1634f;
        fragmentActivity.f1534j = true;
        try {
            if (i2 == -1) {
                fragmentActivity.startActivityForResult(intent, -1, bundle);
            } else {
                FragmentActivity.d(i2);
                fragmentActivity.startActivityForResult(intent, ((fragmentActivity.c(abstractComponentCallbacksC0098j) + 1) << 16) + (i2 & 65535), bundle);
            }
        } finally {
            fragmentActivity.f1534j = false;
        }
    }
}
