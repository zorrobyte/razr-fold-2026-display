package com.android.systemui.settings;

import android.app.IActivityManager;
import android.app.UserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: compiled from: UserTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public class UserTrackerImpl extends BroadcastReceiver implements UserTracker, Dumpable {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(UserTrackerImpl.class, "userId", "getUserId()I", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(UserTrackerImpl.class, "userHandle", "getUserHandle()Landroid/os/UserHandle;", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(UserTrackerImpl.class, "userContext", "getUserContext()Landroid/content/Context;", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(UserTrackerImpl.class, "userProfiles", "getUserProfiles()Ljava/util/List;", 0))};
    public static final Companion Companion = new Companion(null);
    private Job afterUserSwitchingJob;
    private final CoroutineScope appScope;
    private final CoroutineDispatcher backgroundContext;
    private final Handler backgroundHandler;
    private final List callbacks;
    private final Context context;
    private final DumpManager dumpManager;
    private final IActivityManager iActivityManager;
    private boolean initialized;
    private boolean isUserSwitching;
    private final Object mutex;
    private final SynchronizedDelegate userContext$delegate;
    private final SynchronizedDelegate userHandle$delegate;
    private final SynchronizedDelegate userId$delegate;
    private final UserManager userManager;
    private final SynchronizedDelegate userProfiles$delegate;
    private Job userSwitchingJob;

    /* JADX INFO: compiled from: UserTrackerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: UserTrackerImpl.kt */
    final class SynchronizedDelegate implements ReadWriteProperty {
        private Object value;

        public SynchronizedDelegate(Object obj) {
            obj.getClass();
            this.value = obj;
        }

        @Override // kotlin.properties.ReadWriteProperty
        public Object getValue(UserTrackerImpl userTrackerImpl, KProperty kProperty) {
            Object obj;
            userTrackerImpl.getClass();
            kProperty.getClass();
            if (userTrackerImpl.getInitialized()) {
                synchronized (userTrackerImpl.mutex) {
                    obj = this.value;
                }
                return obj;
            }
            throw new IllegalStateException("Must initialize before getting " + kProperty.getName());
        }

        @Override // kotlin.properties.ReadWriteProperty
        public void setValue(UserTrackerImpl userTrackerImpl, KProperty kProperty, Object obj) {
            userTrackerImpl.getClass();
            kProperty.getClass();
            obj.getClass();
            synchronized (userTrackerImpl.mutex) {
                this.value = obj;
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchingCoroutines$2, reason: invalid class name */
    /* JADX INFO: compiled from: UserTrackerImpl.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $newUserId;
        final /* synthetic */ Function0 $onDone;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ UserTrackerImpl this$0;

        /* JADX INFO: renamed from: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchingCoroutines$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: UserTrackerImpl.kt */
        final class C00112 extends SuspendLambda implements Function2 {
            final /* synthetic */ UserTracker.Callback $callback;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ UserTrackerImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00112(UserTrackerImpl userTrackerImpl, UserTracker.Callback callback, Continuation continuation) {
                super(2, continuation);
                this.this$0 = userTrackerImpl;
                this.$callback = callback;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00112 c00112 = new C00112(this.this$0, this.$callback, continuation);
                c00112.L$0 = obj;
                return c00112;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00112) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Job job;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    final Mutex Mutex = MutexKt.Mutex(true);
                    Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, this.this$0.backgroundContext, null, new UserTrackerImpl$handleUserSwitchingCoroutines$2$2$thresholdLogJob$1(this.$callback, null), 2, null);
                    this.$callback.onUserChanging(this.this$0.getUserId(), this.this$0.getUserContext(), new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl.handleUserSwitchingCoroutines.2.2.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Mutex.DefaultImpls.unlock$default(Mutex, null, 1, null);
                        }
                    });
                    this.L$0 = jobLaunch$default;
                    this.label = 1;
                    if (Mutex.DefaultImpls.lock$default(Mutex, null, this, 1, null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    job = jobLaunch$default;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    job = (Job) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                Job.DefaultImpls.cancel$default(job, null, 1, null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(int i, UserTrackerImpl userTrackerImpl, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$newUserId = i;
            this.this$0 = userTrackerImpl;
            this.$onDone = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$newUserId, this.this$0, this.$onDone, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            List list;
            Iterator it;
            CoroutineScope coroutineScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                Assert.isNotMainThread();
                Log.i("UserTrackerImpl", "Switching to user " + this.$newUserId);
                List list2 = this.this$0.callbacks;
                UserTrackerImpl userTrackerImpl = this.this$0;
                synchronized (list2) {
                    list = CollectionsKt.toList(userTrackerImpl.callbacks);
                }
                it = list.iterator();
                coroutineScope = coroutineScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$1;
                coroutineScope = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (it.hasNext()) {
                DataItem dataItem = (DataItem) it.next();
                UserTracker.Callback callback = (UserTracker.Callback) dataItem.getCallback().get();
                if (callback != null) {
                    Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, ExecutorsKt.from(dataItem.getExecutor()), null, new C00112(this.this$0, callback, null), 2, null);
                    this.L$0 = coroutineScope;
                    this.L$1 = it;
                    this.label = 1;
                    if (jobLaunch$default.join(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            }
            this.$onDone.mo2224invoke();
            return Unit.INSTANCE;
        }
    }

    public UserTrackerImpl(Context context, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        context.getClass();
        userManager.getClass();
        iActivityManager.getClass();
        dumpManager.getClass();
        coroutineScope.getClass();
        coroutineDispatcher.getClass();
        handler.getClass();
        this.context = context;
        this.userManager = userManager;
        this.iActivityManager = iActivityManager;
        this.dumpManager = dumpManager;
        this.appScope = coroutineScope;
        this.backgroundContext = coroutineDispatcher;
        this.backgroundHandler = handler;
        this.mutex = new Object();
        this.userId$delegate = new SynchronizedDelegate(Integer.valueOf(context.getUserId()));
        UserHandle user = context.getUser();
        user.getClass();
        this.userHandle$delegate = new SynchronizedDelegate(user);
        this.userContext$delegate = new SynchronizedDelegate(context);
        this.userProfiles$delegate = new SynchronizedDelegate(CollectionsKt.emptyList());
        this.callbacks = new ArrayList();
    }

    static /* synthetic */ Object handleUserSwitchingCoroutines$suspendImpl(UserTrackerImpl userTrackerImpl, int i, Function0 function0, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(i, userTrackerImpl, function0, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isBackgroundUserSwitchEnabled() {
        return false;
    }

    private final void registerUserSwitchObserver() {
        this.iActivityManager.registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.settings.UserTrackerImpl.registerUserSwitchObserver.1
            public void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) throws InterruptedException {
                UserTrackerImpl.this.handleBeforeUserSwitching(i);
                if (iRemoteCallback != null) {
                    iRemoteCallback.sendResult((Bundle) null);
                }
            }

            public void onUserSwitchComplete(int i) {
                UserTrackerImpl.this.setUserSwitching(false);
                if (!UserTrackerImpl.this.isBackgroundUserSwitchEnabled()) {
                    UserTrackerImpl.this.handleUserSwitchComplete(i);
                    return;
                }
                Job job = UserTrackerImpl.this.afterUserSwitchingJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, null, 1, null);
                }
                UserTrackerImpl userTrackerImpl = UserTrackerImpl.this;
                userTrackerImpl.afterUserSwitchingJob = BuildersKt__Builders_commonKt.launch$default(userTrackerImpl.appScope, UserTrackerImpl.this.backgroundContext, null, new UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitchComplete$1(UserTrackerImpl.this, i, null), 2, null);
            }

            public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws InterruptedException {
                UserTrackerImpl.this.setUserSwitching(true);
                if (!UserTrackerImpl.this.isBackgroundUserSwitchEnabled()) {
                    UserTrackerImpl.this.handleUserSwitching(i);
                    if (iRemoteCallback != null) {
                        iRemoteCallback.sendResult((Bundle) null);
                        return;
                    }
                    return;
                }
                Job job = UserTrackerImpl.this.userSwitchingJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, null, 1, null);
                }
                UserTrackerImpl userTrackerImpl = UserTrackerImpl.this;
                userTrackerImpl.userSwitchingJob = BuildersKt__Builders_commonKt.launch$default(userTrackerImpl.appScope, UserTrackerImpl.this.backgroundContext, null, new UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1(UserTrackerImpl.this, i, iRemoteCallback, null), 2, null);
            }
        }, "UserTrackerImpl");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeCallback$lambda$13$lambda$12(UserTracker.Callback callback, DataItem dataItem) {
        dataItem.getClass();
        return dataItem.sameOrEmpty(callback);
    }

    private final Pair setUserIdInternal(int i) {
        List profiles = this.userManager.getProfiles(i);
        UserHandle userHandle = new UserHandle(i);
        Context contextCreateContextAsUser = this.context.createContextAsUser(userHandle, 0);
        contextCreateContextAsUser.getClass();
        synchronized (this.mutex) {
            try {
                setUserId(i);
                setUserHandle(userHandle);
                setUserContext(contextCreateContextAsUser);
                profiles.getClass();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(profiles, 10));
                Iterator it = profiles.iterator();
                while (it.hasNext()) {
                    arrayList.add(new UserInfo((UserInfo) it.next()));
                }
                setUserProfiles(arrayList);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return TuplesKt.to(contextCreateContextAsUser, profiles);
    }

    @Override // com.android.systemui.settings.UserTracker
    public void addCallback(UserTracker.Callback callback, Executor executor) {
        callback.getClass();
        executor.getClass();
        synchronized (this.callbacks) {
            this.callbacks.add(new DataItem(new WeakReference(callback), executor));
        }
    }

    public final boolean getInitialized() {
        return this.initialized;
    }

    @Override // com.android.systemui.settings.UserContextProvider
    public Context getUserContext() {
        return (Context) this.userContext$delegate.getValue(this, $$delegatedProperties[2]);
    }

    @Override // com.android.systemui.settings.UserTracker
    public int getUserId() {
        return ((Number) this.userId$delegate.getValue(this, $$delegatedProperties[0])).intValue();
    }

    public List getUserProfiles() {
        return (List) this.userProfiles$delegate.getValue(this, $$delegatedProperties[3]);
    }

    protected void handleBeforeUserSwitching(final int i) throws InterruptedException {
        List<DataItem> list;
        setUserIdInternal(i);
        synchronized (this.callbacks) {
            list = CollectionsKt.toList(this.callbacks);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataItem dataItem : list) {
            final UserTracker.Callback callback = (UserTracker.Callback) dataItem.getCallback().get();
            if (callback != null) {
                dataItem.getExecutor().execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserTracker.Callback callback2 = callback;
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        Runnable runnable = new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                countDownLatch2.countDown();
                            }
                        };
                        callback2.onBeforeUserSwitching(i);
                        runnable.run();
                    }
                });
            } else {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
    }

    protected void handleProfilesChanged() {
        List<DataItem> list;
        Assert.isNotMainThread();
        final List profiles = this.userManager.getProfiles(getUserId());
        synchronized (this.mutex) {
            try {
                profiles.getClass();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(profiles, 10));
                Iterator it = profiles.iterator();
                while (it.hasNext()) {
                    arrayList.add(new UserInfo((UserInfo) it.next()));
                }
                setUserProfiles(arrayList);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.callbacks) {
            list = CollectionsKt.toList(this.callbacks);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataItem dataItem : list) {
            final UserTracker.Callback callback = (UserTracker.Callback) dataItem.getCallback().get();
            if (callback != null) {
                dataItem.getExecutor().execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserTracker.Callback callback2 = callback;
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                countDownLatch2.countDown();
                            }
                        };
                        profiles.getClass();
                        callback2.onProfilesChanged(profiles);
                    }
                });
            } else {
                countDownLatch.countDown();
            }
        }
    }

    protected void handleUserSwitchComplete(final int i) {
        List<DataItem> list;
        Assert.isNotMainThread();
        Log.i("UserTrackerImpl", "Switched to user " + i);
        synchronized (this.callbacks) {
            list = CollectionsKt.toList(this.callbacks);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataItem dataItem : list) {
            final UserTracker.Callback callback = (UserTracker.Callback) dataItem.getCallback().get();
            if (callback != null) {
                dataItem.getExecutor().execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserTracker.Callback callback2 = callback;
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitchComplete$$inlined$notifySubscribers$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                countDownLatch2.countDown();
                            }
                        };
                        callback2.onUserChanged(i, this.getUserContext());
                        callback2.onProfilesChanged(this.getUserProfiles());
                    }
                });
            } else {
                countDownLatch.countDown();
            }
        }
    }

    protected void handleUserSwitching(final int i) throws InterruptedException {
        List<DataItem> list;
        Assert.isNotMainThread();
        Log.i("UserTrackerImpl", "Switching to user " + i);
        synchronized (this.callbacks) {
            list = CollectionsKt.toList(this.callbacks);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (DataItem dataItem : list) {
            final UserTracker.Callback callback = (UserTracker.Callback) dataItem.getCallback().get();
            if (callback != null) {
                dataItem.getExecutor().execute(new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserTracker.Callback callback2 = callback;
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        callback2.onUserChanging(i, this.getUserContext(), new Runnable() { // from class: com.android.systemui.settings.UserTrackerImpl$handleUserSwitching$$inlined$notifySubscribers$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                countDownLatch2.countDown();
                            }
                        });
                    }
                });
            } else {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
    }

    protected Object handleUserSwitchingCoroutines(int i, Function0 function0, Continuation continuation) {
        return handleUserSwitchingCoroutines$suspendImpl(this, i, function0, continuation);
    }

    public void initialize(int i) {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        setUserIdInternal(i);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter.addAction("android.intent.action.PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.PROFILE_REMOVED");
        intentFilter.addAction("android.intent.action.PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.PROFILE_UNAVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNLOCKED");
        this.context.registerReceiverForAllUsers(this, intentFilter, null, this.backgroundHandler);
        registerUserSwitchObserver();
        DumpManager.registerDumpable$default(this.dumpManager, "UserTrackerImpl", this, null, 4, null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.getClass();
        intent.getClass();
        String action = intent.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1823736795:
                    if (!action.equals("android.intent.action.PROFILE_REMOVED")) {
                        return;
                    }
                    break;
                case -1462075554:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_UNLOCKED")) {
                        return;
                    }
                    break;
                case -1238404651:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                        return;
                    }
                    break;
                case -1112607227:
                    if (!action.equals("android.intent.action.PROFILE_ADDED")) {
                        return;
                    }
                    break;
                case -1062385259:
                    if (!action.equals("android.intent.action.PROFILE_UNAVAILABLE")) {
                        return;
                    }
                    break;
                case -864107122:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                        return;
                    }
                    break;
                case -385593787:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                        return;
                    }
                    break;
                case -201513518:
                    if (!action.equals("android.intent.action.USER_INFO_CHANGED")) {
                        return;
                    }
                    break;
                case -19011148:
                    if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                        return;
                    }
                    break;
                case 1051477093:
                    if (!action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")) {
                        return;
                    }
                    break;
                case 2014285134:
                    if (!action.equals("android.intent.action.PROFILE_AVAILABLE")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            handleProfilesChanged();
        }
    }

    @Override // com.android.systemui.settings.UserTracker
    public void removeCallback(final UserTracker.Callback callback) {
        callback.getClass();
        synchronized (this.callbacks) {
            List list = this.callbacks;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.settings.UserTrackerImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(UserTrackerImpl.removeCallback$lambda$13$lambda$12(callback, (DataItem) obj));
                }
            };
            list.removeIf(new Predicate(function1) { // from class: com.android.systemui.settings.UserTrackerImpl$sam$java_util_function_Predicate$0
                private final /* synthetic */ Function1 function;

                {
                    function1.getClass();
                    this.function = function1;
                }

                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj) {
                    return ((Boolean) this.function.invoke(obj)).booleanValue();
                }
            });
        }
    }

    protected void setUserContext(Context context) {
        context.getClass();
        this.userContext$delegate.setValue(this, $$delegatedProperties[2], (Object) context);
    }

    protected void setUserHandle(UserHandle userHandle) {
        userHandle.getClass();
        this.userHandle$delegate.setValue(this, $$delegatedProperties[1], (Object) userHandle);
    }

    protected void setUserId(int i) {
        this.userId$delegate.setValue(this, $$delegatedProperties[0], (Object) Integer.valueOf(i));
    }

    protected void setUserProfiles(List list) {
        list.getClass();
        this.userProfiles$delegate.setValue(this, $$delegatedProperties[3], (Object) list);
    }

    protected final void setUserSwitching(boolean z) {
        this.isUserSwitching = z;
    }
}
