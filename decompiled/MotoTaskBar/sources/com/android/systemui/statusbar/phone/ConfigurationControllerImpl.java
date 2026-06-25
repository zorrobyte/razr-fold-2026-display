package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.LocaleList;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConfigurationControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConfigurationControllerImpl implements ConfigurationController {
    private final Context context;
    private int density;
    private float fontScale;
    private final boolean inCarMode;
    private final Configuration lastConfig;
    private int layoutDirection;
    private final List listeners;
    private LocaleList localeList;
    private Rect maxBounds;
    private int orientation;
    private int smallestScreenWidth;
    private int uiMode;

    public ConfigurationControllerImpl(Context context) {
        context.getClass();
        this.listeners = new ArrayList();
        this.lastConfig = new Configuration();
        this.maxBounds = new Rect();
        Configuration configuration = context.getResources().getConfiguration();
        this.context = context;
        this.fontScale = configuration.fontScale;
        this.density = configuration.densityDpi;
        this.smallestScreenWidth = configuration.smallestScreenWidthDp;
        this.maxBounds.set(configuration.windowConfiguration.getMaxBounds());
        int i = configuration.uiMode;
        this.inCarMode = (i & 15) == 3;
        this.uiMode = i & 48;
        this.localeList = configuration.getLocales();
        this.layoutDirection = configuration.getLayoutDirection();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(ConfigurationController.ConfigurationListener configurationListener) {
        configurationListener.getClass();
        this.listeners.add(configurationListener);
        configurationListener.onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController
    public String getNightModeName() {
        int i = this.uiMode & 48;
        return i != 0 ? i != 16 ? i != 32 ? "err" : "night" : "day" : "undefined";
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController
    public void onConfigurationChanged(Configuration configuration) {
        configuration.getClass();
        ArrayList arrayList = new ArrayList(this.listeners);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
            if (this.listeners.contains(configurationListener)) {
                configurationListener.onConfigChanged(configuration);
            }
        }
        float f = configuration.fontScale;
        int i3 = configuration.densityDpi;
        int i4 = configuration.uiMode & 48;
        boolean z = i4 != this.uiMode;
        if (i3 != this.density || f != this.fontScale || (this.inCarMode && z)) {
            int size2 = arrayList.size();
            int i5 = 0;
            while (i5 < size2) {
                Object obj2 = arrayList.get(i5);
                i5++;
                ConfigurationController.ConfigurationListener configurationListener2 = (ConfigurationController.ConfigurationListener) obj2;
                if (this.listeners.contains(configurationListener2)) {
                    configurationListener2.onDensityOrFontScaleChanged();
                }
            }
            this.density = i3;
            this.fontScale = f;
        }
        int i6 = configuration.smallestScreenWidthDp;
        if (i6 != this.smallestScreenWidth) {
            this.smallestScreenWidth = i6;
            int size3 = arrayList.size();
            int i7 = 0;
            while (i7 < size3) {
                Object obj3 = arrayList.get(i7);
                i7++;
                ConfigurationController.ConfigurationListener configurationListener3 = (ConfigurationController.ConfigurationListener) obj3;
                if (this.listeners.contains(configurationListener3)) {
                    configurationListener3.onSmallestScreenWidthChanged();
                }
            }
        }
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        maxBounds.getClass();
        if (!Intrinsics.areEqual(maxBounds, this.maxBounds)) {
            this.maxBounds.set(maxBounds);
            int size4 = arrayList.size();
            int i8 = 0;
            while (i8 < size4) {
                Object obj4 = arrayList.get(i8);
                i8++;
                ConfigurationController.ConfigurationListener configurationListener4 = (ConfigurationController.ConfigurationListener) obj4;
                if (this.listeners.contains(configurationListener4)) {
                    configurationListener4.onMaxBoundsChanged();
                }
            }
        }
        LocaleList locales = configuration.getLocales();
        locales.getClass();
        if (!Intrinsics.areEqual(locales, this.localeList)) {
            this.localeList = locales;
            int size5 = arrayList.size();
            int i9 = 0;
            while (i9 < size5) {
                Object obj5 = arrayList.get(i9);
                i9++;
                ConfigurationController.ConfigurationListener configurationListener5 = (ConfigurationController.ConfigurationListener) obj5;
                if (this.listeners.contains(configurationListener5)) {
                    configurationListener5.onLocaleListChanged();
                }
            }
        }
        if (z) {
            this.context.getTheme().applyStyle(this.context.getThemeResId(), true);
            this.uiMode = i4;
            int size6 = arrayList.size();
            int i10 = 0;
            while (i10 < size6) {
                Object obj6 = arrayList.get(i10);
                i10++;
                ConfigurationController.ConfigurationListener configurationListener6 = (ConfigurationController.ConfigurationListener) obj6;
                if (this.listeners.contains(configurationListener6)) {
                    configurationListener6.onUiModeChanged();
                }
            }
        }
        if (this.layoutDirection != configuration.getLayoutDirection()) {
            this.layoutDirection = configuration.getLayoutDirection();
            int size7 = arrayList.size();
            int i11 = 0;
            while (i11 < size7) {
                Object obj7 = arrayList.get(i11);
                i11++;
                ConfigurationController.ConfigurationListener configurationListener7 = (ConfigurationController.ConfigurationListener) obj7;
                if (this.listeners.contains(configurationListener7)) {
                    configurationListener7.onLayoutDirectionChanged(this.layoutDirection == 1);
                }
            }
        }
        if ((this.lastConfig.updateFrom(configuration) & Integer.MIN_VALUE) != 0) {
            int size8 = arrayList.size();
            int i12 = 0;
            while (i12 < size8) {
                Object obj8 = arrayList.get(i12);
                i12++;
                ConfigurationController.ConfigurationListener configurationListener8 = (ConfigurationController.ConfigurationListener) obj8;
                if (this.listeners.contains(configurationListener8)) {
                    configurationListener8.onThemeChanged();
                }
            }
        }
        int i13 = configuration.orientation;
        if (this.orientation != i13) {
            this.orientation = i13;
            int size9 = arrayList.size();
            while (i < size9) {
                Object obj9 = arrayList.get(i);
                i++;
                ConfigurationController.ConfigurationListener configurationListener9 = (ConfigurationController.ConfigurationListener) obj9;
                if (this.listeners.contains(configurationListener9)) {
                    configurationListener9.onOrientationChanged(this.orientation);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(ConfigurationController.ConfigurationListener configurationListener) {
        configurationListener.getClass();
        this.listeners.remove(configurationListener);
    }
}
