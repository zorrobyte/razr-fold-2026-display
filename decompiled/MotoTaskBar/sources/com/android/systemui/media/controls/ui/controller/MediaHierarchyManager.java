package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.os.Trace;
import android.view.ViewGroup;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: MediaHierarchyManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaHierarchyManager {
    public static final Companion Companion = new Companion(null);
    private float carouselAlpha;
    private final Context context;
    private int currentAttachmentLocation;
    private int desiredLocation;
    private final MediaCarouselController mediaCarouselController;
    private MediaHost mediaHost;
    private final MediaDataManager mediaManager;
    private boolean qsExpanded;

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$1, reason: invalid class name */
    /* JADX INFO: compiled from: MediaHierarchyManager.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        AnonymousClass1(Object obj) {
            super(0, obj, MediaHierarchyManager.class, "updateUserVisibility", "updateUserVisibility()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m1395invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1395invoke() {
            ((MediaHierarchyManager) this.receiver).updateUserVisibility();
        }
    }

    /* JADX INFO: compiled from: MediaHierarchyManager.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaHierarchyManager(Context context, MediaCarouselController mediaCarouselController, MediaDataManager mediaDataManager) {
        context.getClass();
        mediaCarouselController.getClass();
        mediaDataManager.getClass();
        this.context = context;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaManager = mediaDataManager;
        this.currentAttachmentLocation = -1;
        this.qsExpanded = true;
        this.carouselAlpha = 1.0f;
        mediaCarouselController.setUpdateUserVisibility(new AnonymousClass1(this));
        mediaCarouselController.setUpdateHostVisibility(new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaHierarchyManager._init_$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$0(MediaHierarchyManager mediaHierarchyManager) {
        MediaHost mediaHost = mediaHierarchyManager.mediaHost;
        if (mediaHost != null) {
            mediaHost.updateViewVisibility();
        }
        return Unit.INSTANCE;
    }

    private final UniqueObjectHostView createUniqueObjectHost() {
        return new UniqueObjectHostView(this.context);
    }

    private final boolean getHasActiveMediaOrRecommendation() {
        return this.mediaManager.hasActiveMediaOrRecommendation();
    }

    private final ViewGroup getMediaFrame() {
        return this.mediaCarouselController.getMediaFrame();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit register$lambda$1(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        mediaHierarchyManager.updateDesiredLocation();
        return Unit.INSTANCE;
    }

    private final int resolveLocationForFading() {
        return 0;
    }

    private final void setCarouselAlpha(float f) {
        if (this.carouselAlpha == f) {
            return;
        }
        this.carouselAlpha = f;
        CrossFadeHelper.fadeIn(getMediaFrame(), f);
    }

    private final void updateDesiredLocation() {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaHierarchyManager#updateDesiredLocation");
        }
        try {
            this.mediaCarouselController.onDesiredLocationChanged(this.desiredLocation, this.mediaHost, false, 200L, 0L);
            setCarouselAlpha(1.0f);
            this.mediaCarouselController.setCurrentState(-1, this.desiredLocation, 1.0f, true);
            updateHostAttachment();
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
        }
    }

    private final void updateHostAttachment() {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaHierarchyManager#updateHostAttachment");
        }
        try {
            int iResolveLocationForFading = resolveLocationForFading();
            if (this.currentAttachmentLocation != iResolveLocationForFading) {
                this.currentAttachmentLocation = iResolveLocationForFading;
                ViewGroup viewGroup = (ViewGroup) getMediaFrame().getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(getMediaFrame());
                }
                MediaHost mediaHost = this.mediaHost;
                mediaHost.getClass();
                mediaHost.getHostView().addView(getMediaFrame());
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUserVisibility() {
        this.mediaCarouselController.getMediaCarouselScrollHandler().setVisibleToUser(this.qsExpanded || getHasActiveMediaOrRecommendation());
    }

    public final UniqueObjectHostView register(MediaHost mediaHost) {
        mediaHost.getClass();
        UniqueObjectHostView uniqueObjectHostViewCreateUniqueObjectHost = createUniqueObjectHost();
        mediaHost.setHostView(uniqueObjectHostViewCreateUniqueObjectHost);
        mediaHost.addVisibilityChangeListener(new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaHierarchyManager.register$lambda$1(this.f$0, ((Boolean) obj).booleanValue());
            }
        });
        this.mediaHost = mediaHost;
        updateDesiredLocation();
        return uniqueObjectHostViewCreateUniqueObjectHost;
    }
}
