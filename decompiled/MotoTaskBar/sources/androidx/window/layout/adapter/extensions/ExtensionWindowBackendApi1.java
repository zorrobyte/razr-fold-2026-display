package androidx.window.layout.adapter.extensions;

import android.app.Activity;
import android.content.Context;
import androidx.core.util.Consumer;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.layout.WindowLayoutComponent;
import androidx.window.extensions.layout.WindowLayoutInfo;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: ExtensionWindowBackendApi1.kt */
/* JADX INFO: loaded from: classes.dex */
public class ExtensionWindowBackendApi1 extends ExtensionWindowBackendApi0 {
    private final WindowLayoutComponent component;
    private final ConsumerAdapter consumerAdapter;
    private final Map consumerToToken;
    private final Map contextToListeners;
    private final ReentrantLock globalLock;
    private final Map listenerToContext;

    public ExtensionWindowBackendApi1(WindowLayoutComponent windowLayoutComponent, ConsumerAdapter consumerAdapter) {
        windowLayoutComponent.getClass();
        consumerAdapter.getClass();
        this.component = windowLayoutComponent;
        this.consumerAdapter = consumerAdapter;
        this.globalLock = new ReentrantLock();
        this.contextToListeners = new LinkedHashMap();
        this.listenerToContext = new LinkedHashMap();
        this.consumerToToken = new LinkedHashMap();
    }

    public final WindowLayoutComponent getComponent() {
        return this.component;
    }

    @Override // androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi0, androidx.window.layout.adapter.WindowBackend
    public void registerLayoutChangeCallback(Context context, Executor executor, Consumer consumer) {
        Unit unit;
        context.getClass();
        executor.getClass();
        consumer.getClass();
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            MulticastConsumer multicastConsumer = (MulticastConsumer) this.contextToListeners.get(context);
            if (multicastConsumer != null) {
                multicastConsumer.addListener(consumer);
                this.listenerToContext.put(consumer, context);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                MulticastConsumer multicastConsumer2 = new MulticastConsumer(context);
                this.contextToListeners.put(context, multicastConsumer2);
                this.listenerToContext.put(consumer, context);
                multicastConsumer2.addListener(consumer);
                if (!(context instanceof Activity)) {
                    multicastConsumer2.accept(new WindowLayoutInfo(CollectionsKt.emptyList()));
                    reentrantLock.unlock();
                    return;
                } else {
                    this.consumerToToken.put(multicastConsumer2, this.consumerAdapter.createSubscription(this.component, Reflection.getOrCreateKotlinClass(WindowLayoutInfo.class), "addWindowLayoutInfoListener", "removeWindowLayoutInfoListener", (Activity) context, new ExtensionWindowBackendApi1$registerLayoutChangeCallback$1$2$disposableToken$1(multicastConsumer2)));
                }
            }
            Unit unit2 = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi0, androidx.window.layout.adapter.WindowBackend
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
            MulticastConsumer multicastConsumer = (MulticastConsumer) this.contextToListeners.get(context);
            if (multicastConsumer == null) {
                reentrantLock.unlock();
                return;
            }
            multicastConsumer.removeListener(consumer);
            this.listenerToContext.remove(consumer);
            if (multicastConsumer.isEmpty()) {
                this.contextToListeners.remove(context);
                ConsumerAdapter.Subscription subscription = (ConsumerAdapter.Subscription) this.consumerToToken.remove(multicastConsumer);
                if (subscription != null) {
                    subscription.dispose();
                }
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
