package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import androidx.lifecycle.Observer;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifLiveDataStoreImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifLiveDataImpl implements NotifLiveData {
    private final ListenerSet asyncObservers;
    private final AtomicReference atomicValue;
    private Object lastAsyncValue;
    private final Executor mainExecutor;
    private final String name;
    private final ListenerSet syncObservers;

    public NotifLiveDataImpl(String str, Object obj, Executor executor) {
        str.getClass();
        executor.getClass();
        this.name = str;
        this.mainExecutor = executor;
        this.syncObservers = new ListenerSet();
        this.asyncObservers = new ListenerSet();
        this.atomicValue = new AtomicReference(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchToAsyncObservers() {
        Object obj = this.atomicValue.get();
        if (Intrinsics.areEqual(this.lastAsyncValue, obj)) {
            return;
        }
        this.lastAsyncValue = obj;
        String str = "NotifLiveData(" + this.name + ").dispatchToAsyncObservers";
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            Iterator<E> it = this.asyncObservers.iterator();
            while (it.hasNext()) {
                ((Observer) it.next()).onChanged(obj);
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setValueAndProvideDispatcher$lambda$4(final NotifLiveDataImpl notifLiveDataImpl, Object obj) {
        if (!notifLiveDataImpl.syncObservers.isEmpty()) {
            String str = "NotifLiveData(" + notifLiveDataImpl.name + ").dispatchToSyncObservers";
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice(str);
            }
            try {
                Iterator<E> it = notifLiveDataImpl.syncObservers.iterator();
                while (it.hasNext()) {
                    ((Observer) it.next()).onChanged(obj);
                }
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
        if (!notifLiveDataImpl.asyncObservers.isEmpty()) {
            notifLiveDataImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$setValueAndProvideDispatcher$1$2
                @Override // java.lang.Runnable
                public final void run() {
                    this.$tmp0.dispatchToAsyncObservers();
                }
            });
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotifLiveData
    public Object getValue() {
        return this.atomicValue.get();
    }

    public final Function0 setValueAndProvideDispatcher(final Object obj) {
        return !Intrinsics.areEqual(this.atomicValue.getAndSet(obj), obj) ? new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotifLiveDataImpl.setValueAndProvideDispatcher$lambda$4(this.f$0, obj);
            }
        } : new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Unit.INSTANCE;
            }
        };
    }
}
