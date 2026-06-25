package com.motorola.plugin.core.provider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.XmlResourceParser;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.context.PluginClassLoader;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.extension.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: InstalledPluginProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class InstalledPluginProvider extends SideLoadPluginProvider {
    private final PackageManager mPackageManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InstalledPluginProvider(@DisplayContext Context context, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, ConfigurationController configurationController, PluginEnabler pluginEnabler, PluginEvent pluginEvent) {
        super("i", context, pluginWhitelistPolicyExt, configurationController, pluginEnabler, pluginEvent);
        context.getClass();
        pluginWhitelistPolicyExt.getClass();
        configurationController.getClass();
        pluginEnabler.getClass();
        pluginEvent.getClass();
        PackageManager packageManager = context.getPackageManager();
        packageManager.getClass();
        this.mPackageManager = packageManager;
    }

    @Override // com.motorola.plugin.core.provider.SideLoadPluginProvider
    protected ClassLoader createPluginClassLoader(ServiceInfo serviceInfo, ClassLoader classLoader) {
        serviceInfo.getClass();
        PluginClassLoader.Factory factory = PluginClassLoader.Factory;
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        applicationInfo.getClass();
        return factory.create(applicationInfo, classLoader);
    }

    @Override // com.motorola.plugin.core.provider.SideLoadPluginProvider
    protected List gatheringPluginServiceInfoList(PluginId pluginId) {
        PackageManager packageManager = this.mPackageManager;
        Intent intent = new Intent("com.motorola.plugin.action.PLUGIN_DISCOVERY");
        intent.setPackage(pluginId == null ? null : pluginId.getId());
        Unit unit = Unit.INSTANCE;
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 786560);
        listQueryIntentServices.getClass();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listQueryIntentServices, 10));
        Iterator<T> it = listQueryIntentServices.iterator();
        while (it.hasNext()) {
            arrayList.add(((ResolveInfo) it.next()).serviceInfo);
        }
        return arrayList;
    }

    @Override // com.motorola.plugin.core.provider.SideLoadPluginProvider
    protected ApplicationInfo getPluginPackageAppInfo(PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        pluginPackage.getClass();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(this.mPackageManager.getApplicationInfo(pluginPackage.getPluginId().getId(), 0));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
            objM2707constructorimpl = null;
        }
        return (ApplicationInfo) objM2707constructorimpl;
    }

    @Override // com.motorola.plugin.core.provider.SideLoadPluginProvider
    protected XmlResourceParser loadXmlMetaData(ServiceInfo serviceInfo, String str) {
        serviceInfo.getClass();
        str.getClass();
        return serviceInfo.loadXmlMetaData(this.mPackageManager, str);
    }
}
