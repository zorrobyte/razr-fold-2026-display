package androidx.compose.ui.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: AndroidWindowInfo.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LazyWindowInfo implements WindowInfo {
    private MutableState _containerSize;
    private final MutableState isWindowFocused$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, null, 2, null);
    private Function0 onInitializeContainerSize;

    /* JADX INFO: renamed from: setKeyboardModifiers-5xRPYO0, reason: not valid java name */
    public void m1471setKeyboardModifiers5xRPYO0(int i) {
        WindowInfoImpl.Companion.getGlobalKeyboardModifiers$ui_release().setValue(PointerKeyboardModifiers.m1248boximpl(i));
    }

    public final void setOnInitializeContainerSize(Function0 function0) {
        if (this._containerSize == null) {
            this.onInitializeContainerSize = function0;
        }
    }

    public void setWindowFocused(boolean z) {
        this.isWindowFocused$delegate.setValue(Boolean.valueOf(z));
    }
}
