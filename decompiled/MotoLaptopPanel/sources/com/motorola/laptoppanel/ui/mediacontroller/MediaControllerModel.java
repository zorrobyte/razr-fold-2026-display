package com.motorola.laptoppanel.ui.mediacontroller;

import android.app.Application;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel;
import com.motorola.laptoppanel.ui.mediacontroller.MediaUiState;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import com.motorola.laptoppanel.ui.panel.LaptopPanelUiState;
import com.motorola.laptoppanel.util.Logger;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: MediaControllerModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControllerModel extends AndroidViewModel {
    private final String HBO_PKG;
    private final long SEEK_TIMEOUT;
    private final MutableStateFlow _uiState;
    private SessionToken activeSessionToken;
    private boolean isSeeking;
    private MediaController mediaController;
    private final LaptopPanelModel panelModel;
    private PlayerListener playerListener;
    private Job progressUpdateJob;
    private long seekStartTime;
    private long targetSeekPosition;
    private final StateFlow uiState;
    private Job uiUpdateDebounceJob;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: MediaControllerModel.kt */
        final class AnonymousClass2 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;

            AnonymousClass2(Continuation continuation) {
                super(3, continuation);
            }

            public final Object invoke(android.media.session.MediaController mediaController, boolean z, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                anonymousClass2.L$0 = mediaController;
                anonymousClass2.Z$0 = z;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return invoke((android.media.session.MediaController) obj, ((Boolean) obj2).booleanValue(), (Continuation) obj3);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                android.media.session.MediaController mediaController = (android.media.session.MediaController) this.L$0;
                if (!this.Z$0 || mediaController == null) {
                    return null;
                }
                return mediaController;
            }
        }

        /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: MediaControllerModel.kt */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaControllerModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(MediaControllerModel mediaControllerModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaControllerModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, continuation);
                anonymousClass3.L$0 = obj;
                return anonymousClass3;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(android.media.session.MediaController mediaController, Continuation continuation) {
                return ((AnonymousClass3) create(mediaController, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    android.media.session.MediaController mediaController = (android.media.session.MediaController) this.L$0;
                    if (mediaController != null) {
                        Logger.INSTANCE.i("MediaControllerModel", "Valid controller found and Media is Playing. Connecting " + mediaController.getPackageName(), new Object[0]);
                        MediaControllerModel mediaControllerModel = this.this$0;
                        MediaSession.Token sessionToken = mediaController.getSessionToken();
                        sessionToken.getClass();
                        this.label = 1;
                        if (mediaControllerModel.connectToSession(sessionToken, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        Logger.INSTANCE.i("MediaControllerModel", "Media stopped or controller lost. Releasing...", new Object[0]);
                        this.this$0.releaseController();
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

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaControllerModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow currentMediaController = MediaControllerModel.this.panelModel.getCurrentMediaController();
                final StateFlow uiState = MediaControllerModel.this.panelModel.getUiState();
                Flow flowCombine = FlowKt.combine(currentMediaController, FlowKt.distinctUntilChanged(new Flow() { // from class: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1

                    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    /* JADX INFO: compiled from: Emitters.kt */
                    public final class AnonymousClass2 implements FlowCollector {
                        final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L49
                            L29:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L31:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                com.motorola.laptoppanel.ui.panel.LaptopPanelUiState r5 = (com.motorola.laptoppanel.ui.panel.LaptopPanelUiState) r5
                                boolean r5 = r5.isMediaPlaying()
                                java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                                r0.label = r3
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L49
                                return r1
                            L49:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = uiState.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                }), new AnonymousClass2(null));
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(MediaControllerModel.this, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowCombine, anonymousClass3, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invokeSuspend$lambda$1(Pair pair, Pair pair2) {
            MediaMetadata mediaMetadata = (MediaMetadata) pair.getFirst();
            MediaMetadata mediaMetadata2 = (MediaMetadata) pair2.getFirst();
            PlaybackState playbackState = (PlaybackState) pair.getSecond();
            PlaybackState playbackState2 = (PlaybackState) pair2.getSecond();
            return Intrinsics.areEqual(mediaMetadata != null ? mediaMetadata.getString("android.media.metadata.TITLE") : null, mediaMetadata2 != null ? mediaMetadata2.getString("android.media.metadata.TITLE") : null) && (((mediaMetadata != null ? mediaMetadata.getLong("android.media.metadata.DURATION") : 0L) > (mediaMetadata2 != null ? mediaMetadata2.getLong("android.media.metadata.DURATION") : 0L) ? 1 : ((mediaMetadata != null ? mediaMetadata.getLong("android.media.metadata.DURATION") : 0L) == (mediaMetadata2 != null ? mediaMetadata2.getLong("android.media.metadata.DURATION") : 0L) ? 0 : -1)) == 0) && Intrinsics.areEqual(playbackState != null ? Integer.valueOf(playbackState.getState()) : null, playbackState2 != null ? Integer.valueOf(playbackState2.getState()) : null);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaControllerModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final StateFlow uiState = MediaControllerModel.this.panelModel.getUiState();
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1

                    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    /* JADX INFO: compiled from: Emitters.kt */
                    public final class AnonymousClass2 implements FlowCollector {
                        final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L4d
                            L29:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L31:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                com.motorola.laptoppanel.ui.panel.LaptopPanelUiState r5 = (com.motorola.laptoppanel.ui.panel.LaptopPanelUiState) r5
                                android.media.MediaMetadata r6 = r5.getMediaMetadata()
                                android.media.session.PlaybackState r5 = r5.getPlaybackState()
                                kotlin.Pair r5 = kotlin.TuplesKt.to(r6, r5)
                                r0.label = r3
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L4d
                                return r1
                            L4d:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = uiState.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                }, new Function2() { // from class: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$2$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        return Boolean.valueOf(MediaControllerModel.AnonymousClass2.invokeSuspend$lambda$1((Pair) obj2, (Pair) obj3));
                    }
                });
                final MediaControllerModel mediaControllerModel = MediaControllerModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel.2.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Pair pair, Continuation continuation) {
                        Logger.INSTANCE.d("MediaControllerModel", "Legacy media meta data or playbackState changed!", new Object[0]);
                        MediaControllerModel.updateUiState$default(mediaControllerModel, null, false, 3, null);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX INFO: compiled from: MediaControllerModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ViewModelProvider.Factory provideFactory(final LaptopPanelModel laptopPanelModel, final Application application) {
            laptopPanelModel.getClass();
            application.getClass();
            return new ViewModelProvider.Factory() { // from class: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$Companion$provideFactory$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public ViewModel create(Class cls) {
                    cls.getClass();
                    if (cls.isAssignableFrom(MediaControllerModel.class)) {
                        return new MediaControllerModel(application, laptopPanelModel);
                    }
                    throw new IllegalArgumentException("Unknown ViewModel class");
                }
            };
        }
    }

    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class PlayerListener implements Player.Listener {
        public PlayerListener() {
        }

        @Override // androidx.media3.common.Player.Listener
        public void onIsPlayingChanged(boolean z) {
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onIsPlayingChanged -> " + z, new Object[0]);
            MediaControllerModel.updateUiState$default(MediaControllerModel.this, null, false, 3, null);
        }

        @Override // androidx.media3.common.Player.Listener
        public void onMediaItemTransition(MediaItem mediaItem, int i) {
            androidx.media3.common.MediaMetadata mediaMetadata;
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onMediaItemTransition -> " + ((Object) ((mediaItem == null || (mediaMetadata = mediaItem.mediaMetadata) == null) ? null : mediaMetadata.title)), new Object[0]);
            MediaControllerModel.updateUiState$default(MediaControllerModel.this, null, false, 3, null);
        }

        @Override // androidx.media3.common.Player.Listener
        public void onMediaMetadataChanged(androidx.media3.common.MediaMetadata mediaMetadata) {
            mediaMetadata.getClass();
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onMediaMetadataChanged -> " + ((Object) mediaMetadata.title), new Object[0]);
            MediaControllerModel.updateUiState$default(MediaControllerModel.this, null, false, 3, null);
        }

        @Override // androidx.media3.common.Player.Listener
        public void onPlaybackStateChanged(int i) {
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onPlaybackStateChanged -> " + i, new Object[0]);
            MediaControllerModel.updateUiState$default(MediaControllerModel.this, null, false, 3, null);
        }

        @Override // androidx.media3.common.Player.Listener
        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            positionInfo.getClass();
            positionInfo2.getClass();
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onPositionDiscontinuity: " + positionInfo.positionMs + "-> " + positionInfo2.positionMs, new Object[0]);
            MediaControllerModel.this.updateUiState(Long.valueOf(positionInfo2.positionMs), true);
        }

        @Override // androidx.media3.common.Player.Listener
        public void onTimelineChanged(Timeline timeline, int i) {
            timeline.getClass();
            Logger.INSTANCE.d("MediaControllerModel", "Player.Listener: onTimelineChanged", new Object[0]);
            MediaControllerModel.updateUiState$default(MediaControllerModel.this, null, false, 3, null);
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$connectToSession$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class C01241 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01241(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MediaControllerModel.this.connectToSession(null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$create$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class C01251 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C01251(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MediaControllerModel.this.create(null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$startProgressUpdates$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class C01261 extends SuspendLambda implements Function2 {
        int label;

        C01261(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaControllerModel.this.new C01261(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0 && i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            do {
                MediaController mediaController = MediaControllerModel.this.mediaController;
                if (mediaController != null) {
                    MediaControllerModel mediaControllerModel = MediaControllerModel.this;
                    Logger logger = Logger.INSTANCE;
                    logger.v("MediaControllerModel", "Progress update ....", new Object[0]);
                    MediaUiState.Ready readyCalculateMediaUiState$default = MediaControllerModel.calculateMediaUiState$default(mediaControllerModel, mediaController, null, 2, null);
                    if (Intrinsics.areEqual(mediaControllerModel._uiState.getValue(), readyCalculateMediaUiState$default)) {
                        logger.v("MediaControllerModel", "Skipping progress update: UI state is not Ready", new Object[0]);
                    } else {
                        mediaControllerModel._uiState.setValue(readyCalculateMediaUiState$default);
                    }
                }
                this.label = 1;
            } while (DelayKt.delay(500L, this) != coroutine_suspended);
            return coroutine_suspended;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$updateUiState$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaControllerModel.kt */
    final class C01271 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $force;
        final /* synthetic */ Long $specificPosition;
        int label;
        final /* synthetic */ MediaControllerModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01271(Long l, boolean z, MediaControllerModel mediaControllerModel, Continuation continuation) {
            super(2, continuation);
            this.$specificPosition = l;
            this.$force = z;
            this.this$0 = mediaControllerModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01271(this.$specificPosition, this.$force, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$specificPosition == null && !this.$force) {
                    this.label = 1;
                    if (DelayKt.delay(500L, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            MediaController mediaController = this.this$0.mediaController;
            if (mediaController != null) {
                MediaControllerModel mediaControllerModel = this.this$0;
                MediaUiState.Ready readyCalculateMediaUiState = mediaControllerModel.calculateMediaUiState(mediaController, this.$specificPosition);
                if (!Intrinsics.areEqual(mediaControllerModel._uiState.getValue(), readyCalculateMediaUiState)) {
                    mediaControllerModel._uiState.setValue(readyCalculateMediaUiState);
                    Logger.INSTANCE.d("MediaControllerModel", "UI State updated: " + readyCalculateMediaUiState, new Object[0]);
                }
                if (readyCalculateMediaUiState.isPlaying()) {
                    mediaControllerModel.startProgressUpdates();
                } else {
                    mediaControllerModel.stopProgressUpdates();
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaControllerModel(Application application, LaptopPanelModel laptopPanelModel) {
        super(application);
        application.getClass();
        laptopPanelModel.getClass();
        this.panelModel = laptopPanelModel;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(MediaUiState.NoMedia.INSTANCE);
        this._uiState = MutableStateFlow;
        this.uiState = FlowKt.asStateFlow(MutableStateFlow);
        this.playerListener = new PlayerListener();
        this.SEEK_TIMEOUT = 2000L;
        this.targetSeekPosition = -1L;
        Logger.INSTANCE.d("MediaControllerModel", "Initializing MediaControllerModel", new Object[0]);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3, null);
        this.HBO_PKG = "com.wbd.stream";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MediaUiState.Ready calculateMediaUiState(MediaController mediaController, Long l) {
        androidx.media3.common.MediaMetadata mediaMetadata = mediaController.getMediaMetadata();
        mediaMetadata.getClass();
        CharSequence charSequence = mediaMetadata.title;
        if (charSequence == null || charSequence.length() == 0) {
            mediaMetadata = new MediaMetadata.Builder().setTitle(getLegacyTitle()).build();
        }
        androidx.media3.common.MediaMetadata mediaMetadata2 = mediaMetadata;
        long realDuration = getRealDuration(mediaController);
        if (realDuration == -9223372036854775807L || realDuration <= 0) {
            realDuration = getLegacyDuration();
        }
        long jCoerceAtLeast = RangesKt.coerceAtLeast(mediaController.getCurrentPosition(), 0L);
        Logger logger = Logger.INSTANCE;
        logger.v("MediaControllerModel", "Controller.currentPosition = " + jCoerceAtLeast, new Object[0]);
        long jLongValue = l != null ? l.longValue() : jCoerceAtLeast;
        if (this.isSeeking) {
            long jAbs = Math.abs(jCoerceAtLeast - this.targetSeekPosition);
            boolean z = System.currentTimeMillis() - this.seekStartTime > this.SEEK_TIMEOUT;
            if (jAbs < 500 || z) {
                logger.d("MediaControllerModel", "Seek finished or timeout. Diff=" + jAbs + ", Timeout=" + z, new Object[0]);
                this.isSeeking = false;
                this.targetSeekPosition = -1L;
            } else {
                logger.v("MediaControllerModel", "Seeking in progress... Force using target: " + this.targetSeekPosition, new Object[0]);
                jCoerceAtLeast = this.targetSeekPosition;
            }
        } else {
            jCoerceAtLeast = jLongValue;
        }
        if (Intrinsics.areEqual(((LaptopPanelUiState) this.panelModel.getUiState().getValue()).getPackageName(), this.HBO_PKG)) {
            logger.v("MediaControllerModel", "Handle HBO special case: ", new Object[0]);
            jCoerceAtLeast = getLegacyPosition();
        }
        logger.v("MediaControllerModel", "Get current progress = " + jCoerceAtLeast, new Object[0]);
        if (jCoerceAtLeast < 1000) {
            logger.d("MediaControllerModel", "Progress is too small: " + jCoerceAtLeast + ", adjust to 0", new Object[0]);
            jCoerceAtLeast = 0;
        }
        return new MediaUiState.Ready(mediaController.isPlaying(), mediaMetadata2, getDurationFromProgress(realDuration, jCoerceAtLeast), jCoerceAtLeast, isNextEnabled(mediaController), isPreviousEnabled(mediaController));
    }

    static /* synthetic */ MediaUiState.Ready calculateMediaUiState$default(MediaControllerModel mediaControllerModel, MediaController mediaController, Long l, int i, Object obj) {
        if ((i & 2) != 0) {
            l = null;
        }
        return mediaControllerModel.calculateMediaUiState(mediaController, l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object connectToSession(android.media.session.MediaSession.Token r9, kotlin.coroutines.Continuation r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 211
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel.connectToSession(android.media.session.MediaSession$Token, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final long getDurationFromProgress(long j, long j2) {
        return (j < 1000 || j <= j2) ? j2 : j;
    }

    private final long getLegacyDuration() {
        android.media.MediaMetadata mediaMetadata = ((LaptopPanelUiState) this.panelModel.getUiState().getValue()).getMediaMetadata();
        long j = mediaMetadata != null ? mediaMetadata.getLong("android.media.metadata.DURATION") : 0L;
        Logger.INSTANCE.v("MediaControllerModel", "getLegacyDuration: " + j, new Object[0]);
        return RangesKt.coerceAtLeast(j, 0L);
    }

    private final long getLegacyPosition() {
        long j;
        PlaybackState playbackState = ((LaptopPanelUiState) this.panelModel.getUiState().getValue()).getPlaybackState();
        if (playbackState != null) {
            long position = playbackState.getPosition();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long lastPositionUpdateTime = playbackState.getLastPositionUpdateTime();
            float playbackSpeed = playbackState.getPlaybackSpeed();
            Logger.INSTANCE.v("MediaControllerModel", "legacyPlaybackState.position = " + position + ", realTime = " + jElapsedRealtime + ", lastUpdateTime = " + lastPositionUpdateTime + ", speed = " + playbackSpeed, new Object[0]);
            j = position + ((long) (((float) (jElapsedRealtime - lastPositionUpdateTime)) * playbackSpeed));
        } else {
            j = 0;
        }
        Logger.INSTANCE.v("MediaControllerModel", "getLegacyPosition: " + j, new Object[0]);
        return RangesKt.coerceAtLeast(j, 0L);
    }

    private final String getLegacyTitle() {
        String string;
        android.media.MediaMetadata mediaMetadata = ((LaptopPanelUiState) this.panelModel.getUiState().getValue()).getMediaMetadata();
        if (mediaMetadata == null || (string = mediaMetadata.getString("android.media.metadata.TITLE")) == null) {
            string = "";
        }
        Logger.INSTANCE.v("MediaControllerModel", "getLegacyTitle: " + string, new Object[0]);
        return string;
    }

    private final long getRealDuration(MediaController mediaController) {
        long contentDuration = mediaController.getContentDuration();
        Logger logger = Logger.INSTANCE;
        logger.v("MediaControllerModel", "getRealDuration: Controller.contentDuration = " + contentDuration, new Object[0]);
        if (contentDuration == -9223372036854775807L || contentDuration <= 0) {
            contentDuration = mediaController.getDuration();
            logger.v("MediaControllerModel", "getRealDuration: Controller.duration = " + contentDuration, new Object[0]);
        }
        if (contentDuration != -9223372036854775807L && contentDuration > 0) {
            return contentDuration;
        }
        Bundle bundle = mediaController.getMediaMetadata().extras;
        long j = bundle != null ? bundle.getLong("android.media.metadata.DURATION", 0L) : 0L;
        if (j > 0) {
            contentDuration = j;
        }
        logger.v("MediaControllerModel", "getRealDuration: Media 3 Metadata.duration = " + contentDuration, new Object[0]);
        return contentDuration;
    }

    private final boolean isNextEnabled(MediaController mediaController) {
        return mediaController.isCommandAvailable(8);
    }

    private final boolean isPreviousEnabled(MediaController mediaController) {
        return mediaController.isCommandAvailable(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void releaseController() {
        if (this.mediaController == null && (this._uiState.getValue() instanceof MediaUiState.NoMedia)) {
            return;
        }
        Logger.INSTANCE.d("MediaControllerModel", "Releasing MediaController.", new Object[0]);
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.removeListener(this.playerListener);
        }
        MediaController mediaController2 = this.mediaController;
        if (mediaController2 != null) {
            mediaController2.release();
        }
        this.mediaController = null;
        this.activeSessionToken = null;
        this._uiState.setValue(MediaUiState.NoMedia.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startProgressUpdates() {
        Job job = this.progressUpdateJob;
        if (job == null || !job.isActive()) {
            Logger.INSTANCE.d("MediaControllerModel", "startProgressUpdates", new Object[0]);
            this.progressUpdateJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01261(null), 3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopProgressUpdates() {
        Logger.INSTANCE.d("MediaControllerModel", "stopProgressUpdates", new Object[0]);
        Job job = this.progressUpdateJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.progressUpdateJob = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUiState(Long l, boolean z) {
        Job job = this.uiUpdateDebounceJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.uiUpdateDebounceJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C01271(l, z, this, null), 3, null);
    }

    static /* synthetic */ void updateUiState$default(MediaControllerModel mediaControllerModel, Long l, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            l = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        mediaControllerModel.updateUiState(l, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object create(androidx.media3.session.SessionToken r8, kotlin.coroutines.Continuation r9) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel.C01251
            if (r0 == 0) goto L13
            r0 = r9
            com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$create$1 r0 = (com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel.C01251) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$create$1 r0 = new com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel$create$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            r5 = 0
            java.lang.String r6 = "MediaControllerModel"
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            goto L57
        L2d:
            r7 = move-exception
            goto L5a
        L2f:
            r7 = move-exception
            goto L64
        L31:
            r7 = move-exception
            goto L6e
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.media3.session.MediaController$Builder r9 = new androidx.media3.session.MediaController$Builder     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            android.app.Application r7 = r7.getApplication()     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            r9.<init>(r7, r8)     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            com.google.common.util.concurrent.ListenableFuture r7 = r9.buildAsync()     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            r7.getClass()     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            r0.label = r3     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            java.lang.Object r9 = kotlinx.coroutines.guava.ListenableFutureKt.await(r7, r0)     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            if (r9 != r1) goto L57
            return r1
        L57:
            androidx.media3.session.MediaController r9 = (androidx.media3.session.MediaController) r9     // Catch: java.lang.Exception -> L2d java.lang.SecurityException -> L2f java.util.concurrent.ExecutionException -> L31
            return r9
        L5a:
            com.motorola.laptoppanel.util.Logger r8 = com.motorola.laptoppanel.util.Logger.INSTANCE
            java.lang.String r9 = "Generic exception creating media3 controller"
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r8.e(r6, r7, r9, r0)
            goto La4
        L64:
            com.motorola.laptoppanel.util.Logger r8 = com.motorola.laptoppanel.util.Logger.INSTANCE
            java.lang.String r9 = "SecurityException creating media3 controller"
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r8.e(r6, r7, r9, r0)
            goto La4
        L6e:
            java.lang.Throwable r8 = r7.getCause()
            boolean r8 = r8 instanceof java.lang.SecurityException
            if (r8 == 0) goto L9b
            com.motorola.laptoppanel.util.Logger r8 = com.motorola.laptoppanel.util.Logger.INSTANCE
            java.lang.Throwable r9 = r7.getCause()
            if (r9 == 0) goto L83
            java.lang.String r9 = r9.getMessage()
            goto L84
        L83:
            r9 = r4
        L84:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Connection rejected by session during create: "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r8.e(r6, r7, r9, r0)
            goto La4
        L9b:
            com.motorola.laptoppanel.util.Logger r8 = com.motorola.laptoppanel.util.Logger.INSTANCE
            java.lang.String r9 = "ExecutionException creating media3 controller"
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r8.e(r6, r7, r9, r0)
        La4:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel.create(androidx.media3.session.SessionToken, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final StateFlow getUiState() {
        return this.uiState;
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        Logger.INSTANCE.d("MediaControllerModel", "onCleared: Releasing all resources.", new Object[0]);
        releaseController();
        stopProgressUpdates();
        super.onCleared();
    }

    public final void pause() {
        Logger.INSTANCE.d("MediaControllerModel", "Pause ...", new Object[0]);
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.pause();
        }
    }

    public final void play() {
        Logger.INSTANCE.d("MediaControllerModel", "Play ...", new Object[0]);
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.play();
        }
    }

    public final void seekTo(long j) {
        Logger.INSTANCE.d("MediaControllerModel", "Seek to " + MediaControllerModelKt.formatDuration(j) + "...", new Object[0]);
        this.isSeeking = true;
        this.targetSeekPosition = j;
        this.seekStartTime = System.currentTimeMillis();
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.seekTo(j);
        }
        Object value = this.uiState.getValue();
        MediaUiState.Ready ready = value instanceof MediaUiState.Ready ? (MediaUiState.Ready) value : null;
        if (ready != null) {
            this._uiState.setValue(MediaUiState.Ready.copy$default(ready, false, null, 0L, j, false, false, 55, null));
        }
    }

    public final void skipToNext() {
        Logger.INSTANCE.d("MediaControllerModel", "Skip to next ...", new Object[0]);
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.seekToNext();
        }
    }

    public final void skipToPrevious() {
        Logger.INSTANCE.d("MediaControllerModel", "Skip to previous ...", new Object[0]);
        MediaController mediaController = this.mediaController;
        if (mediaController != null) {
            mediaController.seekToPrevious();
        }
    }
}
