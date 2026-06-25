package com.android.systemui.qs;

import android.animation.AnimatorSet;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class PagedTileLayout extends ViewPager implements QSPanel.QSTileLayout {
    private static final Interpolator SCROLL_CUBIC = new Interpolator() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return PagedTileLayout.$r8$lambda$rkP_65dVBFP2D8uUQnvlOkRt4RU(f);
        }
    };
    private final PagerAdapter mAdapter;
    private AnimatorSet mBounceAnimatorSet;
    private boolean mDistributeTiles;
    private int mExcessHeight;
    private int mLastExcessHeight;
    private float mLastExpansion;
    private int mLastMaxHeight;
    private int mLayoutDirection;
    private int mLayoutOrientation;
    private boolean mListening;
    private int mMaxColumns;
    private int mMinRows;
    private final ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PageIndicator mPageIndicator;
    private float mPageIndicatorPosition;
    private int mPageToRestore;
    private final ArrayList mPages;
    private boolean mRunningInTestHarness;
    Scroller mScroller;
    private final ArrayList mTiles;
    private final UiEventLogger mUiEventLogger;

    public interface PageListener {
    }

    public static /* synthetic */ float $r8$lambda$rkP_65dVBFP2D8uUQnvlOkRt4RU(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }

    /* JADX INFO: renamed from: -$$Nest$fgetmPageListener, reason: not valid java name */
    static /* bridge */ /* synthetic */ PageListener m1583$$Nest$fgetmPageListener(PagedTileLayout pagedTileLayout) {
        pagedTileLayout.getClass();
        return null;
    }

    public PagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mDistributeTiles = false;
        this.mPageToRestore = -1;
        this.mUiEventLogger = QSEvents.INSTANCE.getQsUiEventsLogger();
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        this.mRunningInTestHarness = ActivityManager.isRunningInTestHarness();
        this.mLastMaxHeight = -1;
        ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qs.PagedTileLayout.2
            private int mCurrentScrollState = 0;
            private boolean mIsScrollJankTraceBegin = false;

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (i != this.mCurrentScrollState && i == 0) {
                    InteractionJankMonitor.getInstance().end(6);
                    this.mIsScrollJankTraceBegin = false;
                }
                this.mCurrentScrollState = i;
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                if (!this.mIsScrollJankTraceBegin && this.mCurrentScrollState == 1) {
                    InteractionJankMonitor.getInstance().begin(PagedTileLayout.this, 6);
                    this.mIsScrollJankTraceBegin = true;
                }
                if (PagedTileLayout.this.mPageIndicator == null) {
                    return;
                }
                PagedTileLayout.this.mPageIndicatorPosition = i + f;
                PagedTileLayout.this.mPageIndicator.setLocation(PagedTileLayout.this.mPageIndicatorPosition);
                PagedTileLayout.m1583$$Nest$fgetmPageListener(PagedTileLayout.this);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                PagedTileLayout.this.updateSelected();
                if (PagedTileLayout.this.mPageIndicator == null) {
                    return;
                }
                PagedTileLayout.m1583$$Nest$fgetmPageListener(PagedTileLayout.this);
            }
        };
        this.mOnPageChangeListener = simpleOnPageChangeListener;
        PagerAdapter pagerAdapter = new PagerAdapter() { // from class: com.android.systemui.qs.PagedTileLayout.3
            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                viewGroup.removeView((View) obj);
                PagedTileLayout.this.updateListening();
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return PagedTileLayout.this.mPages.size();
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup viewGroup, int i) {
                if (PagedTileLayout.this.isLayoutRtl()) {
                    i = (PagedTileLayout.this.mPages.size() - 1) - i;
                }
                ViewGroup viewGroup2 = (ViewGroup) PagedTileLayout.this.mPages.get(i);
                if (viewGroup2.getParent() != null) {
                    viewGroup.removeView(viewGroup2);
                }
                viewGroup.addView(viewGroup2);
                PagedTileLayout.this.updateListening();
                return viewGroup2;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }
        };
        this.mAdapter = pagerAdapter;
        this.mScroller = new Scroller(context, SCROLL_CUBIC);
        setAdapter(pagerAdapter);
        setOnPageChangeListener(simpleOnPageChangeListener);
        setCurrentItem(0, false);
        this.mLayoutOrientation = getResources().getConfiguration().orientation;
        this.mLayoutDirection = getLayoutDirection();
    }

    private TileLayout createTileLayout() {
        TileLayout tileLayout = (TileLayout) LayoutInflater.from(getContext()).inflate(R$layout.qs_paged_page, (ViewGroup) this, false);
        tileLayout.setMinRows(this.mMinRows);
        tileLayout.setMaxColumns(this.mMaxColumns);
        tileLayout.setSelected(false);
        tileLayout.setSquishinessFraction(this.mPages.isEmpty() ? 1.0f : ((TileLayout) this.mPages.get(0)).getSquishinessFraction());
        return tileLayout;
    }

    private void distributeTiles() {
        emptyAndInflateOrRemovePages();
        int iMaxTiles = ((TileLayout) this.mPages.get(0)).maxTiles();
        int size = this.mTiles.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            QSPanelControllerBase.TileRecord tileRecord = (QSPanelControllerBase.TileRecord) this.mTiles.get(i2);
            if (((TileLayout) this.mPages.get(i)).mRecords.size() == iMaxTiles) {
                i++;
            }
            ((TileLayout) this.mPages.get(i)).addTile(tileRecord);
        }
    }

    private void emptyAndInflateOrRemovePages() {
        int numPages = getNumPages();
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            ((TileLayout) this.mPages.get(i)).removeAllViews();
        }
        PageIndicator pageIndicator = this.mPageIndicator;
        if (pageIndicator != null) {
            pageIndicator.setNumPages(numPages);
        }
        if (size == numPages) {
            return;
        }
        while (this.mPages.size() < numPages) {
            this.mPages.add(createTileLayout());
        }
        while (this.mPages.size() > numPages) {
            this.mPages.remove(r1.size() - 1);
        }
        setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        int i2 = this.mPageToRestore;
        if (i2 != -1) {
            setCurrentItem(i2, false);
            this.mPageToRestore = -1;
        }
    }

    private int getCurrentPageNumber() {
        return getPageNumberForDirection(isLayoutRtl());
    }

    private int getDeltaXForPageScrolling(int i) {
        if (i == 0 && getCurrentItem() != 0) {
            return -getWidth();
        }
        if (i != 1 || getCurrentItem() == this.mPages.size() - 1) {
            return 0;
        }
        return getWidth();
    }

    private int getPageNumberForDirection(boolean z) {
        int currentItem = getCurrentItem();
        return z ? (this.mPages.size() - 1) - currentItem : currentItem;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fakeDragBy$1(int i) {
        setCurrentItem(i, true);
        AnimatorSet animatorSet = this.mBounceAnimatorSet;
        if (animatorSet != null) {
            animatorSet.start();
        }
        setOffscreenPageLimit(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPageIndicator$2(int i) {
        if (this.mScroller.isFinished()) {
            scrollByX(getDeltaXForPageScrolling(i), 300);
        }
    }

    private void logVisibleTiles(TileLayout tileLayout) {
        for (int i = 0; i < tileLayout.mRecords.size(); i++) {
            QSTile qSTile = ((QSPanelControllerBase.TileRecord) tileLayout.mRecords.get(i)).tile;
            this.mUiEventLogger.logWithInstanceId(QSEvent.QS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
        }
    }

    private int sanitizePageAction(int i) {
        int id = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT.getId();
        return (i == id || i == AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT.getId()) ? !isLayoutRtl() ? i == id ? 8192 : 4096 : i == id ? 4096 : 8192 : i;
    }

    private void scrollByX(int i, int i2) {
        if (i != 0) {
            this.mScroller.startScroll(getScrollX(), getScrollY(), i, 0, i2);
            postInvalidateOnAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateListening() {
        ArrayList arrayList = this.mPages;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TileLayout tileLayout = (TileLayout) obj;
            tileLayout.setListening(tileLayout.getParent() != null && this.mListening);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelected() {
        float f = this.mLastExpansion;
        if (f <= 0.0f || f >= 1.0f) {
            boolean z = f == 1.0f;
            setImportantForAccessibility(4);
            int currentPageNumber = getCurrentPageNumber();
            int i = 0;
            while (i < this.mPages.size()) {
                TileLayout tileLayout = (TileLayout) this.mPages.get(i);
                tileLayout.setSelected(i == currentPageNumber ? z : false);
                if (tileLayout.isSelected()) {
                    logVisibleTiles(tileLayout);
                }
                i++;
            }
            setImportantForAccessibility(0);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void addTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        forceTilesRedistribution("adding new tile");
        requestLayout();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (!isFakeDragging()) {
                beginFakeDrag();
            }
            fakeDragBy(getScrollX() - this.mScroller.getCurrX());
        } else if (isFakeDragging()) {
            endFakeDrag();
            AnimatorSet animatorSet = this.mBounceAnimatorSet;
            if (animatorSet != null) {
                animatorSet.start();
            }
            setOffscreenPageLimit(1);
        }
        super.computeScroll();
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void endFakeDrag() {
        try {
            super.endFakeDrag();
        } catch (NullPointerException unused) {
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void fakeDragBy(float f) {
        try {
            super.fakeDragBy(f);
            postInvalidateOnAnimation();
        } catch (NullPointerException unused) {
            final int size = this.mPages.size() - 1;
            post(new Runnable() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fakeDragBy$1(size);
                }
            });
        }
    }

    public void forceTilesRedistribution(String str) {
        this.mDistributeTiles = true;
    }

    public int getNumPages() {
        int size = this.mTiles.size();
        int iMax = Math.max(size / ((TileLayout) this.mPages.get(0)).maxTiles(), 1);
        return size > ((TileLayout) this.mPages.get(0)).maxTiles() * iMax ? iMax + 1 : iMax;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getTilesHeight() {
        TileLayout tileLayout = (TileLayout) this.mPages.get(0);
        if (tileLayout == null) {
            return 0;
        }
        return tileLayout.getTilesHeight();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.mPages.get(i);
            if (view.getParent() == null) {
                view.dispatchConfigurationChanged(configuration);
            }
        }
        int i2 = this.mLayoutOrientation;
        int i3 = configuration.orientation;
        if (i2 != i3) {
            this.mLayoutOrientation = i3;
            forceTilesRedistribution("orientation changed to " + this.mLayoutOrientation);
            setCurrentItem(0, false);
            this.mPageToRestore = 0;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add(createTileLayout());
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            return;
        }
        accessibilityEvent.setItemCount(this.mAdapter.getCount());
        accessibilityEvent.setFromIndex(getCurrentPageNumber());
        accessibilityEvent.setToIndex(getCurrentPageNumber());
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (getCurrentItem() != 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT);
        }
        if (getCurrentItem() != this.mPages.size() - 1) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).layout(i, i2, i3, i4);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = this.mTiles.size();
        if (this.mDistributeTiles || this.mLastMaxHeight != View.MeasureSpec.getSize(i2) || this.mLastExcessHeight != this.mExcessHeight) {
            int size2 = View.MeasureSpec.getSize(i2);
            this.mLastMaxHeight = size2;
            int i3 = this.mExcessHeight;
            this.mLastExcessHeight = i3;
            if (((TileLayout) this.mPages.get(0)).updateMaxRows(size2 - i3, size) || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                distributeTiles();
            }
            int i4 = ((TileLayout) this.mPages.get(0)).mRows;
            for (int i5 = 0; i5 < this.mPages.size(); i5++) {
                ((TileLayout) this.mPages.get(i5)).mRows = i4;
            }
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            int measuredHeight = getChildAt(i7).getMeasuredHeight();
            if (measuredHeight > i6) {
                i6 = measuredHeight;
            }
        }
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).measure(i, i2);
            int measuredHeight2 = ((TileLayout) this.mPages.get(0)).getMeasuredHeight();
            if (measuredHeight2 > i6) {
                i6 = measuredHeight2;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), i6 + getPaddingBottom());
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        int pageNumberForDirection = getPageNumberForDirection(this.mLayoutDirection == 1);
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(pageNumberForDirection, false);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        int iSanitizePageAction = sanitizePageAction(i);
        boolean zPerformAccessibilityAction = super.performAccessibilityAction(iSanitizePageAction, bundle);
        if (zPerformAccessibilityAction && (iSanitizePageAction == 8192 || iSanitizePageAction == 4096)) {
            requestAccessibilityFocus();
        }
        return zPerformAccessibilityAction;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        if (this.mTiles.remove(tileRecord)) {
            forceTilesRedistribution("removing tile");
            requestLayout();
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    public void setExcessHeight(int i) {
        this.mExcessHeight = i;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        updateListening();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMaxColumns(int i) {
        this.mMaxColumns = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (((TileLayout) this.mPages.get(i2)).setMaxColumns(i)) {
                forceTilesRedistribution("maxColumns in pages changed");
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMinRows(int i) {
        this.mMinRows = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (((TileLayout) this.mPages.get(i2)).setMinRows(i)) {
                forceTilesRedistribution("minRows changed in page");
                z = true;
            }
        }
        return z;
    }

    public void setPageIndicator(PageIndicator pageIndicator) {
        this.mPageIndicator = pageIndicator;
        pageIndicator.setNumPages(this.mPages.size());
        this.mPageIndicator.setLocation(this.mPageIndicatorPosition);
        this.mPageIndicator.setPageScrollActionListener(new PageIndicator.PageScrollActionListener() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda2
            @Override // com.android.systemui.qs.PageIndicator.PageScrollActionListener
            public final void onScrollActionTriggered(int i) {
                this.f$0.lambda$setPageIndicator$2(i);
            }
        });
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setSquishinessFraction(float f) {
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            ((TileLayout) this.mPages.get(i)).setSquishinessFraction(f);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean updateResources() {
        boolean zUpdateResources = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            zUpdateResources |= ((TileLayout) this.mPages.get(i)).updateResources();
        }
        if (zUpdateResources) {
            forceTilesRedistribution("resources in pages changed");
            requestLayout();
        }
        return zUpdateResources;
    }
}
