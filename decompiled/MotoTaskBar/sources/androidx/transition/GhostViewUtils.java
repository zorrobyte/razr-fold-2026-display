package androidx.transition;

import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
abstract class GhostViewUtils {
    static GhostView addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
        return GhostViewPort.addGhost(view, viewGroup, matrix);
    }

    static void removeGhost(View view) {
        GhostViewPort.removeGhost(view);
    }
}
