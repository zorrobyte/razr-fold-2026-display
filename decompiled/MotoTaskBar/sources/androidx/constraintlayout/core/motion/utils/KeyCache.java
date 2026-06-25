package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class KeyCache {
    HashMap mMap = new HashMap();

    public float getFloatValue(Object obj, String str, int i) {
        HashMap map;
        float[] fArr;
        if (this.mMap.containsKey(obj) && (map = (HashMap) this.mMap.get(obj)) != null && map.containsKey(str) && (fArr = (float[]) map.get(str)) != null && fArr.length > i) {
            return fArr[i];
        }
        return Float.NaN;
    }

    public void setFloatValue(Object obj, String str, int i, float f) {
        if (!this.mMap.containsKey(obj)) {
            HashMap map = new HashMap();
            float[] fArr = new float[i + 1];
            fArr[i] = f;
            map.put(str, fArr);
            this.mMap.put(obj, map);
            return;
        }
        HashMap map2 = (HashMap) this.mMap.get(obj);
        if (map2 == null) {
            map2 = new HashMap();
        }
        if (!map2.containsKey(str)) {
            float[] fArr2 = new float[i + 1];
            fArr2[i] = f;
            map2.put(str, fArr2);
            this.mMap.put(obj, map2);
            return;
        }
        float[] fArrCopyOf = (float[]) map2.get(str);
        if (fArrCopyOf == null) {
            fArrCopyOf = new float[0];
        }
        if (fArrCopyOf.length <= i) {
            fArrCopyOf = Arrays.copyOf(fArrCopyOf, i + 1);
        }
        fArrCopyOf[i] = f;
        map2.put(str, fArrCopyOf);
    }
}
