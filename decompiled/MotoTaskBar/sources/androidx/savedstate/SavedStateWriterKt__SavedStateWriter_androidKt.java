package androidx.savedstate;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: SavedStateWriter.android.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class SavedStateWriterKt__SavedStateWriter_androidKt {
    public static final ArrayList toArrayListUnsafe(Collection collection) {
        collection.getClass();
        return collection instanceof ArrayList ? (ArrayList) collection : new ArrayList(collection);
    }
}
