package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ActionReceiver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActionReceiver extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);
    private static final AtomicInteger index = new AtomicInteger(0);
    private final String action;
    private final ArraySet activeCategories;
    private final Executor bgExecutor;
    private final ArraySet receiverDatas;
    private final Function2 registerAction;
    private boolean registered;
    private final Function1 unregisterAction;
    private final int userId;

    /* JADX INFO: compiled from: ActionReceiver.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ActionReceiver(String str, int i, Function2 function2, Function1 function1, Executor executor) {
        str.getClass();
        function2.getClass();
        function1.getClass();
        executor.getClass();
        this.action = str;
        this.userId = i;
        this.registerAction = function2;
        this.unregisterAction = function1;
        this.bgExecutor = executor;
        this.receiverDatas = new ArraySet();
        this.activeCategories = new ArraySet();
    }

    private final IntentFilter createFilter() {
        IntentFilter intentFilter = new IntentFilter(this.action);
        Iterator it = this.activeCategories.iterator();
        while (it.hasNext()) {
            intentFilter.addCategory((String) it.next());
        }
        return intentFilter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeReceiver$lambda$1(BroadcastReceiver broadcastReceiver, ReceiverData receiverData) {
        return Intrinsics.areEqual(receiverData.getReceiver(), broadcastReceiver);
    }

    public final void addReceiverData(ReceiverData receiverData) {
        Sequence sequenceEmptySequence;
        receiverData.getClass();
        if (!receiverData.getFilter().hasAction(this.action)) {
            throw new IllegalArgumentException("Trying to attach to " + this.action + " without correct action,receiver: " + receiverData.getReceiver());
        }
        ArraySet arraySet = this.activeCategories;
        Iterator<String> itCategoriesIterator = receiverData.getFilter().categoriesIterator();
        if (itCategoriesIterator == null || (sequenceEmptySequence = SequencesKt.asSequence(itCategoriesIterator)) == null) {
            sequenceEmptySequence = SequencesKt.emptySequence();
        }
        boolean zAddAll = CollectionsKt.addAll(arraySet, sequenceEmptySequence);
        if (this.receiverDatas.add(receiverData) && this.receiverDatas.size() == 1) {
            this.registerAction.invoke(this, createFilter());
            this.registered = true;
        } else if (zAddAll) {
            this.unregisterAction.invoke(this);
            this.registerAction.invoke(this, createFilter());
        }
    }

    public final boolean hasReceiver(BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        ArraySet arraySet = this.receiverDatas;
        if (arraySet != null && arraySet.isEmpty()) {
            return false;
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((ReceiverData) it.next()).getReceiver(), broadcastReceiver)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        context.getClass();
        intent.getClass();
        if (Intrinsics.areEqual(intent.getAction(), this.action)) {
            index.getAndIncrement();
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver.onReceive.1
                @Override // java.lang.Runnable
                public final void run() {
                    ArraySet<ReceiverData> arraySet = ActionReceiver.this.receiverDatas;
                    final Intent intent2 = intent;
                    final ActionReceiver actionReceiver = ActionReceiver.this;
                    final Context context2 = context;
                    for (final ReceiverData receiverData : arraySet) {
                        if (receiverData.getFilter().matchCategories(intent2.getCategories()) == null) {
                            receiverData.getExecutor().execute(new Runnable() { // from class: com.android.systemui.broadcast.ActionReceiver$onReceive$1$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    receiverData.getReceiver().setPendingResult(actionReceiver.getPendingResult());
                                    receiverData.getReceiver().onReceive(context2, intent2);
                                }
                            });
                        }
                    }
                }
            });
            return;
        }
        throw new IllegalStateException("Received intent for " + intent.getAction() + " in receiver for " + this.action + "}");
    }

    public final void removeReceiver(final BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        if (CollectionsKt.removeAll(this.receiverDatas, new Function1() { // from class: com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(ActionReceiver.removeReceiver$lambda$1(broadcastReceiver, (ReceiverData) obj));
            }
        }) && this.receiverDatas.isEmpty() && this.registered) {
            this.unregisterAction.invoke(this);
            this.registered = false;
            this.activeCategories.clear();
        }
    }
}
