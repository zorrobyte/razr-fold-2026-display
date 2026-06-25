package androidx.compose.ui.input.pointer;

/* JADX INFO: compiled from: PointerEvent.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerEvent_androidKt {
    public static final int EmptyPointerKeyboardModifiers() {
        return PointerKeyboardModifiers.m1249constructorimpl(0);
    }

    /* JADX INFO: renamed from: isPrimaryPressed-aHzCx-E, reason: not valid java name */
    public static final boolean m1223isPrimaryPressedaHzCxE(int i) {
        return (i & 33) != 0;
    }
}
