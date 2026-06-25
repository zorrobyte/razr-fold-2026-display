package com.google.android.setupcompat.internal;

import android.os.BaseBundle;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import com.google.android.setupcompat.util.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class PersistableBundles {
    private static final Logger LOG = new Logger("PersistableBundles");

    public static PersistableBundle assertIsValid(PersistableBundle persistableBundle) {
        Preconditions.checkNotNull(persistableBundle, "PersistableBundle cannot be null!");
        for (String str : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(str);
            Preconditions.checkArgument(isSupportedDataType(obj), String.format("Unknown/unsupported data type [%s] for key %s", obj, str));
        }
        return persistableBundle;
    }

    public static boolean equals(PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
        return persistableBundle == persistableBundle2 || toMap(persistableBundle).equals(toMap(persistableBundle2));
    }

    private static boolean isSupportedDataType(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static PersistableBundle mergeBundles(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, PersistableBundle... persistableBundleArr) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.addAll(Arrays.asList(persistableBundle, persistableBundle2));
        Collections.addAll(arrayList, persistableBundleArr);
        PersistableBundle persistableBundle3 = new PersistableBundle();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            PersistableBundle persistableBundle4 = (PersistableBundle) obj;
            Iterator<String> it = persistableBundle4.keySet().iterator();
            while (it.hasNext()) {
                Preconditions.checkArgument(!persistableBundle3.containsKey(r4), String.format("Found duplicate key [%s] while attempting to merge bundles.", it.next()));
            }
            persistableBundle3.putAll(persistableBundle4);
        }
        return persistableBundle3;
    }

    public static Bundle toBundle(PersistableBundle persistableBundle) {
        Bundle bundle = new Bundle();
        bundle.putAll(persistableBundle);
        return bundle;
    }

    private static ArrayMap toMap(BaseBundle baseBundle) {
        if (baseBundle == null || baseBundle.isEmpty()) {
            return new ArrayMap(0);
        }
        ArrayMap arrayMap = new ArrayMap(baseBundle.size());
        for (String str : baseBundle.keySet()) {
            Object obj = baseBundle.get(str);
            if (isSupportedDataType(obj)) {
                arrayMap.put(str, baseBundle.get(str));
            } else {
                LOG.w(String.format("Unknown/unsupported data type [%s] for key %s", obj, str));
            }
        }
        return arrayMap;
    }
}
