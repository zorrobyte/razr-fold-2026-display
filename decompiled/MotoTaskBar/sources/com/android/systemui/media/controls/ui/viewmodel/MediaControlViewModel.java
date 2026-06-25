package com.android.systemui.media.controls.ui.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaControlModel;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.kotlin.FlowKt;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$string;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: MediaControlViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControlViewModel {
    public static final Companion Companion = new Companion(null);
    private static final List SEMANTIC_ACTIONS_ALL;
    private static final List SEMANTIC_ACTIONS_COMPACT;
    private static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    private final Context applicationContext;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Executor backgroundExecutor;
    private final MediaControlInteractor interactor;
    private final MutableStateFlow isAnyButtonClicked;
    private final MediaUiEventLogger logger;
    private final Flow playTurbulenceNoise;
    private final Flow player;

    /* JADX INFO: compiled from: MediaControlViewModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getSEMANTIC_ACTIONS_ALL() {
            return MediaControlViewModel.SEMANTIC_ACTIONS_ALL;
        }

        public final List getSEMANTIC_ACTIONS_COMPACT() {
            return MediaControlViewModel.SEMANTIC_ACTIONS_COMPACT;
        }

        public final List getSEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING() {
            return MediaControlViewModel.SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$toViewModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControlViewModel.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MediaControlViewModel.this.toViewModel(null, false, this);
        }
    }

    static {
        int i = R$id.actionPlayPause;
        Integer numValueOf = Integer.valueOf(i);
        int i2 = R$id.actionPrev;
        Integer numValueOf2 = Integer.valueOf(i2);
        int i3 = R$id.actionNext;
        SEMANTIC_ACTIONS_COMPACT = CollectionsKt.listOf((Object[]) new Integer[]{numValueOf, numValueOf2, Integer.valueOf(i3)});
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i2), Integer.valueOf(i3)});
        SEMANTIC_ACTIONS_ALL = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(R$id.action0), Integer.valueOf(R$id.action1)});
    }

    public MediaControlViewModel(Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, MediaControlInteractor mediaControlInteractor, MediaUiEventLogger mediaUiEventLogger) {
        context.getClass();
        coroutineDispatcher.getClass();
        executor.getClass();
        mediaControlInteractor.getClass();
        mediaUiEventLogger.getClass();
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.interactor = mediaControlInteractor;
        this.logger = mediaUiEventLogger;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isAnyButtonClicked = MutableStateFlow;
        this.playTurbulenceNoise = FlowKt.sample(mediaControlInteractor.getMediaControl(), kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(kotlinx.coroutines.flow.FlowKt.combine(MutableStateFlow, mediaControlInteractor.isStartedPlaying(), new MediaControlViewModel$playTurbulenceNoise$1(null))));
        this.player = kotlinx.coroutines.flow.FlowKt.flowOn(kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(kotlinx.coroutines.flow.FlowKt.transformLatest(mediaControlInteractor.getOnAnyMediaConfigurationChange(), new MediaControlViewModel$special$$inlined$flatMapLatest$1(null, this))), coroutineDispatcher);
    }

    private final boolean canShowScrubbingTimeViews(final MediaButton mediaButton) {
        if (mediaButton == null) {
            return false;
        }
        Stream stream = SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.stream();
        final Function1 function1 = new Function1() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(MediaControlViewModel.canShowScrubbingTimeViews$lambda$23$lambda$22(mediaButton, ((Integer) obj).intValue()));
            }
        };
        return stream.allMatch(new Predicate(function1) { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function1.getClass();
                this.function = function1;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean canShowScrubbingTimeViews$lambda$23$lambda$22(MediaButton mediaButton, int i) {
        return mediaButton.getActionById(i) != null;
    }

    private final Icon getIconFromApp(String str) {
        try {
            Drawable applicationIcon = this.applicationContext.getPackageManager().getApplicationIcon(str);
            applicationIcon.getClass();
            return new Icon.Loaded(applicationIcon, null);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("MediaControlViewModel", "Cannot find icon for package " + str, e);
            return new Icon.Resource(R$drawable.ic_music_note, null);
        }
    }

    private final void onButtonClicked(int i, int i2, String str, InstanceId instanceId, Runnable runnable) {
        this.logger.logTapAction(i, i2, str, instanceId);
        this.isAnyButtonClicked.setValue(Boolean.TRUE);
        runnable.run();
    }

    private final void onDismissMediaData(MediaSession.Token token, int i, String str, InstanceId instanceId) {
        this.logger.logLongPressDismiss(i, str, instanceId);
        this.interactor.removeMediaControl(token, instanceId, 334L);
    }

    private final List toActionViewModels(MediaControlModel mediaControlModel) {
        MediaButton semanticActionButtons = mediaControlModel.getSemanticActionButtons();
        ArrayList arrayList = null;
        if (semanticActionButtons != null) {
            boolean zCanShowScrubbingTimeViews = canShowScrubbingTimeViews(semanticActionButtons);
            List list = SEMANTIC_ACTIONS_ALL;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                MediaAction actionById = semanticActionButtons.getActionById(iIntValue);
                arrayList2.add(actionById != null ? toSemanticActionViewModel(mediaControlModel, actionById, iIntValue, zCanShowScrubbingTimeViews) : null);
            }
            arrayList = arrayList2;
        }
        List notificationActionButtons = mediaControlModel.getNotificationActionButtons();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(notificationActionButtons, 10));
        int i = 0;
        for (Object obj : notificationActionButtons) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList3.add(toNotifActionViewModel(mediaControlModel, (MediaAction) obj, i));
            i = i2;
        }
        return arrayList == null ? arrayList3 : arrayList;
    }

    private final GutsViewModel toGutsViewModel(final MediaControlModel mediaControlModel, ColorScheme colorScheme) {
        String string = mediaControlModel.isDismissible() ? this.applicationContext.getString(R$string.controls_media_close_session, mediaControlModel.getAppName()) : this.applicationContext.getString(com.android.systemui.res.R$string.controls_media_active_session);
        string.getClass();
        return new GutsViewModel(string, MediaColorSchemesKt.textPrimaryFromScheme(colorScheme), MediaColorSchemesKt.accentPrimaryFromScheme(colorScheme), MediaColorSchemesKt.surfaceFromScheme(colorScheme), mediaControlModel.isDismissible(), new Function0() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaControlViewModel.toGutsViewModel$lambda$11(this.f$0, mediaControlModel);
            }
        }, mediaControlModel.isDismissible() ? this.applicationContext.getDrawable(com.android.systemui.res.R$drawable.qs_media_outline_button) : this.applicationContext.getDrawable(com.android.systemui.res.R$drawable.qs_media_solid_button), new Function0() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaControlViewModel.toGutsViewModel$lambda$12(this.f$0, mediaControlModel);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toGutsViewModel$lambda$11(MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel) {
        mediaControlViewModel.onDismissMediaData(mediaControlModel.getToken(), mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toGutsViewModel$lambda$12(MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel) {
        mediaControlViewModel.logger.logLongPressSettings(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
        mediaControlViewModel.interactor.startSettings();
        return Unit.INSTANCE;
    }

    private final MediaActionViewModel toNotifActionViewModel(final MediaControlModel mediaControlModel, final MediaAction mediaAction, int i) {
        return new MediaActionViewModel(mediaAction.getIcon(), mediaAction.getContentDescription(), mediaAction.getBackground(), false, 0, mediaControlModel.getActionsToShowInCollapsed().contains(Integer.valueOf(i)), mediaAction.getRebindId(), null, mediaAction.getAction() != null, new Function1() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaControlViewModel.toNotifActionViewModel$lambda$21(mediaAction, this, mediaControlModel, ((Integer) obj).intValue());
            }
        }, 152, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toNotifActionViewModel$lambda$21(MediaAction mediaAction, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel, int i) {
        Runnable action = mediaAction.getAction();
        if (action != null) {
            mediaControlViewModel.onButtonClicked(i, mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId(), action);
        }
        return Unit.INSTANCE;
    }

    private final MediaOutputSwitcherViewModel toOutputSwitcherViewModel(final MediaControlModel mediaControlModel) {
        CharSequence string;
        Drawable icon;
        final MediaDeviceData deviceData = mediaControlModel.getDeviceData();
        boolean z = !(deviceData == null || deviceData.getEnabled()) || mediaControlModel.isResume();
        if (deviceData == null || (string = deviceData.getName()) == null) {
            string = this.applicationContext.getString(com.android.systemui.res.R$string.media_seamless_other_device);
            string.getClass();
        }
        CharSequence charSequence = string;
        boolean z2 = !z;
        Icon resource = (deviceData == null || (icon = deviceData.getIcon()) == null) ? new Icon.Resource(com.android.systemui.res.R$drawable.ic_media_home_devices, null) : new Icon.Loaded(icon, null);
        boolean z3 = (deviceData != null ? deviceData.getIntent() : null) != null;
        float f = z ? 0.38f : 1.0f;
        final boolean z4 = false;
        final boolean z5 = false;
        return new MediaOutputSwitcherViewModel(z2, charSequence, resource, false, z3, f, false, new Function0() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaControlViewModel.toOutputSwitcherViewModel$lambda$10(z4, z5, this, mediaControlModel, deviceData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toOutputSwitcherViewModel$lambda$10(boolean z, boolean z2, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel, MediaDeviceData mediaDeviceData) {
        PendingIntent intent;
        if (!z) {
            mediaControlViewModel.logger.logOpenOutputSwitcher(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
            if (mediaDeviceData == null || (intent = mediaDeviceData.getIntent()) == null) {
                mediaControlViewModel.interactor.startMediaOutputDialog(mediaControlModel.getPackageName());
            } else {
                mediaControlViewModel.interactor.startDeviceIntent(intent);
            }
        } else if (z2) {
            mediaControlViewModel.logger.logOpenOutputSwitcher(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
            mediaControlViewModel.interactor.startMediaOutputDialog(mediaControlModel.getPackageName());
        } else {
            mediaControlViewModel.logger.logOpenBroadcastDialog(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
            mediaControlViewModel.interactor.startBroadcastDialog(String.valueOf(mediaDeviceData != null ? mediaDeviceData.getName() : null), mediaControlModel.getPackageName());
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel toSemanticActionViewModel(final com.android.systemui.media.controls.shared.model.MediaControlModel r15, final com.android.systemui.media.controls.shared.model.MediaAction r16, int r17, boolean r18) {
        /*
            r14 = this;
            r0 = r17
            java.util.List r1 = com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.SEMANTIC_ACTIONS_COMPACT
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            boolean r9 = r1.contains(r2)
            java.util.List r1 = com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            boolean r1 = r1.contains(r2)
            r2 = 0
            r3 = 1
            if (r18 == 0) goto L1f
            if (r1 == 0) goto L1f
            r1 = r3
            r4 = r1
            goto L21
        L1f:
            r1 = r2
            r4 = r3
        L21:
            com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel r3 = new com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel
            r5 = r4
            android.graphics.drawable.Drawable r4 = r16.getIcon()
            r6 = r5
            java.lang.CharSequence r5 = r16.getContentDescription()
            r7 = r6
            android.graphics.drawable.Drawable r6 = r16.getBackground()
            r1 = r1 ^ r7
            int r8 = com.motorola.taskbar.R$id.actionPrev
            if (r0 != r8) goto L44
            com.android.systemui.media.controls.shared.model.MediaButton r8 = r15.getSemanticActionButtons()
            r8.getClass()
            boolean r8 = r8.getReservePrev()
            if (r8 != 0) goto L55
        L44:
            int r8 = com.motorola.taskbar.R$id.actionNext
            if (r0 != r8) goto L57
            com.android.systemui.media.controls.shared.model.MediaButton r8 = r15.getSemanticActionButtons()
            r8.getClass()
            boolean r8 = r8.getReserveNext()
            if (r8 == 0) goto L57
        L55:
            r8 = 4
            goto L59
        L57:
            r8 = 8
        L59:
            java.lang.Integer r10 = r16.getRebindId()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r0)
            java.lang.Runnable r0 = r16.getAction()
            if (r0 == 0) goto L69
            r12 = r7
            goto L6a
        L69:
            r12 = r2
        L6a:
            com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda5 r13 = new com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda5
            r0 = r16
            r13.<init>()
            r7 = r1
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.toSemanticActionViewModel(com.android.systemui.media.controls.shared.model.MediaControlModel, com.android.systemui.media.controls.shared.model.MediaAction, int, boolean):com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toSemanticActionViewModel$lambda$19(MediaAction mediaAction, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel, int i) {
        Runnable action = mediaAction.getAction();
        if (action != null) {
            mediaControlViewModel.onButtonClicked(i, mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId(), action);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object toViewModel(com.android.systemui.media.controls.shared.model.MediaControlModel r32, boolean r33, kotlin.coroutines.Continuation r34) {
        /*
            Method dump skipped, instruction units count: 289
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.toViewModel(com.android.systemui.media.controls.shared.model.MediaControlModel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence toViewModel$lambda$2(GutsViewModel gutsViewModel, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel, boolean z) {
        if (z) {
            return gutsViewModel.getGutsText();
        }
        String string = mediaControlViewModel.applicationContext.getString(com.android.systemui.res.R$string.controls_media_playing_item_description, mediaControlModel.getSongName(), mediaControlModel.getArtistName(), mediaControlModel.getAppName());
        string.getClass();
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$4(MediaControlModel mediaControlModel, MediaControlViewModel mediaControlViewModel) {
        PendingIntent clickIntent = mediaControlModel.getClickIntent();
        if (clickIntent != null) {
            mediaControlViewModel.logger.logTapContentView(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
            mediaControlViewModel.interactor.startClickIntent(clickIntent);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$5(MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel) {
        mediaControlViewModel.logger.logLongPressOpen(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$6(MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel) {
        mediaControlViewModel.logger.logSeek(mediaControlModel.getUid(), mediaControlModel.getPackageName(), mediaControlModel.getInstanceId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$7(final MediaControlModel mediaControlModel, final MediaControlViewModel mediaControlViewModel, final SeekBarViewModel seekBarViewModel) {
        seekBarViewModel.getClass();
        if (!mediaControlModel.isResume() || mediaControlModel.getResumeProgress() == null) {
            mediaControlViewModel.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$toViewModel$6$1
                @Override // java.lang.Runnable
                public final void run() {
                    SeekBarViewModel seekBarViewModel2 = seekBarViewModel;
                    MediaSession.Token token = mediaControlModel.getToken();
                    seekBarViewModel2.updateController(token != null ? new MediaController(mediaControlViewModel.applicationContext, token) : null);
                }
            });
        } else {
            seekBarViewModel.updateStaticProgress(mediaControlModel.getResumeProgress().doubleValue());
        }
        return Unit.INSTANCE;
    }

    public final Flow getPlayer() {
        return this.player;
    }
}
