package androidx.window.layout.util;

import android.content.Context;
import android.content.res.Configuration;
import android.view.WindowMetrics;

/* JADX INFO: compiled from: DensityCompatHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DensityCompatHelper {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: DensityCompatHelper.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final DensityCompatHelper getInstance() {
            return DensityCompatHelperApi34Impl.INSTANCE;
        }
    }

    float density(Context context);

    float density(Configuration configuration, WindowMetrics windowMetrics);
}
