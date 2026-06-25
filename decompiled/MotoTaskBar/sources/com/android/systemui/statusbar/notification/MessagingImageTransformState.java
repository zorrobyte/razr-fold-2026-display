package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.internal.widget.MessagingImageMessage;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.TransformState;

/* JADX INFO: loaded from: classes.dex */
public class MessagingImageTransformState extends ImageTransformState {
    private MessagingImageMessage mImageMessage;
    private static Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    private static final int START_ACTUAL_WIDTH = R$id.transformation_start_actual_width;
    private static final int START_ACTUAL_HEIGHT = R$id.transformation_start_actual_height;

    public static MessagingImageTransformState obtain() {
        MessagingImageTransformState messagingImageTransformState = (MessagingImageTransformState) sInstancePool.acquire();
        return messagingImageTransformState != null ? messagingImageTransformState : new MessagingImageTransformState();
    }

    public int getStartActualHeight() {
        Object tag = this.mTransformedView.getTag(START_ACTUAL_HEIGHT);
        if (tag == null) {
            return -1;
        }
        return ((Integer) tag).intValue();
    }

    public int getStartActualWidth() {
        Object tag = this.mTransformedView.getTag(START_ACTUAL_WIDTH);
        if (tag == null) {
            return -1;
        }
        return ((Integer) tag).intValue();
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        this.mImageMessage = (MessagingImageMessage) view;
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        if (getClass() == MessagingImageTransformState.class) {
            sInstancePool.release(this);
        }
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    protected void reset() {
        super.reset();
        this.mImageMessage = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void resetTransformedView() {
        super.resetTransformedView();
        MessagingImageMessage messagingImageMessage = this.mImageMessage;
        messagingImageMessage.setActualWidth(messagingImageMessage.getWidth());
        MessagingImageMessage messagingImageMessage2 = this.mImageMessage;
        messagingImageMessage2.setActualHeight(messagingImageMessage2.getHeight());
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    protected boolean sameAs(TransformState transformState) {
        if (super.sameAs(transformState)) {
            return true;
        }
        if (transformState instanceof MessagingImageTransformState) {
            return this.mImageMessage.sameAs(((MessagingImageTransformState) transformState).mImageMessage);
        }
        return false;
    }

    public void setStartActualHeight(int i) {
        this.mTransformedView.setTag(START_ACTUAL_HEIGHT, Integer.valueOf(i));
    }

    public void setStartActualWidth(int i) {
        this.mTransformedView.setTag(START_ACTUAL_WIDTH, Integer.valueOf(i));
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected boolean transformScale(TransformState transformState) {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        super.transformViewFrom(transformState, i, customTransformation, f);
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if ((transformState instanceof MessagingImageTransformState) && sameAs(transformState)) {
            MessagingImageMessage messagingImageMessage = ((MessagingImageTransformState) transformState).mImageMessage;
            if (f == 0.0f) {
                setStartActualWidth(messagingImageMessage.getActualWidth());
                setStartActualHeight(messagingImageMessage.getActualHeight());
            }
            float startActualWidth = getStartActualWidth();
            this.mImageMessage.setActualWidth((int) NotificationUtils.interpolate(startActualWidth, r3.getWidth(), interpolation));
            float startActualHeight = getStartActualHeight();
            this.mImageMessage.setActualHeight((int) NotificationUtils.interpolate(startActualHeight, r0.getHeight(), interpolation));
        }
    }
}
