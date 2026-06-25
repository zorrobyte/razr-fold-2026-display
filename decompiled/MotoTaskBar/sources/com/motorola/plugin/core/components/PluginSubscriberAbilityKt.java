package com.motorola.plugin.core.components;

import com.motorola.plugin.core.context.PluginPackage;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: PluginSubscriberAbility.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginSubscriberAbilityKt {
    public static final void subscribePlugin(PluginSubscriberAbility pluginSubscriberAbility, KClass kClass, PluginPackage pluginPackage) {
        pluginSubscriberAbility.getClass();
        kClass.getClass();
        pluginSubscriberAbility.subscribePlugin(JvmClassMappingKt.getJavaClass(kClass), pluginPackage);
    }

    public static /* synthetic */ void subscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, KClass kClass, PluginPackage pluginPackage, int i, Object obj) {
        if ((i & 2) != 0) {
            pluginPackage = null;
        }
        subscribePlugin(pluginSubscriberAbility, kClass, pluginPackage);
    }

    public static final void unsubscribePlugin(PluginSubscriberAbility pluginSubscriberAbility, KClass kClass, PluginPackage pluginPackage) {
        pluginSubscriberAbility.getClass();
        kClass.getClass();
        pluginSubscriberAbility.unsubscribePlugin(JvmClassMappingKt.getJavaClass(kClass), pluginPackage);
    }

    public static /* synthetic */ void unsubscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, KClass kClass, PluginPackage pluginPackage, int i, Object obj) {
        if ((i & 2) != 0) {
            pluginPackage = null;
        }
        unsubscribePlugin(pluginSubscriberAbility, kClass, pluginPackage);
    }
}
