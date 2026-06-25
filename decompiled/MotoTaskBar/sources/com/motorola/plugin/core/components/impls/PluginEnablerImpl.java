package com.motorola.plugin.core.components.impls;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginEnabler;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PluginEnablerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginEnablerImpl implements PluginEnabler {
    public static final Companion Companion = new Companion(null);
    private static final String PLUGIN_PREF_NAME = "plugin_enable_prefs";
    private final SharedPreferences mSharedPrefs;

    /* JADX INFO: compiled from: PluginEnablerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PluginEnablerImpl(@AppContext Context context) {
        context.getClass();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLUGIN_PREF_NAME, 0);
        sharedPreferences.getClass();
        this.mSharedPrefs = sharedPreferences;
    }

    private final int getState(ComponentName componentName) {
        return this.mSharedPrefs.getInt(componentName.flattenToString(), 0);
    }

    private final void logReason(final ComponentName componentName, final int i) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginEnablerImpl.logReason.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Disable plugin: " + componentName + " for reason " + PluginEnabler.Companion.disableReasonToString(i);
            }
        }, 60, null);
    }

    private final void setState(ComponentName componentName, @PluginEnabler.DisableReason int i) {
        SharedPreferences.Editor editorEdit = this.mSharedPrefs.edit();
        editorEdit.getClass();
        editorEdit.putInt(componentName.flattenToString(), i);
        editorEdit.apply();
    }

    @Override // com.motorola.plugin.core.components.PluginEnabler
    public int getDisableReason(ComponentName componentName) {
        componentName.getClass();
        return getState(componentName);
    }

    @Override // com.motorola.plugin.core.components.PluginEnabler
    public boolean isEnabled(ComponentName componentName) {
        componentName.getClass();
        return getState(componentName) == 0;
    }

    @Override // com.motorola.plugin.core.components.PluginEnabler
    public void setDisabled(ComponentName componentName, int i) {
        componentName.getClass();
        logReason(componentName, i);
        setState(componentName, i);
    }

    @Override // com.motorola.plugin.core.components.PluginEnabler
    public void setEnabled(ComponentName componentName) {
        componentName.getClass();
        setState(componentName, 0);
    }
}
