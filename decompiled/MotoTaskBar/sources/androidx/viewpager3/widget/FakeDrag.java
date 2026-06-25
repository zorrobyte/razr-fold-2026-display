package androidx.viewpager3.widget;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
final class FakeDrag {
    private final RecyclerView mRecyclerView;
    private final ScrollEventAdapter mScrollEventAdapter;
    private final ViewPager3 mViewPager;

    FakeDrag(ViewPager3 viewPager3, ScrollEventAdapter scrollEventAdapter, RecyclerView recyclerView) {
        this.mViewPager = viewPager3;
        this.mScrollEventAdapter = scrollEventAdapter;
        this.mRecyclerView = recyclerView;
    }

    boolean isFakeDragging() {
        return this.mScrollEventAdapter.isFakeDragging();
    }
}
