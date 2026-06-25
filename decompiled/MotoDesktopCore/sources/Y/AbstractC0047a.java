package Y;

import android.os.SystemProperties;

/* JADX INFO: renamed from: Y.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0047a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final boolean f350a = SystemProperties.getBoolean("ro.vendor.audio.voip.hdmi.support", false);

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final boolean f351b = SystemProperties.getBoolean("ro.vendor.audio.voip.wifidisplay.support", false);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final boolean f352c = SystemProperties.getBoolean("ro.vendor.audio.voice.hdmi.support", false);

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final boolean f353d = SystemProperties.getBoolean("ro.vendor.audio.voice.wifidisplay.support", false);

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final String f354e = SystemProperties.get("ro.mot.product_wave");
}
