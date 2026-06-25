package androidx.viewpager3.widget;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager3.widget.ViewPager3;

/* JADX INFO: loaded from: classes.dex */
final class PageTransformerAdapter extends ViewPager3.OnPageChangeCallback {
    private final LinearLayoutManager mLayoutManager;

    PageTransformerAdapter(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageSelected(int i) {
    }
}
