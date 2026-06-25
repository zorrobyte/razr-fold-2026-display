package E;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    public static Class a(Class cls) {
        return Class.forName(cls.getPackage().getName() + "." + cls.getSimpleName() + "Parcelizer", false, cls.getClassLoader());
    }

    public abstract boolean b(int i2);

    public abstract void c(int i2);
}
