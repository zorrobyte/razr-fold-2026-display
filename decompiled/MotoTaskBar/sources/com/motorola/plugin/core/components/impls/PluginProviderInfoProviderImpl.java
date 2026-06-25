package com.motorola.plugin.core.components.impls;

import android.content.res.Resources;
import android.graphics.drawable.Icon;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.components.PluginProviderInfoProvider;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.discovery.PluginInfo;
import com.motorola.plugin.core.discovery.PluginProviderInfo;
import com.motorola.plugin.core.discovery.RemotePluginInfo;
import com.motorola.plugin.core.misc.BitFlagKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: PluginProviderInfoProviderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginProviderInfoProviderImpl implements PluginProviderInfoProvider {
    private final PluginInfoManager pluginInfoManager;

    public PluginProviderInfoProviderImpl(PluginInfoManager pluginInfoManager) {
        pluginInfoManager.getClass();
        this.pluginInfoManager = pluginInfoManager;
    }

    private final PluginProviderInfo toPluginProviderInfo(PluginInfo pluginInfo) {
        String id = pluginInfo.getPluginPackage().getPluginId().getId();
        CharSequence text = null;
        Icon iconCreateWithResource = pluginInfo.getIcon() != 0 ? Icon.createWithResource(id, pluginInfo.getIcon()) : null;
        Icon iconCreateWithResource2 = pluginInfo.getPreviewImage() != 0 ? Icon.createWithResource(id, pluginInfo.getPreviewImage()) : null;
        if (BitFlagKt.contains(pluginInfo.getFlags(), 2) && (pluginInfo.getIcon() != 0 || pluginInfo.getPreviewImage() != 0 || pluginInfo.getLabel() != 0)) {
            PluginContext pluginContext = this.pluginInfoManager.getPluginContext(pluginInfo);
            Resources resources = pluginContext == null ? null : pluginContext.getResources();
            if (pluginInfo.getIcon() != 0) {
                iconCreateWithResource = ExtensionsKt.toIcon(resources == null ? null : resources.getDrawable(pluginInfo.getIcon(), pluginContext.getTheme()));
            }
            if (pluginInfo.getPreviewImage() != 0) {
                iconCreateWithResource2 = ExtensionsKt.toIcon(resources == null ? null : resources.getDrawable(pluginInfo.getPreviewImage(), pluginContext.getTheme()));
            }
            if (pluginInfo.getLabel() != 0 && resources != null) {
                text = resources.getText(pluginInfo.getLabel(), PluginConfigKt.TAG_PLUGIN);
            }
        }
        return new PluginProviderInfo(id, pluginInfo.getAction(), pluginInfo.getPrototypePluginClass(), pluginInfo.getLabel(), text, iconCreateWithResource, iconCreateWithResource2, pluginInfo.getConfigure(), pluginInfo.getCompatibilityInfo(), pluginInfo.getPluginStyle(), new ArrayList(pluginInfo.getPluginTags()));
    }

    private final PluginProviderInfo toPluginProviderInfo(RemotePluginInfo remotePluginInfo) {
        throw new NotImplementedError("An operation is not implemented: todo: to support the remote plugin?");
    }

    @Override // com.motorola.plugin.core.components.PluginProviderInfoProvider
    public List getInstalledPluginProviderInfoList(boolean z) {
        List installedPluginInfoList = this.pluginInfoManager.getInstalledPluginInfoList();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(installedPluginInfoList, 10));
        Iterator it = installedPluginInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(toPluginProviderInfo((PluginInfo) it.next()));
        }
        if (!z) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((PluginProviderInfo) obj).getCompatibilityInfo().getOptimisticCompat()) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
