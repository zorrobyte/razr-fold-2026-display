package androidx.transition;

import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
abstract class ViewGroupUtils {

    abstract class Api29Impl {
        static void suppressLayout(ViewGroup viewGroup, boolean z) {
            viewGroup.suppressLayout(z);
        }
    }

    static void suppressLayout(ViewGroup viewGroup, boolean z) {
        Api29Impl.suppressLayout(viewGroup, z);
    }
}
