package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.widget.BigPictureNotificationImageView;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.row.BigPictureIconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class NotificationBigPictureTemplateViewWrapper extends NotificationTemplateViewWrapper {
    private BigPictureNotificationImageView mImageView;

    protected NotificationBigPictureTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    private void resolveViews() {
        this.mImageView = this.mView.findViewById(R.id.big_text);
    }

    private void updateImageTag(StatusBarNotification statusBarNotification) {
        Icon icon = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.largeIcon.big", Icon.class);
        if (icon == null) {
            this.mRightIcon.setTag(ImageTransformState.ICON_TAG, getLargeIcon(statusBarNotification.getNotification()));
            return;
        }
        ImageView imageView = this.mRightIcon;
        int i = ImageTransformState.ICON_TAG;
        imageView.setTag(i, icon);
        this.mLeftIcon.setTag(i, icon);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentShown(boolean z) {
        super.onContentShown(z);
        BigPictureIconManager bigPictureIconManager = this.mRow.getBigPictureIconManager();
        if (bigPictureIconManager != null) {
            bigPictureIconManager.onViewShown(z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        super.onContentUpdated(expandableNotificationRow);
        resolveViews();
        updateImageTag(expandableNotificationRow.getEntry().getSbn());
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setAnimationsRunning(boolean z) {
        BigPictureNotificationImageView bigPictureNotificationImageView = this.mImageView;
        if (bigPictureNotificationImageView == null) {
            return;
        }
        Drawable drawable = bigPictureNotificationImageView.getDrawable();
        if (drawable instanceof AnimatedImageDrawable) {
            AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) drawable;
            if (z) {
                animatedImageDrawable.start();
            } else {
                animatedImageDrawable.stop();
            }
        }
    }
}
