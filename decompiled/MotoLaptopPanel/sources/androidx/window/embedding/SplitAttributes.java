package androidx.window.embedding;

import androidx.window.core.SpecificationComputer;
import androidx.window.core.VerificationMode;
import androidx.window.embedding.EmbeddingAnimationParams;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SplitAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SplitAttributes {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = SplitAttributes.class.getSimpleName();
    private final EmbeddingAnimationParams animationParams;
    private final DividerAttributes dividerAttributes;
    private final LayoutDirection layoutDirection;
    private final SplitType splitType;

    /* JADX INFO: compiled from: SplitAttributes.kt */
    public final class Builder {
        private SplitType splitType = SplitType.SPLIT_TYPE_EQUAL;
        private LayoutDirection layoutDirection = LayoutDirection.LOCALE;
        private EmbeddingAnimationParams animationParams = new EmbeddingAnimationParams.Builder().build();
        private DividerAttributes dividerAttributes = DividerAttributes.NO_DIVIDER;

        public final SplitAttributes build() {
            return new SplitAttributes(this.splitType, this.layoutDirection, this.animationParams, this.dividerAttributes, null);
        }

        public final Builder setAnimationParams(EmbeddingAnimationParams embeddingAnimationParams) {
            embeddingAnimationParams.getClass();
            this.animationParams = embeddingAnimationParams;
            return this;
        }

        public final Builder setDividerAttributes(DividerAttributes dividerAttributes) {
            dividerAttributes.getClass();
            this.dividerAttributes = dividerAttributes;
            return this;
        }

        public final Builder setLayoutDirection(LayoutDirection layoutDirection) {
            layoutDirection.getClass();
            this.layoutDirection = layoutDirection;
            return this;
        }

        public final Builder setSplitType(SplitType splitType) {
            splitType.getClass();
            this.splitType = splitType;
            return this;
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    public final class LayoutDirection {
        private final String description;
        private final int value;
        public static final Companion Companion = new Companion(null);
        public static final LayoutDirection LOCALE = new LayoutDirection("LOCALE", 0);
        public static final LayoutDirection LEFT_TO_RIGHT = new LayoutDirection("LEFT_TO_RIGHT", 1);
        public static final LayoutDirection RIGHT_TO_LEFT = new LayoutDirection("RIGHT_TO_LEFT", 2);
        public static final LayoutDirection TOP_TO_BOTTOM = new LayoutDirection("TOP_TO_BOTTOM", 3);
        public static final LayoutDirection BOTTOM_TO_TOP = new LayoutDirection("BOTTOM_TO_TOP", 4);

        /* JADX INFO: compiled from: SplitAttributes.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private LayoutDirection(String str, int i) {
            this.description = str;
            this.value = i;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LayoutDirection)) {
                return false;
            }
            LayoutDirection layoutDirection = (LayoutDirection) obj;
            return Intrinsics.areEqual(this.description, layoutDirection.description) && this.value == layoutDirection.value;
        }

        public int hashCode() {
            return (this.description.hashCode() * 31) + this.value;
        }

        public String toString() {
            return this.description;
        }
    }

    /* JADX INFO: compiled from: SplitAttributes.kt */
    public final class SplitType {
        public static final Companion Companion;
        public static final SplitType SPLIT_TYPE_EQUAL;
        public static final SplitType SPLIT_TYPE_EXPAND;
        public static final SplitType SPLIT_TYPE_HINGE;
        private final String description;
        private final float value;

        /* JADX INFO: compiled from: SplitAttributes.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final SplitType buildSplitTypeFromValue$window_release(float f) {
                SplitType splitType = SplitType.SPLIT_TYPE_EXPAND;
                return f == splitType.getValue$window_release() ? splitType : ratio(f);
            }

            public final SplitType ratio(final float f) {
                SpecificationComputer.Companion companion = SpecificationComputer.Companion;
                Float fValueOf = Float.valueOf(f);
                String str = SplitAttributes.TAG;
                str.getClass();
                Object objCompute = SpecificationComputer.Companion.startSpecification$default(companion, fValueOf, str, VerificationMode.STRICT, null, 4, null).require("Ratio must be in range (0.0, 1.0). Use SplitType.expandContainers() instead of 0 or 1.", new Function1() { // from class: androidx.window.embedding.SplitAttributes$SplitType$Companion$ratio$checkedRatio$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    public final Boolean invoke(float f2) {
                        double d = f;
                        return Boolean.valueOf(0.0d <= d && d <= 1.0d && !ArraysKt.contains(new Float[]{Float.valueOf(0.0f), Float.valueOf(1.0f)}, Float.valueOf(f)));
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return invoke(((Number) obj).floatValue());
                    }
                }).compute();
                objCompute.getClass();
                float fFloatValue = ((Number) objCompute).floatValue();
                return new SplitType("ratio:" + fFloatValue, fFloatValue);
            }
        }

        static {
            Companion companion = new Companion(null);
            Companion = companion;
            SPLIT_TYPE_EXPAND = new SplitType("expandContainers", 0.0f);
            SPLIT_TYPE_EQUAL = companion.ratio(0.5f);
            SPLIT_TYPE_HINGE = new SplitType("hinge", -1.0f);
        }

        public SplitType(String str, float f) {
            str.getClass();
            this.description = str;
            this.value = f;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitType)) {
                return false;
            }
            SplitType splitType = (SplitType) obj;
            return this.value == splitType.value && Intrinsics.areEqual(this.description, splitType.description);
        }

        public final float getValue$window_release() {
            return this.value;
        }

        public int hashCode() {
            return this.description.hashCode() + (Float.hashCode(this.value) * 31);
        }

        public String toString() {
            return this.description;
        }
    }

    private SplitAttributes(SplitType splitType, LayoutDirection layoutDirection, EmbeddingAnimationParams embeddingAnimationParams, DividerAttributes dividerAttributes) {
        this.splitType = splitType;
        this.layoutDirection = layoutDirection;
        this.animationParams = embeddingAnimationParams;
        this.dividerAttributes = dividerAttributes;
    }

    public /* synthetic */ SplitAttributes(SplitType splitType, LayoutDirection layoutDirection, EmbeddingAnimationParams embeddingAnimationParams, DividerAttributes dividerAttributes, DefaultConstructorMarker defaultConstructorMarker) {
        this(splitType, layoutDirection, embeddingAnimationParams, dividerAttributes);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitAttributes)) {
            return false;
        }
        SplitAttributes splitAttributes = (SplitAttributes) obj;
        return Intrinsics.areEqual(this.splitType, splitAttributes.splitType) && Intrinsics.areEqual(this.layoutDirection, splitAttributes.layoutDirection) && Intrinsics.areEqual(this.animationParams, splitAttributes.animationParams) && Intrinsics.areEqual(this.dividerAttributes, splitAttributes.dividerAttributes);
    }

    public int hashCode() {
        return (((((this.splitType.hashCode() * 31) + this.layoutDirection.hashCode()) * 31) + this.animationParams.hashCode()) * 31) + this.dividerAttributes.hashCode();
    }

    public String toString() {
        return SplitAttributes.class.getSimpleName() + ":{splitType=" + this.splitType + ", layoutDir=" + this.layoutDirection + ", animationParams=" + this.animationParams + ", dividerAttributes=" + this.dividerAttributes + " }";
    }
}
