package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import android.view.View;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.Unit;

/* JADX INFO: compiled from: ShadeViewDiffer.kt */
/* JADX INFO: loaded from: classes.dex */
final class ShadeNode {
    private final NodeController controller;
    private ShadeNode parent;

    public ShadeNode(NodeController nodeController) {
        nodeController.getClass();
        this.controller = nodeController;
    }

    public final void addChildAt(ShadeNode shadeNode, int i) {
        shadeNode.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ShadeNode#addChildAt");
        }
        try {
            this.controller.addChildAt(shadeNode.controller, i);
            shadeNode.controller.onViewAdded();
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final View getChildAt(int i) {
        return this.controller.getChildAt(i);
    }

    public final int getChildCount() {
        return this.controller.getChildCount();
    }

    public final NodeController getController() {
        return this.controller;
    }

    public final String getLabel() {
        return this.controller.getNodeLabel();
    }

    public final ShadeNode getParent() {
        return this.parent;
    }

    public final View getView() {
        return this.controller.getView();
    }

    public final void moveChildTo(ShadeNode shadeNode, int i) {
        shadeNode.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ShadeNode#moveChildTo");
        }
        try {
            this.controller.moveChildTo(shadeNode.controller, i);
            shadeNode.controller.onViewMoved();
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final boolean offerToKeepInParentForAnimation() {
        return this.controller.offerToKeepInParentForAnimation();
    }

    public final void removeChild(ShadeNode shadeNode, boolean z) {
        shadeNode.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ShadeNode#removeChild");
        }
        try {
            this.controller.removeChild(shadeNode.controller, z);
            shadeNode.controller.onViewRemoved();
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final boolean removeFromParentIfKeptForAnimation() {
        return this.controller.removeFromParentIfKeptForAnimation();
    }

    public final void resetKeepInParentForAnimation() {
        this.controller.resetKeepInParentForAnimation();
    }

    public final void setParent(ShadeNode shadeNode) {
        this.parent = shadeNode;
    }
}
