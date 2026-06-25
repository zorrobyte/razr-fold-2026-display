package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSTileViewImplKt;
import com.android.systemui.res.R$dimen;
import com.android.systemui.util.Utils;
import com.motorola.taskbar.R$integer;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TileLayout extends ViewGroup implements QSPanel.QSTileLayout {
    protected int mCellHeight;
    protected int mCellMarginHorizontal;
    protected int mCellMarginVertical;
    protected int mCellWidth;
    protected int mColumns;
    protected int mEstimatedCellHeight;
    private final Boolean mIsSmallLandscapeLockscreenEnabled;
    protected int mLastTileBottom;
    private final boolean mLessRows;
    protected boolean mListening;
    protected int mMaxAllowedRows;
    private int mMaxColumns;
    private int mMinRows;
    protected final ArrayList mRecords;
    protected int mResourceCellHeight;
    protected int mResourceCellHeightResId;
    protected int mResourceColumns;
    protected int mRows;
    protected int mSidePadding;
    private float mSquishinessFraction;
    protected TextView mTempTextView;

    public TileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResourceCellHeightResId = R$dimen.qs_tile_height;
        boolean z = true;
        this.mRows = 1;
        this.mRecords = new ArrayList();
        this.mMaxAllowedRows = 3;
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        this.mSquishinessFraction = 1.0f;
        this.mIsSmallLandscapeLockscreenEnabled = Boolean.FALSE;
        if (Settings.System.getInt(context.getContentResolver(), "qs_less_rows", 0) == 0 && !Utils.useQsMediaPlayer(context)) {
            z = false;
        }
        this.mLessRows = z;
        this.mTempTextView = new TextView(context);
        updateResources();
    }

    protected static int exactly(int i) {
        return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    }

    private void layoutTileRecords(int i, boolean z) {
        boolean z2 = getLayoutDirection() == 1;
        this.mLastTileBottom = 0;
        int iMin = Math.min(i, this.mRows * this.mColumns);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < iMin) {
            if (i3 == this.mColumns) {
                i4++;
                i3 = 0;
            }
            QSPanelControllerBase.TileRecord tileRecord = (QSPanelControllerBase.TileRecord) this.mRecords.get(i2);
            int rowTop = getRowTop(i4);
            int columnStart = getColumnStart(z2 ? (this.mColumns - i3) - 1 : i3);
            int i5 = this.mCellWidth + columnStart;
            int measuredHeight = tileRecord.tileView.getMeasuredHeight() + rowTop;
            if (z) {
                tileRecord.tileView.layout(columnStart, rowTop, i5, measuredHeight);
            } else {
                tileRecord.tileView.setLeftTopRightBottom(columnStart, rowTop, i5, measuredHeight);
            }
            tileRecord.tileView.setPosition(i2);
            this.mLastTileBottom = rowTop + ((int) (tileRecord.tileView.getMeasuredHeight() * QSTileViewImplKt.constrainSquishiness(this.mSquishinessFraction)));
            i2++;
            i3++;
        }
    }

    private boolean updateColumns() {
        int i = this.mColumns;
        int iMin = Math.min(this.mResourceColumns, this.mMaxColumns);
        this.mColumns = iMin;
        return i != iMin;
    }

    private boolean useSmallLandscapeLockscreenResources() {
        return false;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void addTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.add(tileRecord);
        tileRecord.tile.setListening(this, this.mListening);
        addTileView(tileRecord);
    }

    protected void addTileView(QSPanelControllerBase.TileRecord tileRecord) {
        ViewParent viewParent = tileRecord.tileView;
        if (viewParent instanceof HeightOverrideable) {
            ((HeightOverrideable) viewParent).setSquishinessFraction(this.mSquishinessFraction);
        }
        addView(tileRecord.tileView);
    }

    protected void estimateCellHeight() {
        FontSizeUtils.updateFontSize(this.mTempTextView, R$dimen.qs_tile_text_size);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mTempTextView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        this.mEstimatedCellHeight = (this.mTempTextView.getMeasuredHeight() * 2) + (((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R$dimen.qs_tile_padding) * 2);
    }

    protected int getCellHeight() {
        return Math.max(this.mResourceCellHeight, this.mEstimatedCellHeight);
    }

    protected int getColumnStart(int i) {
        return getPaddingStart() + this.mSidePadding + (i * (this.mCellWidth + this.mCellMarginHorizontal));
    }

    protected int getRowTop(int i) {
        return (int) (i * ((this.mCellHeight * QSTileViewImplKt.constrainSquishiness(this.mSquishinessFraction)) + this.mCellMarginVertical));
    }

    public float getSquishinessFraction() {
        return this.mSquishinessFraction;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getTilesHeight() {
        return this.mLastTileBottom + getPaddingBottom();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public int maxTiles() {
        return Math.max(this.mColumns * this.mMaxAllowedRows, 1);
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(new AccessibilityNodeInfo.CollectionInfo(this.mRecords.size(), 1, false));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutTileRecords(this.mRecords.size(), true);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = this.mRecords.size();
        int size2 = View.MeasureSpec.getSize(i);
        int paddingStart = (size2 - getPaddingStart()) - getPaddingEnd();
        if (View.MeasureSpec.getMode(i2) == 0) {
            this.mRows = ((size + r10) - 1) / this.mColumns;
        }
        int i3 = this.mColumns;
        this.mCellWidth = ((paddingStart - (this.mCellMarginHorizontal * (i3 - 1))) - (this.mSidePadding * 2)) / i3;
        int iExactly = exactly(getCellHeight());
        ArrayList arrayList = this.mRecords;
        int size3 = arrayList.size();
        View viewUpdateAccessibilityOrder = this;
        int i4 = 0;
        while (i4 < size3) {
            Object obj = arrayList.get(i4);
            i4++;
            QSPanelControllerBase.TileRecord tileRecord = (QSPanelControllerBase.TileRecord) obj;
            if (tileRecord.tileView.getVisibility() != 8) {
                tileRecord.tileView.measure(exactly(this.mCellWidth), iExactly);
                viewUpdateAccessibilityOrder = tileRecord.tileView.updateAccessibilityOrder(viewUpdateAccessibilityOrder);
                this.mCellHeight = tileRecord.tileView.getMeasuredHeight();
            }
        }
        int i5 = this.mCellHeight;
        int i6 = this.mCellMarginVertical;
        int i7 = ((i5 + i6) * this.mRows) - i6;
        setMeasuredDimension(size2, i7 >= 0 ? i7 : 0);
    }

    protected void processDesktopCase() {
        this.mMaxAllowedRows = getContext().getResources().getInteger(R$integer.desktop_qs_max_rows);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getContext().getDisplay().getRealMetrics(displayMetrics);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.qs_footer_height);
        while (true) {
            int cellHeight = getCellHeight() + this.mCellMarginVertical;
            int i = this.mMaxAllowedRows;
            if ((cellHeight * i) + dimensionPixelSize <= displayMetrics.heightPixels / 2) {
                break;
            }
            int i2 = i - 1;
            this.mMaxAllowedRows = i2;
            if (i2 <= 1) {
                this.mMaxAllowedRows = 1;
                break;
            }
        }
        if (this.mMaxAllowedRows <= 1 || ((getCellHeight() + this.mCellMarginVertical) * this.mMaxAllowedRows) + dimensionPixelSize <= displayMetrics.heightPixels / 3 || !((QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class)).isMediaPanelVisible(getContext().getDisplayId())) {
            return;
        }
        int i3 = this.mMaxAllowedRows - 2;
        this.mMaxAllowedRows = i3;
        if (i3 <= 1) {
            this.mMaxAllowedRows = 1;
        }
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        ArrayList arrayList = this.mRecords;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((QSPanelControllerBase.TileRecord) obj).tile.setListening(this, false);
        }
        this.mRecords.clear();
        super.removeAllViews();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.remove(tileRecord);
        tileRecord.tile.setListening(this, false);
        removeView(tileRecord.tileView);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        ArrayList arrayList = this.mRecords;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((QSPanelControllerBase.TileRecord) obj).tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMaxColumns(int i) {
        this.mMaxColumns = i;
        return updateColumns();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMinRows(int i) {
        if (this.mMinRows == i) {
            return false;
        }
        this.mMinRows = i;
        updateResources();
        return true;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setSquishinessFraction(float f) {
        if (Float.compare(this.mSquishinessFraction, f) == 0) {
            return;
        }
        this.mSquishinessFraction = f;
        int i = 0;
        layoutTileRecords(this.mRecords.size(), false);
        ArrayList arrayList = this.mRecords;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ViewParent viewParent = ((QSPanelControllerBase.TileRecord) obj).tileView;
            if (viewParent instanceof HeightOverrideable) {
                ((HeightOverrideable) viewParent).setSquishinessFraction(this.mSquishinessFraction);
            }
        }
    }

    public boolean updateMaxRows(int i, int i2) {
        int i3 = i + this.mCellMarginVertical;
        int i4 = this.mRows;
        int cellHeight = i3 / (getCellHeight() + this.mCellMarginVertical);
        this.mRows = cellHeight;
        int i5 = this.mMinRows;
        if (cellHeight < i5) {
            this.mRows = i5;
        } else {
            int i6 = this.mMaxAllowedRows;
            if (cellHeight >= i6) {
                this.mRows = i6;
            }
        }
        int i7 = this.mRows;
        int i8 = this.mColumns;
        if (i7 > ((i2 + i8) - 1) / i8) {
            this.mRows = ((i2 + i8) - 1) / i8;
        }
        return i4 != this.mRows;
    }

    public boolean updateResources() {
        Resources resources = getResources();
        this.mResourceColumns = Math.max(1, useSmallLandscapeLockscreenResources() ? resources.getInteger(com.android.systemui.res.R$integer.small_land_lockscreen_quick_settings_num_columns) : resources.getInteger(com.android.systemui.res.R$integer.quick_settings_num_columns));
        this.mResourceCellHeight = resources.getDimensionPixelSize(this.mResourceCellHeightResId);
        this.mCellMarginHorizontal = resources.getDimensionPixelSize(R$dimen.qs_tile_margin_horizontal);
        this.mSidePadding = useSidePadding() ? this.mCellMarginHorizontal / 2 : 0;
        this.mCellMarginVertical = resources.getDimensionPixelSize(R$dimen.qs_tile_margin_vertical);
        int i = this.mMaxAllowedRows;
        int iMax = Math.max(1, useSmallLandscapeLockscreenResources() ? resources.getInteger(com.android.systemui.res.R$integer.small_land_lockscreen_quick_settings_max_rows) : resources.getInteger(com.android.systemui.res.R$integer.quick_settings_max_rows));
        this.mMaxAllowedRows = iMax;
        if (this.mLessRows) {
            this.mMaxAllowedRows = Math.max(this.mMinRows, iMax - 1);
        }
        this.mTempTextView.dispatchConfigurationChanged(((ViewGroup) this).mContext.getResources().getConfiguration());
        estimateCellHeight();
        processDesktopCase();
        if (updateColumns()) {
            requestLayout();
            return true;
        }
        if (i == this.mMaxAllowedRows) {
            return false;
        }
        requestLayout();
        return true;
    }

    protected boolean useSidePadding() {
        return true;
    }
}
