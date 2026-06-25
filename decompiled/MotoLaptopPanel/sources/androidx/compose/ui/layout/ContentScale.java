package androidx.compose.ui.layout;

/* JADX INFO: compiled from: ContentScale.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ContentScale {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ContentScale.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ContentScale Crop = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Crop$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                float fM1276computeFillMaxDimensioniLBOSCw = ContentScaleKt.m1276computeFillMaxDimensioniLBOSCw(j, j2);
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fM1276computeFillMaxDimensioniLBOSCw)) << 32) | (((long) Float.floatToRawIntBits(fM1276computeFillMaxDimensioniLBOSCw)) & 4294967295L));
            }
        };
        private static final ContentScale Fit = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Fit$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                float fM1277computeFillMinDimensioniLBOSCw = ContentScaleKt.m1277computeFillMinDimensioniLBOSCw(j, j2);
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fM1277computeFillMinDimensioniLBOSCw)) << 32) | (((long) Float.floatToRawIntBits(fM1277computeFillMinDimensioniLBOSCw)) & 4294967295L));
            }
        };
        private static final ContentScale FillHeight = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillHeight$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) << 32) | (((long) Float.floatToRawIntBits(fIntBitsToFloat)) & 4294967295L));
            }
        };
        private static final ContentScale FillWidth = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillWidth$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) << 32) | (((long) Float.floatToRawIntBits(fIntBitsToFloat)) & 4294967295L));
            }
        };
        private static final ContentScale Inside = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Inside$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                if (Float.intBitsToFloat((int) (j >> 32)) <= Float.intBitsToFloat((int) (j2 >> 32)) && Float.intBitsToFloat((int) (j & 4294967295L)) <= Float.intBitsToFloat((int) (j2 & 4294967295L))) {
                    return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(1.0f)) << 32) | (((long) Float.floatToRawIntBits(1.0f)) & 4294967295L));
                }
                float fM1277computeFillMinDimensioniLBOSCw = ContentScaleKt.m1277computeFillMinDimensioniLBOSCw(j, j2);
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fM1277computeFillMinDimensioniLBOSCw)) << 32) | (((long) Float.floatToRawIntBits(fM1277computeFillMinDimensioniLBOSCw)) & 4294967295L));
            }
        };
        private static final FixedScale None = new FixedScale(1.0f);
        private static final ContentScale FillBounds = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillBounds$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
            public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
                float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) << 32) | (((long) Float.floatToRawIntBits(fIntBitsToFloat2)) & 4294967295L));
            }
        };

        private Companion() {
        }

        public final ContentScale getFit() {
            return Fit;
        }

        public final ContentScale getInside() {
            return Inside;
        }
    }

    /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA, reason: not valid java name */
    long mo1273computeScaleFactorH7hwNQA(long j, long j2);
}
