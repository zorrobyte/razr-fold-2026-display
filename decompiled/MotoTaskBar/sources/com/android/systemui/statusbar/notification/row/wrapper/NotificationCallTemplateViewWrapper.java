package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.view.View;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.CallLayout;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationCallTemplateViewWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationCallTemplateViewWrapper extends NotificationTemplateViewWrapper {
    private View appName;
    private final CallLayout callLayout;
    private View conversationBadgeBg;
    private View conversationIconContainer;
    private CachingIconView conversationIconView;
    private View conversationTitleView;
    private View expandBtn;
    private final int minHeightWithActions;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationCallTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        context.getClass();
        view.getClass();
        expandableNotificationRow.getClass();
        this.minHeightWithActions = NotificationUtils.getFontScaledHeight(context, R$dimen.notification_max_height);
        this.callLayout = (CallLayout) view;
    }

    private final void resolveViews() {
        CallLayout callLayout = this.callLayout;
        this.conversationIconContainer = callLayout.requireViewById(R.id.cross_task_transition);
        this.conversationIconView = callLayout.requireViewById(R.id.conversation_icon_container);
        this.conversationBadgeBg = callLayout.requireViewById(R.id.conversation_text);
        this.expandBtn = callLayout.requireViewById(R.id.expand_button_number);
        this.appName = callLayout.requireViewById(R.id.app_ops);
        this.conversationTitleView = callLayout.requireViewById(R.id.crosshair);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public int getMinLayoutHeight() {
        return this.minHeightWithActions;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        expandableNotificationRow.getClass();
        resolveViews();
        super.onContentUpdated(expandableNotificationRow);
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

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    protected void updateTransformedTypes() {
        super.updateTransformedTypes();
        View[] viewArr = new View[2];
        View view = this.appName;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appName");
            view = null;
        }
        viewArr[0] = view;
        View view3 = this.conversationTitleView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("conversationTitleView");
            view3 = null;
        }
        viewArr[1] = view3;
        addTransformedViews(viewArr);
        View[] viewArr2 = new View[3];
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
        } else {
            view2 = view5;
        }
        viewArr2[2] = view2;
        addViewsTransformingToSimilar(viewArr2);
    }
}
