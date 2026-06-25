package androidx.lifecycle;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final /* synthetic */ a[] $VALUES;
    public static final a ON_ANY;
    public static final a ON_CREATE;
    public static final a ON_DESTROY;
    public static final a ON_PAUSE;
    public static final a ON_RESUME;
    public static final a ON_START;
    public static final a ON_STOP;

    static {
        a aVar = new a("ON_CREATE", 0);
        ON_CREATE = aVar;
        a aVar2 = new a("ON_START", 1);
        ON_START = aVar2;
        a aVar3 = new a("ON_RESUME", 2);
        ON_RESUME = aVar3;
        a aVar4 = new a("ON_PAUSE", 3);
        ON_PAUSE = aVar4;
        a aVar5 = new a("ON_STOP", 4);
        ON_STOP = aVar5;
        a aVar6 = new a("ON_DESTROY", 5);
        ON_DESTROY = aVar6;
        a aVar7 = new a("ON_ANY", 6);
        ON_ANY = aVar7;
        $VALUES = new a[]{aVar, aVar2, aVar3, aVar4, aVar5, aVar6, aVar7};
    }

    public static a valueOf(String str) {
        return (a) Enum.valueOf(a.class, str);
    }

    public static a[] values() {
        return (a[]) $VALUES.clone();
    }
}
