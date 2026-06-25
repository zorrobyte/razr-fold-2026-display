package androidx.compose.ui.platform.actionmodecallback;

import androidx.compose.ui.geometry.Rect;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextActionModeCallback.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextActionModeCallback {
    private final Function0 onActionModeDestroy;
    private Function0 onAutofillRequested;
    private Function0 onCopyRequested;
    private Function0 onCutRequested;
    private Function0 onPasteRequested;
    private Function0 onSelectAllRequested;
    private Rect rect;

    public TextActionModeCallback(Function0 function0, Rect rect, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06) {
        this.onActionModeDestroy = function0;
        this.rect = rect;
        this.onCopyRequested = function02;
        this.onPasteRequested = function03;
        this.onCutRequested = function04;
        this.onSelectAllRequested = function05;
        this.onAutofillRequested = function06;
    }

    public /* synthetic */ TextActionModeCallback(Function0 function0, Rect rect, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : function0, (i & 2) != 0 ? Rect.Companion.getZero() : rect, (i & 4) != 0 ? null : function02, (i & 8) != 0 ? null : function03, (i & 16) != 0 ? null : function04, (i & 32) != 0 ? null : function05, (i & 64) != 0 ? null : function06);
    }
}
