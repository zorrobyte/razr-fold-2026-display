package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: compiled from: NotifRemoteViewsFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotifRemoteViewsFactory {
    View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, View view, String str, Context context, AttributeSet attributeSet);
}
