package androidx.compose.ui.platform;

import android.app.Activity;
import android.graphics.Rect;

/* JADX INFO: compiled from: AndroidWindowInfo.android.kt */
/* JADX INFO: loaded from: classes.dex */
interface BoundsHelper {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: AndroidWindowInfo.android.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final BoundsHelper getInstance() {
            return BoundsHelperApi30Impl.INSTANCE;
        }
    }

    Rect currentWindowBounds(Activity activity);
}
