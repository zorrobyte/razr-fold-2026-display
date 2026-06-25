package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$4 extends FunctionReferenceImpl implements Function3 {
    MediaCarouselController$listenForMediaItemsChanges$1$1$listUpdateCallback$4(Object obj) {
        super(3, obj, MediaCarouselController.class, "onMoved", "onMoved(Lcom/android/systemui/media/controls/ui/viewmodel/MediaCommonViewModel;II)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        invoke((MediaCommonViewModel) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(MediaCommonViewModel mediaCommonViewModel, int i, int i2) {
        mediaCommonViewModel.getClass();
        ((MediaCarouselController) this.receiver).onMoved(mediaCommonViewModel, i, i2);
    }
}
