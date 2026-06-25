package androidx.compose.foundation;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.view.KeyEvent;
import androidx.compose.foundation.gestures.ScrollableContainerNode;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.semantics.Role;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;

/* JADX INFO: compiled from: Clickable.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ClickableKt {
    /* JADX INFO: renamed from: clickable-O2vRcR0, reason: not valid java name */
    public static final Modifier m97clickableO2vRcR0(Modifier modifier, MutableInteractionSource mutableInteractionSource, final Indication indication, final boolean z, final String str, final Role role, final Function0 function0) {
        Modifier modifierComposed$default;
        if (indication instanceof IndicationNodeFactory) {
            modifierComposed$default = new ClickableElement(mutableInteractionSource, (IndicationNodeFactory) indication, z, str, role, function0, null);
        } else if (indication == null) {
            modifierComposed$default = new ClickableElement(mutableInteractionSource, null, z, str, role, function0, null);
        } else if (mutableInteractionSource != null) {
            modifierComposed$default = IndicationKt.indication(Modifier.Companion, mutableInteractionSource, indication).then(new ClickableElement(mutableInteractionSource, null, z, str, role, function0, null));
        } else {
            modifierComposed$default = ComposedModifierKt.composed$default(Modifier.Companion, null, new Function3() { // from class: androidx.compose.foundation.ClickableKt$clickable-O2vRcR0$$inlined$clickableWithIndicationIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                public final Modifier invoke(Modifier modifier2, Composer composer, int i) {
                    composer.startReplaceGroup(-1525724089);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1525724089, i, -1, "androidx.compose.foundation.clickableWithIndicationIfNeeded.<anonymous> (Clickable.kt:473)");
                    }
                    Object objRememberedValue = composer.rememberedValue();
                    if (objRememberedValue == Composer.Companion.getEmpty()) {
                        objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composer.updateRememberedValue(objRememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                    Modifier modifierThen = IndicationKt.indication(Modifier.Companion, mutableInteractionSource2, indication).then(new ClickableElement(mutableInteractionSource2, null, z, str, role, function0, null));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composer.endReplaceGroup();
                    return modifierThen;
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                    return invoke((Modifier) obj, (Composer) obj2, ((Number) obj3).intValue());
                }
            }, 1, null);
        }
        return modifier.then(modifierComposed$default);
    }

    /* JADX INFO: renamed from: clickable-O2vRcR0$default, reason: not valid java name */
    public static /* synthetic */ Modifier m98clickableO2vRcR0$default(Modifier modifier, MutableInteractionSource mutableInteractionSource, Indication indication, boolean z, String str, Role role, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return m97clickableO2vRcR0(modifier, mutableInteractionSource, indication, z, (i & 8) != 0 ? null : str, (i & 16) != 0 ? null : role, function0);
    }

    public static final boolean hasScrollableContainer(TraversableNode traversableNode) {
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        TraversableNodeKt.traverseAncestors(traversableNode, ScrollableContainerNode.TraverseKey, new Function1() { // from class: androidx.compose.foundation.ClickableKt.hasScrollableContainer.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TraversableNode traversableNode2) {
                Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                if (ref$BooleanRef2.element) {
                    ref$BooleanRef2.element = true;
                    return Boolean.valueOf(!true);
                }
                traversableNode2.getClass();
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(traversableNode2);
                throw null;
            }
        });
        return ref$BooleanRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: isClick-ZmokQxo, reason: not valid java name */
    public static final boolean m99isClickZmokQxo(KeyEvent keyEvent) {
        return KeyEventType.m1190equalsimpl0(KeyEvent_androidKt.m1195getTypeZmokQxo(keyEvent), KeyEventType.Companion.m1192getKeyUpCS__XNY()) && m100isEnterZmokQxo(keyEvent);
    }

    /* JADX INFO: renamed from: isEnter-ZmokQxo, reason: not valid java name */
    private static final boolean m100isEnterZmokQxo(KeyEvent keyEvent) {
        long jM1194getKeyZmokQxo = KeyEvent_androidKt.m1194getKeyZmokQxo(keyEvent);
        Key.Companion companion = Key.Companion;
        if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion.m1167getDirectionCenterEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion.m1172getEnterEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion.m1178getNumPadEnterEK5gGoQ())) {
            return true;
        }
        return Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion.m1181getSpacebarEK5gGoQ());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: isPress-ZmokQxo, reason: not valid java name */
    public static final boolean m101isPressZmokQxo(KeyEvent keyEvent) {
        return KeyEventType.m1190equalsimpl0(KeyEvent_androidKt.m1195getTypeZmokQxo(keyEvent), KeyEventType.Companion.m1191getKeyDownCS__XNY()) && m100isEnterZmokQxo(keyEvent);
    }
}
