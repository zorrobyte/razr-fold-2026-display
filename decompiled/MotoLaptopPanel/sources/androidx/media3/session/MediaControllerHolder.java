package androidx.media3.session;

import android.os.Handler;
import android.os.Looper;
import androidx.media3.common.util.Util;
import androidx.media3.session.MediaController;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class MediaControllerHolder extends AbstractFuture implements MediaController.ConnectionCallback {
    private boolean accepted;
    private MediaController controller;
    private final Handler handler;

    public static /* synthetic */ void $r8$lambda$uUP6uG1La2JYxvft6nMxeL9XL5k(MediaControllerHolder mediaControllerHolder, MediaController mediaController) {
        if (mediaControllerHolder.isCancelled()) {
            mediaController.release();
        }
    }

    public MediaControllerHolder(Looper looper) {
        this.handler = new Handler(looper);
    }

    private void maybeSetException() {
        setException(new SecurityException("Session rejected the connection request."));
    }

    private void maybeSetFutureResult() {
        MediaController mediaController = this.controller;
        if (mediaController == null || !this.accepted) {
            return;
        }
        set(mediaController);
    }

    @Override // androidx.media3.session.MediaController.ConnectionCallback
    public void onAccepted() {
        this.accepted = true;
        maybeSetFutureResult();
    }

    @Override // androidx.media3.session.MediaController.ConnectionCallback
    public void onRejected() {
        maybeSetException();
    }

    public void setController(final MediaController mediaController) {
        this.controller = mediaController;
        maybeSetFutureResult();
        addListener(new Runnable() { // from class: androidx.media3.session.MediaControllerHolder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerHolder.$r8$lambda$uUP6uG1La2JYxvft6nMxeL9XL5k(this.f$0, mediaController);
            }
        }, new Executor() { // from class: androidx.media3.session.MediaControllerHolder$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                Util.postOrRun(this.f$0.handler, runnable);
            }
        });
    }
}
