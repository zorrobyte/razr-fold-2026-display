package androidx.compose.ui.res;

import androidx.collection.MutableIntObjectMap;
import kotlin.Unit;

/* JADX INFO: compiled from: Resources.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ResourceIdCache {
    private final MutableIntObjectMap resIdPathMap = new MutableIntObjectMap(0, 1, null);

    public final void clear() {
        synchronized (this) {
            this.resIdPathMap.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}
