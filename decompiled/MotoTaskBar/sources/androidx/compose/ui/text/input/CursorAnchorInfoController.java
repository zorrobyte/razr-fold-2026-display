package androidx.compose.ui.text.input;

import android.view.inputmethod.CursorAnchorInfo;
import androidx.compose.ui.graphics.AndroidMatrixConversions_androidKt;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: CursorAnchorInfoController.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CursorAnchorInfoController {
    private boolean hasPendingImmediateRequest;
    private boolean includeCharacterBounds;
    private boolean includeEditorBounds;
    private boolean includeInsertionMarker;
    private boolean includeLineBounds;
    private final InputMethodManager inputMethodManager;
    private boolean monitorEnabled;
    private final MatrixPositionCalculator rootPositionCalculator;
    private TextFieldValue textFieldValue;
    private final Object lock = new Object();
    private Function1 textFieldToRootTransform = new Function1() { // from class: androidx.compose.ui.text.input.CursorAnchorInfoController$textFieldToRootTransform$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            m832invoke58bKbWc(((Matrix) obj).m316unboximpl());
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke-58bKbWc, reason: not valid java name */
        public final void m832invoke58bKbWc(float[] fArr) {
        }
    };
    private final CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
    private final float[] matrix = Matrix.m305constructorimpl$default(null, 1, null);
    private final android.graphics.Matrix androidMatrix = new android.graphics.Matrix();

    public CursorAnchorInfoController(MatrixPositionCalculator matrixPositionCalculator, InputMethodManager inputMethodManager) {
        this.rootPositionCalculator = matrixPositionCalculator;
        this.inputMethodManager = inputMethodManager;
    }

    private final void updateCursorAnchorInfo() {
        if (this.inputMethodManager.isActive()) {
            this.textFieldToRootTransform.invoke(Matrix.m303boximpl(this.matrix));
            this.rootPositionCalculator.mo472localToScreen58bKbWc(this.matrix);
            AndroidMatrixConversions_androidKt.m219setFromEL8BTi8(this.androidMatrix, this.matrix);
            this.textFieldValue.getClass();
            throw null;
        }
    }

    public final void requestUpdate(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        synchronized (this.lock) {
            try {
                this.includeInsertionMarker = z3;
                this.includeCharacterBounds = z4;
                this.includeEditorBounds = z5;
                this.includeLineBounds = z6;
                if (z) {
                    this.hasPendingImmediateRequest = true;
                    if (this.textFieldValue != null) {
                        updateCursorAnchorInfo();
                    }
                }
                this.monitorEnabled = z2;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
