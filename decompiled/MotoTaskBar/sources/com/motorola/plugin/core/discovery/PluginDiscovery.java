package com.motorola.plugin.core.discovery;

import android.content.Context;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import java.util.List;

/* JADX INFO: compiled from: PluginDiscovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginDiscovery extends Disposable, ISnapshotAware {

    /* JADX INFO: compiled from: PluginDiscovery.kt */
    public final class DefaultImpls {
        public static /* synthetic */ List discovery$default(PluginDiscovery pluginDiscovery, Context context, String str, PluginPackage pluginPackage, String str2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: discovery");
            }
            if ((i & 4) != 0) {
                pluginPackage = null;
            }
            if ((i & 8) != 0) {
                str2 = null;
            }
            return pluginDiscovery.discovery(context, str, pluginPackage, str2);
        }
    }

    List discovery(Context context, String str, PluginPackage pluginPackage, String str2);
}
