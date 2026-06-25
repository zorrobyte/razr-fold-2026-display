package X;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.motorola.android.provider.MotorolaSettings;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class Q0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static boolean f288a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static String f289b = "";

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static int f290c = -1;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static boolean f291d = false;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static AudioPolicy f293f;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final ArrayList f292e = new ArrayList();

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static int f294g = -1;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static String f295h = "";

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static int f296i = 0;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static boolean f297j = false;

    public static AudioTrack a(AudioFormat audioFormat, AudioManager audioManager) {
        AudioPolicy audioPolicy;
        AudioMix audioMixBuild = new AudioMix.Builder(new AudioMixingRule.Builder().addMixRule(2, new AudioAttributes.Builder().setCapturePreset(1).build()).setTargetMixRole(1).build()).setFormat(audioFormat).setRouteFlags(2).build();
        if (audioManager != null && (audioPolicy = f293f) != null) {
            audioManager.unregisterAudioPolicyAsync(audioPolicy);
            f293f = null;
        }
        AudioPolicy audioPolicyBuild = new AudioPolicy.Builder((Context) null).addMix(audioMixBuild).build();
        f293f = audioPolicyBuild;
        if (audioManager.registerAudioPolicy(audioPolicyBuild) != 0) {
            throw new UnsupportedOperationException("Error: could not register audio policy");
        }
        AudioTrack audioTrackCreateAudioTrackSource = f293f.createAudioTrackSource(audioMixBuild);
        if (audioTrackCreateAudioTrackSource != null) {
            return audioTrackCreateAudioTrackSource;
        }
        throw new UnsupportedOperationException("Cannot create injection AudioTrack");
    }

    public static void b(Context context, String str, boolean z2) {
        v0.a("Utils", "enableDisableComponent:" + str + ", enable:" + z2);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, str), z2 ? 1 : 2, 1);
    }

    public static void c(Context context) {
        boolean zHasSystemFeature = context.getPackageManager().hasSystemFeature("com.motorola.readyforservice.qs");
        boolean zEquals = "1".equals(SystemProperties.get("ro.product.motodesktop"));
        boolean zEquals2 = "true".equals(SystemProperties.get("ro.vendor.hw.displayport"));
        boolean zF = f(context);
        boolean z2 = false;
        boolean z3 = SystemProperties.getBoolean("ro.product.is_prc", false);
        v0.a("Utils", "enableDisableComponents, isCoreShowEntryFeature:" + zHasSystemFeature + ", isSupportMotoDesktopMode:" + zEquals + ", isSupportHdmi:" + zEquals2 + ", isPrc:" + z3 + ", isReadyforEnabled:" + zF);
        b(context, "com.motorola.mobiledesktop.core.MainActivity", zHasSystemFeature && ((!z3 && zEquals && zEquals2) || zF));
        b(context, "com.motorola.mobiledesktop.core.DesktopActivityAlias", zHasSystemFeature && ((!z3 && zEquals && zEquals2) || zF));
        if (zHasSystemFeature && zF) {
            z2 = true;
        }
        b(context, "com.motorola.mobiledesktop.core.desktop.tile.MobileDesktopTile", z2);
        l(context, zF);
    }

    public static int d(String str, String str2) {
        try {
            Class<?> cls = Class.forName(str);
            return cls.getField(str2).getInt(cls);
        } catch (Exception e2) {
            if (!g()) {
                v0.c("Utils", "getFieldValue Exception!", e2);
            }
            return 0;
        }
    }

    public static boolean e(String str, String str2) {
        try {
            for (Method method : Class.forName(str).getDeclaredMethods()) {
                if (method.getName().equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            v0.c("Utils", "isMethodExists Exception", e2);
            return false;
        }
    }

    public static boolean f(Context context) {
        boolean z2 = false;
        try {
            z2 = context.getPackageManager().getPackageInfo("com.motorola.mobiledesktop", 0).applicationInfo.enabled;
            v0.a("Utils", "MobileDesktop enabled status:" + z2);
            return z2;
        } catch (Exception e2) {
            v0.a("Utils", "Get MobileDesktop applicationInfo error:" + e2.toString());
            return z2;
        }
    }

    public static boolean g() {
        if (f294g == -1) {
            f294g = !SystemProperties.get("ro.com.zui.version").isEmpty() ? 1 : 0;
        }
        return f294g == 1;
    }

    public static void h(String str, int i2) {
        if (TextUtils.equals(f289b, str) && f290c == i2) {
            return;
        }
        f289b = str;
        f290c = i2;
        for (Y.o oVar : f292e) {
            oVar.getClass();
            v0.a("AudioReceiver", "onClientDeviceInfoChange");
            Iterator it = oVar.f419a.f423c.iterator();
            while (it.hasNext()) {
                ((Y.f) it.next()).a();
            }
        }
    }

    public static void i(boolean z2) {
        v0.a("Utils", "setDuringSwitchState " + z2);
        f291d = z2;
    }

    public static void j(boolean z2) {
        f288a = z2;
        String str = z2 ? "true" : "false";
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, "mot.rdp.audio_output_activated", str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        for (Y.o oVar : f292e) {
            oVar.getClass();
            v0.a("AudioReceiver", "onRdpAudioConnectStateChange isConnect = " + z2);
            Iterator it = oVar.f419a.f423c.iterator();
            while (it.hasNext()) {
                Y.i iVar = ((Y.f) it.next()).f360a;
                if (iVar.f396i != z2) {
                    iVar.f396i = z2;
                    iVar.x();
                    iVar.f378I = false;
                    if (iVar.f396i) {
                        Y.i.b(iVar, 500);
                    } else {
                        MotorolaSettings.Secure.putInt(iVar.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0);
                    }
                }
            }
        }
    }

    public static void k(int i2, boolean z2) {
        v0.g("Utils", "mReadyForConnectedType = " + i2 + " mIsReadyForConnectedUseUsb = " + z2);
        f296i = i2;
        f297j = z2;
    }

    public static void l(Context context, boolean z2) {
        v0.a("Utils", "updateKeepAliveService isReadyforEnabled:" + z2);
        b(context, "com.motorola.mobiledesktop.core.WakeupService", z2);
    }
}
