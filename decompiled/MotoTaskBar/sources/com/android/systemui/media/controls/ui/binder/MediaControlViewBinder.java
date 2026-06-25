package com.android.systemui.media.controls.ui.binder;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Trace;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.media.controls.ui.animation.AnimationBindHandler;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.util.MediaArtworkHelper;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.GutsViewModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaOutputSwitcherViewModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaPlayerViewModel;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.res.R$drawable;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.ripple.RippleAnimation;
import com.android.systemui.surfaceeffects.ripple.RippleAnimationConfig;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.motorola.taskbar.R$id;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: MediaControlViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControlViewBinder {
    public static final MediaControlViewBinder INSTANCE = new MediaControlViewBinder();

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bind$1, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControlViewBinder.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ CoroutineDispatcher $backgroundDispatcher;
        final /* synthetic */ CoroutineDispatcher $mainDispatcher;
        final /* synthetic */ MediaFlags $mediaFlags;
        final /* synthetic */ MediaViewController $viewController;
        final /* synthetic */ MediaViewHolder $viewHolder;
        final /* synthetic */ MediaControlViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: MediaControlViewBinder.kt */
        final class C00071 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineDispatcher $backgroundDispatcher;
            final /* synthetic */ CoroutineDispatcher $mainDispatcher;
            final /* synthetic */ MediaFlags $mediaFlags;
            final /* synthetic */ MediaViewController $viewController;
            final /* synthetic */ MediaViewHolder $viewHolder;
            final /* synthetic */ MediaControlViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: MediaControlViewBinder.kt */
            final class C00081 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineDispatcher $backgroundDispatcher;
                final /* synthetic */ CoroutineDispatcher $mainDispatcher;
                final /* synthetic */ MediaFlags $mediaFlags;
                final /* synthetic */ MediaViewController $viewController;
                final /* synthetic */ MediaViewHolder $viewHolder;
                final /* synthetic */ MediaControlViewModel $viewModel;
                int label;

                /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
                /* JADX INFO: compiled from: MediaControlViewBinder.kt */
                final class C00091 extends SuspendLambda implements Function2 {
                    final /* synthetic */ CoroutineDispatcher $backgroundDispatcher;
                    final /* synthetic */ CoroutineDispatcher $mainDispatcher;
                    final /* synthetic */ MediaFlags $mediaFlags;
                    final /* synthetic */ MediaViewController $viewController;
                    final /* synthetic */ MediaViewHolder $viewHolder;
                    /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C00091(MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags, Continuation continuation) {
                        super(2, continuation);
                        this.$viewHolder = mediaViewHolder;
                        this.$viewController = mediaViewController;
                        this.$backgroundDispatcher = coroutineDispatcher;
                        this.$mainDispatcher = coroutineDispatcher2;
                        this.$mediaFlags = mediaFlags;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C00091 c00091 = new C00091(this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, continuation);
                        c00091.L$0 = obj;
                        return c00091;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(MediaPlayerViewModel mediaPlayerViewModel, Continuation continuation) {
                        return ((C00091) create(mediaPlayerViewModel, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            MediaPlayerViewModel mediaPlayerViewModel = (MediaPlayerViewModel) this.L$0;
                            if (mediaPlayerViewModel != null) {
                                MediaViewHolder mediaViewHolder = this.$viewHolder;
                                MediaViewController mediaViewController = this.$viewController;
                                CoroutineDispatcher coroutineDispatcher = this.$backgroundDispatcher;
                                CoroutineDispatcher coroutineDispatcher2 = this.$mainDispatcher;
                                MediaFlags mediaFlags = this.$mediaFlags;
                                MediaControlViewBinder mediaControlViewBinder = MediaControlViewBinder.INSTANCE;
                                this.label = 1;
                                if (mediaControlViewBinder.bindMediaCard(mediaViewHolder, mediaViewController, mediaPlayerViewModel, coroutineDispatcher, coroutineDispatcher2, mediaFlags, this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
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

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00081(MediaControlViewModel mediaControlViewModel, MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = mediaControlViewModel;
                    this.$viewHolder = mediaViewHolder;
                    this.$viewController = mediaViewController;
                    this.$backgroundDispatcher = coroutineDispatcher;
                    this.$mainDispatcher = coroutineDispatcher2;
                    this.$mediaFlags = mediaFlags;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00081(this.$viewModel, this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C00081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow player = this.$viewModel.getPlayer();
                        C00091 c00091 = new C00091(this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(player, c00091, this) == coroutine_suspended) {
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00071(MediaControlViewModel mediaControlViewModel, MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = mediaControlViewModel;
                this.$viewHolder = mediaViewHolder;
                this.$viewController = mediaViewController;
                this.$backgroundDispatcher = coroutineDispatcher;
                this.$mainDispatcher = coroutineDispatcher2;
                this.$mediaFlags = mediaFlags;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00071 c00071 = new C00071(this.$viewModel, this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, continuation);
                c00071.L$0 = obj;
                return c00071;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new C00081(this.$viewModel, this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, null), 3, null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(MediaControlViewModel mediaControlViewModel, MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = mediaControlViewModel;
            this.$viewHolder = mediaViewHolder;
            this.$viewController = mediaViewController;
            this.$backgroundDispatcher = coroutineDispatcher;
            this.$mainDispatcher = coroutineDispatcher2;
            this.$mediaFlags = mediaFlags;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, continuation);
            anonymousClass1.L$0 = lifecycleOwner;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C00071 c00071 = new C00071(this.$viewModel, this.$viewHolder, this.$viewController, this.$backgroundDispatcher, this.$mainDispatcher, this.$mediaFlags, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00071, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindArtworkAndColor$2, reason: invalid class name */
    /* JADX INFO: compiled from: MediaControlViewBinder.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$IntRef $height;
        final /* synthetic */ CoroutineDispatcher $mainDispatcher;
        final /* synthetic */ int $traceCookie;
        final /* synthetic */ String $traceName;
        final /* synthetic */ boolean $updateBackground;
        final /* synthetic */ MediaViewController $viewController;
        final /* synthetic */ MediaViewHolder $viewHolder;
        final /* synthetic */ MediaPlayerViewModel $viewModel;
        final /* synthetic */ Ref$IntRef $width;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindArtworkAndColor$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: MediaControlViewBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Drawable $artwork;
            final /* synthetic */ Ref$IntRef $height;
            final /* synthetic */ int $traceCookie;
            final /* synthetic */ String $traceName;
            final /* synthetic */ boolean $updateBackground;
            final /* synthetic */ MediaViewController $viewController;
            final /* synthetic */ MediaViewHolder $viewHolder;
            final /* synthetic */ MediaPlayerViewModel $viewModel;
            final /* synthetic */ Ref$IntRef $width;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(MediaViewController mediaViewController, MediaPlayerViewModel mediaPlayerViewModel, MediaViewHolder mediaViewHolder, boolean z, Drawable drawable, String str, int i, Ref$IntRef ref$IntRef, Ref$IntRef ref$IntRef2, Continuation continuation) {
                super(2, continuation);
                this.$viewController = mediaViewController;
                this.$viewModel = mediaPlayerViewModel;
                this.$viewHolder = mediaViewHolder;
                this.$updateBackground = z;
                this.$artwork = drawable;
                this.$traceName = str;
                this.$traceCookie = i;
                this.$width = ref$IntRef;
                this.$height = ref$IntRef2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewController, this.$viewModel, this.$viewHolder, this.$updateBackground, this.$artwork, this.$traceName, this.$traceCookie, this.$width, this.$height, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                boolean zUpdateColorScheme = this.$viewController.getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().updateColorScheme(this.$viewModel.getColorScheme());
                ImageView albumView = this.$viewHolder.getAlbumView();
                albumView.setPadding(0, 0, 0, 0);
                if (this.$updateBackground || zUpdateColorScheme || (!this.$viewController.isArtworkBound() && this.$viewModel.getShouldAddGradient())) {
                    Drawable prevArtwork = this.$viewController.getPrevArtwork();
                    if (prevArtwork != null) {
                        Drawable drawable = this.$artwork;
                        Ref$IntRef ref$IntRef = this.$width;
                        Ref$IntRef ref$IntRef2 = this.$height;
                        MediaPlayerViewModel mediaPlayerViewModel = this.$viewModel;
                        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{prevArtwork, drawable});
                        MediaControlViewBinder mediaControlViewBinder = MediaControlViewBinder.INSTANCE;
                        mediaControlViewBinder.scaleTransitionDrawableLayer(transitionDrawable, 0, ref$IntRef.element, ref$IntRef2.element);
                        mediaControlViewBinder.scaleTransitionDrawableLayer(transitionDrawable, 1, ref$IntRef.element, ref$IntRef2.element);
                        transitionDrawable.setLayerGravity(0, 17);
                        transitionDrawable.setLayerGravity(1, 17);
                        transitionDrawable.setCrossFadeEnabled(true);
                        albumView.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(mediaPlayerViewModel.getShouldAddGradient() ? 333 : 80);
                    } else {
                        albumView.setImageDrawable(this.$artwork);
                    }
                }
                this.$viewController.setArtworkBound(this.$viewModel.getShouldAddGradient());
                this.$viewController.setPrevArtwork(this.$artwork);
                if (this.$viewModel.getUseGrayColorFilter()) {
                    this.$viewHolder.getAppIcon().setColorFilter(MediaControlViewBinder.INSTANCE.getGrayscaleFilter());
                    Icon launcherIcon = this.$viewModel.getLauncherIcon();
                    if (launcherIcon instanceof Icon.Loaded) {
                        this.$viewHolder.getAppIcon().setImageDrawable(((Icon.Loaded) this.$viewModel.getLauncherIcon()).getDrawable());
                    } else {
                        if (!(launcherIcon instanceof Icon.Resource)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        this.$viewHolder.getAppIcon().setImageResource(((Icon.Resource) this.$viewModel.getLauncherIcon()).getRes());
                    }
                } else {
                    this.$viewHolder.getAppIcon().setColorFilter(this.$viewController.getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().getAccentPrimary().getTargetColor());
                    this.$viewHolder.getAppIcon().setImageIcon(this.$viewModel.getAppIcon());
                }
                Trace.endAsyncSection(this.$traceName, this.$traceCookie);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(MediaPlayerViewModel mediaPlayerViewModel, MediaViewHolder mediaViewHolder, Ref$IntRef ref$IntRef, Ref$IntRef ref$IntRef2, CoroutineDispatcher coroutineDispatcher, MediaViewController mediaViewController, boolean z, String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = mediaPlayerViewModel;
            this.$viewHolder = mediaViewHolder;
            this.$width = ref$IntRef;
            this.$height = ref$IntRef2;
            this.$mainDispatcher = coroutineDispatcher;
            this.$viewController = mediaViewController;
            this.$updateBackground = z;
            this.$traceName = str;
            this.$traceCookie = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$viewModel, this.$viewHolder, this.$width, this.$height, this.$mainDispatcher, this.$viewController, this.$updateBackground, this.$traceName, this.$traceCookie, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Drawable colorDrawable;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$viewModel.getShouldAddGradient()) {
                    MediaControlViewBinder mediaControlViewBinder = MediaControlViewBinder.INSTANCE;
                    Context context = this.$viewHolder.getAlbumView().getContext();
                    context.getClass();
                    android.graphics.drawable.Icon backgroundCover = this.$viewModel.getBackgroundCover();
                    backgroundCover.getClass();
                    colorDrawable = mediaControlViewBinder.addGradientToPlayerAlbum(context, backgroundCover, this.$viewModel.getColorScheme(), this.$width.element, this.$height.element);
                } else {
                    colorDrawable = new ColorDrawable(0);
                }
                Drawable drawable = colorDrawable;
                CoroutineDispatcher coroutineDispatcher = this.$mainDispatcher;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewController, this.$viewModel, this.$viewHolder, this.$updateBackground, drawable, this.$traceName, this.$traceCookie, this.$width, this.$height, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindMediaCard$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MediaControlViewBinder.kt */
    final class C00971 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C00971(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MediaControlViewBinder.this.bindMediaCard(null, null, null, null, null, null, this);
        }
    }

    private MediaControlViewBinder() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LayerDrawable addGradientToPlayerAlbum(Context context, android.graphics.drawable.Icon icon, ColorScheme colorScheme, int i, int i2) {
        MediaArtworkHelper mediaArtworkHelper = MediaArtworkHelper.INSTANCE;
        Drawable scaledBackground = mediaArtworkHelper.getScaledBackground(context, icon, i, i2);
        Drawable drawable = context.getDrawable(R$drawable.qs_media_scrim);
        Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
        drawableMutate.getClass();
        return mediaArtworkHelper.setUpGradientColorOnDrawable(scaledBackground, (GradientDrawable) drawableMutate, colorScheme, 0.25f, 1.0f);
    }

    private final void bindActionButtons(final MediaViewHolder mediaViewHolder, MediaPlayerViewModel mediaPlayerViewModel, final MediaViewController mediaViewController) {
        Set genericButtonIds = MediaViewHolder.Companion.getGenericButtonIds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(genericButtonIds, 10));
        Iterator it = genericButtonIds.iterator();
        while (it.hasNext()) {
            arrayList.add(mediaViewHolder.getAction(((Number) it.next()).intValue()));
        }
        ConstraintSet expandedLayout = mediaViewController.getExpandedLayout();
        ConstraintSet collapsedLayout = mediaViewController.getCollapsedLayout();
        int i = 0;
        if (mediaPlayerViewModel.getUseSemanticActions()) {
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ImageButton imageButton = (ImageButton) obj;
                MediaControlViewBinder mediaControlViewBinder = INSTANCE;
                mediaControlViewBinder.setVisibleAndAlpha(expandedLayout, imageButton.getId(), false);
                mediaControlViewBinder.setVisibleAndAlpha(collapsedLayout, imageButton.getId(), false);
            }
            for (Object obj2 : MediaControlViewModel.Companion.getSEMANTIC_ACTIONS_ALL()) {
                int i3 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                final int iIntValue = ((Number) obj2).intValue();
                final ImageButton action = mediaViewHolder.getAction(iIntValue);
                final MediaActionViewModel mediaActionViewModel = (MediaActionViewModel) mediaPlayerViewModel.getActionButtons().get(i);
                if (action.getId() == R$id.actionPrev) {
                    if (mediaActionViewModel != null) {
                        mediaViewController.setUpPrevButtonInfo(true, mediaActionViewModel.getNotVisibleValue());
                    }
                } else if (action.getId() == R$id.actionNext && mediaActionViewModel != null) {
                    mediaViewController.setUpNextButtonInfo(true, mediaActionViewModel.getNotVisibleValue());
                }
                if (mediaActionViewModel != null) {
                    Object tag = action.getTag();
                    if (tag == null) {
                        tag = new AnimationBindHandler();
                    }
                    final AnimationBindHandler animationBindHandler = (AnimationBindHandler) tag;
                    animationBindHandler.tryExecute(new Function0() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return MediaControlViewBinder.bindActionButtons$lambda$9$lambda$8$lambda$7(animationBindHandler, mediaActionViewModel, action, mediaViewHolder, mediaViewController, iIntValue);
                        }
                    });
                } else {
                    INSTANCE.clearButton(action);
                }
                i = i3;
            }
        } else {
            Iterator it2 = MediaControlViewModel.Companion.getSEMANTIC_ACTIONS_COMPACT().iterator();
            while (it2.hasNext()) {
                int iIntValue2 = ((Number) it2.next()).intValue();
                MediaControlViewBinder mediaControlViewBinder2 = INSTANCE;
                mediaControlViewBinder2.setVisibleAndAlpha(expandedLayout, iIntValue2, false);
                mediaControlViewBinder2.setVisibleAndAlpha(expandedLayout, iIntValue2, false);
            }
            int size2 = arrayList.size();
            int i4 = 0;
            int i5 = 0;
            while (i5 < size2) {
                Object obj3 = arrayList.get(i5);
                i5++;
                int i6 = i4 + 1;
                if (i4 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ImageButton imageButton2 = (ImageButton) obj3;
                if (i4 < mediaPlayerViewModel.getActionButtons().size()) {
                    MediaActionViewModel mediaActionViewModel2 = (MediaActionViewModel) mediaPlayerViewModel.getActionButtons().get(i4);
                    if (mediaActionViewModel2 != null) {
                        MediaControlViewBinder mediaControlViewBinder3 = INSTANCE;
                        mediaControlViewBinder3.bindButtonCommon(imageButton2, mediaViewHolder.getMultiRippleView(), mediaActionViewModel2, mediaViewController);
                        mediaControlViewBinder3.setVisibleAndAlpha(expandedLayout, imageButton2.getId(), true);
                        mediaControlViewBinder3.setVisibleAndAlpha(collapsedLayout, imageButton2.getId(), mediaActionViewModel2.getShowInCollapsed());
                    } else {
                        INSTANCE.clearButton(imageButton2);
                    }
                } else {
                    MediaControlViewBinder mediaControlViewBinder4 = INSTANCE;
                    mediaControlViewBinder4.clearButton(imageButton2);
                    mediaControlViewBinder4.setVisibleAndAlpha(expandedLayout, imageButton2.getId(), false);
                    mediaControlViewBinder4.setVisibleAndAlpha(collapsedLayout, imageButton2.getId(), false);
                }
                i4 = i6;
            }
        }
        updateSeekBarVisibility(mediaViewController.getExpandedLayout(), mediaViewController.isSeekBarEnabled());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit bindActionButtons$lambda$9$lambda$8$lambda$7(AnimationBindHandler animationBindHandler, MediaActionViewModel mediaActionViewModel, ImageButton imageButton, MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, int i) {
        if (animationBindHandler.updateRebindId(mediaActionViewModel.getRebindId())) {
            animationBindHandler.unregisterAll();
            animationBindHandler.tryRegister(mediaActionViewModel.getIcon());
            animationBindHandler.tryRegister(mediaActionViewModel.getBackground());
            INSTANCE.bindButtonCommon(imageButton, mediaViewHolder.getMultiRippleView(), mediaActionViewModel, mediaViewController);
        }
        INSTANCE.setSemanticButtonVisibleAndAlpha(mediaViewHolder.getAction(i), mediaViewController.getExpandedLayout(), mediaViewController.getCollapsedLayout(), mediaActionViewModel.isVisibleWhenScrubbing() || !mediaViewController.isScrubbing(), mediaActionViewModel.getNotVisibleValue(), mediaActionViewModel.getShowInCollapsed());
        return Unit.INSTANCE;
    }

    private final Object bindArtworkAndColor(MediaViewHolder mediaViewHolder, MediaPlayerViewModel mediaPlayerViewModel, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags, boolean z, Continuation continuation) {
        int iHashCode = mediaViewHolder.hashCode();
        Trace.beginAsyncSection("MediaControlViewBinder#bindArtworkAndColor", iHashCode);
        if (z) {
            mediaViewController.setArtworkBound(false);
        }
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = mediaViewHolder.getAlbumView().getMeasuredWidth();
        Ref$IntRef ref$IntRef2 = new Ref$IntRef();
        ref$IntRef2.element = mediaViewHolder.getAlbumView().getMeasuredHeight();
        if (mediaFlags.isSceneContainerEnabled() && (ref$IntRef.element <= 0 || ref$IntRef2.element <= 0)) {
            ref$IntRef.element = mediaViewController.getWidthInSceneContainerPx();
            ref$IntRef2.element = mediaViewController.getHeightInSceneContainerPx();
        }
        Object objWithContext = BuildersKt.withContext(coroutineDispatcher, new AnonymousClass2(mediaPlayerViewModel, mediaViewHolder, ref$IntRef, ref$IntRef2, coroutineDispatcher2, mediaViewController, z, "MediaControlViewBinder#bindArtworkAndColor", iHashCode, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    private final void bindButtonCommon(final ImageButton imageButton, final MultiRippleView multiRippleView, final MediaActionViewModel mediaActionViewModel, final MediaViewController mediaViewController) {
        imageButton.setImageDrawable(mediaActionViewModel.getIcon());
        imageButton.setBackground(mediaActionViewModel.getBackground());
        imageButton.setContentDescription(mediaActionViewModel.getContentDescription());
        imageButton.setEnabled(mediaActionViewModel.isEnabled());
        if (mediaActionViewModel.isEnabled()) {
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder.bindButtonCommon.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    mediaActionViewModel.getOnClicked().invoke(Integer.valueOf(view.getId()));
                    mediaViewController.getMultiRippleController$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().play(MediaControlViewBinder.INSTANCE.createTouchRippleAnimation(imageButton, mediaViewController.getColorSchemeTransition$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(), multiRippleView));
                    if (mediaActionViewModel.getIcon() instanceof Animatable) {
                        ((Animatable) mediaActionViewModel.getIcon()).start();
                    }
                    if (mediaActionViewModel.getBackground() instanceof Animatable) {
                        ((Animatable) mediaActionViewModel.getBackground()).start();
                    }
                }
            });
        }
    }

    private final void bindGutsViewModel(final MediaViewHolder mediaViewHolder, final MediaPlayerViewModel mediaPlayerViewModel, final MediaViewController mediaViewController) {
        GutsViewHolder gutsViewHolder = mediaViewHolder.getGutsViewHolder();
        final GutsViewModel gutsMenu = mediaPlayerViewModel.getGutsMenu();
        gutsViewHolder.getGutsText().setText(gutsMenu.getGutsText());
        gutsViewHolder.getDismissText().setVisibility(gutsMenu.isDismissEnabled() ? 0 : 8);
        gutsViewHolder.getDismiss().setEnabled(gutsMenu.isDismissEnabled());
        gutsViewHolder.getDismiss().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindGutsViewModel$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                gutsMenu.getOnDismissClicked().mo2224invoke();
            }
        });
        gutsViewHolder.getCancelText().setBackground(gutsMenu.getCancelTextBackground());
        gutsViewHolder.getCancel().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindGutsViewModel$1$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaControlViewBinder.INSTANCE.closeGuts(mediaViewHolder, mediaViewController, mediaPlayerViewModel);
            }
        });
        gutsViewHolder.getSettings().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindGutsViewModel$1$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                gutsMenu.getOnSettingsClicked().mo2224invoke();
            }
        });
        gutsViewHolder.setDismissible(gutsMenu.isDismissEnabled());
        gutsViewHolder.setTextPrimaryColor(gutsMenu.getTextPrimaryColor());
        gutsViewHolder.setAccentPrimaryColor(gutsMenu.getAccentPrimaryColor());
        gutsViewHolder.setSurfaceColor(gutsMenu.getSurfaceColor());
    }

    private final void bindOutputSwitcherModel(MediaViewHolder mediaViewHolder, final MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel, MediaViewController mediaViewController) {
        ViewGroup seamless = mediaViewHolder.getSeamless();
        seamless.setVisibility(0);
        seamless.setEnabled(mediaOutputSwitcherViewModel.isTapEnabled());
        seamless.setContentDescription(mediaOutputSwitcherViewModel.getDeviceString());
        seamless.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$bindOutputSwitcherModel$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                mediaOutputSwitcherViewModel.getOnClicked().mo2224invoke();
            }
        });
        Icon deviceIcon = mediaOutputSwitcherViewModel.getDeviceIcon();
        if (deviceIcon instanceof Icon.Loaded) {
            mediaViewHolder.getSeamlessIcon().setImageDrawable(((Icon.Loaded) mediaOutputSwitcherViewModel.getDeviceIcon()).getDrawable());
        } else {
            if (!(deviceIcon instanceof Icon.Resource)) {
                throw new NoWhenBranchMatchedException();
            }
            mediaViewHolder.getSeamlessIcon().setImageResource(((Icon.Resource) mediaOutputSwitcherViewModel.getDeviceIcon()).getRes());
        }
        mediaViewHolder.getSeamlessButton().setAlpha(mediaOutputSwitcherViewModel.getAlpha());
        mediaViewHolder.getSeamlessText().setText(mediaOutputSwitcherViewModel.getDeviceString());
    }

    private final void bindScrubbingTime(MediaViewHolder mediaViewHolder, MediaPlayerViewModel mediaPlayerViewModel, MediaViewController mediaViewController) {
        ConstraintSet expandedLayout = mediaViewController.getExpandedLayout();
        boolean z = mediaPlayerViewModel.getCanShowTime() && mediaViewController.isScrubbing();
        mediaViewController.setCanShowScrubbingTime(mediaPlayerViewModel.getCanShowTime());
        setVisibleAndAlpha(expandedLayout, mediaViewHolder.getScrubbingElapsedTimeView().getId(), z);
        setVisibleAndAlpha(expandedLayout, mediaViewHolder.getScrubbingTotalTimeView().getId(), z);
    }

    private final boolean bindSongMetadata(final MediaViewHolder mediaViewHolder, final MediaPlayerViewModel mediaPlayerViewModel, final MediaViewController mediaViewController) {
        final ConstraintSet expandedLayout = mediaViewController.getExpandedLayout();
        final ConstraintSet collapsedLayout = mediaViewController.getCollapsedLayout();
        return mediaViewController.getMetadataAnimationHandler$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().setNext(new Triple(mediaPlayerViewModel.getTitleName(), mediaPlayerViewModel.getArtistName(), Boolean.valueOf(mediaPlayerViewModel.isExplicitVisible())), new Function0() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaControlViewBinder.bindSongMetadata$lambda$13(mediaViewHolder, mediaPlayerViewModel, expandedLayout, collapsedLayout, mediaViewController);
            }
        }, new Function0() { // from class: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return MediaControlViewBinder.bindSongMetadata$lambda$14(mediaViewController);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit bindSongMetadata$lambda$13(MediaViewHolder mediaViewHolder, MediaPlayerViewModel mediaPlayerViewModel, ConstraintSet constraintSet, ConstraintSet constraintSet2, MediaViewController mediaViewController) {
        mediaViewHolder.getTitleText().setText(mediaPlayerViewModel.getTitleName());
        mediaViewHolder.getArtistText().setText(mediaPlayerViewModel.getArtistName());
        MediaControlViewBinder mediaControlViewBinder = INSTANCE;
        int i = R$id.media_explicit_indicator;
        mediaControlViewBinder.setVisibleAndAlpha(constraintSet, i, mediaPlayerViewModel.isExplicitVisible());
        mediaControlViewBinder.setVisibleAndAlpha(constraintSet2, i, mediaPlayerViewModel.isExplicitVisible());
        mediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit bindSongMetadata$lambda$14(MediaViewController mediaViewController) {
        mediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    private final void clearButton(ImageButton imageButton) {
        imageButton.setImageDrawable(null);
        imageButton.setContentDescription(null);
        imageButton.setEnabled(false);
        imageButton.setBackground(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeGuts(MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, MediaPlayerViewModel mediaPlayerViewModel) {
        mediaViewHolder.marquee(false, MediaViewController.GUTS_ANIMATION_DURATION);
        mediaViewController.closeGuts(false);
        mediaViewHolder.getPlayer().setContentDescription((CharSequence) mediaPlayerViewModel.getContentDescription().invoke(Boolean.FALSE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RippleAnimation createTouchRippleAnimation(ImageButton imageButton, ColorSchemeTransition colorSchemeTransition, MultiRippleView multiRippleView) {
        float width = multiRippleView.getWidth() * 2;
        return new RippleAnimation(new RippleAnimationConfig(RippleShader.RippleShape.CIRCLE, 1500L, imageButton.getX() + (imageButton.getWidth() * 0.5f), (imageButton.getHeight() * 0.5f) + imageButton.getY(), width, width, imageButton.getContext().getResources().getDisplayMetrics().density, colorSchemeTransition.getAccentPrimary().getCurrentColor(), 100, 0.0f, null, null, null, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ColorMatrixColorFilter getGrayscaleFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openGuts(MediaViewHolder mediaViewHolder, MediaViewController mediaViewController, MediaPlayerViewModel mediaPlayerViewModel) {
        mediaViewHolder.marquee(true, MediaViewController.GUTS_ANIMATION_DURATION);
        mediaViewController.openGuts();
        mediaViewHolder.getPlayer().setContentDescription((CharSequence) mediaPlayerViewModel.getContentDescription().invoke(Boolean.TRUE));
        mediaPlayerViewModel.getOnLongClicked().mo2224invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scaleTransitionDrawableLayer(TransitionDrawable transitionDrawable, int i, int i2, int i3) {
        Drawable drawable = transitionDrawable.getDrawable(i);
        if (drawable == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        float scaleFactor = MediaDataUtils.getScaleFactor(new Pair(Integer.valueOf(intrinsicWidth), Integer.valueOf(intrinsicHeight)), new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
        if (scaleFactor == 0.0f) {
            return;
        }
        transitionDrawable.setLayerSize(i, (int) (intrinsicWidth * scaleFactor), (int) (scaleFactor * intrinsicHeight));
    }

    private final void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z, int i2) {
        if (z) {
            i2 = 0;
        }
        constraintSet.setVisibility(i, i2);
        constraintSet.setAlpha(i, z ? 1.0f : 0.0f);
    }

    public final void bind(MediaViewHolder mediaViewHolder, MediaControlViewModel mediaControlViewModel, MediaViewController mediaViewController, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, MediaFlags mediaFlags) {
        mediaViewHolder.getClass();
        mediaControlViewModel.getClass();
        mediaViewController.getClass();
        coroutineDispatcher.getClass();
        coroutineDispatcher2.getClass();
        mediaFlags.getClass();
        RepeatWhenAttachedKt.repeatWhenAttached$default(mediaViewHolder.getPlayer(), null, new AnonymousClass1(mediaControlViewModel, mediaViewHolder, mediaViewController, coroutineDispatcher, coroutineDispatcher2, mediaFlags, null), 1, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object bindMediaCard(final com.android.systemui.media.controls.ui.view.MediaViewHolder r11, final com.android.systemui.media.controls.ui.controller.MediaViewController r12, final com.android.systemui.media.controls.ui.viewmodel.MediaPlayerViewModel r13, kotlinx.coroutines.CoroutineDispatcher r14, kotlinx.coroutines.CoroutineDispatcher r15, com.android.systemui.media.controls.util.MediaFlags r16, kotlin.coroutines.Continuation r17) {
        /*
            Method dump skipped, instruction units count: 241
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.binder.MediaControlViewBinder.bindMediaCard(com.android.systemui.media.controls.ui.view.MediaViewHolder, com.android.systemui.media.controls.ui.controller.MediaViewController, com.android.systemui.media.controls.ui.viewmodel.MediaPlayerViewModel, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.media.controls.util.MediaFlags, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void setSemanticButtonVisibleAndAlpha(ImageButton imageButton, ConstraintSet constraintSet, ConstraintSet constraintSet2, boolean z, int i, boolean z2) {
        imageButton.getClass();
        constraintSet.getClass();
        constraintSet2.getClass();
        if (i == 4) {
            imageButton.setFocusable(z);
            imageButton.setClickable(z);
        }
        setVisibleAndAlpha(constraintSet, imageButton.getId(), z, i);
        setVisibleAndAlpha(constraintSet2, imageButton.getId(), z && z2);
    }

    public final void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        constraintSet.getClass();
        setVisibleAndAlpha(constraintSet, i, z, 8);
    }

    public final void updateSeekBarVisibility(ConstraintSet constraintSet, boolean z) {
        constraintSet.getClass();
        if (z) {
            int i = R$id.media_progress_bar;
            constraintSet.setVisibility(i, 0);
            constraintSet.setAlpha(i, 1.0f);
        } else {
            int i2 = R$id.media_progress_bar;
            constraintSet.setVisibility(i2, 4);
            constraintSet.setAlpha(i2, 0.0f);
        }
    }
}
