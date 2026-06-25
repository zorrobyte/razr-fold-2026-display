package Y;

import X.C0005a;
import X.K0;
import X.Q0;
import X.z0;
import android.content.Context;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.MediaRouter2Manager;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f421a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final MediaRouter2Manager f422b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final ArrayList f423c;
    private final PhoneStateListener callStateListener;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final C0005a f424d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f425e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final K0 f426f;

    public q(Context context, AudioManager audioManager, MediaRouter2Manager mediaRouter2Manager, DisplayManager displayManager, TelephonyManager telephonyManager) {
        p pVar = new p(this);
        o oVar = new o(this);
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        this.f423c = new ArrayList();
        this.f425e = -1;
        k kVar = new k(this);
        z0 z0Var = new z0(1, this);
        this.callStateListener = z0Var;
        j jVar = new j(1, this);
        l lVar = new l(this);
        m mVar = new m(this);
        n nVar = new n(this);
        this.f426f = new K0(2, this);
        this.f421a = context;
        this.f422b = mediaRouter2Manager;
        synchronized (q.class) {
            try {
                mediaRouter2Manager.registerCallback(executorServiceNewSingleThreadExecutor, pVar);
                Q0.f292e.add(oVar);
                displayManager.registerDisplayListener(kVar, null);
                telephonyManager.listen(z0Var, 32);
                audioManager.registerAudioPlaybackCallback(jVar, handler);
                audioManager.registerAudioRecordingCallback(lVar, handler);
                audioManager.addOnModeChangedListener(context.getMainExecutor(), mVar);
                audioManager.addOnCommunicationDeviceChangedListener(context.getMainExecutor(), nVar);
                a();
                if (this.f424d == null) {
                    C0005a c0005a = new C0005a(this);
                    this.f424d = c0005a;
                    c0005a.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("com.motorola.server.telecom.action.CALL_AUDIO_ROUTE_CHANGE");
        intentFilter.addAction("com.motorola.mobiledesktop.action.call_audio_switch");
        this.f421a.registerReceiver(this.f426f, intentFilter, 2);
    }
}
