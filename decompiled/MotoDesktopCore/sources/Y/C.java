package Y;

import X.Q0;
import android.content.Context;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public final class C extends t {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f348h;

    @Override // Y.t
    public final String a() {
        if (!this.f445f) {
            return "";
        }
        Context context = this.f440a;
        return r.U(context) ? context.getString(R.string.switch_to_tablet_mic) : context.getString(R.string.switch_to_phone_mic);
    }

    @Override // Y.t
    public final int b() {
        return this.f444e.getStreamVolume(3);
    }

    @Override // Y.t
    public final int c() {
        return 1000;
    }

    @Override // Y.t
    public final int d() {
        int i2 = R.drawable.ic_connect_pc;
        int i3 = Q0.f290c;
        return i3 != 1 ? i3 != 2 ? i3 != 201 ? i2 : R.drawable.ic_quest : R.drawable.ic_tablet : R.drawable.ic_phone;
    }

    @Override // Y.t
    public final int e() {
        return this.f348h;
    }

    @Override // Y.t
    public final String f() {
        String string = Q0.f289b;
        boolean zIsEmpty = TextUtils.isEmpty(string);
        Context context = this.f440a;
        if (zIsEmpty) {
            string = context.getString(R.string.rpd_media_device_name);
        }
        if (Q0.f296i == 0) {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(" ");
        sb.append(Q0.f297j ? context.getString(R.string.rf_audio_type_usb) : context.getString(R.string.rf_audio_type_wifi));
        return sb.toString();
    }

    @Override // Y.t
    public final int g() {
        return R.array.audio_local_volume;
    }

    @Override // Y.t
    public final void i(int i2) {
        if (Q0.f291d) {
            return;
        }
        this.f444e.setStreamVolume(3, i2, 0);
    }
}
