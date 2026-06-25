package androidx.window.layout.util;

import android.content.Context;
import android.graphics.Rect;

/* JADX INFO: compiled from: BoundsHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public interface BoundsHelper {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: BoundsHelper.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final String TAG = BoundsHelper.class.getSimpleName();

        private Companion() {
        }

        public final BoundsHelper getInstance() {
            return BoundsHelperApi30Impl.INSTANCE;
        }
    }

    Rect maximumWindowBounds(Context context);
}
