package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;

/* JADX INFO: compiled from: SectionHeaderController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SectionHeaderController {
    SectionHeaderView getHeaderView();

    void reinflateView(ViewGroup viewGroup);

    void setClearSectionEnabled(boolean z);

    void setOnClearSectionClickListener(View.OnClickListener onClickListener);
}
