package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: UserBroadcastDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public class UserBroadcastDispatcher {
    public static final Companion Companion = new Companion(null);
    private static final AtomicInteger index = new AtomicInteger(0);
    private final ArrayMap actionsToActionsReceivers;
    private final Executor bgExecutor;
    private final UserBroadcastDispatcher$bgHandler$1 bgHandler;
    private final Looper bgLooper;
    private final Context context;
    private final ArrayMap receiverToActions;
    private final int userId;

    /* JADX INFO: compiled from: UserBroadcastDispatcher.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.broadcast.UserBroadcastDispatcher$bgHandler$1] */
    public UserBroadcastDispatcher(Context context, int i, final Looper looper, Executor executor) {
        context.getClass();
        looper.getClass();
        executor.getClass();
        this.context = context;
        this.userId = i;
        this.bgLooper = looper;
        this.bgExecutor = executor;
        this.bgHandler = new Handler(looper) { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$bgHandler$1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                message.getClass();
                int i2 = message.what;
                if (i2 == 0) {
                    UserBroadcastDispatcher userBroadcastDispatcher = this.this$0;
                    Object obj = message.obj;
                    obj.getClass();
                    userBroadcastDispatcher.handleRegisterReceiver((ReceiverData) obj);
                    return;
                }
                if (i2 != 1) {
                    return;
                }
                UserBroadcastDispatcher userBroadcastDispatcher2 = this.this$0;
                Object obj2 = message.obj;
                obj2.getClass();
                userBroadcastDispatcher2.handleUnregisterReceiver((BroadcastReceiver) obj2);
            }
        };
        this.actionsToActionsReceivers = new ArrayMap();
        this.receiverToActions = new ArrayMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit createActionReceiver$lambda$4(UserBroadcastDispatcher userBroadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        broadcastReceiver.getClass();
        intentFilter.getClass();
        userBroadcastDispatcher.context.registerReceiverAsUser(broadcastReceiver, UserHandle.of(userBroadcastDispatcher.userId), intentFilter, null, userBroadcastDispatcher.bgHandler, 2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit createActionReceiver$lambda$5(UserBroadcastDispatcher userBroadcastDispatcher, String str, BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        try {
            userBroadcastDispatcher.context.unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e("UserBroadcastDispatcher", "Trying to unregister unregistered receiver for user " + userBroadcastDispatcher.userId + ", action " + str, new IllegalStateException(e));
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void getActionsToActionsReceivers$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleRegisterReceiver(ReceiverData receiverData) {
        Sequence sequenceEmptySequence;
        Preconditions.checkState(getLooper().isCurrentThread(), "This method should only be called from BG thread");
        ArrayMap arrayMap = this.receiverToActions;
        BroadcastReceiver receiver = receiverData.getReceiver();
        Object arraySet = arrayMap.get(receiver);
        if (arraySet == null) {
            arraySet = new ArraySet();
            arrayMap.put(receiver, arraySet);
        }
        Collection collection = (Collection) arraySet;
        Iterator<String> itActionsIterator = receiverData.getFilter().actionsIterator();
        if (itActionsIterator == null || (sequenceEmptySequence = SequencesKt.asSequence(itActionsIterator)) == null) {
            sequenceEmptySequence = SequencesKt.emptySequence();
        }
        CollectionsKt.addAll(collection, sequenceEmptySequence);
        Iterator<String> itActionsIterator2 = receiverData.getFilter().actionsIterator();
        itActionsIterator2.getClass();
        while (itActionsIterator2.hasNext()) {
            String next = itActionsIterator2.next();
            ArrayMap arrayMap2 = this.actionsToActionsReceivers;
            Object objCreateActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = arrayMap2.get(next);
            if (objCreateActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core == null) {
                next.getClass();
                objCreateActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = createActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(next);
                arrayMap2.put(next, objCreateActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core);
            }
            ((ActionReceiver) objCreateActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core).addReceiverData(receiverData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleUnregisterReceiver(BroadcastReceiver broadcastReceiver) {
        Preconditions.checkState(getLooper().isCurrentThread(), "This method should only be called from BG thread");
        Object orDefault = this.receiverToActions.getOrDefault(broadcastReceiver, new LinkedHashSet());
        orDefault.getClass();
        Iterator it = ((Iterable) orDefault).iterator();
        while (it.hasNext()) {
            ActionReceiver actionReceiver = (ActionReceiver) this.actionsToActionsReceivers.get((String) it.next());
            if (actionReceiver != null) {
                actionReceiver.removeReceiver(broadcastReceiver);
            }
        }
        this.receiverToActions.remove(broadcastReceiver);
    }

    public ActionReceiver createActionReceiver$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(final String str) {
        str.getClass();
        return new ActionReceiver(str, this.userId, new Function2() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return UserBroadcastDispatcher.createActionReceiver$lambda$4(this.f$0, (BroadcastReceiver) obj, (IntentFilter) obj2);
            }
        }, new Function1() { // from class: com.android.systemui.broadcast.UserBroadcastDispatcher$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return UserBroadcastDispatcher.createActionReceiver$lambda$5(this.f$0, str, (BroadcastReceiver) obj);
            }
        }, this.bgExecutor);
    }

    public final boolean isReceiverReferenceHeld$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        Collection collectionValues = this.actionsToActionsReceivers.values();
        collectionValues.getClass();
        Collection collection = collectionValues;
        if (!collection.isEmpty()) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (((ActionReceiver) it.next()).hasReceiver(broadcastReceiver)) {
                    return true;
                }
            }
        }
        return this.receiverToActions.containsKey(broadcastReceiver);
    }

    public final void registerReceiver(ReceiverData receiverData) {
        receiverData.getClass();
        obtainMessage(0, receiverData).sendToTarget();
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        obtainMessage(1, broadcastReceiver).sendToTarget();
    }
}
