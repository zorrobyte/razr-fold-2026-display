package androidx.savedstate;

import android.os.Bundle;
import java.util.List;

/* JADX INFO: compiled from: SavedStateWriter.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SavedStateWriter {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Bundle m1104constructorimpl(Bundle bundle) {
        bundle.getClass();
        return bundle;
    }

    /* JADX INFO: renamed from: putAll-impl, reason: not valid java name */
    public static final void m1105putAllimpl(Bundle bundle, Bundle bundle2) {
        bundle2.getClass();
        bundle.putAll(bundle2);
    }

    /* JADX INFO: renamed from: putSavedState-impl, reason: not valid java name */
    public static final void m1106putSavedStateimpl(Bundle bundle, String str, Bundle bundle2) {
        str.getClass();
        bundle2.getClass();
        bundle.putBundle(str, bundle2);
    }

    /* JADX INFO: renamed from: putStringList-impl, reason: not valid java name */
    public static final void m1107putStringListimpl(Bundle bundle, String str, List list) {
        str.getClass();
        list.getClass();
        bundle.putStringArrayList(str, SavedStateWriterKt.toArrayListUnsafe(list));
    }

    /* JADX INFO: renamed from: remove-impl, reason: not valid java name */
    public static final void m1108removeimpl(Bundle bundle, String str) {
        str.getClass();
        bundle.remove(str);
    }
}
