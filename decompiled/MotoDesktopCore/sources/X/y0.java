package X;

import android.os.IBinder;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class y0 implements IBinder.DeathRecipient {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0041u f343a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f344b;

    public y0(MotoDesktopService motoDesktopService, InterfaceC0041u interfaceC0041u) {
        this.f344b = motoDesktopService;
        this.f343a = interfaceC0041u;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        MotoDesktopService.c(this.f344b, this.f343a);
    }
}
