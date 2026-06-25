package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* JADX INFO: compiled from: RootNodeController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RootNodeController implements NodeController {
    private final NotificationListContainer listContainer;
    private final String nodeLabel;
    private final View view;

    public RootNodeController(NotificationListContainer notificationListContainer, View view) {
        notificationListContainer.getClass();
        view.getClass();
        this.listContainer = notificationListContainer;
        this.view = view;
        this.nodeLabel = "<root>";
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void addChildAt(NodeController nodeController, int i) {
        nodeController.getClass();
        this.listContainer.addContainerViewAt(nodeController.getView(), i);
        this.listContainer.onNotificationViewUpdateFinished();
        View view = nodeController.getView();
        ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.setChangingPosition(false);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getChildAt(int i) {
        return this.listContainer.getContainerChildAt(i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public int getChildCount() {
        return this.listContainer.getContainerChildCount();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getView() {
        return this.view;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void moveChildTo(NodeController nodeController, int i) {
        nodeController.getClass();
        NotificationListContainer notificationListContainer = this.listContainer;
        View view = nodeController.getView();
        view.getClass();
        notificationListContainer.changeViewPosition((ExpandableView) view, i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void removeChild(NodeController nodeController, boolean z) {
        nodeController.getClass();
        if (z) {
            this.listContainer.setChildTransferInProgress(true);
            View view = nodeController.getView();
            ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.setChangingPosition(true);
            }
        }
        this.listContainer.removeContainerView(nodeController.getView());
        if (z) {
            this.listContainer.setChildTransferInProgress(false);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void resetKeepInParentForAnimation() {
    }
}
