package kotlin.reflect;

/* JADX INFO: compiled from: KClasses.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KClasses {
    public static final Object cast(KClass kClass, Object obj) {
        kClass.getClass();
        if (kClass.isInstance(obj)) {
            obj.getClass();
            return obj;
        }
        throw new ClassCastException("Value cannot be cast to " + kClass.getQualifiedName());
    }
}
