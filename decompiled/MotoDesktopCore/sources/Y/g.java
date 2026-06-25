package Y;

import X.v0;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f361a = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f362b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f363c = -1;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f364d = -1;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f365e = -1;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f366f = -1;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f367g = -1;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ i f368h;

    public g(i iVar) {
        this.f368h = iVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0027, code lost:
    
        if ((r2 != null ? r2.getType() : 0) == 2) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0030, code lost:
    
        if (r2.f398k.isWiredHeadsetOn() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0033, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int a(int r3, boolean r4) {
        /*
            r2 = this;
            r0 = 2
            r1 = 0
            if (r3 == 0) goto L14
            if (r3 == r0) goto L33
            r0 = 3
            if (r3 == r0) goto L33
            r0 = 4
            if (r3 == r0) goto L33
            r0 = 5
            if (r3 == r0) goto L33
            r0 = 6
            if (r3 == r0) goto L33
        L12:
            r0 = r1
            goto L33
        L14:
            r3 = 1
            Y.i r2 = r2.f368h
            if (r4 != 0) goto L2a
            android.media.AudioManager r2 = r2.f398k
            android.media.AudioDeviceInfo r2 = r2.getCommunicationDevice()
            if (r2 == 0) goto L26
            int r2 = r2.getType()
            goto L27
        L26:
            r2 = r1
        L27:
            if (r2 != r0) goto L12
            goto L32
        L2a:
            android.media.AudioManager r2 = r2.f398k
            boolean r2 = r2.isWiredHeadsetOn()
            if (r2 == 0) goto L12
        L32:
            r0 = r3
        L33:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.g.a(int, boolean):int");
    }

    public final boolean b() {
        i iVar = this.f368h;
        return iVar.f396i && iVar.f400m;
    }

    public final void c() {
        if (b()) {
            int i2 = this.f367g;
            int i3 = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? -1 : this.f366f : this.f361a : this.f365e : this.f364d : this.f363c : this.f362b;
            if (i3 > 0) {
                this.f368h.f398k.setStreamVolume(0, i3, 0);
                v0.a("i", "recoveryCallVolumeByType type = " + this.f367g + " callVolume = " + i3);
            }
        }
    }

    public final String toString() {
        return "CallAudioVolumeControl mCurrentType = " + this.f367g + " mRdpVolume = " + this.f361a + " mEarpieceVolume = " + this.f362b + " mSpeakerVolume = " + this.f363c + " mHeadsetVolume = " + this.f364d + " mHdmiVolume = " + this.f365e + " mWifiVolume = " + this.f366f;
    }
}
