package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: SmartReplyStateInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SmartReplyStateInflaterImpl implements SmartReplyStateInflater {
    private final ActivityManagerWrapper activityManagerWrapper;
    private final SmartReplyConstants constants;
    private final DevicePolicyManagerWrapper devicePolicyManagerWrapper;
    private final PackageManagerWrapper packageManagerWrapper;
    private final SmartActionInflater smartActionsInflater;
    private final SmartReplyInflater smartRepliesInflater;

    public SmartReplyStateInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper, PackageManagerWrapper packageManagerWrapper, DevicePolicyManagerWrapper devicePolicyManagerWrapper, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        smartReplyConstants.getClass();
        activityManagerWrapper.getClass();
        packageManagerWrapper.getClass();
        devicePolicyManagerWrapper.getClass();
        smartReplyInflater.getClass();
        smartActionInflater.getClass();
        this.constants = smartReplyConstants;
        this.activityManagerWrapper = activityManagerWrapper;
        this.packageManagerWrapper = packageManagerWrapper;
        this.devicePolicyManagerWrapper = devicePolicyManagerWrapper;
        this.smartRepliesInflater = smartReplyInflater;
        this.smartActionsInflater = smartActionInflater;
    }

    private final List filterAllowlistedLockTaskApps(List list) {
        Intent intent;
        ResolveInfo resolveInfoResolveActivity;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            PendingIntent pendingIntent = ((Notification.Action) obj).actionIntent;
            boolean zIsLockTaskPermitted = false;
            if (pendingIntent != null && (intent = pendingIntent.getIntent()) != null && (resolveInfoResolveActivity = this.packageManagerWrapper.resolveActivity(intent, 0)) != null) {
                zIsLockTaskPermitted = this.devicePolicyManagerWrapper.isLockTaskPermitted(resolveInfoResolveActivity.activityInfo.packageName);
            }
            if (zIsLockTaskPermitted) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Button inflateSmartReplyViewHolder$lambda$1$lambda$0(SmartReplyStateInflaterImpl smartReplyStateInflaterImpl, SmartReplyView smartReplyView, NotificationEntry notificationEntry, SmartReplyView.SmartReplies smartReplies, boolean z, int i, CharSequence charSequence) {
        SmartReplyInflater smartReplyInflater = smartReplyStateInflaterImpl.smartRepliesInflater;
        smartReplyView.getClass();
        charSequence.getClass();
        return smartReplyInflater.inflateReplyButton(smartReplyView, notificationEntry, smartReplies, i, charSequence, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean inflateSmartReplyViewHolder$lambda$4$lambda$2(Notification.Action action) {
        return action.actionIntent != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Button inflateSmartReplyViewHolder$lambda$4$lambda$3(SmartReplyStateInflaterImpl smartReplyStateInflaterImpl, SmartReplyView smartReplyView, NotificationEntry notificationEntry, SmartReplyView.SmartActions smartActions, boolean z, ContextThemeWrapper contextThemeWrapper, int i, Notification.Action action) {
        SmartActionInflater smartActionInflater = smartReplyStateInflaterImpl.smartActionsInflater;
        smartReplyView.getClass();
        action.getClass();
        return smartActionInflater.inflateActionButton(smartReplyView, notificationEntry, smartActions, i, action, z, contextThemeWrapper);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyState chooseSmartRepliesAndActions(com.android.systemui.statusbar.notification.collection.NotificationEntry r12) {
        /*
            Method dump skipped, instruction units count: 362
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.chooseSmartRepliesAndActions(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.policy.InflatedSmartReplyState");
    }

    @Override // com.android.systemui.statusbar.policy.SmartReplyStateInflater
    public InflatedSmartReplyState inflateSmartReplyState(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return chooseSmartRepliesAndActions(notificationEntry);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    @Override // com.android.systemui.statusbar.policy.SmartReplyStateInflater
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(android.content.Context r8, android.content.Context r9, com.android.systemui.statusbar.notification.collection.NotificationEntry r10, com.android.systemui.statusbar.policy.InflatedSmartReplyState r11, com.android.systemui.statusbar.policy.InflatedSmartReplyState r12) {
        /*
            r7 = this;
            r8.getClass()
            r9.getClass()
            r10.getClass()
            r12.getClass()
            boolean r0 = com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt.shouldShowSmartReplyView(r10, r12)
            if (r0 != 0) goto L19
            com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder r7 = new com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder
            r8 = 0
            r7.<init>(r8, r8)
            return r7
        L19:
            boolean r11 = com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt.areSuggestionsSimilar(r11, r12)
            r5 = r11 ^ 1
            com.android.systemui.statusbar.policy.SmartReplyConstants r11 = r7.constants
            com.android.systemui.statusbar.policy.SmartReplyView r2 = com.android.systemui.statusbar.policy.SmartReplyView.inflate(r8, r11)
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r4 = r12.getSmartReplies()
            if (r4 == 0) goto L2e
            boolean r11 = r4.fromAssistant
            goto L2f
        L2e:
            r11 = 0
        L2f:
            r2.setSmartRepliesGeneratedByAssistant(r11)
            if (r4 == 0) goto L4b
            java.util.List r11 = r4.choices
            r11.getClass()
            kotlin.sequences.Sequence r11 = kotlin.collections.CollectionsKt.asSequence(r11)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda0 r0 = new com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda0
            r1 = r7
            r3 = r10
            r0.<init>()
            kotlin.sequences.Sequence r7 = kotlin.sequences.SequencesKt.mapIndexed(r11, r0)
            if (r7 != 0) goto L51
            goto L4d
        L4b:
            r1 = r7
            r3 = r10
        L4d:
            kotlin.sequences.Sequence r7 = kotlin.sequences.SequencesKt.emptySequence()
        L51:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r4 = r12.getSmartActions()
            if (r4 == 0) goto L7d
            android.view.ContextThemeWrapper r6 = new android.view.ContextThemeWrapper
            android.content.res.Resources$Theme r8 = r8.getTheme()
            r6.<init>(r9, r8)
            java.util.List r8 = r4.actions
            r8.getClass()
            kotlin.sequences.Sequence r8 = kotlin.collections.CollectionsKt.asSequence(r8)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda1 r9 = new com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda1
            r9.<init>()
            kotlin.sequences.Sequence r8 = kotlin.sequences.SequencesKt.filter(r8, r9)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda2 r0 = new com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda2
            r0.<init>()
            kotlin.sequences.Sequence r8 = kotlin.sequences.SequencesKt.mapIndexed(r8, r0)
            if (r8 != 0) goto L81
        L7d:
            kotlin.sequences.Sequence r8 = kotlin.sequences.SequencesKt.emptySequence()
        L81:
            com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder r9 = new com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder
            kotlin.sequences.Sequence r7 = kotlin.sequences.SequencesKt.plus(r7, r8)
            java.util.List r7 = kotlin.sequences.SequencesKt.toList(r7)
            r9.<init>(r2, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyViewHolder(android.content.Context, android.content.Context, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.InflatedSmartReplyState, com.android.systemui.statusbar.policy.InflatedSmartReplyState):com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder");
    }
}
