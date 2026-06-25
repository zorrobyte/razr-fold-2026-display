package kotlin;

import java.io.Serializable;

/* JADX INFO: compiled from: Lazy.kt */
/* JADX INFO: loaded from: classes.dex */
public final class InitializedLazyImpl implements Lazy, Serializable {
    private final Object value;

    public InitializedLazyImpl(Object obj) {
        this.value = obj;
    }

    @Override // kotlin.Lazy
    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(getValue());
    }
}
