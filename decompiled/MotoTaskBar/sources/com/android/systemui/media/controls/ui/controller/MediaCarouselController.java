package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.recyclerview.widget.DiffUtil;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Dumpable;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.binder.MediaControlViewBinder;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.controller.MediaPlayerData;
import com.android.systemui.media.controls.ui.util.MediaViewModelCallback;
import com.android.systemui.media.controls.ui.util.MediaViewModelListUpdateCallback;
import com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaScrollView;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.res.R$color;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.animation.UniqueObjectHostViewKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private static final PathInterpolator TRANSFORM_BEZIER = new PathInterpolator(0.68f, 0.0f, 0.0f, 1.0f);
    private final ActivityStarter activityStarter;
    private final ContentObserver animationScaleObserver;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Executor bgExecutor;
    private Locale carouselLocale;
    private int carouselMeasureHeight;
    private int carouselMeasureWidth;
    private final List commonViewModels;
    private final MediaCarouselController$configListener$1 configListener;
    private final Context context;
    private final Map controllerByViewModel;
    private int currentCarouselHeight;
    private int currentCarouselWidth;
    private int currentEndLocation;
    private int currentStartLocation;
    private float currentTransitionProgress;
    private boolean currentlyExpanded;
    private boolean currentlyShowingOnlyActive;
    private final MediaCarouselControllerLogger debugLogger;
    private MediaHostState desiredHostState;
    private int desiredLocation;
    private final GlobalSettings globalSettings;
    private int heightInSceneContainerPx;
    private boolean isRtl;
    private Set keysNeedRemoval;
    private final MediaUiEventLogger logger;
    private final CoroutineDispatcher mainDispatcher;
    private MediaScrollView mediaCarousel;
    private final MediaCarouselScrollHandler mediaCarouselScrollHandler;
    private final MediaCarouselViewModel mediaCarouselViewModel;
    private final ViewGroup mediaContent;
    private final Provider mediaControlPanelFactory;
    public MediaDataManager.Listener mediaDataListener;
    private final MediaFlags mediaFlags;
    private final ViewGroup mediaFrame;
    private final MediaHostStatesManager mediaHostStatesManager;
    private final MediaDataManager mediaManager;
    private final Provider mediaViewControllerFactory;
    private boolean needsReordering;
    private PageIndicator pageIndicator;
    private boolean playersVisible;
    private View settingsButton;
    private boolean shouldScrollToKey;
    private final SystemClock systemClock;
    public Function0 updateHostVisibility;
    public Function0 updateUserVisibility;
    public OnReorderingAllowedListener visualStabilityCallback;
    private final VisualStabilityProvider visualStabilityProvider;
    private int widthInSceneContainerPx;

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$1, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        AnonymousClass1(Object obj) {
            super(0, obj, MediaCarouselController.class, "onSwipeToDismiss", "onSwipeToDismiss()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m1376invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1376invoke() {
            ((MediaCarouselController) this.receiver).onSwipeToDismiss();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$11, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final class AnonymousClass11 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$11$1, reason: invalid class name */
        /* JADX INFO: compiled from: MediaCarouselController.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaCarouselController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(MediaCarouselController mediaCarouselController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaCarouselController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                if (!this.this$0.mediaFlags.isMediaControlsRefactorEnabled()) {
                    return Unit.INSTANCE;
                }
                this.this$0.listenForMediaItemsChanges(coroutineScope);
                return Unit.INSTANCE;
            }
        }

        AnonymousClass11(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            AnonymousClass11 anonymousClass11 = MediaCarouselController.this.new AnonymousClass11(continuation);
            anonymousClass11.L$0 = lifecycleOwner;
            return anonymousClass11.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaCarouselController.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$2, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
        AnonymousClass2(Object obj) {
            super(0, obj, MediaCarouselController.class, "updatePageIndicatorLocation", "updatePageIndicatorLocation()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m1377invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1377invoke() {
            ((MediaCarouselController) this.receiver).updatePageIndicatorLocation();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$3, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        AnonymousClass3(Object obj) {
            super(1, obj, MediaCarouselController.class, "updateSeekbarListening", "updateSeekbarListening(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z) {
            ((MediaCarouselController) this.receiver).updateSeekbarListening(z);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$4, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1 {
        AnonymousClass4(Object obj) {
            super(1, obj, MediaCarouselController.class, "closeGuts", "closeGuts(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z) {
            ((MediaCarouselController) this.receiver).closeGuts(z);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$5, reason: invalid class name */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function1 {
        AnonymousClass5(Object obj) {
            super(1, obj, MediaCarouselController.class, "logSmartspaceImpression", "logSmartspaceImpression(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(boolean z) {
            ((MediaCarouselController) this.receiver).logSmartspaceImpression(z);
        }
    }

    /* JADX INFO: compiled from: MediaCarouselController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float calculateAlpha(float f, float f2, float f3) {
            return getTRANSFORM_BEZIER().getInterpolation(MathUtils.constrain((f - f2) / (f3 - f2), 0.0f, 1.0f));
        }

        public final PathInterpolator getTRANSFORM_BEZIER() {
            return MediaCarouselController.TRANSFORM_BEZIER;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForMediaItemsChanges$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final class C00991 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForMediaItemsChanges$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: MediaCarouselController.kt */
        final class C00101 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaCarouselController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00101(MediaCarouselController mediaCarouselController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaCarouselController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00101 c00101 = new C00101(this.this$0, continuation);
                c00101.L$0 = obj;
                return c00101;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(List list, Continuation continuation) {
                return ((C00101) create(list, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List list = (List) this.L$0;
                MediaViewModelCallback mediaViewModelCallback = new MediaViewModelCallback(this.this$0.commonViewModels, list);
                DiffUtil.calculateDiff(mediaViewModelCallback).dispatchUpdatesTo(new MediaViewModelListUpdateCallback(this.this$0.commonViewModels, list, new MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$1(this.this$0), new MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$2(this.this$0), new MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$3(this.this$0), new MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$4(this.this$0)));
                this.this$0.setNewViewModelsList(list);
                return Unit.INSTANCE;
            }
        }

        C00991(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaCarouselController.this.new C00991(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00991) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow mediaItems = MediaCarouselController.this.mediaCarouselViewModel.getMediaItems();
                C00101 c00101 = new C00101(MediaCarouselController.this, null);
                this.label = 1;
                if (FlowKt.collectLatest(mediaItems, c00101, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$onAdded$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaCarouselController.kt */
    final /* synthetic */ class C01001 extends FunctionReferenceImpl implements Function0 {
        C01001(Object obj) {
            super(0, obj, MediaCarouselController.class, "updateCarouselDimensions", "updateCarouselDimensions()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m1379invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1379invoke() {
            ((MediaCarouselController) this.receiver).updateCarouselDimensions();
        }
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.media.controls.ui.controller.MediaCarouselController$configListener$1] */
    public MediaCarouselController(Context context, Provider provider, VisualStabilityProvider visualStabilityProvider, MediaHostStatesManager mediaHostStatesManager, ActivityStarter activityStarter, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, DelayableExecutor delayableExecutor, Executor executor, CoroutineDispatcher coroutineDispatcher2, MediaDataManager mediaDataManager, final ConfigurationController configurationController, DumpManager dumpManager, MediaUiEventLogger mediaUiEventLogger, MediaCarouselControllerLogger mediaCarouselControllerLogger, MediaFlags mediaFlags, GlobalSettings globalSettings, MediaCarouselViewModel mediaCarouselViewModel, Provider provider2) {
        context.getClass();
        provider.getClass();
        visualStabilityProvider.getClass();
        mediaHostStatesManager.getClass();
        activityStarter.getClass();
        systemClock.getClass();
        coroutineDispatcher.getClass();
        delayableExecutor.getClass();
        executor.getClass();
        coroutineDispatcher2.getClass();
        mediaDataManager.getClass();
        configurationController.getClass();
        dumpManager.getClass();
        mediaUiEventLogger.getClass();
        mediaCarouselControllerLogger.getClass();
        mediaFlags.getClass();
        globalSettings.getClass();
        mediaCarouselViewModel.getClass();
        provider2.getClass();
        this.context = context;
        this.mediaControlPanelFactory = provider;
        this.visualStabilityProvider = visualStabilityProvider;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.activityStarter = activityStarter;
        this.systemClock = systemClock;
        this.mainDispatcher = coroutineDispatcher;
        this.bgExecutor = executor;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.mediaManager = mediaDataManager;
        this.logger = mediaUiEventLogger;
        this.debugLogger = mediaCarouselControllerLogger;
        this.mediaFlags = mediaFlags;
        this.globalSettings = globalSettings;
        this.mediaCarouselViewModel = mediaCarouselViewModel;
        this.mediaViewControllerFactory = provider2;
        this.desiredLocation = -1;
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.keysNeedRemoval = new LinkedHashSet();
        ContentObserver contentObserver = new ContentObserver() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$animationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (this.this$0.mediaFlags.isMediaControlsRefactorEnabled()) {
                    Iterator it = this.this$0.controllerByViewModel.values().iterator();
                    while (it.hasNext()) {
                        ((MediaViewController) it.next()).updateAnimatorDurationScale();
                    }
                } else {
                    Iterator it2 = MediaPlayerData.INSTANCE.players().iterator();
                    while (it2.hasNext()) {
                        ((MediaControlPanel) it2.next()).updateAnimatorDurationScale();
                    }
                }
            }
        };
        this.animationScaleObserver = contentObserver;
        this.currentlyExpanded = true;
        this.configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                if (configuration == null) {
                    return;
                }
                this.this$0.setRtl(configuration.getLayoutDirection() == 1);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onDensityOrFontScaleChanged() {
                this.this$0.updatePlayers(true);
                this.this$0.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onLocaleListChanged() {
                if (Intrinsics.areEqual(this.this$0.carouselLocale, this.this$0.context.getResources().getConfiguration().getLocales().get(0))) {
                    return;
                }
                MediaCarouselController mediaCarouselController = this.this$0;
                mediaCarouselController.carouselLocale = mediaCarouselController.context.getResources().getConfiguration().getLocales().get(0);
                this.this$0.updatePlayers(true);
                this.this$0.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onThemeChanged() {
                this.this$0.updatePlayers(false);
                this.this$0.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onUiModeChanged() {
                this.this$0.updatePlayers(false);
                this.this$0.inflateSettingsButton();
            }
        };
        this.controllerByViewModel = new LinkedHashMap();
        this.commonViewModels = new ArrayList();
        DumpManager.registerDumpable$default(dumpManager, "MediaCarouselController", this, null, 4, null);
        ViewGroup viewGroupInflateMediaCarousel = inflateMediaCarousel();
        this.mediaFrame = viewGroupInflateMediaCarousel;
        this.mediaCarousel = (MediaScrollView) viewGroupInflateMediaCarousel.requireViewById(R$id.media_carousel_scroller);
        this.pageIndicator = (PageIndicator) viewGroupInflateMediaCarousel.requireViewById(R$id.media_page_indicator);
        this.mediaCarouselScrollHandler = new MediaCarouselScrollHandler(this.mediaCarousel, this.pageIndicator, delayableExecutor, new AnonymousClass1(this), new AnonymousClass2(this), new AnonymousClass3(this), new AnonymousClass4(this), new AnonymousClass5(this), mediaUiEventLogger);
        this.carouselLocale = context.getResources().getConfiguration().getLocales().get(0);
        setRtl(context.getResources().getConfiguration().getLayoutDirection() == 1);
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) this.mediaCarousel.requireViewById(R$id.media_carousel);
        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.6
            @Override // java.lang.Runnable
            public final void run() {
                configurationController.addCallback(this.configListener);
            }
        });
        if (!mediaFlags.isMediaControlsRefactorEnabled()) {
            setVisualStabilityCallback(new OnReorderingAllowedListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.7
            });
            setMediaDataListener(new MediaDataManager.Listener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.8
                @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
                public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
                    str.getClass();
                    mediaData.getClass();
                    MediaCarouselController.this.debugLogger.logMediaLoaded(str, mediaData.getActive());
                    if (MediaCarouselController.this.addOrUpdatePlayer(str, str2, mediaData, z2) && MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleToUser() && MediaCarouselController.this.getMediaCarouselScrollHandler().getVisibleMediaIndex() == MediaPlayerData.INSTANCE.getMediaPlayerIndex(str)) {
                        MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                        mediaCarouselController.logSmartspaceImpression(mediaCarouselController.getMediaCarouselScrollHandler().getQsExpanded());
                    }
                    Boolean boolIsPlaying = mediaData.isPlaying();
                    if (!((boolIsPlaying != null ? boolIsPlaying.booleanValue() ^ true : mediaData.isClearable()) && !mediaData.getActive()) || Utils.useMediaResumption(MediaCarouselController.this.context)) {
                        MediaCarouselController.this.keysNeedRemoval.remove(str);
                    } else if (MediaCarouselController.this.isReorderingAllowed()) {
                        onMediaDataRemoved(str);
                    } else {
                        MediaCarouselController.this.keysNeedRemoval.add(str);
                    }
                }

                @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
                public void onMediaDataRemoved(String str) {
                    str.getClass();
                    MediaCarouselController.this.debugLogger.logMediaRemoved(str);
                    MediaCarouselController.removePlayer$default(MediaCarouselController.this, str, false, false, 6, null);
                }
            });
            setUpListeners();
        }
        viewGroupInflateMediaCarousel.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.9
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                MediaCarouselController.this.updatePageIndicatorLocation();
            }
        });
        mediaHostStatesManager.addCallback(new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.10
            @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
            public void onHostStateChanged(int i, MediaHostState mediaHostState) {
                mediaHostState.getClass();
                MediaCarouselController.this.getUpdateUserVisibility().mo2224invoke();
                if (i == MediaCarouselController.this.desiredLocation) {
                    MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                    MediaCarouselController.onDesiredLocationChanged$default(mediaCarouselController, mediaCarouselController.desiredLocation, mediaHostState, false, 0L, 0L, 24, null);
                }
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached$default(this.mediaCarousel, null, new AnonymousClass11(null), 1, null);
        globalSettings.registerContentObserver(Settings.Global.getUriFor("animator_duration_scale"), contentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean addOrUpdatePlayer(String str, String str2, MediaData mediaData, boolean z) {
        MediaControlPanel mediaControlPanel;
        TransitionLayout player;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaCarouselController#addOrUpdatePlayer");
        }
        try {
            MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
            MediaPlayerData.moveIfExists$default(mediaPlayerData, str2, str, null, 4, null);
            MediaControlPanel mediaPlayer = mediaPlayerData.getMediaPlayer(str);
            MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt.elementAtOrNull(mediaPlayerData.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex());
            if (mediaPlayer == null) {
                MediaControlPanel mediaControlPanel2 = (MediaControlPanel) this.mediaControlPanelFactory.get();
                if (this.mediaFlags.isSceneContainerEnabled()) {
                    mediaControlPanel2.getMediaViewController().setWidthInSceneContainerPx(this.widthInSceneContainerPx);
                    mediaControlPanel2.getMediaViewController().setHeightInSceneContainerPx(this.heightInSceneContainerPx);
                }
                MediaViewHolder.Companion companion = MediaViewHolder.Companion;
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.context);
                layoutInflaterFrom.getClass();
                mediaControlPanel2.attachPlayer(companion.create(layoutInflaterFrom, this.mediaContent));
                mediaControlPanel2.getMediaViewController().setSizeChangedListener(new MediaCarouselController$addOrUpdatePlayer$1$1(this));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                MediaViewHolder mediaViewHolder = mediaControlPanel2.getMediaViewHolder();
                if (mediaViewHolder != null && (player = mediaViewHolder.getPlayer()) != null) {
                    player.setLayoutParams(layoutParams);
                }
                mediaControlPanel2.bindPlayer(mediaData, str);
                mediaControlPanel2.setListening(this.mediaCarouselScrollHandler.getVisibleToUser() && this.currentlyExpanded);
                mediaPlayerData.addMediaPlayer(str, mediaData, mediaControlPanel2, this.systemClock, z, this.debugLogger);
                MediaViewController mediaViewController = mediaControlPanel2.getMediaViewController();
                mediaViewController.getClass();
                updateViewControllerToState(mediaViewController, true);
                if (!(this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE)) && (this.shouldScrollToKey || !mediaData.getActive())) {
                    this.needsReordering = true;
                } else {
                    reorderAllPlayers(mediaSortKey, str);
                }
                mediaControlPanel = mediaPlayer;
            } else {
                mediaPlayer.bindPlayer(mediaData, str);
                mediaControlPanel = mediaPlayer;
                mediaPlayerData.addMediaPlayer(str, mediaData, mediaControlPanel, this.systemClock, z, this.debugLogger);
                this.needsReordering = true;
            }
            updatePageIndicator();
            this.mediaCarouselScrollHandler.onPlayersChanged();
            UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
            boolean z2 = mediaControlPanel == null;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return z2;
        } finally {
        }
    }

    public static /* synthetic */ void getCurrentEndLocation$annotations() {
    }

    public static /* synthetic */ void getCurrentlyExpanded$annotations() {
    }

    public static /* synthetic */ void getMediaCarousel$annotations() {
    }

    public static /* synthetic */ void getPageIndicator$annotations() {
    }

    public static /* synthetic */ void getSettingsButton$annotations() {
    }

    private final ViewGroup inflateMediaCarousel() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R$layout.media_carousel, (ViewGroup) new UniqueObjectHostView(this.context), false);
        viewInflate.getClass();
        ViewGroup viewGroup = (ViewGroup) viewInflate;
        viewGroup.setLayoutDirection(3);
        return viewGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void inflateSettingsButton() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R$layout.media_carousel_settings_button, this.mediaFrame, false);
        viewInflate.getClass();
        if (this.settingsButton != null) {
            this.mediaFrame.removeView(getSettingsButton());
        }
        this.settingsButton = viewInflate;
        this.mediaFrame.addView(getSettingsButton());
        this.mediaCarouselScrollHandler.onSettingsButtonUpdated(viewInflate);
        getSettingsButton().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.inflateSettingsButton.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaCarouselController.this.logger.logCarouselSettings();
                MediaCarouselController.this.activityStarter.startActivity(MediaCarouselControllerKt.settingsIntent, true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isReorderingAllowed() {
        return this.visualStabilityProvider.isReorderingAllowed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Job listenForMediaItemsChanges(CoroutineScope coroutineScope) {
        return BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00991(null), 3, null);
    }

    private final void maybeResetSettingsCog() {
        Map mediaHostStates = this.mediaHostStatesManager.getMediaHostStates();
        MediaHostState mediaHostState = (MediaHostState) mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        boolean showsOnlyActiveMedia = mediaHostState != null ? mediaHostState.getShowsOnlyActiveMedia() : true;
        MediaHostState mediaHostState2 = (MediaHostState) mediaHostStates.get(Integer.valueOf(this.currentStartLocation));
        boolean showsOnlyActiveMedia2 = mediaHostState2 != null ? mediaHostState2.getShowsOnlyActiveMedia() : showsOnlyActiveMedia;
        if (this.currentlyShowingOnlyActive == showsOnlyActiveMedia) {
            float f = this.currentTransitionProgress;
            if (f == 1.0f || f == 0.0f || showsOnlyActiveMedia2 == showsOnlyActiveMedia) {
                return;
            }
        }
        this.currentlyShowingOnlyActive = showsOnlyActiveMedia;
        this.mediaCarouselScrollHandler.resetTranslation(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAdded(MediaCommonViewModel mediaCommonViewModel, int i) {
        MediaViewController mediaViewController = (MediaViewController) this.mediaViewControllerFactory.get();
        mediaViewController.setSizeChangedListener(new C01001(this));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        if (!(mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl)) {
            throw new NoWhenBranchMatchedException();
        }
        MediaViewHolder.Companion companion = MediaViewHolder.Companion;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.context);
        layoutInflaterFrom.getClass();
        MediaViewHolder mediaViewHolderCreate = companion.create(layoutInflaterFrom, this.mediaContent);
        if (this.mediaFlags.isSceneContainerEnabled()) {
            mediaViewController.setWidthInSceneContainerPx(this.widthInSceneContainerPx);
            mediaViewController.setHeightInSceneContainerPx(this.heightInSceneContainerPx);
        }
        mediaViewController.attachPlayer(mediaViewHolderCreate);
        mediaViewController.getMediaViewHolder().getPlayer().setLayoutParams(layoutParams);
        MediaControlViewBinder.INSTANCE.bind(mediaViewHolderCreate, ((MediaCommonViewModel.MediaControl) mediaCommonViewModel).getControlViewModel(), mediaViewController, this.backgroundDispatcher, this.mainDispatcher, this.mediaFlags);
        this.mediaContent.addView(mediaViewHolderCreate.getPlayer(), i);
        mediaViewController.setListening(this.mediaCarouselScrollHandler.getVisibleToUser() && this.currentlyExpanded);
        updateViewControllerToState(mediaViewController, true);
        updatePageIndicator();
        this.mediaCarouselScrollHandler.onPlayersChanged();
        UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
        mediaCommonViewModel.getOnAdded().invoke(mediaCommonViewModel);
        this.controllerByViewModel.put(mediaCommonViewModel, mediaViewController);
    }

    public static /* synthetic */ Unit onDesiredLocationChanged$default(MediaCarouselController mediaCarouselController, int i, MediaHostState mediaHostState, boolean z, long j, long j2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            j = 200;
        }
        return mediaCarouselController.onDesiredLocationChanged(i, mediaHostState, z, j, (i2 & 16) != 0 ? 0L : j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onMoved(MediaCommonViewModel mediaCommonViewModel, int i, int i2) {
        MediaViewController mediaViewController = (MediaViewController) this.controllerByViewModel.get(mediaCommonViewModel);
        if (mediaViewController != null) {
            this.mediaContent.removeViewAt(i);
            if (!(mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl)) {
                throw new NoWhenBranchMatchedException();
            }
            this.mediaContent.addView(mediaViewController.getMediaViewHolder().getPlayer(), i2);
        }
        updatePageIndicator();
        this.mediaCarouselScrollHandler.onPlayersChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRemoved(MediaCommonViewModel mediaCommonViewModel) {
        MediaViewController mediaViewController = (MediaViewController) this.controllerByViewModel.remove(mediaCommonViewModel);
        if (mediaViewController != null) {
            if (!(mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl)) {
                throw new NoWhenBranchMatchedException();
            }
            this.mediaCarouselScrollHandler.onPrePlayerRemoved(mediaViewController.getMediaViewHolder().getPlayer());
            this.mediaContent.removeView(mediaViewController.getMediaViewHolder().getPlayer());
            mediaViewController.onDestroy();
            this.mediaCarouselScrollHandler.onPlayersChanged();
            updatePageIndicator();
            mediaCommonViewModel.getOnRemoved().invoke(Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSwipeToDismiss() {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.mediaCarouselViewModel.onSwipeToDismiss();
            return;
        }
        int i = 0;
        for (Object obj : MediaPlayerData.INSTANCE.players()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
            if (mediaControlPanel.mIsImpressed) {
                mediaControlPanel.mIsImpressed = false;
            }
            i = i2;
        }
        this.logger.logSwipeDismiss();
        this.mediaManager.onSwipeToDismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onUpdated(MediaCommonViewModel mediaCommonViewModel) {
        mediaCommonViewModel.getOnUpdated().invoke(mediaCommonViewModel);
        updatePageIndicator();
        this.mediaCarouselScrollHandler.onPlayersChanged();
    }

    public static /* synthetic */ MediaControlPanel removePlayer$default(MediaCarouselController mediaCarouselController, String str, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        return mediaCarouselController.removePlayer(str, z, z2);
    }

    private final void reorderAllPlayers(MediaPlayerData.MediaSortKey mediaSortKey, String str) {
        this.mediaContent.removeAllViews();
        Iterator it = MediaPlayerData.INSTANCE.players().iterator();
        while (it.hasNext()) {
            MediaViewHolder mediaViewHolder = ((MediaControlPanel) it.next()).getMediaViewHolder();
            if (mediaViewHolder != null) {
                this.mediaContent.addView(mediaViewHolder.getPlayer());
            }
        }
        this.mediaCarouselScrollHandler.onPlayersChanged();
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        mediaPlayerData.updateVisibleMediaPlayers();
        int i = 0;
        if (this.shouldScrollToKey) {
            this.shouldScrollToKey = false;
            int i2 = -1;
            int mediaPlayerIndex = str != null ? mediaPlayerData.getMediaPlayerIndex(str) : -1;
            if (mediaPlayerIndex != -1) {
                if (mediaSortKey != null) {
                    Iterator it2 = mediaPlayerData.playerKeys().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Object next = it2.next();
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        if (Intrinsics.areEqual(mediaSortKey, (MediaPlayerData.MediaSortKey) next)) {
                            i2 = i;
                            break;
                        }
                        i++;
                    }
                    this.mediaCarouselScrollHandler.scrollToPlayer(i2, mediaPlayerIndex);
                } else {
                    MediaCarouselScrollHandler.scrollToPlayer$default(this.mediaCarouselScrollHandler, 0, mediaPlayerIndex, 1, null);
                }
            }
        } else if (this.isRtl && this.mediaContent.getChildCount() > 0) {
            MediaCarouselScrollHandler.scrollToPlayer$default(this.mediaCarouselScrollHandler, 0, 0, 1, null);
        }
        MediaPlayerData mediaPlayerData2 = MediaPlayerData.INSTANCE;
        if (mediaPlayerData2.players().size() != this.mediaContent.getChildCount()) {
            Log.e("MediaCarouselController", "Size of players list and number of views in carousel are out of sync. Players size is " + mediaPlayerData2.players().size() + ". View count is " + this.mediaContent.getChildCount() + ".");
        }
    }

    static /* synthetic */ void reorderAllPlayers$default(MediaCarouselController mediaCarouselController, MediaPlayerData.MediaSortKey mediaSortKey, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        mediaCarouselController.reorderAllPlayers(mediaSortKey, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setNewViewModelsList(List list) {
        this.commonViewModels.clear();
        this.commonViewModels.addAll(list);
        HashSet hashSet = CollectionsKt.toHashSet(list);
        Map map = this.controllerByViewModel;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (!hashSet.contains(entry.getKey())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            onRemoved((MediaCommonViewModel) ((Map.Entry) it.next()).getKey());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setRtl(boolean z) {
        if (z != this.isRtl) {
            this.isRtl = z;
            this.mediaFrame.setLayoutDirection(z ? 1 : 0);
            this.mediaCarouselScrollHandler.scrollToStart();
        }
    }

    private final void setUpListeners() {
        this.visualStabilityProvider.addPersistentReorderingAllowedListener(getVisualStabilityCallback());
        this.mediaManager.addListener(getMediaDataListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateCarouselDimensions() {
        int iMax;
        int iMax2 = 0;
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            iMax = 0;
            for (MediaViewController mediaViewController : this.controllerByViewModel.values()) {
                iMax2 = Math.max(iMax2, mediaViewController.getCurrentWidth() + ((int) mediaViewController.getTranslationX()));
                iMax = Math.max(iMax, mediaViewController.getCurrentHeight() + ((int) mediaViewController.getTranslationY()));
            }
        } else {
            Iterator it = MediaPlayerData.INSTANCE.players().iterator();
            iMax = 0;
            while (it.hasNext()) {
                MediaViewController mediaViewController2 = ((MediaControlPanel) it.next()).getMediaViewController();
                mediaViewController2.getClass();
                iMax2 = Math.max(iMax2, mediaViewController2.getCurrentWidth() + ((int) mediaViewController2.getTranslationX()));
                iMax = Math.max(iMax, mediaViewController2.getCurrentHeight() + ((int) mediaViewController2.getTranslationY()));
            }
        }
        if (iMax2 == this.currentCarouselWidth && iMax == this.currentCarouselHeight) {
            return;
        }
        this.currentCarouselWidth = iMax2;
        this.currentCarouselHeight = iMax;
        this.mediaCarouselScrollHandler.setCarouselBounds(iMax2, iMax);
        updatePageIndicatorLocation();
        updatePageIndicatorAlpha();
    }

    private final void updateCarouselSize() {
        MeasurementInput measurementInput;
        MeasurementInput measurementInput2;
        MeasurementInput measurementInput3;
        MeasurementInput measurementInput4;
        MediaHostState mediaHostState = this.desiredHostState;
        int width = (mediaHostState == null || (measurementInput4 = mediaHostState.getMeasurementInput()) == null) ? 0 : measurementInput4.getWidth();
        MediaHostState mediaHostState2 = this.desiredHostState;
        int height = (mediaHostState2 == null || (measurementInput3 = mediaHostState2.getMeasurementInput()) == null) ? 0 : measurementInput3.getHeight();
        if ((width == this.carouselMeasureWidth || width == 0) && (height == this.carouselMeasureHeight || height == 0)) {
            return;
        }
        this.carouselMeasureWidth = width;
        this.carouselMeasureHeight = height;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.qs_media_padding) + width;
        MediaHostState mediaHostState3 = this.desiredHostState;
        int widthMeasureSpec = (mediaHostState3 == null || (measurementInput2 = mediaHostState3.getMeasurementInput()) == null) ? 0 : measurementInput2.getWidthMeasureSpec();
        MediaHostState mediaHostState4 = this.desiredHostState;
        this.mediaCarousel.measure(widthMeasureSpec, (mediaHostState4 == null || (measurementInput = mediaHostState4.getMeasurementInput()) == null) ? 0 : measurementInput.getHeightMeasureSpec());
        MediaScrollView mediaScrollView = this.mediaCarousel;
        mediaScrollView.layout(0, 0, width, mediaScrollView.getMeasuredHeight());
        this.mediaCarouselScrollHandler.setPlayerWidthPlusPadding(dimensionPixelSize);
    }

    private final void updateMediaPlayers(boolean z) {
        MediaViewHolder mediaViewHolder;
        this.pageIndicator.setTintList(ColorStateList.valueOf(this.context.getColor(R$color.media_paging_indicator)));
        if (z) {
            this.mediaContent.removeAllViews();
            for (MediaCommonViewModel mediaCommonViewModel : this.commonViewModels) {
                if (!(mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl)) {
                    throw new NoWhenBranchMatchedException();
                }
                MediaViewController mediaViewController = (MediaViewController) this.controllerByViewModel.get(mediaCommonViewModel);
                if (mediaViewController != null && (mediaViewHolder = mediaViewController.getMediaViewHolder()) != null) {
                    this.mediaContent.addView(mediaViewHolder.getPlayer());
                }
            }
        }
    }

    private final void updatePageIndicator() {
        int childCount = this.mediaContent.getChildCount();
        this.pageIndicator.setNumPages(childCount);
        if (childCount == 1) {
            this.pageIndicator.setLocation(0.0f);
        }
        updatePageIndicatorAlpha();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updatePageIndicatorLocation() {
        int width;
        int width2;
        if (this.isRtl) {
            width = this.pageIndicator.getWidth();
            width2 = this.currentCarouselWidth;
        } else {
            width = this.currentCarouselWidth;
            width2 = this.pageIndicator.getWidth();
        }
        this.pageIndicator.setTranslationX(((width - width2) / 2.0f) + this.mediaCarouselScrollHandler.getContentTranslation());
        this.pageIndicator.getLayoutParams().getClass();
        this.pageIndicator.setTranslationY((this.mediaCarousel.getMeasuredHeight() - this.pageIndicator.getHeight()) - ((ViewGroup.MarginLayoutParams) r0).bottomMargin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updatePlayers(boolean z) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            updateMediaPlayers(z);
            return;
        }
        this.pageIndicator.setTintList(ColorStateList.valueOf(this.context.getColor(R$color.media_paging_indicator)));
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt.elementAtOrNull(mediaPlayerData.visiblePlayerKeys(), this.mediaCarouselScrollHandler.getVisibleMediaIndex());
        for (Triple triple : mediaPlayerData.mediaData()) {
            String str = (String) triple.component1();
            MediaData mediaData = (MediaData) triple.component2();
            ((Boolean) triple.component3()).getClass();
            boolean zIsSsReactivated = MediaPlayerData.INSTANCE.isSsReactivated(str);
            if (z) {
                removePlayer(str, false, false);
            }
            addOrUpdatePlayer(str, null, mediaData, zIsSsReactivated);
            if (z) {
                reorderAllPlayers$default(this, mediaSortKey, null, 2, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSeekbarListening(boolean z) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            Iterator it = this.controllerByViewModel.values().iterator();
            while (it.hasNext()) {
                ((MediaViewController) it.next()).setListening(z && this.currentlyExpanded);
            }
        } else {
            Iterator it2 = MediaPlayerData.INSTANCE.players().iterator();
            while (it2.hasNext()) {
                ((MediaControlPanel) it2.next()).setListening(z && this.currentlyExpanded);
            }
        }
    }

    private final void updateViewControllerToState(MediaViewController mediaViewController, boolean z) {
        MediaViewController.setCurrentState$default(mediaViewController, this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, z, false, 16, null);
    }

    public final void closeGuts(boolean z) {
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            Iterator it = this.controllerByViewModel.values().iterator();
            while (it.hasNext()) {
                ((MediaViewController) it.next()).closeGuts(z);
            }
        } else {
            Iterator it2 = MediaPlayerData.INSTANCE.players().iterator();
            while (it2.hasNext()) {
                ((MediaControlPanel) it2.next()).closeGuts(z);
            }
        }
    }

    public final MediaCarouselScrollHandler getMediaCarouselScrollHandler() {
        return this.mediaCarouselScrollHandler;
    }

    public final MediaDataManager.Listener getMediaDataListener() {
        MediaDataManager.Listener listener = this.mediaDataListener;
        if (listener != null) {
            return listener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaDataListener");
        return null;
    }

    public final ViewGroup getMediaFrame() {
        return this.mediaFrame;
    }

    public final View getSettingsButton() {
        View view = this.settingsButton;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
        return null;
    }

    public final Function0 getUpdateUserVisibility() {
        Function0 function0 = this.updateUserVisibility;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("updateUserVisibility");
        return null;
    }

    public final OnReorderingAllowedListener getVisualStabilityCallback() {
        OnReorderingAllowedListener onReorderingAllowedListener = this.visualStabilityCallback;
        if (onReorderingAllowedListener != null) {
            return onReorderingAllowedListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("visualStabilityCallback");
        return null;
    }

    public final void logSmartspaceImpression(boolean z) {
        int visibleMediaIndex = this.mediaCarouselScrollHandler.getVisibleMediaIndex();
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        if (mediaPlayerData.players().size() > visibleMediaIndex) {
            MediaControlPanel mediaControlPanel = mediaPlayerData.getMediaControlPanel(visibleMediaIndex);
            if ((mediaPlayerData.hasActiveMediaOrRecommendationCard() || z) && mediaControlPanel != null) {
                mediaControlPanel.mIsImpressed = true;
            }
        }
    }

    public final Unit onDesiredLocationChanged(final int i, MediaHostState mediaHostState, boolean z, long j, long j2) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaCarouselController#onDesiredLocationChanged");
        }
        Unit unit = null;
        if (mediaHostState != null) {
            try {
                if (this.desiredLocation != i) {
                    this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$onDesiredLocationChanged$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.this$0.logger.logCarouselPosition(i);
                        }
                    });
                }
                this.desiredLocation = i;
                this.desiredHostState = mediaHostState;
                setCurrentlyExpanded(mediaHostState.getExpansion() > 0.0f);
                boolean z2 = (this.currentlyExpanded || this.mediaManager.hasActiveMediaOrRecommendation() || !mediaHostState.getShowsOnlyActiveMedia()) ? false : true;
                if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
                    for (MediaViewController mediaViewController : this.controllerByViewModel.values()) {
                        if (z) {
                            mediaViewController.animatePendingStateChange(j, j2);
                        }
                        if (z2 && mediaViewController.isGutsVisible()) {
                            mediaViewController.closeGuts(!z);
                        }
                        mediaViewController.onLocationPreChange(i);
                    }
                } else {
                    for (MediaControlPanel mediaControlPanel : MediaPlayerData.INSTANCE.players()) {
                        if (z) {
                            mediaControlPanel.getMediaViewController().animatePendingStateChange(j, j2);
                        }
                        if (z2 && mediaControlPanel.getMediaViewController().isGutsVisible()) {
                            mediaControlPanel.closeGuts(!z);
                        }
                        mediaControlPanel.getMediaViewController().onLocationPreChange(i);
                    }
                }
                this.mediaCarouselScrollHandler.setShowsSettingsButton(!mediaHostState.getShowsOnlyActiveMedia());
                this.mediaCarouselScrollHandler.setFalsingProtectionNeeded(mediaHostState.getFalsingProtectionNeeded());
                boolean visible = mediaHostState.getVisible();
                if (visible != this.playersVisible) {
                    this.playersVisible = visible;
                    if (visible) {
                        MediaCarouselScrollHandler.resetTranslation$default(this.mediaCarouselScrollHandler, false, 1, null);
                    }
                }
                updateCarouselSize();
                unit = Unit.INSTANCE;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
        if (zIsEnabled) {
            TraceUtilsKt.endSlice();
        }
        return unit;
    }

    public final MediaControlPanel removePlayer(String str, boolean z, boolean z2) {
        str.getClass();
        MediaControlPanel mediaControlPanelRemoveMediaPlayer = MediaPlayerData.INSTANCE.removeMediaPlayer(str, z || z2);
        if (mediaControlPanelRemoveMediaPlayer == null) {
            return null;
        }
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        MediaViewHolder mediaViewHolder = mediaControlPanelRemoveMediaPlayer.getMediaViewHolder();
        mediaCarouselScrollHandler.onPrePlayerRemoved(mediaViewHolder != null ? mediaViewHolder.getPlayer() : null);
        ViewGroup viewGroup = this.mediaContent;
        MediaViewHolder mediaViewHolder2 = mediaControlPanelRemoveMediaPlayer.getMediaViewHolder();
        viewGroup.removeView(mediaViewHolder2 != null ? mediaViewHolder2.getPlayer() : null);
        mediaControlPanelRemoveMediaPlayer.onDestroy();
        this.mediaCarouselScrollHandler.onPlayersChanged();
        updatePageIndicator();
        if (z) {
            this.mediaManager.dismissMediaData(str, 0L);
        }
        return mediaControlPanelRemoveMediaPlayer;
    }

    public final void setCurrentState(int i, int i2, float f, boolean z) {
        if (i == this.currentStartLocation && i2 == this.currentEndLocation && f == this.currentTransitionProgress && !z) {
            return;
        }
        this.currentStartLocation = i;
        this.currentEndLocation = i2;
        this.currentTransitionProgress = f;
        if (this.mediaFlags.isMediaControlsRefactorEnabled()) {
            Iterator it = this.controllerByViewModel.values().iterator();
            while (it.hasNext()) {
                updateViewControllerToState((MediaViewController) it.next(), z);
            }
        } else {
            Iterator it2 = MediaPlayerData.INSTANCE.players().iterator();
            while (it2.hasNext()) {
                MediaViewController mediaViewController = ((MediaControlPanel) it2.next()).getMediaViewController();
                mediaViewController.getClass();
                updateViewControllerToState(mediaViewController, z);
            }
        }
        maybeResetSettingsCog();
        updatePageIndicatorAlpha();
    }

    public final void setCurrentlyExpanded(boolean z) {
        if (this.currentlyExpanded != z) {
            this.currentlyExpanded = z;
            updateSeekbarListening(this.mediaCarouselScrollHandler.getVisibleToUser());
        }
    }

    public final void setMediaDataListener(MediaDataManager.Listener listener) {
        listener.getClass();
        this.mediaDataListener = listener;
    }

    public final void setUpdateHostVisibility(Function0 function0) {
        function0.getClass();
        this.updateHostVisibility = function0;
    }

    public final void setUpdateUserVisibility(Function0 function0) {
        function0.getClass();
        this.updateUserVisibility = function0;
    }

    public final void setVisualStabilityCallback(OnReorderingAllowedListener onReorderingAllowedListener) {
        onReorderingAllowedListener.getClass();
        this.visualStabilityCallback = onReorderingAllowedListener;
    }

    public final void stopAllListeners() {
        if (!this.mediaFlags.isMediaControlsRefactorEnabled()) {
            this.visualStabilityProvider.removeReorderingAllowedListener(getVisualStabilityCallback());
            this.mediaManager.removeListener(getMediaDataListener());
        }
        for (Triple triple : MediaPlayerData.INSTANCE.mediaData()) {
            String str = (String) triple.component1();
            ((Boolean) triple.component3()).getClass();
            removePlayer(str, false, false);
        }
    }

    public final void updatePageIndicatorAlpha() {
        Map mediaHostStates = this.mediaHostStatesManager.getMediaHostStates();
        MediaHostState mediaHostState = (MediaHostState) mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        boolean visible = mediaHostState != null ? mediaHostState.getVisible() : false;
        MediaHostState mediaHostState2 = (MediaHostState) mediaHostStates.get(Integer.valueOf(this.currentStartLocation));
        boolean visible2 = mediaHostState2 != null ? mediaHostState2.getVisible() : false;
        float fLerp = 1.0f;
        float f = visible2 ? 1.0f : 0.0f;
        MediaHostState mediaHostState3 = (MediaHostState) mediaHostStates.get(Integer.valueOf(this.currentEndLocation));
        float fCalculateAlpha = (visible ? 1.0f : 0.0f) * Companion.calculateAlpha(mediaHostState3 != null ? mediaHostState3.getSquishFraction() : 1.0f, (this.pageIndicator.getTranslationY() + this.pageIndicator.getHeight()) / this.mediaCarousel.getMeasuredHeight(), 1.0f);
        if (!visible || !visible2) {
            float f2 = this.currentTransitionProgress;
            if (!visible) {
                f2 = 1.0f - f2;
            }
            fLerp = MathUtils.lerp(f, fCalculateAlpha, MathUtils.constrain(MathUtils.map(0.95f, 1.0f, 0.0f, 1.0f, f2), 0.0f, 1.0f));
        }
        this.pageIndicator.setAlpha(fLerp);
    }
}
