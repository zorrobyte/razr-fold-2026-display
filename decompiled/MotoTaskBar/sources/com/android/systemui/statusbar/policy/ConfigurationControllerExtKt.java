package com.android.systemui.statusbar.policy;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: ConfigurationControllerExt.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ConfigurationControllerExtKt {
    public static final Flow getOnDensityOrFontScaleChanged(ConfigurationController configurationController) {
        configurationController.getClass();
        return ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1(configurationController, null));
    }

    public static final Flow getOnThemeChanged(ConfigurationController configurationController) {
        configurationController.getClass();
        return ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new ConfigurationControllerExtKt$onThemeChanged$1(configurationController, null));
    }
}
