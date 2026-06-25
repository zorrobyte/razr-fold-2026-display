package com.android.systemui.statusbar.notification.collection.render;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;

/* JADX INFO: compiled from: SectionHeaderController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SectionHeaderNodeControllerImpl implements NodeController, SectionHeaderController {
    private SectionHeaderView _view;
    private final ActivityStarter activityStarter;
    private boolean clearAllButtonEnabled;
    private View.OnClickListener clearAllClickListener;
    private final String clickIntentAction;
    private final int headerTextResId;
    private final LayoutInflater layoutInflater;
    private final String nodeLabel;
    private final View.OnClickListener onHeaderClickListener;

    public SectionHeaderNodeControllerImpl(String str, LayoutInflater layoutInflater, int i, ActivityStarter activityStarter, String str2) {
        str.getClass();
        layoutInflater.getClass();
        activityStarter.getClass();
        str2.getClass();
        this.nodeLabel = str;
        this.layoutInflater = layoutInflater;
        this.headerTextResId = i;
        this.activityStarter = activityStarter;
        this.clickIntentAction = str2;
        this.onHeaderClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl$onHeaderClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.activityStarter.startActivity(new Intent(this.this$0.clickIntentAction), true, true, 536870912);
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.SectionHeaderController
    public SectionHeaderView getHeaderView() {
        return this._view;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getView() {
        SectionHeaderView sectionHeaderView = this._view;
        sectionHeaderView.getClass();
        return sectionHeaderView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void onViewAdded() {
        SectionHeaderView headerView = getHeaderView();
        if (headerView != null) {
            headerView.setContentVisibleAnimated(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    @Override // com.android.systemui.statusbar.notification.collection.render.SectionHeaderController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void reinflateView(android.view.ViewGroup r6) {
        /*
            r5 = this;
            r6.getClass()
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r0 = r5._view
            r1 = -1
            if (r0 == 0) goto L19
            r0.removeFromTransientContainer()
            android.view.ViewParent r2 = r0.getParent()
            if (r2 != r6) goto L19
            int r2 = r6.indexOfChild(r0)
            r6.removeView(r0)
            goto L1a
        L19:
            r2 = r1
        L1a:
            android.view.LayoutInflater r0 = r5.layoutInflater
            int r3 = com.motorola.taskbar.R$layout.status_bar_notification_section_header
            r4 = 0
            android.view.View r0 = r0.inflate(r3, r6, r4)
            r0.getClass()
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r0 = (com.android.systemui.statusbar.notification.stack.SectionHeaderView) r0
            int r3 = r5.headerTextResId
            r0.setHeaderText(r3)
            android.view.View$OnClickListener r3 = r5.onHeaderClickListener
            r0.setOnHeaderClickListener(r3)
            android.view.View$OnClickListener r3 = r5.clearAllClickListener
            if (r3 == 0) goto L39
            r0.setOnClearAllClickListener(r3)
        L39:
            if (r2 == r1) goto L3e
            r6.addView(r0, r2)
        L3e:
            r5._view = r0
            boolean r5 = r5.clearAllButtonEnabled
            r0.setClearSectionButtonEnabled(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl.reinflateView(android.view.ViewGroup):void");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void resetKeepInParentForAnimation() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.SectionHeaderController
    public void setClearSectionEnabled(boolean z) {
        this.clearAllButtonEnabled = z;
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.setClearSectionButtonEnabled(z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.SectionHeaderController
    public void setOnClearSectionClickListener(View.OnClickListener onClickListener) {
        onClickListener.getClass();
        this.clearAllClickListener = onClickListener;
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.setOnClearAllClickListener(onClickListener);
        }
    }
}
