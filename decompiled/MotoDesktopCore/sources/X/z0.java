package X;

import android.telephony.PhoneStateListener;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class z0 extends PhoneStateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f345a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f346b;

    public /* synthetic */ z0(int i2, Object obj) {
        this.f345a = i2;
        this.f346b = obj;
    }

    @Override // android.telephony.PhoneStateListener
    public final void onCallStateChanged(int i2, String str) {
        switch (this.f345a) {
            case 0:
                MotoDesktopService motoDesktopService = (MotoDesktopService) this.f346b;
                try {
                    InterfaceC0039s interfaceC0039s = motoDesktopService.f2243A;
                    if (interfaceC0039s != null) {
                        ((C0037q) interfaceC0039s).a(i2, str);
                    }
                } catch (Exception e2) {
                    v0.a("MotoDesktopService", "mCallStateListener died error: " + e2.toString());
                    motoDesktopService.f2243A = null;
                    e2.printStackTrace();
                }
                super.onCallStateChanged(i2, str);
                break;
            default:
                w0.b(i2, "onCallStateChanged state = ", "AudioReceiver");
                Iterator it = ((Y.q) this.f346b).f423c.iterator();
                while (it.hasNext()) {
                    Y.i iVar = ((Y.f) it.next()).f360a;
                    int iQ = iVar.q();
                    if (i2 == 0) {
                        iVar.f400m = false;
                        iVar.f385P = false;
                        if (iQ != 3 && iQ != 6) {
                            iVar.l(true);
                        } else if (iVar.f380K) {
                            Y.r.h0(iVar.f398k, 0, false);
                            iVar.f380K = false;
                        }
                    } else if (i2 == 2) {
                        iVar.f385P = true;
                        if (iQ == 2 || iQ == 5) {
                            iVar.f400m = true;
                            iVar.k(true);
                        }
                    }
                }
                break;
        }
    }
}
