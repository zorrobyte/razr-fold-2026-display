package com.android.systemui.qs;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.motorola.systemui.desktop.widget.DesktopQSPanelArrowLayout;
import com.motorola.taskbar.R$id;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
public class QSPanelController extends QSPanelControllerBase {
    private boolean mListening;
    private final boolean mSceneContainerEnabled;

    QSPanelController(QSPanel qSPanel, QSHost qSHost, boolean z, MediaHost mediaHost, DumpManager dumpManager, Provider provider) {
        super(qSPanel, qSHost, z, mediaHost, dumpManager, provider);
        this.mSceneContainerEnabled = false;
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    protected void onConfigurationChanged() {
        ((QSPanel) this.mView).updateResources();
        if (((QSPanel) this.mView).isListening()) {
            refreshAllTiles();
        }
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase, com.android.systemui.util.ViewController
    public void onInit() {
        super.onInit();
        this.mMediaHost.setExpansion(1.0f);
        this.mMediaHost.setShowsOnlyActiveMedia(true);
        this.mMediaHost.init(0);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    protected void onSplitShadeChanged(boolean z) {
        ((PagedTileLayout) ((QSPanel) this.mView).getOrCreateTileLayout()).forceTilesRedistribution("Split shade state changed");
        ((QSPanel) this.mView).setCanCollapse(!z);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase, com.android.systemui.util.ViewController
    protected void onViewAttached() {
        super.onViewAttached();
        updateMediaDisappearParameters();
        ((QSPanel) this.mView).updateResources();
        ((QSPanel) this.mView).setSceneContainerEnabled(this.mSceneContainerEnabled);
        if (((QSPanel) this.mView).isListening()) {
            refreshAllTiles();
        }
        switchTileLayout(true);
        PagedTileLayout pagedTileLayout = (PagedTileLayout) ((QSPanel) this.mView).getOrCreateTileLayout();
        setFooterPageIndicator((PageIndicator) ((QSPanel) this.mView).findViewById(R$id.footer_page_indicator));
        ((DesktopQSPanelArrowLayout) ((QSPanel) this.mView).findViewById(R$id.qs_footer_page_arrow)).setPageTileLayout(pagedTileLayout);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase, com.android.systemui.util.ViewController
    protected void onViewDetached() {
        super.onViewDetached();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void refreshAllTiles() {
        super.refreshAllTiles();
    }

    public void setFooterPageIndicator(PageIndicator pageIndicator) {
        ((QSPanel) this.mView).setFooterPageIndicator(pageIndicator);
    }

    public void setListening(boolean z, boolean z2) {
        setListening(z && z2);
        if (z != this.mListening) {
            this.mListening = z;
        }
    }
}
