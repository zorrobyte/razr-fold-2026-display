package com.android.systemui.statusbar.notification.collection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.Person;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.ContentInfo;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationEntry extends ListEntry {
    public EditedSuggestionInfo editedSuggestionInfo;
    private boolean hasSentReply;
    private long initializationTime;
    private boolean interruption;
    private long lastFullScreenIntentLaunchTime;
    public long lastRemoteInputSent;
    private boolean mBlockable;
    private Notification.BubbleMetadata mBubbleMetadata;
    private int mBucket;
    private int mCachedContrastColor;
    private int mCachedContrastColorIsFor;
    int mCancellationReason;
    final List mDismissInterceptors;
    private DismissState mDismissState;
    private boolean mExpandAnimationRunning;
    private boolean mHasEverBeenGroupChild;
    private boolean mHasEverBeenGroupSummary;
    private final MutableStateFlow mHeadsUpStatusBarText;
    private final MutableStateFlow mHeadsUpStatusBarTextPublic;
    private IconPack mIcons;
    private boolean mIsDemoted;
    private boolean mIsDeskHeadsUp;
    private boolean mIsHeadsUpEntry;
    private boolean mIsMarkedForUserTriggeredMovement;
    private final String mKey;
    final List mLifetimeExtenders;
    private final ListenerSet mOnSensitivityChangedListeners;
    private NotificationListenerService.Ranking mRanking;
    public boolean mRemoteEditImeAnimatingAway;
    public boolean mRemoteEditImeVisible;
    private ExpandableNotificationRowController mRowController;
    private InflationTask mRunningTask;
    private StatusBarNotification mSbn;
    private final MutableStateFlow mSensitive;
    public ContentInfo remoteInputAttachment;
    public String remoteInputMimeType;
    public CharSequence remoteInputText;
    public CharSequence remoteInputTextWhenReset;
    public Uri remoteInputUri;
    public List remoteInputs;
    private ExpandableNotificationRow row;
    public int targetSdk;

    public enum DismissState {
        NOT_DISMISSED,
        DISMISSED,
        PARENT_DISMISSED
    }

    public class EditedSuggestionInfo {
        public final int index;
        public final CharSequence originalText;

        public EditedSuggestionInfo(CharSequence charSequence, int i) {
            this.originalText = charSequence;
            this.index = i;
        }
    }

    public interface OnSensitivityChangedListener {
        void onSensitivityChanged(NotificationEntry notificationEntry);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NotificationEntry(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, long j) {
        statusBarNotification.getClass();
        String key = statusBarNotification.getKey();
        key.getClass();
        super(key, j);
        this.mLifetimeExtenders = new ArrayList();
        this.mDismissInterceptors = new ArrayList();
        this.mCancellationReason = -1;
        this.mDismissState = DismissState.NOT_DISMISSED;
        this.mIcons = IconPack.buildEmptyPack(null);
        this.lastFullScreenIntentLaunchTime = -2000L;
        this.remoteInputs = null;
        this.mCachedContrastColor = 1;
        this.mCachedContrastColorIsFor = 1;
        this.mRunningTask = null;
        this.lastRemoteInputSent = -2000L;
        this.mHeadsUpStatusBarText = StateFlowKt.MutableStateFlow(null);
        this.mHeadsUpStatusBarTextPublic = StateFlowKt.MutableStateFlow(null);
        this.initializationTime = -1L;
        this.mSensitive = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this.mOnSensitivityChangedListeners = new ListenerSet();
        this.mBucket = 5;
        this.mIsDemoted = false;
        this.mIsDeskHeadsUp = false;
        ranking.getClass();
        this.mKey = statusBarNotification.getKey();
        setSbn(statusBarNotification);
        setRanking(ranking);
    }

    private static boolean isCategory(String str, Notification notification) {
        return Objects.equals(notification.category, str);
    }

    private static boolean isNotificationBlockedByPolicy(Notification notification) {
        return isCategory("call", notification) || isCategory("msg", notification) || isCategory("alarm", notification) || isCategory("event", notification) || isCategory("reminder", notification);
    }

    private boolean shouldSuppressVisualEffect(int i) {
        return (isExemptFromDndVisualSuppression() || (getSuppressedVisualEffects() & i) == 0) ? false : true;
    }

    private void updateIsBlockable() {
        if (getChannel() == null) {
            this.mBlockable = false;
        } else if (!getChannel().isImportanceLockedByCriticalDeviceFunction() || getChannel().isBlockable()) {
            this.mBlockable = true;
        } else {
            this.mBlockable = false;
        }
    }

    public boolean abortTask() {
        InflationTask inflationTask = this.mRunningTask;
        if (inflationTask == null) {
            return false;
        }
        inflationTask.abort();
        this.mRunningTask = null;
        return true;
    }

    public void addOnSensitivityChangedListener(OnSensitivityChangedListener onSensitivityChangedListener) {
        this.mOnSensitivityChangedListeners.addIfAbsent(onSensitivityChangedListener);
    }

    public boolean canBubble() {
        return this.mRanking.canBubble();
    }

    public void closeRemoteInput() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.closeRemoteInput();
        }
    }

    public void demoteStickyHun() {
        this.mIsDemoted = true;
    }

    public List getAttachedNotifChildren() {
        List attachedChildren;
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow == null || (attachedChildren = expandableNotificationRow.getAttachedChildren()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = attachedChildren.iterator();
        while (it.hasNext()) {
            arrayList.add(((ExpandableNotificationRow) it.next()).getEntry());
        }
        return arrayList;
    }

    public Notification.BubbleMetadata getBubbleMetadata() {
        return this.mBubbleMetadata;
    }

    public int getBucket() {
        return this.mBucket;
    }

    public NotificationChannel getChannel() {
        return this.mRanking.getChannel();
    }

    public int getContrastedColor(Context context, boolean z, int i) {
        int i2;
        int i3 = z ? 0 : this.mSbn.getNotification().color;
        if (this.mCachedContrastColorIsFor == i3 && (i2 = this.mCachedContrastColor) != 1) {
            return i2;
        }
        int iResolveContrastColor = ContrastColorUtil.resolveContrastColor(context, i3, i);
        this.mCachedContrastColorIsFor = i3;
        this.mCachedContrastColor = iResolveContrastColor;
        return iResolveContrastColor;
    }

    public DismissState getDismissState() {
        return this.mDismissState;
    }

    public ExpandableNotificationRow getHeadsUpAnimationView() {
        return this.row;
    }

    public IconPack getIcons() {
        return this.mIcons;
    }

    public int getImportance() {
        return this.mRanking.getImportance();
    }

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public String getKey() {
        return this.mKey;
    }

    public long getLastAudiblyAlertedMs() {
        return this.mRanking.getLastAudiblyAlertedMillis();
    }

    public String getNotificationStyle() {
        if (isSummaryWithChildren()) {
            return "summary";
        }
        Class notificationStyle = getSbn().getNotification().getNotificationStyle();
        return notificationStyle == null ? "nostyle" : notificationStyle.getSimpleName();
    }

    public NotificationListenerService.Ranking getRanking() {
        return this.mRanking;
    }

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public NotificationEntry getRepresentativeEntry() {
        return this;
    }

    public ExpandableNotificationRow getRow() {
        return this.row;
    }

    public ExpandableNotificationRowController getRowController() {
        return this.mRowController;
    }

    public InflationTask getRunningTask() {
        return this.mRunningTask;
    }

    public StatusBarNotification getSbn() {
        return this.mSbn;
    }

    public List getSmartActions() {
        return this.mRanking.getSmartActions();
    }

    public List getSmartReplies() {
        return this.mRanking.getSmartReplies();
    }

    public int getSuppressedVisualEffects() {
        return this.mRanking.getSuppressedVisualEffects();
    }

    public boolean hasEverBeenGroupChild() {
        return this.mHasEverBeenGroupChild;
    }

    public boolean hasEverBeenGroupSummary() {
        return this.mHasEverBeenGroupSummary;
    }

    public boolean hasFinishedInitialization() {
        return this.initializationTime != -1 && SystemClock.elapsedRealtime() > this.initializationTime + 400;
    }

    public boolean hasInterrupted() {
        return this.interruption;
    }

    public boolean hasJustLaunchedFullScreenIntent() {
        return SystemClock.elapsedRealtime() < this.lastFullScreenIntentLaunchTime + 2000;
    }

    public boolean hasJustSentRemoteInput() {
        return SystemClock.elapsedRealtime() < this.lastRemoteInputSent + 500;
    }

    public boolean isAmbient() {
        return this.mRanking.isAmbient();
    }

    public boolean isBlockable() {
        return this.mBlockable;
    }

    public boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public boolean isCanceled() {
        return this.mCancellationReason != -1;
    }

    public boolean isChannelVisibilityPrivate() {
        return getRanking().getChannel() != null && getRanking().getChannel().getLockscreenVisibility() == 0;
    }

    public boolean isClearable() {
        if (!this.mSbn.isClearable()) {
            return false;
        }
        List attachedNotifChildren = getAttachedNotifChildren();
        if (attachedNotifChildren == null || attachedNotifChildren.size() <= 0) {
            return true;
        }
        for (int i = 0; i < attachedNotifChildren.size(); i++) {
            if (!((NotificationEntry) attachedNotifChildren.get(i)).getSbn().isClearable()) {
                return false;
            }
        }
        return true;
    }

    public boolean isDemoted() {
        return this.mIsDemoted;
    }

    public boolean isDeskHeadsUp() {
        return this.mIsDeskHeadsUp;
    }

    public boolean isDismissableForState(boolean z) {
        if (this.mSbn.isNonDismissable()) {
            return false;
        }
        return (this.mSbn.isOngoing() && z) ? false : true;
    }

    boolean isExemptFromDndVisualSuppression() {
        if (isNotificationBlockedByPolicy(this.mSbn.getNotification())) {
            return false;
        }
        return this.mSbn.getNotification().isFgsOrUij() || this.mSbn.getNotification().isMediaNotification() || !isBlockable();
    }

    public boolean isExpandAnimationRunning() {
        return this.mExpandAnimationRunning;
    }

    public boolean isLastMessageFromReply() {
        Notification.MessagingStyle.Message message;
        if (!this.hasSentReply) {
            return false;
        }
        Bundle bundle = this.mSbn.getNotification().extras;
        if (!ArrayUtils.isEmpty(bundle.getParcelableArray("android.remoteInputHistoryItems"))) {
            return true;
        }
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages"));
        if (messagesFromBundleArray == null || messagesFromBundleArray.isEmpty() || (message = messagesFromBundleArray.get(messagesFromBundleArray.size() - 1)) == null) {
            return false;
        }
        Person senderPerson = message.getSenderPerson();
        if (senderPerson == null) {
            return true;
        }
        return Objects.equals((Person) bundle.getParcelable("android.messagingUser", Person.class), senderPerson);
    }

    public boolean isMarkedForUserTriggeredMovement() {
        return this.mIsMarkedForUserTriggeredMovement;
    }

    public boolean isNotificationVisibilityPrivate() {
        return getSbn().getNotification().visibility == 0;
    }

    public boolean isPinnedAndExpanded() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isPinnedAndExpanded();
    }

    public boolean isRowDismissed() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isDismissed();
    }

    public boolean isRowPinned() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isPinned();
    }

    public boolean isRowRemoved() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isRemoved();
    }

    public StateFlow isSensitive() {
        return this.mSensitive;
    }

    public boolean isStickyAndNotDemoted() {
        boolean z = (getSbn().getNotification().flags & 16384) != 0;
        if (!z && !this.mIsDemoted) {
            demoteStickyHun();
        }
        return z && !this.mIsDemoted;
    }

    public boolean isSummaryWithChildren() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.isSummaryWithChildren();
    }

    public void markAsGroupChild() {
        this.mHasEverBeenGroupChild = true;
    }

    public void markAsGroupSummary() {
        this.mHasEverBeenGroupSummary = true;
    }

    public void markForUserTriggeredMovement(boolean z) {
        this.mIsMarkedForUserTriggeredMovement = z;
    }

    public void notifyHeightChanged(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.notifyHeightChanged(z);
        }
    }

    public void onDensityOrFontScaleChanged() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.onDensityOrFontScaleChanged();
        }
    }

    public void onInflationTaskFinished() {
        this.mRunningTask = null;
    }

    public void onRemoteInputInserted() {
        this.lastRemoteInputSent = -2000L;
        this.remoteInputTextWhenReset = null;
    }

    public void removeOnSensitivityChangedListener(OnSensitivityChangedListener onSensitivityChangedListener) {
        this.mOnSensitivityChangedListeners.remove(onSensitivityChangedListener);
    }

    public void removeRow() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.setRemoved();
        }
    }

    public void resetInitializationTime() {
        this.initializationTime = -1L;
    }

    public void resetUserExpansion() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.resetUserExpansion();
        }
    }

    public boolean rowExists() {
        return this.row != null;
    }

    public void sendAccessibilityEvent(int i) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.sendAccessibilityEvent(i);
        }
    }

    public void setBucket(int i) {
        this.mBucket = i;
    }

    public void setDeskHeadsUp(boolean z) {
        this.mIsDeskHeadsUp = z;
    }

    void setDismissState(DismissState dismissState) {
        dismissState.getClass();
        this.mDismissState = dismissState;
    }

    public void setHasSentReply() {
        this.hasSentReply = true;
    }

    public void setHeadsUp(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.setHeadsUp(z);
        }
    }

    public void setHeadsUpStatusBarText(CharSequence charSequence) {
        this.mHeadsUpStatusBarText.setValue(charSequence);
    }

    public void setHeadsUpStatusBarTextPublic(CharSequence charSequence) {
        this.mHeadsUpStatusBarTextPublic.setValue(charSequence);
    }

    public void setIcons(IconPack iconPack) {
        this.mIcons = iconPack;
    }

    public void setInflationTask(InflationTask inflationTask) {
        abortTask();
        this.mRunningTask = inflationTask;
    }

    public void setInitializationTime(long j) {
        if (this.initializationTime == -1) {
            this.initializationTime = j;
        }
    }

    public void setInterruption() {
        this.interruption = true;
    }

    public void setIsHeadsUpEntry(boolean z) {
        this.mIsHeadsUpEntry = z;
    }

    public void setRanking(NotificationListenerService.Ranking ranking) {
        ranking.getClass();
        ranking.getKey().getClass();
        if (ranking.getKey().equals(this.mKey)) {
            this.mRanking = ranking.withAudiblyAlertedInfo(this.mRanking);
            updateIsBlockable();
            return;
        }
        throw new IllegalArgumentException("New key " + ranking.getKey() + " doesn't match existing key " + this.mKey);
    }

    public void setRow(ExpandableNotificationRow expandableNotificationRow) {
        this.row = expandableNotificationRow;
    }

    public void setRowController(ExpandableNotificationRowController expandableNotificationRowController) {
        this.mRowController = expandableNotificationRowController;
    }

    public void setRowPinned(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.setPinned(z);
        }
    }

    public void setSbn(StatusBarNotification statusBarNotification) {
        statusBarNotification.getClass();
        statusBarNotification.getKey().getClass();
        if (statusBarNotification.getKey().equals(this.mKey)) {
            this.mSbn = statusBarNotification;
            this.mBubbleMetadata = statusBarNotification.getNotification().getBubbleMetadata();
            return;
        }
        throw new IllegalArgumentException("New key " + statusBarNotification.getKey() + " doesn't match existing key " + this.mKey);
    }

    public void setSensitive(boolean z, boolean z2) {
        getRow().setSensitive(z, z2);
        if (z != ((Boolean) this.mSensitive.getValue()).booleanValue()) {
            this.mSensitive.setValue(Boolean.valueOf(z));
            Iterator it = this.mOnSensitivityChangedListeners.iterator();
            while (it.hasNext()) {
                ((OnSensitivityChangedListener) it.next()).onSensitivityChanged(this);
            }
        }
    }

    public boolean shouldSuppressAmbient() {
        return shouldSuppressVisualEffect(128);
    }

    public boolean shouldSuppressFullScreenIntent() {
        return shouldSuppressVisualEffect(4);
    }

    public boolean shouldSuppressNotificationList() {
        return shouldSuppressVisualEffect(256);
    }

    public boolean shouldSuppressPeek() {
        return shouldSuppressVisualEffect(16);
    }

    public boolean shouldSuppressStatusBar() {
        return shouldSuppressVisualEffect(32);
    }

    public boolean showingPulsing() {
        ExpandableNotificationRow expandableNotificationRow = this.row;
        return expandableNotificationRow != null && expandableNotificationRow.showingPulsing();
    }
}
