package Y;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public class t {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f440a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final MediaRoute2Info f441b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final MediaRouter2Manager f442c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final String f443d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final AudioManager f444e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f445f = false;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f446g = false;

    public t(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, AudioManager audioManager) {
        this.f440a = context;
        this.f444e = audioManager;
        this.f441b = mediaRoute2Info;
        this.f442c = mediaRouter2Manager;
        this.f443d = str;
        if (mediaRoute2Info == null) {
            return;
        }
        mediaRoute2Info.getType();
    }

    public String a() {
        return "";
    }

    public int b() {
        return r.f429c ? this.f444e.getStreamVolume(3) : this.f441b.getVolume();
    }

    public int c() {
        return this.f441b.getType();
    }

    public int d() {
        int i2 = R.drawable.ic_headset;
        MediaRoute2Info mediaRoute2Info = this.f441b;
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            return r.U(this.f440a) ? R.drawable.ic_tablet : R.drawable.ic_phone;
        }
        if (type == 23 || type == 26 || type == 8) {
            return r.Q(mediaRoute2Info.getAddress()) ? R.drawable.ic_connect_pc : R.drawable.ic_bluetooth;
        }
        return type != 9 ? i2 : R.drawable.ic_hdmi;
    }

    public int e() {
        return this.f441b.getVolumeMax();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String f() {
        /*
            r4 = this;
            android.media.MediaRoute2Info r0 = r4.f441b
            java.lang.CharSequence r1 = r0.getName()
            int r2 = r0.getType()
            r3 = 2
            android.content.Context r4 = r4.f440a
            if (r2 == r3) goto L5a
            r3 = 3
            if (r2 == r3) goto L53
            r3 = 4
            if (r2 == r3) goto L53
            r3 = 8
            if (r2 == r3) goto L2e
            r3 = 26
            if (r2 == r3) goto L2e
            r3 = 11
            if (r2 == r3) goto L53
            r3 = 12
            if (r2 == r3) goto L53
            r3 = 22
            if (r2 == r3) goto L53
            r3 = 23
            if (r2 == r3) goto L2e
            goto L7f
        L2e:
            java.lang.String r0 = r0.getAddress()
            boolean r0 = Y.r.Q(r0)
            if (r0 == 0) goto L7f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            int r1 = com.motorola.mobiledesktop.core.R.string.rf_audio_type_bluetooth
            java.lang.String r4 = r4.getString(r1)
            r0.append(r4)
            java.lang.String r1 = r0.toString()
            goto L7f
        L53:
            int r0 = com.motorola.mobiledesktop.core.R.string.wired_usb_media_device_name
            java.lang.String r1 = r4.getString(r0)
            goto L7f
        L5a:
            if (r4 != 0) goto L60
            java.lang.String r0 = ""
        L5e:
            r1 = r0
            goto L73
        L60:
            android.content.ContentResolver r0 = r4.getContentResolver()
            java.lang.String r1 = "device_name"
            java.lang.String r0 = android.provider.Settings.Global.getString(r0, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L5e
            java.lang.String r0 = android.os.Build.MODEL
            goto L5e
        L73:
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 == 0) goto L7f
            int r0 = com.motorola.mobiledesktop.core.R.string.phone_media_device_name
            java.lang.String r1 = r4.getString(r0)
        L7f:
            java.lang.String r4 = r1.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.t.f():java.lang.String");
    }

    public int g() {
        int i2 = R.array.audio_headset_volume;
        MediaRoute2Info mediaRoute2Info = this.f441b;
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            return R.array.audio_phone_speaker_volume;
        }
        if (type == 23 || type == 26 || type == 8) {
            return r.Q(mediaRoute2Info.getAddress()) ? R.array.audio_local_volume : R.array.audio_bluetooth_volume;
        }
        return type != 9 ? i2 : R.array.audio_local_volume;
    }

    public boolean h() {
        int type = this.f441b.getType();
        if (type == 2 || type == 3 || type == 4 || type == 9 || type == 22) {
            return true;
        }
        switch (type) {
            case 11:
            case 12:
            case 13:
                return true;
            default:
                return false;
        }
    }

    public void i(int i2) {
        int type;
        MediaRoute2Info mediaRoute2Info = this.f441b;
        if (i2 >= 9 && ((type = mediaRoute2Info.getType()) == 3 || type == 4 || type == 8 || type == 26 || type == 22 || type == 23)) {
            this.f444e.disableSafeMediaVolume();
        }
        this.f442c.setRouteVolume(mediaRoute2Info, i2);
    }
}
