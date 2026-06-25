package com.motorola.plugin.core.misc;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.io.Closeable;
import java.io.IOException;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ReceiveChannel;

/* JADX INFO: compiled from: Disposable.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DisposableKt {
    public static final void add(DisposableContainer disposableContainer, Lifecycle lifecycle, LifecycleObserver lifecycleObserver) {
        disposableContainer.getClass();
        lifecycle.getClass();
        lifecycleObserver.getClass();
        disposableContainer.add(asDisposable(lifecycle, lifecycleObserver));
    }

    public static final void add(DisposableContainer disposableContainer, LifecycleOwner lifecycleOwner, LifecycleObserver lifecycleObserver) {
        disposableContainer.getClass();
        lifecycleOwner.getClass();
        lifecycleObserver.getClass();
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        lifecycle.getClass();
        disposableContainer.add(asDisposable(lifecycle, lifecycleObserver));
    }

    public static final void add(DisposableContainer disposableContainer, Closeable closeable) {
        disposableContainer.getClass();
        disposableContainer.add(asDisposable(closeable));
    }

    public static final void add(DisposableContainer disposableContainer, Function0 function0) {
        disposableContainer.getClass();
        function0.getClass();
        disposableContainer.add(asDisposable(function0));
    }

    public static final void add(DisposableContainer disposableContainer, CoroutineScope coroutineScope) {
        disposableContainer.getClass();
        coroutineScope.getClass();
        disposableContainer.add(asDisposable(coroutineScope));
    }

    public static final void add(DisposableContainer disposableContainer, DisposableHandle disposableHandle) {
        disposableContainer.getClass();
        disposableContainer.add(asDisposable(disposableHandle));
    }

    public static final void add(DisposableContainer disposableContainer, Job job) {
        disposableContainer.getClass();
        disposableContainer.add(asDisposable(job));
    }

    public static final void add(DisposableContainer disposableContainer, kotlinx.coroutines.channels.Channel channel) {
        disposableContainer.getClass();
        disposableContainer.add(asDisposable(channel));
    }

    public static final Disposable asDisposable(final Lifecycle lifecycle, final LifecycleObserver lifecycleObserver) {
        lifecycle.getClass();
        lifecycleObserver.getClass();
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.2
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                lifecycle.removeObserver(lifecycleObserver);
            }
        };
    }

    public static final Disposable asDisposable(final Closeable closeable) {
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.3
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() throws IOException {
                Closeable closeable2 = closeable;
                if (closeable2 == null) {
                    return;
                }
                closeable2.close();
            }
        };
    }

    public static final Disposable asDisposable(final Function0 function0) {
        function0.getClass();
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.1
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                function0.mo2224invoke();
            }
        };
    }

    public static final Disposable asDisposable(final CoroutineScope coroutineScope) {
        coroutineScope.getClass();
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.4
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
            }
        };
    }

    public static final Disposable asDisposable(final DisposableHandle disposableHandle) {
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.7
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                DisposableHandle disposableHandle2 = disposableHandle;
                if (disposableHandle2 == null) {
                    return;
                }
                disposableHandle2.dispose();
            }
        };
    }

    public static final Disposable asDisposable(final Job job) {
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.5
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                Job job2 = job;
                if (job2 == null) {
                    return;
                }
                Job.DefaultImpls.cancel$default(job2, null, 1, null);
            }
        };
    }

    public static final Disposable asDisposable(final kotlinx.coroutines.channels.Channel channel) {
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.asDisposable.6
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                kotlinx.coroutines.channels.Channel channel2 = channel;
                if (channel2 == null) {
                    return;
                }
                ReceiveChannel.DefaultImpls.cancel$default(channel2, null, 1, null);
            }
        };
    }

    public static final Disposable disposable(final Function0 function0) {
        function0.getClass();
        return new Disposable() { // from class: com.motorola.plugin.core.misc.DisposableKt.disposable.1
            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
                function0.mo2224invoke();
            }
        };
    }

    public static final DisposableContainer newDisposableContainer() {
        return new CompositeDisposable();
    }
}
