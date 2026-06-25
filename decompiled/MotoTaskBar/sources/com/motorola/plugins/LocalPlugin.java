package com.motorola.plugins;

import android.app.Activity;
import android.view.ViewGroup;
import com.motorola.plugin.sdk.Plugin;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(action = LocalPlugin.ACTION, version = 1)
public interface LocalPlugin extends Plugin {
    public static final String ACTION = "com.motorola.plugin.action.PLUGIN_LOCAL_ACTIONS";
    public static final int VERSION = 1;

    void click();

    void setup(ViewGroup viewGroup, Activity activity);
}
