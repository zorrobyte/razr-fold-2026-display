package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnterExitTransition.kt */
/* JADX INFO: loaded from: classes.dex */
final class EnterExitTransitionElement extends ModifierNodeElement {
    private EnterTransition enter;
    private ExitTransition exit;
    private GraphicsLayerBlockForEnterExit graphicsLayerBlock;
    private Function0 isEnabled;
    private Transition.DeferredAnimation offsetAnimation;
    private Transition.DeferredAnimation sizeAnimation;
    private Transition.DeferredAnimation slideAnimation;
    private final Transition transition;

    public EnterExitTransitionElement(Transition transition, Transition.DeferredAnimation deferredAnimation, Transition.DeferredAnimation deferredAnimation2, Transition.DeferredAnimation deferredAnimation3, EnterTransition enterTransition, ExitTransition exitTransition, Function0 function0, GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit) {
        this.transition = transition;
        this.sizeAnimation = deferredAnimation;
        this.offsetAnimation = deferredAnimation2;
        this.slideAnimation = deferredAnimation3;
        this.enter = enterTransition;
        this.exit = exitTransition;
        this.isEnabled = function0;
        this.graphicsLayerBlock = graphicsLayerBlockForEnterExit;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public EnterExitTransitionModifierNode create() {
        return new EnterExitTransitionModifierNode(this.transition, this.sizeAnimation, this.offsetAnimation, this.slideAnimation, this.enter, this.exit, this.isEnabled, this.graphicsLayerBlock);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnterExitTransitionElement)) {
            return false;
        }
        EnterExitTransitionElement enterExitTransitionElement = (EnterExitTransitionElement) obj;
        return Intrinsics.areEqual(this.transition, enterExitTransitionElement.transition) && Intrinsics.areEqual(this.sizeAnimation, enterExitTransitionElement.sizeAnimation) && Intrinsics.areEqual(this.offsetAnimation, enterExitTransitionElement.offsetAnimation) && Intrinsics.areEqual(this.slideAnimation, enterExitTransitionElement.slideAnimation) && Intrinsics.areEqual(this.enter, enterExitTransitionElement.enter) && Intrinsics.areEqual(this.exit, enterExitTransitionElement.exit) && Intrinsics.areEqual(this.isEnabled, enterExitTransitionElement.isEnabled) && Intrinsics.areEqual(this.graphicsLayerBlock, enterExitTransitionElement.graphicsLayerBlock);
    }

    public int hashCode() {
        int iHashCode = this.transition.hashCode() * 31;
        Transition.DeferredAnimation deferredAnimation = this.sizeAnimation;
        int iHashCode2 = (iHashCode + (deferredAnimation == null ? 0 : deferredAnimation.hashCode())) * 31;
        Transition.DeferredAnimation deferredAnimation2 = this.offsetAnimation;
        int iHashCode3 = (iHashCode2 + (deferredAnimation2 == null ? 0 : deferredAnimation2.hashCode())) * 31;
        Transition.DeferredAnimation deferredAnimation3 = this.slideAnimation;
        return ((((((((iHashCode3 + (deferredAnimation3 != null ? deferredAnimation3.hashCode() : 0)) * 31) + this.enter.hashCode()) * 31) + this.exit.hashCode()) * 31) + this.isEnabled.hashCode()) * 31) + this.graphicsLayerBlock.hashCode();
    }

    public String toString() {
        return "EnterExitTransitionElement(transition=" + this.transition + ", sizeAnimation=" + this.sizeAnimation + ", offsetAnimation=" + this.offsetAnimation + ", slideAnimation=" + this.slideAnimation + ", enter=" + this.enter + ", exit=" + this.exit + ", isEnabled=" + this.isEnabled + ", graphicsLayerBlock=" + this.graphicsLayerBlock + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(EnterExitTransitionModifierNode enterExitTransitionModifierNode) {
        enterExitTransitionModifierNode.setTransition(this.transition);
        enterExitTransitionModifierNode.setSizeAnimation(this.sizeAnimation);
        enterExitTransitionModifierNode.setOffsetAnimation(this.offsetAnimation);
        enterExitTransitionModifierNode.setSlideAnimation(this.slideAnimation);
        enterExitTransitionModifierNode.setEnter(this.enter);
        enterExitTransitionModifierNode.setExit(this.exit);
        enterExitTransitionModifierNode.setEnabled(this.isEnabled);
        enterExitTransitionModifierNode.setGraphicsLayerBlock(this.graphicsLayerBlock);
    }
}
