package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import com.motorola.taskbar.R$string;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface QSHost {
    static List getDefaultSpecs(Resources resources) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(resources.getString(R$string.quick_settings_tiles_default).split(",")));
        return arrayList;
    }

    Context getContext();

    Collection getTiles();
}
