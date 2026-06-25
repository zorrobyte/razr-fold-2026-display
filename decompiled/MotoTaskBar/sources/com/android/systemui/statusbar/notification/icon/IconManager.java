package com.android.systemui.statusbar.notification.icon;

import android.app.Notification;
import android.app.Person;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.widget.ImageView;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Flags;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: IconManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IconManager implements ConversationIconManager {
    private final CoroutineScope applicationCoroutineScope;
    private final CoroutineContext bgCoroutineContext;
    private final IconManager$entryListener$1 entryListener;
    private final IconBuilder iconBuilder;
    private final LauncherApps launcherApps;
    private ConcurrentHashMap launcherPeopleAvatarIconJobs;
    private final CoroutineContext mainCoroutineContext;
    private final CommonNotifCollection notifCollection;
    private final NotificationEntry.OnSensitivityChangedListener sensitivityListener;
    private Set unimportantConversationKeys;

    /* JADX INFO: compiled from: IconManager.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StatusBarIcon.Type.values().length];
            try {
                iArr[StatusBarIcon.Type.PeopleAvatar.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.icon.IconManager$createPeopleAvatar$1, reason: invalid class name */
    /* JADX INFO: compiled from: IconManager.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationEntry $entry;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(NotificationEntry notificationEntry, Continuation continuation) {
            super(2, continuation);
            this.$entry = notificationEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IconManager.this.new AnonymousClass1(this.$entry, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                IconManager iconManager = IconManager.this;
                NotificationEntry notificationEntry = this.$entry;
                this.label = 1;
                if (iconManager.getLauncherShortcutIconForPeopleAvatar(notificationEntry, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.icon.IconManager$getLauncherShortcutIconForPeopleAvatar$2, reason: invalid class name */
    /* JADX INFO: compiled from: IconManager.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationEntry $entry;
        int label;
        final /* synthetic */ IconManager this$0;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.icon.IconManager$getLauncherShortcutIconForPeopleAvatar$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: IconManager.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationEntry $entry;
            final /* synthetic */ Ref$ObjectRef $icon;
            int label;
            final /* synthetic */ IconManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(IconManager iconManager, Ref$ObjectRef ref$ObjectRef, NotificationEntry notificationEntry, Continuation continuation) {
                super(2, continuation);
                this.this$0 = iconManager;
                this.$icon = ref$ObjectRef;
                this.$entry = notificationEntry;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$icon, this.$entry, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$entry.getIcons().setPeopleAvatarDescriptor(this.this$0.toStatusBarIcon((Icon) this.$icon.element, this.$entry, StatusBarIcon.Type.PeopleAvatar));
                return this.this$0.updateIcons(this.$entry, true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(NotificationEntry notificationEntry, IconManager iconManager, Continuation continuation) {
            super(2, continuation);
            this.$entry = notificationEntry;
            this.this$0 = iconManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$entry, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ShortcutInfo conversationShortcutInfo = this.$entry.getRanking().getConversationShortcutInfo();
                if (conversationShortcutInfo != null) {
                    try {
                        ref$ObjectRef.element = this.this$0.launcherApps.getShortcutIcon(conversationShortcutInfo);
                    } catch (Exception e) {
                        Boxing.boxInt(Log.e("IconManager", "Error calling LauncherApps#getShortcutIcon for notification " + this.$entry + ": " + e));
                    }
                }
                if (ref$ObjectRef.element != null) {
                    CoroutineContext coroutineContext = this.this$0.mainCoroutineContext;
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, ref$ObjectRef, this.$entry, null);
                    this.label = 1;
                    if (BuildersKt.withContext(coroutineContext, anonymousClass1, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.icon.IconManager$entryListener$1] */
    public IconManager(CommonNotifCollection commonNotifCollection, LauncherApps launcherApps, IconBuilder iconBuilder, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
        commonNotifCollection.getClass();
        launcherApps.getClass();
        iconBuilder.getClass();
        coroutineScope.getClass();
        coroutineContext.getClass();
        coroutineContext2.getClass();
        this.notifCollection = commonNotifCollection;
        this.launcherApps = launcherApps;
        this.iconBuilder = iconBuilder;
        this.applicationCoroutineScope = coroutineScope;
        this.bgCoroutineContext = coroutineContext;
        this.mainCoroutineContext = coroutineContext2;
        this.unimportantConversationKeys = SetsKt.emptySet();
        this.launcherPeopleAvatarIconJobs = new ConcurrentHashMap();
        this.entryListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$entryListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                notificationEntry.removeOnSensitivityChangedListener(this.this$0.sensitivityListener);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryInit(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                notificationEntry.addOnSensitivityChangedListener(this.this$0.sensitivityListener);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onRankingApplied() {
                this.this$0.recalculateForImportantConversationChange();
            }
        };
        this.sensitivityListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$sensitivityListener$1
            @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
            public final void onSensitivityChanged(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                this.this$0.updateIconsSafe(notificationEntry);
            }
        };
    }

    private final void cacheIconDescriptor(NotificationEntry notificationEntry, StatusBarIcon statusBarIcon) {
        if (Flags.notificationsUseAppIcon() || Flags.notificationsUseMonochromeAppIcon()) {
            StatusBarIcon.Type type = statusBarIcon.type;
            if ((type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) == 1) {
                notificationEntry.getIcons().setPeopleAvatarDescriptor(statusBarIcon);
                return;
            } else {
                notificationEntry.getIcons().setSmallIconDescriptor(statusBarIcon);
                return;
            }
        }
        if (isImportantConversation(notificationEntry)) {
            if (statusBarIcon.type == StatusBarIcon.Type.PeopleAvatar) {
                notificationEntry.getIcons().setPeopleAvatarDescriptor(statusBarIcon);
            } else {
                notificationEntry.getIcons().setSmallIconDescriptor(statusBarIcon);
            }
        }
    }

    private final Icon createPeopleAvatar(final NotificationEntry notificationEntry) throws InflationException {
        Icon shortcutIcon = null;
        if (Flags.notificationsBackgroundIcons()) {
            Job job = (Job) this.launcherPeopleAvatarIconJobs.get(notificationEntry.getKey());
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            ConcurrentHashMap concurrentHashMap = this.launcherPeopleAvatarIconJobs;
            String key = notificationEntry.getKey();
            Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(this.applicationCoroutineScope, null, null, new AnonymousClass1(notificationEntry, null), 3, null);
            jobLaunch$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.statusbar.notification.icon.IconManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return IconManager.createPeopleAvatar$lambda$8$lambda$7(this.f$0, notificationEntry, (Throwable) obj);
                }
            });
            concurrentHashMap.put(key, jobLaunch$default);
        } else {
            ShortcutInfo conversationShortcutInfo = notificationEntry.getRanking().getConversationShortcutInfo();
            if (conversationShortcutInfo != null) {
                shortcutIcon = this.launcherApps.getShortcutIcon(conversationShortcutInfo);
            }
        }
        if (shortcutIcon == null) {
            Bundle bundle = notificationEntry.getSbn().getNotification().extras;
            bundle.getClass();
            List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray("android.messages"));
            messagesFromBundleArray.getClass();
            Person person = (Person) bundle.getParcelable("android.messagingUser");
            int size = messagesFromBundleArray.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    Notification.MessagingStyle.Message message = messagesFromBundleArray.get(size);
                    Person senderPerson = message.getSenderPerson();
                    if (senderPerson != null && senderPerson != person) {
                        Person senderPerson2 = message.getSenderPerson();
                        senderPerson2.getClass();
                        shortcutIcon = senderPerson2.getIcon();
                        break;
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
        }
        if (shortcutIcon == null) {
            shortcutIcon = notificationEntry.getSbn().getNotification().getLargeIcon();
        }
        if (shortcutIcon == null) {
            shortcutIcon = notificationEntry.getSbn().getNotification().getSmallIcon();
        }
        if (shortcutIcon != null) {
            return shortcutIcon;
        }
        throw new InflationException("No icon in notification from " + notificationEntry.getSbn().getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit createPeopleAvatar$lambda$8$lambda$7(IconManager iconManager, NotificationEntry notificationEntry, Throwable th) {
        iconManager.launcherPeopleAvatarIconJobs.remove(notificationEntry.getKey());
        return Unit.INSTANCE;
    }

    private final StatusBarIcon getCachedIconDescriptor(NotificationEntry notificationEntry, boolean z) {
        StatusBarIcon peopleAvatarDescriptor = notificationEntry.getIcons().getPeopleAvatarDescriptor();
        StatusBarIcon smallIconDescriptor = notificationEntry.getIcons().getSmallIconDescriptor();
        if (z && peopleAvatarDescriptor != null) {
            return peopleAvatarDescriptor;
        }
        if (smallIconDescriptor == null) {
            return null;
        }
        return smallIconDescriptor;
    }

    private final StatusBarIcon getIconDescriptor(NotificationEntry notificationEntry, boolean z) throws InflationException {
        boolean z2 = !z && isImportantConversation(notificationEntry);
        StatusBarIcon cachedIconDescriptor = getCachedIconDescriptor(notificationEntry, z2);
        if (cachedIconDescriptor != null) {
            return cachedIconDescriptor;
        }
        Pair pair = z2 ? TuplesKt.to(createPeopleAvatar(notificationEntry), StatusBarIcon.Type.PeopleAvatar) : TuplesKt.to(notificationEntry.getSbn().getNotification().getSmallIcon(), StatusBarIcon.Type.NotifSmallIcon);
        Icon icon = (Icon) pair.component1();
        StatusBarIcon.Type type = (StatusBarIcon.Type) pair.component2();
        if (icon != null) {
            StatusBarIcon statusBarIcon = toStatusBarIcon(icon, notificationEntry, type);
            cacheIconDescriptor(notificationEntry, statusBarIcon);
            return statusBarIcon;
        }
        throw new InflationException("No icon in notification from " + notificationEntry.getSbn().getPackageName());
    }

    private final Pair getIconDescriptors(NotificationEntry notificationEntry) throws InflationException {
        StatusBarIcon iconDescriptor = getIconDescriptor(notificationEntry, false);
        return new Pair(iconDescriptor, ((Boolean) notificationEntry.isSensitive().getValue()).booleanValue() ? getIconDescriptor(notificationEntry, true) : iconDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getLauncherShortcutIconForPeopleAvatar(NotificationEntry notificationEntry, Continuation continuation) {
        Object objWithContext = BuildersKt.withContext(this.bgCoroutineContext, new AnonymousClass2(notificationEntry, this, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    private final boolean isImportantConversation(NotificationEntry notificationEntry) {
        return notificationEntry.getRanking().getChannel() != null && notificationEntry.getRanking().getChannel().isImportantConversation() && notificationEntry.getSbn().getNotification().isStyle(Notification.MessagingStyle.class) && !this.unimportantConversationKeys.contains(notificationEntry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recalculateForImportantConversationChange() {
        for (NotificationEntry notificationEntry : this.notifCollection.getAllNotifs()) {
            notificationEntry.getClass();
            boolean zIsImportantConversation = isImportantConversation(notificationEntry);
            if (notificationEntry.getIcons().getAreIconsAvailable() && zIsImportantConversation != notificationEntry.getIcons().isImportantConversation()) {
                updateIconsSafe(notificationEntry);
            }
            notificationEntry.getIcons().setImportantConversation(zIsImportantConversation);
        }
    }

    private final void setIcon(NotificationEntry notificationEntry, StatusBarIcon statusBarIcon, StatusBarIconView statusBarIconView) throws InflationException {
        statusBarIconView.setShowsConversation(showsConversation(notificationEntry, statusBarIconView, statusBarIcon));
        statusBarIconView.setTag(R$id.icon_is_pre_L, Boolean.valueOf(notificationEntry.targetSdk < 21));
        if (statusBarIconView.set(statusBarIcon)) {
            return;
        }
        throw new InflationException("Couldn't create icon " + statusBarIcon);
    }

    private final boolean showsConversation(NotificationEntry notificationEntry, StatusBarIconView statusBarIconView, StatusBarIcon statusBarIcon) {
        return (!isImportantConversation(notificationEntry) || statusBarIcon.icon.equals(notificationEntry.getSbn().getNotification().getSmallIcon()) || ((statusBarIconView == notificationEntry.getIcons().getShelfIcon() || statusBarIconView == notificationEntry.getIcons().getAodIcon()) && ((Boolean) notificationEntry.isSensitive().getValue()).booleanValue())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final StatusBarIcon toStatusBarIcon(Icon icon, NotificationEntry notificationEntry, StatusBarIcon.Type type) {
        Notification notification = notificationEntry.getSbn().getNotification();
        return new StatusBarIcon(notificationEntry.getSbn().getUser(), notificationEntry.getSbn().getPackageName(), icon, notification.iconLevel, notification.number, this.iconBuilder.getIconContentDescription(notification), type);
    }

    public static /* synthetic */ Unit updateIcons$default(IconManager iconManager, NotificationEntry notificationEntry, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return iconManager.updateIcons(notificationEntry, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateIconsSafe(NotificationEntry notificationEntry) {
        try {
            updateIcons$default(this, notificationEntry, false, 2, null);
        } catch (InflationException e) {
            Log.e("IconManager", "Unable to update icon", e);
        }
    }

    public final void attach() {
        this.notifCollection.addCollectionListener(this.entryListener);
    }

    public final void createIcons(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("IconManager.createIcons");
        }
        try {
            StatusBarIconView statusBarIconViewCreateIconView = this.iconBuilder.createIconView(notificationEntry);
            ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;
            statusBarIconViewCreateIconView.setScaleType(scaleType);
            StatusBarIconView statusBarIconViewCreateIconView2 = this.iconBuilder.createIconView(notificationEntry);
            statusBarIconViewCreateIconView2.setScaleType(scaleType);
            statusBarIconViewCreateIconView2.setVisibility(4);
            StatusBarIconView statusBarIconViewCreateIconView3 = this.iconBuilder.createIconView(notificationEntry);
            statusBarIconViewCreateIconView3.setScaleType(scaleType);
            statusBarIconViewCreateIconView3.setIncreasedSize(true);
            Pair iconDescriptors = getIconDescriptors(notificationEntry);
            StatusBarIcon statusBarIcon = (StatusBarIcon) iconDescriptors.component1();
            StatusBarIcon statusBarIcon2 = (StatusBarIcon) iconDescriptors.component2();
            try {
                setIcon(notificationEntry, statusBarIcon, statusBarIconViewCreateIconView);
                setIcon(notificationEntry, statusBarIcon2, statusBarIconViewCreateIconView2);
                setIcon(notificationEntry, statusBarIcon2, statusBarIconViewCreateIconView3);
                notificationEntry.setIcons(IconPack.buildPack(statusBarIconViewCreateIconView, statusBarIconViewCreateIconView2, statusBarIconViewCreateIconView3, notificationEntry.getIcons()));
                Unit unit = Unit.INSTANCE;
            } catch (InflationException e) {
                notificationEntry.setIcons(IconPack.buildEmptyPack(notificationEntry.getIcons()));
                throw e;
            }
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.icon.ConversationIconManager
    public void setUnimportantConversations(Collection collection) {
        collection.getClass();
        Set set = CollectionsKt.toSet(collection);
        boolean zAreEqual = Intrinsics.areEqual(this.unimportantConversationKeys, set);
        this.unimportantConversationKeys = set;
        if (zAreEqual) {
            return;
        }
        recalculateForImportantConversationChange();
    }

    public final Unit updateIcons(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("IconManager.updateIcons");
        }
        try {
            if (notificationEntry.getIcons().getAreIconsAvailable()) {
                if (z && !Flags.notificationsBackgroundIcons()) {
                    Log.wtf("IconManager", "Updating using the cache is not supported when the notifications_background_icons flag is off");
                }
                if (!z || !Flags.notificationsBackgroundIcons()) {
                    notificationEntry.getIcons().setSmallIconDescriptor(null);
                    notificationEntry.getIcons().setPeopleAvatarDescriptor(null);
                }
                Pair iconDescriptors = getIconDescriptors(notificationEntry);
                StatusBarIcon statusBarIcon = (StatusBarIcon) iconDescriptors.component1();
                StatusBarIcon statusBarIcon2 = (StatusBarIcon) iconDescriptors.component2();
                Notification notification = notificationEntry.getSbn().getNotification();
                CharSequence iconContentDescription = notification != null ? this.iconBuilder.getIconContentDescription(notification) : null;
                StatusBarIconView statusBarIcon3 = notificationEntry.getIcons().getStatusBarIcon();
                if (statusBarIcon3 != null) {
                    statusBarIcon3.setNotification(notificationEntry.getSbn(), iconContentDescription);
                    setIcon(notificationEntry, statusBarIcon, statusBarIcon3);
                }
                StatusBarIconView shelfIcon = notificationEntry.getIcons().getShelfIcon();
                if (shelfIcon != null) {
                    shelfIcon.setNotification(notificationEntry.getSbn(), iconContentDescription);
                    setIcon(notificationEntry, statusBarIcon2, shelfIcon);
                }
                StatusBarIconView aodIcon = notificationEntry.getIcons().getAodIcon();
                if (aodIcon != null) {
                    aodIcon.setNotification(notificationEntry.getSbn(), iconContentDescription);
                    setIcon(notificationEntry, statusBarIcon2, aodIcon);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return unit;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
