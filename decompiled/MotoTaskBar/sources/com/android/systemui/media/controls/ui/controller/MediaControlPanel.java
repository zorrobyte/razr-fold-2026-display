package com.android.systemui.media.controls.ui.controller;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceId;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Flags;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.animation.AnimationBindHandler;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.controls.util.SmallHash;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$anim;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.ripple.RippleAnimation;
import com.android.systemui.surfaceeffects.ripple.RippleAnimationConfig;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* JADX INFO: loaded from: classes.dex */
public class MediaControlPanel {
    private static final List SEMANTIC_ACTIONS_ALL;
    private static final List SEMANTIC_ACTIONS_COMPACT;
    private static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    private static final Intent SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    static final long TURBULENCE_NOISE_PLAY_DURATION = 7500;
    private final ActivityIntentHelper mActivityIntentHelper;
    private final ActivityStarter mActivityStarter;
    protected final Executor mBackgroundExecutor;
    private final BroadcastSender mBroadcastSender;
    private ColorSchemeTransition mColorSchemeTransition;
    private Context mContext;
    private MediaController mController;
    private final GlobalSettings mGlobalSettings;
    private InstanceId mInstanceId;
    private String mKey;
    private LoadingEffect mLoadingEffect;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private MediaUiEventLogger mLogger;
    private final DelayableExecutor mMainExecutor;
    private MediaCarouselController mMediaCarouselController;
    private MediaData mMediaData;
    private Lazy mMediaDataManagerLazy;
    private final MediaFlags mMediaFlags;
    private MediaViewController mMediaViewController;
    private MediaViewHolder mMediaViewHolder;
    private MetadataAnimationHandler mMetadataAnimationHandler;
    private MultiRippleController mMultiRippleController;
    private String mPackageName;
    private SeekBarObserver mSeekBarObserver;
    private final SeekBarViewModel mSeekBarViewModel;
    private SystemClock mSystemClock;
    private MediaSession.Token mToken;
    private TurbulenceNoiseAnimationConfig mTurbulenceNoiseAnimationConfig;
    private TurbulenceNoiseController mTurbulenceNoiseController;
    protected int mUid = -1;
    private Drawable mPrevArtwork = null;
    private boolean mIsArtworkBound = false;
    private int mArtworkBoundId = 0;
    private int mArtworkNextBindRequestId = 0;
    protected boolean mIsImpressed = false;
    protected int mSmartspaceId = -1;
    private boolean mIsScrubbing = false;
    private boolean mIsSeekBarEnabled = false;
    private final SeekBarViewModel.ScrubbingChangeListener mScrubbingChangeListener = new SeekBarViewModel.ScrubbingChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda5
        @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.ScrubbingChangeListener
        public final void onScrubbingChanged(boolean z) {
            this.f$0.setIsScrubbing(z);
        }
    };
    private final SeekBarViewModel.EnabledChangeListener mEnabledChangeListener = new SeekBarViewModel.EnabledChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda6
        @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.EnabledChangeListener
        public final void onEnabledChanged(boolean z) {
            this.f$0.setIsSeekBarEnabled(z);
        }
    };
    private boolean mWasPlaying = false;
    private boolean mButtonClicked = false;
    private final PaintDrawCallback mNoiseDrawCallback = new PaintDrawCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.1
        @Override // com.android.systemui.surfaceeffects.PaintDrawCallback
        public void onDraw(Paint paint) {
            MediaControlPanel.this.mMediaViewHolder.getLoadingEffectView().draw(paint);
        }
    };
    private final LoadingEffect.AnimationStateChangedCallback mStateChangedCallback = new LoadingEffect.AnimationStateChangedCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel.2
        @Override // com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.AnimationStateChangedCallback
        public void onStateChanged(LoadingEffect.AnimationState animationState, LoadingEffect.AnimationState animationState2) {
            LoadingEffectView loadingEffectView = MediaControlPanel.this.mMediaViewHolder.getLoadingEffectView();
            if (animationState2 == LoadingEffect.AnimationState.NOT_PLAYING) {
                loadingEffectView.setVisibility(4);
            } else {
                loadingEffectView.setVisibility(0);
            }
        }
    };

    public static /* synthetic */ boolean $r8$lambda$d563sGnzbmM59Y8HBXjmtqehmXU(MediaButton mediaButton, Integer num) {
        return mediaButton.getActionById(num.intValue()) != null;
    }

    static {
        int i = R$id.actionPlayPause;
        Integer numValueOf = Integer.valueOf(i);
        int i2 = R$id.actionPrev;
        Integer numValueOf2 = Integer.valueOf(i2);
        int i3 = R$id.actionNext;
        SEMANTIC_ACTIONS_COMPACT = List.of(numValueOf, numValueOf2, Integer.valueOf(i3));
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = List.of(Integer.valueOf(i2), Integer.valueOf(i3));
        SEMANTIC_ACTIONS_ALL = List.of(Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(R$id.action0), Integer.valueOf(R$id.action1));
    }

    public MediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy lazy, MediaCarouselController mediaCarouselController, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, GlobalSettings globalSettings, MediaFlags mediaFlags) {
        this.mContext = context;
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = delayableExecutor;
        this.mActivityStarter = activityStarter;
        this.mBroadcastSender = broadcastSender;
        this.mSeekBarViewModel = seekBarViewModel;
        this.mMediaViewController = mediaViewController;
        this.mMediaDataManagerLazy = lazy;
        this.mMediaCarouselController = mediaCarouselController;
        this.mSystemClock = systemClock;
        this.mLogger = mediaUiEventLogger;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mMediaFlags = mediaFlags;
        seekBarViewModel.setLogSeek(new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return this.f$0.lambda$new$0();
            }
        });
        this.mGlobalSettings = globalSettings;
        updateAnimatorDurationScale();
    }

    private void bindActionButtons(MediaData mediaData) {
        MediaControlPanel mediaControlPanel;
        MediaButton semanticActions = mediaData.getSemanticActions();
        ArrayList arrayList = new ArrayList();
        Iterator it = MediaViewHolder.Companion.getGenericButtonIds().iterator();
        while (it.hasNext()) {
            arrayList.add(this.mMediaViewHolder.getAction(((Integer) it.next()).intValue()));
        }
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        ConstraintSet collapsedLayout = this.mMediaViewController.getCollapsedLayout();
        int i = 0;
        if (semanticActions != null) {
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ImageButton imageButton = (ImageButton) obj;
                setVisibleAndAlpha(collapsedLayout, imageButton.getId(), false);
                setVisibleAndAlpha(expandedLayout, imageButton.getId(), false);
            }
            Iterator it2 = SEMANTIC_ACTIONS_ALL.iterator();
            while (it2.hasNext()) {
                int iIntValue = ((Integer) it2.next()).intValue();
                setSemanticButton(this.mMediaViewHolder.getAction(iIntValue), semanticActions.getActionById(iIntValue), semanticActions);
            }
            mediaControlPanel = this;
        } else {
            Iterator it3 = SEMANTIC_ACTIONS_COMPACT.iterator();
            while (it3.hasNext()) {
                int iIntValue2 = ((Integer) it3.next()).intValue();
                setVisibleAndAlpha(collapsedLayout, iIntValue2, false);
                setVisibleAndAlpha(expandedLayout, iIntValue2, false);
            }
            List actionsToShowInCompact = mediaData.getActionsToShowInCompact();
            List actions = mediaData.getActions();
            while (i < actions.size() && i < arrayList.size()) {
                setGenericButton((ImageButton) arrayList.get(i), (MediaAction) actions.get(i), collapsedLayout, expandedLayout, actionsToShowInCompact.contains(Integer.valueOf(i)));
                i++;
            }
            mediaControlPanel = this;
            while (i < arrayList.size()) {
                mediaControlPanel.setGenericButton((ImageButton) arrayList.get(i), null, collapsedLayout, expandedLayout, false);
                i++;
            }
        }
        mediaControlPanel.updateSeekBarVisibility();
    }

    private void bindArtworkAndColors(final MediaData mediaData, String str, final boolean z) {
        final int iHashCode = mediaData.hashCode();
        final String str2 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
        Trace.beginAsyncSection(str2, iHashCode);
        final int i = this.mArtworkNextBindRequestId;
        this.mArtworkNextBindRequestId = i + 1;
        if (z) {
            this.mIsArtworkBound = false;
        }
        int measuredWidth = this.mMediaViewHolder.getAlbumView().getMeasuredWidth();
        int measuredHeight = this.mMediaViewHolder.getAlbumView().getMeasuredHeight();
        if (this.mMediaFlags.isSceneContainerEnabled() && (measuredWidth <= 0 || measuredHeight <= 0)) {
            measuredWidth = this.mMediaViewController.getWidthInSceneContainerPx();
            measuredHeight = this.mMediaViewController.getHeightInSceneContainerPx();
        }
        final int i2 = measuredWidth;
        final int i3 = measuredHeight;
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bindArtworkAndColors$10(mediaData, i2, i3, i, str2, iHashCode, z);
            }
        });
    }

    private void bindButtonCommon(final ImageButton imageButton, MediaAction mediaAction) {
        if (mediaAction == null) {
            clearButton(imageButton);
            return;
        }
        final Drawable icon = mediaAction.getIcon();
        imageButton.setImageDrawable(icon);
        imageButton.setContentDescription(mediaAction.getContentDescription());
        final Drawable background = mediaAction.getBackground();
        imageButton.setBackground(background);
        final Runnable action = mediaAction.getAction();
        if (action == null) {
            imageButton.setEnabled(false);
        } else {
            imageButton.setEnabled(true);
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$bindButtonCommon$12(imageButton, action, icon, background, view);
                }
            });
        }
    }

    private void bindButtonWithAnimations(ImageButton imageButton, MediaAction mediaAction, AnimationBindHandler animationBindHandler) {
        if (mediaAction == null) {
            animationBindHandler.unregisterAll();
            clearButton(imageButton);
        } else if (animationBindHandler.updateRebindId(mediaAction.getRebindId())) {
            animationBindHandler.unregisterAll();
            animationBindHandler.tryRegister(mediaAction.getIcon());
            animationBindHandler.tryRegister(mediaAction.getBackground());
            bindButtonCommon(imageButton, mediaAction);
        }
    }

    private void bindGutsMenuCommon(boolean z, String str, GutsViewHolder gutsViewHolder, final Runnable runnable) {
        gutsViewHolder.getGutsText().setText(z ? this.mContext.getString(R$string.controls_media_close_session, str) : this.mContext.getString(R$string.controls_media_active_session));
        gutsViewHolder.getDismissText().setVisibility(z ? 0 : 8);
        gutsViewHolder.getDismiss().setEnabled(z);
        gutsViewHolder.getDismiss().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$bindGutsMenuCommon$15(runnable, view);
            }
        });
        TextView cancelText = gutsViewHolder.getCancelText();
        if (z) {
            cancelText.setBackground(this.mContext.getDrawable(R$drawable.qs_media_outline_button));
        } else {
            cancelText.setBackground(this.mContext.getDrawable(R$drawable.qs_media_solid_button));
        }
        gutsViewHolder.getCancel().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$bindGutsMenuCommon$16(view);
            }
        });
        gutsViewHolder.setDismissible(z);
        gutsViewHolder.getSettings().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$bindGutsMenuCommon$17(view);
            }
        });
    }

    private void bindGutsMenuForPlayer(final MediaData mediaData) {
        bindGutsMenuCommon(mediaData.isClearable(), mediaData.getApp(), this.mMediaViewHolder.getGutsViewHolder(), new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bindGutsMenuForPlayer$6(mediaData);
            }
        });
    }

    private void bindOutputSwitcherAndBroadcastButton(MediaData mediaData) {
        ViewGroup seamless = this.mMediaViewHolder.getSeamless();
        seamless.setVisibility(0);
        ImageView seamlessIcon = this.mMediaViewHolder.getSeamlessIcon();
        TextView seamlessText = this.mMediaViewHolder.getSeamlessText();
        MediaDeviceData device = mediaData.getDevice();
        boolean z = !(device == null || device.getEnabled()) || mediaData.getResumption();
        boolean z2 = !z;
        CharSequence string = this.mContext.getString(R$string.media_seamless_other_device);
        int i = R$drawable.ic_media_home_devices;
        this.mMediaViewHolder.getSeamlessButton().setAlpha(z ? 0.38f : 1.0f);
        seamless.setEnabled(z2);
        if (device != null) {
            seamlessIcon.setImageDrawable(device.getIcon());
            if (device.getName() != null) {
                string = device.getName();
            }
        } else {
            seamlessIcon.setImageResource(i);
        }
        seamlessText.setText(string);
        seamless.setContentDescription(string);
        seamless.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DesktopFocusAudioOutputMonitor.startDesktopMediaRouteActivity(view.getContext(), view.getContext().getDisplayId());
            }
        });
    }

    private void bindPlayerContentDescription(MediaData mediaData) {
        if (this.mMediaViewHolder == null) {
            return;
        }
        this.mMediaViewHolder.getPlayer().setContentDescription(this.mMediaViewController.isGutsVisible() ? this.mMediaViewHolder.getGutsViewHolder().getGutsText().getText() : mediaData != null ? this.mContext.getString(R$string.controls_media_playing_item_description, mediaData.getSong(), mediaData.getArtist(), mediaData.getApp()) : null);
    }

    private void bindScrubbingTime(MediaData mediaData) {
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        int id = this.mMediaViewHolder.getScrubbingElapsedTimeView().getId();
        int id2 = this.mMediaViewHolder.getScrubbingTotalTimeView().getId();
        boolean z = scrubbingTimeViewsEnabled(mediaData.getSemanticActions()) && this.mIsScrubbing;
        setVisibleAndAlpha(expandedLayout, id, z);
        setVisibleAndAlpha(expandedLayout, id2, z);
    }

    private boolean bindSongMetadata(final MediaData mediaData) {
        final TextView titleText = this.mMediaViewHolder.getTitleText();
        final TextView artistText = this.mMediaViewHolder.getArtistText();
        final ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        final ConstraintSet collapsedLayout = this.mMediaViewController.getCollapsedLayout();
        return this.mMetadataAnimationHandler.setNext(new Triple(mediaData.getSong(), mediaData.getArtist(), Boolean.valueOf(mediaData.isExplicit())), new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return this.f$0.lambda$bindSongMetadata$7(titleText, mediaData, artistText, expandedLayout, collapsedLayout);
            }
        }, new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return this.f$0.lambda$bindSongMetadata$8();
            }
        });
    }

    private void clearButton(ImageButton imageButton) {
        imageButton.setImageDrawable(null);
        imageButton.setContentDescription(null);
        imageButton.setEnabled(false);
        imageButton.setBackground(null);
    }

    private void closeGuts() {
        closeGuts(false);
    }

    private RippleAnimation createTouchRippleAnimation(ImageButton imageButton) {
        float width = this.mMediaViewHolder.getMultiRippleView().getWidth() * 2;
        return new RippleAnimation(new RippleAnimationConfig(RippleShader.RippleShape.CIRCLE, 1500L, imageButton.getX() + (imageButton.getWidth() * 0.5f), (imageButton.getHeight() * 0.5f) + imageButton.getY(), width, width, getContext().getResources().getDisplayMetrics().density, this.mColorSchemeTransition.getAccentPrimary().getCurrentColor(), 100, 0.0f, null, null, null, false));
    }

    private TurbulenceNoiseAnimationConfig createTurbulenceNoiseConfig() {
        View loadingEffectView = Flags.shaderlibLoadingEffectRefactor() ? this.mMediaViewHolder.getLoadingEffectView() : this.mMediaViewHolder.getTurbulenceNoiseView();
        int width = loadingEffectView.getWidth();
        int height = loadingEffectView.getHeight();
        Random random = new Random();
        return new TurbulenceNoiseAnimationConfig(2.14f, 1.0f, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.42f, 0.0f, 0.3f, this.mColorSchemeTransition.getAccentPrimary().getCurrentColor(), DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT, width, height, 30000.0f, 1350.0f, 1350.0f, getContext().getResources().getDisplayMetrics().density, 0.26f, 0.09f, false);
    }

    private ColorMatrixColorFilter getGrayscaleFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    private Drawable getScaledBackground(Icon icon, int i, int i2) {
        if (icon == null) {
            return null;
        }
        Drawable drawableLoadDrawable = icon.loadDrawable(this.mContext);
        Rect rect = new Rect(0, 0, i, i2);
        if (rect.width() > i || rect.height() > i2) {
            rect.offset((int) (-((rect.width() - i) / 2.0f)), (int) (-((rect.height() - i2) / 2.0f)));
        }
        drawableLoadDrawable.setBounds(rect);
        return drawableLoadDrawable;
    }

    private Drawable getScaledRecommendationCover(Icon icon, int i, int i2) {
        if (i == 0 || i2 == 0 || icon == null || (!(icon.getType() == 1 || icon.getType() == 5) || icon.getBitmap() == null)) {
            return null;
        }
        return new BitmapDrawable(this.mContext.getResources(), Bitmap.createScaledBitmap(icon.getBitmap(), i, i2, false));
    }

    private int getSeekBarVisibility() {
        return this.mIsSeekBarEnabled ? 0 : 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$attachPlayer$2(View view) {
        if (this.mMediaViewController.isGutsVisible()) {
            closeGuts();
            return true;
        }
        openGuts();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindArtworkAndColors$10(final MediaData mediaData, final int i, int i2, final int i3, final String str, final int i4, final boolean z) {
        final int i5;
        final ColorScheme colorScheme;
        final Drawable drawableAddGradientToPlayerAlbum;
        final boolean z2;
        Icon artwork = mediaData.getArtwork();
        WallpaperColors wallpaperColor = getWallpaperColor(artwork);
        if (wallpaperColor != null) {
            ColorScheme colorScheme2 = new ColorScheme(wallpaperColor, true, Style.CONTENT);
            i5 = i2;
            drawableAddGradientToPlayerAlbum = addGradientToPlayerAlbum(artwork, colorScheme2, i, i5);
            z2 = true;
            colorScheme = colorScheme2;
        } else {
            i5 = i2;
            ColorDrawable colorDrawable = new ColorDrawable(0);
            try {
                colorScheme = new ColorScheme(WallpaperColors.fromDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData.getPackageName())), true, Style.CONTENT);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaControlPanel", "Cannot find icon for package " + mediaData.getPackageName(), e);
                colorScheme = null;
            }
            drawableAddGradientToPlayerAlbum = colorDrawable;
            z2 = false;
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bindArtworkAndColors$9(i3, str, i4, colorScheme, z, z2, drawableAddGradientToPlayerAlbum, i, i5, mediaData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindArtworkAndColors$9(int i, String str, int i2, ColorScheme colorScheme, boolean z, boolean z2, Drawable drawable, int i3, int i4, MediaData mediaData) {
        if (i < this.mArtworkBoundId) {
            Trace.endAsyncSection(str, i2);
            return;
        }
        this.mArtworkBoundId = i;
        boolean zUpdateColorScheme = this.mColorSchemeTransition.updateColorScheme(colorScheme);
        ImageView albumView = this.mMediaViewHolder.getAlbumView();
        albumView.setPadding(0, 0, 0, 0);
        if (z || zUpdateColorScheme || (!this.mIsArtworkBound && z2)) {
            if (this.mPrevArtwork == null) {
                albumView.setImageDrawable(drawable);
            } else {
                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{this.mPrevArtwork, drawable});
                scaleTransitionDrawableLayer(transitionDrawable, 0, i3, i4);
                scaleTransitionDrawableLayer(transitionDrawable, 1, i3, i4);
                transitionDrawable.setLayerGravity(0, 17);
                transitionDrawable.setLayerGravity(1, 17);
                transitionDrawable.setCrossFadeEnabled(true);
                albumView.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(z2 ? 333 : 80);
            }
            this.mPrevArtwork = drawable;
            this.mIsArtworkBound = z2;
        }
        ImageView appIcon = this.mMediaViewHolder.getAppIcon();
        appIcon.clearColorFilter();
        if (mediaData.getAppIcon() == null || mediaData.getResumption()) {
            appIcon.setColorFilter(getGrayscaleFilter());
            try {
                appIcon.setImageDrawable(this.mContext.getPackageManager().getApplicationIcon(mediaData.getPackageName()));
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaControlPanel", "Cannot find icon for package " + mediaData.getPackageName(), e);
                appIcon.setImageResource(com.motorola.taskbar.R$drawable.ic_music_note);
            }
        } else {
            appIcon.setImageIcon(mediaData.getAppIcon());
            appIcon.setColorFilter(this.mColorSchemeTransition.getAccentPrimary().getTargetColor());
        }
        Trace.endAsyncSection(str, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$bindButtonCommon$12(ImageButton imageButton, Runnable runnable, Drawable drawable, Drawable drawable2, View view) {
        this.mLogger.logTapAction(imageButton.getId(), this.mUid, this.mPackageName, this.mInstanceId);
        this.mWasPlaying = isPlaying();
        this.mButtonClicked = true;
        runnable.run();
        this.mMultiRippleController.play(createTouchRippleAnimation(imageButton));
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        if (drawable2 instanceof Animatable) {
            ((Animatable) drawable2).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindGutsMenuCommon$15(Runnable runnable, View view) {
        this.mLogger.logLongPressDismiss(this.mUid, this.mPackageName, this.mInstanceId);
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindGutsMenuCommon$16(View view) {
        closeGuts();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindGutsMenuCommon$17(View view) {
        this.mLogger.logLongPressSettings(this.mUid, this.mPackageName, this.mInstanceId);
        this.mActivityStarter.startActivity(SETTINGS_INTENT, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindGutsMenuForPlayer$6(MediaData mediaData) {
        if (this.mKey == null) {
            Log.w("MediaControlPanel", "Dismiss media with null notification. Token uid=" + mediaData.getToken().getUid());
            return;
        }
        closeGuts();
        if (((MediaDataManager) this.mMediaDataManagerLazy.get()).dismissMediaData(this.mKey, MediaViewController.GUTS_ANIMATION_DURATION + 100)) {
            return;
        }
        Log.w("MediaControlPanel", "Manager failed to dismiss media " + this.mKey);
        this.mMediaCarouselController.removePlayer(this.mKey, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindPlayer$3(PendingIntent pendingIntent, String str, View view) {
        if (this.mMediaViewController.isGutsVisible()) {
            return;
        }
        this.mLogger.logTapContentView(this.mUid, this.mPackageName, this.mInstanceId);
        this.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindPlayer$4(MediaController mediaController) {
        this.mSeekBarViewModel.updateController(mediaController);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$bindSongMetadata$7(TextView textView, MediaData mediaData, TextView textView2, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
        textView.setText(mediaData.getSong());
        textView2.setText(mediaData.getArtist());
        int i = R$id.media_explicit_indicator;
        setVisibleAndAlpha(constraintSet, i, mediaData.isExplicit());
        setVisibleAndAlpha(constraintSet2, i, mediaData.isExplicit());
        this.mMediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$bindSongMetadata$8() {
        this.mMediaViewController.refreshState();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$new$0() {
        InstanceId instanceId;
        String str = this.mPackageName;
        if (str != null && (instanceId = this.mInstanceId) != null) {
            this.mLogger.logSeek(this.mUid, str, instanceId);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsScrubbing$1() {
        updateDisplayForScrubbingChange(this.mMediaData.getSemanticActions());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$setSemanticButton$11(ImageButton imageButton, MediaAction mediaAction, AnimationBindHandler animationBindHandler, MediaButton mediaButton) {
        bindButtonWithAnimations(imageButton, mediaAction, animationBindHandler);
        setSemanticButtonVisibleAndAlpha(imageButton.getId(), mediaAction, mediaButton);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDisplayForScrubbingChange$13(MediaButton mediaButton, Integer num) {
        setSemanticButtonVisibleAndAlpha(num.intValue(), mediaButton.getActionById(num.intValue()), mediaButton);
    }

    private void openGuts() {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(true, MediaViewController.GUTS_ANIMATION_DURATION);
        }
        this.mMediaViewController.openGuts();
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        }
        this.mLogger.logLongPressOpen(this.mUid, this.mPackageName, this.mInstanceId);
    }

    private void scaleTransitionDrawableLayer(TransitionDrawable transitionDrawable, int i, int i2, int i3) {
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

    private boolean scrubbingTimeViewsEnabled(final MediaButton mediaButton) {
        return mediaButton != null && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.stream().allMatch(new Predicate() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaControlPanel.$r8$lambda$d563sGnzbmM59Y8HBXjmtqehmXU(mediaButton, (Integer) obj);
            }
        });
    }

    private void setGenericButton(ImageButton imageButton, MediaAction mediaAction, ConstraintSet constraintSet, ConstraintSet constraintSet2, boolean z) {
        bindButtonCommon(imageButton, mediaAction);
        boolean z2 = false;
        boolean z3 = mediaAction != null;
        setVisibleAndAlpha(constraintSet2, imageButton.getId(), z3);
        int id = imageButton.getId();
        if (z3 && z) {
            z2 = true;
        }
        setVisibleAndAlpha(constraintSet, id, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsScrubbing(boolean z) {
        MediaData mediaData = this.mMediaData;
        if (mediaData == null || mediaData.getSemanticActions() == null || z == this.mIsScrubbing) {
            return;
        }
        this.mIsScrubbing = z;
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setIsScrubbing$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsSeekBarEnabled(boolean z) {
        if (z == this.mIsSeekBarEnabled) {
            return;
        }
        this.mIsSeekBarEnabled = z;
        updateSeekBarVisibility();
    }

    private void setSemanticButton(final ImageButton imageButton, final MediaAction mediaAction, final MediaButton mediaButton) {
        AnimationBindHandler animationBindHandler;
        if (imageButton.getTag() == null) {
            animationBindHandler = new AnimationBindHandler();
            imageButton.setTag(animationBindHandler);
        } else {
            animationBindHandler = (AnimationBindHandler) imageButton.getTag();
        }
        final AnimationBindHandler animationBindHandler2 = animationBindHandler;
        animationBindHandler2.tryExecute(new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return this.f$0.lambda$setSemanticButton$11(imageButton, mediaAction, animationBindHandler2, mediaButton);
            }
        });
    }

    private void setSemanticButtonVisibleAndAlpha(int i, MediaAction mediaAction, MediaButton mediaButton) {
        int i2;
        ConstraintSet collapsedLayout = this.mMediaViewController.getCollapsedLayout();
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        boolean zContains = SEMANTIC_ACTIONS_COMPACT.contains(Integer.valueOf(i));
        boolean z = false;
        boolean z2 = (mediaAction == null || (scrubbingTimeViewsEnabled(mediaButton) && SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.contains(Integer.valueOf(i)) && this.mIsScrubbing)) ? false : true;
        if ((i == R$id.actionPrev && mediaButton.getReservePrev()) || (i == R$id.actionNext && mediaButton.getReserveNext())) {
            this.mMediaViewHolder.getAction(i).setFocusable(z2);
            this.mMediaViewHolder.getAction(i).setClickable(z2);
            i2 = 4;
        } else {
            i2 = 8;
        }
        setVisibleAndAlpha(expandedLayout, i, z2, i2);
        if (z2 && zContains) {
            z = true;
        }
        setVisibleAndAlpha(collapsedLayout, i, z);
    }

    private void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z) {
        setVisibleAndAlpha(constraintSet, i, z, 8);
    }

    private void setVisibleAndAlpha(ConstraintSet constraintSet, int i, boolean z, int i2) {
        if (z) {
            i2 = 0;
        }
        constraintSet.setVisibility(i, i2);
        constraintSet.setAlpha(i, z ? 1.0f : 0.0f);
    }

    private LayerDrawable setupGradientColorOnDrawable(Drawable drawable, GradientDrawable gradientDrawable, ColorScheme colorScheme, float f, float f2) {
        gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundStartFromScheme(colorScheme), f), ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundEndFromScheme(colorScheme), f2)});
        return new LayerDrawable(new Drawable[]{drawable, gradientDrawable});
    }

    private boolean shouldPlayTurbulenceNoise() {
        return this.mButtonClicked && !this.mWasPlaying && isPlaying();
    }

    private void updateDisplayForScrubbingChange(final MediaButton mediaButton) {
        bindScrubbingTime(this.mMediaData);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$updateDisplayForScrubbingChange$13(mediaButton, (Integer) obj);
            }
        });
        if (this.mMetadataAnimationHandler.isRunning()) {
            return;
        }
        this.mMediaViewController.refreshState();
    }

    private void updateSeekBarVisibility() {
        ConstraintSet expandedLayout = this.mMediaViewController.getExpandedLayout();
        int i = R$id.media_progress_bar;
        expandedLayout.setVisibility(i, getSeekBarVisibility());
        expandedLayout.setAlpha(i, this.mIsSeekBarEnabled ? 1.0f : 0.0f);
    }

    protected LayerDrawable addGradientToPlayerAlbum(Icon icon, ColorScheme colorScheme, int i, int i2) {
        return setupGradientColorOnDrawable(getScaledBackground(icon, i, i2), (GradientDrawable) this.mContext.getDrawable(R$drawable.qs_media_scrim).mutate(), colorScheme, 0.25f, 1.0f);
    }

    protected LayerDrawable addGradientToRecommendationAlbum(Icon icon, ColorScheme colorScheme, int i, int i2) {
        Drawable scaledRecommendationCover = getScaledRecommendationCover(icon, i, i2);
        if (scaledRecommendationCover == null) {
            scaledRecommendationCover = getScaledBackground(icon, i, i2);
        }
        return setupGradientColorOnDrawable(scaledRecommendationCover, (GradientDrawable) this.mContext.getDrawable(R$drawable.qs_media_rec_scrim).mutate(), colorScheme, 0.15f, 1.0f);
    }

    public void attachPlayer(MediaViewHolder mediaViewHolder) {
        this.mMediaViewHolder = mediaViewHolder;
        TransitionLayout player = mediaViewHolder.getPlayer();
        this.mSeekBarObserver = new SeekBarObserver(mediaViewHolder);
        this.mSeekBarViewModel.getProgress().observeForever(this.mSeekBarObserver);
        this.mSeekBarViewModel.attachTouchHandlers(mediaViewHolder.getSeekBar());
        this.mSeekBarViewModel.setScrubbingChangeListener(this.mScrubbingChangeListener);
        this.mSeekBarViewModel.setEnabledChangeListener(this.mEnabledChangeListener);
        this.mMediaViewController.attach(player, MediaViewController.TYPE.PLAYER);
        mediaViewHolder.getPlayer().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$attachPlayer$2(view);
            }
        });
        this.mMediaViewHolder.getAlbumView().setLayerType(2, null);
        TextView titleText = this.mMediaViewHolder.getTitleText();
        TextView artistText = this.mMediaViewHolder.getArtistText();
        CachingIconView explicitIndicator = this.mMediaViewHolder.getExplicitIndicator();
        AnimatorSet animatorSetLoadAnimator = loadAnimator(R$anim.media_metadata_enter, Interpolators.EMPHASIZED_DECELERATE, titleText, artistText, explicitIndicator);
        AnimatorSet animatorSetLoadAnimator2 = loadAnimator(R$anim.media_metadata_exit, Interpolators.EMPHASIZED_ACCELERATE, titleText, artistText, explicitIndicator);
        this.mMultiRippleController = new MultiRippleController(mediaViewHolder.getMultiRippleView());
        TurbulenceNoiseView turbulenceNoiseView = mediaViewHolder.getTurbulenceNoiseView();
        BlendMode blendMode = BlendMode.SCREEN;
        turbulenceNoiseView.setBlendMode(blendMode);
        LoadingEffectView loadingEffectView = mediaViewHolder.getLoadingEffectView();
        loadingEffectView.setBlendMode(blendMode);
        loadingEffectView.setVisibility(4);
        TurbulenceNoiseController turbulenceNoiseController = new TurbulenceNoiseController(turbulenceNoiseView);
        this.mTurbulenceNoiseController = turbulenceNoiseController;
        this.mColorSchemeTransition = new ColorSchemeTransition(this.mContext, this.mMediaViewHolder, this.mMultiRippleController, turbulenceNoiseController);
        this.mMetadataAnimationHandler = new MetadataAnimationHandler(animatorSetLoadAnimator2, animatorSetLoadAnimator);
    }

    public void bindPlayer(MediaData mediaData, final String str) {
        if (this.mMediaViewHolder == null) {
            return;
        }
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "MediaControlPanel#bindPlayer<" + str + ">");
        }
        this.mKey = str;
        this.mMediaData = mediaData;
        MediaSession.Token token = mediaData.getToken();
        this.mPackageName = mediaData.getPackageName();
        int appUid = mediaData.getAppUid();
        this.mUid = appUid;
        if (this.mSmartspaceId == -1) {
            this.mSmartspaceId = SmallHash.hash(appUid + ((int) this.mSystemClock.currentTimeMillis()));
        }
        this.mInstanceId = mediaData.getInstanceId();
        MediaSession.Token token2 = this.mToken;
        if (token2 == null || !token2.equals(token)) {
            this.mToken = token;
        }
        if (this.mToken != null) {
            this.mController = new MediaController(this.mContext, this.mToken);
        } else {
            this.mController = null;
        }
        final PendingIntent clickIntent = mediaData.getClickIntent();
        if (clickIntent != null) {
            this.mMediaViewHolder.getPlayer().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$bindPlayer$3(clickIntent, str, view);
                }
            });
        }
        if (!mediaData.getResumption() || mediaData.getResumeProgress() == null) {
            final MediaController controller = getController();
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bindPlayer$4(controller);
                }
            });
        } else {
            this.mSeekBarViewModel.updateStaticProgress(mediaData.getResumeProgress().doubleValue());
        }
        bindOutputSwitcherAndBroadcastButton(mediaData);
        bindGutsMenuForPlayer(mediaData);
        bindPlayerContentDescription(mediaData);
        bindScrubbingTime(mediaData);
        bindActionButtons(mediaData);
        bindArtworkAndColors(mediaData, str, bindSongMetadata(mediaData));
        if (!this.mMetadataAnimationHandler.isRunning() && !this.mMediaFlags.isSceneContainerEnabled()) {
            this.mMediaViewController.refreshState();
        }
        if (shouldPlayTurbulenceNoise()) {
            if (this.mTurbulenceNoiseAnimationConfig == null) {
                this.mTurbulenceNoiseAnimationConfig = createTurbulenceNoiseConfig();
            }
            if (Flags.shaderlibLoadingEffectRefactor()) {
                if (this.mLoadingEffect == null) {
                    LoadingEffect loadingEffect = new LoadingEffect(TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE, this.mTurbulenceNoiseAnimationConfig, this.mNoiseDrawCallback, this.mStateChangedCallback);
                    this.mLoadingEffect = loadingEffect;
                    this.mColorSchemeTransition.setLoadingEffect(loadingEffect);
                }
                this.mLoadingEffect.play();
                DelayableExecutor delayableExecutor = this.mMainExecutor;
                final LoadingEffect loadingEffect2 = this.mLoadingEffect;
                loadingEffect2.getClass();
                delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        loadingEffect2.finish();
                    }
                }, TURBULENCE_NOISE_PLAY_DURATION);
            } else {
                this.mTurbulenceNoiseController.play(TurbulenceNoiseShader.Companion.Type.SIMPLEX_NOISE, this.mTurbulenceNoiseAnimationConfig);
                DelayableExecutor delayableExecutor2 = this.mMainExecutor;
                final TurbulenceNoiseController turbulenceNoiseController = this.mTurbulenceNoiseController;
                turbulenceNoiseController.getClass();
                delayableExecutor2.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        turbulenceNoiseController.finish();
                    }
                }, TURBULENCE_NOISE_PLAY_DURATION);
            }
        }
        this.mButtonClicked = false;
        this.mWasPlaying = isPlaying();
        Trace.endSection();
    }

    public void closeGuts(boolean z) {
        MediaViewHolder mediaViewHolder = this.mMediaViewHolder;
        if (mediaViewHolder != null) {
            mediaViewHolder.marquee(false, MediaViewController.GUTS_ANIMATION_DURATION);
        }
        this.mMediaViewController.closeGuts(z);
        if (this.mMediaViewHolder != null) {
            bindPlayerContentDescription(this.mMediaData);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public MediaController getController() {
        return this.mController;
    }

    public boolean getListening() {
        return this.mSeekBarViewModel.getListening();
    }

    public MediaViewController getMediaViewController() {
        return this.mMediaViewController;
    }

    public MediaViewHolder getMediaViewHolder() {
        return this.mMediaViewHolder;
    }

    protected WallpaperColors getWallpaperColor(Icon icon) {
        if (icon != null) {
            if (icon.getType() == 1 || icon.getType() == 5) {
                Bitmap bitmap = icon.getBitmap();
                if (!bitmap.isRecycled()) {
                    return WallpaperColors.fromBitmap(bitmap);
                }
                Log.d("MediaControlPanel", "Cannot load wallpaper color from a recycled bitmap");
                return null;
            }
            Drawable drawableLoadDrawable = icon.loadDrawable(this.mContext);
            if (drawableLoadDrawable != null) {
                return WallpaperColors.fromDrawable(drawableLoadDrawable);
            }
        }
        return null;
    }

    public boolean isPlaying() {
        return isPlaying(this.mController);
    }

    protected boolean isPlaying(MediaController mediaController) {
        PlaybackState playbackState;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    protected AnimatorSet loadAnimator(int i, Interpolator interpolator, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        for (View view : viewArr) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this.mContext, i);
            animatorSet.getChildAnimations().get(0).setInterpolator(interpolator);
            animatorSet.setTarget(view);
            arrayList.add(animatorSet);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(arrayList);
        return animatorSet2;
    }

    public void onDestroy() {
        if (this.mSeekBarObserver != null) {
            this.mSeekBarViewModel.getProgress().removeObserver(this.mSeekBarObserver);
        }
        this.mSeekBarViewModel.removeScrubbingChangeListener(this.mScrubbingChangeListener);
        this.mSeekBarViewModel.removeEnabledChangeListener(this.mEnabledChangeListener);
        this.mSeekBarViewModel.onDestroy();
        this.mMediaViewController.onDestroy();
    }

    public void setListening(boolean z) {
        this.mSeekBarViewModel.setListening(z);
    }

    void updateAnimatorDurationScale() {
        SeekBarObserver seekBarObserver = this.mSeekBarObserver;
        if (seekBarObserver != null) {
            seekBarObserver.setAnimationEnabled(this.mGlobalSettings.getFloat("animator_duration_scale", 1.0f) > 0.0f);
        }
    }
}
