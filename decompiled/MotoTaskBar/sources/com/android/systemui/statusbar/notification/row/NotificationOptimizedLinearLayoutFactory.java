package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.widget.NotificationOptimizedLinearLayout;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationOptimizedLinearLayoutFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationOptimizedLinearLayoutFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, View view, String str, Context context, AttributeSet attributeSet) {
        expandableNotificationRow.getClass();
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        if (Intrinsics.areEqual(str, LinearLayout.class.getName()) || Intrinsics.areEqual(str, LinearLayout.class.getSimpleName())) {
            return new NotificationOptimizedLinearLayout(context, attributeSet);
        }
        return null;
    }
}
