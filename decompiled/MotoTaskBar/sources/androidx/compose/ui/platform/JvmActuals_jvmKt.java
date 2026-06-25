package androidx.compose.ui.platform;

import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: JvmActuals.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class JvmActuals_jvmKt {
    public static final String simpleIdentityToString(Object obj, String str) {
        if (str == null) {
            str = obj.getClass().isAnonymousClass() ? obj.getClass().getName() : obj.getClass().getSimpleName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append('@');
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        sb.append(String.format("%07x", Arrays.copyOf(new Object[]{Integer.valueOf(System.identityHashCode(obj))}, 1)));
        return sb.toString();
    }
}
