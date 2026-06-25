package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;
import android.view.DragEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceIdSequence;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.policy.HeadsUpManager;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableNotificationRowDragController {
    private static final String TAG = "ExpandableNotificationRowDragController";
    private final Context mContext;
    private final HeadsUpManager mHeadsUpManager;
    private int mIconSize;
    private NotificationPanelLogger mNotificationPanelLogger;

    public static /* synthetic */ void $r8$lambda$z_NnL1LZaxc6u0ZnH5n7LODyBcE(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, ValueAnimator valueAnimator) {
        transaction.setAlpha(surfaceControl, 1.0f - valueAnimator.getAnimatedFraction());
        transaction.apply();
    }

    public ExpandableNotificationRowDragController(Context context, HeadsUpManager headsUpManager, NotificationPanelLogger notificationPanelLogger) {
        this.mContext = context;
        this.mHeadsUpManager = headsUpManager;
        this.mNotificationPanelLogger = notificationPanelLogger;
        init();
    }

    private void dismissShade() {
    }

    private void fadeOutAndRemoveDragSurface(DragEvent dragEvent) {
        final SurfaceControl dragSurface = dragEvent.getDragSurface();
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ExpandableNotificationRowDragController.$r8$lambda$z_NnL1LZaxc6u0ZnH5n7LODyBcE(transaction, dragSurface, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController.1
            private boolean mCanceled = false;

            private void cleanUpSurface() {
                transaction.remove(dragSurface);
                transaction.apply();
                transaction.close();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                cleanUpSurface();
                this.mCanceled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (this.mCanceled) {
                    return;
                }
                cleanUpSurface();
            }
        });
        valueAnimatorOfFloat.start();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private View.OnDragListener getDraggedViewDragListener() {
        return new View.OnDragListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                return this.f$0.lambda$getDraggedViewDragListener$0(view, dragEvent);
            }
        };
    }

    private Drawable getPkgIcon(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 795136);
            if (applicationInfo != null) {
                return packageManager.getApplicationIcon(applicationInfo);
            }
            Log.d(TAG, " application info is null ");
            return packageManager.getDefaultActivityIcon();
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "can not find package with : " + str);
            return packageManager.getDefaultActivityIcon();
        }
    }

    private void init() {
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.drag_and_drop_icon_size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getDraggedViewDragListener$0(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action != 1) {
            if (action != 4) {
                return false;
            }
            if (!dragEvent.getResult()) {
                fadeOutAndRemoveDragSurface(dragEvent);
            } else if (view instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) view).dragAndDropSuccess();
            }
            view.setOnDragListener(null);
        }
        return true;
    }

    public void startDragAndDrop(View view) {
        ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
        Notification notification = expandableNotificationRow.getEntry().getSbn().getNotification();
        PendingIntent pendingIntent = notification.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification.fullScreenIntent;
        }
        if (pendingIntent == null) {
            if (!expandableNotificationRow.isPinned()) {
                dismissShade();
            }
            Toast.makeText(this.mContext, R$string.drag_split_not_supported, 0).show();
            return;
        }
        Bitmap bitmapFromDrawable = getBitmapFromDrawable(getPkgIcon(expandableNotificationRow.getEntry().getSbn().getPackageName()));
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageBitmap(bitmapFromDrawable);
        int i = this.mIconSize;
        imageView.layout(0, 0, i, i);
        ClipDescription clipDescription = new ClipDescription("Drag And Drop", new String[]{"application/vnd.android.activity"});
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.PENDING_INTENT", pendingIntent);
        intent.putExtra("android.intent.extra.USER", Process.myUserHandle());
        ClipData.Item item = new ClipData.Item(intent);
        item.getIntent().putExtra("android.intent.extra.LOGGING_INSTANCE_ID", (Parcelable) new InstanceIdSequence(Integer.MAX_VALUE).newInstanceId());
        ClipData clipData = new ClipData(clipDescription, item);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(imageView);
        view.setOnDragListener(getDraggedViewDragListener());
        if (view.startDragAndDrop(clipData, dragShadowBuilder, null, 2304)) {
            this.mNotificationPanelLogger.logNotificationDrag(expandableNotificationRow.getEntry());
            view.performHapticFeedback(0);
            if (expandableNotificationRow.isPinned()) {
                this.mHeadsUpManager.releaseAllImmediately();
            } else {
                dismissShade();
            }
        }
    }
}
