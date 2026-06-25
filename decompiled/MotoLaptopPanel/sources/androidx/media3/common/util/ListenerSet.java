package androidx.media3.common.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.media3.common.FlagSet;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: classes.dex */
public final class ListenerSet {
    private final Clock clock;
    private final ArrayDeque flushingEvents;
    private final HandlerWrapper handler;
    private final IterationFinishedEvent iterationFinishedEvent;
    private final CopyOnWriteArraySet listeners;
    private final ArrayDeque queuedEvents;
    private boolean released;
    private final Object releasedLock;
    private boolean throwsWhenUsingWrongThread;

    public interface Event {
        void invoke(Object obj);
    }

    public interface IterationFinishedEvent {
        void invoke(Object obj, FlagSet flagSet);
    }

    final class ListenerHolder {
        private FlagSet.Builder flagsBuilder = new FlagSet.Builder();
        public final Object listener;
        private boolean needsIterationFinishedEvent;
        private boolean released;

        public ListenerHolder(Object obj) {
            this.listener = obj;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ListenerHolder.class != obj.getClass()) {
                return false;
            }
            return this.listener.equals(((ListenerHolder) obj).listener);
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        public void invoke(int i, Event event) {
            if (this.released) {
                return;
            }
            if (i != -1) {
                this.flagsBuilder.add(i);
            }
            this.needsIterationFinishedEvent = true;
            event.invoke(this.listener);
        }

        public void iterationFinished(IterationFinishedEvent iterationFinishedEvent) {
            if (this.released || !this.needsIterationFinishedEvent) {
                return;
            }
            FlagSet flagSetBuild = this.flagsBuilder.build();
            this.flagsBuilder = new FlagSet.Builder();
            this.needsIterationFinishedEvent = false;
            iterationFinishedEvent.invoke(this.listener, flagSetBuild);
        }

        public void release(IterationFinishedEvent iterationFinishedEvent) {
            this.released = true;
            if (this.needsIterationFinishedEvent) {
                this.needsIterationFinishedEvent = false;
                iterationFinishedEvent.invoke(this.listener, this.flagsBuilder.build());
            }
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$Maws-DUsVhcg5uFQgolq5mcQJiM, reason: not valid java name */
    public static /* synthetic */ void m1999$r8$lambda$MawsDUsVhcg5uFQgolq5mcQJiM(CopyOnWriteArraySet copyOnWriteArraySet, int i, Event event) {
        Iterator it = copyOnWriteArraySet.iterator();
        while (it.hasNext()) {
            ((ListenerHolder) it.next()).invoke(i, event);
        }
    }

    public ListenerSet(Looper looper, Clock clock, IterationFinishedEvent iterationFinishedEvent) {
        this(new CopyOnWriteArraySet(), looper, clock, iterationFinishedEvent, true);
    }

    private ListenerSet(CopyOnWriteArraySet copyOnWriteArraySet, Looper looper, Clock clock, IterationFinishedEvent iterationFinishedEvent, boolean z) {
        this.clock = clock;
        this.listeners = copyOnWriteArraySet;
        this.iterationFinishedEvent = iterationFinishedEvent;
        this.releasedLock = new Object();
        this.flushingEvents = new ArrayDeque();
        this.queuedEvents = new ArrayDeque();
        this.handler = clock.createHandler(looper, new Handler.Callback() { // from class: androidx.media3.common.util.ListenerSet$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.handleMessage(message);
            }
        });
        this.throwsWhenUsingWrongThread = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message message) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((ListenerHolder) it.next()).iterationFinished(this.iterationFinishedEvent);
            if (this.handler.hasMessages(1)) {
                break;
            }
        }
        return true;
    }

    private void verifyCurrentThread() {
        if (this.throwsWhenUsingWrongThread) {
            Assertions.checkState(Thread.currentThread() == this.handler.getLooper().getThread());
        }
    }

    public void add(Object obj) {
        Assertions.checkNotNull(obj);
        synchronized (this.releasedLock) {
            try {
                if (this.released) {
                    return;
                }
                this.listeners.add(new ListenerHolder(obj));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void flushEvents() {
        verifyCurrentThread();
        if (this.queuedEvents.isEmpty()) {
            return;
        }
        if (!this.handler.hasMessages(1)) {
            HandlerWrapper handlerWrapper = this.handler;
            handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(1));
        }
        boolean zIsEmpty = this.flushingEvents.isEmpty();
        this.flushingEvents.addAll(this.queuedEvents);
        this.queuedEvents.clear();
        if (zIsEmpty) {
            while (!this.flushingEvents.isEmpty()) {
                ((Runnable) this.flushingEvents.peekFirst()).run();
                this.flushingEvents.removeFirst();
            }
        }
    }

    public void queueEvent(final int i, final Event event) {
        verifyCurrentThread();
        final CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet(this.listeners);
        this.queuedEvents.add(new Runnable() { // from class: androidx.media3.common.util.ListenerSet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ListenerSet.m1999$r8$lambda$MawsDUsVhcg5uFQgolq5mcQJiM(copyOnWriteArraySet, i, event);
            }
        });
    }

    public void release() {
        verifyCurrentThread();
        synchronized (this.releasedLock) {
            this.released = true;
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((ListenerHolder) it.next()).release(this.iterationFinishedEvent);
        }
        this.listeners.clear();
    }

    public void remove(Object obj) {
        verifyCurrentThread();
        for (ListenerHolder listenerHolder : this.listeners) {
            if (listenerHolder.listener.equals(obj)) {
                listenerHolder.release(this.iterationFinishedEvent);
                this.listeners.remove(listenerHolder);
            }
        }
    }

    public void sendEvent(int i, Event event) {
        queueEvent(i, event);
        flushEvents();
    }
}
