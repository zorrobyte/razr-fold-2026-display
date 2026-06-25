package androidx.compose.ui.platform;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Rect;
import androidx.compose.ui.unit.IntSize;

/* JADX INFO: compiled from: AndroidWindowInfo.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidWindowInfo_androidKt {
    public static final long calculateWindowSize(AndroidComposeView androidComposeView) {
        Context context = androidComposeView.getContext();
        Activity activityFindActivity = findActivity(context);
        if (activityFindActivity != null) {
            Rect rectCurrentWindowBounds = BoundsHelper.Companion.getInstance().currentWindowBounds(activityFindActivity);
            return IntSize.m1919constructorimpl((((long) rectCurrentWindowBounds.height()) & 4294967295L) | (((long) rectCurrentWindowBounds.width()) << 32));
        }
        Configuration configuration = context.getResources().getConfiguration();
        float f = context.getResources().getDisplayMetrics().density;
        return IntSize.m1919constructorimpl((((long) Math.round(configuration.screenHeightDp * f)) & 4294967295L) | (((long) Math.round(configuration.screenWidthDp * f)) << 32));
    }

    private static final Activity findActivity(Context context) {
        while (!(context instanceof Activity)) {
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }
}
