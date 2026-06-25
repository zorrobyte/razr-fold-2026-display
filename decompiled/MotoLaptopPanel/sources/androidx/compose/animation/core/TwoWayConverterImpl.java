package androidx.compose.animation.core;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: VectorConverters.kt */
/* JADX INFO: loaded from: classes.dex */
final class TwoWayConverterImpl implements TwoWayConverter {
    private final Function1 convertFromVector;
    private final Function1 convertToVector;

    public TwoWayConverterImpl(Function1 function1, Function1 function12) {
        this.convertToVector = function1;
        this.convertFromVector = function12;
    }

    @Override // androidx.compose.animation.core.TwoWayConverter
    public Function1 getConvertFromVector() {
        return this.convertFromVector;
    }

    @Override // androidx.compose.animation.core.TwoWayConverter
    public Function1 getConvertToVector() {
        return this.convertToVector;
    }
}
