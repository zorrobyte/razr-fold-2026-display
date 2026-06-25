package com.motorola.plugin.core.components;

import android.content.Intent;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.NotPluginClassException;
import java.util.Map;

/* JADX INFO: compiled from: PluginSubscriberAbility.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginSubscriberAbility {

    /* JADX INFO: compiled from: PluginSubscriberAbility.kt */
    public final class DefaultImpls {
        public static /* synthetic */ com.motorola.plugin.sdk.Plugin getPlugin$default(PluginSubscriberAbility pluginSubscriberAbility, Class cls, PluginPackage pluginPackage, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getPlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            return pluginSubscriberAbility.getPlugin(cls, pluginPackage);
        }

        public static /* synthetic */ com.motorola.plugin.sdk.Plugin getPlugin$default(PluginSubscriberAbility pluginSubscriberAbility, String str, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getPlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            return pluginSubscriberAbility.getPlugin(str, pluginPackage);
        }

        public static /* synthetic */ boolean isPluginSubscribed$default(PluginSubscriberAbility pluginSubscriberAbility, Class cls, PluginPackage pluginPackage, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isPluginSubscribed");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            return pluginSubscriberAbility.isPluginSubscribed(cls, pluginPackage);
        }

        public static /* synthetic */ boolean isPluginSubscribed$default(PluginSubscriberAbility pluginSubscriberAbility, String str, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isPluginSubscribed");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            return pluginSubscriberAbility.isPluginSubscribed(str, pluginPackage);
        }

        public static /* synthetic */ void subscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, Class cls, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subscribePlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            pluginSubscriberAbility.subscribePlugin(cls, pluginPackage);
        }

        public static /* synthetic */ void subscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, String str, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subscribePlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            pluginSubscriberAbility.subscribePlugin(str, pluginPackage);
        }

        public static /* synthetic */ void unsubscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, Class cls, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: unsubscribePlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            pluginSubscriberAbility.unsubscribePlugin(cls, pluginPackage);
        }

        public static /* synthetic */ void unsubscribePlugin$default(PluginSubscriberAbility pluginSubscriberAbility, String str, PluginPackage pluginPackage, int i, Object obj) throws NotPluginClassException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: unsubscribePlugin");
            }
            if ((i & 2) != 0) {
                pluginPackage = null;
            }
            pluginSubscriberAbility.unsubscribePlugin(str, pluginPackage);
        }
    }

    boolean dependsOn(com.motorola.plugin.sdk.Plugin plugin, Class cls);

    Map getAvailablePlugins();

    com.motorola.plugin.sdk.Plugin getPlugin(Class cls, PluginPackage pluginPackage);

    com.motorola.plugin.sdk.Plugin getPlugin(String str, PluginPackage pluginPackage) throws NotPluginClassException;

    boolean isPluginSubscribed(Class cls, PluginPackage pluginPackage);

    boolean isPluginSubscribed(String str, PluginPackage pluginPackage) throws NotPluginClassException;

    void keepChannelConnectionAlive(boolean z);

    boolean launchPlugin(Intent intent, PluginPackage pluginPackage);

    void subscribePlugin(Class cls, PluginPackage pluginPackage) throws NotPluginClassException;

    void subscribePlugin(String str, PluginPackage pluginPackage) throws NotPluginClassException;

    void unsubscribePlugin(Class cls, PluginPackage pluginPackage) throws NotPluginClassException;

    void unsubscribePlugin(String str, PluginPackage pluginPackage) throws NotPluginClassException;
}
