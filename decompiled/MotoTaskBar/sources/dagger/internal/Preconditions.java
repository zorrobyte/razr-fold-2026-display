package dagger.internal;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Preconditions {
    public static void checkBuilderRequirement(Object obj, Class cls) {
        if (obj != null) {
            return;
        }
        throw new IllegalStateException(cls.getCanonicalName() + " must be set");
    }
}
