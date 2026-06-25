package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$3 extends FunctionReferenceImpl implements Function1 {
    MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$3(Object obj) {
        super(1, obj, MediaCarouselController.class, "onRemoved", "onRemoved(Lcom/android/systemui/media/controls/ui/viewmodel/MediaCommonViewModel;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((MediaCommonViewModel) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(MediaCommonViewModel mediaCommonViewModel) {
        mediaCommonViewModel.getClass();
        ((MediaCarouselController) this.receiver).onRemoved(mediaCommonViewModel);
    }
}
