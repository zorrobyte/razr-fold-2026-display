package androidx.lifecycle;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final b f1696a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final b f1697b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final b f1698c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final b f1699d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final b f1700e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final /* synthetic */ b[] f1701f;

    static {
        b bVar = new b("DESTROYED", 0);
        f1696a = bVar;
        b bVar2 = new b("INITIALIZED", 1);
        f1697b = bVar2;
        b bVar3 = new b("CREATED", 2);
        f1698c = bVar3;
        b bVar4 = new b("STARTED", 3);
        f1699d = bVar4;
        b bVar5 = new b("RESUMED", 4);
        f1700e = bVar5;
        f1701f = new b[]{bVar, bVar2, bVar3, bVar4, bVar5};
    }

    public static b valueOf(String str) {
        return (b) Enum.valueOf(b.class, str);
    }

    public static b[] values() {
        return (b[]) f1701f.clone();
    }
}
