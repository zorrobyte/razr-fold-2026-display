package androidx.fragment.app;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class v {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f1659a = {R.attr.name, R.attr.id, R.attr.tag};

    public AbstractComponentCallbacksC0098j a(Context context, String str, Bundle bundle) {
        return AbstractComponentCallbacksC0098j.instantiate(context, str, bundle);
    }

    public abstract View b(int i2);

    public abstract boolean c();
}
