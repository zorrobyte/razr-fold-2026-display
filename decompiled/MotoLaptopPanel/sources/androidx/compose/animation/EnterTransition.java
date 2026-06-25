package androidx.compose.animation;

import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnterExitTransition.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class EnterTransition {
    public static final Companion Companion = new Companion(null);
    private static final EnterTransition None = new EnterTransitionImpl(new TransitionData(null, null, null, null, false, null, 63, null));

    /* JADX INFO: compiled from: EnterExitTransition.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final EnterTransition getNone() {
            return EnterTransition.None;
        }
    }

    private EnterTransition() {
    }

    public /* synthetic */ EnterTransition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public boolean equals(Object obj) {
        return (obj instanceof EnterTransition) && Intrinsics.areEqual(((EnterTransition) obj).getData$animation(), getData$animation());
    }

    public abstract TransitionData getData$animation();

    public int hashCode() {
        return getData$animation().hashCode();
    }

    public final EnterTransition plus(EnterTransition enterTransition) {
        Fade fade = enterTransition.getData$animation().getFade();
        if (fade == null) {
            fade = getData$animation().getFade();
        }
        enterTransition.getData$animation().getSlide();
        getData$animation().getSlide();
        ChangeSize changeSize = enterTransition.getData$animation().getChangeSize();
        if (changeSize == null) {
            changeSize = getData$animation().getChangeSize();
        }
        ChangeSize changeSize2 = changeSize;
        enterTransition.getData$animation().getScale();
        getData$animation().getScale();
        return new EnterTransitionImpl(new TransitionData(fade, null, changeSize2, null, false, MapsKt.plus(getData$animation().getEffectsMap(), enterTransition.getData$animation().getEffectsMap()), 16, null));
    }

    public String toString() {
        if (Intrinsics.areEqual(this, None)) {
            return "EnterTransition.None";
        }
        TransitionData data$animation = getData$animation();
        StringBuilder sb = new StringBuilder();
        sb.append("EnterTransition: \nFade - ");
        Fade fade = data$animation.getFade();
        sb.append(fade != null ? fade.toString() : null);
        sb.append(",\nSlide - ");
        data$animation.getSlide();
        sb.append((String) null);
        sb.append(",\nShrink - ");
        ChangeSize changeSize = data$animation.getChangeSize();
        sb.append(changeSize != null ? changeSize.toString() : null);
        sb.append(",\nScale - ");
        data$animation.getScale();
        sb.append((String) null);
        return sb.toString();
    }
}
