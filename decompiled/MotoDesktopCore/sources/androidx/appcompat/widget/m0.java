package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;

/* JADX INFO: loaded from: classes.dex */
public abstract class m0 extends ContextWrapper {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object f1269a = null;

    public static void a(Context context) {
        if (context.getResources() instanceof o0) {
            return;
        }
        context.getResources();
        int i2 = w0.f1342a;
    }
}
