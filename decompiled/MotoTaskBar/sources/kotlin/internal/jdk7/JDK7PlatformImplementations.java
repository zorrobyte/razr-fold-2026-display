package kotlin.internal.jdk7;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementations;

/* JADX INFO: compiled from: JDK7PlatformImplementations.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class JDK7PlatformImplementations extends PlatformImplementations {

    /* JADX INFO: compiled from: JDK7PlatformImplementations.kt */
    final class ReflectSdkVersion {
        public static final ReflectSdkVersion INSTANCE = new ReflectSdkVersion();
        public static final Integer sdkVersion;

        static {
            Object obj;
            Integer num = null;
            try {
                obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Throwable unused) {
            }
            Integer num2 = obj instanceof Integer ? (Integer) obj : null;
            if (num2 != null && num2.intValue() > 0) {
                num = num2;
            }
            sdkVersion = num;
        }

        private ReflectSdkVersion() {
        }
    }

    private final boolean sdkIsNullOrAtLeast(int i) {
        Integer num = ReflectSdkVersion.sdkVersion;
        return num == null || num.intValue() >= i;
    }

    @Override // kotlin.internal.PlatformImplementations
    public void addSuppressed(Throwable th, Throwable th2) throws IllegalAccessException, InvocationTargetException {
        th.getClass();
        th2.getClass();
        if (sdkIsNullOrAtLeast(19)) {
            th.addSuppressed(th2);
        } else {
            super.addSuppressed(th, th2);
        }
    }

    @Override // kotlin.internal.PlatformImplementations
    public List getSuppressed(Throwable th) {
        th.getClass();
        if (!sdkIsNullOrAtLeast(19)) {
            return super.getSuppressed(th);
        }
        Throwable[] suppressed = th.getSuppressed();
        suppressed.getClass();
        return ArraysKt.asList(suppressed);
    }
}
