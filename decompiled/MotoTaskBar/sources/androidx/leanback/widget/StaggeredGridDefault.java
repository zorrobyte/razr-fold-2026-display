package androidx.leanback.widget;

import androidx.leanback.widget.StaggeredGrid;

/* JADX INFO: loaded from: classes.dex */
final class StaggeredGridDefault extends StaggeredGrid {
    StaggeredGridDefault() {
    }

    private int findRowEdgeLimitSearchIndex(boolean z) {
        boolean z2 = false;
        if (z) {
            for (int i = this.mLastVisibleIndex; i >= this.mFirstVisibleIndex; i--) {
                int i2 = getLocation(i).mRow;
                if (i2 == 0) {
                    z2 = true;
                } else if (z2 && i2 == this.mNumRows - 1) {
                    return i;
                }
            }
            return -1;
        }
        for (int i3 = this.mFirstVisibleIndex; i3 <= this.mLastVisibleIndex; i3++) {
            int i4 = getLocation(i3).mRow;
            if (i4 == this.mNumRows - 1) {
                z2 = true;
            } else if (z2 && i4 == 0) {
                return i3;
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x0136, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0107 A[LOOP:2: B:88:0x0107->B:104:0x012b, LOOP_START, PHI: r6 r9 r10
      0x0107: PHI (r6v12 int) = (r6v6 int), (r6v16 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]
      0x0107: PHI (r9v20 int) = (r9v18 int), (r9v21 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]
      0x0107: PHI (r10v7 int) = (r10v5 int), (r10v9 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean appendVisibleItemsWithoutCache(int r14, boolean r15) {
        /*
            Method dump skipped, instruction units count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.appendVisibleItemsWithoutCache(int, boolean):boolean");
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMax(boolean z, int i, int[] iArr) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.mRow;
        if (this.mReversedFlow) {
            i2 = i3;
            int i4 = i2;
            int i5 = 1;
            int i6 = edge;
            for (int i7 = i + 1; i5 < this.mNumRows && i7 <= this.mLastVisibleIndex; i7++) {
                StaggeredGrid.Location location2 = getLocation(i7);
                i6 += location2.mOffset;
                int i8 = location2.mRow;
                if (i8 != i4) {
                    i5++;
                    if (!z ? i6 >= edge : i6 <= edge) {
                        i4 = i8;
                    } else {
                        edge = i6;
                        i = i7;
                        i2 = i8;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i9 = 1;
            int i10 = i3;
            StaggeredGrid.Location location3 = location;
            int i11 = edge;
            edge = this.mProvider.getSize(i) + edge;
            i2 = i10;
            for (int i12 = i - 1; i9 < this.mNumRows && i12 >= this.mFirstVisibleIndex; i12--) {
                i11 -= location3.mOffset;
                location3 = getLocation(i12);
                int i13 = location3.mRow;
                if (i13 != i10) {
                    i9++;
                    int size = this.mProvider.getSize(i12) + i11;
                    if (!z ? size >= edge : size <= edge) {
                        i10 = i13;
                    } else {
                        edge = size;
                        i = i12;
                        i2 = i13;
                        i10 = i2;
                    }
                }
            }
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return edge;
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMin(boolean z, int i, int[] iArr) {
        int size;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i2 = location.mRow;
        if (this.mReversedFlow) {
            int i3 = 1;
            size = edge - this.mProvider.getSize(i);
            int i4 = i2;
            for (int i5 = i - 1; i3 < this.mNumRows && i5 >= this.mFirstVisibleIndex; i5--) {
                edge -= location.mOffset;
                location = getLocation(i5);
                int i6 = location.mRow;
                if (i6 != i4) {
                    i3++;
                    int size2 = edge - this.mProvider.getSize(i5);
                    if (!z ? size2 >= size : size2 <= size) {
                        i4 = i6;
                    } else {
                        size = size2;
                        i = i5;
                        i2 = i6;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i7 = i2;
            int i8 = i7;
            int i9 = 1;
            int i10 = edge;
            for (int i11 = i + 1; i9 < this.mNumRows && i11 <= this.mLastVisibleIndex; i11++) {
                StaggeredGrid.Location location2 = getLocation(i11);
                i10 += location2.mOffset;
                int i12 = location2.mRow;
                if (i12 != i8) {
                    i9++;
                    if (!z ? i10 >= edge : i10 <= edge) {
                        i8 = i12;
                    } else {
                        edge = i10;
                        i = i11;
                        i7 = i12;
                        i8 = i7;
                    }
                }
            }
            size = edge;
            i2 = i7;
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return size;
    }

    int getRowMax(int i) {
        int i2;
        StaggeredGrid.Location location;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MIN_VALUE;
        }
        if (this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).mRow == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.mOffset;
                }
            } while (location.mRow != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.mRow == i) {
            i2 = location2.mSize;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= getFirstIndex()) {
                    edge2 -= location2.mOffset;
                    location2 = getLocation(i5);
                }
            } while (location2.mRow != i);
            i2 = location2.mSize;
        }
        return edge2 + i2;
        return Integer.MIN_VALUE;
    }

    int getRowMin(int i) {
        StaggeredGrid.Location location;
        int i2;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MAX_VALUE;
        }
        if (!this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).mRow == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.mOffset;
                }
            } while (location.mRow != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.mRow == i) {
            i2 = location2.mSize;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= getFirstIndex()) {
                    edge2 -= location2.mOffset;
                    location2 = getLocation(i5);
                }
            } while (location2.mRow != i);
            i2 = location2.mSize;
        }
        return edge2 - i2;
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0130, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0101 A[LOOP:2: B:86:0x0101->B:102:0x0125, LOOP_START, PHI: r5 r8 r9
      0x0101: PHI (r5v12 int) = (r5v6 int), (r5v17 int) binds: [B:85:0x00ff, B:102:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x0101: PHI (r8v19 int) = (r8v17 int), (r8v20 int) binds: [B:85:0x00ff, B:102:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x0101: PHI (r9v8 int) = (r9v6 int), (r9v10 int) binds: [B:85:0x00ff, B:102:0x0125] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean prependVisibleItemsWithoutCache(int r13, boolean r14) {
        /*
            Method dump skipped, instruction units count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.prependVisibleItemsWithoutCache(int, boolean):boolean");
    }
}
