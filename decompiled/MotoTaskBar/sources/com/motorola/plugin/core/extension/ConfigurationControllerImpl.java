package com.motorola.plugin.core.extension;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.BitFlagKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ConfigurationControllerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ConfigurationControllerImpl implements ConfigurationController, ComponentCallbacks2 {
    private final Context context;
    private int density;
    private float fontScale;
    private final boolean inCarMode;
    private final Configuration lastConfig;
    private int layoutDirection;
    private final List listeners;
    private LocaleList localeList;
    private int smallestScreenWidth;
    private int uiMode;

    public ConfigurationControllerImpl(@DisplayContext Context context) {
        context.getClass();
        this.context = context;
        this.listeners = new ArrayList();
        this.lastConfig = new Configuration();
        Configuration configuration = context.getResources().getConfiguration();
        this.fontScale = configuration.fontScale;
        this.density = configuration.densityDpi;
        this.smallestScreenWidth = configuration.smallestScreenWidthDp;
        int i = configuration.uiMode;
        this.inCarMode = (i & 15) == 3;
        this.uiMode = i & 48;
        this.localeList = configuration.getLocales();
        this.layoutDirection = configuration.getLayoutDirection();
        context.registerComponentCallbacks(this);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController
    public void addCallback(ConfigurationController.ConfigurationListener configurationListener) {
        configurationListener.getClass();
        this.listeners.add(configurationListener);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.listeners.clear();
        this.context.unregisterComponentCallbacks(this);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController
    public boolean isLayoutRtl() {
        return this.layoutDirection == 1;
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController
    public void notifyThemeChanged() {
        ArrayList arrayList = new ArrayList(this.listeners);
        Iterator it = SequencesKt.filter(CollectionsKt.asSequence(arrayList), new Function1() { // from class: com.motorola.plugin.core.extension.ConfigurationControllerImpl.notifyThemeChanged.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((ConfigurationController.ConfigurationListener) obj));
            }

            public final boolean invoke(ConfigurationController.ConfigurationListener configurationListener) {
                return ConfigurationControllerImpl.this.listeners.contains(configurationListener);
            }
        }).iterator();
        while (it.hasNext()) {
            ((ConfigurationController.ConfigurationListener) it.next()).onConfigChanged(this.lastConfig, BitFlag.Companion.wrap(32));
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController
    public void onConfigurationChanged(Configuration configuration) {
        configuration.getClass();
        BitFlag bitFlagEmpty = BitFlag.Companion.empty();
        float f = configuration.fontScale;
        int i = configuration.densityDpi;
        int i2 = configuration.uiMode & 48;
        boolean z = i2 != this.uiMode;
        if (i != this.density || f != this.fontScale || (this.inCarMode && z)) {
            BitFlagKt.plusAssign(bitFlagEmpty, 2);
            this.density = i;
            this.fontScale = f;
        }
        int i3 = configuration.smallestScreenWidthDp;
        if (i3 != this.smallestScreenWidth) {
            this.smallestScreenWidth = i3;
            BitFlagKt.plusAssign(bitFlagEmpty, 4);
        }
        LocaleList locales = configuration.getLocales();
        locales.getClass();
        if (!Intrinsics.areEqual(locales, this.localeList)) {
            this.localeList = locales;
            BitFlagKt.plusAssign(bitFlagEmpty, 64);
        }
        if (z) {
            this.uiMode = i2;
            BitFlagKt.plusAssign(bitFlagEmpty, 16);
        }
        if (this.layoutDirection != configuration.getLayoutDirection()) {
            this.layoutDirection = configuration.getLayoutDirection();
            BitFlagKt.plusAssign(bitFlagEmpty, 128);
        }
        if ((this.lastConfig.updateFrom(configuration) & Integer.MIN_VALUE) != 0) {
            BitFlagKt.plusAssign(bitFlagEmpty, 8);
        }
        if (bitFlagEmpty.getEmpty()) {
            return;
        }
        Iterator it = SequencesKt.filter(CollectionsKt.asSequence(CollectionsKt.toList(this.listeners)), new Function1() { // from class: com.motorola.plugin.core.extension.ConfigurationControllerImpl.onConfigurationChanged.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((ConfigurationController.ConfigurationListener) obj));
            }

            public final boolean invoke(ConfigurationController.ConfigurationListener configurationListener) {
                configurationListener.getClass();
                return ConfigurationControllerImpl.this.listeners.contains(configurationListener);
            }
        }).iterator();
        while (it.hasNext()) {
            ((ConfigurationController.ConfigurationListener) it.next()).onConfigChanged(configuration, bitFlagEmpty);
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        ArrayList arrayList = new ArrayList(this.listeners);
        Iterator it = SequencesKt.filter(CollectionsKt.asSequence(arrayList), new Function1() { // from class: com.motorola.plugin.core.extension.ConfigurationControllerImpl.onLowMemory.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((ConfigurationController.ConfigurationListener) obj));
            }

            public final boolean invoke(ConfigurationController.ConfigurationListener configurationListener) {
                return ConfigurationControllerImpl.this.listeners.contains(configurationListener);
            }
        }).iterator();
        while (it.hasNext()) {
            ((ConfigurationController.ConfigurationListener) it.next()).onLowMemory();
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        ArrayList arrayList = new ArrayList(this.listeners);
        Iterator it = SequencesKt.filter(CollectionsKt.asSequence(arrayList), new Function1() { // from class: com.motorola.plugin.core.extension.ConfigurationControllerImpl.onTrimMemory.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((ConfigurationController.ConfigurationListener) obj));
            }

            public final boolean invoke(ConfigurationController.ConfigurationListener configurationListener) {
                return ConfigurationControllerImpl.this.listeners.contains(configurationListener);
            }
        }).iterator();
        while (it.hasNext()) {
            ((ConfigurationController.ConfigurationListener) it.next()).onTrimMemory(i);
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController
    public void removeCallback(ConfigurationController.ConfigurationListener configurationListener) {
        configurationListener.getClass();
        this.listeners.remove(configurationListener);
    }
}
