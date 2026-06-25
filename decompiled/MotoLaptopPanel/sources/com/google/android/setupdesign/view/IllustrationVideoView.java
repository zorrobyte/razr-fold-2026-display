package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupdesign.R$styleable;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class IllustrationVideoView extends TextureView implements Animatable, TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener {
    private float aspectRatio;
    private boolean isMediaPlayerLoading;
    protected MediaPlayer mediaPlayer;
    private boolean prepared;
    private boolean shouldPauseVideoWhenFinished;
    Surface surface;
    private int videoResId;
    private String videoResPackageName;
    private int visibility;

    public IllustrationVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.aspectRatio = 1.0f;
        this.videoResId = 0;
        this.shouldPauseVideoWhenFinished = true;
        this.visibility = 0;
        this.isMediaPlayerLoading = false;
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIllustrationVideoView);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudIllustrationVideoView_sudVideo, 0);
        if (BuildCompatUtils.isAtLeastS()) {
            setPauseVideoWhenFinished(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudIllustrationVideoView_sudPauseVideoWhenFinished, true));
        }
        typedArrayObtainStyledAttributes.recycle();
        setVideoResource(resourceId);
        setScaleX(0.9999999f);
        setScaleX(0.9999999f);
        setSurfaceTextureListener(this);
    }

    private void initVideo() {
        if (getWindowVisibility() != 0) {
            return;
        }
        createSurface();
        if (this.surface != null) {
            createMediaPlayer();
        } else {
            Log.i("IllustrationVideoView", "Surface is null");
        }
    }

    private void reattach() {
        if (this.surface == null) {
            initVideo();
        }
    }

    private void setIsMediaPlayerLoading(boolean z) {
        this.isMediaPlayerLoading = z;
        setVisibility(this.visibility);
    }

    private void setVideoResourceInternal(int i, String str) {
        try {
            this.mediaPlayer.setDataSource(getContext(), Uri.parse("android.resource://" + str + "/" + i), (Map<String, String>) null);
            this.mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("IllustrationVideoView", "Unable to set video data source: " + i, e);
        }
    }

    protected void createMediaPlayer() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (this.surface == null || this.videoResId == 0) {
            return;
        }
        MediaPlayer mediaPlayer2 = new MediaPlayer();
        this.mediaPlayer = mediaPlayer2;
        mediaPlayer2.setSurface(this.surface);
        this.mediaPlayer.setOnPreparedListener(this);
        this.mediaPlayer.setOnSeekCompleteListener(this);
        this.mediaPlayer.setOnInfoListener(this);
        this.mediaPlayer.setOnErrorListener(this);
        setVideoResourceInternal(this.videoResId, this.videoResPackageName);
    }

    protected void createSurface() {
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
            this.surface = null;
        }
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (surfaceTexture != null) {
            setIsMediaPlayerLoading(true);
            this.surface = new Surface(surfaceTexture);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    protected boolean isPrepared() {
        return this.prepared;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.w("IllustrationVideoView", "MediaPlayer error. what=" + i + " extra=" + i2);
        return false;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 3) {
            setIsMediaPlayerLoading(false);
            onRenderingStart();
        }
        return false;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        float f = size2;
        float f2 = size;
        float f3 = this.aspectRatio;
        if (f < f2 * f3) {
            size = (int) (f / f3);
        } else {
            size2 = (int) (f2 * f3);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        float videoHeight;
        this.prepared = true;
        mediaPlayer.setLooping(shouldLoop());
        if (mediaPlayer.getVideoWidth() <= 0 || mediaPlayer.getVideoHeight() <= 0) {
            Log.w("IllustrationVideoView", "Unexpected video size=" + mediaPlayer.getVideoWidth() + "x" + mediaPlayer.getVideoHeight());
            videoHeight = 0.0f;
        } else {
            videoHeight = mediaPlayer.getVideoHeight() / mediaPlayer.getVideoWidth();
        }
        if (Float.compare(this.aspectRatio, videoHeight) != 0) {
            this.aspectRatio = videoHeight;
            requestLayout();
        }
        if (getWindowVisibility() == 0) {
            start();
        }
    }

    protected void onRenderingStart() {
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        if (isPrepared()) {
            mediaPlayer.start();
        } else {
            Log.e("IllustrationVideoView", "Seek complete but media player not prepared");
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        setIsMediaPlayerLoading(true);
        initVideo();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        release();
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            start();
        } else {
            stop();
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            reattach();
        } else {
            release();
        }
    }

    public void release() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.mediaPlayer = null;
            this.prepared = false;
        }
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
            this.surface = null;
        }
    }

    public void setPauseVideoWhenFinished(boolean z) {
        this.shouldPauseVideoWhenFinished = z;
    }

    public void setVideoResource(int i) {
        setVideoResource(i, getContext().getPackageName());
    }

    public void setVideoResource(int i, String str) {
        if (i == this.videoResId && (str == null || str.equals(this.videoResPackageName))) {
            return;
        }
        this.videoResId = i;
        this.videoResPackageName = str;
        createMediaPlayer();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        this.visibility = i;
        if (this.isMediaPlayerLoading && i == 0) {
            i = 4;
        }
        super.setVisibility(i);
    }

    protected boolean shouldLoop() {
        return true;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        MediaPlayer mediaPlayer;
        if (!this.prepared || (mediaPlayer = this.mediaPlayer) == null || mediaPlayer.isPlaying()) {
            return;
        }
        this.mediaPlayer.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        MediaPlayer mediaPlayer;
        if (this.shouldPauseVideoWhenFinished && this.prepared && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.pause();
        }
    }
}
