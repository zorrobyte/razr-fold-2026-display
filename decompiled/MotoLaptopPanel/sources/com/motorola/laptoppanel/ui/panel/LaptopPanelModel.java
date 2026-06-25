package com.motorola.laptoppanel.ui.panel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.laptoppanel.R;
import com.motorola.laptoppanel.ui.brightness.BrightnessHelper;
import com.motorola.laptoppanel.util.LaptopPrefs;
import com.motorola.laptoppanel.util.Logger;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: LaptopPanelModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelModel extends AndroidViewModel {
    private final MutableStateFlow _autoDimResetKey;
    private final MutableStateFlow _currentMediaController;
    private final MutableStateFlow _uiState;
    private final MediaSessionManager.OnActiveSessionsChangedListener activeSessionsChangedListener;
    private final Lazy audioManager$delegate;
    private final StateFlow autoDimResetKey;
    private final BrightnessHelper brightnessHelper;
    private final Function0 brightnessListener;
    private final LaptopPanelModel$controllerCallback$1 controllerCallback;
    private final StateFlow currentMediaController;
    private final int defaultPanelStyle;
    private Job dismissJob;
    private final LaptopPrefs laptopPrefs;
    private final Handler mainHandler;
    private final float maxBrightness;
    private final Lazy mediaSessionManager$delegate;
    private Job mediaStateJob;
    private final float minBrightness;
    private final LaptopPanelModel$panelViewStyleObserver$1 panelViewStyleObserver;
    private final LaptopPanelModel$screenStateReceiver$1 screenStateReceiver;
    private final StateFlow uiState;
    private final LaptopPanelModel$volumeReceiver$1 volumeReceiver;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ViewModelProvider.Factory provideFactory(final Application application, final String str) {
            application.getClass();
            str.getClass();
            return new ViewModelProvider.Factory() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$Companion$provideFactory$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public ViewModel create(Class cls) {
                    cls.getClass();
                    if (cls.isAssignableFrom(LaptopPanelModel.class)) {
                        return new LaptopPanelModel(application, str);
                    }
                    throw new IllegalArgumentException("Unknown ViewModel class");
                }
            };
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    public final class PanelType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ PanelType[] $VALUES;
        public static final PanelType NONE = new PanelType("NONE", 0);
        public static final PanelType TOUCHPAD = new PanelType("TOUCHPAD", 1);
        public static final PanelType MEDIA_CONTROLLER = new PanelType("MEDIA_CONTROLLER", 2);
        public static final PanelType BRIGHTNESS_SLIDER = new PanelType("BRIGHTNESS_SLIDER", 3);
        public static final PanelType VOLUME_SLIDER = new PanelType("VOLUME_SLIDER", 4);

        private static final /* synthetic */ PanelType[] $values() {
            return new PanelType[]{NONE, TOUCHPAD, MEDIA_CONTROLLER, BRIGHTNESS_SLIDER, VOLUME_SLIDER};
        }

        static {
            PanelType[] panelTypeArr$values = $values();
            $VALUES = panelTypeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(panelTypeArr$values);
        }

        private PanelType(String str, int i) {
        }

        public static PanelType valueOf(String str) {
            return (PanelType) Enum.valueOf(PanelType.class, str);
        }

        public static PanelType[] values() {
            return (PanelType[]) $VALUES.clone();
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$checkMedia$1, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ List $controllers;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ LaptopPanelModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(List list, LaptopPanelModel laptopPanelModel, Continuation continuation) {
            super(2, continuation);
            this.$controllers = list;
            this.this$0 = laptopPanelModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$controllers, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:49:0x013e  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) {
            /*
                Method dump skipped, instruction units count: 356
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$setIsMediaPlaying$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    final class C01291 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isPlaying;
        int label;
        final /* synthetic */ LaptopPanelModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01291(boolean z, LaptopPanelModel laptopPanelModel, Continuation continuation) {
            super(2, continuation);
            this.$isPlaying = z;
            this.this$0 = laptopPanelModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01291(this.$isPlaying, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object value;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Logger.INSTANCE.d("LaptopPanelModel", "setIsMediaPlaying: " + this.$isPlaying, new Object[0]);
            MutableStateFlow mutableStateFlow = this.this$0._uiState;
            boolean z = this.$isPlaying;
            do {
                value = mutableStateFlow.getValue();
            } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, z, false, false, 0, 0, null, null, 1015, null)));
            if (this.$isPlaying) {
                if (((LaptopPanelUiState) this.this$0._uiState.getValue()).getActivePanel() == PanelType.TOUCHPAD) {
                    this.this$0.showPanel(PanelType.MEDIA_CONTROLLER);
                }
            } else if (((LaptopPanelUiState) this.this$0._uiState.getValue()).getActivePanel() == PanelType.MEDIA_CONTROLLER) {
                this.this$0.showPanel(PanelType.TOUCHPAD);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$startDismissalTimer$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    final class C01301 extends SuspendLambda implements Function2 {
        int label;

        C01301(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LaptopPanelModel.this.new C01301(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(5000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Logger.INSTANCE.d("LaptopPanelModel", "Timeout reached. Hiding slider.", new Object[0]);
            LaptopPanelModel laptopPanelModel = LaptopPanelModel.this;
            laptopPanelModel.hideSliderPanel(((LaptopPanelUiState) laptopPanelModel._uiState.getValue()).getActivePanel());
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$updateModernStyle$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LaptopPanelModel.kt */
    final class C01311 extends SuspendLambda implements Function2 {
        int label;

        C01311(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LaptopPanelModel.this.new C01311(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object value;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = false;
            try {
            } catch (Exception e) {
                Logger.INSTANCE.e("LaptopPanelModel", "Failed to read panel style, using default. Error: " + e.getMessage(), new Object[0]);
                if (LaptopPanelModel.this.defaultPanelStyle == 1) {
                }
            }
            if (MotorolaSettings.Global.getInt(LaptopPanelModel.this.getApplication().getContentResolver(), "show_panel_view_modern_style", LaptopPanelModel.this.defaultPanelStyle) == 1) {
                z = true;
            }
            boolean z2 = z;
            MutableStateFlow mutableStateFlow = LaptopPanelModel.this._uiState;
            do {
                value = mutableStateFlow.getValue();
            } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, false, false, z2, 0, 0, null, null, 991, null)));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.motorola.laptoppanel.ui.panel.LaptopPanelModel$controllerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.motorola.laptoppanel.ui.panel.LaptopPanelModel$panelViewStyleObserver$1] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.motorola.laptoppanel.ui.panel.LaptopPanelModel$volumeReceiver$1] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.motorola.laptoppanel.ui.panel.LaptopPanelModel$screenStateReceiver$1] */
    public LaptopPanelModel(Application application, String str) {
        super(application);
        application.getClass();
        str.getClass();
        final Handler handler = new Handler(Looper.getMainLooper());
        this.mainHandler = handler;
        LaptopPrefs laptopPrefs = new LaptopPrefs(application);
        this.laptopPrefs = laptopPrefs;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(new LaptopPanelUiState(null, null, null, false, laptopPrefs.isGuideShown(), false, 0, 0, null, null, 1007, null));
        this._uiState = MutableStateFlow;
        this.uiState = FlowKt.asStateFlow(MutableStateFlow);
        this.defaultPanelStyle = Build.IS_PRC_PRODUCT ? 1 : 0;
        this.panelViewStyleObserver = new ContentObserver(handler) { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$panelViewStyleObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                this.this$0.updateModernStyle();
            }
        };
        this.mediaSessionManager$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelModel.mediaSessionManager_delegate$lambda$0(this.f$0);
            }
        });
        this.audioManager$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelModel.audioManager_delegate$lambda$1(this.f$0);
            }
        });
        MutableStateFlow MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._currentMediaController = MutableStateFlow2;
        this.currentMediaController = FlowKt.asStateFlow(MutableStateFlow2);
        BrightnessHelper companion = BrightnessHelper.Companion.getInstance(getApplication());
        this.brightnessHelper = companion;
        this.maxBrightness = 65535.0f;
        this.volumeReceiver = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$volumeReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.media.VOLUME_CHANGED_ACTION")) {
                    this.this$0.updateVolumeIcon();
                }
            }
        };
        this.activeSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$activeSessionsChangedListener$1
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                Logger.INSTANCE.i("LaptopPanelModel", "Active media sessions changed. Count: " + (list != null ? list.size() : 0), new Object[0]);
                this.this$0.checkMedia(list);
            }
        };
        this.controllerCallback = new MediaController.Callback() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$controllerCallback$1
            @Override // android.media.session.MediaController.Callback
            public void onMetadataChanged(MediaMetadata mediaMetadata) {
                this.this$0.checkMediaControllability();
            }

            @Override // android.media.session.MediaController.Callback
            public void onPlaybackStateChanged(PlaybackState playbackState) {
                this.this$0.checkMediaControllability();
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionDestroyed() {
                Logger.INSTANCE.d("LaptopPanelModel", "onSessionDestroyed", new Object[0]);
                this.this$0.setIsMediaPlaying(false);
                MediaController mediaController = (MediaController) this.this$0._currentMediaController.getValue();
                if (mediaController != null) {
                    mediaController.unregisterCallback(this);
                }
                this.this$0._currentMediaController.setValue(null);
            }
        };
        Function0 function0 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelModel.brightnessListener$lambda$2(this.f$0);
            }
        };
        this.brightnessListener = function0;
        MutableStateFlow MutableStateFlow3 = StateFlowKt.MutableStateFlow(0);
        this._autoDimResetKey = MutableStateFlow3;
        this.autoDimResetKey = FlowKt.asStateFlow(MutableStateFlow3);
        this.screenStateReceiver = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelModel$screenStateReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.SCREEN_ON")) {
                    Logger.INSTANCE.d("LaptopPanelModel", "Screen ON detected in ViewModel, triggering reset", new Object[0]);
                    MutableStateFlow mutableStateFlow = this.this$0._autoDimResetKey;
                    mutableStateFlow.setValue(Integer.valueOf(((Number) mutableStateFlow.getValue()).intValue() + 1));
                }
            }
        };
        registerStyleObserver();
        updateModernStyle();
        setPackageName(str);
        registerSessionListener();
        companion.registerBrightnessObserver(function0);
        registerVolumeReceiver();
        registerScreenOnReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean areControllersSame(MediaController mediaController, MediaController mediaController2) {
        return (mediaController == null || mediaController2 == null) ? Intrinsics.areEqual(mediaController, mediaController2) : Intrinsics.areEqual(mediaController.getSessionToken(), mediaController2.getSessionToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AudioManager audioManager_delegate$lambda$1(LaptopPanelModel laptopPanelModel) {
        Object systemService = laptopPanelModel.getApplication().getSystemService("audio");
        systemService.getClass();
        return (AudioManager) systemService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit brightnessListener$lambda$2(LaptopPanelModel laptopPanelModel) {
        laptopPanelModel.updateBrightnessIcon();
        return Unit.INSTANCE;
    }

    private final void cancelDismissalTimer() {
        Job job = this.dismissJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.dismissJob = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkMedia(List list) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new AnonymousClass1(list, this, null), 2, null);
    }

    static /* synthetic */ void checkMedia$default(LaptopPanelModel laptopPanelModel, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = null;
        }
        laptopPanelModel.checkMedia(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkMediaControllability() {
        MediaController mediaController = (MediaController) this.currentMediaController.getValue();
        if (mediaController == null) {
            Logger.INSTANCE.d("LaptopPanelModel", "No active media session!", new Object[0]);
            setIsMediaPlaying(false);
            return;
        }
        if (isSessionControllable(mediaController)) {
            Logger.INSTANCE.v("LaptopPanelModel", "Media is playing and controllable for " + ((LaptopPanelUiState) this._uiState.getValue()).getPackageName(), new Object[0]);
            setIsMediaPlaying(true);
            return;
        }
        Logger logger = Logger.INSTANCE;
        PlaybackState playbackState = mediaController.getPlaybackState();
        Integer numValueOf = playbackState != null ? Integer.valueOf(playbackState.getState()) : null;
        logger.w("LaptopPanelModel", "Media session is not controllable (State: " + numValueOf + "  Metadata: " + mediaController.getMetadata() + ")", new Object[0]);
        setIsMediaPlaying(false);
    }

    private final AudioManager getAudioManager() {
        return (AudioManager) this.audioManager$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MediaSessionManager getMediaSessionManager() {
        return (MediaSessionManager) this.mediaSessionManager$delegate.getValue();
    }

    private final boolean hasMediaPermission() {
        return ContextCompat.checkSelfPermission(getApplication(), "android.permission.MEDIA_CONTENT_CONTROL") == 0;
    }

    private final boolean isSessionControllable(MediaController mediaController) {
        Object value;
        PlaybackState playbackState = mediaController.getPlaybackState();
        MediaMetadata metadata = mediaController.getMetadata();
        MutableStateFlow mutableStateFlow = this._uiState;
        do {
            value = mutableStateFlow.getValue();
        } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, false, false, false, 0, 0, metadata != null ? new MediaMetadata.Builder(metadata).build() : null, playbackState != null ? new PlaybackState.Builder(playbackState).build() : null, 255, null)));
        if (playbackState == null) {
            Logger.INSTANCE.d("LaptopPanelModel", "Session: state is null!", new Object[0]);
            return false;
        }
        Logger logger = Logger.INSTANCE;
        logger.v("LaptopPanelModel", "Session: state = " + playbackState, new Object[0]);
        if (playbackState.getState() == 0 || playbackState.getState() == 1) {
            logger.d("LaptopPanelModel", "Session stopped", new Object[0]);
            return false;
        }
        boolean zIsMediaPlaying = ((LaptopPanelUiState) this._uiState.getValue()).isMediaPlaying();
        if (playbackState.getState() == 7) {
            logger.d("LaptopPanelModel", "Session: state is Error! Ignore this check to make controller still controllable for later try!", new Object[0]);
            return zIsMediaPlaying;
        }
        MediaController.TransportControls transportControls = mediaController.getTransportControls();
        if (transportControls == null) {
            return false;
        }
        logger.v("LaptopPanelModel", "Session: transportControls = " + transportControls, new Object[0]);
        logger.v("LaptopPanelModel", "metadata = " + metadata + ",", new Object[0]);
        if (metadata == null) {
            logger.d("LaptopPanelModel", "Session: metadata is null!", new Object[0]);
            return false;
        }
        String string = metadata.getString("android.media.metadata.TITLE");
        if (string == null || string.length() == 0) {
            logger.d("LaptopPanelModel", "Session: title is null!", new Object[0]);
        } else {
            logger.v("LaptopPanelModel", "Session: title = " + string, new Object[0]);
        }
        long actions = playbackState.getActions();
        logger.v("LaptopPanelModel", "Session: actions = " + actions, new Object[0]);
        return (actions & 566) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MediaSessionManager mediaSessionManager_delegate$lambda$0(LaptopPanelModel laptopPanelModel) {
        Object systemService = laptopPanelModel.getApplication().getSystemService("media_session");
        systemService.getClass();
        return (MediaSessionManager) systemService;
    }

    private final void registerScreenOnReceiver() {
        getApplication().registerReceiver(this.screenStateReceiver, new IntentFilter("android.intent.action.SCREEN_ON"), 4);
    }

    private final void registerSessionListener() {
        if (hasMediaPermission()) {
            getMediaSessionManager().addOnActiveSessionsChangedListener(this.activeSessionsChangedListener, new ComponentName(getApplication(), (Class<?>) LaptopPanelModel.class));
            Logger.INSTANCE.d("LaptopPanelModel", "Active session listener registered.", new Object[0]);
        } else {
            Logger.INSTANCE.w("LaptopPanelModel", "MEDIA_CONTENT_CONTROL permission not granted. Cannot listen for sessions.", new Object[0]);
        }
        checkMedia$default(this, null, 1, null);
    }

    private final void registerStyleObserver() {
        try {
            getApplication().getContentResolver().registerContentObserver(MotorolaSettings.Global.getUriFor("show_panel_view_modern_style"), false, this.panelViewStyleObserver);
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to register style observer. Error: " + e.getMessage(), new Object[0]);
        }
    }

    private final void registerVolumeReceiver() {
        try {
            getApplication().registerReceiver(this.volumeReceiver, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            updateVolumeIcon();
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to register volume receiver", e);
        }
    }

    private final void startDismissalTimer() {
        cancelDismissalTimer();
        this.dismissJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01301(null), 3, null);
    }

    private final void updateBrightnessIcon() {
        Object value;
        try {
            float currentBrightness = this.brightnessHelper.getCurrentBrightness();
            float f = this.minBrightness;
            float f2 = ((currentBrightness - f) * 100.0f) / (this.maxBrightness - f);
            int i = f2 <= 20.0f ? R.drawable.zz_moto_ic_brightness_in_bar_low : f2 <= 80.0f ? R.drawable.zz_moto_ic_brightness_in_bar_medium : R.drawable.zz_moto_ic_brightness_in_bar_high;
            if (((LaptopPanelUiState) this._uiState.getValue()).getBrightnessIcon() != i) {
                MutableStateFlow mutableStateFlow = this._uiState;
                do {
                    value = mutableStateFlow.getValue();
                } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, false, false, false, i, 0, null, null, 959, null)));
                Logger.INSTANCE.d("LaptopPanelModel", "Brightness icon changed, updating UI state.", new Object[0]);
            }
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to get system brightness", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateModernStyle() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C01311(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateVolumeIcon() {
        Object value;
        try {
            int i = getAudioManager().getStreamVolume(3) == 0 ? R.drawable.zz_moto_ic_volume_in_bar_mute : R.drawable.zz_moto_ic_volume;
            if (((LaptopPanelUiState) this._uiState.getValue()).getVolumeIcon() != i) {
                MutableStateFlow mutableStateFlow = this._uiState;
                do {
                    value = mutableStateFlow.getValue();
                } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, false, false, false, 0, i, null, null, 895, null)));
            }
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to get system volume", e);
        }
    }

    public final StateFlow getAutoDimResetKey() {
        return this.autoDimResetKey;
    }

    public final StateFlow getCurrentMediaController() {
        return this.currentMediaController;
    }

    public final StateFlow getUiState() {
        return this.uiState;
    }

    public final void hideSliderPanel(PanelType panelType) {
        panelType.getClass();
        if (((LaptopPanelUiState) this._uiState.getValue()).getActivePanel() != panelType) {
            return;
        }
        cancelDismissalTimer();
        PanelType previousPanel = ((LaptopPanelUiState) this._uiState.getValue()).getPreviousPanel();
        PanelType panelType2 = PanelType.MEDIA_CONTROLLER;
        if (previousPanel != panelType2 || !((LaptopPanelUiState) this._uiState.getValue()).isMediaPlaying()) {
            panelType2 = PanelType.TOUCHPAD;
        }
        showPanel(panelType2);
    }

    public final void logToolbarTap() {
        LaptopPrefs laptopPrefs = this.laptopPrefs;
        laptopPrefs.setToolbarTapCount(laptopPrefs.getToolbarTapCount() + 1);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        Logger.INSTANCE.d("LaptopPanelModel", "onCleared: Cleaning up LaptopPanelModel.", new Object[0]);
        cancelDismissalTimer();
        Job job = this.mediaStateJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        ContentResolver contentResolver = getApplication().getContentResolver();
        Application application = getApplication();
        try {
            contentResolver.unregisterContentObserver(this.panelViewStyleObserver);
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to unregister style observer", e);
        }
        try {
            MediaController mediaController = (MediaController) this.currentMediaController.getValue();
            if (mediaController != null) {
                mediaController.unregisterCallback(this.controllerCallback);
            }
            this._currentMediaController.setValue(null);
        } catch (Exception e2) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to unregister controller callback", e2);
        }
        try {
            getMediaSessionManager().removeOnActiveSessionsChangedListener(this.activeSessionsChangedListener);
        } catch (Exception e3) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to remove session listener", e3);
        }
        try {
            this.brightnessHelper.unregisterBrightnessObserver(this.brightnessListener);
        } catch (Exception e4) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to unregister brightness observer", e4);
        }
        try {
            application.unregisterReceiver(this.volumeReceiver);
        } catch (Exception e5) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to unregister volume receiver", e5);
        }
        try {
            application.unregisterReceiver(this.screenStateReceiver);
        } catch (IllegalArgumentException e6) {
            Logger.INSTANCE.e("LaptopPanelModel", "Failed to unregister screen receiver", e6);
        }
        super.onCleared();
    }

    public final void onUserInteraction() {
        if (((LaptopPanelUiState) this._uiState.getValue()).getActivePanel() == PanelType.BRIGHTNESS_SLIDER || ((LaptopPanelUiState) this._uiState.getValue()).getActivePanel() == PanelType.VOLUME_SLIDER) {
            startDismissalTimer();
        }
    }

    public final void setIsMediaPlaying(boolean z) {
        if (((LaptopPanelUiState) this._uiState.getValue()).isMediaPlaying() == z) {
            Job job = this.mediaStateJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
                return;
            }
            return;
        }
        Job job2 = this.mediaStateJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, null, 1, null);
        }
        this.mediaStateJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01291(z, this, null), 3, null);
    }

    public final void setPackageName(String str) {
        String str2 = str;
        str2.getClass();
        Logger.INSTANCE.i(this, "Package name updated to: " + str2, new Object[0]);
        if (str2.length() > 0) {
            if (!Intrinsics.areEqual(((LaptopPanelUiState) this._uiState.getValue()).getPackageName(), str2)) {
                MutableStateFlow mutableStateFlow = this._uiState;
                while (true) {
                    Object value = mutableStateFlow.getValue();
                    if (mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, str2, false, false, false, 0, 0, null, null, 1019, null))) {
                        break;
                    } else {
                        str2 = str;
                    }
                }
            }
            checkMedia$default(this, null, 1, null);
        }
    }

    public final void setShowGuideUi(boolean z, boolean z2) {
        if (z2) {
            this.laptopPrefs.setGuideShown(z);
        }
        MutableStateFlow mutableStateFlow = this._uiState;
        while (true) {
            Object value = mutableStateFlow.getValue();
            boolean z3 = z;
            if (mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default((LaptopPanelUiState) value, null, null, null, false, z3, false, 0, 0, null, null, 1007, null))) {
                return;
            } else {
                z = z3;
            }
        }
    }

    public final void showPanel(PanelType panelType) {
        Object value;
        LaptopPanelUiState laptopPanelUiState;
        panelType.getClass();
        Logger.INSTANCE.d("LaptopPanelModel", "ShowPanel: " + panelType + " (previous " + this._uiState.getValue() + ")", new Object[0]);
        if (((LaptopPanelUiState) this._uiState.getValue()).getActivePanel() == panelType) {
            onUserInteraction();
            return;
        }
        MutableStateFlow mutableStateFlow = this._uiState;
        do {
            value = mutableStateFlow.getValue();
            laptopPanelUiState = (LaptopPanelUiState) value;
        } while (!mutableStateFlow.compareAndSet(value, LaptopPanelUiState.copy$default(laptopPanelUiState, panelType, laptopPanelUiState.getActivePanel(), null, false, false, false, 0, 0, null, null, 1020, null)));
        if (panelType == PanelType.BRIGHTNESS_SLIDER || panelType == PanelType.VOLUME_SLIDER) {
            startDismissalTimer();
        } else {
            cancelDismissalTimer();
        }
    }
}
