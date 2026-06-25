package com.android.systemui.qs;

import android.content.res.Configuration;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.animation.DisappearParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes.dex */
public abstract class QSPanelControllerBase extends ViewController implements Dumpable {
    private String mCachedSpecs;
    private final DumpManager mDumpManager;
    protected final QSHost mHost;
    private int mLastOrientation;
    private int mLastScreenLayout;
    private final Provider mLongPressEffectProvider;
    protected final MediaHost mMediaHost;
    private final Function1 mMediaHostVisibilityListener;
    private Consumer mMediaVisibilityChangedListener;
    protected final QSPanel.OnConfigurationChangedListener mOnConfigurationChangedListener;
    protected final ArrayList mRecords;
    protected boolean mShouldUseSplitNotificationShade;
    private boolean mUsingHorizontalLayout;
    private Runnable mUsingHorizontalLayoutChangedListener;
    private final boolean mUsingMediaPlayer;

    public final class TileRecord {
        public QSTile.Callback callback;
        public QSTile tile;
        public QSTileView tileView;

        public TileRecord(QSTile qSTile, QSTileView qSTileView) {
            this.tile = qSTile;
            this.tileView = qSTileView;
        }
    }

    protected QSPanelControllerBase(QSPanel qSPanel, QSHost qSHost, boolean z, MediaHost mediaHost, DumpManager dumpManager, Provider provider) {
        super(qSPanel);
        this.mRecords = new ArrayList();
        this.mCachedSpecs = "";
        this.mOnConfigurationChangedListener = new QSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.QSPanelControllerBase.1
            @Override // com.android.systemui.qs.QSPanel.OnConfigurationChangedListener
            public void onConfigurationChange(Configuration configuration) {
                QSPanelControllerBase qSPanelControllerBase = QSPanelControllerBase.this;
                boolean z2 = qSPanelControllerBase.mShouldUseSplitNotificationShade;
                int unused = qSPanelControllerBase.mLastOrientation;
                int unused2 = QSPanelControllerBase.this.mLastScreenLayout;
                QSPanelControllerBase qSPanelControllerBase2 = QSPanelControllerBase.this;
                qSPanelControllerBase2.mShouldUseSplitNotificationShade = false;
                qSPanelControllerBase2.mLastOrientation = configuration.orientation;
                QSPanelControllerBase.this.mLastScreenLayout = configuration.screenLayout;
                QSPanelControllerBase.this.switchTileLayoutIfNeeded();
                QSPanelControllerBase.this.onConfigurationChanged();
                QSPanelControllerBase qSPanelControllerBase3 = QSPanelControllerBase.this;
                boolean z3 = qSPanelControllerBase3.mShouldUseSplitNotificationShade;
                if (z2 != z3) {
                    qSPanelControllerBase3.onSplitShadeChanged(z3);
                }
            }
        };
        this.mMediaHostVisibilityListener = new Function1() { // from class: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.lambda$new$0((Boolean) obj);
            }
        };
        this.mHost = qSHost;
        this.mUsingMediaPlayer = z;
        this.mMediaHost = mediaHost;
        this.mDumpManager = dumpManager;
        this.mShouldUseSplitNotificationShade = false;
        this.mLongPressEffectProvider = provider;
    }

    private void addTile(QSTile qSTile, boolean z) {
        TileRecord tileRecord = new TileRecord(qSTile, new QSTileViewImpl(getContext(), z, FlagsFake.quickSettingsVisualHapticsLongpress() ? (QSLongPressEffect) this.mLongPressEffectProvider.get() : null));
        ((QSPanel) this.mView).addTile(tileRecord);
        this.mRecords.add(tileRecord);
        this.mCachedSpecs = getTilesSpecs();
    }

    private String getTilesSpecs() {
        return (String) this.mRecords.stream().map(new Function() { // from class: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((QSPanelControllerBase.TileRecord) obj).tile.getTileSpec();
            }
        }).collect(Collectors.joining(","));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$new$0(Boolean bool) {
        Consumer consumer = this.mMediaVisibilityChangedListener;
        if (consumer != null) {
            consumer.accept(bool);
        }
        switchTileLayout(false);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchTileLayoutIfNeeded() {
        switchTileLayout(false);
    }

    public MediaHost getMediaHost() {
        return this.mMediaHost;
    }

    protected abstract void onConfigurationChanged();

    @Override // com.android.systemui.util.ViewController
    protected void onInit() {
        ((QSPanel) this.mView).initialize(this.mUsingMediaPlayer);
    }

    protected abstract void onSplitShadeChanged(boolean z);

    @Override // com.android.systemui.util.ViewController
    protected void onViewAttached() {
        this.mMediaHost.addVisibilityChangeListener(this.mMediaHostVisibilityListener);
        ((QSPanel) this.mView).addOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        setTiles();
        this.mLastOrientation = getResources().getConfiguration().orientation;
        this.mLastScreenLayout = getResources().getConfiguration().screenLayout;
        switchTileLayout(true);
        this.mDumpManager.registerDumpable(((QSPanel) this.mView).getDumpableTag(), this);
    }

    @Override // com.android.systemui.util.ViewController
    protected void onViewDetached() {
        ((QSPanel) this.mView).removeOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        ((QSPanel) this.mView).getTileLayout().setListening(false);
        this.mMediaHost.removeVisibilityChangeListener(this.mMediaHostVisibilityListener);
        this.mDumpManager.unregisterDumpable(((QSPanel) this.mView).getDumpableTag());
    }

    public void refreshAllTiles() {
        ArrayList arrayList = this.mRecords;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TileRecord tileRecord = (TileRecord) obj;
            if (!tileRecord.tile.isListening()) {
                tileRecord.tile.refreshState();
            }
        }
    }

    void setListening(boolean z) {
        if (((QSPanel) this.mView).isListening() == z) {
            return;
        }
        ((QSPanel) this.mView).setListening(z);
        if (((QSPanel) this.mView).getTileLayout() != null) {
            ((QSPanel) this.mView).getTileLayout().setListening(z);
        }
        if (((QSPanel) this.mView).isListening()) {
            refreshAllTiles();
        }
    }

    public void setTiles() {
        setTiles(this.mHost.getTiles(), false);
    }

    public void setTiles(Collection collection, boolean z) {
        boolean z2 = true;
        int i = 0;
        if (collection.size() <= this.mRecords.size()) {
            Iterator it = collection.iterator();
            int i2 = 0;
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                } else if (((QSTile) it.next()) != ((TileRecord) this.mRecords.get(i2)).tile) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z2 && i2 < this.mRecords.size()) {
                ArrayList arrayList = this.mRecords;
                List<TileRecord> listSubList = arrayList.subList(i2, arrayList.size());
                for (TileRecord tileRecord : listSubList) {
                    ((QSPanel) this.mView).removeTile(tileRecord);
                    tileRecord.tile.removeCallback(tileRecord.callback);
                }
                listSubList.clear();
                this.mCachedSpecs = getTilesSpecs();
            }
        }
        if (!z2) {
            ArrayList arrayList2 = this.mRecords;
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                TileRecord tileRecord2 = (TileRecord) obj;
                tileRecord2.tile.addCallback(tileRecord2.callback);
            }
            return;
        }
        ArrayList arrayList3 = this.mRecords;
        int size2 = arrayList3.size();
        while (i < size2) {
            Object obj2 = arrayList3.get(i);
            i++;
            TileRecord tileRecord3 = (TileRecord) obj2;
            ((QSPanel) this.mView).removeTile(tileRecord3);
            tileRecord3.tile.removeCallback(tileRecord3.callback);
        }
        this.mRecords.clear();
        this.mCachedSpecs = "";
        Iterator it2 = collection.iterator();
        while (it2.hasNext()) {
            addTile((QSTile) it2.next(), z);
        }
    }

    boolean shouldUseHorizontalLayout() {
        return false;
    }

    boolean switchTileLayout(boolean z) {
        boolean zShouldUseHorizontalLayout = shouldUseHorizontalLayout();
        if (zShouldUseHorizontalLayout == this.mUsingHorizontalLayout && !z) {
            return false;
        }
        this.mUsingHorizontalLayout = zShouldUseHorizontalLayout;
        ((QSPanel) this.mView).setUsingHorizontalLayout(zShouldUseHorizontalLayout, this.mMediaHost.getHostView(), z);
        updateMediaDisappearParameters();
        Runnable runnable = this.mUsingHorizontalLayoutChangedListener;
        if (runnable == null) {
            return true;
        }
        runnable.run();
        return true;
    }

    void updateMediaDisappearParameters() {
        if (this.mUsingMediaPlayer) {
            DisappearParameters disappearParameters = this.mMediaHost.getDisappearParameters();
            if (this.mUsingHorizontalLayout) {
                disappearParameters.getDisappearSize().set(0.0f, 0.4f);
                disappearParameters.getGonePivot().set(1.0f, 0.0f);
                disappearParameters.getContentTranslationFraction().set(0.25f, 1.0f);
                disappearParameters.setDisappearEnd(0.6f);
            } else {
                disappearParameters.getDisappearSize().set(1.0f, 0.0f);
                disappearParameters.getGonePivot().set(0.0f, 0.0f);
                disappearParameters.getContentTranslationFraction().set(0.0f, 1.0f);
                disappearParameters.setDisappearEnd(0.95f);
            }
            disappearParameters.setFadeStartPosition(0.95f);
            disappearParameters.setDisappearStart(0.0f);
            this.mMediaHost.setDisappearParameters(disappearParameters);
        }
    }
}
