package com.android.systemui.media.controls.ui.view;

import android.graphics.Rect;
import android.util.ArraySet;
import android.view.View;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaHost.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaHost implements MediaHostState {
    private final View.OnAttachStateChangeListener attachStateChangeListener;
    private final Rect currentBounds;
    private final Rect currentClipping;
    public UniqueObjectHostView hostView;
    private boolean inited;
    private final MediaHost$listener$1 listener;
    private boolean listeningToMediaData;
    private int location;
    private final MediaCarouselController mediaCarouselController;
    private final MediaDataManager mediaDataManager;
    private final MediaHierarchyManager mediaHierarchyManager;
    private final MediaHostStatesManager mediaHostStatesManager;
    private final MediaHostStateHolder state;
    private final int[] tmpLocationOnScreen;
    private ArraySet visibleChangedListeners;

    /* JADX INFO: compiled from: MediaHost.kt */
    public final class MediaHostStateHolder implements MediaHostState {
        private Function0 changedListener;
        private boolean expandedMatchesParentHeight;
        private float expansion;
        private boolean falsingProtectionNeeded;
        private MeasurementInput measurementInput;
        private boolean showsOnlyActiveMedia;
        private float squishFraction = 1.0f;
        private boolean visible = true;
        private DisappearParameters disappearParameters = new DisappearParameters();
        private int lastDisappearHash = getDisappearParameters().hashCode();

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public MediaHostState copy() {
            MediaHostStateHolder mediaHostStateHolder = new MediaHostStateHolder();
            mediaHostStateHolder.setExpansion(getExpansion());
            mediaHostStateHolder.setExpandedMatchesParentHeight(getExpandedMatchesParentHeight());
            mediaHostStateHolder.setSquishFraction(getSquishFraction());
            mediaHostStateHolder.setShowsOnlyActiveMedia(getShowsOnlyActiveMedia());
            MeasurementInput measurementInput = getMeasurementInput();
            mediaHostStateHolder.setMeasurementInput(measurementInput != null ? MeasurementInput.copy$default(measurementInput, 0, 0, 3, null) : null);
            mediaHostStateHolder.setVisible(getVisible());
            mediaHostStateHolder.setDisappearParameters(getDisappearParameters().deepCopy());
            mediaHostStateHolder.setFalsingProtectionNeeded(getFalsingProtectionNeeded());
            return mediaHostStateHolder;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MediaHostState)) {
                return false;
            }
            MediaHostState mediaHostState = (MediaHostState) obj;
            return Objects.equals(getMeasurementInput(), mediaHostState.getMeasurementInput()) && getExpansion() == mediaHostState.getExpansion() && getSquishFraction() == mediaHostState.getSquishFraction() && getShowsOnlyActiveMedia() == mediaHostState.getShowsOnlyActiveMedia() && getVisible() == mediaHostState.getVisible() && getFalsingProtectionNeeded() == mediaHostState.getFalsingProtectionNeeded() && getDisappearParameters().equals(mediaHostState.getDisappearParameters());
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public DisappearParameters getDisappearParameters() {
            return this.disappearParameters;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public boolean getExpandedMatchesParentHeight() {
            return this.expandedMatchesParentHeight;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public float getExpansion() {
            return this.expansion;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public boolean getFalsingProtectionNeeded() {
            return this.falsingProtectionNeeded;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public MeasurementInput getMeasurementInput() {
            return this.measurementInput;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public boolean getShowsOnlyActiveMedia() {
            return this.showsOnlyActiveMedia;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public float getSquishFraction() {
            return this.squishFraction;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public boolean getVisible() {
            return this.visible;
        }

        public int hashCode() {
            MeasurementInput measurementInput = getMeasurementInput();
            return ((((((((((((measurementInput != null ? measurementInput.hashCode() : 0) * 31) + Float.hashCode(getExpansion())) * 31) + Float.hashCode(getSquishFraction())) * 31) + Boolean.hashCode(getFalsingProtectionNeeded())) * 31) + Boolean.hashCode(getShowsOnlyActiveMedia())) * 31) + (getVisible() ? 1 : 2)) * 31) + getDisappearParameters().hashCode();
        }

        public final void setChangedListener(Function0 function0) {
            this.changedListener = function0;
        }

        public void setDisappearParameters(DisappearParameters disappearParameters) {
            disappearParameters.getClass();
            int iHashCode = disappearParameters.hashCode();
            if (Integer.valueOf(this.lastDisappearHash).equals(Integer.valueOf(iHashCode))) {
                return;
            }
            this.disappearParameters = disappearParameters;
            this.lastDisappearHash = iHashCode;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }

        public void setExpandedMatchesParentHeight(boolean z) {
            if (z != this.expandedMatchesParentHeight) {
                this.expandedMatchesParentHeight = z;
                Function0 function0 = this.changedListener;
                if (function0 != null) {
                    function0.mo2224invoke();
                }
            }
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public void setExpansion(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                return;
            }
            this.expansion = f;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }

        public void setFalsingProtectionNeeded(boolean z) {
            if (this.falsingProtectionNeeded == z) {
                return;
            }
            this.falsingProtectionNeeded = z;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }

        public void setMeasurementInput(MeasurementInput measurementInput) {
            if (measurementInput == null || !measurementInput.equals(this.measurementInput)) {
                this.measurementInput = measurementInput;
                Function0 function0 = this.changedListener;
                if (function0 != null) {
                    function0.mo2224invoke();
                }
            }
        }

        public void setShowsOnlyActiveMedia(boolean z) {
            if (Boolean.valueOf(z).equals(Boolean.valueOf(this.showsOnlyActiveMedia))) {
                return;
            }
            this.showsOnlyActiveMedia = z;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }

        public void setSquishFraction(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.squishFraction))) {
                return;
            }
            this.squishFraction = f;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }

        public void setVisible(boolean z) {
            if (this.visible == z) {
                return;
            }
            this.visible = z;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.mo2224invoke();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.controls.ui.view.MediaHost$listener$1] */
    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager, MediaCarouselController mediaCarouselController) {
        mediaHostStateHolder.getClass();
        mediaHierarchyManager.getClass();
        mediaDataManager.getClass();
        mediaHostStatesManager.getClass();
        mediaCarouselController.getClass();
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.mediaDataManager = mediaDataManager;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.mediaCarouselController = mediaCarouselController;
        this.location = -1;
        this.visibleChangedListeners = new ArraySet();
        this.tmpLocationOnScreen = new int[]{0, 0};
        this.currentBounds = new Rect();
        this.currentClipping = new Rect();
        this.listener = new MediaDataManager.Listener() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$listener$1
            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
            public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
                str.getClass();
                mediaData.getClass();
                if (z) {
                    this.this$0.updateViewVisibility();
                }
            }

            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
            public void onMediaDataRemoved(String str) {
                str.getClass();
                this.this$0.updateViewVisibility();
            }
        };
        this.attachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$attachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                view.getClass();
                this.this$0.setListeningToMediaData(true);
                this.this$0.updateViewVisibility();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                view.getClass();
                this.this$0.setListeningToMediaData(false);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit init$lambda$0(MediaHost mediaHost, int i) {
        mediaHost.mediaHostStatesManager.updateHostState(i, mediaHost.state);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setListeningToMediaData(boolean z) {
        if (z != this.listeningToMediaData) {
            this.listeningToMediaData = z;
            if (z) {
                this.mediaDataManager.addListener(this.listener);
            } else {
                this.mediaDataManager.removeListener(this.listener);
            }
        }
    }

    public final void addVisibilityChangeListener(Function1 function1) {
        function1.getClass();
        this.visibleChangedListeners.add(function1);
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public MediaHostState copy() {
        return this.state.copy();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public DisappearParameters getDisappearParameters() {
        return this.state.getDisappearParameters();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public boolean getExpandedMatchesParentHeight() {
        return this.state.getExpandedMatchesParentHeight();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public float getExpansion() {
        return this.state.getExpansion();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public boolean getFalsingProtectionNeeded() {
        return this.state.getFalsingProtectionNeeded();
    }

    public final UniqueObjectHostView getHostView() {
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView != null) {
            return uniqueObjectHostView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostView");
        return null;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public MeasurementInput getMeasurementInput() {
        return this.state.getMeasurementInput();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public boolean getShowsOnlyActiveMedia() {
        return this.state.getShowsOnlyActiveMedia();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public float getSquishFraction() {
        return this.state.getSquishFraction();
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public boolean getVisible() {
        return this.state.getVisible();
    }

    public final void init(final int i) {
        if (this.inited) {
            return;
        }
        this.inited = true;
        this.location = i;
        setHostView(this.mediaHierarchyManager.register(this));
        setListeningToMediaData(true);
        getHostView().addOnAttachStateChangeListener(this.attachStateChangeListener);
        getHostView().setMeasurementManager(new UniqueObjectHostView.MeasurementManager() { // from class: com.android.systemui.media.controls.ui.view.MediaHost.init.1
            @Override // com.android.systemui.util.animation.UniqueObjectHostView.MeasurementManager
            public MeasurementOutput onMeasure(MeasurementInput measurementInput) {
                measurementInput.getClass();
                if (View.MeasureSpec.getMode(measurementInput.getWidthMeasureSpec()) == Integer.MIN_VALUE) {
                    measurementInput.setWidthMeasureSpec(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measurementInput.getWidthMeasureSpec()), 1073741824));
                }
                MediaHost.this.state.setMeasurementInput(measurementInput);
                return MediaHost.this.mediaHostStatesManager.updateCarouselDimensions(i, MediaHost.this.state);
            }
        });
        this.state.setChangedListener(new Function0() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaHost.init$lambda$0(this.f$0, i);
            }
        });
        updateViewVisibility();
    }

    public final void removeVisibilityChangeListener(Function1 function1) {
        function1.getClass();
        this.visibleChangedListeners.remove(function1);
    }

    public void setDisappearParameters(DisappearParameters disappearParameters) {
        disappearParameters.getClass();
        this.state.setDisappearParameters(disappearParameters);
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public void setExpansion(float f) {
        this.state.setExpansion(f);
    }

    public final void setHostView(UniqueObjectHostView uniqueObjectHostView) {
        uniqueObjectHostView.getClass();
        this.hostView = uniqueObjectHostView;
    }

    public void setShowsOnlyActiveMedia(boolean z) {
        this.state.setShowsOnlyActiveMedia(z);
    }

    public final void stopAllListeners() {
        this.mediaCarouselController.stopAllListeners();
        getHostView().removeOnAttachStateChangeListener(this.attachStateChangeListener);
        this.visibleChangedListeners.clear();
        setListeningToMediaData(false);
        this.state.setChangedListener(null);
    }

    public final void updateViewVisibility() {
        this.state.setVisible(getShowsOnlyActiveMedia() ? this.mediaDataManager.hasActiveMediaOrRecommendation() : this.mediaDataManager.hasAnyMediaOrRecommendation());
        int i = getVisible() ? 0 : 8;
        if (i != getHostView().getVisibility()) {
            getHostView().setVisibility(i);
            Iterator it = this.visibleChangedListeners.iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).invoke(Boolean.valueOf(getVisible()));
            }
        }
    }
}
