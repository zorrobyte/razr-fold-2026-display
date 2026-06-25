package com.motorola.plugin.core.components;

import com.motorola.plugin.core.components.PluginWhitelistPolicy;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.MarkFlag;
import kotlin.Deprecated;

/* JADX INFO: compiled from: PluginWhitelistPolicyExt.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginWhitelistPolicyExt extends PluginWhitelistPolicy, OnInitializedAware {

    /* JADX INFO: compiled from: PluginWhitelistPolicyExt.kt */
    public final class DefaultImpls {
        @Deprecated
        public static void installWhitelistedPlugins(PluginWhitelistPolicyExt pluginWhitelistPolicyExt, String[] strArr) {
            pluginWhitelistPolicyExt.getClass();
            strArr.getClass();
            PluginWhitelistPolicy.DefaultImpls.installWhitelistedPlugins(pluginWhitelistPolicyExt, strArr);
        }
    }

    void onPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag);
}
