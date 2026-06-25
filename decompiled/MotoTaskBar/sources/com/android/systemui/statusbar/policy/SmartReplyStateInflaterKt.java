package com.android.systemui.statusbar.policy;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.View;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationUiAdjustment;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SmartReplyStateInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SmartReplyStateInflaterKt {
    private static final ThreadPoolExecutor iconTaskThreadPool = new ThreadPoolExecutor(0, 25, 1, TimeUnit.MINUTES, new SynchronousQueue());
    private static final boolean DEBUG = Log.isLoggable("SmartReplyViewInflater", 3);

    public static final boolean areSuggestionsSimilar(InflatedSmartReplyState inflatedSmartReplyState, InflatedSmartReplyState inflatedSmartReplyState2) {
        if (inflatedSmartReplyState == inflatedSmartReplyState2) {
            return true;
        }
        return inflatedSmartReplyState != null && inflatedSmartReplyState2 != null && inflatedSmartReplyState.getHasPhishingAction() == inflatedSmartReplyState2.getHasPhishingAction() && Intrinsics.areEqual(inflatedSmartReplyState.getSmartRepliesList(), inflatedSmartReplyState2.getSmartRepliesList()) && Intrinsics.areEqual(inflatedSmartReplyState.getSuppressedActionIndices(), inflatedSmartReplyState2.getSuppressedActionIndices()) && !NotificationUiAdjustment.areDifferent(inflatedSmartReplyState.getSmartActionsList(), inflatedSmartReplyState2.getSmartActionsList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWhenUnlocked(KeyguardDismissUtil keyguardDismissUtil, boolean z, final Function0 function0) {
        keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction(function0) { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$sam$com_android_systemui_displays_ActivityStarter_OnDismissAction$0
            private final /* synthetic */ Function0 function;

            {
                function0.getClass();
                this.function = function0;
            }

            @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
            public final /* synthetic */ boolean onDismiss() {
                return ((Boolean) this.function.mo2224invoke()).booleanValue();
            }
        }, z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Drawable loadIconDrawableWithTimeout(final Icon icon, final Context context, final int i) {
        Object objM2707constructorimpl;
        if (icon.getType() != 4 && icon.getType() != 6) {
            return icon.loadDrawable(context);
        }
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1
            @Override // java.util.concurrent.Callable
            public final Bitmap call() throws IOException {
                Context context2 = context;
                Icon icon2 = icon;
                final int i2 = i;
                long jCurrentTimeMillis = System.currentTimeMillis();
                ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(context2.getContentResolver(), icon2.getUri());
                sourceCreateSource.getClass();
                Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1$durationMillis$1$1
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.getClass();
                        imageInfo.getClass();
                        source.getClass();
                        int i3 = i2;
                        imageDecoder.setTargetSize(i3, i3);
                        imageDecoder.setAllocator(0);
                    }
                });
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                if (jCurrentTimeMillis2 > 500) {
                    Log.w("SmartReplyViewInflater", "Loading " + icon + " took " + (jCurrentTimeMillis2 / 1000.0f) + " sec");
                }
                if (bitmapDecodeBitmap != null) {
                    return bitmapDecodeBitmap;
                }
                throw new IllegalStateException("ImageDecoder.decodeBitmap() returned null");
            }
        });
        try {
            Result.Companion companion = Result.Companion;
            iconTaskThreadPool.execute(futureTask);
            objM2707constructorimpl = Result.m2707constructorimpl((Bitmap) futureTask.get(500L, TimeUnit.MILLISECONDS));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            Log.e("SmartReplyViewInflater", "Failed to load " + icon + ": " + thM2709exceptionOrNullimpl);
            futureTask.cancel(true);
            return null;
        }
        Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), (Bitmap) objM2707constructorimpl);
        if (icon.getType() == 6) {
            bitmapDrawable = new AdaptiveIconDrawable(null, bitmapDrawable);
        }
        if (icon.hasTint()) {
            bitmapDrawable.mutate();
            bitmapDrawable.setTintList(icon.getTintList());
            bitmapDrawable.setTintBlendMode(icon.getTintBlendMode());
        }
        return bitmapDrawable;
    }

    public static final boolean shouldShowSmartReplyView(NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState) {
        notificationEntry.getClass();
        inflatedSmartReplyState.getClass();
        if ((inflatedSmartReplyState.getSmartReplies() == null && inflatedSmartReplyState.getSmartActions() == null) || notificationEntry.getSbn().getNotification().extras.getBoolean("android.remoteInputSpinner", false)) {
            return false;
        }
        return !notificationEntry.getSbn().getNotification().extras.getBoolean("android.hideSmartReplies", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startPendingIntentDismissingKeyguard(ActivityStarter activityStarter, PendingIntent pendingIntent, View view, final Function0 function0) {
        activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new Runnable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt.startPendingIntentDismissingKeyguard.1
            @Override // java.lang.Runnable
            public final void run() {
                function0.mo2224invoke();
            }
        }, view);
    }
}
