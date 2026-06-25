package androidx.window.layout.adapter.extensions;

import android.content.Context;
import androidx.core.util.Consumer;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.reflection.Consumer2;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;

/* JADX INFO: compiled from: MulticastConsumerApi2.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MulticastConsumerApi2 implements Consumer, Consumer2 {
    private final Context context;
    private final ReentrantLock globalLock;
    private WindowLayoutInfo lastKnownValue;
    private final Set registeredListeners;

    public MulticastConsumerApi2(Context context) {
        context.getClass();
        this.context = context;
        this.globalLock = new ReentrantLock();
        this.registeredListeners = new LinkedHashSet();
    }

    @Override // androidx.core.util.Consumer
    public void accept(androidx.window.extensions.layout.WindowLayoutInfo windowLayoutInfo) {
        windowLayoutInfo.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            WindowLayoutInfo windowLayoutInfoTranslate$window_release = ExtensionsWindowLayoutInfoAdapter.INSTANCE.translate$window_release(this.context, windowLayoutInfo);
            this.lastKnownValue = windowLayoutInfoTranslate$window_release;
            Iterator it = this.registeredListeners.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(windowLayoutInfoTranslate$window_release);
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final void addListener(Consumer consumer) {
        consumer.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            WindowLayoutInfo windowLayoutInfo = this.lastKnownValue;
            if (windowLayoutInfo != null) {
                consumer.accept(windowLayoutInfo);
            }
            this.registeredListeners.add(consumer);
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final boolean isEmpty() {
        return this.registeredListeners.isEmpty();
    }

    public final void removeListener(Consumer consumer) {
        consumer.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            this.registeredListeners.remove(consumer);
        } finally {
            reentrantLock.unlock();
        }
    }
}
