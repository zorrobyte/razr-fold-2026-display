package androidx.core.view;

import android.view.ViewGroup;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewGroupCompat {
    private static final WindowInsets CONSUMED = WindowInsetsCompat.CONSUMED.toWindowInsets();
    static boolean sCompatInsetsDispatchInstalled = false;

    abstract class Api21Impl {
        static boolean isTransitionGroup(ViewGroup viewGroup) {
            return viewGroup.isTransitionGroup();
        }
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        return Api21Impl.isTransitionGroup(viewGroup);
    }
}
