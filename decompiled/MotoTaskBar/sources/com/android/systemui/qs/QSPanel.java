package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.QSTile;
import com.android.systemui.res.R$dimen;
import com.android.systemui.util.Utils;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class QSPanel extends LinearLayout {
    private boolean mCanCollapse;
    private final ArrayMap mChildrenLayoutTop;
    private final Rect mClippingRect;
    private Runnable mCollapseExpandAction;
    protected final Context mContext;
    protected View mFooter;
    private PageIndicator mFooterPageIndicator;
    protected boolean mListening;
    private ViewGroup mMediaHostView;
    private final int mMediaTopMargin;
    private final int mMediaTotalBottomMargin;
    private int mMovableContentStartIndex;
    private final List mOnConfigurationChangedListeners;
    private boolean mSceneContainerEnabled;
    private boolean mShouldMoveMediaOnExpansion;
    private float mSquishinessFraction;
    protected QSTileLayout mTileLayout;
    protected boolean mUsingMediaPlayer;

    interface OnConfigurationChangedListener {
        void onConfigurationChange(Configuration configuration);
    }

    public interface QSTileLayout {
        void addTile(QSPanelControllerBase.TileRecord tileRecord);

        int getHeight();

        int getTilesHeight();

        void removeTile(QSPanelControllerBase.TileRecord tileRecord);

        void setListening(boolean z);

        boolean setMaxColumns(int i);

        boolean setMinRows(int i);

        void setSquishinessFraction(float f);

        boolean updateResources();
    }

    public QSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnConfigurationChangedListeners = new ArrayList();
        this.mSquishinessFraction = 1.0f;
        this.mChildrenLayoutTop = new ArrayMap();
        this.mClippingRect = new Rect();
        this.mShouldMoveMediaOnExpansion = true;
        this.mCanCollapse = true;
        this.mUsingMediaPlayer = Utils.useQsMediaPlayer(context);
        this.mMediaTotalBottomMargin = getResources().getDimensionPixelSize(R$dimen.quick_settings_bottom_margin_media);
        this.mMediaTopMargin = getResources().getDimensionPixelSize(R$dimen.qs_tile_margin_vertical);
        this.mContext = context;
        setOrientation(1);
        this.mMovableContentStartIndex = getChildCount();
    }

    private boolean needsDynamicRowsAndColumns() {
        return true;
    }

    private void reAttachMediaHost(ViewGroup viewGroup, boolean z) {
        if (!this.mUsingMediaPlayer) {
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(viewGroup);
                return;
            }
            return;
        }
        this.mMediaHostView = viewGroup;
        ViewGroup viewGroup3 = (ViewGroup) viewGroup.getParent();
        Log.d(getDumpableTag(), "Reattaching media host: " + z + ", current " + viewGroup3 + ", new " + this);
        if (viewGroup3 != this) {
            if (viewGroup3 != null) {
                viewGroup3.removeView(viewGroup);
            }
            addView(viewGroup);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams.height = -2;
            int i = 0;
            layoutParams.width = z ? 0 : -1;
            layoutParams.weight = z ? 1.0f : 0.0f;
            layoutParams.bottomMargin = (!z || displayMediaMarginsOnMedia()) ? Math.max(this.mMediaTotalBottomMargin - getPaddingBottom(), 0) : 0;
            if (mediaNeedsTopMargin() && !z) {
                i = this.mMediaTopMargin;
            }
            layoutParams.topMargin = i;
            viewGroup.setLayoutParams(layoutParams);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void switchAllContentToParent(ViewGroup viewGroup, QSTileLayout qSTileLayout) {
        int i = viewGroup == this ? this.mMovableContentStartIndex : 0;
        switchToParent((View) qSTileLayout, viewGroup, i);
        int i2 = i + 1;
        View view = this.mFooter;
        if (view != null) {
            switchToParent(view, viewGroup, i2);
        }
    }

    private void switchToParent(View view, ViewGroup viewGroup, int i) {
        switchToParent(view, viewGroup, i, getDumpableTag());
    }

    static void switchToParent(View view, ViewGroup viewGroup, int i, String str) {
        if (viewGroup == null) {
            Log.w(str, "Trying to move view to null parent", new IllegalStateException());
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        if (viewGroup2 != viewGroup) {
            if (viewGroup2 != null) {
                viewGroup2.removeView(view);
            }
            viewGroup.addView(view, i);
        } else {
            if (viewGroup.indexOfChild(view) == i) {
                return;
            }
            viewGroup.removeView(view);
            viewGroup.addView(view, i);
        }
    }

    private void updateMargins(ViewGroup viewGroup) {
        updatePadding();
    }

    private void updatePageIndicator() {
        PageIndicator pageIndicator;
        if (!(this.mTileLayout instanceof PagedTileLayout) || (pageIndicator = this.mFooterPageIndicator) == null) {
            return;
        }
        pageIndicator.setVisibility(8);
        ((PagedTileLayout) this.mTileLayout).setPageIndicator(this.mFooterPageIndicator);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateViewPositions() {
        /*
            r9 = this;
            com.android.systemui.qs.QSPanel$QSTileLayout r0 = r9.mTileLayout
            int r0 = r0.getTilesHeight()
            com.android.systemui.qs.QSPanel$QSTileLayout r1 = r9.mTileLayout
            int r1 = r1.getHeight()
            int r0 = r0 - r1
            r1 = 0
            r2 = r1
            r3 = r2
        L10:
            int r4 = r9.getChildCount()
            if (r2 >= r4) goto L4f
            android.view.View r4 = r9.getChildAt(r2)
            if (r3 == 0) goto L47
            android.view.ViewGroup r5 = r9.mMediaHostView
            if (r4 != r5) goto L26
            boolean r5 = r9.mShouldMoveMediaOnExpansion
            if (r5 != 0) goto L26
            r5 = r1
            goto L27
        L26:
            r5 = r0
        L27:
            android.util.ArrayMap r6 = r9.mChildrenLayoutTop
            java.lang.Object r6 = r6.get(r4)
            java.lang.Integer r6 = (java.lang.Integer) r6
            if (r6 != 0) goto L32
            goto L4c
        L32:
            int r6 = r6.intValue()
            int r7 = r4.getLeft()
            int r6 = r6 + r5
            int r5 = r4.getRight()
            int r8 = r4.getHeight()
            int r8 = r8 + r6
            r4.setLeftTopRightBottom(r7, r6, r5, r8)
        L47:
            com.android.systemui.qs.QSPanel$QSTileLayout r5 = r9.mTileLayout
            if (r4 != r5) goto L4c
            r3 = 1
        L4c:
            int r2 = r2 + 1
            goto L10
        L4f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSPanel.updateViewPositions():void");
    }

    void addOnConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mOnConfigurationChangedListeners.add(onConfigurationChangedListener);
    }

    final void addTile(final QSPanelControllerBase.TileRecord tileRecord) {
        QSTile.Callback callback = new QSTile.Callback() { // from class: com.android.systemui.qs.QSPanel.1
            @Override // com.android.systemui.qs.QSTile.Callback
            public void onStateChanged(QSTile.State state) {
                QSPanel.this.drawTile(tileRecord, state);
            }
        };
        tileRecord.tile.addCallback(callback);
        tileRecord.callback = callback;
        tileRecord.tileView.init(tileRecord.tile);
        tileRecord.tile.refreshState();
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.addTile(tileRecord);
        }
    }

    protected boolean displayMediaMarginsOnMedia() {
        return true;
    }

    protected void drawTile(QSPanelControllerBase.TileRecord tileRecord, QSTile.State state) {
        tileRecord.tileView.onStateChanged(state);
    }

    protected String getDumpableTag() {
        return "QSPanel";
    }

    public QSTileLayout getOrCreateTileLayout() {
        if (this.mTileLayout == null) {
            QSTileLayout qSTileLayout = (QSTileLayout) LayoutInflater.from(this.mContext).inflate(R$layout.qs_paged_tile_layout, (ViewGroup) this, false);
            this.mTileLayout = qSTileLayout;
            qSTileLayout.setSquishinessFraction(this.mSquishinessFraction);
        }
        return this.mTileLayout;
    }

    QSTileLayout getTileLayout() {
        return this.mTileLayout;
    }

    void initialize(boolean z) {
        this.mUsingMediaPlayer = z;
        this.mTileLayout = getOrCreateTileLayout();
    }

    public boolean isListening() {
        return this.mListening;
    }

    protected boolean mediaNeedsTopMargin() {
        return false;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(final Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mOnConfigurationChangedListeners.forEach(new Consumer() { // from class: com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QSPanel.OnConfigurationChangedListener) obj).onConfigurationChange(configuration);
            }
        });
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mFooter = findViewById(R$id.qs_footer);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCanCollapse) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            this.mChildrenLayoutTop.put(childAt, Integer.valueOf(childAt.getTop()));
        }
        updateViewPositions();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout instanceof PagedTileLayout) {
            PageIndicator pageIndicator = this.mFooterPageIndicator;
            if (pageIndicator != null) {
                pageIndicator.setNumPages(((PagedTileLayout) qSTileLayout).getNumPages());
            }
            if (((View) this.mTileLayout).getParent() == this) {
                int size = 10000 - View.MeasureSpec.getSize(i2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, 1073741824);
                ((PagedTileLayout) this.mTileLayout).setExcessHeight(size);
                i2 = iMakeMeasureSpec;
            }
        }
        super.onMeasure(i, i2);
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = paddingBottom + childAt.getMeasuredHeight();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                paddingBottom = measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), paddingBottom);
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        Runnable runnable;
        if ((i != 262144 && i != 524288) || (runnable = this.mCollapseExpandAction) == null) {
            return super.performAccessibilityAction(i, bundle);
        }
        runnable.run();
        return true;
    }

    void removeOnConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mOnConfigurationChangedListeners.remove(onConfigurationChangedListener);
    }

    void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mTileLayout.removeTile(tileRecord);
    }

    public void setCanCollapse(boolean z) {
        this.mCanCollapse = z;
    }

    public void setFooterPageIndicator(PageIndicator pageIndicator) {
        if (this.mTileLayout instanceof PagedTileLayout) {
            this.mFooterPageIndicator = pageIndicator;
            updatePageIndicator();
        }
    }

    public void setListening(boolean z) {
        this.mListening = z;
    }

    void setSceneContainerEnabled(boolean z) {
        this.mSceneContainerEnabled = z;
        if (z) {
            updatePadding();
        }
    }

    void setUsingHorizontalLayout(boolean z, ViewGroup viewGroup, boolean z2) {
        if (z || z2) {
            Log.d(getDumpableTag(), "setUsingHorizontalLayout: " + z + ", " + z2);
            switchAllContentToParent(this, this.mTileLayout);
            reAttachMediaHost(viewGroup, z);
            if (needsDynamicRowsAndColumns()) {
                this.mTileLayout.setMinRows(z ? 2 : 1);
                this.mTileLayout.setMaxColumns(z ? 2 : 4);
            }
            updateMargins(viewGroup);
        }
    }

    protected void updatePadding() {
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.qs_panel_padding_top);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.qs_panel_padding_bottom);
        int paddingStart = getPaddingStart();
        if (this.mSceneContainerEnabled) {
            dimensionPixelSize = 0;
        }
        int paddingEnd = getPaddingEnd();
        if (this.mSceneContainerEnabled) {
            dimensionPixelSize2 = 0;
        }
        setPaddingRelative(paddingStart, dimensionPixelSize, paddingEnd, dimensionPixelSize2);
    }

    public void updateResources() {
        updatePadding();
        updatePageIndicator();
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.updateResources();
        }
    }
}
