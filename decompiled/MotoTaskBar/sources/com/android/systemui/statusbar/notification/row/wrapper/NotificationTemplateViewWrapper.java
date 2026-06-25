package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationTemplateViewWrapper extends NotificationHeaderViewWrapper {
    private NotificationActionListLayout mActions;
    protected View mActionsContainer;
    private final boolean mAllowHideHeader;
    private boolean mCanHideHeader;
    final ArraySet mCancelledPendingIntents;
    private int mContentHeight;
    private final int mFullHeaderTranslation;
    private float mHeaderTranslation;
    protected ImageView mLeftIcon;
    private int mMinHeightHint;
    private ProgressBar mProgressBar;
    private View mRemoteInputHistory;
    protected ImageView mRightIcon;
    protected View mSmartReplyContainer;
    private TextView mText;
    private TextView mTitle;

    final class ActionPendingIntentCancellationHandler implements View.OnAttachStateChangeListener {
        private static UiOffloadThread sUiOffloadThread;
        private final PendingIntent.CancelListener mCancelListener = new AnonymousClass1();
        private final Consumer mOnCancelledCallback;
        private final PendingIntent mPendingIntent;
        private final View mView;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$1, reason: invalid class name */
        class AnonymousClass1 implements PendingIntent.CancelListener {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCanceled$0(PendingIntent pendingIntent) {
                ActionPendingIntentCancellationHandler.this.mOnCancelledCallback.accept(pendingIntent);
                ActionPendingIntentCancellationHandler.this.remove();
            }

            public void onCanceled(final PendingIntent pendingIntent) {
                ActionPendingIntentCancellationHandler.this.mView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCanceled$0(pendingIntent);
                    }
                });
            }
        }

        ActionPendingIntentCancellationHandler(PendingIntent pendingIntent, View view, Consumer consumer) {
            this.mPendingIntent = pendingIntent;
            this.mView = view;
            this.mOnCancelledCallback = consumer;
        }

        private static UiOffloadThread getUiOffloadThread() {
            if (sUiOffloadThread == null) {
                sUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
            }
            return sUiOffloadThread;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onViewAttachedToWindow$0() {
            this.mPendingIntent.registerCancelListener(this.mCancelListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onViewDetachedFromWindow$1() {
            this.mPendingIntent.unregisterCancelListener(this.mCancelListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$remove$2() {
            this.mPendingIntent.unregisterCancelListener(this.mCancelListener);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            getUiOffloadThread().execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onViewAttachedToWindow$0();
                }
            });
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            getUiOffloadThread().execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onViewDetachedFromWindow$1();
                }
            });
        }

        public void remove() {
            this.mView.removeOnAttachStateChangeListener(this);
            View view = this.mView;
            int i = R$id.pending_intent_listener_tag;
            if (view.getTag(i) == this) {
                this.mView.setTag(i, null);
            }
            getUiOffloadThread().execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$remove$2();
                }
            });
        }
    }

    protected NotificationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mCancelledPendingIntents = new ArraySet();
        this.mAllowHideHeader = context.getResources().getBoolean(R$bool.heads_up_notification_hides_header);
        this.mTransformationHelper.setCustomTransformation(new ViewTransformationHelper.CustomTransformation(this) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper.1
            private float getTransformationY(TransformState transformState, TransformState transformState2) {
                return ((transformState2.getLaidOutLocationOnScreen()[1] + transformState2.getTransformedView().getHeight()) - transformState.getLaidOutLocationOnScreen()[1]) * 0.33f;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean customTransformTarget(TransformState transformState, TransformState transformState2) {
                transformState.setTransformationEndY(getTransformationY(transformState, transformState2));
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean initTransformation(TransformState transformState, TransformState transformState2) {
                transformState.setTransformationStartY(getTransformationY(transformState, transformState2));
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                CrossFadeHelper.fadeIn(transformState.getTransformedView(), f, true);
                if (currentState != null) {
                    transformState.transformViewVerticalFrom(currentState, this, f);
                    currentState.recycle();
                }
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                CrossFadeHelper.fadeOut(transformState.getTransformedView(), f);
                if (currentState != null) {
                    transformState.transformViewVerticalTo(currentState, this, f);
                    currentState.recycle();
                }
                return true;
            }
        }, 2);
        this.mFullHeaderTranslation = context.getResources().getDimensionPixelSize(R.dimen.notification_header_shrink_hide_width) - context.getResources().getDimensionPixelSize(R.dimen.notification_headerless_line_height);
    }

    private int blendColorWithBackground(int i, float f) {
        return ContrastColorUtil.compositeColors(Color.argb((int) (f * 255.0f), Color.red(i), Color.green(i), Color.blue(i)), resolveBackgroundColor());
    }

    private void disableActionView(Button button) {
        if (button.isEnabled()) {
            button.setEnabled(false);
            ColorStateList textColors = button.getTextColors();
            int[] colors = textColors.getColors();
            int[] iArr = new int[colors.length];
            float f = this.mView.getResources().getFloat(R.dimen.notification_content_margin_top);
            for (int i = 0; i < colors.length; i++) {
                iArr[i] = blendColorWithBackground(colors[i], f);
            }
            button.setTextColor(new ColorStateList(textColors.getStates(), iArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableActionViewWithIntent(PendingIntent pendingIntent) {
        this.mCancelledPendingIntents.add(Integer.valueOf(getHashCodeForPendingIntent(pendingIntent)));
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        if (notificationActionListLayout != null) {
            int childCount = notificationActionListLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) this.mActions.getChildAt(i);
                if (pendingIntent.equals(getPendingIntentForAction(button))) {
                    disableActionView(button);
                }
            }
        }
    }

    private static int getHashCodeForPendingIntent(PendingIntent pendingIntent) {
        return System.identityHashCode(pendingIntent.getTarget().asBinder());
    }

    private static PendingIntent getPendingIntentForAction(View view) {
        return (PendingIntent) view.getTag(R.id.permission_list);
    }

    private void resolveTemplateViews(StatusBarNotification statusBarNotification) {
        ImageView imageView = (ImageView) this.mView.findViewById(R.id.scrollView);
        this.mRightIcon = imageView;
        if (imageView != null) {
            imageView.setTag(ImageTransformState.ICON_TAG, getRightIcon(statusBarNotification.getNotification()));
            this.mRightIcon.setTag(TransformState.ALIGN_END_TAG, Boolean.TRUE);
        }
        ImageView imageView2 = (ImageView) this.mView.findViewById(R.id.list_menu_presenter);
        this.mLeftIcon = imageView2;
        if (imageView2 != null) {
            imageView2.setTag(ImageTransformState.ICON_TAG, getLargeIcon(statusBarNotification.getNotification()));
        }
        this.mTitle = (TextView) this.mView.findViewById(R.id.title);
        this.mText = (TextView) this.mView.findViewById(R.id.textPostalAddress);
        View viewFindViewById = this.mView.findViewById(R.id.progress);
        if (viewFindViewById instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar) viewFindViewById;
        } else {
            this.mProgressBar = null;
        }
        this.mSmartReplyContainer = this.mView.findViewById(R.id.splashscreen);
        this.mActionsContainer = this.mView.findViewById(R.id.actions_container);
        this.mActions = this.mView.findViewById(R.id.actions);
        this.mRemoteInputHistory = this.mView.findViewById(R.id.notouch);
        updatePendingIntentCancellations();
    }

    private void updateActionOffset() {
        if (this.mActionsContainer != null) {
            this.mActionsContainer.setTranslationY((Math.max(this.mContentHeight, this.mMinHeightHint) - this.mView.getHeight()) - getHeaderTranslation(false));
        }
    }

    private void updatePendingIntentCancellationListener(Button button, PendingIntent pendingIntent) {
        View.OnAttachStateChangeListener actionPendingIntentCancellationHandler;
        if (pendingIntent != null) {
            actionPendingIntentCancellationHandler = new ActionPendingIntentCancellationHandler(pendingIntent, button, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.disableActionViewWithIntent((PendingIntent) obj);
                }
            });
            button.addOnAttachStateChangeListener(actionPendingIntentCancellationHandler);
            if (button.isAttachedToWindow()) {
                actionPendingIntentCancellationHandler.onViewAttachedToWindow(button);
            }
        } else {
            actionPendingIntentCancellationHandler = null;
        }
        int i = R$id.pending_intent_listener_tag;
        ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler2 = (ActionPendingIntentCancellationHandler) button.getTag(i);
        if (actionPendingIntentCancellationHandler2 != null) {
            actionPendingIntentCancellationHandler2.remove();
        }
        button.setTag(i, actionPendingIntentCancellationHandler);
    }

    private void updatePendingIntentCancellations() {
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        if (notificationActionListLayout != null) {
            int childCount = notificationActionListLayout.getChildCount();
            ArraySet arraySet = new ArraySet(childCount);
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) this.mActions.getChildAt(i);
                PendingIntent pendingIntentForAction = getPendingIntentForAction(button);
                if (pendingIntentForAction != null) {
                    int hashCodeForPendingIntent = getHashCodeForPendingIntent(pendingIntentForAction);
                    arraySet.add(Integer.valueOf(hashCodeForPendingIntent));
                    if (this.mCancelledPendingIntents.contains(Integer.valueOf(hashCodeForPendingIntent))) {
                        disableActionView(button);
                    }
                }
                updatePendingIntentCancellationListener(button, pendingIntentForAction);
            }
            this.mCancelledPendingIntents.retainAll(arraySet);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public int getExtraMeasureHeight() {
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        int extraMeasureHeight = notificationActionListLayout != null ? notificationActionListLayout.getExtraMeasureHeight() : 0;
        View view = this.mRemoteInputHistory;
        if (view != null && view.getVisibility() != 8) {
            extraMeasureHeight += this.mRow.getContext().getResources().getDimensionPixelSize(R$dimen.remote_input_history_extra_height);
        }
        return extraMeasureHeight + super.getExtraMeasureHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public int getHeaderTranslation(boolean z) {
        return (z && this.mCanHideHeader) ? this.mFullHeaderTranslation : (int) this.mHeaderTranslation;
    }

    protected final Icon getLargeIcon(Notification notification) {
        Bitmap bitmap;
        Icon largeIcon = notification.getLargeIcon();
        return (largeIcon != null || (bitmap = notification.largeIcon) == null) ? largeIcon : Icon.createWithBitmap(bitmap);
    }

    protected final Icon getRightIcon(Notification notification) {
        Icon pictureIcon;
        return (notification.extras.getBoolean("android.showBigPictureWhenCollapsed") && notification.isStyle(Notification.BigPictureStyle.class) && (pictureIcon = Notification.BigPictureStyle.getPictureIcon(notification.extras)) != null) ? pictureIcon : getLargeIcon(notification);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ImageView imageView;
        resolveTemplateViews(expandableNotificationRow.getEntry().getSbn());
        super.onContentUpdated(expandableNotificationRow);
        this.mCanHideHeader = this.mAllowHideHeader && this.mNotificationHeader != null && ((imageView = this.mRightIcon) == null || imageView.getVisibility() != 0);
        if (expandableNotificationRow.getHeaderVisibleAmount() != 1.0f) {
            setHeaderVisibleAmount(expandableNotificationRow.getHeaderVisibleAmount());
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setContentHeight(int i, int i2) {
        super.setContentHeight(i, i2);
        this.mContentHeight = i;
        this.mMinHeightHint = i2;
        updateActionOffset();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setHeaderVisibleAmount(float f) {
        float f2;
        NotificationHeaderView notificationHeaderView;
        super.setHeaderVisibleAmount(f);
        if (!this.mCanHideHeader || (notificationHeaderView = this.mNotificationHeader) == null) {
            f2 = 0.0f;
        } else {
            notificationHeaderView.setAlpha(f);
            f2 = (1.0f - f) * this.mFullHeaderTranslation;
        }
        this.mHeaderTranslation = f2;
        this.mView.setTranslationY(f2);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public boolean shouldClipToRounding(boolean z, boolean z2) {
        View view;
        if (super.shouldClipToRounding(z, z2)) {
            return true;
        }
        return (!z2 || (view = this.mActionsContainer) == null || view.getVisibility() == 8) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    protected void updateTransformedTypes() {
        super.updateTransformedTypes();
        TextView textView = this.mTitle;
        if (textView != null) {
            this.mTransformationHelper.addTransformedView(1, textView);
        }
        TextView textView2 = this.mText;
        if (textView2 != null) {
            this.mTransformationHelper.addTransformedView(2, textView2);
        }
        ImageView imageView = this.mRightIcon;
        if (imageView != null) {
            this.mTransformationHelper.addTransformedView(3, imageView);
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            this.mTransformationHelper.addTransformedView(4, progressBar);
        }
        addViewsTransformingToSimilar(this.mLeftIcon);
        addTransformedViews(this.mSmartReplyContainer);
    }
}
