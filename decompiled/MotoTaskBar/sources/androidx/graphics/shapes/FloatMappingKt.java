package androidx.graphics.shapes;

import androidx.collection.FloatList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.IntIterator;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: FloatMapping.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FloatMappingKt {
    public static final float linearMap(FloatList floatList, FloatList floatList2, float f) {
        floatList.getClass();
        floatList2.getClass();
        if (0.0f > f || f > 1.0f) {
            throw new IllegalArgumentException(("Invalid progress: " + f).toString());
        }
        Iterator it = RangesKt.until(0, floatList._size).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            int i = iNextInt + 1;
            if (progressInRange(f, floatList.get(iNextInt), floatList.get(i % floatList.getSize()))) {
                int size = i % floatList.getSize();
                float fPositiveModulo = Utils.positiveModulo(floatList.get(size) - floatList.get(iNextInt), 1.0f);
                return Utils.positiveModulo(floatList2.get(iNextInt) + (Utils.positiveModulo(floatList2.get(size) - floatList2.get(iNextInt), 1.0f) * (fPositiveModulo < 0.001f ? 0.5f : Utils.positiveModulo(f - floatList.get(iNextInt), 1.0f) / fPositiveModulo)), 1.0f);
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static final float progressDistance(float f, float f2) {
        float fAbs = Math.abs(f - f2);
        return Math.min(fAbs, 1.0f - fAbs);
    }

    public static final boolean progressInRange(float f, float f2, float f3) {
        return f3 >= f2 ? f2 <= f && f <= f3 : f >= f2 || f <= f3;
    }

    public static final void validateProgress(FloatList floatList) {
        floatList.getClass();
        float fLast = floatList.last();
        int size = floatList.getSize();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            float f = floatList.get(i);
            if (f < 0.0f || f >= 1.0f) {
                throw new IllegalArgumentException(("FloatMapping - Progress outside of range: " + FloatList.joinToString$default(floatList, null, null, null, 0, null, 31, null)).toString());
            }
            if (progressDistance(f, fLast) <= 1.0E-4f) {
                throw new IllegalArgumentException(("FloatMapping - Progress repeats a value: " + FloatList.joinToString$default(floatList, null, null, null, 0, null, 31, null)).toString());
            }
            if (f < fLast && (i2 = i2 + 1) > 1) {
                throw new IllegalArgumentException(("FloatMapping - Progress wraps more than once: " + FloatList.joinToString$default(floatList, null, null, null, 0, null, 31, null)).toString());
            }
            i++;
            size = size;
            floatList = floatList;
            fLast = f;
        }
    }
}
