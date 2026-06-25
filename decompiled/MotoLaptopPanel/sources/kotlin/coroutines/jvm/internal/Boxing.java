package kotlin.coroutines.jvm.internal;

/* JADX INFO: compiled from: boxing.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Boxing {
    public static final Boolean boxBoolean(boolean z) {
        return Boolean.valueOf(z);
    }

    public static final Float boxFloat(float f) {
        return new Float(f);
    }

    public static final Integer boxInt(int i) {
        return new Integer(i);
    }

    public static final Long boxLong(long j) {
        return new Long(j);
    }
}
