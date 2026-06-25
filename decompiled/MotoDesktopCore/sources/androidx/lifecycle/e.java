package androidx.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int[] f1702a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final /* synthetic */ int[] f1703b;

    static {
        int[] iArr = new int[b.values().length];
        f1703b = iArr;
        try {
            iArr[1] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1703b[2] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1703b[3] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f1703b[4] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f1703b[0] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        int[] iArr2 = new int[a.values().length];
        f1702a = iArr2;
        try {
            iArr2[a.ON_CREATE.ordinal()] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f1702a[a.ON_STOP.ordinal()] = 2;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f1702a[a.ON_START.ordinal()] = 3;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f1702a[a.ON_PAUSE.ordinal()] = 4;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f1702a[a.ON_RESUME.ordinal()] = 5;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            f1702a[a.ON_DESTROY.ordinal()] = 6;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            f1702a[a.ON_ANY.ordinal()] = 7;
        } catch (NoSuchFieldError unused12) {
        }
    }
}
