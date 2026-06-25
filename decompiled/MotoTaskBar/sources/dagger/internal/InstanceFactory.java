package dagger.internal;

import dagger.Lazy;

/* JADX INFO: loaded from: classes2.dex */
public final class InstanceFactory implements Factory, Lazy {
    private static final InstanceFactory NULL_INSTANCE_FACTORY = new InstanceFactory(null);
    private final Object instance;

    private InstanceFactory(Object obj) {
        this.instance = obj;
    }

    public static Factory create(Object obj) {
        obj.getClass();
        return new InstanceFactory(obj);
    }

    @Override // javax.inject.Provider
    public Object get() {
        return this.instance;
    }
}
