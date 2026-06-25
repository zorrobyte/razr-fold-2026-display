package Y;

import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public final class D extends t {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f349h;

    @Override // Y.t
    public final int b() {
        return this.f444e.getStreamVolume(3);
    }

    @Override // Y.t
    public final int c() {
        return 1001;
    }

    @Override // Y.t
    public final int d() {
        return R.drawable.ic_cast;
    }

    @Override // Y.t
    public final int e() {
        return this.f349h;
    }

    @Override // Y.t
    public final String f() {
        return this.f440a.getString(R.string.wireless_display_media_device_name);
    }

    @Override // Y.t
    public final int g() {
        return R.array.audio_local_volume;
    }

    @Override // Y.t
    public final boolean h() {
        return true;
    }

    @Override // Y.t
    public final void i(int i2) {
        this.f444e.setStreamVolume(3, i2, 0);
    }
}
