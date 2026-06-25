package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;

/* JADX INFO: compiled from: Row.kt */
/* JADX INFO: loaded from: classes.dex */
public interface RowScope {
    static /* synthetic */ Modifier weight$default(RowScope rowScope, Modifier modifier, float f, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: weight");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return rowScope.weight(modifier, f, z);
    }

    Modifier weight(Modifier modifier, float f, boolean z);
}
