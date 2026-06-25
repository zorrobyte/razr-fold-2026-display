package com.motorola.plugins.ext;

import android.content.Context;
import com.motorola.plugin.sdk.Plugin;
import com.motorola.plugin.sdk.channel.IRemoteChannel;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BasePlugin implements Plugin {
    private IRemoteChannel myChannel;
    private Context myHostContext;
    private Context myPluginContext;

    public IRemoteChannel getChannel() {
        return this.myChannel;
    }

    public Context getContext() {
        return this.myPluginContext;
    }

    public Context getHostContext() {
        return this.myHostContext;
    }

    @Override // com.motorola.plugin.sdk.Plugin
    public void onCreate(Context context, Context context2, IRemoteChannel iRemoteChannel) {
        super.onCreate(context, context2, iRemoteChannel);
        this.myHostContext = context;
        this.myPluginContext = context2;
        this.myChannel = iRemoteChannel;
    }
}
