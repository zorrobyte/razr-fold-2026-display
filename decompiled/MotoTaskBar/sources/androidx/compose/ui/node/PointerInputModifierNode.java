package androidx.compose.ui.node;

import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;

/* JADX INFO: compiled from: PointerInputModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PointerInputModifierNode extends DelegatableNode {
    /* JADX INFO: renamed from: getTouchBoundsExpansion-RZrCHBk, reason: not valid java name */
    default long m666getTouchBoundsExpansionRZrCHBk() {
        return TouchBoundsExpansion.Companion.m679getNoneRZrCHBk();
    }

    boolean interceptOutOfBoundsChildEvents();

    void onCancelPointerInput();

    /* JADX INFO: renamed from: onPointerEvent-H0pRuoY */
    void mo555onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j);

    default void onViewConfigurationChange() {
        onCancelPointerInput();
    }

    boolean sharePointerInputWithSiblings();
}
