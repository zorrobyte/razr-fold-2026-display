package androidx.core.os;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public abstract class BundleCompat {

    abstract class Api33Impl {
        static Object getParcelable(Bundle bundle, String str, Class cls) {
            return bundle.getParcelable(str, cls);
        }
    }

    public static Object getParcelable(Bundle bundle, String str, Class cls) {
        return Api33Impl.getParcelable(bundle, str, cls);
    }
}
