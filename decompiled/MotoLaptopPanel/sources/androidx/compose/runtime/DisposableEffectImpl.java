package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes.dex */
final class DisposableEffectImpl implements RememberObserver {
    private final Function1 effect;
    private DisposableEffectResult onDispose;

    public DisposableEffectImpl(Function1 function1) {
        this.effect = function1;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onAbandoned() {
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onForgotten() {
        DisposableEffectResult disposableEffectResult = this.onDispose;
        if (disposableEffectResult != null) {
            disposableEffectResult.dispose();
        }
        this.onDispose = null;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onRemembered() {
        this.onDispose = (DisposableEffectResult) this.effect.invoke(EffectsKt.InternalDisposableEffectScope);
    }
}
