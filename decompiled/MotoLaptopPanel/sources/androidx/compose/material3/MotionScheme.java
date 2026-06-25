package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.tokens.StandardMotionTokens;

/* JADX INFO: compiled from: MotionScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MotionScheme {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: MotionScheme.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final MotionScheme standard() {
            return new MotionScheme() { // from class: androidx.compose.material3.MotionScheme$Companion$standard$1
                private final SpringSpec defaultEffectsSpec;
                private final SpringSpec defaultSpatialSpec;
                private final SpringSpec fastEffectsSpec;
                private final SpringSpec fastSpatialSpec;
                private final SpringSpec slowEffectsSpec;
                private final SpringSpec slowSpatialSpec;

                {
                    StandardMotionTokens standardMotionTokens = StandardMotionTokens.INSTANCE;
                    this.defaultSpatialSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringDefaultSpatialDamping(), standardMotionTokens.getSpringDefaultSpatialStiffness(), null, 4, null);
                    this.fastSpatialSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringFastSpatialDamping(), standardMotionTokens.getSpringFastSpatialStiffness(), null, 4, null);
                    this.slowSpatialSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringSlowSpatialDamping(), standardMotionTokens.getSpringSlowSpatialStiffness(), null, 4, null);
                    this.defaultEffectsSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringDefaultEffectsDamping(), standardMotionTokens.getSpringDefaultEffectsStiffness(), null, 4, null);
                    this.fastEffectsSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringFastEffectsDamping(), standardMotionTokens.getSpringFastEffectsStiffness(), null, 4, null);
                    this.slowEffectsSpec = AnimationSpecKt.spring$default(standardMotionTokens.getSpringSlowEffectsDamping(), standardMotionTokens.getSpringSlowEffectsStiffness(), null, 4, null);
                }
            };
        }
    }
}
