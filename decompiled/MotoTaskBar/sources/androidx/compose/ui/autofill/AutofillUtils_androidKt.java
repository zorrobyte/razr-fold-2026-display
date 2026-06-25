package androidx.compose.ui.autofill;

import androidx.collection.MutableIntSet;

/* JADX INFO: compiled from: AutofillUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AutofillUtils_androidKt {
    public static final boolean containsAll(MutableIntSet mutableIntSet, MutableIntSet mutableIntSet2) {
        int[] iArr = mutableIntSet2.elements;
        long[] jArr = mutableIntSet2.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return true;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128 && !mutableIntSet.contains(iArr[(i << 3) + i3])) {
                        return false;
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return true;
                }
            }
            if (i == length) {
                return true;
            }
            i++;
        }
    }

    public static final void copyFrom(MutableIntSet mutableIntSet, MutableIntSet mutableIntSet2) {
        mutableIntSet.clear();
        mutableIntSet.addAll(mutableIntSet2);
    }
}
