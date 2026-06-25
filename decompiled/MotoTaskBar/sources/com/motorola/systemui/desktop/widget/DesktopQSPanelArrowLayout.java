package com.motorola.systemui.desktop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.qs.PagedTileLayout;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopQSPanelArrowLayout extends LinearLayout {
    private ImageView mLeftArrow;
    private int mNumPages;
    private final ViewPager.OnAdapterChangeListener mOnAdapterChangeListener;
    private final ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mPosition;
    private PagedTileLayout mQSTileLayout;
    private ImageView mRightArrow;

    public DesktopQSPanelArrowLayout(Context context) {
        super(context);
        this.mPosition = -1;
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DesktopQSPanelArrowLayout.this.setPosition(i);
            }
        };
        this.mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.2
            @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
            public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
                if (pagerAdapter2 != null) {
                    DesktopQSPanelArrowLayout.this.setNumPages(pagerAdapter2.getCount());
                }
            }
        };
    }

    public DesktopQSPanelArrowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPosition = -1;
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DesktopQSPanelArrowLayout.this.setPosition(i);
            }
        };
        this.mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.2
            @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
            public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
                if (pagerAdapter2 != null) {
                    DesktopQSPanelArrowLayout.this.setNumPages(pagerAdapter2.getCount());
                }
            }
        };
    }

    public DesktopQSPanelArrowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPosition = -1;
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i22) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                DesktopQSPanelArrowLayout.this.setPosition(i2);
            }
        };
        this.mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.2
            @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
            public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
                if (pagerAdapter2 != null) {
                    DesktopQSPanelArrowLayout.this.setNumPages(pagerAdapter2.getCount());
                }
            }
        };
    }

    public DesktopQSPanelArrowLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPosition = -1;
        this.mOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i22) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i22, float f, int i222) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i22) {
                DesktopQSPanelArrowLayout.this.setPosition(i22);
            }
        };
        this.mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout.2
            @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
            public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
                if (pagerAdapter2 != null) {
                    DesktopQSPanelArrowLayout.this.setNumPages(pagerAdapter2.getCount());
                }
            }
        };
    }

    private void handleLeftArrow() {
        int i;
        PagedTileLayout pagedTileLayout = this.mQSTileLayout;
        if (pagedTileLayout != null && (i = this.mPosition) > 0) {
            pagedTileLayout.setCurrentItem(i - 1, true);
        }
    }

    private void handleRightArrow() {
        int i;
        PagedTileLayout pagedTileLayout = this.mQSTileLayout;
        if (pagedTileLayout != null && (i = this.mPosition) >= 0 && i < this.mNumPages - 1) {
            pagedTileLayout.setCurrentItem(i + 1, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        handleLeftArrow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$1(View view) {
        handleRightArrow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNumPages(int i) {
        this.mNumPages = i;
        setVisibility(i > 1 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPosition(int i) {
        this.mPosition = i;
        this.mLeftArrow.setEnabled(true);
        this.mRightArrow.setEnabled(true);
        if (i == 0) {
            this.mLeftArrow.setEnabled(false);
        } else if (i == this.mNumPages - 1) {
            this.mRightArrow.setEnabled(false);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = (ImageView) findViewById(R$id.left_arrow);
        this.mLeftArrow = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onFinishInflate$0(view);
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R$id.right_arrow);
        this.mRightArrow = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onFinishInflate$1(view);
            }
        });
    }

    public void setPageTileLayout(PagedTileLayout pagedTileLayout) {
        this.mQSTileLayout = pagedTileLayout;
        if (pagedTileLayout != null) {
            pagedTileLayout.removeOnPageChangeListener(this.mOnPageChangeListener);
            this.mQSTileLayout.addOnPageChangeListener(this.mOnPageChangeListener);
            this.mQSTileLayout.addOnAdapterChangeListener(this.mOnAdapterChangeListener);
            setNumPages(this.mQSTileLayout.getNumPages());
            setPosition(this.mQSTileLayout.getCurrentItem());
        }
    }
}
