package com.motorola.laptoppanel.util;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LaptopPrefs.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPrefs {
    private final SharedPreferences prefs;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopPrefs.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LaptopPrefs(Context context) {
        context.getClass();
        this.prefs = context.getSharedPreferences("laptop_panel_prefs", 0);
    }

    public final String getInteractedApps() {
        return this.prefs.getString("interacted_apps_list", "");
    }

    public final long getScreenOnTime() {
        return this.prefs.getLong("screen_on_time", 0L);
    }

    public final int getScrollCount() {
        return this.prefs.getInt("scroll_count", 0);
    }

    public final int getTapCount() {
        return this.prefs.getInt("tap_count", 0);
    }

    public final int getToolbarTapCount() {
        return this.prefs.getInt("toolbar_tap_count", 0);
    }

    public final boolean isGuideShown() {
        return this.prefs.getBoolean("pref_guide_shown", true);
    }

    public final void resetDailyStats() {
        this.prefs.edit().putLong("screen_on_time", 0L).putString("interacted_apps_list", "").putInt("tap_count", 0).putInt("scroll_count", 0).putInt("toolbar_tap_count", 0).apply();
    }

    public final void setGuideShown(boolean z) {
        this.prefs.edit().putBoolean("pref_guide_shown", z).apply();
    }

    public final void setInteractedApps(String str) {
        this.prefs.edit().putString("interacted_apps_list", str).apply();
    }

    public final void setScreenOnTime(long j) {
        this.prefs.edit().putLong("screen_on_time", j).apply();
    }

    public final void setScrollCount(int i) {
        this.prefs.edit().putInt("scroll_count", i).apply();
    }

    public final void setTapCount(int i) {
        this.prefs.edit().putInt("tap_count", i).apply();
    }

    public final void setToolbarTapCount(int i) {
        this.prefs.edit().putInt("toolbar_tap_count", i).apply();
    }
}
