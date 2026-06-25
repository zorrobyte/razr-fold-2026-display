package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.app.PendingIntent;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.service.notification.StatusBarNotification;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Dumpable;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataCombineLatest;
import com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaFlags;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: MediaCarouselInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselInteractor implements MediaDataManager, Dumpable {
    public static final Companion Companion = new Companion(null);
    private final StateFlow hasActiveMedia;
    private final StateFlow hasActiveMediaOrRecommendation;
    private final StateFlow hasAnyMedia;
    private final StateFlow hasAnyMediaOrRecommendation;
    private final StateFlow isMediaFromRec;
    private final MediaDataCombineLatest mediaDataCombineLatest;
    private final MediaDataFilterImpl mediaDataFilter;
    private final MediaDataProcessor mediaDataProcessor;
    private final MediaDataRepository mediaDataRepository;
    private final MediaDeviceManager mediaDeviceManager;
    private final MediaFlags mediaFlags;
    private final MediaResumeListener mediaResumeListener;
    private final MediaSessionBasedFilter mediaSessionBasedFilter;
    private final MediaTimeoutListener mediaTimeoutListener;
    private final StateFlow sortedMedia;

    /* JADX INFO: compiled from: MediaCarouselInteractor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Void getUnsupported() {
            throw new IllegalStateException("Code path not supported when {MediaControlsRefactorFlag.FLAG_NAME} is enabled");
        }
    }

    public MediaCarouselInteractor(CoroutineScope coroutineScope, MediaDataRepository mediaDataRepository, MediaDataProcessor mediaDataProcessor, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilterImpl mediaDataFilterImpl, MediaFilterRepository mediaFilterRepository, MediaFlags mediaFlags) {
        coroutineScope.getClass();
        mediaDataRepository.getClass();
        mediaDataProcessor.getClass();
        mediaTimeoutListener.getClass();
        mediaResumeListener.getClass();
        mediaSessionBasedFilter.getClass();
        mediaDeviceManager.getClass();
        mediaDataCombineLatest.getClass();
        mediaDataFilterImpl.getClass();
        mediaFilterRepository.getClass();
        mediaFlags.getClass();
        this.mediaDataRepository = mediaDataRepository;
        this.mediaDataProcessor = mediaDataProcessor;
        this.mediaTimeoutListener = mediaTimeoutListener;
        this.mediaResumeListener = mediaResumeListener;
        this.mediaSessionBasedFilter = mediaSessionBasedFilter;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataCombineLatest = mediaDataCombineLatest;
        this.mediaDataFilter = mediaDataFilterImpl;
        this.mediaFlags = mediaFlags;
        final StateFlow selectedUserEntries = mediaFilterRepository.getSelectedUserEntries();
        Flow flow = new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1

            /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6e
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.util.Map r5 = (java.util.Map) r5
                        boolean r6 = r5.isEmpty()
                        r2 = 0
                        if (r6 == 0) goto L40
                        goto L61
                    L40:
                        java.util.Set r5 = r5.entrySet()
                        java.util.Iterator r5 = r5.iterator()
                    L48:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L61
                        java.lang.Object r6 = r5.next()
                        java.util.Map$Entry r6 = (java.util.Map.Entry) r6
                        java.lang.Object r6 = r6.getValue()
                        com.android.systemui.media.controls.shared.model.MediaData r6 = (com.android.systemui.media.controls.shared.model.MediaData) r6
                        boolean r6 = r6.getActive()
                        if (r6 == 0) goto L48
                        r2 = r3
                    L61:
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r2)
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = selectedUserEntries.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        SharingStarted sharingStartedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null);
        Boolean bool = Boolean.FALSE;
        this.hasActiveMediaOrRecommendation = FlowKt.stateIn(flow, coroutineScope, sharingStartedWhileSubscribed$default, bool);
        final StateFlow selectedUserEntries2 = mediaFilterRepository.getSelectedUserEntries();
        this.hasAnyMediaOrRecommendation = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2

            /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.util.Map r5 = (java.util.Map) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = selectedUserEntries2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null), bool);
        this.hasActiveMedia = FlowKt.stateIn(FlowKt.mapLatest(mediaFilterRepository.getSelectedUserEntries(), new MediaCarouselInteractor$hasActiveMedia$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null), bool);
        this.hasAnyMedia = FlowKt.stateIn(FlowKt.mapLatest(mediaFilterRepository.getSelectedUserEntries(), new MediaCarouselInteractor$hasAnyMedia$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null), bool);
        this.sortedMedia = FlowKt.stateIn(FlowKt.mapLatest(mediaFilterRepository.getSortedMedia(), new MediaCarouselInteractor$sortedMedia$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 0L, 0L, 3, null), CollectionsKt.emptyList());
        this.isMediaFromRec = mediaFilterRepository.isMediaFromRec();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void addListener(MediaDataManager.Listener listener) {
        listener.getClass();
        this.mediaDataFilter.addListener(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void addResumptionControls(int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2) {
        mediaDescription.getClass();
        runnable.getClass();
        token.getClass();
        str.getClass();
        pendingIntent.getClass();
        str2.getClass();
        this.mediaDataProcessor.addResumptionControls(i, mediaDescription, runnable, token, str, pendingIntent, str2);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean dismissMediaData(String str, long j) {
        str.getClass();
        return this.mediaDataProcessor.dismissMediaData(str, j);
    }

    public final StateFlow getSortedMedia() {
        return this.sortedMedia;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean hasActiveMediaOrRecommendation() {
        return ((Boolean) this.hasActiveMediaOrRecommendation.getValue()).booleanValue();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public boolean hasAnyMediaOrRecommendation() {
        return ((Boolean) this.hasAnyMediaOrRecommendation.getValue()).booleanValue();
    }

    public final StateFlow isMediaFromRec() {
        return this.isMediaFromRec;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        str.getClass();
        statusBarNotification.getClass();
        this.mediaDataProcessor.onNotificationAdded(str, statusBarNotification);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onNotificationRemoved(String str) {
        str.getClass();
        this.mediaDataProcessor.onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void onSwipeToDismiss() {
        this.mediaDataFilter.onSwipeToDismiss();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void removeListener(MediaDataManager.Listener listener) {
        listener.getClass();
        this.mediaDataFilter.removeListener(listener);
    }

    public final void removeMediaControl(InstanceId instanceId, long j) {
        instanceId.getClass();
        this.mediaDataProcessor.dismissMediaData(instanceId, j);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    /* JADX INFO: renamed from: setInactive, reason: merged with bridge method [inline-methods] */
    public Void mo1360setInactive(String str, boolean z, boolean z2) {
        str.getClass();
        Companion.getUnsupported();
        throw new KotlinNothingValueException();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public void setResumeAction(String str, Runnable runnable) {
        str.getClass();
        this.mediaDataProcessor.setResumeAction(str, runnable);
    }
}
