package kotlin.properties;

import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: Interfaces.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ReadWriteProperty {
    Object getValue(Object obj, KProperty kProperty);

    void setValue(Object obj, KProperty kProperty, Object obj2);
}
