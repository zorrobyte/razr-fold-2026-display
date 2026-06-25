package androidx.compose.material3.internal;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: AccessibilityUtil.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AccessibilityUtilKt {
    private static final float HorizontalSemanticsBoundsPadding;
    private static final Modifier IncreaseHorizontalSemanticsBounds;
    private static final Modifier IncreaseVerticalSemanticsBounds;
    private static final float VerticalSemanticsBoundsPadding;

    static {
        float f = 10;
        float fM1877constructorimpl = Dp.m1877constructorimpl(f);
        HorizontalSemanticsBoundsPadding = fM1877constructorimpl;
        float fM1877constructorimpl2 = Dp.m1877constructorimpl(f);
        VerticalSemanticsBoundsPadding = fM1877constructorimpl2;
        Modifier.Companion companion = Modifier.Companion;
        IncreaseHorizontalSemanticsBounds = PaddingKt.m162paddingVpY3zN4$default(SemanticsModifierKt.semantics(LayoutModifierKt.layout(companion, new Function3() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return m348invoke3p2s80s((MeasureScope) obj, (Measurable) obj2, ((Constraints) obj3).m1865unboximpl());
            }

            /* JADX INFO: renamed from: invoke-3p2s80s, reason: not valid java name */
            public final MeasureResult m348invoke3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
                final int iMo141roundToPx0680j_4 = measureScope.mo141roundToPx0680j_4(AccessibilityUtilKt.getHorizontalSemanticsBoundsPadding());
                int i = iMo141roundToPx0680j_4 * 2;
                final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(ConstraintsKt.m1873offsetNN6EwU(j, i, 0));
                return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo0.getWidth() - i, placeableMo1284measureBRTryo0.getHeight(), null, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((Placeable.PlacementScope) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Placeable.PlacementScope placementScope) {
                        Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo0, -iMo141roundToPx0680j_4, 0, 0.0f, 4, null);
                    }
                }, 4, null);
            }
        }), true, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseHorizontalSemanticsBounds$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((SemanticsPropertyReceiver) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
            }
        }), fM1877constructorimpl, 0.0f, 2, null);
        IncreaseVerticalSemanticsBounds = PaddingKt.m162paddingVpY3zN4$default(SemanticsModifierKt.semantics(LayoutModifierKt.layout(companion, new Function3() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return m349invoke3p2s80s((MeasureScope) obj, (Measurable) obj2, ((Constraints) obj3).m1865unboximpl());
            }

            /* JADX INFO: renamed from: invoke-3p2s80s, reason: not valid java name */
            public final MeasureResult m349invoke3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
                final int iMo141roundToPx0680j_4 = measureScope.mo141roundToPx0680j_4(AccessibilityUtilKt.getVerticalSemanticsBoundsPadding());
                int i = iMo141roundToPx0680j_4 * 2;
                final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(ConstraintsKt.m1873offsetNN6EwU(j, 0, i));
                return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo0.getWidth(), placeableMo1284measureBRTryo0.getHeight() - i, null, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((Placeable.PlacementScope) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Placeable.PlacementScope placementScope) {
                        Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo0, 0, -iMo141roundToPx0680j_4, 0.0f, 4, null);
                    }
                }, 4, null);
            }
        }), true, new Function1() { // from class: androidx.compose.material3.internal.AccessibilityUtilKt$IncreaseVerticalSemanticsBounds$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((SemanticsPropertyReceiver) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
            }
        }), 0.0f, fM1877constructorimpl2, 1, null);
    }

    public static final float getHorizontalSemanticsBoundsPadding() {
        return HorizontalSemanticsBoundsPadding;
    }

    public static final Modifier getIncreaseHorizontalSemanticsBounds() {
        return IncreaseHorizontalSemanticsBounds;
    }

    public static final float getVerticalSemanticsBoundsPadding() {
        return VerticalSemanticsBoundsPadding;
    }
}
