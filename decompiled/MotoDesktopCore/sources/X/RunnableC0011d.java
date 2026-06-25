package X;

import android.content.ContentValues;
import com.motorola.mobiledesktop.core.desktop.DesktopFragment;

/* JADX INFO: renamed from: X.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0011d implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f310a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f311b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f312c;

    public /* synthetic */ RunnableC0011d(Object obj, int i2, int i3) {
        this.f310a = i3;
        this.f312c = obj;
        this.f311b = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f310a) {
            case 0:
                ContentValues contentValues = new ContentValues();
                contentValues.put("DeviceIndex", Integer.valueOf(this.f311b));
                ((C0013e) this.f312c).f318a.getContentResolver().update(C0013e.f315e, contentValues, null, null);
                break;
            default:
                ((DesktopFragment) ((B0) this.f312c).f260b).updateWhileHdmiConnect(this.f311b == 2);
                break;
        }
    }
}
