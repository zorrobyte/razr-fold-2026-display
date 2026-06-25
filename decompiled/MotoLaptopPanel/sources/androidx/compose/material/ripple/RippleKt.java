package androidx.compose.material.ripple;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.DelegatableNode;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: Ripple.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RippleKt {
    private static final TweenSpec DefaultTweenSpec = new TweenSpec(15, 0, EasingKt.getLinearEasing(), 2, null);

    /* JADX INFO: renamed from: createRippleModifierNode-TDGSqEk, reason: not valid java name */
    public static final DelegatableNode m235createRippleModifierNodeTDGSqEk(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0) {
        return Ripple_androidKt.m237createPlatformRippleNodeTDGSqEk(interactionSource, z, f, colorProducer, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AnimationSpec incomingStateLayerAnimationSpecFor(Interaction interaction) {
        if (interaction instanceof HoverInteraction$Enter) {
            return DefaultTweenSpec;
        }
        if (!(interaction instanceof FocusInteraction$Focus) && !(interaction instanceof DragInteraction$Start)) {
            return DefaultTweenSpec;
        }
        return new TweenSpec(45, 0, EasingKt.getLinearEasing(), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AnimationSpec outgoingStateLayerAnimationSpecFor(Interaction interaction) {
        if (!(interaction instanceof HoverInteraction$Enter) && !(interaction instanceof FocusInteraction$Focus) && (interaction instanceof DragInteraction$Start)) {
            return new TweenSpec(150, 0, EasingKt.getLinearEasing(), 2, null);
        }
        return DefaultTweenSpec;
    }
}
