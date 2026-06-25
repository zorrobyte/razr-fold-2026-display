package androidx.compose.runtime.collection;

import androidx.collection.ScatterSet;
import java.util.Set;

/* JADX INFO: compiled from: ScatterSetWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ScatterSetWrapperKt {
    public static final Set wrapIntoSet(ScatterSet scatterSet) {
        return new ScatterSetWrapper(scatterSet);
    }
}
