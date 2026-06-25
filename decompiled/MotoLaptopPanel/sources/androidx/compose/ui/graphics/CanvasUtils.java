package androidx.compose.ui.graphics;

/* JADX INFO: compiled from: CanvasUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CanvasUtils {
    public static final CanvasUtils INSTANCE = new CanvasUtils();

    private CanvasUtils() {
    }

    public final void enableZ(android.graphics.Canvas canvas, boolean z) {
        CanvasZHelper.INSTANCE.enableZ(canvas, z);
    }
}
