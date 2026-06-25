package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
class SingleRow extends Grid {
    private final Grid.Location mTmpLocation = new Grid.Location(0);

    SingleRow() {
        setNumRows(1);
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean appendVisibleItems(int i, boolean z) {
        int edge;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!z && checkAppendOverLimit(i)) {
            return false;
        }
        int startIndexForAppend = getStartIndexForAppend();
        boolean z2 = false;
        while (startIndexForAppend < this.mProvider.getCount()) {
            int iCreateItem = this.mProvider.createItem(startIndexForAppend, true, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                edge = this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                this.mFirstVisibleIndex = startIndexForAppend;
                this.mLastVisibleIndex = startIndexForAppend;
            } else {
                if (this.mReversedFlow) {
                    int i2 = startIndexForAppend - 1;
                    edge = (this.mProvider.getEdge(i2) - this.mProvider.getSize(i2)) - this.mSpacing;
                } else {
                    int i3 = startIndexForAppend - 1;
                    edge = this.mProvider.getEdge(i3) + this.mProvider.getSize(i3) + this.mSpacing;
                }
                this.mLastVisibleIndex = startIndexForAppend;
            }
            this.mProvider.addItem(this.mTmpItem[0], startIndexForAppend, iCreateItem, 0, edge);
            if (z || checkAppendOverLimit(i)) {
                return true;
            }
            startIndexForAppend++;
            z2 = true;
        }
        return z2;
    }

    @Override // androidx.leanback.widget.Grid
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int startIndexForPrepend;
        int i3;
        if (!this.mReversedFlow ? i2 < 0 : i2 > 0) {
            if (getLastVisibleIndex() == this.mProvider.getCount() - 1) {
                return;
            }
            startIndexForPrepend = getStartIndexForAppend();
            int size = this.mProvider.getSize(this.mLastVisibleIndex) + this.mSpacing;
            int edge = this.mProvider.getEdge(this.mLastVisibleIndex);
            if (this.mReversedFlow) {
                size = -size;
            }
            i3 = size + edge;
        } else {
            if (getFirstVisibleIndex() == 0) {
                return;
            }
            startIndexForPrepend = getStartIndexForPrepend();
            int edge2 = this.mProvider.getEdge(this.mFirstVisibleIndex);
            boolean z = this.mReversedFlow;
            int i4 = this.mSpacing;
            if (!z) {
                i4 = -i4;
            }
            i3 = edge2 + i4;
        }
        layoutPrefetchRegistry.addPosition(startIndexForPrepend, Math.abs(i3 - i));
    }

    @Override // androidx.leanback.widget.Grid
    protected final int findRowMax(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? this.mProvider.getEdge(i) : this.mProvider.getEdge(i) + this.mProvider.getSize(i);
    }

    @Override // androidx.leanback.widget.Grid
    protected final int findRowMin(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? this.mProvider.getEdge(i) - this.mProvider.getSize(i) : this.mProvider.getEdge(i);
    }

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        this.mTmpItemPositionsInRows[0].clear();
        this.mTmpItemPositionsInRows[0].addLast(i);
        this.mTmpItemPositionsInRows[0].addLast(i2);
        return this.mTmpItemPositionsInRows;
    }

    @Override // androidx.leanback.widget.Grid
    public final Grid.Location getLocation(int i) {
        return this.mTmpLocation;
    }

    int getStartIndexForAppend() {
        int i = this.mLastVisibleIndex;
        if (i >= 0) {
            return i + 1;
        }
        int i2 = this.mStartIndex;
        if (i2 != -1) {
            return Math.min(i2, this.mProvider.getCount() - 1);
        }
        return 0;
    }

    int getStartIndexForPrepend() {
        int i = this.mFirstVisibleIndex;
        if (i >= 0) {
            return i - 1;
        }
        int i2 = this.mStartIndex;
        return i2 != -1 ? Math.min(i2, this.mProvider.getCount() - 1) : this.mProvider.getCount() - 1;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean prependVisibleItems(int i, boolean z) {
        int edge;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (!z && checkPrependOverLimit(i)) {
            return false;
        }
        int minIndex = this.mProvider.getMinIndex();
        boolean z2 = false;
        for (int startIndexForPrepend = getStartIndexForPrepend(); startIndexForPrepend >= minIndex; startIndexForPrepend--) {
            int iCreateItem = this.mProvider.createItem(startIndexForPrepend, false, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                edge = this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                this.mFirstVisibleIndex = startIndexForPrepend;
                this.mLastVisibleIndex = startIndexForPrepend;
            } else {
                edge = this.mReversedFlow ? this.mProvider.getEdge(startIndexForPrepend + 1) + this.mSpacing + iCreateItem : (this.mProvider.getEdge(startIndexForPrepend + 1) - this.mSpacing) - iCreateItem;
                this.mFirstVisibleIndex = startIndexForPrepend;
            }
            this.mProvider.addItem(this.mTmpItem[0], startIndexForPrepend, iCreateItem, 0, edge);
            z2 = true;
            if (z || checkPrependOverLimit(i)) {
                break;
            }
        }
        return z2;
    }
}
