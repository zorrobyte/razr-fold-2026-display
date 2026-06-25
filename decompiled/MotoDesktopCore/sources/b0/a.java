package b0;

import X.B0;
import com.motorola.mobiledesktop.core.desktop.tile.MobileDesktopTile;

/* JADX INFO: loaded from: classes.dex */
public final class a implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2011a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f2012b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f2013c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ B0 f2014d;

    public a(B0 b02, int i2, String str, String str2) {
        this.f2014d = b02;
        this.f2011a = i2;
        this.f2012b = str;
        this.f2013c = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ((MobileDesktopTile) this.f2014d.f260b).a(this.f2011a, this.f2012b, this.f2013c);
    }
}
