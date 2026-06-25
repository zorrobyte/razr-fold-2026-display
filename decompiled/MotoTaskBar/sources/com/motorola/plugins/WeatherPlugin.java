package com.motorola.plugins;

import com.motorola.plugin.sdk.annotations.ProvidesInterface;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(action = WeatherPlugin.ACTION, version = 3)
public interface WeatherPlugin extends ViewTypePlugin {
    public static final String ACTION = "com.motorola.plugin.action.PLUGIN_WEATHER";
    public static final int VERSION = 3;
}
