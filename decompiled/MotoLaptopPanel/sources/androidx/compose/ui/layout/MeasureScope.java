package androidx.compose.ui.layout;

import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: MeasureScope.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MeasureScope extends IntrinsicMeasureScope {
    static /* synthetic */ MeasureResult layout$default(MeasureScope measureScope, int i, int i2, Map map, Function1 function1, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: layout");
        }
        if ((i3 & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        return measureScope.layout(i, i2, map, function1);
    }

    default MeasureResult layout(int i, int i2, Map map, Function1 function1) {
        return layout(i, i2, map, null, function1);
    }

    MeasureResult layout(int i, int i2, Map map, Function1 function1, Function1 function12);
}
