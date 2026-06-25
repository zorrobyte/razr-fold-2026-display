package com.motorola.taskbar.settings.wallpaper.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThirdPartyAppCategory.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ThirdPartyAppCategory extends Category {
    public static final Companion Companion = new Companion(null);
    private final ResolveInfo mResolveInfo;
    private final int overlayIconSizeDp;

    /* JADX INFO: compiled from: ThirdPartyAppCategory.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getAll(Context context, int i, List list) {
            context.getClass();
            list.getClass();
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.SET_WALLPAPER"), 0);
            listQueryIntentActivities.getClass();
            ArrayList arrayList = new ArrayList();
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            List<ResolveInfo> listQueryIntentActivities2 = context.getPackageManager().queryIntentActivities(intent, 0);
            listQueryIntentActivities2.getClass();
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                String packageName = new ComponentName(activityInfo.packageName, activityInfo.name).getPackageName();
                packageName.getClass();
                if (!list.contains(packageName) && !Intrinsics.areEqual(packageName, context.getPackageName())) {
                    Iterator<ResolveInfo> it = listQueryIntentActivities2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            arrayList.add(new ThirdPartyAppCategory(context, resolveInfo, "third_party_app_wallpaper_collection_id_" + packageName, i));
                            break;
                        }
                        if (Intrinsics.areEqual(packageName, it.next().activityInfo.packageName)) {
                            break;
                        }
                    }
                }
            }
            return arrayList;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThirdPartyAppCategory(Context context, ResolveInfo resolveInfo, String str, int i) {
        super(resolveInfo.loadLabel(context.getPackageManager()).toString(), str, i);
        context.getClass();
        resolveInfo.getClass();
        str.getClass();
        this.mResolveInfo = resolveInfo;
        this.overlayIconSizeDp = 48;
    }
}
