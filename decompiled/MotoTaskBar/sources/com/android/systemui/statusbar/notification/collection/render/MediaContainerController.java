package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.statusbar.notification.stack.MediaContainerView;

/* JADX INFO: compiled from: MediaContainerController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaContainerController implements NodeController {
    private final LayoutInflater layoutInflater;
    private MediaContainerView mediaContainerView;
    private final String nodeLabel;

    public MediaContainerController(LayoutInflater layoutInflater) {
        layoutInflater.getClass();
        this.layoutInflater = layoutInflater;
        this.nodeLabel = "MediaContainer";
    }

    public final MediaContainerView getMediaContainerView() {
        return this.mediaContainerView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getView() {
        MediaContainerView mediaContainerView = this.mediaContainerView;
        mediaContainerView.getClass();
        return mediaContainerView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean offerToKeepInParentForAnimation() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void reinflateView(android.view.ViewGroup r6) {
        /*
            r5 = this;
            r6.getClass()
            com.android.systemui.statusbar.notification.stack.MediaContainerView r0 = r5.mediaContainerView
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
            int r3 = com.motorola.taskbar.R$layout.keyguard_media_container
            r4 = 0
            android.view.View r0 = r0.inflate(r3, r6, r4)
            r0.getClass()
            com.android.systemui.statusbar.notification.stack.MediaContainerView r0 = (com.android.systemui.statusbar.notification.stack.MediaContainerView) r0
            if (r2 == r1) goto L2d
            r6.addView(r0, r2)
        L2d:
            r5.mediaContainerView = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.render.MediaContainerController.reinflateView(android.view.ViewGroup):void");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void resetKeepInParentForAnimation() {
    }
}
