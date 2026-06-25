package com.motorola.laptoppanel.util;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.motorola.android.provider.MotorolaSettings;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LaptopSettingsObserver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopSettingsObserver extends ContentObserver {
    private static final Uri uriDisabledList;
    private static final Uri uriEnabled;
    private final Function1 onDisabledListChanged;
    private final Function1 onEnabledChanged;
    private boolean registered;
    private final ContentResolver resolver;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopSettingsObserver.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Uri uriFor = MotorolaSettings.Secure.getUriFor("laptop_panel_enabled");
        uriFor.getClass();
        uriEnabled = uriFor;
        Uri uriFor2 = MotorolaSettings.Secure.getUriFor("laptop_panel_disabled_apps_list");
        uriFor2.getClass();
        uriDisabledList = uriFor2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LaptopSettingsObserver(ContentResolver contentResolver, Handler handler, Function1 function1, Function1 function12) {
        super(handler);
        contentResolver.getClass();
        handler.getClass();
        function1.getClass();
        function12.getClass();
        this.resolver = contentResolver;
        this.onEnabledChanged = function1;
        this.onDisabledListChanged = function12;
    }

    private final String getLaptopPanelSettingAppDisabledList() {
        return MotorolaSettings.Secure.getString(this.resolver, "laptop_panel_disabled_apps_list");
    }

    private final boolean isLaptopPanelSettingEnabled() {
        return MotorolaSettings.Secure.getInt(this.resolver, "laptop_panel_enabled", 1) == 1;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        if (Intrinsics.areEqual(uri, uriEnabled)) {
            this.onEnabledChanged.invoke(Boolean.valueOf(isLaptopPanelSettingEnabled()));
        } else if (Intrinsics.areEqual(uri, uriDisabledList)) {
            this.onDisabledListChanged.invoke(getLaptopPanelSettingAppDisabledList());
        } else {
            this.onEnabledChanged.invoke(Boolean.valueOf(isLaptopPanelSettingEnabled()));
            this.onDisabledListChanged.invoke(getLaptopPanelSettingAppDisabledList());
        }
    }

    public final void start() {
        if (this.registered) {
            return;
        }
        this.resolver.registerContentObserver(uriEnabled, false, this);
        this.resolver.registerContentObserver(uriDisabledList, false, this);
        this.registered = true;
        this.onEnabledChanged.invoke(Boolean.valueOf(isLaptopPanelSettingEnabled()));
        this.onDisabledListChanged.invoke(getLaptopPanelSettingAppDisabledList());
    }

    public final void stop() {
        if (this.registered) {
            try {
                this.resolver.unregisterContentObserver(this);
            } catch (Throwable unused) {
            }
            this.registered = false;
        }
    }
}
