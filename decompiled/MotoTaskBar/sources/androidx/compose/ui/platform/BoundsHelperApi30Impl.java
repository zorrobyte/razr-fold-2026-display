package androidx.compose.ui.platform;

import android.app.Activity;
import android.graphics.Rect;
import android.view.WindowManager;

/* JADX INFO: compiled from: AndroidWindowInfo.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class BoundsHelperApi30Impl implements BoundsHelper {
    public static final BoundsHelperApi30Impl INSTANCE = new BoundsHelperApi30Impl();

    private BoundsHelperApi30Impl() {
    }

    @Override // androidx.compose.ui.platform.BoundsHelper
    public Rect currentWindowBounds(Activity activity) {
        return ((WindowManager) activity.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
    }
}
