package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import java.util.List;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: RowColumnMeasurePolicy.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RowColumnMeasurePolicyKt {
    public static final MeasureResult measure(RowColumnMeasurePolicy rowColumnMeasurePolicy, int i, int i2, int i3, int i4, int i5, MeasureScope measureScope, List list, Placeable[] placeableArr, int i6, int i7, int[] iArr, int i8) {
        int i9;
        RowColumnMeasurePolicy rowColumnMeasurePolicy2;
        int i10;
        long j;
        int i11;
        int i12 = i4;
        long j2 = i5;
        int i13 = i7 - i6;
        int[] iArr2 = new int[i13];
        int i14 = 0;
        int i15 = i6;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        float f = 0.0f;
        while (true) {
            int i21 = 1;
            if (i15 >= i7) {
                break;
            }
            Measurable measurable = (Measurable) list.get(i15);
            RowColumnParentData rowColumnParentData = RowColumnImplKt.getRowColumnParentData(measurable);
            float weight = RowColumnImplKt.getWeight(rowColumnParentData);
            if (i18 == 0 && !RowColumnImplKt.isRelative(rowColumnParentData)) {
                i21 = i14;
            }
            if (weight > 0.0f) {
                f += weight;
                i19++;
                i10 = i15;
                j = j2;
            } else {
                if (i12 != Integer.MAX_VALUE && rowColumnParentData != null) {
                    rowColumnParentData.getFlowLayoutData();
                }
                int i22 = i3 - i20;
                Placeable placeableMo1284measureBRTryo0 = placeableArr[i15];
                if (placeableMo1284measureBRTryo0 == null) {
                    int i23 = i3 != Integer.MAX_VALUE ? i22 < 0 ? i14 : i22 : Integer.MAX_VALUE;
                    i10 = i15;
                    rowColumnMeasurePolicy2 = rowColumnMeasurePolicy;
                    j = j2;
                    i11 = i17;
                    placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(RowColumnMeasurePolicy.m171createConstraintsxF2OJ5Q$default(rowColumnMeasurePolicy2, 0, 0, i23, i12, false, 16, null));
                } else {
                    rowColumnMeasurePolicy2 = rowColumnMeasurePolicy;
                    i10 = i15;
                    j = j2;
                    i11 = i17;
                }
                int iMainAxisSize = rowColumnMeasurePolicy2.mainAxisSize(placeableMo1284measureBRTryo0);
                int iCrossAxisSize = rowColumnMeasurePolicy2.crossAxisSize(placeableMo1284measureBRTryo0);
                iArr2[i10 - i6] = iMainAxisSize;
                int i24 = i22 - iMainAxisSize;
                if (i24 < 0) {
                    i24 = 0;
                }
                int iMin = Math.min(i5, i24);
                i20 += iMainAxisSize + iMin;
                int iMax = Math.max(i11, iCrossAxisSize);
                placeableArr[i10] = placeableMo1284measureBRTryo0;
                i17 = iMax;
                i16 = iMin;
            }
            i15 = i10 + 1;
            i18 = i21;
            j2 = j;
            i14 = 0;
        }
        RowColumnMeasurePolicy rowColumnMeasurePolicy3 = rowColumnMeasurePolicy;
        long j3 = j2;
        int i25 = i17;
        if (i19 == 0) {
            i20 -= i16;
            i9 = 0;
        } else {
            long j4 = j3 * ((long) (i19 - 1));
            long jRound = ((long) ((i3 != Integer.MAX_VALUE ? i3 : i) - i20)) - j4;
            if (jRound < 0) {
                jRound = 0;
            }
            float f2 = jRound / f;
            for (int i26 = i6; i26 < i7; i26++) {
                jRound -= (long) Math.round(RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData((Measurable) list.get(i26))) * f2);
            }
            int i27 = i6;
            int i28 = 0;
            while (i27 < i7) {
                if (placeableArr[i27] == null) {
                    Measurable measurable2 = (Measurable) list.get(i27);
                    RowColumnParentData rowColumnParentData2 = RowColumnImplKt.getRowColumnParentData(measurable2);
                    float weight2 = RowColumnImplKt.getWeight(rowColumnParentData2);
                    if (i12 != Integer.MAX_VALUE && rowColumnParentData2 != null) {
                        rowColumnParentData2.getFlowLayoutData();
                    }
                    if (!(weight2 > 0.0f)) {
                        InlineClassHelperKt.throwIllegalStateException("All weights <= 0 should have placeables");
                    }
                    int sign = MathKt.getSign(jRound);
                    long j5 = jRound - ((long) sign);
                    int iMax2 = Math.max(0, Math.round(weight2 * f2) + sign);
                    int i29 = (!RowColumnImplKt.getFill(rowColumnParentData2) || iMax2 == Integer.MAX_VALUE) ? 0 : iMax2;
                    rowColumnMeasurePolicy3 = rowColumnMeasurePolicy;
                    Placeable placeableMo1284measureBRTryo02 = measurable2.mo1284measureBRTryo0(rowColumnMeasurePolicy3.mo158createConstraintsxF2OJ5Q(i29, 0, iMax2, i12, true));
                    int iMainAxisSize2 = rowColumnMeasurePolicy3.mainAxisSize(placeableMo1284measureBRTryo02);
                    int iCrossAxisSize2 = rowColumnMeasurePolicy3.crossAxisSize(placeableMo1284measureBRTryo02);
                    iArr2[i27 - i6] = iMainAxisSize2;
                    i28 += iMainAxisSize2;
                    int iMax3 = Math.max(i25, iCrossAxisSize2);
                    placeableArr[i27] = placeableMo1284measureBRTryo02;
                    i25 = iMax3;
                    jRound = j5;
                }
                i27++;
                i12 = i4;
            }
            i9 = (int) (((long) i28) + j4);
            int i30 = i3 - i20;
            if (i9 < 0) {
                i9 = 0;
            }
            if (i9 > i30) {
                i9 = i30;
            }
        }
        int i31 = i25;
        if (i18 != 0) {
            for (int i32 = i6; i32 < i7; i32++) {
                Placeable placeable = placeableArr[i32];
                placeable.getClass();
                RowColumnImplKt.getCrossAxisAlignment(RowColumnImplKt.getRowColumnParentData(placeable));
            }
        }
        int i33 = i20 + i9;
        if (i33 < 0) {
            i33 = 0;
        }
        int iMax4 = Math.max(i33, i);
        int iMax5 = Math.max(i31, Math.max(i2, 0));
        int[] iArr3 = new int[i13];
        rowColumnMeasurePolicy3.populateMainAxisPositions(iMax4, iArr2, iArr3, measureScope);
        return rowColumnMeasurePolicy3.placeHelper(placeableArr, measureScope, 0, iArr3, iMax4, iMax5, iArr, i8, i6, i7);
    }
}
