package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NotificationConversationTemplateViewWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationConversationTemplateViewWrapper extends NotificationTemplateViewWrapper {
    private View appName;
    private View conversationBadgeBg;
    private View conversationIconContainer;
    private CachingIconView conversationIconView;
    private final ConversationLayout conversationLayout;
    private View conversationTitleView;
    private View expandBtn;
    private View expandBtnContainer;
    private View facePileBottom;
    private View facePileBottomBg;
    private View facePileTop;
    private ViewGroup imageMessageContainer;
    private View importanceRing;
    private ArrayList messageContainers;
    private MessagingLinearLayout messagingLinearLayout;
    private final int minHeightWithActions;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationConversationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        context.getClass();
        view.getClass();
        expandableNotificationRow.getClass();
        this.minHeightWithActions = NotificationUtils.getFontScaledHeight(context, R$dimen.notification_messaging_actions_min_height);
        this.conversationLayout = (ConversationLayout) view;
    }

    private final void resolveViews() {
        this.messagingLinearLayout = this.conversationLayout.getMessagingLinearLayout();
        this.imageMessageContainer = this.conversationLayout.getImageMessageContainer();
        this.messageContainers = this.conversationLayout.getMessagingGroups();
        ConversationLayout conversationLayout = this.conversationLayout;
        this.conversationIconContainer = conversationLayout.requireViewById(R.id.cross_task_transition);
        this.conversationIconView = conversationLayout.requireViewById(R.id.conversation_icon_container);
        this.conversationBadgeBg = conversationLayout.requireViewById(R.id.conversation_text);
        this.expandBtn = conversationLayout.requireViewById(R.id.expand_button_number);
        this.expandBtnContainer = conversationLayout.requireViewById(R.id.expand_button_touch_container);
        this.importanceRing = conversationLayout.requireViewById(R.id.costsMoney);
        this.appName = conversationLayout.requireViewById(R.id.app_ops);
        this.conversationTitleView = conversationLayout.requireViewById(R.id.crosshair);
        this.facePileTop = conversationLayout.findViewById(R.id.conversation_icon_badge_bg);
        this.facePileBottom = conversationLayout.findViewById(R.id.conversation_icon);
        this.facePileBottomBg = conversationLayout.findViewById(R.id.conversation_icon_badge);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MessagingLinearLayout setAnimationsRunning$lambda$1(MessagingGroup messagingGroup) {
        messagingGroup.getClass();
        return messagingGroup.getMessageContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence setAnimationsRunning$lambda$2(ViewGroup viewGroup) {
        viewGroup.getClass();
        return ConvenienceExtensionsKt.getChildren(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AnimatedImageDrawable setAnimationsRunning$lambda$4(View view) {
        MessagingImageMessage messagingImageMessage = view instanceof MessagingImageMessage ? (MessagingImageMessage) view : null;
        if (messagingImageMessage != null) {
            Drawable drawable = messagingImageMessage.getDrawable();
            if (drawable instanceof AnimatedImageDrawable) {
                return (AnimatedImageDrawable) drawable;
            }
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public int getMinLayoutHeight() {
        View view = this.mActionsContainer;
        return (view == null || view.getVisibility() == 8) ? super.getMinLayoutHeight() : this.minHeightWithActions;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        expandableNotificationRow.getClass();
        resolveViews();
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setAnimationsRunning(boolean z) {
        ArrayList arrayList = this.messageContainers;
        ViewGroup viewGroup = null;
        if (arrayList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("messageContainers");
            arrayList = null;
        }
        Sequence map = SequencesKt.map(CollectionsKt.asSequence(arrayList), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationConversationTemplateViewWrapper.setAnimationsRunning$lambda$1((MessagingGroup) obj);
            }
        });
        ViewGroup[] viewGroupArr = new ViewGroup[1];
        ViewGroup viewGroup2 = this.imageMessageContainer;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageMessageContainer");
        } else {
            viewGroup = viewGroup2;
        }
        viewGroupArr[0] = viewGroup;
        for (AnimatedImageDrawable animatedImageDrawable : SequencesKt.toSet(SequencesKt.mapNotNull(SequencesKt.flatMap(SequencesKt.plus(map, SequencesKt.sequenceOf(viewGroupArr)), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationConversationTemplateViewWrapper.setAnimationsRunning$lambda$2((ViewGroup) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationConversationTemplateViewWrapper$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationConversationTemplateViewWrapper.setAnimationsRunning$lambda$4((View) obj);
            }
        }))) {
            if (z) {
                animatedImageDrawable.start();
            } else if (!z) {
                animatedImageDrawable.stop();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setNotificationFaded(boolean z) {
        View view = this.expandBtn;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expandBtn");
            view = null;
        }
        NotificationFadeAware.setLayerTypeForFaded(view, z);
        View view3 = this.conversationIconContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("conversationIconContainer");
        } else {
            view2 = view3;
        }
        NotificationFadeAware.setLayerTypeForFaded(view2, z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setRemoteInputVisible(boolean z) {
        this.conversationLayout.showHistoricMessages(z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        onClickListener.getClass();
        this.conversationLayout.updateExpandability(z, onClickListener);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    protected void updateTransformedTypes() {
        super.updateTransformedTypes();
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        View view = this.conversationTitleView;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("conversationTitleView");
            view = null;
        }
        viewTransformationHelper.addTransformedView(1, view);
        View[] viewArr = new View[2];
        MessagingLinearLayout messagingLinearLayout = this.messagingLinearLayout;
        if (messagingLinearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("messagingLinearLayout");
            messagingLinearLayout = null;
        }
        viewArr[0] = messagingLinearLayout;
        View view3 = this.appName;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appName");
            view3 = null;
        }
        viewArr[1] = view3;
        addTransformedViews(viewArr);
        ViewTransformationHelper viewTransformationHelper2 = this.mTransformationHelper;
        ViewGroup viewGroup = this.imageMessageContainer;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageMessageContainer");
            viewGroup = null;
        }
        NotificationMessagingTemplateViewWrapper.setCustomImageMessageTransform(viewTransformationHelper2, viewGroup);
        View[] viewArr2 = new View[7];
        CachingIconView cachingIconView = this.conversationIconView;
        if (cachingIconView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("conversationIconView");
            cachingIconView = null;
        }
        viewArr2[0] = cachingIconView;
        View view4 = this.conversationBadgeBg;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("conversationBadgeBg");
            view4 = null;
        }
        viewArr2[1] = view4;
        View view5 = this.expandBtn;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expandBtn");
            view5 = null;
        }
        viewArr2[2] = view5;
        View view6 = this.importanceRing;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("importanceRing");
        } else {
            view2 = view6;
        }
        viewArr2[3] = view2;
        viewArr2[4] = this.facePileTop;
        viewArr2[5] = this.facePileBottom;
        viewArr2[6] = this.facePileBottomBg;
        addViewsTransformingToSimilar(viewArr2);
    }
}
