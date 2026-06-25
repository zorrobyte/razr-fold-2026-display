package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Iterator;

/* JADX INFO: compiled from: NotifPipelineChoreographer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifPipelineChoreographerImpl implements NotifPipelineChoreographer {
    private final DelayableExecutor executor;
    private final Choreographer.FrameCallback frameCallback;
    private boolean isScheduled;
    private final ListenerSet listeners;
    private Runnable timeoutSubscription;
    private final Choreographer viewChoreographer;

    public NotifPipelineChoreographerImpl(Choreographer choreographer, DelayableExecutor delayableExecutor) {
        choreographer.getClass();
        delayableExecutor.getClass();
        this.viewChoreographer = choreographer;
        this.executor = delayableExecutor;
        this.listeners = new ListenerSet();
        this.frameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl$frameCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                if (this.this$0.isScheduled) {
                    this.this$0.isScheduled = false;
                    Runnable runnable = this.this$0.timeoutSubscription;
                    if (runnable != null) {
                        runnable.run();
                    }
                    Iterator<E> it = this.this$0.listeners.iterator();
                    while (it.hasNext()) {
                        ((Runnable) it.next()).run();
                    }
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onTimeout() {
        if (this.isScheduled) {
            this.isScheduled = false;
            this.viewChoreographer.removeFrameCallback(this.frameCallback);
            Iterator<E> it = this.listeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographer
    public void addOnEvalListener(Runnable runnable) {
        runnable.getClass();
        this.listeners.addIfAbsent(runnable);
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographer
    public void schedule() {
        if (this.isScheduled) {
            return;
        }
        this.isScheduled = true;
        this.viewChoreographer.postFrameCallback(this.frameCallback);
        if (this.isScheduled) {
            this.timeoutSubscription = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl.schedule.1
                @Override // java.lang.Runnable
                public final void run() {
                    NotifPipelineChoreographerImpl.this.onTimeout();
                }
            }, 100L);
        }
    }
}
