package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.BigPictureNotificationImageView;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BigPictureLayoutInflaterFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BigPictureLayoutInflaterFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, View view, String str, Context context, AttributeSet attributeSet) {
        expandableNotificationRow.getClass();
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        if (i != 2 || !Intrinsics.areEqual(str, BigPictureNotificationImageView.class.getName())) {
            return null;
        }
        BigPictureNotificationImageView bigPictureNotificationImageView = new BigPictureNotificationImageView(context, attributeSet);
        bigPictureNotificationImageView.setIconManager(expandableNotificationRow.getBigPictureIconManager());
        return bigPictureNotificationImageView;
    }
}
