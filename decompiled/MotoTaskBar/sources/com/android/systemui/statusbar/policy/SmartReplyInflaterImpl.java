package com.android.systemui.statusbar.policy;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.motorola.taskbar.R$layout;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SmartReplyStateInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SmartReplyInflaterImpl implements SmartReplyInflater {
    private final SmartReplyConstants constants;
    private final Context context;
    private final KeyguardDismissUtil keyguardDismissUtil;
    private final NotificationRemoteInputManager remoteInputManager;
    private final SmartReplyController smartReplyController;

    public SmartReplyInflaterImpl(SmartReplyConstants smartReplyConstants, KeyguardDismissUtil keyguardDismissUtil, NotificationRemoteInputManager notificationRemoteInputManager, SmartReplyController smartReplyController, Context context) {
        smartReplyConstants.getClass();
        keyguardDismissUtil.getClass();
        notificationRemoteInputManager.getClass();
        smartReplyController.getClass();
        context.getClass();
        this.constants = smartReplyConstants;
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.remoteInputManager = notificationRemoteInputManager;
        this.smartReplyController = smartReplyController;
        this.context = context;
    }

    private final Intent createRemoteInputIntent(SmartReplyView.SmartReplies smartReplies, CharSequence charSequence) {
        Bundle bundle = new Bundle();
        bundle.putString(smartReplies.remoteInput.getResultKey(), charSequence.toString());
        Intent intentAddFlags = new Intent().addFlags(268435456);
        intentAddFlags.getClass();
        RemoteInput.addResultsToIntent(new RemoteInput[]{smartReplies.remoteInput}, intentAddFlags, bundle);
        RemoteInput.setResultsSource(intentAddFlags, 1);
        return intentAddFlags;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSmartReplyClick(final NotificationEntry notificationEntry, final SmartReplyView.SmartReplies smartReplies, final int i, final SmartReplyView smartReplyView, final Button button, final CharSequence charSequence) {
        SmartReplyStateInflaterKt.executeWhenUnlocked(this.keyguardDismissUtil, !notificationEntry.isRowPinned(), new Function0() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Boolean.valueOf(SmartReplyInflaterImpl.onSmartReplyClick$lambda$1(this.f$0, smartReplies, button, charSequence, i, notificationEntry, smartReplyView));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSmartReplyClick$lambda$1(SmartReplyInflaterImpl smartReplyInflaterImpl, SmartReplyView.SmartReplies smartReplies, Button button, CharSequence charSequence, int i, NotificationEntry notificationEntry, SmartReplyView smartReplyView) {
        if (smartReplyInflaterImpl.constants.getEffectiveEditChoicesBeforeSending(smartReplies.remoteInput.getEditChoicesBeforeSending())) {
            NotificationRemoteInputManager notificationRemoteInputManager = smartReplyInflaterImpl.remoteInputManager;
            RemoteInput remoteInput = smartReplies.remoteInput;
            notificationRemoteInputManager.activateRemoteInput(button, new RemoteInput[]{remoteInput}, remoteInput, smartReplies.pendingIntent, new NotificationEntry.EditedSuggestionInfo(charSequence, i));
        } else {
            smartReplyInflaterImpl.smartReplyController.smartReplySent(notificationEntry, i, button.getText(), NotificationLogger.getNotificationLocation(notificationEntry).toMetricsEventEnum(), false);
            notificationEntry.setHasSentReply();
            try {
                Intent intentCreateRemoteInputIntent = smartReplyInflaterImpl.createRemoteInputIntent(smartReplies, charSequence);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                smartReplies.pendingIntent.send(smartReplyInflaterImpl.context, 0, intentCreateRemoteInputIntent, null, null, null, activityOptionsMakeBasic.toBundle());
            } catch (PendingIntent.CanceledException e) {
                Log.w("SmartReplyViewInflater", "Unable to send smart reply", e);
            }
            smartReplyView.hideSmartSuggestions();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.SmartReplyInflater
    public Button inflateReplyButton(final SmartReplyView smartReplyView, final NotificationEntry notificationEntry, final SmartReplyView.SmartReplies smartReplies, final int i, final CharSequence charSequence, boolean z) {
        smartReplyView.getClass();
        notificationEntry.getClass();
        smartReplies.getClass();
        charSequence.getClass();
        View viewInflate = LayoutInflater.from(smartReplyView.getContext()).inflate(R$layout.smart_reply_button, (ViewGroup) smartReplyView, false);
        viewInflate.getClass();
        final Button button = (Button) viewInflate;
        button.setText(charSequence);
        View.OnClickListener delayedOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.onSmartReplyClick(notificationEntry, smartReplies, i, smartReplyView, button, charSequence);
            }
        };
        if (z) {
            delayedOnClickListener = new DelayedOnClickListener(delayedOnClickListener, this.constants.getOnClickInitDelay());
        }
        button.setOnClickListener(delayedOnClickListener);
        button.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                view.getClass();
                accessibilityNodeInfo.getClass();
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                String string = smartReplyView.getResources().getString(R$string.accessibility_send_smart_reply);
                string.getClass();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, string));
            }
        });
        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        layoutParams.getClass();
        ((SmartReplyView.LayoutParams) layoutParams).mButtonType = SmartReplyView.SmartButtonType.REPLY;
        return button;
    }
}
