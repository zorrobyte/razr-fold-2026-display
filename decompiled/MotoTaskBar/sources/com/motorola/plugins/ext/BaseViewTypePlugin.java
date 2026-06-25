package com.motorola.plugins.ext;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.motorola.plugin.sdk.channel.IRemoteChannel;
import com.motorola.plugins.ViewTypePlugin;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseViewTypePlugin extends BasePlugin implements ViewTypePlugin {
    protected ViewTypePlugin.IIntentDelegate mIntentDelegate;

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // com.motorola.plugins.ext.BasePlugin, com.motorola.plugin.sdk.Plugin
    public void onCreate(Context context, Context context2, IRemoteChannel iRemoteChannel) {
        super.onCreate(context, context2, iRemoteChannel);
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public Animator onCreateAnimator(int i, boolean z, int i2) {
        return null;
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    @Override // com.motorola.plugin.sdk.Plugin
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onDestroyView() {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onMultiWindowModeChanged(boolean z) {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onPictureInPictureModeChanged(boolean z) {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onPluginHidden() {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onPluginShown() {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onRequestRefresh(boolean z, ViewTypePlugin.OnRefreshCallback onRefreshCallback) {
        if (onRefreshCallback != null) {
            onRefreshCallback.onRefresh(0L, true);
        }
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public abstract void onViewCreated(View view, Bundle bundle);

    @Override // com.motorola.plugins.ViewTypePlugin
    public void onViewStateRestored(Bundle bundle) {
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public void setIntentDelegate(ViewTypePlugin.IIntentDelegate iIntentDelegate) {
        this.mIntentDelegate = iIntentDelegate;
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public boolean startActivity(Intent intent) {
        return startActivity(intent, null);
    }

    @Override // com.motorola.plugins.ViewTypePlugin
    public boolean startActivity(Intent intent, Bundle bundle) {
        ViewTypePlugin.IIntentDelegate iIntentDelegate = this.mIntentDelegate;
        if (iIntentDelegate != null) {
            return iIntentDelegate.startActivity(intent, bundle);
        }
        getContext().startActivity(intent, bundle);
        return true;
    }
}
