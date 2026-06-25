package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.model.MediaControlModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: MediaControlInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControlInteractor {
    public static final Companion Companion = new Companion(null);
    private static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    private final ActivityIntentHelper activityIntentHelper;
    private final ActivityStarter activityStarter;
    private final InstanceId instanceId;
    private final Flow isStartedPlaying;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final Flow mediaControl;
    private final MediaDataProcessor mediaDataProcessor;
    private final Flow onAnyMediaConfigurationChange;

    /* JADX INFO: compiled from: MediaControlInteractor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$isStartedPlaying$2, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControlInteractor.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
        }

        public final Object invoke(boolean z, boolean z2, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = z;
            anonymousClass2.Z$1 = z2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(!this.Z$0 && this.Z$1);
        }
    }

    public MediaControlInteractor(final Context context, InstanceId instanceId, MediaFilterRepository mediaFilterRepository, MediaDataProcessor mediaDataProcessor, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager) {
        context.getClass();
        instanceId.getClass();
        mediaFilterRepository.getClass();
        mediaDataProcessor.getClass();
        activityStarter.getClass();
        activityIntentHelper.getClass();
        notificationLockscreenUserManager.getClass();
        this.instanceId = instanceId;
        this.mediaDataProcessor = mediaDataProcessor;
        this.activityStarter = activityStarter;
        this.activityIntentHelper = activityIntentHelper;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        final StateFlow selectedUserEntries = mediaFilterRepository.getSelectedUserEntries();
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1

            /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ MediaControlInteractor this$0;

                /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MediaControlInteractor mediaControlInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControlInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        java.util.Map r5 = (java.util.Map) r5
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor r2 = r4.this$0
                        com.android.internal.logging.InstanceId r2 = com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor.access$getInstanceId$p(r2)
                        java.lang.Object r5 = r5.get(r2)
                        com.android.systemui.media.controls.shared.model.MediaData r5 = (com.android.systemui.media.controls.shared.model.MediaData) r5
                        if (r5 == 0) goto L4d
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor r4 = r4.this$0
                        com.android.systemui.media.controls.shared.model.MediaControlModel r4 = com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor.access$toMediaControlModel(r4, r5)
                        goto L4e
                    L4d:
                        r4 = 0
                    L4e:
                        r0.label = r3
                        java.lang.Object r4 = r6.emit(r4, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = selectedUserEntries.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        });
        this.mediaControl = flowDistinctUntilChanged;
        this.isStartedPlaying = FlowKt.distinctUntilChanged(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2

            /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ Context $applicationContext$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, Context context) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$applicationContext$inlined = context;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L71
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                        com.android.systemui.media.controls.shared.model.MediaControlModel r6 = (com.android.systemui.media.controls.shared.model.MediaControlModel) r6
                        r2 = 0
                        if (r6 == 0) goto L64
                        android.media.session.MediaSession$Token r6 = r6.getToken()
                        if (r6 == 0) goto L64
                        android.media.session.MediaController r4 = new android.media.session.MediaController
                        android.content.Context r5 = r5.$applicationContext$inlined
                        r4.<init>(r5, r6)
                        android.media.session.PlaybackState r5 = r4.getPlaybackState()
                        if (r5 == 0) goto L5d
                        int r5 = r5.getState()
                        r6 = 3
                        if (r5 != r6) goto L57
                        r5 = r3
                        goto L58
                    L57:
                        r5 = r2
                    L58:
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                        goto L5e
                    L5d:
                        r5 = 0
                    L5e:
                        if (r5 == 0) goto L64
                        boolean r2 = r5.booleanValue()
                    L64:
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r2)
                        r0.label = r3
                        java.lang.Object r5 = r7.emit(r5, r0)
                        if (r5 != r1) goto L71
                        return r1
                    L71:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, context), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, Boolean.FALSE, new AnonymousClass2(null)));
        this.onAnyMediaConfigurationChange = mediaFilterRepository.getOnAnyMediaConfigurationChange();
    }

    private final boolean launchOverLockscreen(PendingIntent pendingIntent) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MediaControlModel toMediaControlModel(MediaData mediaData) {
        return new MediaControlModel(mediaData.getAppUid(), mediaData.getPackageName(), mediaData.getInstanceId(), mediaData.getToken(), mediaData.getAppIcon(), mediaData.getClickIntent(), mediaData.getApp(), mediaData.getSong(), mediaData.getArtist(), mediaData.isExplicit(), mediaData.getArtwork(), mediaData.getDevice(), mediaData.getSemanticActions(), mediaData.getActions(), mediaData.getActionsToShowInCompact(), mediaData.isClearable(), mediaData.getResumption(), mediaData.getResumeProgress());
    }

    public final Flow getMediaControl() {
        return this.mediaControl;
    }

    public final Flow getOnAnyMediaConfigurationChange() {
        return this.onAnyMediaConfigurationChange;
    }

    public final Flow isStartedPlaying() {
        return this.isStartedPlaying;
    }

    public final boolean removeMediaControl(MediaSession.Token token, InstanceId instanceId, long j) {
        instanceId.getClass();
        boolean zDismissMediaData = this.mediaDataProcessor.dismissMediaData(instanceId, j);
        if (!zDismissMediaData) {
            Log.w("MediaControlInteractor", "Manager failed to dismiss media of instanceId=" + instanceId + ", Token uid=" + (token != null ? Integer.valueOf(token.getUid()) : null));
        }
        return zDismissMediaData;
    }

    public final void startBroadcastDialog(String str, String str2) {
        str.getClass();
        str2.getClass();
    }

    public final void startClickIntent(PendingIntent pendingIntent) {
        pendingIntent.getClass();
        if (launchOverLockscreen(pendingIntent)) {
            return;
        }
        this.activityStarter.postStartActivityDismissingKeyguard(pendingIntent);
    }

    public final void startDeviceIntent(PendingIntent pendingIntent) {
        pendingIntent.getClass();
        if (pendingIntent.isActivity()) {
            if (launchOverLockscreen(pendingIntent)) {
                return;
            }
            this.activityStarter.postStartActivityDismissingKeyguard(pendingIntent);
        } else {
            Log.w("MediaControlInteractor", "Device pending intent of instanceId=" + this.instanceId + " is not an activity.");
        }
    }

    public final void startMediaOutputDialog(String str) {
        str.getClass();
    }

    public final void startSettings() {
        this.activityStarter.startActivity(SETTINGS_INTENT, true);
    }
}
