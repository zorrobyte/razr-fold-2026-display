package androidx.compose.ui.spatial;

import java.util.Arrays;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: RectList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RectList {
    public int itemsSize;
    public long[] items = new long[192];
    public long[] stack = new long[192];

    public static /* synthetic */ void insert$default(RectList rectList, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7, Object obj) {
        boolean z3;
        RectList rectList2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13 = (i7 & 32) != 0 ? -1 : i6;
        boolean z4 = (i7 & 64) != 0 ? false : z;
        if ((i7 & 128) != 0) {
            z3 = false;
            i8 = i;
            i9 = i2;
            i10 = i3;
            i11 = i4;
            i12 = i5;
            rectList2 = rectList;
        } else {
            z3 = z2;
            rectList2 = rectList;
            i8 = i;
            i9 = i2;
            i10 = i3;
            i11 = i4;
            i12 = i5;
        }
        rectList2.insert(i8, i9, i10, i11, i12, i13, z4, z3);
    }

    private final void resizeStorage(int i, int i2, long[] jArr) {
        int iMax = Math.max(i * 2, i2 + 3);
        long[] jArrCopyOf = Arrays.copyOf(jArr, iMax);
        jArrCopyOf.getClass();
        this.items = jArrCopyOf;
        long[] jArrCopyOf2 = Arrays.copyOf(this.stack, iMax);
        jArrCopyOf2.getClass();
        this.stack = jArrCopyOf2;
    }

    private final void updateSubhierarchy(long j, int i, int i2) {
        int i3;
        char c;
        char c2;
        long[] jArr = this.items;
        long[] jArr2 = this.stack;
        int size = getSize();
        jArr2[0] = j;
        int i4 = 1;
        while (i4 > 0) {
            i4--;
            long j2 = jArr2[i4];
            int i5 = 67108863;
            int i6 = ((int) j2) & 67108863;
            char c3 = 26;
            int i7 = ((int) (j2 >> 26)) & 67108863;
            char c4 = '4';
            int i8 = ((int) (j2 >> 52)) & 511;
            int i9 = i8 == 511 ? size : i8 + i7;
            if (i7 < 0) {
                return;
            }
            while (i7 < jArr.length - 2 && i7 < i9) {
                int i10 = i7 + 2;
                long j3 = jArr[i10];
                if ((((int) (j3 >> c3)) & i5) == i6) {
                    long j4 = jArr[i7];
                    int i11 = i7 + 1;
                    i3 = i5;
                    c = c3;
                    long j5 = jArr[i11];
                    c2 = c4;
                    jArr[i7] = (((long) (((int) j4) + i2)) & 4294967295L) | (((long) (((int) (j4 >> 32)) + i)) << 32);
                    jArr[i11] = (((long) (((int) j5) + i2)) & 4294967295L) | (((long) (((int) (j5 >> 32)) + i)) << 32);
                    jArr[i10] = 2305843009213693952L | j3;
                    if ((((int) (j3 >> c2)) & 511) > 0) {
                        jArr2[i4] = ((-4503599560261633L) & j3) | (((long) ((i7 + 3) & i3)) << c);
                        i4++;
                    }
                } else {
                    i3 = i5;
                    c = c3;
                    c2 = c4;
                }
                i7 += 3;
                i5 = i3;
                c3 = c;
                c4 = c2;
            }
        }
    }

    public final void clearUpdated() {
        long[] jArr = this.items;
        int i = this.itemsSize;
        for (int i2 = 0; i2 < jArr.length - 2 && i2 < i; i2 += 3) {
            int i3 = i2 + 2;
            jArr[i3] = jArr[i3] & (-2305843009213693953L);
        }
    }

    public final void defragment() {
        long[] jArr = this.items;
        int i = this.itemsSize;
        long[] jArr2 = this.stack;
        int i2 = 0;
        for (int i3 = 0; i3 < jArr.length - 2 && i2 < jArr2.length - 2 && i3 < i; i3 += 3) {
            int i4 = i3 + 2;
            if (jArr[i4] != 2305843009213693951L) {
                jArr2[i2] = jArr[i3];
                jArr2[i2 + 1] = jArr[i3 + 1];
                jArr2[i2 + 2] = jArr[i4];
                i2 += 3;
            }
        }
        this.itemsSize = i2;
        this.items = jArr2;
        this.stack = jArr;
    }

    public final int getSize() {
        return this.itemsSize / 3;
    }

    public final void insert(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        long[] jArr = this.items;
        int i7 = this.itemsSize;
        int i8 = i7 + 3;
        this.itemsSize = i8;
        int length = jArr.length;
        if (length <= i8) {
            resizeStorage(length, i7, jArr);
        }
        long[] jArr2 = this.items;
        jArr2[i7] = (((long) i2) << 32) | (((long) i3) & 4294967295L);
        jArr2[i7 + 1] = (((long) i4) << 32) | (((long) i5) & 4294967295L);
        int i9 = i6 & 67108863;
        jArr2[i7 + 2] = ((z2 ? 1L : 0L) << 63) | ((z ? 1L : 0L) << 62) | (((long) 1) << 61) | (((long) 0) << 52) | (((long) i9) << 26) | ((long) (i & 67108863));
        if (i6 < 0) {
            return;
        }
        for (int i10 = i7 - 3; i10 >= 0; i10 -= 3) {
            int i11 = i10 + 2;
            long j = jArr2[i11];
            if ((((int) j) & 67108863) == i9) {
                jArr2[i11] = (j & (-2301339409586323457L)) | (((long) ((i7 - i10) & 511)) << 52);
                return;
            }
        }
    }

    public final void markUpdated(int i) {
        int i2 = i & 67108863;
        long[] jArr = this.items;
        int i3 = this.itemsSize;
        for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
            int i5 = i4 + 2;
            long j = jArr[i5];
            if ((((int) j) & 67108863) == i2) {
                jArr[i5] = 2305843009213693952L | j;
                return;
            }
        }
    }

    public final boolean move(int i, int i2, int i3, int i4, int i5) {
        int i6 = i & 67108863;
        long[] jArr = this.items;
        int i7 = this.itemsSize;
        for (int i8 = 0; i8 < jArr.length - 2 && i8 < i7; i8 += 3) {
            int i9 = i8 + 2;
            long j = jArr[i9];
            if ((((int) j) & 67108863) == i6) {
                long j2 = jArr[i8];
                jArr[i8] = (((long) i3) & 4294967295L) | (((long) i2) << 32);
                int i10 = i8;
                jArr[i8 + 1] = (((long) i5) & 4294967295L) | (((long) i4) << 32);
                jArr[i9] = 2305843009213693952L | j;
                int i11 = i2 - ((int) (j2 >> 32));
                int i12 = i3 - ((int) j2);
                if ((i11 != 0) | (i12 != 0)) {
                    updateSubhierarchy(((-4503599560261633L) & j) | (((long) ((i10 + 3) & 67108863)) << 26), i11, i12);
                }
                return true;
            }
        }
        return false;
    }

    public final boolean remove(int i) {
        int i2 = i & 67108863;
        long[] jArr = this.items;
        int i3 = this.itemsSize;
        for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
            int i5 = i4 + 2;
            if ((((int) jArr[i5]) & 67108863) == i2) {
                jArr[i4] = -1;
                jArr[i4 + 1] = -1;
                jArr[i5] = 2305843009213693951L;
                return true;
            }
        }
        return false;
    }

    public final boolean update(int i, int i2, int i3, int i4, int i5) {
        int i6 = i & 67108863;
        long[] jArr = this.items;
        int i7 = this.itemsSize;
        for (int i8 = 0; i8 < jArr.length - 2 && i8 < i7; i8 += 3) {
            int i9 = i8 + 2;
            long j = jArr[i9];
            if ((((int) j) & 67108863) == i6) {
                jArr[i8] = (((long) i2) << 32) | (((long) i3) & 4294967295L);
                jArr[i8 + 1] = (((long) i4) << 32) | (4294967295L & ((long) i5));
                jArr[i9] = 2305843009213693952L | j;
                return true;
            }
        }
        return false;
    }

    public final boolean withRect(int i, Function4 function4) {
        int i2 = i & 67108863;
        long[] jArr = this.items;
        int i3 = this.itemsSize;
        for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
            if ((((int) jArr[i4 + 2]) & 67108863) == i2) {
                long j = jArr[i4];
                long j2 = jArr[i4 + 1];
                function4.invoke(Integer.valueOf((int) (j >> 32)), Integer.valueOf((int) j), Integer.valueOf((int) (j2 >> 32)), Integer.valueOf((int) j2));
                return true;
            }
        }
        return false;
    }
}
