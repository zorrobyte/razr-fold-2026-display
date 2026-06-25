package androidx.graphics.path;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PathIteratorImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PathIteratorImpl {
    private static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: PathIteratorImpl.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        if (StringsKt.equals("dalvik", System.getProperty("java.vm.name"), true)) {
            System.loadLibrary("androidx.graphics.path");
        }
    }
}
