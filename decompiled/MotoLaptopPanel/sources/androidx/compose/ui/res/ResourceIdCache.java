package androidx.compose.ui.res;

import android.content.res.Resources;
import android.util.TypedValue;
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

    public final TypedValue resolveResourcePath(Resources resources, int i) {
        TypedValue typedValue;
        synchronized (this) {
            typedValue = (TypedValue) this.resIdPathMap.get(i);
            if (typedValue == null) {
                typedValue = new TypedValue();
                resources.getValue(i, typedValue, true);
                this.resIdPathMap.put(i, typedValue);
            }
        }
        return typedValue;
    }
}
