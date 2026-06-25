package Y;

import X.Q0;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public final class s extends t {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f434h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final int f435i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final int f436j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final boolean f437k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public String f438l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public String f439m;

    public s(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, AudioManager audioManager, int i2, boolean z2) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str, audioManager);
        this.f437k = z2;
        this.f436j = i2;
        if (z2) {
            this.f435i = audioManager.getStreamMaxVolume(6);
        } else {
            this.f435i = audioManager.getStreamMaxVolume(0);
        }
    }

    @Override // Y.t
    public final String a() {
        return this.f436j != 5 ? "" : this.f440a.getString(R.string.switch_to_phone_mic);
    }

    @Override // Y.t
    public final int b() {
        int i2;
        AudioManager audioManager = this.f444e;
        boolean z2 = this.f437k;
        if (z2) {
            this.f434h = audioManager.getStreamVolume(6);
        } else {
            this.f434h = audioManager.getStreamVolume(0);
        }
        if (!z2 && this.f434h == 0) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
            this.f434h = audioManager.getStreamVolume(0);
        }
        return (z2 || (i2 = this.f434h) < 1) ? this.f434h : i2 - 1;
    }

    @Override // Y.t
    public final int d() {
        int i2 = R.drawable.ic_headset;
        int i3 = this.f436j;
        if (i3 == 0) {
            return r.U(this.f440a) ? R.drawable.ic_tablet : R.drawable.ic_phone;
        }
        if (i3 == 2) {
            return i2;
        }
        if (i3 == 3) {
            return r.Q(this.f439m) ? R.drawable.ic_connect_pc : R.drawable.ic_bluetooth;
        }
        if (i3 == 4) {
            return R.drawable.ic_hdmi;
        }
        if (i3 != 5) {
            return i3 != 6 ? i2 : R.drawable.ic_cast;
        }
        int i4 = R.drawable.ic_connect_pc;
        int i5 = Q0.f290c;
        if (i5 == 1) {
            i4 = R.drawable.ic_phone;
        } else if (i5 == 2) {
            i4 = R.drawable.ic_tablet;
        } else if (i5 == 201) {
            i4 = R.drawable.ic_quest;
        }
        return i4;
    }

    @Override // Y.t
    public final int e() {
        boolean z2 = this.f437k;
        int i2 = this.f435i;
        return (z2 || i2 < 2) ? i2 : i2 - 1;
    }

    @Override // Y.t
    public final String f() {
        CharSequence name = this.f441b.getName();
        int i2 = this.f436j;
        Context context = this.f440a;
        if (i2 == 0) {
            if (context == null) {
                name = "";
            } else {
                String string = Settings.Global.getString(context.getContentResolver(), "device_name");
                if (TextUtils.isEmpty(string)) {
                    string = Build.MODEL;
                }
                name = string;
            }
            if (TextUtils.isEmpty(name)) {
                name = context.getString(R.string.call_device_name_phone);
            }
        } else if (i2 == 2) {
            name = context.getString(R.string.wired_usb_media_device_name);
        } else if (i2 == 3) {
            name = this.f438l;
            if (r.Q(this.f439m)) {
                name = ((Object) name) + " " + context.getString(R.string.rf_audio_type_bluetooth);
            }
        } else if (i2 == 4) {
            name = context.getString(R.string.hdmi_media_device_name);
        } else if (i2 == 5) {
            String string2 = Q0.f289b;
            if (TextUtils.isEmpty(string2)) {
                string2 = context.getString(R.string.rpd_media_device_name);
            }
            name = string2;
            if (Q0.f296i != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append((Object) name);
                sb.append(" ");
                sb.append(Q0.f297j ? context.getString(R.string.rf_audio_type_usb) : context.getString(R.string.rf_audio_type_wifi));
                name = sb.toString();
            }
        } else if (i2 == 6) {
            name = context.getString(R.string.wireless_display_media_device_name);
        }
        return name == null ? "" : name.toString();
    }

    @Override // Y.t
    public final int g() {
        int i2 = R.array.audio_headset_volume;
        int i3 = this.f436j;
        if (i3 == 0) {
            return R.array.audio_phone_speaker_volume;
        }
        if (i3 == 2) {
            return i2;
        }
        if (i3 != 3) {
            return (i3 == 4 || i3 == 5 || i3 == 6) ? R.array.audio_local_volume : i2;
        }
        return r.Q(this.f439m) ? R.array.audio_local_volume : R.array.audio_bluetooth_volume;
    }

    @Override // Y.t
    public final void i(int i2) {
        boolean z2 = this.f437k;
        AudioManager audioManager = this.f444e;
        if (z2) {
            audioManager.setStreamVolume(6, i2, 0);
            return;
        }
        if (i2 < this.f435i) {
            i2++;
        }
        audioManager.setStreamVolume(0, i2, 0);
    }
}
