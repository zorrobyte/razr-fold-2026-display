package com.motorola.laptoppanel.checkin;

import android.content.ContentResolver;
import android.content.Context;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.laptoppanel.util.LaptopPrefs;
import com.motorola.laptoppanel.util.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DailyStatsCheckin.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DailyStatsCheckin extends BaseCheckinEvent {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: DailyStatsCheckin.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DailyStatsCheckin() {
        super("MOT_FOLDABLE", "LaptopModeDailyStats", "1.0", 0L, 8, null);
    }

    public final void collectAndPublish(Context context) {
        context.getClass();
        ContentResolver contentResolver = context.getContentResolver();
        LaptopPrefs laptopPrefs = new LaptopPrefs(context);
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, -1);
        String str = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
        str.getClass();
        setValue("ddate", str);
        setValue("md_ltp", 1 == MotorolaSettings.Secure.getInt(contentResolver, "laptop_panel_enabled", 1) ? "1" : "0");
        String string = MotorolaSettings.Secure.getString(contentResolver, "laptop_panel_disabled_apps_list");
        if (string != null && string.length() != 0) {
            List listSplit$default = StringsKt.split$default((CharSequence) string, new char[]{';', ','}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(StringsKt.trim((String) it.next()).toString());
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (((String) obj).length() > 0) {
                    arrayList2.add(obj);
                }
            }
            setValue("ap_lnx", CollectionsKt.joinToString$default(arrayList2, ",", null, null, 0, null, null, 62, null));
        }
        long screenOnTime = laptopPrefs.getScreenOnTime() / ((long) 1000);
        if (screenOnTime > 0) {
            setValue("st_lmt", String.valueOf(screenOnTime));
        }
        String interactedApps = laptopPrefs.getInteractedApps();
        if (interactedApps != null && interactedApps.length() != 0) {
            setValue("ap_pad", interactedApps);
        }
        int tapCount = laptopPrefs.getTapCount();
        if (tapCount > 0) {
            setValue("tap_pad", String.valueOf(tapCount));
        }
        int scrollCount = laptopPrefs.getScrollCount();
        if (scrollCount > 0) {
            setValue("scr_pad", String.valueOf(scrollCount));
        }
        int toolbarTapCount = laptopPrefs.getToolbarTapCount();
        if (toolbarTapCount > 0) {
            setValue("tap_bar", String.valueOf(toolbarTapCount));
        }
        contentResolver.getClass();
        publish(contentResolver);
        Logger.INSTANCE.i("DailyStatsCheckin", "Daily stats published.", new Object[0]);
        laptopPrefs.resetDailyStats();
    }
}
