package androidx.core.app;

import android.os.Bundle;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public abstract class BundleCompat {
    public static IBinder getBinder(Bundle bundle, String str) {
        return bundle.getBinder(str);
    }
}
