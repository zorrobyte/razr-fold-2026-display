package com.android.systemui.plugins;

import android.util.Log;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAnimationsProtector;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockControllerProtector;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockEventsProtector;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceControllerProtector;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockFaceEventsProtector;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.plugins.clocks.ClockFaceLayoutProtector;
import com.android.systemui.plugins.clocks.ClockProvider;
import com.android.systemui.plugins.clocks.ClockProviderPlugin;
import com.android.systemui.plugins.clocks.ClockProviderPluginProtector;
import com.android.systemui.plugins.clocks.ClockProviderProtector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class PluginProtector {
    private static final String TAG = "PluginProtector";
    private static final Map sFactories = Map.ofEntries(Map.entry(TestPlugin.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda0
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return TestPluginProtector.protect((TestPlugin) obj, protectedPluginListener);
        }
    }), Map.entry(ClockAnimations.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda1
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockAnimationsProtector.protect((ClockAnimations) obj, protectedPluginListener);
        }
    }), Map.entry(ClockController.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda2
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockControllerProtector.protect((ClockController) obj, protectedPluginListener);
        }
    }), Map.entry(ClockEvents.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda3
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockEventsProtector.protect((ClockEvents) obj, protectedPluginListener);
        }
    }), Map.entry(ClockFaceController.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda4
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockFaceControllerProtector.protect((ClockFaceController) obj, protectedPluginListener);
        }
    }), Map.entry(ClockFaceEvents.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda5
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockFaceEventsProtector.protect((ClockFaceEvents) obj, protectedPluginListener);
        }
    }), Map.entry(ClockFaceLayout.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda6
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockFaceLayoutProtector.protect((ClockFaceLayout) obj, protectedPluginListener);
        }
    }), Map.entry(ClockProvider.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda7
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockProviderProtector.protect((ClockProvider) obj, protectedPluginListener);
        }
    }), Map.entry(ClockProviderPlugin.class, new Factory() { // from class: com.android.systemui.plugins.PluginProtector$$ExternalSyntheticLambda8
        @Override // com.android.systemui.plugins.PluginProtector.Factory
        public final Object create(Object obj, ProtectedPluginListener protectedPluginListener) {
            return ClockProviderPluginProtector.protect((ClockProviderPlugin) obj, protectedPluginListener);
        }
    }));

    interface Factory {
        Object create(Object obj, ProtectedPluginListener protectedPluginListener);
    }

    private PluginProtector() {
    }

    public static Object protectIfAble(Object obj, ProtectedPluginListener protectedPluginListener) {
        Object objTryProtect = tryProtect(obj, protectedPluginListener);
        return objTryProtect != null ? objTryProtect : obj;
    }

    public static Object tryProtect(Object obj, ProtectedPluginListener protectedPluginListener) {
        int i;
        HashSet hashSet = new HashSet();
        Class<?> superclass = obj.getClass();
        while (true) {
            i = 0;
            if (superclass == null) {
                break;
            }
            Class<?>[] interfaces = superclass.getInterfaces();
            int length = interfaces.length;
            while (i < length) {
                hashSet.add(interfaces[i]);
                i++;
            }
            superclass = superclass.getSuperclass();
        }
        Iterator it = hashSet.iterator();
        Factory factory = null;
        while (it.hasNext()) {
            Factory factory2 = (Factory) sFactories.get((Class) it.next());
            if (factory2 != null) {
                i++;
                factory = factory2;
            }
        }
        if (factory != null) {
            if (i < 2) {
                return factory.create(obj, protectedPluginListener);
            }
            throw new UnsupportedOperationException("Plugin implements more than one protected interface");
        }
        Log.i(TAG, "Wasn't able to wrap " + obj);
        return null;
    }
}
