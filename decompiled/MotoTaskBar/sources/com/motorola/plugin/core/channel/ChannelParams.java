package com.motorola.plugin.core.channel;

import android.content.ComponentName;
import android.content.Context;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.discovery.PluginType;
import com.motorola.plugin.core.misc.DeviceState;
import com.motorola.plugin.sdk.channel.ClientId;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ChannelParams.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ChannelParams {
    private final String action;
    private final Long bindServiceTimeout;
    private final ClientId clientId;
    private final Context context;
    private final DeviceState deviceState;
    private final ClassLoader pluginClassLoader;
    private final PluginEvent pluginEvent;
    private final PluginType pluginType;
    private final ComponentName serviceComponent;
    private final Long transferTimeoutPerAction;

    public ChannelParams(@AppContext Context context, String str, ComponentName componentName, ClassLoader classLoader, ClientId clientId, PluginType pluginType, PluginEvent pluginEvent, DeviceState deviceState, Long l, Long l2) {
        context.getClass();
        str.getClass();
        componentName.getClass();
        classLoader.getClass();
        clientId.getClass();
        pluginType.getClass();
        pluginEvent.getClass();
        deviceState.getClass();
        this.context = context;
        this.action = str;
        this.serviceComponent = componentName;
        this.pluginClassLoader = classLoader;
        this.clientId = clientId;
        this.pluginType = pluginType;
        this.pluginEvent = pluginEvent;
        this.deviceState = deviceState;
        this.bindServiceTimeout = l;
        this.transferTimeoutPerAction = l2;
    }

    public /* synthetic */ ChannelParams(Context context, String str, ComponentName componentName, ClassLoader classLoader, ClientId clientId, PluginType pluginType, PluginEvent pluginEvent, DeviceState deviceState, Long l, Long l2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, componentName, classLoader, clientId, pluginType, pluginEvent, deviceState, (i & 256) != 0 ? null : l, (i & 512) != 0 ? null : l2);
    }

    public final String getAction() {
        return this.action;
    }

    public final Long getBindServiceTimeout() {
        return this.bindServiceTimeout;
    }

    public final ClientId getClientId() {
        return this.clientId;
    }

    public final Context getContext() {
        return this.context;
    }

    public final DeviceState getDeviceState() {
        return this.deviceState;
    }

    public final ClassLoader getPluginClassLoader() {
        return this.pluginClassLoader;
    }

    public final PluginEvent getPluginEvent() {
        return this.pluginEvent;
    }

    public final PluginType getPluginType() {
        return this.pluginType;
    }

    public final ComponentName getServiceComponent() {
        return this.serviceComponent;
    }

    public final Long getTransferTimeoutPerAction() {
        return this.transferTimeoutPerAction;
    }
}
