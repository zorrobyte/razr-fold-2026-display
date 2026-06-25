package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import com.android.systemui.Flags;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NotifUiAdjustment.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifUiAdjustment {
    public static final Companion Companion = new Companion(null);
    private final boolean isChildInGroup;
    private final boolean isConversation;
    private final boolean isGroupSummary;
    private final boolean isMinimized;
    private final boolean isSnoozeEnabled;
    private final String key;
    private final boolean needsRedaction;
    private final List smartActions;
    private final List smartReplies;

    /* JADX INFO: compiled from: NotifUiAdjustment.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean areDifferent(Icon icon, Icon icon2) {
            if (icon == icon2) {
                return false;
            }
            return icon == null || icon2 == null || !icon.sameAs(icon2);
        }

        private final boolean areDifferent(List list, List list2) {
            if (list == list2) {
                return false;
            }
            if (list.size() != list2.size()) {
                return true;
            }
            for (Pair pair : SequencesKt.zip(CollectionsKt.asSequence(list), CollectionsKt.asSequence(list2))) {
                if (TextUtils.equals(((Notification.Action) pair.getFirst()).title, ((Notification.Action) pair.getSecond()).title)) {
                    Companion companion = NotifUiAdjustment.Companion;
                    if (companion.areDifferent(((Notification.Action) pair.getFirst()).getIcon(), ((Notification.Action) pair.getSecond()).getIcon()) || !Intrinsics.areEqual(((Notification.Action) pair.getFirst()).actionIntent, ((Notification.Action) pair.getSecond()).actionIntent) || companion.areDifferent(((Notification.Action) pair.getFirst()).getRemoteInputs(), ((Notification.Action) pair.getSecond()).getRemoteInputs())) {
                    }
                }
                return true;
            }
            return false;
        }

        private final boolean areDifferent(RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2) {
            if (remoteInputArr == remoteInputArr2) {
                return false;
            }
            if (remoteInputArr == null || remoteInputArr2 == null || remoteInputArr.length != remoteInputArr2.length) {
                return true;
            }
            for (Pair pair : SequencesKt.zip(ArraysKt.asSequence(remoteInputArr), ArraysKt.asSequence(remoteInputArr2))) {
                if (!TextUtils.equals(((RemoteInput) pair.getFirst()).getLabel(), ((RemoteInput) pair.getSecond()).getLabel()) || NotifUiAdjustment.Companion.areDifferent(((RemoteInput) pair.getFirst()).getChoices(), ((RemoteInput) pair.getSecond()).getChoices())) {
                    return true;
                }
            }
            return false;
        }

        private final boolean areDifferent(CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
            if (charSequenceArr == charSequenceArr2) {
                return false;
            }
            if (charSequenceArr == null || charSequenceArr2 == null || charSequenceArr.length != charSequenceArr2.length) {
                return true;
            }
            for (Pair pair : SequencesKt.zip(ArraysKt.asSequence(charSequenceArr), ArraysKt.asSequence(charSequenceArr2))) {
                if (!TextUtils.equals((CharSequence) pair.getFirst(), (CharSequence) pair.getSecond())) {
                    return true;
                }
            }
            return false;
        }

        public final boolean needReinflate(NotifUiAdjustment notifUiAdjustment, NotifUiAdjustment notifUiAdjustment2) {
            notifUiAdjustment.getClass();
            notifUiAdjustment2.getClass();
            if (notifUiAdjustment == notifUiAdjustment2) {
                return false;
            }
            if (notifUiAdjustment.isConversation() != notifUiAdjustment2.isConversation() || notifUiAdjustment.isSnoozeEnabled() != notifUiAdjustment2.isSnoozeEnabled() || notifUiAdjustment.isMinimized() != notifUiAdjustment2.isMinimized() || notifUiAdjustment.getNeedsRedaction() != notifUiAdjustment2.getNeedsRedaction() || areDifferent(notifUiAdjustment.getSmartActions(), notifUiAdjustment2.getSmartActions()) || !Intrinsics.areEqual(notifUiAdjustment2.getSmartReplies(), notifUiAdjustment.getSmartReplies())) {
                return true;
            }
            if (Flags.notificationAsyncHybridViewInflation() && !notifUiAdjustment.isChildInGroup() && notifUiAdjustment2.isChildInGroup()) {
                return true;
            }
            return Flags.notificationAsyncGroupHeaderInflation() && !notifUiAdjustment.isGroupSummary() && notifUiAdjustment2.isGroupSummary();
        }
    }

    public NotifUiAdjustment(String str, List list, List list2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        str.getClass();
        list.getClass();
        list2.getClass();
        this.key = str;
        this.smartActions = list;
        this.smartReplies = list2;
        this.isConversation = z;
        this.isSnoozeEnabled = z2;
        this.isMinimized = z3;
        this.needsRedaction = z4;
        this.isChildInGroup = z5;
        this.isGroupSummary = z6;
    }

    public static final boolean needReinflate(NotifUiAdjustment notifUiAdjustment, NotifUiAdjustment notifUiAdjustment2) {
        return Companion.needReinflate(notifUiAdjustment, notifUiAdjustment2);
    }

    public final boolean getNeedsRedaction() {
        return this.needsRedaction;
    }

    public final List getSmartActions() {
        return this.smartActions;
    }

    public final List getSmartReplies() {
        return this.smartReplies;
    }

    public final boolean isChildInGroup() {
        return this.isChildInGroup;
    }

    public final boolean isConversation() {
        return this.isConversation;
    }

    public final boolean isGroupSummary() {
        return this.isGroupSummary;
    }

    public final boolean isMinimized() {
        return this.isMinimized;
    }

    public final boolean isSnoozeEnabled() {
        return this.isSnoozeEnabled;
    }
}
