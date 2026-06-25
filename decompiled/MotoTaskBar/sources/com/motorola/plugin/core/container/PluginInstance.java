package com.motorola.plugin.core.container;

import android.content.ComponentName;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.channel.IRemoteChannelExtension;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.VersionInfo;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.Plugin;
import com.motorola.plugin.sdk.channel.ClientId;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginInstance implements ISnapshotAware {
    private final Class implementorPluginClass;
    private final String pluginAction;
    private final ComponentName pluginComponent;
    private final PluginContext pluginContext;
    private final ClientId pluginId;
    private final Plugin pluginInstance;
    private final int pluginVersion;
    private final Class prototypePluginClass;
    private final IRemoteChannelExtension remoteChannel;
    private final VersionInfo versionInfo;

    /* JADX INFO: compiled from: PluginInstance.kt */
    final class PluginInstanceSnapshot extends AbstractSnapshot {
        private String myPluginAction;
        private String myPluginClass;
        public ClientId myPluginId;
        private int myPluginInstance;
        private String myPrototypePluginClass;
        private int myVersion;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginInstanceSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.myPluginAction = "";
            this.myPrototypePluginClass = "";
            this.myPluginClass = "";
            this.myVersion = -1;
        }

        public final String getMyPluginAction() {
            return this.myPluginAction;
        }

        public final String getMyPluginClass() {
            return this.myPluginClass;
        }

        public final ClientId getMyPluginId() {
            ClientId clientId = this.myPluginId;
            if (clientId != null) {
                return clientId;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginId");
            throw null;
        }

        public final int getMyPluginInstance() {
            return this.myPluginInstance;
        }

        public final String getMyPrototypePluginClass() {
            return this.myPrototypePluginClass;
        }

        public final int getMyVersion() {
            return this.myVersion;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.increaseIndent();
            iPrinter.printPair("id", getMyPluginId().uniqueId);
            iPrinter.printPair("package", getMyPluginId().packageName);
            iPrinter.printPair("action", getMyPluginAction()).newLine();
            iPrinter.printPair("prototype", getMyPrototypePluginClass());
            iPrinter.printPair("plugin", getMyPluginClass());
            iPrinter.printHexPair("instance", getMyPluginInstance());
            iPrinter.printPair("version", Integer.valueOf(getMyVersion())).newLine();
            iPrinter.newLine();
            iPrinter.decreaseIndent();
        }

        public final void setMyPluginAction(String str) {
            str.getClass();
            this.myPluginAction = str;
        }

        public final void setMyPluginClass(String str) {
            str.getClass();
            this.myPluginClass = str;
        }

        public final void setMyPluginId(ClientId clientId) {
            clientId.getClass();
            this.myPluginId = clientId;
        }

        public final void setMyPluginInstance(int i) {
            this.myPluginInstance = i;
        }

        public final void setMyPrototypePluginClass(String str) {
            str.getClass();
            this.myPrototypePluginClass = str;
        }

        public final void setMyVersion(int i) {
            this.myVersion = i;
        }
    }

    public PluginInstance(ClientId clientId, Class cls, Class cls2, Plugin plugin, ComponentName componentName, PluginContext pluginContext, VersionInfo versionInfo, String str, IRemoteChannelExtension iRemoteChannelExtension) {
        clientId.getClass();
        cls.getClass();
        cls2.getClass();
        plugin.getClass();
        componentName.getClass();
        pluginContext.getClass();
        versionInfo.getClass();
        str.getClass();
        iRemoteChannelExtension.getClass();
        this.pluginId = clientId;
        this.prototypePluginClass = cls;
        this.implementorPluginClass = cls2;
        this.pluginInstance = plugin;
        this.pluginComponent = componentName;
        this.pluginContext = pluginContext;
        this.versionInfo = versionInfo;
        this.pluginAction = str;
        this.remoteChannel = iRemoteChannelExtension;
        this.pluginVersion = versionInfo.getDefaultVersion();
    }

    public static /* synthetic */ PluginInstance copy$default(PluginInstance pluginInstance, ClientId clientId, Class cls, Class cls2, Plugin plugin, ComponentName componentName, PluginContext pluginContext, VersionInfo versionInfo, String str, IRemoteChannelExtension iRemoteChannelExtension, int i, Object obj) {
        if ((i & 1) != 0) {
            clientId = pluginInstance.pluginId;
        }
        if ((i & 2) != 0) {
            cls = pluginInstance.prototypePluginClass;
        }
        if ((i & 4) != 0) {
            cls2 = pluginInstance.implementorPluginClass;
        }
        if ((i & 8) != 0) {
            plugin = pluginInstance.pluginInstance;
        }
        if ((i & 16) != 0) {
            componentName = pluginInstance.pluginComponent;
        }
        if ((i & 32) != 0) {
            pluginContext = pluginInstance.pluginContext;
        }
        if ((i & 64) != 0) {
            versionInfo = pluginInstance.versionInfo;
        }
        if ((i & 128) != 0) {
            str = pluginInstance.pluginAction;
        }
        if ((i & 256) != 0) {
            iRemoteChannelExtension = pluginInstance.remoteChannel;
        }
        String str2 = str;
        IRemoteChannelExtension iRemoteChannelExtension2 = iRemoteChannelExtension;
        PluginContext pluginContext2 = pluginContext;
        VersionInfo versionInfo2 = versionInfo;
        ComponentName componentName2 = componentName;
        Class cls3 = cls2;
        return pluginInstance.copy(clientId, cls, cls3, plugin, componentName2, pluginContext2, versionInfo2, str2, iRemoteChannelExtension2);
    }

    public final ClientId component1() {
        return this.pluginId;
    }

    public final Class component2() {
        return this.prototypePluginClass;
    }

    public final Class component3() {
        return this.implementorPluginClass;
    }

    public final Plugin component4() {
        return this.pluginInstance;
    }

    public final ComponentName component5() {
        return this.pluginComponent;
    }

    public final PluginContext component6() {
        return this.pluginContext;
    }

    public final VersionInfo component7() {
        return this.versionInfo;
    }

    public final String component8() {
        return this.pluginAction;
    }

    public final IRemoteChannelExtension component9() {
        return this.remoteChannel;
    }

    public final PluginInstance copy(ClientId clientId, Class cls, Class cls2, Plugin plugin, ComponentName componentName, PluginContext pluginContext, VersionInfo versionInfo, String str, IRemoteChannelExtension iRemoteChannelExtension) {
        clientId.getClass();
        cls.getClass();
        cls2.getClass();
        plugin.getClass();
        componentName.getClass();
        pluginContext.getClass();
        versionInfo.getClass();
        str.getClass();
        iRemoteChannelExtension.getClass();
        return new PluginInstance(clientId, cls, cls2, plugin, componentName, pluginContext, versionInfo, str, iRemoteChannelExtension);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PluginInstance)) {
            return false;
        }
        PluginInstance pluginInstance = (PluginInstance) obj;
        return Intrinsics.areEqual(this.pluginId, pluginInstance.pluginId) && Intrinsics.areEqual(this.prototypePluginClass, pluginInstance.prototypePluginClass) && Intrinsics.areEqual(this.implementorPluginClass, pluginInstance.implementorPluginClass) && Intrinsics.areEqual(this.pluginInstance, pluginInstance.pluginInstance) && Intrinsics.areEqual(this.pluginComponent, pluginInstance.pluginComponent) && Intrinsics.areEqual(this.pluginContext, pluginInstance.pluginContext) && Intrinsics.areEqual(this.versionInfo, pluginInstance.versionInfo) && Intrinsics.areEqual(this.pluginAction, pluginInstance.pluginAction) && Intrinsics.areEqual(this.remoteChannel, pluginInstance.remoteChannel);
    }

    public final Class getImplementorPluginClass() {
        return this.implementorPluginClass;
    }

    public final String getPluginAction() {
        return this.pluginAction;
    }

    public final ComponentName getPluginComponent() {
        return this.pluginComponent;
    }

    public final PluginContext getPluginContext() {
        return this.pluginContext;
    }

    public final ClientId getPluginId() {
        return this.pluginId;
    }

    public final Plugin getPluginInstance() {
        return this.pluginInstance;
    }

    public final Class getPrototypePluginClass() {
        return this.prototypePluginClass;
    }

    public final IRemoteChannelExtension getRemoteChannel() {
        return this.remoteChannel;
    }

    public final VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public int hashCode() {
        return (((((((((((((((this.pluginId.hashCode() * 31) + this.prototypePluginClass.hashCode()) * 31) + this.implementorPluginClass.hashCode()) * 31) + this.pluginInstance.hashCode()) * 31) + this.pluginComponent.hashCode()) * 31) + this.pluginContext.hashCode()) * 31) + this.versionInfo.hashCode()) * 31) + this.pluginAction.hashCode()) * 31) + this.remoteChannel.hashCode();
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginInstanceSnapshot pluginInstanceSnapshot = new PluginInstanceSnapshot(iSnapshot);
        ClientId clientIdCopy = getPluginId().copy();
        clientIdCopy.getClass();
        pluginInstanceSnapshot.setMyPluginId(clientIdCopy);
        pluginInstanceSnapshot.setMyPluginAction(getPluginAction());
        pluginInstanceSnapshot.setMyPrototypePluginClass(getPrototypePluginClass().getSimpleName());
        pluginInstanceSnapshot.setMyPluginClass(getImplementorPluginClass().getSimpleName());
        pluginInstanceSnapshot.setMyPluginInstance(getPluginInstance().hashCode());
        pluginInstanceSnapshot.setMyVersion(this.pluginVersion);
        return pluginInstanceSnapshot;
    }

    public String toString() {
        return '[' + this.pluginAction + "] : [" + ((Object) this.pluginId.packageName) + "] -> " + ((Object) this.pluginInstance.getClass().getSimpleName()) + '[' + this.pluginVersion + "]@" + ExtensionsKt.hashCodeHex(this.pluginInstance);
    }
}
