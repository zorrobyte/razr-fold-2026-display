package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.res.R$integer;

/* JADX INFO: compiled from: SideLabelTileLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public class SideLabelTileLayout extends TileLayout {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SideLabelTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
    }

    @Override // com.android.systemui.qs.TileLayout
    public boolean updateMaxRows(int i, int i2) {
        int i3 = this.mRows;
        int i4 = this.mMaxAllowedRows;
        this.mRows = i4;
        int i5 = this.mColumns;
        if (i4 > ((i2 + i5) - 1) / i5) {
            this.mRows = ((i2 + i5) - 1) / i5;
        }
        return i3 != this.mRows;
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean updateResources() {
        boolean zUpdateResources = super.updateResources();
        this.mMaxAllowedRows = getContext().getResources().getInteger(R$integer.quick_settings_max_rows);
        processDesktopCase();
        return zUpdateResources;
    }

    @Override // com.android.systemui.qs.TileLayout
    protected boolean useSidePadding() {
        return false;
    }
}
