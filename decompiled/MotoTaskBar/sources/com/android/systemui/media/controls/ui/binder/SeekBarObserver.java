package com.android.systemui.media.controls.ui.binder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import com.android.app.animation.Interpolators;
import com.android.systemui.media.controls.ui.drawable.SquigglyProgress;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$string;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SeekBarObserver.kt */
/* JADX INFO: loaded from: classes.dex */
public class SeekBarObserver implements Observer {
    public static final Companion Companion = new Companion(null);
    private static final int RESET_ANIMATION_DURATION_MS = 750;
    private static final int RESET_ANIMATION_THRESHOLD_MS = 250;
    private boolean animationEnabled;
    private final MediaViewHolder holder;
    private final int seekBarDisabledHeight;
    private final int seekBarDisabledVerticalPadding;
    private final int seekBarEnabledMaxHeight;
    private final int seekBarEnabledVerticalPadding;
    private Animator seekBarResetAnimator;

    /* JADX INFO: compiled from: SeekBarObserver.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SeekBarObserver(MediaViewHolder mediaViewHolder) {
        mediaViewHolder.getClass();
        this.holder = mediaViewHolder;
        this.seekBarEnabledMaxHeight = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_enabled_seekbar_height);
        this.seekBarDisabledHeight = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_disabled_seekbar_height);
        this.seekBarEnabledVerticalPadding = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_session_enabled_seekbar_vertical_padding);
        this.seekBarDisabledVerticalPadding = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_session_disabled_seekbar_vertical_padding);
        this.animationEnabled = true;
        float dimensionPixelSize = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_wavelength);
        float dimensionPixelSize2 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_amplitude);
        float dimensionPixelSize3 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_phase);
        float dimensionPixelSize4 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_stroke_width);
        Drawable progressDrawable = mediaViewHolder.getSeekBar().getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (squigglyProgress != null) {
            squigglyProgress.setWaveLength(dimensionPixelSize);
            squigglyProgress.setLineAmplitude(dimensionPixelSize2);
            squigglyProgress.setPhaseSpeed(dimensionPixelSize3);
            squigglyProgress.setStrokeWidth(dimensionPixelSize4);
        }
    }

    public Animator buildResetAnimator(int i) {
        SeekBar seekBar = this.holder.getSeekBar();
        int progress = this.holder.getSeekBar().getProgress();
        int i2 = RESET_ANIMATION_DURATION_MS;
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(seekBar, "progress", progress, i + i2);
        objectAnimatorOfInt.setAutoCancel(true);
        objectAnimatorOfInt.setDuration(i2);
        objectAnimatorOfInt.setInterpolator(Interpolators.EMPHASIZED);
        return objectAnimatorOfInt;
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(SeekBarViewModel.Progress progress) {
        progress.getClass();
        Drawable progressDrawable = this.holder.getSeekBar().getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (!progress.getEnabled()) {
            if (this.holder.getSeekBar().getMaxHeight() != this.seekBarDisabledHeight) {
                this.holder.getSeekBar().setMaxHeight(this.seekBarDisabledHeight);
                setVerticalPadding(this.seekBarDisabledVerticalPadding);
            }
            this.holder.getSeekBar().setEnabled(false);
            if (squigglyProgress != null) {
                squigglyProgress.setAnimate(false);
            }
            this.holder.getSeekBar().getThumb().setAlpha(0);
            this.holder.getSeekBar().setProgress(0);
            this.holder.getSeekBar().setContentDescription("");
            this.holder.getScrubbingElapsedTimeView().setText("");
            this.holder.getScrubbingTotalTimeView().setText("");
            return;
        }
        this.holder.getSeekBar().getThumb().setAlpha(progress.getSeekAvailable() ? 255 : 0);
        this.holder.getSeekBar().setEnabled(progress.getSeekAvailable());
        if (squigglyProgress != null) {
            squigglyProgress.setAnimate(progress.getPlaying() && !progress.getScrubbing() && this.animationEnabled && progress.getListening());
        }
        if (squigglyProgress != null) {
            squigglyProgress.setTransitionEnabled(true ^ progress.getSeekAvailable());
        }
        if (this.holder.getSeekBar().getMaxHeight() != this.seekBarEnabledMaxHeight) {
            this.holder.getSeekBar().setMaxHeight(this.seekBarEnabledMaxHeight);
            setVerticalPadding(this.seekBarEnabledVerticalPadding);
        }
        this.holder.getSeekBar().setMax(progress.getDuration());
        String elapsedTime = DateUtils.formatElapsedTime(((long) progress.getDuration()) / 1000);
        if (progress.getScrubbing()) {
            this.holder.getScrubbingTotalTimeView().setText(elapsedTime);
        }
        Integer elapsedTime2 = progress.getElapsedTime();
        if (elapsedTime2 != null) {
            int iIntValue = elapsedTime2.intValue();
            if (!progress.getScrubbing()) {
                Animator animator = this.seekBarResetAnimator;
                if (!(animator != null ? animator.isRunning() : false)) {
                    int i = RESET_ANIMATION_THRESHOLD_MS;
                    if (iIntValue > i || this.holder.getSeekBar().getProgress() <= i) {
                        this.holder.getSeekBar().setProgress(iIntValue);
                    } else {
                        Animator animatorBuildResetAnimator = buildResetAnimator(iIntValue);
                        animatorBuildResetAnimator.start();
                        this.seekBarResetAnimator = animatorBuildResetAnimator;
                    }
                }
            }
            String elapsedTime3 = DateUtils.formatElapsedTime(((long) iIntValue) / 1000);
            if (progress.getScrubbing()) {
                this.holder.getScrubbingElapsedTimeView().setText(elapsedTime3);
            }
            this.holder.getSeekBar().setContentDescription(this.holder.getSeekBar().getContext().getString(R$string.controls_media_seekbar_description, elapsedTime3, elapsedTime));
        }
    }

    public final void setAnimationEnabled(boolean z) {
        this.animationEnabled = z;
    }

    public final void setVerticalPadding(int i) {
        this.holder.getSeekBar().setPadding(this.holder.getSeekBar().getPaddingLeft(), i, this.holder.getSeekBar().getPaddingRight(), this.holder.getSeekBar().getPaddingBottom());
    }
}
