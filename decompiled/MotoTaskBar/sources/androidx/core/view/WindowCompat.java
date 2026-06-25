package androidx.core.view;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public abstract class WindowCompat {

    abstract class Api35Impl {
        static void setDecorFitsSystemWindows(Window window, boolean z) {
            window.setDecorFitsSystemWindows(z);
        }
    }

    public static WindowInsetsControllerCompat getInsetsController(Window window, View view) {
        return new WindowInsetsControllerCompat(window, view);
    }

    public static void setDecorFitsSystemWindows(Window window, boolean z) {
        Api35Impl.setDecorFitsSystemWindows(window, z);
    }
}
