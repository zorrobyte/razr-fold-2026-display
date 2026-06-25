package androidx.compose.animation;

import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnterExitTransition.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ExitTransition {
    public static final Companion Companion = new Companion(null);
    private static final ExitTransition None = new ExitTransitionImpl(new TransitionData(null, null, null, null, false, null, 63, null));
    private static final ExitTransition KeepUntilTransitionsFinished = new ExitTransitionImpl(new TransitionData(null, null, null, null, true, null, 47, null));

    /* JADX INFO: compiled from: EnterExitTransition.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ExitTransition getNone() {
            return ExitTransition.None;
        }
    }

    private ExitTransition() {
    }

    public /* synthetic */ ExitTransition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ExitTransition) && Intrinsics.areEqual(((ExitTransition) obj).getData$animation(), getData$animation());
    }

    public abstract TransitionData getData$animation();

    public int hashCode() {
        return getData$animation().hashCode();
    }

    public final ExitTransition plus(ExitTransition exitTransition) {
        Fade fade = exitTransition.getData$animation().getFade();
        if (fade == null) {
            fade = getData$animation().getFade();
        }
        exitTransition.getData$animation().getSlide();
        getData$animation().getSlide();
        ChangeSize changeSize = exitTransition.getData$animation().getChangeSize();
        if (changeSize == null) {
            changeSize = getData$animation().getChangeSize();
        }
        ChangeSize changeSize2 = changeSize;
        exitTransition.getData$animation().getScale();
        getData$animation().getScale();
        return new ExitTransitionImpl(new TransitionData(fade, null, changeSize2, null, exitTransition.getData$animation().getHold() || getData$animation().getHold(), MapsKt.plus(getData$animation().getEffectsMap(), exitTransition.getData$animation().getEffectsMap())));
    }

    public String toString() {
        if (Intrinsics.areEqual(this, None)) {
            return "ExitTransition.None";
        }
        if (Intrinsics.areEqual(this, KeepUntilTransitionsFinished)) {
            return "ExitTransition.KeepUntilTransitionsFinished";
        }
        TransitionData data$animation = getData$animation();
        StringBuilder sb = new StringBuilder();
        sb.append("ExitTransition: \nFade - ");
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
        sb.append(",\nKeepUntilTransitionsFinished - ");
        sb.append(data$animation.getHold());
        return sb.toString();
    }
}
