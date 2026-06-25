package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$1 extends FunctionReferenceImpl implements Function2 {
    MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$1(Object obj) {
        super(2, obj, MediaCarouselController.class, "onAdded", "onAdded(Lcom/android/systemui/media/controls/ui/viewmodel/MediaCommonViewModel;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((MediaCommonViewModel) obj, ((Number) obj2).intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(MediaCommonViewModel mediaCommonViewModel, int i) {
        mediaCommonViewModel.getClass();
        ((MediaCarouselController) this.receiver).onAdded(mediaCommonViewModel, i);
    }
}
