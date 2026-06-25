package androidx.window.layout.adapter.extensions;

import android.content.Context;
import androidx.core.util.Consumer;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.layout.WindowLayoutComponent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;

/* JADX INFO: compiled from: ExtensionWindowBackendApi2.kt */
/* JADX INFO: loaded from: classes.dex */
public class ExtensionWindowBackendApi2 extends ExtensionWindowBackendApi1 {
    private final Map contextToListeners;
    private final ReentrantLock globalLock;
    private final Map listenerToContext;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtensionWindowBackendApi2(WindowLayoutComponent windowLayoutComponent, ConsumerAdapter consumerAdapter) {
        super(windowLayoutComponent, consumerAdapter);
        windowLayoutComponent.getClass();
        consumerAdapter.getClass();
        this.globalLock = new ReentrantLock();
        this.contextToListeners = new LinkedHashMap();
        this.listenerToContext = new LinkedHashMap();
    }

    @Override // androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi1, androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi0, androidx.window.layout.adapter.WindowBackend
    public void registerLayoutChangeCallback(Context context, Executor executor, Consumer consumer) {
        Unit unit;
        context.getClass();
        executor.getClass();
        consumer.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            MulticastConsumerApi2 multicastConsumerApi2 = (MulticastConsumerApi2) this.contextToListeners.get(context);
            if (multicastConsumerApi2 != null) {
                multicastConsumerApi2.addListener(consumer);
                this.listenerToContext.put(consumer, context);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                MulticastConsumerApi2 multicastConsumerApi22 = new MulticastConsumerApi2(context);
                this.contextToListeners.put(context, multicastConsumerApi22);
                this.listenerToContext.put(consumer, context);
                multicastConsumerApi22.addListener(consumer);
                getComponent().addWindowLayoutInfoListener(context, multicastConsumerApi22);
            }
            Unit unit2 = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi1, androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi0, androidx.window.layout.adapter.WindowBackend
    public void unregisterLayoutChangeCallback(Consumer consumer) {
        consumer.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            Context context = (Context) this.listenerToContext.get(consumer);
            if (context == null) {
                reentrantLock.unlock();
                return;
            }
            MulticastConsumerApi2 multicastConsumerApi2 = (MulticastConsumerApi2) this.contextToListeners.get(context);
            if (multicastConsumerApi2 == null) {
                reentrantLock.unlock();
                return;
            }
            multicastConsumerApi2.removeListener(consumer);
            this.listenerToContext.remove(consumer);
            if (multicastConsumerApi2.isEmpty()) {
                this.contextToListeners.remove(context);
                getComponent().removeWindowLayoutInfoListener(multicastConsumerApi2);
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
