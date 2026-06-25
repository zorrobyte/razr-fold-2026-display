package com.android.systemui.media.controls.ui.controller;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class MediaCarouselController$addOrUpdatePlayer$1$1 extends FunctionReferenceImpl implements Function0 {
    MediaCarouselController$addOrUpdatePlayer$1$1(Object obj) {
        super(0, obj, MediaCarouselController.class, "updateCarouselDimensions", "updateCarouselDimensions()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    /* JADX INFO: renamed from: invoke */
    public /* bridge */ /* synthetic */ Object mo2224invoke() {
        m1378invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m1378invoke() {
        ((MediaCarouselController) this.receiver).updateCarouselDimensions();
    }
}
