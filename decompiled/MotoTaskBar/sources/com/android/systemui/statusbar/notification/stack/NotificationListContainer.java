package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationListContainer extends ExpandableView.OnHeightChangedListener, VisibilityLocationProvider {
    void addContainerViewAt(View view, int i);

    void bindRow(ExpandableNotificationRow expandableNotificationRow);

    void changeViewPosition(ExpandableView expandableView, int i);

    void cleanUpViewStateForEntry(NotificationEntry notificationEntry);

    View getContainerChildAt(int i);

    int getContainerChildCount();

    ViewGroup getViewParentForNotification(NotificationEntry notificationEntry);

    void notifyGroupChildAdded(ExpandableView expandableView);

    void notifyGroupChildRemoved(ExpandableView expandableView, ViewGroup viewGroup);

    default void onNotificationViewUpdateFinished() {
    }

    void removeContainerView(View view);

    void setChildLocationsChangedListener(NotificationLogger.OnChildLocationsChangedListener onChildLocationsChangedListener);

    void setChildTransferInProgress(boolean z);
}
