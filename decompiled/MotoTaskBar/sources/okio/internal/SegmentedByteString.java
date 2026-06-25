package okio.internal;

import okio.C0207SegmentedByteString;

/* JADX INFO: renamed from: okio.internal.-SegmentedByteString, reason: invalid class name */
/* JADX INFO: compiled from: SegmentedByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SegmentedByteString {
    public static final int binarySearch(int[] iArr, int i, int i2, int i3) {
        iArr.getClass();
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i) {
                i2 = i5 + 1;
            } else {
                if (i6 <= i) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return (-i2) - 1;
    }

    public static final int segment(C0207SegmentedByteString c0207SegmentedByteString, int i) {
        c0207SegmentedByteString.getClass();
        int iBinarySearch = binarySearch(c0207SegmentedByteString.getDirectory$external__okio__android_common__okio_lib(), i + 1, 0, c0207SegmentedByteString.getSegments$external__okio__android_common__okio_lib().length);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }
}
