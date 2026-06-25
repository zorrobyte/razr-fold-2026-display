package androidx.compose.ui.draw;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DrawModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DrawResult {
    private Function1 block;

    public DrawResult(Function1 function1) {
        this.block = function1;
    }

    public final Function1 getBlock$ui_release() {
        return this.block;
    }
}
