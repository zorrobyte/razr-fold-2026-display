package kotlinx.coroutines.internal;

import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InlineList.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class InlineList {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Object m2756constructorimpl(Object obj) {
        return obj;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ Object m2757constructorimpl$default(Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            obj = null;
        }
        return m2756constructorimpl(obj);
    }

    /* JADX INFO: renamed from: plus-FjFbRPM, reason: not valid java name */
    public static final Object m2758plusFjFbRPM(Object obj, Object obj2) {
        if (obj == null) {
            return m2756constructorimpl(obj2);
        }
        if (obj instanceof ArrayList) {
            ((ArrayList) obj).add(obj2);
            return m2756constructorimpl(obj);
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(obj2);
        return m2756constructorimpl(arrayList);
    }
}
