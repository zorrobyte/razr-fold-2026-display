package X;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import com.motorola.android.provider.MotorolaSettings;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: X.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0005a extends ContentObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f302a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f303b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0005a(Y.q qVar) {
        super(null);
        this.f302a = 1;
        this.f303b = qVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0005a(Object obj, Handler handler, int i2) {
        super(handler);
        this.f302a = i2;
        this.f303b = obj;
    }

    public void a() {
        ContentResolver contentResolver = ((Y.q) this.f303b).f421a.getContentResolver();
        ArrayList arrayList = new ArrayList();
        arrayList.add(Settings.Global.getUriFor("device_name"));
        arrayList.add(MotorolaSettings.Secure.getUriFor("is_allow_rdp_audio_output"));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            contentResolver.registerContentObserver((Uri) it.next(), false, this);
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        switch (this.f302a) {
            case 0:
                C0013e.a((C0013e) this.f303b);
                break;
            default:
                super.onChange(z2);
                break;
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2, Uri uri) {
        Object obj = this.f303b;
        switch (this.f302a) {
            case 1:
                onChange(z2);
                v0.a("AudioReceiver", "audioSwitchObserver onChange " + z2 + " rui = " + uri);
                if (uri != null && uri.toString() != null) {
                    Y.q qVar = (Y.q) obj;
                    if (uri.toString().contains("device_name")) {
                        Iterator it = qVar.f423c.iterator();
                        while (it.hasNext()) {
                            ((Y.f) it.next()).a();
                        }
                    } else if (uri.toString().contains("is_allow_rdp_audio_output")) {
                        int i2 = MotorolaSettings.Secure.getInt(qVar.f421a.getContentResolver(), "is_allow_rdp_audio_output", 0);
                        for (Y.f fVar : qVar.f423c) {
                            boolean z3 = i2 == 1;
                            fVar.getClass();
                            if (z3) {
                                Y.r.i0("persist.desktop.activity_rdp_media", "true");
                            } else {
                                Y.r.i0("persist.desktop.activity_rdp_media", "false");
                            }
                        }
                    }
                    break;
                }
                break;
            case 2:
                v0.a("A", "mSettingsObserver onChange(): uri=" + uri.toString());
                e0.A.a((e0.A) obj, "CHANGE_SETTINGS");
                break;
            default:
                super.onChange(z2, uri);
                break;
        }
    }
}
