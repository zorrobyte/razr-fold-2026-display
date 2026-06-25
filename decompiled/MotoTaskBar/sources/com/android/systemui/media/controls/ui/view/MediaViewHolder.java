package com.android.systemui.media.controls.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.animation.TransitionLayout;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: MediaViewHolder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaViewHolder {
    public static final Companion Companion = new Companion(null);
    private static final Set backgroundIds;
    private static final Set controlsIds;
    private static final Set detailIds;
    private static final Set expandedBottomActionIds;
    private static final Set genericButtonIds;
    private final ImageButton action0;
    private final ImageButton action1;
    private final ImageButton action2;
    private final ImageButton action3;
    private final ImageButton action4;
    private final ImageButton actionNext;
    private final ImageButton actionPlayPause;
    private final ImageButton actionPrev;
    private final Barrier actionsTopBarrier;
    private final ImageView albumView;
    private final ImageView appIcon;
    private final TextView artistText;
    private final CachingIconView explicitIndicator;
    private final GutsViewHolder gutsViewHolder;
    private final LoadingEffectView loadingEffectView;
    private final MultiRippleView multiRippleView;
    private final TransitionLayout player;
    private final TextView scrubbingElapsedTimeView;
    private final TextView scrubbingTotalTimeView;
    private final ViewGroup seamless;
    private final View seamlessButton;
    private final ImageView seamlessIcon;
    private final TextView seamlessText;
    private final SeekBar seekBar;
    private final TextView titleText;
    private final TurbulenceNoiseView turbulenceNoiseView;

    /* JADX INFO: compiled from: MediaViewHolder.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MediaViewHolder create(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            layoutInflater.getClass();
            viewGroup.getClass();
            View viewInflate = layoutInflater.inflate(R$layout.media_session_view, viewGroup, false);
            viewInflate.setLayerType(2, null);
            viewInflate.setLayoutDirection(3);
            MediaViewHolder mediaViewHolder = new MediaViewHolder(viewInflate);
            mediaViewHolder.getSeekBar().setLayoutDirection(0);
            return mediaViewHolder;
        }

        public final Set getBackgroundIds() {
            return MediaViewHolder.backgroundIds;
        }

        public final Set getControlsIds() {
            return MediaViewHolder.controlsIds;
        }

        public final Set getDetailIds() {
            return MediaViewHolder.detailIds;
        }

        public final Set getExpandedBottomActionIds() {
            return MediaViewHolder.expandedBottomActionIds;
        }

        public final Set getGenericButtonIds() {
            return MediaViewHolder.genericButtonIds;
        }
    }

    static {
        int i = R$id.icon;
        Integer numValueOf = Integer.valueOf(i);
        Integer numValueOf2 = Integer.valueOf(R$id.app_name);
        int i2 = R$id.header_title;
        Integer numValueOf3 = Integer.valueOf(i2);
        int i3 = R$id.header_artist;
        Integer numValueOf4 = Integer.valueOf(i3);
        int i4 = R$id.media_explicit_indicator;
        Integer numValueOf5 = Integer.valueOf(i4);
        Integer numValueOf6 = Integer.valueOf(R$id.media_seamless);
        int i5 = R$id.media_progress_bar;
        Integer numValueOf7 = Integer.valueOf(i5);
        int i6 = R$id.actionPlayPause;
        Integer numValueOf8 = Integer.valueOf(i6);
        int i7 = R$id.actionNext;
        Integer numValueOf9 = Integer.valueOf(i7);
        int i8 = R$id.actionPrev;
        Integer numValueOf10 = Integer.valueOf(i8);
        int i9 = R$id.action0;
        Integer numValueOf11 = Integer.valueOf(i9);
        int i10 = R$id.action1;
        Integer numValueOf12 = Integer.valueOf(i10);
        int i11 = R$id.action2;
        Integer numValueOf13 = Integer.valueOf(i11);
        int i12 = R$id.action3;
        Integer numValueOf14 = Integer.valueOf(i12);
        int i13 = R$id.action4;
        Integer numValueOf15 = Integer.valueOf(i13);
        Integer numValueOf16 = Integer.valueOf(i);
        int i14 = R$id.media_scrubbing_elapsed_time;
        Integer numValueOf17 = Integer.valueOf(i14);
        int i15 = R$id.media_scrubbing_total_time;
        controlsIds = SetsKt.setOf((Object[]) new Integer[]{numValueOf, numValueOf2, numValueOf3, numValueOf4, numValueOf5, numValueOf6, numValueOf7, numValueOf8, numValueOf9, numValueOf10, numValueOf11, numValueOf12, numValueOf13, numValueOf14, numValueOf15, numValueOf16, numValueOf17, Integer.valueOf(i15)});
        genericButtonIds = SetsKt.setOf((Object[]) new Integer[]{Integer.valueOf(i9), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13)});
        expandedBottomActionIds = SetsKt.setOf((Object[]) new Integer[]{Integer.valueOf(i5), Integer.valueOf(i8), Integer.valueOf(i7), Integer.valueOf(i9), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14), Integer.valueOf(i15)});
        detailIds = SetsKt.setOf((Object[]) new Integer[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i6)});
        backgroundIds = SetsKt.setOf((Object[]) new Integer[]{Integer.valueOf(R$id.album_art), Integer.valueOf(R$id.turbulence_noise_view), Integer.valueOf(R$id.loading_effect_view), Integer.valueOf(R$id.touch_ripple_view)});
    }

    public MediaViewHolder(View view) {
        view.getClass();
        this.player = (TransitionLayout) view;
        View viewRequireViewById = view.requireViewById(R$id.album_art);
        viewRequireViewById.getClass();
        this.albumView = (ImageView) viewRequireViewById;
        View viewRequireViewById2 = view.requireViewById(R$id.touch_ripple_view);
        viewRequireViewById2.getClass();
        this.multiRippleView = (MultiRippleView) viewRequireViewById2;
        View viewRequireViewById3 = view.requireViewById(R$id.turbulence_noise_view);
        viewRequireViewById3.getClass();
        this.turbulenceNoiseView = (TurbulenceNoiseView) viewRequireViewById3;
        View viewRequireViewById4 = view.requireViewById(R$id.loading_effect_view);
        viewRequireViewById4.getClass();
        this.loadingEffectView = (LoadingEffectView) viewRequireViewById4;
        View viewRequireViewById5 = view.requireViewById(R$id.icon);
        viewRequireViewById5.getClass();
        this.appIcon = (ImageView) viewRequireViewById5;
        View viewRequireViewById6 = view.requireViewById(R$id.header_title);
        viewRequireViewById6.getClass();
        this.titleText = (TextView) viewRequireViewById6;
        View viewRequireViewById7 = view.requireViewById(R$id.header_artist);
        viewRequireViewById7.getClass();
        this.artistText = (TextView) viewRequireViewById7;
        CachingIconView cachingIconViewRequireViewById = view.requireViewById(R$id.media_explicit_indicator);
        cachingIconViewRequireViewById.getClass();
        this.explicitIndicator = cachingIconViewRequireViewById;
        View viewRequireViewById8 = view.requireViewById(R$id.media_seamless);
        viewRequireViewById8.getClass();
        this.seamless = (ViewGroup) viewRequireViewById8;
        View viewRequireViewById9 = view.requireViewById(R$id.media_seamless_image);
        viewRequireViewById9.getClass();
        this.seamlessIcon = (ImageView) viewRequireViewById9;
        View viewRequireViewById10 = view.requireViewById(R$id.media_seamless_text);
        viewRequireViewById10.getClass();
        this.seamlessText = (TextView) viewRequireViewById10;
        View viewRequireViewById11 = view.requireViewById(R$id.media_seamless_button);
        viewRequireViewById11.getClass();
        this.seamlessButton = viewRequireViewById11;
        View viewRequireViewById12 = view.requireViewById(R$id.media_progress_bar);
        viewRequireViewById12.getClass();
        this.seekBar = (SeekBar) viewRequireViewById12;
        View viewRequireViewById13 = view.requireViewById(R$id.media_scrubbing_elapsed_time);
        viewRequireViewById13.getClass();
        this.scrubbingElapsedTimeView = (TextView) viewRequireViewById13;
        View viewRequireViewById14 = view.requireViewById(R$id.media_scrubbing_total_time);
        viewRequireViewById14.getClass();
        this.scrubbingTotalTimeView = (TextView) viewRequireViewById14;
        this.gutsViewHolder = new GutsViewHolder(view);
        View viewRequireViewById15 = view.requireViewById(R$id.actionPlayPause);
        viewRequireViewById15.getClass();
        this.actionPlayPause = (ImageButton) viewRequireViewById15;
        View viewRequireViewById16 = view.requireViewById(R$id.actionNext);
        viewRequireViewById16.getClass();
        this.actionNext = (ImageButton) viewRequireViewById16;
        View viewRequireViewById17 = view.requireViewById(R$id.actionPrev);
        viewRequireViewById17.getClass();
        this.actionPrev = (ImageButton) viewRequireViewById17;
        View viewRequireViewById18 = view.requireViewById(R$id.action0);
        viewRequireViewById18.getClass();
        this.action0 = (ImageButton) viewRequireViewById18;
        View viewRequireViewById19 = view.requireViewById(R$id.action1);
        viewRequireViewById19.getClass();
        this.action1 = (ImageButton) viewRequireViewById19;
        View viewRequireViewById20 = view.requireViewById(R$id.action2);
        viewRequireViewById20.getClass();
        this.action2 = (ImageButton) viewRequireViewById20;
        View viewRequireViewById21 = view.requireViewById(R$id.action3);
        viewRequireViewById21.getClass();
        this.action3 = (ImageButton) viewRequireViewById21;
        View viewRequireViewById22 = view.requireViewById(R$id.action4);
        viewRequireViewById22.getClass();
        this.action4 = (ImageButton) viewRequireViewById22;
        View viewRequireViewById23 = view.requireViewById(R$id.media_action_barrier_top);
        viewRequireViewById23.getClass();
        this.actionsTopBarrier = (Barrier) viewRequireViewById23;
    }

    public final ImageButton getAction(int i) {
        if (i == R$id.actionPlayPause) {
            return this.actionPlayPause;
        }
        if (i == R$id.actionNext) {
            return this.actionNext;
        }
        if (i == R$id.actionPrev) {
            return this.actionPrev;
        }
        if (i == R$id.action0) {
            return this.action0;
        }
        if (i == R$id.action1) {
            return this.action1;
        }
        if (i == R$id.action2) {
            return this.action2;
        }
        if (i == R$id.action3) {
            return this.action3;
        }
        if (i == R$id.action4) {
            return this.action4;
        }
        throw new IllegalArgumentException();
    }

    public final ImageButton getActionPlayPause() {
        return this.actionPlayPause;
    }

    public final ImageView getAlbumView() {
        return this.albumView;
    }

    public final ImageView getAppIcon() {
        return this.appIcon;
    }

    public final TextView getArtistText() {
        return this.artistText;
    }

    public final CachingIconView getExplicitIndicator() {
        return this.explicitIndicator;
    }

    public final GutsViewHolder getGutsViewHolder() {
        return this.gutsViewHolder;
    }

    public final LoadingEffectView getLoadingEffectView() {
        return this.loadingEffectView;
    }

    public final MultiRippleView getMultiRippleView() {
        return this.multiRippleView;
    }

    public final TransitionLayout getPlayer() {
        return this.player;
    }

    public final TextView getScrubbingElapsedTimeView() {
        return this.scrubbingElapsedTimeView;
    }

    public final TextView getScrubbingTotalTimeView() {
        return this.scrubbingTotalTimeView;
    }

    public final ViewGroup getSeamless() {
        return this.seamless;
    }

    public final View getSeamlessButton() {
        return this.seamlessButton;
    }

    public final ImageView getSeamlessIcon() {
        return this.seamlessIcon;
    }

    public final TextView getSeamlessText() {
        return this.seamlessText;
    }

    public final SeekBar getSeekBar() {
        return this.seekBar;
    }

    public final TextView getTitleText() {
        return this.titleText;
    }

    public final List getTransparentActionButtons() {
        return CollectionsKt.listOf((Object[]) new ImageButton[]{this.actionNext, this.actionPrev, this.action0, this.action1, this.action2, this.action3, this.action4});
    }

    public final TurbulenceNoiseView getTurbulenceNoiseView() {
        return this.turbulenceNoiseView;
    }

    public final void marquee(boolean z, long j) {
        this.gutsViewHolder.marquee(z, j, "MediaViewHolder");
    }
}
