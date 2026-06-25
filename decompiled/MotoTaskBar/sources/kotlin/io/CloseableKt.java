package kotlin.io;

import java.io.Closeable;
import java.io.IOException;
import kotlin.ExceptionsKt;

/* JADX INFO: compiled from: Closeable.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class CloseableKt {
    public static final void closeFinally(Closeable closeable, Throwable th) throws IOException {
        if (closeable != null) {
            if (th == null) {
                closeable.close();
                return;
            }
            try {
                closeable.close();
            } catch (Throwable th2) {
                ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }
}
