package kotlin.reflect;

/* JADX INFO: compiled from: KClass.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface KClass extends KDeclarationContainer {
    String getQualifiedName();

    String getSimpleName();

    int hashCode();

    boolean isInstance(Object obj);
}
