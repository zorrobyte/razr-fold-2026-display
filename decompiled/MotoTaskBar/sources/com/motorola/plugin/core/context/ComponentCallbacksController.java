package com.motorola.plugin.core.context;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.Disposable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ComponentCallbacksController.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ComponentCallbacksController implements Disposable, ConfigurationController.ConfigurationListener {
    private final ConfigurationController configurationController;
    private final Set mComponentCallbacks = new LinkedHashSet();

    public ComponentCallbacksController(ConfigurationController configurationController) {
        this.configurationController = configurationController;
        if (configurationController == null) {
            return;
        }
        configurationController.addCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unregisterComponentCallbacks$lambda-0, reason: not valid java name */
    public static final boolean m2217unregisterComponentCallbacks$lambda0(ComponentCallbacks componentCallbacks, WeakReference weakReference) {
        componentCallbacks.getClass();
        weakReference.getClass();
        return Intrinsics.areEqual(weakReference.get(), componentCallbacks);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        ConfigurationController configurationController = this.configurationController;
        if (configurationController != null) {
            configurationController.removeCallback(this);
        }
        this.mComponentCallbacks.clear();
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration, BitFlag bitFlag) {
        configuration.getClass();
        bitFlag.getClass();
        Iterator it = this.mComponentCallbacks.iterator();
        while (it.hasNext()) {
            ComponentCallbacks componentCallbacks = (ComponentCallbacks) ((WeakReference) it.next()).get();
            if (componentCallbacks != null) {
                componentCallbacks.onConfigurationChanged(configuration);
            }
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onLowMemory() {
        Iterator it = this.mComponentCallbacks.iterator();
        while (it.hasNext()) {
            ComponentCallbacks componentCallbacks = (ComponentCallbacks) ((WeakReference) it.next()).get();
            if (componentCallbacks != null) {
                componentCallbacks.onLowMemory();
            }
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onTrimMemory(int i) {
        Set set = this.mComponentCallbacks;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object obj = ((WeakReference) it.next()).get();
            ComponentCallbacks2 componentCallbacks2 = obj instanceof ComponentCallbacks2 ? (ComponentCallbacks2) obj : null;
            if (componentCallbacks2 != null) {
                arrayList.add(componentCallbacks2);
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ((ComponentCallbacks2) obj2).onTrimMemory(i);
        }
    }

    public final void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        componentCallbacks.getClass();
        this.mComponentCallbacks.add(new WeakReference(componentCallbacks));
    }

    public final void unregisterComponentCallbacks(final ComponentCallbacks componentCallbacks) {
        componentCallbacks.getClass();
        this.mComponentCallbacks.removeIf(new Predicate() { // from class: com.motorola.plugin.core.context.ComponentCallbacksController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComponentCallbacksController.m2217unregisterComponentCallbacks$lambda0(componentCallbacks, (WeakReference) obj);
            }
        });
    }
}
