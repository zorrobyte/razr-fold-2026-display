package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.util.Utils;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: MediaCarouselViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselViewModel {
    private final Context applicationContext;
    private final CoroutineScope applicationScope;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Executor backgroundExecutor;
    private final MediaControlInteractorFactory controlInteractorFactory;
    private final MediaCarouselInteractor interactor;
    private final MediaUiEventLogger logger;
    private final Map mediaControlByInstanceId;
    private final MediaFlags mediaFlags;
    private final StateFlow mediaItems;
    private Set modelsPendingRemoval;
    private boolean shouldReorder;
    private final VisualStabilityProvider visualStabilityProvider;

    public MediaCarouselViewModel(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, VisualStabilityProvider visualStabilityProvider, MediaCarouselInteractor mediaCarouselInteractor, MediaControlInteractorFactory mediaControlInteractorFactory, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        coroutineScope.getClass();
        context.getClass();
        coroutineDispatcher.getClass();
        executor.getClass();
        visualStabilityProvider.getClass();
        mediaCarouselInteractor.getClass();
        mediaControlInteractorFactory.getClass();
        mediaUiEventLogger.getClass();
        mediaFlags.getClass();
        this.applicationScope = coroutineScope;
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.visualStabilityProvider = visualStabilityProvider;
        this.interactor = mediaCarouselInteractor;
        this.controlInteractorFactory = mediaControlInteractorFactory;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        this.mediaItems = FlowKt.stateIn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(FlowKt.transformLatest(FlowConflatedKt.conflatedCallbackFlow(new MediaCarouselViewModel$mediaItems$1(this, null)), new MediaCarouselViewModel$special$$inlined$flatMapLatest$1(null, this)), new MediaCarouselViewModel$mediaItems$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 0L, 0L, 3, null), CollectionsKt.emptyList());
        this.mediaControlByInstanceId = new LinkedHashMap();
        this.modelsPendingRemoval = new LinkedHashSet();
        this.shouldReorder = true;
    }

    private final MediaControlViewModel createMediaControlViewModel(InstanceId instanceId) {
        return new MediaControlViewModel(this.applicationContext, this.backgroundDispatcher, this.backgroundExecutor, this.controlInteractorFactory.create(instanceId), this.logger);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isReorderingAllowed() {
        return this.visualStabilityProvider.isReorderingAllowed();
    }

    private final void onMediaControlAddedOrUpdated(MediaCommonViewModel mediaCommonViewModel, MediaCommonModel.MediaControl mediaControl) {
        if (!mediaControl.getCanBeRemoved() || Utils.useMediaResumption(this.applicationContext)) {
            this.modelsPendingRemoval.remove(mediaControl);
        } else if (isReorderingAllowed()) {
            mediaCommonViewModel.getOnRemoved().invoke(Boolean.TRUE);
        } else {
            this.modelsPendingRemoval.add(mediaControl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MediaCommonViewModel.MediaControl toViewModel(final MediaCommonModel.MediaControl mediaControl) {
        MediaCommonViewModel.MediaControl mediaControlCopy$default;
        final InstanceId instanceId = mediaControl.getMediaLoadedModel().getInstanceId();
        MediaCommonViewModel.MediaControl mediaControl2 = (MediaCommonViewModel.MediaControl) this.mediaControlByInstanceId.get(instanceId);
        if (mediaControl2 != null && (mediaControlCopy$default = MediaCommonViewModel.MediaControl.copy$default(mediaControl2, null, mediaControl.getMediaLoadedModel().getImmediatelyUpdateUi(), null, null, null, null, 61, null)) != null) {
            return mediaControlCopy$default;
        }
        MediaCommonViewModel.MediaControl mediaControl3 = new MediaCommonViewModel.MediaControl(instanceId, mediaControl.getMediaLoadedModel().getImmediatelyUpdateUi(), createMediaControlViewModel(instanceId), new Function1() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselViewModel.toViewModel$lambda$1(this.f$0, mediaControl, (MediaCommonViewModel) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselViewModel.toViewModel$lambda$2(this.f$0, instanceId, ((Boolean) obj).booleanValue());
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselViewModel.toViewModel$lambda$3(this.f$0, mediaControl, (MediaCommonViewModel) obj);
            }
        });
        this.mediaControlByInstanceId.put(instanceId, mediaControl3);
        return mediaControl3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$1(MediaCarouselViewModel mediaCarouselViewModel, MediaCommonModel.MediaControl mediaControl, MediaCommonViewModel mediaCommonViewModel) {
        mediaCommonViewModel.getClass();
        mediaCarouselViewModel.onMediaControlAddedOrUpdated(mediaCommonViewModel, mediaControl);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$2(MediaCarouselViewModel mediaCarouselViewModel, InstanceId instanceId, boolean z) {
        mediaCarouselViewModel.interactor.removeMediaControl(instanceId, 0L);
        mediaCarouselViewModel.mediaControlByInstanceId.remove(instanceId);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toViewModel$lambda$3(MediaCarouselViewModel mediaCarouselViewModel, MediaCommonModel.MediaControl mediaControl, MediaCommonViewModel mediaCommonViewModel) {
        mediaCommonViewModel.getClass();
        mediaCarouselViewModel.onMediaControlAddedOrUpdated(mediaCommonViewModel, mediaControl);
        return Unit.INSTANCE;
    }

    public final StateFlow getMediaItems() {
        return this.mediaItems;
    }

    public final void onSwipeToDismiss() {
        this.logger.logSwipeDismiss();
        this.interactor.onSwipeToDismiss();
    }
}
