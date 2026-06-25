package com.android.systemui.media.controls.ui.controller;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.Flags;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler;
import com.android.systemui.media.controls.ui.binder.MediaControlViewBinder;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$anim;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.TransitionLayoutController;
import com.android.systemui.util.animation.TransitionViewState;
import com.android.systemui.util.animation.WidgetState;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$xml;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaViewController.kt */
/* JADX INFO: loaded from: classes.dex */
public class MediaViewController {
    public static final Companion Companion = new Companion(null);
    public static final long GUTS_ANIMATION_DURATION = 234;
    private boolean animateNextStateChange;
    private long animationDelay;
    private long animationDuration;
    private boolean canShowScrubbingTime;
    private ConstraintSet collapsedLayout;
    public ColorSchemeTransition colorSchemeTransition;
    public Function0 configurationChangeListener;
    private final ConfigurationController configurationController;
    private final MediaViewController$configurationListener$1 configurationListener;
    private final Context context;
    private int currentEndLocation;
    private int currentHeight;
    private int currentStartLocation;
    private float currentTransitionProgress;
    private int currentWidth;
    private final MediaViewController$enabledChangeListener$1 enabledChangeListener;
    private ConstraintSet expandedLayout;
    private boolean firstRefresh;
    private final GlobalSettings globalSettings;
    private int heightInSceneContainerPx;
    private boolean isArtworkBound;
    private boolean isGutsVisible;
    private boolean isNextButtonAvailable;
    private boolean isPrevButtonAvailable;
    private boolean isScrubbing;
    private boolean isSeekBarEnabled;
    private final TransitionLayoutController layoutController;
    private LoadingEffect loadingEffect;
    private final MediaViewLogger logger;
    private final DelayableExecutor mainExecutor;
    private final MeasurementOutput measurement;
    private final MediaFlags mediaFlags;
    private final MediaHostStatesManager mediaHostStatesManager;
    public MediaViewHolder mediaViewHolder;
    public MetadataAnimationHandler metadataAnimationHandler;
    public MultiRippleController multiRippleController;
    private int nextNotVisibleValue;
    private PaintDrawCallback noiseDrawCallback;
    private Drawable prevArtwork;
    private int prevNotVisibleValue;
    private final MediaViewController$scrubbingChangeListener$1 scrubbingChangeListener;
    private SeekBarObserver seekBarObserver;
    private final SeekBarViewModel seekBarViewModel;
    public Function0 sizeChangedListener;
    private final MediaHostStatesManager.Callback stateCallback;
    private LoadingEffect.AnimationStateChangedCallback stateChangedCallback;
    private final CacheKey tmpKey;
    private final TransitionViewState tmpState;
    private final TransitionViewState tmpState2;
    private final TransitionViewState tmpState3;
    private TransitionLayout transitionLayout;
    private TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig;
    private TurbulenceNoiseController turbulenceNoiseController;
    private TYPE type;
    private final Map viewStates;
    private int widthInSceneContainerPx;

    /* JADX INFO: compiled from: MediaViewController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: MediaViewController.kt */
    public final class TYPE {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ TYPE[] $VALUES;
        public static final TYPE PLAYER = new TYPE("PLAYER", 0);
        public static final TYPE RECOMMENDATION = new TYPE("RECOMMENDATION", 1);

        private static final /* synthetic */ TYPE[] $values() {
            return new TYPE[]{PLAYER, RECOMMENDATION};
        }

        static {
            TYPE[] typeArr$values = $values();
            $VALUES = typeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(typeArr$values);
        }

        private TYPE(String str, int i) {
        }

        public static TYPE valueOf(String str) {
            return (TYPE) Enum.valueOf(TYPE.class, str);
        }

        public static TYPE[] values() {
            return (TYPE[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: MediaViewController.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TYPE.values().length];
            try {
                iArr[TYPE.PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TYPE.RECOMMENDATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v10, types: [com.android.systemui.media.controls.ui.controller.MediaViewController$scrubbingChangeListener$1] */
    /* JADX WARN: Type inference failed for: r11v11, types: [com.android.systemui.media.controls.ui.controller.MediaViewController$enabledChangeListener$1] */
    /* JADX WARN: Type inference failed for: r11v12, types: [com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1, java.lang.Object] */
    public MediaViewController(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger, SeekBarViewModel seekBarViewModel, DelayableExecutor delayableExecutor, MediaFlags mediaFlags, GlobalSettings globalSettings) {
        context.getClass();
        configurationController.getClass();
        mediaHostStatesManager.getClass();
        mediaViewLogger.getClass();
        seekBarViewModel.getClass();
        delayableExecutor.getClass();
        mediaFlags.getClass();
        globalSettings.getClass();
        this.context = context;
        this.configurationController = configurationController;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.logger = mediaViewLogger;
        this.seekBarViewModel = seekBarViewModel;
        this.mainExecutor = delayableExecutor;
        this.mediaFlags = mediaFlags;
        this.globalSettings = globalSettings;
        this.firstRefresh = true;
        TransitionLayoutController transitionLayoutController = new TransitionLayoutController();
        this.layoutController = transitionLayoutController;
        this.measurement = new MeasurementOutput(0, 0);
        this.type = TYPE.PLAYER;
        this.viewStates = new LinkedHashMap();
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.tmpState = new TransitionViewState();
        this.tmpState2 = new TransitionViewState();
        this.tmpState3 = new TransitionViewState();
        this.tmpKey = new CacheKey(0, 0, 0.0f, false, 15, null);
        this.prevNotVisibleValue = 8;
        this.nextNotVisibleValue = 8;
        this.scrubbingChangeListener = new SeekBarViewModel.ScrubbingChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$scrubbingChangeListener$1
            @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.ScrubbingChangeListener
            public void onScrubbingChanged(boolean z) {
                if (this.this$0.mediaFlags.isMediaControlsRefactorEnabled() && this.this$0.isScrubbing() != z) {
                    this.this$0.setScrubbing(z);
                    this.this$0.updateDisplayForScrubbingChange();
                }
            }
        };
        this.enabledChangeListener = new SeekBarViewModel.EnabledChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$enabledChangeListener$1
            @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.EnabledChangeListener
            public void onEnabledChanged(boolean z) {
                if (this.this$0.mediaFlags.isMediaControlsRefactorEnabled() && this.this$0.isSeekBarEnabled() != z) {
                    this.this$0.setSeekBarEnabled(z);
                    MediaControlViewBinder.INSTANCE.updateSeekBarVisibility(this.this$0.getExpandedLayout(), this.this$0.isSeekBarEnabled());
                }
            }
        };
        ?? r11 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1
            private int lastOrientation = -1;

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                if (configuration != null) {
                    MediaViewController mediaViewController = this.this$0;
                    TransitionLayout transitionLayout = mediaViewController.transitionLayout;
                    if (transitionLayout == null || transitionLayout.getRawLayoutDirection() != configuration.getLayoutDirection()) {
                        TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
                        if (transitionLayout2 != null) {
                            transitionLayout2.setLayoutDirection(configuration.getLayoutDirection());
                        }
                        mediaViewController.refreshState();
                    }
                    int i = configuration.orientation;
                    if (this.lastOrientation != i) {
                        this.lastOrientation = i;
                        mediaViewController.setBackgroundHeights(mediaViewController.context.getResources().getDimensionPixelSize(R$dimen.qs_media_session_height_expanded));
                    }
                    if (mediaViewController.configurationChangeListener != null) {
                        mediaViewController.getConfigurationChangeListener().mo2224invoke();
                        mediaViewController.refreshState();
                    }
                }
            }
        };
        this.configurationListener = r11;
        this.stateCallback = new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$stateCallback$1
            @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
            public void onHostStateChanged(int i, MediaHostState mediaHostState) {
                mediaHostState.getClass();
                if (i == this.this$0.getCurrentEndLocation() || i == this.this$0.currentStartLocation) {
                    MediaViewController mediaViewController = this.this$0;
                    MediaViewController.setCurrentState$default(mediaViewController, mediaViewController.currentStartLocation, this.this$0.getCurrentEndLocation(), this.this$0.currentTransitionProgress, false, false, 16, null);
                }
            }
        };
        this.collapsedLayout = new ConstraintSet();
        this.expandedLayout = new ConstraintSet();
        mediaHostStatesManager.addController(this);
        transitionLayoutController.setSizeChangedListener(new Function2() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return MediaViewController._init_$lambda$0(this.f$0, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
            }
        });
        configurationController.addCallback(r11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$0(MediaViewController mediaViewController, int i, int i2) {
        mediaViewController.currentWidth = i;
        mediaViewController.currentHeight = i2;
        mediaViewController.getSizeChangedListener().mo2224invoke();
        return Unit.INSTANCE;
    }

    private final float calculateWidgetGroupAlphaForSquishiness(Set set, float f, TransitionViewState transitionViewState, float f2) {
        int measureHeight = transitionViewState.getMeasureHeight();
        float measureHeight2 = transitionViewState.getMeasureHeight();
        Set set2 = set;
        Iterator it = set2.iterator();
        float fMax = 0.0f;
        while (it.hasNext()) {
            WidgetState widgetState = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                measureHeight2 = Float.min(measureHeight2, widgetState.getY());
                fMax = Float.max(fMax, widgetState.getY() + widgetState.getHeight());
            }
        }
        if (fMax == f) {
            fMax = (float) (((double) f) - (((double) (fMax - measureHeight2)) * 0.2d));
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            WidgetState widgetState2 = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it2.next()).intValue()));
            if (widgetState2 != null && widgetState2.getAlpha() != 0.0f) {
                float f3 = measureHeight;
                widgetState2.setAlpha(MediaCarouselController.Companion.calculateAlpha(f2, fMax / f3, f / f3));
            }
        }
        return measureHeight2;
    }

    private final ConstraintSet constraintSetForExpansion(float f) {
        return f > 0.0f ? this.expandedLayout : this.collapsedLayout;
    }

    private final TurbulenceNoiseAnimationConfig createTurbulenceNoiseConfig(LoadingEffectView loadingEffectView, TurbulenceNoiseView turbulenceNoiseView, ColorSchemeTransition colorSchemeTransition) {
        View view = Flags.shaderlibLoadingEffectRefactor() ? loadingEffectView : turbulenceNoiseView;
        int width = view.getWidth();
        int height = view.getHeight();
        Random random = new Random();
        return new TurbulenceNoiseAnimationConfig(2.14f, 1.0f, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.42f, 0.0f, 0.3f, colorSchemeTransition.getAccentPrimary().getCurrentColor(), DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT, width, height, 30000.0f, 1350.0f, 1350.0f, view.getContext().getResources().getDisplayMetrics().density, 0.26f, 0.09f, false);
    }

    private final void ensureAllMeasurements() {
        Iterator it = this.mediaHostStatesManager.getMediaHostStates().entrySet().iterator();
        while (it.hasNext()) {
            obtainViewState$default(this, (MediaHostState) ((Map.Entry) it.next()).getValue(), false, 2, null);
        }
    }

    private final CacheKey getKey(MediaHostState mediaHostState, boolean z, CacheKey cacheKey) {
        MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
        cacheKey.setHeightMeasureSpec(measurementInput != null ? measurementInput.getHeightMeasureSpec() : 0);
        MeasurementInput measurementInput2 = mediaHostState.getMeasurementInput();
        cacheKey.setWidthMeasureSpec(measurementInput2 != null ? measurementInput2.getWidthMeasureSpec() : 0);
        cacheKey.setExpansion(mediaHostState.getExpansion());
        cacheKey.setGutsVisible(z);
        return cacheKey;
    }

    private static /* synthetic */ void getTransitionLayout$annotations() {
    }

    private final void loadLayoutForType(TYPE type) {
        this.type = type;
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            this.collapsedLayout.load(this.context, R$xml.media_session_collapsed);
            this.expandedLayout.load(this.context, R$xml.media_session_expanded);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            this.collapsedLayout.load(this.context, R$xml.media_recommendations_collapsed);
            this.expandedLayout.load(this.context, R$xml.media_recommendations_expanded);
        }
        refreshState();
    }

    private final TransitionViewState obtainSceneContainerViewState() {
        this.logger.logMediaSize("scene container", this.widthInSceneContainerPx, this.heightInSceneContainerPx);
        TransitionLayout transitionLayout = this.transitionLayout;
        TransitionViewState transitionViewStateCalculateViewState = transitionLayout != null ? transitionLayout.calculateViewState(new MeasurementInput(this.widthInSceneContainerPx, this.heightInSceneContainerPx), this.expandedLayout, new TransitionViewState()) : null;
        if (transitionViewStateCalculateViewState != null) {
            setGutsViewState(transitionViewStateCalculateViewState);
        }
        return transitionViewStateCalculateViewState;
    }

    public static /* synthetic */ TransitionViewState obtainViewState$default(MediaViewController mediaViewController, MediaHostState mediaHostState, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: obtainViewState");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return mediaViewController.obtainViewState(mediaHostState, z);
    }

    private final TransitionViewState obtainViewStateForLocation(int i) {
        MediaHostState mediaHostState = (MediaHostState) this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
        if (mediaHostState == null) {
            return null;
        }
        if (this.mediaFlags.isSceneContainerEnabled()) {
            return obtainSceneContainerViewState();
        }
        TransitionViewState transitionViewStateObtainViewState$default = obtainViewState$default(this, mediaHostState, false, 2, null);
        if (transitionViewStateObtainViewState$default == null) {
            return transitionViewStateObtainViewState$default;
        }
        updateViewStateSize(transitionViewStateObtainViewState$default, i, this.tmpState);
        return this.tmpState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setBackgroundHeights(int i) {
        Iterator it = MediaViewHolder.Companion.getBackgroundIds().iterator();
        while (it.hasNext()) {
            this.expandedLayout.getConstraint(((Number) it.next()).intValue()).layout.mHeight = i;
        }
    }

    public static /* synthetic */ void setCurrentState$default(MediaViewController mediaViewController, int i, int i2, float f, boolean z, boolean z2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setCurrentState");
        }
        if ((i3 & 16) != 0) {
            z2 = false;
        }
        mediaViewController.setCurrentState(i, i2, f, z, z2);
    }

    private final void setGutsViewState(TransitionViewState transitionViewState) {
        Set controlsIds = MediaViewHolder.Companion.getControlsIds();
        Set ids = GutsViewHolder.Companion.getIds();
        Iterator it = controlsIds.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WidgetState widgetState = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                widgetState.setAlpha(this.isGutsVisible ? 0.0f : widgetState.getAlpha());
                widgetState.setGone(this.isGutsVisible ? true : widgetState.getGone());
            }
        }
        Iterator it2 = ids.iterator();
        while (it2.hasNext()) {
            WidgetState widgetState2 = (WidgetState) transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it2.next()).intValue()));
            if (widgetState2 != null) {
                widgetState2.setAlpha(this.isGutsVisible ? widgetState2.getAlpha() : 0.0f);
                widgetState2.setGone(this.isGutsVisible ? widgetState2.getGone() : true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDisplayForScrubbingChange() {
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.updateDisplayForScrubbingChange.1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                int i;
                boolean z2 = MediaViewController.this.getCanShowScrubbingTime() && MediaViewController.this.isScrubbing();
                MediaControlViewBinder mediaControlViewBinder = MediaControlViewBinder.INSTANCE;
                mediaControlViewBinder.setVisibleAndAlpha(MediaViewController.this.getExpandedLayout(), MediaViewController.this.getMediaViewHolder().getScrubbingTotalTimeView().getId(), z2);
                mediaControlViewBinder.setVisibleAndAlpha(MediaViewController.this.getExpandedLayout(), MediaViewController.this.getMediaViewHolder().getScrubbingElapsedTimeView().getId(), z2);
                List semantic_actions_hide_when_scrubbing = MediaControlViewModel.Companion.getSEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING();
                MediaViewController mediaViewController = MediaViewController.this;
                Iterator it = semantic_actions_hide_when_scrubbing.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    if (iIntValue == R$id.actionPrev) {
                        z = mediaViewController.isPrevButtonAvailable && !z2;
                        i = mediaViewController.prevNotVisibleValue;
                    } else if (iIntValue == R$id.actionNext) {
                        z = mediaViewController.isNextButtonAvailable && !z2;
                        i = mediaViewController.nextNotVisibleValue;
                    } else {
                        z = !z2;
                        i = 8;
                    }
                    MediaControlViewBinder.INSTANCE.setSemanticButtonVisibleAndAlpha(mediaViewController.getMediaViewHolder().getAction(iIntValue), mediaViewController.getExpandedLayout(), mediaViewController.getCollapsedLayout(), z, i, true);
                }
                if (MediaViewController.this.getMetadataAnimationHandler$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().isRunning()) {
                    return;
                }
                MediaViewController.this.refreshState();
            }
        });
    }

    private final TransitionViewState updateViewStateSize(TransitionViewState transitionViewState, int i, TransitionViewState transitionViewState2) {
        TransitionViewState transitionViewStateCopy;
        if (transitionViewState == null || (transitionViewStateCopy = transitionViewState.copy(transitionViewState2)) == null) {
            return null;
        }
        MediaHostState mediaHostState = (MediaHostState) this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
        MeasurementOutput measurementOutput = (MeasurementOutput) this.mediaHostStatesManager.getCarouselSizes().get(Integer.valueOf(i));
        if (measurementOutput != null && (transitionViewStateCopy.getMeasureHeight() != measurementOutput.getMeasuredHeight() || transitionViewStateCopy.getMeasureWidth() != measurementOutput.getMeasuredWidth())) {
            transitionViewStateCopy.setMeasureHeight(Math.max(measurementOutput.getMeasuredHeight(), transitionViewStateCopy.getMeasureHeight()));
            transitionViewStateCopy.setMeasureWidth(Math.max(measurementOutput.getMeasuredWidth(), transitionViewStateCopy.getMeasureWidth()));
            transitionViewStateCopy.setHeight(transitionViewStateCopy.getMeasureHeight());
            transitionViewStateCopy.setWidth(transitionViewStateCopy.getMeasureWidth());
            Iterator it = MediaViewHolder.Companion.getBackgroundIds().iterator();
            while (it.hasNext()) {
                WidgetState widgetState = (WidgetState) transitionViewStateCopy.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
                if (widgetState != null) {
                    widgetState.setHeight(transitionViewStateCopy.getHeight());
                    widgetState.setWidth(transitionViewStateCopy.getWidth());
                }
            }
            if (mediaHostState != null && mediaHostState.getSquishFraction() <= 1.0f) {
                transitionViewStateCopy = squishViewState$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(transitionViewStateCopy, mediaHostState.getSquishFraction());
            }
        }
        this.logger.logMediaSize("update to carousel", transitionViewStateCopy.getWidth(), transitionViewStateCopy.getHeight());
        return transitionViewStateCopy;
    }

    public final void animatePendingStateChange(long j, long j2) {
        this.animateNextStateChange = true;
        this.animationDuration = j;
        this.animationDelay = j2;
    }

    public final void attach(TransitionLayout transitionLayout, TYPE type) {
        transitionLayout.getClass();
        type.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#attach");
        }
        try {
            loadLayoutForType(type);
            this.logger.logMediaLocation("attach " + type, this.currentStartLocation, this.currentEndLocation);
            this.transitionLayout = transitionLayout;
            this.layoutController.attach(transitionLayout);
            int i = this.currentEndLocation;
            if (i == -1) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } else {
                setCurrentState$default(this, this.currentStartLocation, i, this.currentTransitionProgress, true, false, 16, null);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        } finally {
        }
    }

    public final void attachPlayer(MediaViewHolder mediaViewHolder) {
        mediaViewHolder.getClass();
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            setMediaViewHolder(mediaViewHolder);
            this.seekBarObserver = new SeekBarObserver(mediaViewHolder);
            LiveData progress = this.seekBarViewModel.getProgress();
            SeekBarObserver seekBarObserver = this.seekBarObserver;
            TurbulenceNoiseController turbulenceNoiseController = null;
            if (seekBarObserver == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBarObserver");
                seekBarObserver = null;
            }
            progress.observeForever(seekBarObserver);
            this.seekBarViewModel.attachTouchHandlers(mediaViewHolder.getSeekBar());
            this.seekBarViewModel.setScrubbingChangeListener(this.scrubbingChangeListener);
            this.seekBarViewModel.setEnabledChangeListener(this.enabledChangeListener);
            TransitionLayout player = mediaViewHolder.getPlayer();
            attach(mediaViewHolder.getPlayer(), TYPE.PLAYER);
            TurbulenceNoiseView turbulenceNoiseView = mediaViewHolder.getTurbulenceNoiseView();
            this.turbulenceNoiseController = new TurbulenceNoiseController(turbulenceNoiseView);
            setMultiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(new MultiRippleController(mediaViewHolder.getMultiRippleView()));
            TextView titleText = mediaViewHolder.getTitleText();
            TextView artistText = mediaViewHolder.getArtistText();
            CachingIconView explicitIndicator = mediaViewHolder.getExplicitIndicator();
            Context context = player.getContext();
            context.getClass();
            AnimatorSet animatorSetLoadAnimator = loadAnimator(context, R$anim.media_metadata_enter, Interpolators.EMPHASIZED_DECELERATE, titleText, artistText, explicitIndicator);
            Context context2 = player.getContext();
            context2.getClass();
            setMetadataAnimationHandler$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(new MetadataAnimationHandler(loadAnimator(context2, R$anim.media_metadata_exit, Interpolators.EMPHASIZED_ACCELERATE, titleText, artistText, explicitIndicator), animatorSetLoadAnimator));
            Context context3 = player.getContext();
            context3.getClass();
            MultiRippleController multiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = getMultiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
            TurbulenceNoiseController turbulenceNoiseController2 = this.turbulenceNoiseController;
            if (turbulenceNoiseController2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("turbulenceNoiseController");
            } else {
                turbulenceNoiseController = turbulenceNoiseController2;
            }
            setColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(new ColorSchemeTransition(context3, mediaViewHolder, multiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core, turbulenceNoiseController));
            final LoadingEffectView loadingEffectView = mediaViewHolder.getLoadingEffectView();
            this.turbulenceNoiseAnimationConfig = createTurbulenceNoiseConfig(loadingEffectView, turbulenceNoiseView, getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core());
            this.noiseDrawCallback = new PaintDrawCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.attachPlayer.1
                @Override // com.android.systemui.surfaceeffects.PaintDrawCallback
                public void onDraw(Paint paint) {
                    paint.getClass();
                    loadingEffectView.draw(paint);
                }
            };
            this.stateChangedCallback = new LoadingEffect.AnimationStateChangedCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.attachPlayer.2
                @Override // com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.AnimationStateChangedCallback
                public void onStateChanged(LoadingEffect.AnimationState animationState, LoadingEffect.AnimationState animationState2) {
                    animationState.getClass();
                    animationState2.getClass();
                    if (animationState2 == LoadingEffect.AnimationState.NOT_PLAYING) {
                        loadingEffectView.setVisibility(4);
                    } else {
                        loadingEffectView.setVisibility(0);
                    }
                }
            };
        }
    }

    public final void bindSeekBar(Function0 function0, Function1 function1) {
        function0.getClass();
        function1.getClass();
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.seekBarViewModel.setLogSeek(function0);
            function1.invoke(this.seekBarViewModel);
        }
    }

    public final void closeGuts(boolean z) {
        if (this.isGutsVisible) {
            this.isGutsVisible = false;
            if (!z) {
                animatePendingStateChange(GUTS_ANIMATION_DURATION, 0L);
            }
            setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, z, true);
        }
    }

    public final boolean getCanShowScrubbingTime() {
        return this.canShowScrubbingTime;
    }

    public final ConstraintSet getCollapsedLayout() {
        return this.collapsedLayout;
    }

    public final ColorSchemeTransition getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        ColorSchemeTransition colorSchemeTransition = this.colorSchemeTransition;
        if (colorSchemeTransition != null) {
            return colorSchemeTransition;
        }
        Intrinsics.throwUninitializedPropertyAccessException("colorSchemeTransition");
        return null;
    }

    public final Function0 getConfigurationChangeListener() {
        Function0 function0 = this.configurationChangeListener;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("configurationChangeListener");
        return null;
    }

    public final int getCurrentEndLocation() {
        return this.currentEndLocation;
    }

    public final int getCurrentHeight() {
        return this.currentHeight;
    }

    public final int getCurrentWidth() {
        return this.currentWidth;
    }

    public final ConstraintSet getExpandedLayout() {
        return this.expandedLayout;
    }

    public final int getHeightInSceneContainerPx() {
        return this.heightInSceneContainerPx;
    }

    public final MeasurementOutput getMeasurementsForState(MediaHostState mediaHostState) {
        mediaHostState.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#getMeasurementsForState");
        }
        try {
            TransitionViewState transitionViewStateObtainViewState$default = obtainViewState$default(this, mediaHostState, false, 2, null);
            if (transitionViewStateObtainViewState$default == null) {
                return null;
            }
            this.measurement.setMeasuredWidth(transitionViewStateObtainViewState$default.getMeasureWidth());
            this.measurement.setMeasuredHeight(transitionViewStateObtainViewState$default.getMeasureHeight());
            MeasurementOutput measurementOutput = this.measurement;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return measurementOutput;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final MediaViewHolder getMediaViewHolder() {
        MediaViewHolder mediaViewHolder = this.mediaViewHolder;
        if (mediaViewHolder != null) {
            return mediaViewHolder;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaViewHolder");
        return null;
    }

    public final MetadataAnimationHandler getMetadataAnimationHandler$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        MetadataAnimationHandler metadataAnimationHandler = this.metadataAnimationHandler;
        if (metadataAnimationHandler != null) {
            return metadataAnimationHandler;
        }
        Intrinsics.throwUninitializedPropertyAccessException("metadataAnimationHandler");
        return null;
    }

    public final MultiRippleController getMultiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        MultiRippleController multiRippleController = this.multiRippleController;
        if (multiRippleController != null) {
            return multiRippleController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("multiRippleController");
        return null;
    }

    public final Drawable getPrevArtwork() {
        return this.prevArtwork;
    }

    public final Function0 getSizeChangedListener() {
        Function0 function0 = this.sizeChangedListener;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sizeChangedListener");
        return null;
    }

    public final MediaHostStatesManager.Callback getStateCallback() {
        return this.stateCallback;
    }

    public final float getTranslationX() {
        TransitionLayout transitionLayout = this.transitionLayout;
        if (transitionLayout != null) {
            return transitionLayout.getTranslationX();
        }
        return 0.0f;
    }

    public final float getTranslationY() {
        TransitionLayout transitionLayout = this.transitionLayout;
        if (transitionLayout != null) {
            return transitionLayout.getTranslationY();
        }
        return 0.0f;
    }

    public final int getWidthInSceneContainerPx() {
        return this.widthInSceneContainerPx;
    }

    public final boolean isArtworkBound() {
        return this.isArtworkBound;
    }

    public final boolean isGutsVisible() {
        return this.isGutsVisible;
    }

    public final boolean isScrubbing() {
        return this.isScrubbing;
    }

    public final boolean isSeekBarEnabled() {
        return this.isSeekBarEnabled;
    }

    protected AnimatorSet loadAnimator(Context context, int i, Interpolator interpolator, View... viewArr) {
        context.getClass();
        viewArr.getClass();
        ArrayList arrayList = new ArrayList();
        for (View view : viewArr) {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i);
            animatorLoadAnimator.getClass();
            AnimatorSet animatorSet = (AnimatorSet) animatorLoadAnimator;
            animatorSet.getChildAnimations().get(0).setInterpolator(interpolator);
            animatorSet.setTarget(view);
            arrayList.add(animatorSet);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(arrayList);
        return animatorSet2;
    }

    public final TransitionViewState obtainViewState(MediaHostState mediaHostState, boolean z) {
        if (this.mediaFlags.isSceneContainerEnabled()) {
            return obtainSceneContainerViewState();
        }
        TransitionViewState transitionViewStateCalculateViewState = null;
        if (mediaHostState != null && mediaHostState.getMeasurementInput() != null) {
            CacheKey key = getKey(mediaHostState, this.isGutsVisible, this.tmpKey);
            TransitionViewState transitionViewState = (TransitionViewState) this.viewStates.get(key);
            if (transitionViewState != null) {
                return (mediaHostState.getSquishFraction() > 1.0f || z) ? transitionViewState : squishViewState$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(transitionViewState, mediaHostState.getSquishFraction());
            }
            CacheKey cacheKeyCopy$default = CacheKey.copy$default(key, 0, 0, 0.0f, false, 15, null);
            if (this.transitionLayout == null) {
                return null;
            }
            if (mediaHostState.getExpansion() == 0.0f || mediaHostState.getExpansion() == 1.0f) {
                if (mediaHostState.getExpansion() == 1.0f) {
                    setBackgroundHeights(mediaHostState.getExpandedMatchesParentHeight() ? 0 : this.context.getResources().getDimensionPixelSize(R$dimen.qs_media_session_height_expanded));
                }
                TransitionLayout transitionLayout = this.transitionLayout;
                transitionLayout.getClass();
                MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
                measurementInput.getClass();
                transitionViewStateCalculateViewState = transitionLayout.calculateViewState(measurementInput, constraintSetForExpansion(mediaHostState.getExpansion()), new TransitionViewState());
                setGutsViewState(transitionViewStateCalculateViewState);
                this.viewStates.put(cacheKeyCopy$default, transitionViewStateCalculateViewState);
            } else {
                MediaHostState mediaHostStateCopy = mediaHostState.copy();
                mediaHostStateCopy.setExpansion(0.0f);
                TransitionViewState transitionViewStateObtainViewState = obtainViewState(mediaHostStateCopy, z);
                transitionViewStateObtainViewState.getClass();
                MediaHostState mediaHostStateCopy2 = mediaHostState.copy();
                mediaHostStateCopy2.setExpansion(1.0f);
                TransitionViewState transitionViewStateObtainViewState2 = obtainViewState(mediaHostStateCopy2, z);
                transitionViewStateObtainViewState2.getClass();
                transitionViewStateCalculateViewState = TransitionLayoutController.getInterpolatedState$default(this.layoutController, transitionViewStateObtainViewState, transitionViewStateObtainViewState2, mediaHostState.getExpansion(), null, 8, null);
            }
            if (mediaHostState.getSquishFraction() <= 1.0f && !z) {
                return squishViewState$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(transitionViewStateCalculateViewState, mediaHostState.getSquishFraction());
            }
        }
        return transitionViewStateCalculateViewState;
    }

    public final void onDestroy() {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            if (this.seekBarObserver != null) {
                LiveData progress = this.seekBarViewModel.getProgress();
                SeekBarObserver seekBarObserver = this.seekBarObserver;
                if (seekBarObserver == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("seekBarObserver");
                    seekBarObserver = null;
                }
                progress.removeObserver(seekBarObserver);
            }
            this.seekBarViewModel.removeScrubbingChangeListener(this.scrubbingChangeListener);
            this.seekBarViewModel.removeEnabledChangeListener(this.enabledChangeListener);
            this.seekBarViewModel.onDestroy();
        }
        this.mediaHostStatesManager.removeController(this);
        this.configurationController.removeCallback(this.configurationListener);
    }

    public final void onLocationPreChange(int i) {
        TransitionViewState transitionViewStateObtainViewStateForLocation = obtainViewStateForLocation(i);
        if (transitionViewStateObtainViewStateForLocation != null) {
            this.layoutController.setMeasureState(transitionViewStateObtainViewStateForLocation);
        }
    }

    public final void openGuts() {
        if (this.isGutsVisible) {
            return;
        }
        this.isGutsVisible = true;
        animatePendingStateChange(GUTS_ANIMATION_DURATION, 0L);
        setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, false, true);
    }

    public final void refreshState() {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#refreshState");
        }
        try {
            if (this.mediaFlags.isSceneContainerEnabled()) {
                TransitionViewState transitionViewStateObtainSceneContainerViewState = obtainSceneContainerViewState();
                if (transitionViewStateObtainSceneContainerViewState != null) {
                    TransitionLayoutController.setState$default(this.layoutController, transitionViewStateObtainSceneContainerViewState, true, false, 0L, 0L, false, 24, null);
                }
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            this.viewStates.clear();
            if (this.firstRefresh) {
                ensureAllMeasurements();
                this.firstRefresh = false;
            }
            setCurrentState$default(this, this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true, false, 16, null);
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
        }
    }

    public final void setArtworkBound(boolean z) {
        this.isArtworkBound = z;
    }

    public final void setCanShowScrubbingTime(boolean z) {
        this.canShowScrubbingTime = z;
    }

    public final void setCollapsedLayout(ConstraintSet constraintSet) {
        constraintSet.getClass();
        this.collapsedLayout = constraintSet;
    }

    public final void setColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(ColorSchemeTransition colorSchemeTransition) {
        colorSchemeTransition.getClass();
        this.colorSchemeTransition = colorSchemeTransition;
    }

    public final void setCurrentState(int i, int i2, float f, boolean z, boolean z2) {
        TransitionViewState transitionViewState;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#setCurrentState");
        }
        try {
            this.currentEndLocation = i2;
            this.currentStartLocation = i;
            this.currentTransitionProgress = f;
            this.logger.logMediaLocation("setCurrentState", i, i2);
            boolean z3 = this.animateNextStateChange && !z;
            MediaHostState mediaHostState = (MediaHostState) this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i2));
            if (mediaHostState == null) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            MediaHostState mediaHostState2 = (MediaHostState) this.mediaHostStatesManager.getMediaHostStates().get(Integer.valueOf(i));
            TransitionViewState transitionViewStateObtainViewState = obtainViewState(mediaHostState, z2);
            if (transitionViewStateObtainViewState == null) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            TransitionViewState transitionViewStateUpdateViewStateSize = updateViewStateSize(transitionViewStateObtainViewState, i2, this.tmpState2);
            transitionViewStateUpdateViewStateSize.getClass();
            this.layoutController.setMeasureState(transitionViewStateUpdateViewStateSize);
            this.animateNextStateChange = false;
            if (this.transitionLayout == null) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            TransitionViewState transitionViewStateUpdateViewStateSize2 = updateViewStateSize(obtainViewState(mediaHostState2, z2), i, this.tmpState3);
            if (mediaHostState.getVisible()) {
                if (mediaHostState2 != null && !mediaHostState2.getVisible()) {
                    transitionViewStateUpdateViewStateSize = this.layoutController.getGoneState(transitionViewStateUpdateViewStateSize, mediaHostState.getDisappearParameters(), 1.0f - f, this.tmpState);
                } else if (f != 1.0f && transitionViewStateUpdateViewStateSize2 != null) {
                    if (f == 0.0f) {
                        transitionViewState = transitionViewStateUpdateViewStateSize2;
                    } else {
                        transitionViewStateUpdateViewStateSize = this.layoutController.getInterpolatedState(transitionViewStateUpdateViewStateSize2, transitionViewStateUpdateViewStateSize, f, this.tmpState);
                    }
                }
                transitionViewState = transitionViewStateUpdateViewStateSize;
            } else {
                if (transitionViewStateUpdateViewStateSize2 != null && mediaHostState2 != null) {
                    if (mediaHostState2.getVisible()) {
                        transitionViewStateUpdateViewStateSize = this.layoutController.getGoneState(transitionViewStateUpdateViewStateSize2, mediaHostState2.getDisappearParameters(), f, this.tmpState);
                    }
                }
                transitionViewState = transitionViewStateUpdateViewStateSize;
            }
            this.logger.logMediaSize("setCurrentState (progress " + f + ")", transitionViewState.getWidth(), transitionViewState.getHeight());
            this.layoutController.setState(transitionViewState, z, z3, this.animationDuration, this.animationDelay, z2);
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
        }
    }

    public final void setExpandedLayout(ConstraintSet constraintSet) {
        constraintSet.getClass();
        this.expandedLayout = constraintSet;
    }

    public final void setHeightInSceneContainerPx(int i) {
        this.heightInSceneContainerPx = i;
    }

    public final void setListening(boolean z) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.seekBarViewModel.setListening(z);
        }
    }

    public final void setMediaViewHolder(MediaViewHolder mediaViewHolder) {
        mediaViewHolder.getClass();
        this.mediaViewHolder = mediaViewHolder;
    }

    public final void setMetadataAnimationHandler$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(MetadataAnimationHandler metadataAnimationHandler) {
        metadataAnimationHandler.getClass();
        this.metadataAnimationHandler = metadataAnimationHandler;
    }

    public final void setMultiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(MultiRippleController multiRippleController) {
        multiRippleController.getClass();
        this.multiRippleController = multiRippleController;
    }

    public final void setPrevArtwork(Drawable drawable) {
        this.prevArtwork = drawable;
    }

    public final void setScrubbing(boolean z) {
        this.isScrubbing = z;
    }

    public final void setSeekBarEnabled(boolean z) {
        this.isSeekBarEnabled = z;
    }

    public final void setSizeChangedListener(Function0 function0) {
        function0.getClass();
        this.sizeChangedListener = function0;
    }

    public final void setUpNextButtonInfo(boolean z, int i) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.isNextButtonAvailable = z;
            this.nextNotVisibleValue = i;
        }
    }

    public final void setUpPrevButtonInfo(boolean z, int i) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.isPrevButtonAvailable = z;
            this.prevNotVisibleValue = i;
        }
    }

    public final void setUpTurbulenceNoise() {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            final TurbulenceNoiseController turbulenceNoiseController = null;
            final LoadingEffect loadingEffect = null;
            if (!Flags.shaderlibLoadingEffectRefactor()) {
                TurbulenceNoiseController turbulenceNoiseController2 = this.turbulenceNoiseController;
                if (turbulenceNoiseController2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("turbulenceNoiseController");
                    turbulenceNoiseController2 = null;
                }
                TurbulenceNoiseShader.Companion.Type type = TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.turbulenceNoiseAnimationConfig;
                if (turbulenceNoiseAnimationConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("turbulenceNoiseAnimationConfig");
                    turbulenceNoiseAnimationConfig = null;
                }
                turbulenceNoiseController2.play(type, turbulenceNoiseAnimationConfig);
                DelayableExecutor delayableExecutor = this.mainExecutor;
                TurbulenceNoiseController turbulenceNoiseController3 = this.turbulenceNoiseController;
                if (turbulenceNoiseController3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("turbulenceNoiseController");
                } else {
                    turbulenceNoiseController = turbulenceNoiseController3;
                }
                delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.setUpTurbulenceNoise.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        turbulenceNoiseController.finish();
                    }
                }, 7500L);
                return;
            }
            if (this.loadingEffect == null) {
                TurbulenceNoiseShader.Companion.Type type2 = TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = this.turbulenceNoiseAnimationConfig;
                if (turbulenceNoiseAnimationConfig2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("turbulenceNoiseAnimationConfig");
                    turbulenceNoiseAnimationConfig2 = null;
                }
                PaintDrawCallback paintDrawCallback = this.noiseDrawCallback;
                if (paintDrawCallback == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("noiseDrawCallback");
                    paintDrawCallback = null;
                }
                LoadingEffect.AnimationStateChangedCallback animationStateChangedCallback = this.stateChangedCallback;
                if (animationStateChangedCallback == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("stateChangedCallback");
                    animationStateChangedCallback = null;
                }
                this.loadingEffect = new LoadingEffect(type2, turbulenceNoiseAnimationConfig2, paintDrawCallback, animationStateChangedCallback);
            }
            ColorSchemeTransition colorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
            LoadingEffect loadingEffect2 = this.loadingEffect;
            if (loadingEffect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingEffect");
                loadingEffect2 = null;
            }
            colorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.setLoadingEffect(loadingEffect2);
            LoadingEffect loadingEffect3 = this.loadingEffect;
            if (loadingEffect3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingEffect");
                loadingEffect3 = null;
            }
            loadingEffect3.play();
            DelayableExecutor delayableExecutor2 = this.mainExecutor;
            LoadingEffect loadingEffect4 = this.loadingEffect;
            if (loadingEffect4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingEffect");
            } else {
                loadingEffect = loadingEffect4;
            }
            delayableExecutor2.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.setUpTurbulenceNoise.1
                @Override // java.lang.Runnable
                public final void run() {
                    loadingEffect.finish();
                }
            }, 7500L);
        }
    }

    public final void setWidthInSceneContainerPx(int i) {
        this.widthInSceneContainerPx = i;
    }

    public final TransitionViewState squishViewState$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(TransitionViewState transitionViewState, float f) {
        transitionViewState.getClass();
        TransitionViewState transitionViewStateCopy$default = TransitionViewState.copy$default(transitionViewState, null, 1, null);
        int measureHeight = (int) (transitionViewStateCopy$default.getMeasureHeight() * f);
        transitionViewStateCopy$default.setHeight(measureHeight);
        Iterator it = MediaViewHolder.Companion.getBackgroundIds().iterator();
        while (it.hasNext()) {
            WidgetState widgetState = (WidgetState) transitionViewStateCopy$default.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                widgetState.setHeight(measureHeight);
            }
        }
        MediaViewHolder.Companion companion = MediaViewHolder.Companion;
        calculateWidgetGroupAlphaForSquishiness(companion.getExpandedBottomActionIds(), transitionViewStateCopy$default.getMeasureHeight(), transitionViewStateCopy$default, f);
        calculateWidgetGroupAlphaForSquishiness(companion.getDetailIds(), transitionViewStateCopy$default.getMeasureHeight(), transitionViewStateCopy$default, f);
        return transitionViewStateCopy$default;
    }

    public final void updateAnimatorDurationScale() {
        SeekBarObserver seekBarObserver;
        if (this.mediaFlags.isMediaControlsRefactorEnabled() && (seekBarObserver = this.seekBarObserver) != null) {
            if (seekBarObserver == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBarObserver");
                seekBarObserver = null;
            }
            seekBarObserver.setAnimationEnabled(this.globalSettings.getFloat("animator_duration_scale", 1.0f) > 0.0f);
        }
    }
}
