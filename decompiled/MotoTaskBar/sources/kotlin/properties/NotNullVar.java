package kotlin.properties;

import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: Delegates.kt */
/* JADX INFO: loaded from: classes2.dex */
final class NotNullVar implements ReadWriteProperty {
    private Object value;

    @Override // kotlin.properties.ReadWriteProperty
    public Object getValue(Object obj, KProperty kProperty) {
        kProperty.getClass();
        Object obj2 = this.value;
        if (obj2 != null) {
            return obj2;
        }
        throw new IllegalStateException("Property " + kProperty.getName() + " should be initialized before get.");
    }

    @Override // kotlin.properties.ReadWriteProperty
    public void setValue(Object obj, KProperty kProperty, Object obj2) {
        kProperty.getClass();
        obj2.getClass();
        this.value = obj2;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("NotNullProperty(");
        if (this.value != null) {
            str = "value=" + this.value;
        } else {
            str = "value not initialized yet";
        }
        sb.append(str);
        sb.append(')');
        return sb.toString();
    }
}
