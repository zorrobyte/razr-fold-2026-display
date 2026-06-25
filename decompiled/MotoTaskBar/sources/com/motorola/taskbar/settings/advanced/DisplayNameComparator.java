package com.motorola.taskbar.settings.advanced;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import java.text.Collator;
import java.util.Comparator;

/* JADX INFO: loaded from: classes2.dex */
public class DisplayNameComparator implements Comparator {
    private final Collator mCollator;
    private Context mContext;

    public DisplayNameComparator(Context context) {
        Collator collator = Collator.getInstance();
        this.mCollator = collator;
        this.mContext = context;
        collator.setStrength(0);
    }

    @Override // java.util.Comparator
    public final int compare(LauncherActivityInfo launcherActivityInfo, LauncherActivityInfo launcherActivityInfo2) {
        return this.mCollator.compare(launcherActivityInfo.getLabel().toString(), launcherActivityInfo2.getLabel().toString());
    }
}
