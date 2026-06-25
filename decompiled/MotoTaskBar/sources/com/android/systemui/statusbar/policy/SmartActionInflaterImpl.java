package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.motorola.taskbar.R$layout;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SmartReplyStateInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SmartActionInflaterImpl implements SmartActionInflater {
    private final ActivityStarter activityStarter;
    private final SmartReplyConstants constants;
    private final HeadsUpManager headsUpManager;
    private final SmartReplyController smartReplyController;

    public SmartActionInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityStarter activityStarter, SmartReplyController smartReplyController, HeadsUpManager headsUpManager) {
        smartReplyConstants.getClass();
        activityStarter.getClass();
        smartReplyController.getClass();
        headsUpManager.getClass();
        this.constants = smartReplyConstants;
        this.activityStarter = activityStarter;
        this.smartReplyController = smartReplyController;
        this.headsUpManager = headsUpManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSmartActionClick(final NotificationEntry notificationEntry, final SmartReplyView.SmartActions smartActions, final int i, final Notification.Action action) {
        if (smartActions.fromAssistant && 11 == action.getSemanticAction()) {
            notificationEntry.getRow().doSmartActionClick(((int) notificationEntry.getRow().getX()) / 2, ((int) notificationEntry.getRow().getY()) / 2, 11);
            this.smartReplyController.smartActionClicked(notificationEntry, i, action, smartActions.fromAssistant);
        } else {
            ActivityStarter activityStarter = this.activityStarter;
            PendingIntent pendingIntent = action.actionIntent;
            pendingIntent.getClass();
            SmartReplyStateInflaterKt.startPendingIntentDismissingKeyguard(activityStarter, pendingIntent, notificationEntry.getRow(), new Function0() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return SmartActionInflaterImpl.onSmartActionClick$lambda$1(this.f$0, notificationEntry, i, action, smartActions);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onSmartActionClick$lambda$1(SmartActionInflaterImpl smartActionInflaterImpl, NotificationEntry notificationEntry, int i, Notification.Action action, SmartReplyView.SmartActions smartActions) {
        smartActionInflaterImpl.smartReplyController.smartActionClicked(notificationEntry, i, action, smartActions.fromAssistant);
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.policy.SmartActionInflater
    public Button inflateActionButton(ViewGroup viewGroup, final NotificationEntry notificationEntry, final SmartReplyView.SmartActions smartActions, final int i, final Notification.Action action, boolean z, Context context) {
        viewGroup.getClass();
        notificationEntry.getClass();
        smartActions.getClass();
        action.getClass();
        context.getClass();
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.smart_action_button, viewGroup, false);
        viewInflate.getClass();
        Button button = (Button) viewInflate;
        button.setText(action.title);
        int dimensionPixelSize = button.getContext().getResources().getDimensionPixelSize(R$dimen.smart_action_button_icon_size);
        Icon icon = action.getIcon();
        icon.getClass();
        Drawable drawableLoadIconDrawableWithTimeout = SmartReplyStateInflaterKt.loadIconDrawableWithTimeout(icon, context, dimensionPixelSize);
        if (drawableLoadIconDrawableWithTimeout == null) {
            drawableLoadIconDrawableWithTimeout = new GradientDrawable();
        }
        drawableLoadIconDrawableWithTimeout.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        button.setCompoundDrawablesRelative(drawableLoadIconDrawableWithTimeout, null, null, null);
        View.OnClickListener delayedOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.onSmartActionClick(notificationEntry, smartActions, i, action);
            }
        };
        if (z) {
            delayedOnClickListener = new DelayedOnClickListener(delayedOnClickListener, this.constants.getOnClickInitDelay());
        }
        button.setOnClickListener(delayedOnClickListener);
        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        layoutParams.getClass();
        ((SmartReplyView.LayoutParams) layoutParams).mButtonType = SmartReplyView.SmartButtonType.ACTION;
        return button;
    }
}
