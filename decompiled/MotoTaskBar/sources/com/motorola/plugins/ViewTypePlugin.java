package com.motorola.plugins;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.motorola.plugin.sdk.Plugin;

/* JADX INFO: loaded from: classes2.dex */
public interface ViewTypePlugin extends Plugin {

    public interface IIntentDelegate {
        boolean startActivity(Intent intent, Bundle bundle);
    }

    public interface OnRefreshCallback {
        void onRefresh(long j, boolean z);
    }

    Context getContext();

    Context getHostContext();

    void onConfigurationChanged(Configuration configuration);

    Animator onCreateAnimator(int i, boolean z, int i2);

    View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    void onDestroyView();

    void onMultiWindowModeChanged(boolean z);

    void onPictureInPictureModeChanged(boolean z);

    void onPluginHidden();

    void onPluginShown();

    void onRequestRefresh(boolean z, OnRefreshCallback onRefreshCallback);

    void onSaveInstanceState(Bundle bundle);

    void onViewCreated(View view, Bundle bundle);

    void onViewStateRestored(Bundle bundle);

    void setIntentDelegate(IIntentDelegate iIntentDelegate);

    boolean startActivity(Intent intent);

    boolean startActivity(Intent intent, Bundle bundle);
}
