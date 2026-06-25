package androidx.media3.session;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class MediaControllerImplBase$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ MediaController f$0;

    public /* synthetic */ MediaControllerImplBase$$ExternalSyntheticLambda16(MediaController mediaController) {
        this.f$0 = mediaController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.release();
    }
}
