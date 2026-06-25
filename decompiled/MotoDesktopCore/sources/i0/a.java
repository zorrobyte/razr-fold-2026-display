package i0;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final boolean f2629a;

    static {
        boolean z2;
        try {
            Class.forName("com.motorola.android.dragdrop.IMotoDragDropRfCallback$Stub");
            z2 = true;
        } catch (Exception unused) {
            z2 = false;
        }
        f2629a = z2;
    }
}
