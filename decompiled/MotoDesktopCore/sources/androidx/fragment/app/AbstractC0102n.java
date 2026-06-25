package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/* JADX INFO: renamed from: androidx.fragment.app.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0102n extends v {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Activity f1638b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f1639c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Handler f1640d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final z f1641e;

    public AbstractC0102n(FragmentActivity fragmentActivity) {
        Y.x xVar = fragmentActivity.f1526b;
        this.f1641e = new z();
        this.f1638b = fragmentActivity;
        this.f1639c = fragmentActivity;
        if (xVar == null) {
            throw new NullPointerException("handler == null");
        }
        this.f1640d = xVar;
    }

    public abstract void d(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, Intent intent, int i2, Bundle bundle);
}
