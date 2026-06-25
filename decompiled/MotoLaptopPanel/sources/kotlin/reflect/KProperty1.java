package kotlin.reflect;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: KProperty.kt */
/* JADX INFO: loaded from: classes.dex */
public interface KProperty1 extends KProperty, Function1 {

    /* JADX INFO: compiled from: KProperty.kt */
    public interface Getter extends KFunction, Function1 {
    }

    Object get(Object obj);

    Getter getGetter();
}
