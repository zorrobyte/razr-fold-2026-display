package androidx.leanback.widget;

import androidx.collection.CircularArray;
import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;

/* JADX INFO: loaded from: classes.dex */
abstract class StaggeredGrid extends Grid {
    protected Object mPendingItem;
    protected int mPendingItemSize;
    protected CircularArray mLocations = new CircularArray(64);
    protected int mFirstIndex = -1;

    class Location extends Grid.Location {
        int mOffset;
        int mSize;

        Location(int i, int i2, int i3) {
            super(i);
            this.mOffset = i2;
            this.mSize = i3;
        }
    }

    StaggeredGrid() {
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0041 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0039 -> B:17:0x003f). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int calculateOffsetAfterLastItem(int r3) {
        /*
            r2 = this;
            int r0 = r2.getLastIndex()
        L4:
            int r1 = r2.mFirstIndex
            if (r0 < r1) goto L14
            androidx.leanback.widget.StaggeredGrid$Location r1 = r2.getLocation(r0)
            int r1 = r1.mRow
            if (r1 != r3) goto L11
            goto L18
        L11:
            int r0 = r0 + (-1)
            goto L4
        L14:
            int r0 = r2.getLastIndex()
        L18:
            boolean r3 = r2.isReversedFlow()
            if (r3 == 0) goto L28
            androidx.leanback.widget.StaggeredGrid$Location r3 = r2.getLocation(r0)
            int r3 = r3.mSize
            int r3 = -r3
            int r1 = r2.mSpacing
            goto L3f
        L28:
            androidx.leanback.widget.StaggeredGrid$Location r3 = r2.getLocation(r0)
            int r3 = r3.mSize
            int r1 = r2.mSpacing
            int r3 = r3 + r1
        L31:
            int r0 = r0 + 1
            int r1 = r2.getLastIndex()
            if (r0 > r1) goto L41
            androidx.leanback.widget.StaggeredGrid$Location r1 = r2.getLocation(r0)
            int r1 = r1.mOffset
        L3f:
            int r3 = r3 - r1
            goto L31
        L41:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGrid.calculateOffsetAfterLastItem(int):int");
    }

    protected final boolean appendVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int edge;
        int i3;
        if (this.mLocations.size() == 0) {
            return false;
        }
        int count = this.mProvider.getCount();
        int i4 = this.mLastVisibleIndex;
        if (i4 >= 0) {
            i2 = i4 + 1;
            edge = this.mProvider.getEdge(i4);
        } else {
            int i5 = this.mStartIndex;
            i2 = i5 != -1 ? i5 : 0;
            if (i2 > getLastIndex() + 1 || i2 < getFirstIndex()) {
                this.mLocations.clear();
                return false;
            }
            if (i2 > getLastIndex()) {
                return false;
            }
            edge = Integer.MAX_VALUE;
        }
        int lastIndex = getLastIndex();
        int i6 = i2;
        while (i6 < count && i6 <= lastIndex) {
            Location location = getLocation(i6);
            if (edge != Integer.MAX_VALUE) {
                edge += location.mOffset;
            }
            int i7 = edge;
            int i8 = location.mRow;
            int iCreateItem = this.mProvider.createItem(i6, true, this.mTmpItem, false);
            if (iCreateItem != location.mSize) {
                location.mSize = iCreateItem;
                this.mLocations.removeFromEnd(lastIndex - i6);
                i3 = i6;
            } else {
                i3 = lastIndex;
            }
            this.mLastVisibleIndex = i6;
            if (this.mFirstVisibleIndex < 0) {
                this.mFirstVisibleIndex = i6;
            }
            this.mProvider.addItem(this.mTmpItem[0], i6, iCreateItem, i8, i7);
            if (!z && checkAppendOverLimit(i)) {
                return true;
            }
            int edge2 = i7 == Integer.MAX_VALUE ? this.mProvider.getEdge(i6) : i7;
            if (i8 == this.mNumRows - 1 && z) {
                return true;
            }
            i6++;
            lastIndex = i3;
            edge = edge2;
        }
        return false;
    }

    protected final int appendVisibleItemToRow(int i, int i2, int i3) {
        int i4 = this.mLastVisibleIndex;
        if (i4 >= 0 && (i4 != getLastIndex() || this.mLastVisibleIndex != i - 1)) {
            throw new IllegalStateException();
        }
        int i5 = this.mLastVisibleIndex;
        Location location = new Location(i2, i5 < 0 ? (this.mLocations.size() <= 0 || i != getLastIndex() + 1) ? 0 : calculateOffsetAfterLastItem(i2) : i3 - this.mProvider.getEdge(i5), 0);
        this.mLocations.addLast(location);
        Object obj = this.mPendingItem;
        if (obj != null) {
            location.mSize = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            location.mSize = this.mProvider.createItem(i, true, this.mTmpItem, false);
            obj = this.mTmpItem[0];
        }
        Object obj2 = obj;
        if (this.mLocations.size() == 1) {
            this.mLastVisibleIndex = i;
            this.mFirstVisibleIndex = i;
            this.mFirstIndex = i;
        } else {
            int i6 = this.mLastVisibleIndex;
            if (i6 < 0) {
                this.mLastVisibleIndex = i;
                this.mFirstVisibleIndex = i;
            } else {
                this.mLastVisibleIndex = i6 + 1;
            }
        }
        this.mProvider.addItem(obj2, i, location.mSize, i2, i3);
        return location.mSize;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean appendVisibleItems(int i, boolean z) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!z && checkAppendOverLimit(i)) {
            return false;
        }
        try {
            if (!appendVisbleItemsWithCache(i, z)) {
                return appendVisibleItemsWithoutCache(i, z);
            }
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
            return true;
        } finally {
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
        }
    }

    protected abstract boolean appendVisibleItemsWithoutCache(int i, boolean z);

    public final int getFirstIndex() {
        return this.mFirstIndex;
    }

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        for (int i3 = 0; i3 < this.mNumRows; i3++) {
            this.mTmpItemPositionsInRows[i3].clear();
        }
        if (i >= 0) {
            while (i <= i2) {
                CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[getLocation(i).mRow];
                if (circularIntArray.size() <= 0 || circularIntArray.getLast() != i - 1) {
                    circularIntArray.addLast(i);
                    circularIntArray.addLast(i);
                } else {
                    circularIntArray.popLast();
                    circularIntArray.addLast(i);
                }
                i++;
            }
        }
        return this.mTmpItemPositionsInRows;
    }

    public final int getLastIndex() {
        return (this.mFirstIndex + this.mLocations.size()) - 1;
    }

    @Override // androidx.leanback.widget.Grid
    public final Location getLocation(int i) {
        int i2 = i - this.mFirstIndex;
        if (i2 < 0 || i2 >= this.mLocations.size()) {
            return null;
        }
        return (Location) this.mLocations.get(i2);
    }

    @Override // androidx.leanback.widget.Grid
    public void invalidateItemsAfter(int i) {
        super.invalidateItemsAfter(i);
        this.mLocations.removeFromEnd((getLastIndex() - i) + 1);
        if (this.mLocations.size() == 0) {
            this.mFirstIndex = -1;
        }
    }

    protected final boolean prependVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int edge;
        int i3;
        if (this.mLocations.size() == 0) {
            return false;
        }
        int i4 = this.mFirstVisibleIndex;
        if (i4 >= 0) {
            edge = this.mProvider.getEdge(i4);
            i3 = getLocation(this.mFirstVisibleIndex).mOffset;
            i2 = this.mFirstVisibleIndex - 1;
        } else {
            int i5 = this.mStartIndex;
            i2 = i5 != -1 ? i5 : 0;
            if (i2 > getLastIndex() || i2 < getFirstIndex() - 1) {
                this.mLocations.clear();
                return false;
            }
            if (i2 < getFirstIndex()) {
                return false;
            }
            edge = Integer.MAX_VALUE;
            i3 = 0;
        }
        int iMax = Math.max(this.mProvider.getMinIndex(), this.mFirstIndex);
        for (int i6 = i2; i6 >= iMax; i6--) {
            Location location = getLocation(i6);
            int i7 = location.mRow;
            int iCreateItem = this.mProvider.createItem(i6, false, this.mTmpItem, false);
            if (iCreateItem != location.mSize) {
                this.mLocations.removeFromStart((i6 + 1) - this.mFirstIndex);
                this.mFirstIndex = this.mFirstVisibleIndex;
                this.mPendingItem = this.mTmpItem[0];
                this.mPendingItemSize = iCreateItem;
                return false;
            }
            this.mFirstVisibleIndex = i6;
            if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = i6;
            }
            this.mProvider.addItem(this.mTmpItem[0], i6, iCreateItem, i7, edge - i3);
            if (!z && checkPrependOverLimit(i)) {
                return true;
            }
            edge = this.mProvider.getEdge(i6);
            i3 = location.mOffset;
            if (i7 == 0 && z) {
                return true;
            }
        }
        return false;
    }

    protected final int prependVisibleItemToRow(int i, int i2, int i3) {
        int i4 = this.mFirstVisibleIndex;
        if (i4 >= 0 && (i4 != getFirstIndex() || this.mFirstVisibleIndex != i + 1)) {
            throw new IllegalStateException();
        }
        int i5 = this.mFirstIndex;
        Location location = i5 >= 0 ? getLocation(i5) : null;
        int edge = this.mProvider.getEdge(this.mFirstIndex);
        Location location2 = new Location(i2, 0, 0);
        this.mLocations.addFirst(location2);
        Object obj = this.mPendingItem;
        if (obj != null) {
            location2.mSize = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            location2.mSize = this.mProvider.createItem(i, false, this.mTmpItem, false);
            obj = this.mTmpItem[0];
        }
        Object obj2 = obj;
        this.mFirstVisibleIndex = i;
        this.mFirstIndex = i;
        if (this.mLastVisibleIndex < 0) {
            this.mLastVisibleIndex = i;
        }
        int i6 = !this.mReversedFlow ? i3 - location2.mSize : i3 + location2.mSize;
        if (location != null) {
            location.mOffset = edge - i6;
        }
        this.mProvider.addItem(obj2, i, location2.mSize, i2, i6);
        return location2.mSize;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean prependVisibleItems(int i, boolean z) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!z && checkPrependOverLimit(i)) {
            return false;
        }
        try {
            if (!prependVisbleItemsWithCache(i, z)) {
                return prependVisibleItemsWithoutCache(i, z);
            }
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
            return true;
        } finally {
            this.mTmpItem[0] = null;
            this.mPendingItem = null;
        }
    }

    protected abstract boolean prependVisibleItemsWithoutCache(int i, boolean z);
}
