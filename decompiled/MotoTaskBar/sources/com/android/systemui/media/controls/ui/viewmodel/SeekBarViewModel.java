package com.android.systemui.media.controls.ui.viewmodel;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SeekBarViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SeekBarViewModel {
    private Progress _data;
    private final MutableLiveData _progress;
    private final RepeatableExecutor bgExecutor;
    private SeekBarViewModel$callback$1 callback;
    private Runnable cancel;
    private MediaController controller;
    private EnabledChangeListener enabledChangeListener;
    private MotionEvent firstMotionEvent;
    private boolean isFalseSeek;
    private MotionEvent lastMotionEvent;
    private boolean listening;
    public Function0 logSeek;
    private PlaybackState playbackState;
    private boolean scrubbing;
    private ScrubbingChangeListener scrubbingChangeListener;

    /* JADX INFO: compiled from: SeekBarViewModel.kt */
    public interface EnabledChangeListener {
        void onEnabledChanged(boolean z);
    }

    /* JADX INFO: compiled from: SeekBarViewModel.kt */
    public final class Progress {
        private final int duration;
        private final Integer elapsedTime;
        private final boolean enabled;
        private final boolean listening;
        private final boolean playing;
        private final boolean scrubbing;
        private final boolean seekAvailable;

        public Progress(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i, boolean z5) {
            this.enabled = z;
            this.seekAvailable = z2;
            this.playing = z3;
            this.scrubbing = z4;
            this.elapsedTime = num;
            this.duration = i;
            this.listening = z5;
        }

        public static /* synthetic */ Progress copy$default(Progress progress, boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i, boolean z5, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                z = progress.enabled;
            }
            if ((i2 & 2) != 0) {
                z2 = progress.seekAvailable;
            }
            if ((i2 & 4) != 0) {
                z3 = progress.playing;
            }
            if ((i2 & 8) != 0) {
                z4 = progress.scrubbing;
            }
            if ((i2 & 16) != 0) {
                num = progress.elapsedTime;
            }
            if ((i2 & 32) != 0) {
                i = progress.duration;
            }
            if ((i2 & 64) != 0) {
                z5 = progress.listening;
            }
            int i3 = i;
            boolean z6 = z5;
            Integer num2 = num;
            boolean z7 = z3;
            return progress.copy(z, z2, z7, z4, num2, i3, z6);
        }

        public final Progress copy(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i, boolean z5) {
            return new Progress(z, z2, z3, z4, num, i, z5);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Progress)) {
                return false;
            }
            Progress progress = (Progress) obj;
            return this.enabled == progress.enabled && this.seekAvailable == progress.seekAvailable && this.playing == progress.playing && this.scrubbing == progress.scrubbing && Intrinsics.areEqual(this.elapsedTime, progress.elapsedTime) && this.duration == progress.duration && this.listening == progress.listening;
        }

        public final int getDuration() {
            return this.duration;
        }

        public final Integer getElapsedTime() {
            return this.elapsedTime;
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final boolean getListening() {
            return this.listening;
        }

        public final boolean getPlaying() {
            return this.playing;
        }

        public final boolean getScrubbing() {
            return this.scrubbing;
        }

        public final boolean getSeekAvailable() {
            return this.seekAvailable;
        }

        public int hashCode() {
            int iHashCode = ((((((Boolean.hashCode(this.enabled) * 31) + Boolean.hashCode(this.seekAvailable)) * 31) + Boolean.hashCode(this.playing)) * 31) + Boolean.hashCode(this.scrubbing)) * 31;
            Integer num = this.elapsedTime;
            return ((((iHashCode + (num == null ? 0 : num.hashCode())) * 31) + Integer.hashCode(this.duration)) * 31) + Boolean.hashCode(this.listening);
        }

        public String toString() {
            return "Progress(enabled=" + this.enabled + ", seekAvailable=" + this.seekAvailable + ", playing=" + this.playing + ", scrubbing=" + this.scrubbing + ", elapsedTime=" + this.elapsedTime + ", duration=" + this.duration + ", listening=" + this.listening + ")";
        }
    }

    /* JADX INFO: compiled from: SeekBarViewModel.kt */
    public interface ScrubbingChangeListener {
        void onScrubbingChanged(boolean z);
    }

    /* JADX INFO: compiled from: SeekBarViewModel.kt */
    final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private final SeekBarViewModel viewModel;

        public SeekBarChangeListener(SeekBarViewModel seekBarViewModel) {
            seekBarViewModel.getClass();
            this.viewModel = seekBarViewModel;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            seekBar.getClass();
            if (z) {
                this.viewModel.onSeekProgress(i);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            seekBar.getClass();
            this.viewModel.onSeekStarting();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekBar.getClass();
            if (!this.viewModel.isValidSeekbarGrab()) {
                this.viewModel.onSeekFalse();
            }
            this.viewModel.onSeek(seekBar.getProgress());
        }
    }

    /* JADX INFO: compiled from: SeekBarViewModel.kt */
    final class SeekBarTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
        private final SeekBar bar;
        private final GestureDetectorCompat detector;
        private final int flingVelocity;
        private boolean shouldGoToSeekBar;
        private final SeekBarViewModel viewModel;

        public SeekBarTouchListener(SeekBarViewModel seekBarViewModel, SeekBar seekBar) {
            seekBarViewModel.getClass();
            seekBar.getClass();
            this.viewModel = seekBarViewModel;
            this.bar = seekBar;
            this.detector = new GestureDetectorCompat(seekBar.getContext(), this);
            this.flingVelocity = ViewConfiguration.get(seekBar.getContext()).getScaledMinimumFlingVelocity() * 10;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            double d;
            double d2;
            ViewParent parent;
            motionEvent.getClass();
            int paddingLeft = this.bar.getPaddingLeft();
            int paddingRight = this.bar.getPaddingRight();
            int progress = this.bar.getProgress();
            int max = this.bar.getMax() - this.bar.getMin();
            double min = max > 0 ? ((double) (progress - this.bar.getMin())) / ((double) max) : 0.0d;
            int width = (this.bar.getWidth() - paddingLeft) - paddingRight;
            if (this.bar.isLayoutRtl()) {
                d = paddingLeft;
                d2 = ((double) width) * (((double) 1) - min);
            } else {
                d = paddingLeft;
                d2 = ((double) width) * min;
            }
            double d3 = d + d2;
            long height = this.bar.getHeight() / 2;
            int iRound = (int) (Math.round(d3) - height);
            int iRound2 = (int) (Math.round(d3) + height);
            int iRound3 = Math.round(motionEvent.getX());
            boolean z = iRound3 >= iRound && iRound3 <= iRound2;
            this.shouldGoToSeekBar = z;
            if (z && (parent = this.bar.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            this.viewModel.setFirstMotionEvent(motionEvent.copy());
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            motionEvent2.getClass();
            if (Math.abs(f) > this.flingVelocity || Math.abs(f2) > this.flingVelocity) {
                this.viewModel.onSeekFalse();
            }
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            motionEvent.getClass();
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            motionEvent2.getClass();
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
            motionEvent.getClass();
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            motionEvent.getClass();
            this.shouldGoToSeekBar = true;
            return true;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.getClass();
            motionEvent.getClass();
            if (!Intrinsics.areEqual(view, this.bar)) {
                return false;
            }
            this.detector.onTouchEvent(motionEvent);
            this.viewModel.setLastMotionEvent(motionEvent.copy());
            return !this.shouldGoToSeekBar;
        }
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$callback$1] */
    public SeekBarViewModel(RepeatableExecutor repeatableExecutor) {
        repeatableExecutor.getClass();
        this.bgExecutor = repeatableExecutor;
        this._data = new Progress(false, false, false, false, null, 0, false);
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public void onPlaybackStateChanged(PlaybackState playbackState) {
                this.this$0.playbackState = playbackState;
                if (this.this$0.playbackState != null) {
                    Integer num = 0;
                    if (!num.equals(this.this$0.playbackState)) {
                        this.this$0.checkIfPollingNeeded();
                        return;
                    }
                }
                this.this$0.clearController();
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionDestroyed() {
                this.this$0.clearController();
            }
        };
        this.listening = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void checkIfPollingNeeded() {
        /*
            r8 = this;
            boolean r0 = r8.listening
            r1 = 0
            if (r0 == 0) goto L17
            boolean r0 = r8.scrubbing
            if (r0 != 0) goto L17
            android.media.session.PlaybackState r0 = r8.playbackState
            if (r0 == 0) goto L12
            boolean r0 = com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModelKt.access$isInMotion(r0)
            goto L13
        L12:
            r0 = r1
        L13:
            if (r0 == 0) goto L17
            r0 = 1
            goto L18
        L17:
            r0 = r1
        L18:
            android.media.session.MediaController r2 = r8.controller
            r3 = 0
            if (r2 == 0) goto L22
            android.media.session.MediaSession$Token r2 = r2.getSessionToken()
            goto L23
        L22:
            r2 = r3
        L23:
            if (r2 == 0) goto L29
            int r1 = r2.hashCode()
        L29:
            if (r0 == 0) goto L4b
            java.lang.Runnable r0 = r8.cancel
            if (r0 != 0) goto L4a
            java.lang.String r0 = "SeekBarPollingPosition"
            android.os.Trace.beginAsyncSection(r0, r1)
            com.android.systemui.util.concurrency.RepeatableExecutor r2 = r8.bgExecutor
            com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$cancelPolling$1 r3 = new com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$cancelPolling$1
            r3.<init>()
            r4 = 0
            r6 = 100
            java.lang.Runnable r0 = r2.executeRepeatedly(r3, r4, r6)
            com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$1 r2 = new com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$1
            r2.<init>()
            r8.cancel = r2
        L4a:
            return
        L4b:
            java.lang.Runnable r0 = r8.cancel
            if (r0 == 0) goto L52
            r0.run()
        L52:
            r8.cancel = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.checkIfPollingNeeded():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkPlaybackPosition() {
        int duration = this._data.getDuration();
        PlaybackState playbackState = this.playbackState;
        Integer numValueOf = playbackState != null ? Integer.valueOf((int) SeekBarViewModelKt.computePosition(playbackState, duration)) : null;
        if (numValueOf == null || Intrinsics.areEqual(this._data.getElapsedTime(), numValueOf)) {
            return;
        }
        set_data(Progress.copy$default(this._data, false, false, false, false, numValueOf, 0, false, 111, null));
    }

    public static /* synthetic */ void getFirstMotionEvent$annotations() {
    }

    public static /* synthetic */ void getLastMotionEvent$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isValidSeekbarGrab() {
        MotionEvent motionEvent = this.firstMotionEvent;
        if (motionEvent == null || this.lastMotionEvent == null) {
            return true;
        }
        motionEvent.getClass();
        float x = motionEvent.getX();
        MotionEvent motionEvent2 = this.lastMotionEvent;
        motionEvent2.getClass();
        float fAbs = Math.abs(x - motionEvent2.getX());
        MotionEvent motionEvent3 = this.firstMotionEvent;
        motionEvent3.getClass();
        float y = motionEvent3.getY();
        MotionEvent motionEvent4 = this.lastMotionEvent;
        motionEvent4.getClass();
        return fAbs >= Math.abs(y - motionEvent4.getY());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setController(MediaController mediaController) {
        MediaController mediaController2 = this.controller;
        if (Intrinsics.areEqual(mediaController2 != null ? mediaController2.getSessionToken() : null, mediaController != null ? mediaController.getSessionToken() : null)) {
            return;
        }
        MediaController mediaController3 = this.controller;
        if (mediaController3 != null) {
            mediaController3.unregisterCallback(this.callback);
        }
        if (mediaController != null) {
            mediaController.registerCallback(this.callback);
        }
        this.controller = mediaController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setScrubbing(boolean z) {
        if (this.scrubbing != z) {
            this.scrubbing = z;
            checkIfPollingNeeded();
            ScrubbingChangeListener scrubbingChangeListener = this.scrubbingChangeListener;
            if (scrubbingChangeListener != null) {
                scrubbingChangeListener.onScrubbingChanged(z);
            }
            set_data(Progress.copy$default(this._data, false, false, false, z, null, 0, false, 119, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void set_data(Progress progress) {
        EnabledChangeListener enabledChangeListener;
        boolean z = progress.getEnabled() != this._data.getEnabled();
        this._data = progress;
        if (z && (enabledChangeListener = this.enabledChangeListener) != null) {
            enabledChangeListener.onEnabledChanged(progress.getEnabled());
        }
        this._progress.postValue(progress);
    }

    public final void attachTouchHandlers(SeekBar seekBar) {
        seekBar.getClass();
        seekBar.setOnSeekBarChangeListener(getSeekBarListener());
        seekBar.setOnTouchListener(new SeekBarTouchListener(this, seekBar));
    }

    public final void clearController() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.clearController.1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.this.setController(null);
                SeekBarViewModel.this.playbackState = null;
                Runnable runnable = SeekBarViewModel.this.cancel;
                if (runnable != null) {
                    runnable.run();
                }
                SeekBarViewModel.this.cancel = null;
                SeekBarViewModel seekBarViewModel = SeekBarViewModel.this;
                seekBarViewModel.set_data(Progress.copy$default(seekBarViewModel._data, false, false, false, false, null, 0, false, 126, null));
            }
        });
    }

    public final boolean getListening() {
        return this.listening;
    }

    public final Function0 getLogSeek() {
        Function0 function0 = this.logSeek;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("logSeek");
        return null;
    }

    public final LiveData getProgress() {
        return this._progress;
    }

    public final SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBarChangeListener(this);
    }

    public final void onDestroy() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.onDestroy.1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.this.setController(null);
                SeekBarViewModel.this.playbackState = null;
                Runnable runnable = SeekBarViewModel.this.cancel;
                if (runnable != null) {
                    runnable.run();
                }
                SeekBarViewModel.this.cancel = null;
                SeekBarViewModel.this.scrubbingChangeListener = null;
                SeekBarViewModel.this.enabledChangeListener = null;
            }
        });
    }

    public final void onSeek(final long j) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.onSeek.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.TransportControls transportControls;
                if (SeekBarViewModel.this.isFalseSeek) {
                    SeekBarViewModel.this.setScrubbing(false);
                    SeekBarViewModel.this.checkPlaybackPosition();
                    return;
                }
                SeekBarViewModel.this.getLogSeek().mo2224invoke();
                MediaController mediaController = SeekBarViewModel.this.controller;
                if (mediaController != null && (transportControls = mediaController.getTransportControls()) != null) {
                    transportControls.seekTo(j);
                }
                SeekBarViewModel.this.playbackState = null;
                SeekBarViewModel.this.setScrubbing(false);
            }
        });
    }

    public final void onSeekFalse() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.onSeekFalse.1
            @Override // java.lang.Runnable
            public final void run() {
                if (SeekBarViewModel.this.scrubbing) {
                    SeekBarViewModel.this.isFalseSeek = true;
                }
            }
        });
    }

    public final void onSeekProgress(final long j) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.onSeekProgress.1
            @Override // java.lang.Runnable
            public final void run() {
                if (!SeekBarViewModel.this.scrubbing) {
                    SeekBarViewModel.this.onSeek(j);
                } else {
                    SeekBarViewModel seekBarViewModel = SeekBarViewModel.this;
                    seekBarViewModel.set_data(Progress.copy$default(seekBarViewModel._data, false, false, false, false, Integer.valueOf((int) j), 0, false, 111, null));
                }
            }
        });
    }

    public final void onSeekStarting() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.onSeekStarting.1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.this.setScrubbing(true);
                SeekBarViewModel.this.isFalseSeek = false;
            }
        });
    }

    public final void removeEnabledChangeListener(EnabledChangeListener enabledChangeListener) {
        enabledChangeListener.getClass();
        if (Intrinsics.areEqual(enabledChangeListener, this.enabledChangeListener)) {
            this.enabledChangeListener = null;
        }
    }

    public final void removeScrubbingChangeListener(ScrubbingChangeListener scrubbingChangeListener) {
        scrubbingChangeListener.getClass();
        if (Intrinsics.areEqual(scrubbingChangeListener, this.scrubbingChangeListener)) {
            this.scrubbingChangeListener = null;
        }
    }

    public final void setEnabledChangeListener(EnabledChangeListener enabledChangeListener) {
        enabledChangeListener.getClass();
        this.enabledChangeListener = enabledChangeListener;
    }

    public final void setFirstMotionEvent(MotionEvent motionEvent) {
        this.firstMotionEvent = motionEvent;
    }

    public final void setLastMotionEvent(MotionEvent motionEvent) {
        this.lastMotionEvent = motionEvent;
    }

    public final void setListening(final boolean z) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$listening$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z2 = this.this$0.listening;
                boolean z3 = z;
                if (z2 != z3) {
                    this.this$0.listening = z3;
                    this.this$0.checkIfPollingNeeded();
                    SeekBarViewModel seekBarViewModel = this.this$0;
                    seekBarViewModel.set_data(SeekBarViewModel.Progress.copy$default(seekBarViewModel._data, false, false, false, false, null, 0, z, 63, null));
                }
            }
        });
    }

    public final void setLogSeek(Function0 function0) {
        function0.getClass();
        this.logSeek = function0;
    }

    public final void setScrubbingChangeListener(ScrubbingChangeListener scrubbingChangeListener) {
        scrubbingChangeListener.getClass();
        this.scrubbingChangeListener = scrubbingChangeListener;
    }

    public final void updateController(MediaController mediaController) {
        setController(mediaController);
        MediaController mediaController2 = this.controller;
        this.playbackState = mediaController2 != null ? mediaController2.getPlaybackState() : null;
        MediaController mediaController3 = this.controller;
        MediaMetadata metadata = mediaController3 != null ? mediaController3.getMetadata() : null;
        PlaybackState playbackState = this.playbackState;
        boolean z = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
        PlaybackState playbackState2 = this.playbackState;
        Integer numValueOf = playbackState2 != null ? Integer.valueOf((int) playbackState2.getPosition()) : null;
        int i = metadata != null ? (int) metadata.getLong("android.media.metadata.DURATION") : 0;
        PlaybackState playbackState3 = this.playbackState;
        boolean zIsPlayingState = NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0);
        PlaybackState playbackState4 = this.playbackState;
        set_data(new Progress(playbackState4 != null && (playbackState4 == null || playbackState4.getState() != 0) && i > 0, z, zIsPlayingState, this.scrubbing, numValueOf, i, this.listening));
        checkIfPollingNeeded();
    }

    public final void updateStaticProgress(double d) {
        set_data(new Progress(true, false, false, false, Integer.valueOf((int) (d * ((double) 100))), 100, false));
    }
}
