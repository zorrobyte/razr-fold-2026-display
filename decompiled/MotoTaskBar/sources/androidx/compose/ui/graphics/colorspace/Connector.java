package androidx.compose.ui.graphics.colorspace;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.colorspace.ColorModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Connector.kt */
/* JADX INFO: loaded from: classes.dex */
public class Connector {
    public static final Companion Companion = new Companion(null);
    private final ColorSpace destination;
    private final int renderIntent;
    private final ColorSpace source;
    private final float[] transform;
    private final ColorSpace transformDestination;
    private final ColorSpace transformSource;

    /* JADX INFO: compiled from: Connector.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: computeTransform-YBCOT_4, reason: not valid java name */
        public final float[] m364computeTransformYBCOT_4(ColorSpace colorSpace, ColorSpace colorSpace2, int i) {
            if (!RenderIntent.m367equalsimpl0(i, RenderIntent.Companion.m368getAbsoluteuksYyKA())) {
                return null;
            }
            long jM356getModelxdoWZVw = colorSpace.m356getModelxdoWZVw();
            ColorModel.Companion companion = ColorModel.Companion;
            boolean zM349equalsimpl0 = ColorModel.m349equalsimpl0(jM356getModelxdoWZVw, companion.m354getRgbxdoWZVw());
            boolean zM349equalsimpl02 = ColorModel.m349equalsimpl0(colorSpace2.m356getModelxdoWZVw(), companion.m354getRgbxdoWZVw());
            if (zM349equalsimpl0 && zM349equalsimpl02) {
                return null;
            }
            if (!zM349equalsimpl0 && !zM349equalsimpl02) {
                return null;
            }
            if (!zM349equalsimpl0) {
                colorSpace = colorSpace2;
            }
            Rgb rgb = (Rgb) colorSpace;
            float[] xyz$ui_graphics_release = zM349equalsimpl0 ? rgb.getWhitePoint().toXyz$ui_graphics_release() : Illuminant.INSTANCE.getD50Xyz$ui_graphics_release();
            float[] xyz$ui_graphics_release2 = zM349equalsimpl02 ? rgb.getWhitePoint().toXyz$ui_graphics_release() : Illuminant.INSTANCE.getD50Xyz$ui_graphics_release();
            return new float[]{xyz$ui_graphics_release[0] / xyz$ui_graphics_release2[0], xyz$ui_graphics_release[1] / xyz$ui_graphics_release2[1], xyz$ui_graphics_release[2] / xyz$ui_graphics_release2[2]};
        }

        public final Connector identity$ui_graphics_release(final ColorSpace colorSpace) {
            final int iM370getRelativeuksYyKA = RenderIntent.Companion.m370getRelativeuksYyKA();
            return new Connector(colorSpace, iM370getRelativeuksYyKA) { // from class: androidx.compose.ui.graphics.colorspace.Connector$Companion$identity$1
                {
                    super(colorSpace, colorSpace, iM370getRelativeuksYyKA, null);
                }

                @Override // androidx.compose.ui.graphics.colorspace.Connector
                /* JADX INFO: renamed from: transformToColor-l2rxGTc$ui_graphics_release */
                public long mo362transformToColorl2rxGTc$ui_graphics_release(long j) {
                    return j;
                }
            };
        }
    }

    /* JADX INFO: compiled from: Connector.kt */
    public final class RgbConnector extends Connector {
        private final Rgb mDestination;
        private final Rgb mSource;
        private final float[] mTransform;

        private RgbConnector(Rgb rgb, Rgb rgb2, int i) {
            super(rgb, rgb2, rgb, rgb2, i, null, null);
            this.mSource = rgb;
            this.mDestination = rgb2;
            this.mTransform = m365computeTransformYBCOT_4(rgb, rgb2, i);
        }

        public /* synthetic */ RgbConnector(Rgb rgb, Rgb rgb2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(rgb, rgb2, i);
        }

        /* JADX INFO: renamed from: computeTransform-YBCOT_4, reason: not valid java name */
        private final float[] m365computeTransformYBCOT_4(Rgb rgb, Rgb rgb2, int i) {
            if (ColorSpaceKt.compare(rgb.getWhitePoint(), rgb2.getWhitePoint())) {
                return ColorSpaceKt.mul3x3(rgb2.getInverseTransform$ui_graphics_release(), rgb.getTransform$ui_graphics_release());
            }
            float[] transform$ui_graphics_release = rgb.getTransform$ui_graphics_release();
            float[] inverseTransform$ui_graphics_release = rgb2.getInverseTransform$ui_graphics_release();
            float[] xyz$ui_graphics_release = rgb.getWhitePoint().toXyz$ui_graphics_release();
            float[] xyz$ui_graphics_release2 = rgb2.getWhitePoint().toXyz$ui_graphics_release();
            WhitePoint whitePoint = rgb.getWhitePoint();
            Illuminant illuminant = Illuminant.INSTANCE;
            if (!ColorSpaceKt.compare(whitePoint, illuminant.getD50())) {
                transform$ui_graphics_release = ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(Adaptation.Companion.getBradford().getTransform$ui_graphics_release(), xyz$ui_graphics_release, illuminant.newD50Xyz$ui_graphics_release()), rgb.getTransform$ui_graphics_release());
            }
            if (!ColorSpaceKt.compare(rgb2.getWhitePoint(), illuminant.getD50())) {
                inverseTransform$ui_graphics_release = ColorSpaceKt.inverse3x3(ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(Adaptation.Companion.getBradford().getTransform$ui_graphics_release(), xyz$ui_graphics_release2, illuminant.newD50Xyz$ui_graphics_release()), rgb2.getTransform$ui_graphics_release()));
            }
            if (RenderIntent.m367equalsimpl0(i, RenderIntent.Companion.m368getAbsoluteuksYyKA())) {
                transform$ui_graphics_release = ColorSpaceKt.mul3x3Diag(new float[]{xyz$ui_graphics_release[0] / xyz$ui_graphics_release2[0], xyz$ui_graphics_release[1] / xyz$ui_graphics_release2[1], xyz$ui_graphics_release[2] / xyz$ui_graphics_release2[2]}, transform$ui_graphics_release);
            }
            return ColorSpaceKt.mul3x3(inverseTransform$ui_graphics_release, transform$ui_graphics_release);
        }

        @Override // androidx.compose.ui.graphics.colorspace.Connector
        /* JADX INFO: renamed from: transformToColor-l2rxGTc$ui_graphics_release */
        public long mo362transformToColorl2rxGTc$ui_graphics_release(long j) {
            float fM283getRedimpl = Color.m283getRedimpl(j);
            float fM282getGreenimpl = Color.m282getGreenimpl(j);
            float fM280getBlueimpl = Color.m280getBlueimpl(j);
            float fM279getAlphaimpl = Color.m279getAlphaimpl(j);
            float fInvoke = (float) this.mSource.getEotfFunc$ui_graphics_release().invoke(fM283getRedimpl);
            float fInvoke2 = (float) this.mSource.getEotfFunc$ui_graphics_release().invoke(fM282getGreenimpl);
            float fInvoke3 = (float) this.mSource.getEotfFunc$ui_graphics_release().invoke(fM280getBlueimpl);
            float[] fArr = this.mTransform;
            return ColorKt.Color((float) this.mDestination.getOetfFunc$ui_graphics_release().invoke((fArr[0] * fInvoke) + (fArr[3] * fInvoke2) + (fArr[6] * fInvoke3)), (float) this.mDestination.getOetfFunc$ui_graphics_release().invoke((fArr[1] * fInvoke) + (fArr[4] * fInvoke2) + (fArr[7] * fInvoke3)), (float) this.mDestination.getOetfFunc$ui_graphics_release().invoke((fArr[2] * fInvoke) + (fArr[5] * fInvoke2) + (fArr[8] * fInvoke3)), fM279getAlphaimpl, this.mDestination);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, int i) {
        long jM356getModelxdoWZVw = colorSpace.m356getModelxdoWZVw();
        ColorModel.Companion companion = ColorModel.Companion;
        this(colorSpace, colorSpace2, ColorModel.m349equalsimpl0(jM356getModelxdoWZVw, companion.m354getRgbxdoWZVw()) ? ColorSpaceKt.adapt$default(colorSpace, Illuminant.INSTANCE.getD50(), null, 2, null) : colorSpace, ColorModel.m349equalsimpl0(colorSpace2.m356getModelxdoWZVw(), companion.m354getRgbxdoWZVw()) ? ColorSpaceKt.adapt$default(colorSpace2, Illuminant.INSTANCE.getD50(), null, 2, null) : colorSpace2, i, Companion.m364computeTransformYBCOT_4(colorSpace, colorSpace2, i), null);
    }

    public /* synthetic */ Connector(ColorSpace colorSpace, ColorSpace colorSpace2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(colorSpace, colorSpace2, i);
    }

    private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, int i, float[] fArr) {
        this.source = colorSpace;
        this.destination = colorSpace2;
        this.transformSource = colorSpace3;
        this.transformDestination = colorSpace4;
        this.renderIntent = i;
        this.transform = fArr;
    }

    public /* synthetic */ Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, int i, float[] fArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(colorSpace, colorSpace2, colorSpace3, colorSpace4, i, fArr);
    }

    /* JADX INFO: renamed from: transformToColor-l2rxGTc$ui_graphics_release, reason: not valid java name */
    public long mo362transformToColorl2rxGTc$ui_graphics_release(long j) {
        float fM283getRedimpl = Color.m283getRedimpl(j);
        float fM282getGreenimpl = Color.m282getGreenimpl(j);
        float fM280getBlueimpl = Color.m280getBlueimpl(j);
        float fM279getAlphaimpl = Color.m279getAlphaimpl(j);
        long xy$ui_graphics_release = this.transformSource.toXy$ui_graphics_release(fM283getRedimpl, fM282getGreenimpl, fM280getBlueimpl);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (xy$ui_graphics_release >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (xy$ui_graphics_release & 4294967295L));
        float z$ui_graphics_release = this.transformSource.toZ$ui_graphics_release(fM283getRedimpl, fM282getGreenimpl, fM280getBlueimpl);
        float[] fArr = this.transform;
        if (fArr != null) {
            fIntBitsToFloat *= fArr[0];
            fIntBitsToFloat2 *= fArr[1];
            z$ui_graphics_release *= fArr[2];
        }
        float f = fIntBitsToFloat;
        return this.transformDestination.mo357xyzaToColorJlNiLsg$ui_graphics_release(f, fIntBitsToFloat2, z$ui_graphics_release, fM279getAlphaimpl, this.destination);
    }
}
