package androidx.core.os;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class HandlerCompat {

    abstract class Api28Impl {
        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }
    }

    public static Handler createAsync(Looper looper) {
        return Api28Impl.createAsync(looper);
    }
}
