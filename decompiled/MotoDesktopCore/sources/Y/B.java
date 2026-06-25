package Y;

import com.motorola.mobiledesktop.core.audio.MediaOutputRouteFragment;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class B extends h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MediaOutputRouteFragment f347a;

    public B(MediaOutputRouteFragment mediaOutputRouteFragment) {
        this.f347a = mediaOutputRouteFragment;
    }

    @Override // Y.h
    public final void a(List list, int i2, boolean z2) {
        synchronized (this.f347a.mMediaDevicesLock) {
            this.f347a.mMediaDevices.clear();
            this.f347a.mMediaDevices.addAll(list);
            this.f347a.mCurrentSelect = i2;
            this.f347a.mIsInCallMode = z2;
            this.f347a.mHandler.sendEmptyMessage(1000);
        }
    }

    @Override // Y.h
    public final void b() {
    }
}
