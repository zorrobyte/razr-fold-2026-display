package X;

import android.content.ContentValues;

/* JADX INFO: renamed from: X.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0009c implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ boolean f308a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0013e f309b;

    public RunnableC0009c(C0013e c0013e, boolean z2) {
        this.f309b = c0013e;
        this.f308a = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DeviceCheckboxUpdateState", Boolean.valueOf(this.f308a));
        this.f309b.f318a.getContentResolver().update(C0013e.f317g, contentValues, null, null);
    }
}
