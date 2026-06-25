package dagger.internal;

import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes2.dex */
public abstract class DaggerCollections {
    private static int calculateInitialCapacity(int i) {
        if (i < 3) {
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static LinkedHashMap newLinkedHashMapWithExpectedSize(int i) {
        return new LinkedHashMap(calculateInitialCapacity(i));
    }
}
