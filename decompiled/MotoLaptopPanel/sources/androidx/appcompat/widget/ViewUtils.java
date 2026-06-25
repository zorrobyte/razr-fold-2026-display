package androidx.appcompat.widget;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewUtils {
    static final boolean SDK_LEVEL_SUPPORTS_AUTOSIZE = true;

    public static boolean isLayoutRtl(View view) {
        return view.getLayoutDirection() == 1;
    }
}
