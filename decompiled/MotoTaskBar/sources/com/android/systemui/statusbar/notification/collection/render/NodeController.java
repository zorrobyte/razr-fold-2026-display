package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;

/* JADX INFO: compiled from: NodeController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NodeController {
    default void addChildAt(NodeController nodeController, int i) {
        nodeController.getClass();
        throw new RuntimeException("Not supported");
    }

    default View getChildAt(int i) {
        throw new RuntimeException("Not supported");
    }

    default int getChildCount() {
        return 0;
    }

    String getNodeLabel();

    View getView();

    default void moveChildTo(NodeController nodeController, int i) {
        nodeController.getClass();
        throw new RuntimeException("Not supported");
    }

    boolean offerToKeepInParentForAnimation();

    default void onViewAdded() {
    }

    default void onViewMoved() {
    }

    default void onViewRemoved() {
    }

    default void removeChild(NodeController nodeController, boolean z) {
        nodeController.getClass();
        throw new RuntimeException("Not supported");
    }

    boolean removeFromParentIfKeptForAnimation();

    void resetKeepInParentForAnimation();
}
