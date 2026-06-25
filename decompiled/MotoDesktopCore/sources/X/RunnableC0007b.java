package X;

import android.content.ContentValues;

/* JADX INFO: renamed from: X.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0007b implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f305a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f306b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C0013e f307c;

    public RunnableC0007b(C0013e c0013e, int i2, int i3) {
        this.f307c = c0013e;
        this.f305a = i2;
        this.f306b = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DeviceIndex", Integer.valueOf(this.f305a));
        contentValues.put("CurrentVolume", Integer.valueOf(this.f306b));
        this.f307c.f318a.getContentResolver().update(C0013e.f316f, contentValues, null, null);
    }
}
