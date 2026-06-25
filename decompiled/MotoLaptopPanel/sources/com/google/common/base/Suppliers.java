package com.google.common.base;

import com.google.common.base.Suppliers;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public abstract class Suppliers {

    class MemoizingSupplier implements Supplier, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier delegate;
        volatile transient boolean initialized;
        private transient Object lock = new Object();
        transient Object value;

        MemoizingSupplier(Supplier supplier) {
            supplier.getClass();
            this.delegate = supplier;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.lock = new Object();
        }

        @Override // com.google.common.base.Supplier
        public Object get() {
            if (!this.initialized) {
                synchronized (this.lock) {
                    try {
                        if (!this.initialized) {
                            Object obj = this.delegate.get();
                            this.value = obj;
                            this.initialized = true;
                            return obj;
                        }
                    } finally {
                    }
                }
            }
            return NullnessCasts.uncheckedCastNullableTToT(this.value);
        }

        public String toString() {
            Object obj;
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoize(");
            if (this.initialized) {
                obj = "<supplier that returned " + this.value + ">";
            } else {
                obj = this.delegate;
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    class NonSerializableMemoizingSupplier implements Supplier {
        private static final Supplier SUCCESSFULLY_COMPUTED = new Supplier() { // from class: com.google.common.base.Suppliers$NonSerializableMemoizingSupplier$$ExternalSyntheticLambda0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return Suppliers.NonSerializableMemoizingSupplier.$r8$lambda$z7NQSm4H_wzbZnaYNFygi6b6f7I();
            }
        };
        private volatile Supplier delegate;
        private final Object lock = new Object();
        private Object value;

        public static /* synthetic */ Void $r8$lambda$z7NQSm4H_wzbZnaYNFygi6b6f7I() {
            throw new IllegalStateException();
        }

        NonSerializableMemoizingSupplier(Supplier supplier) {
            supplier.getClass();
            this.delegate = supplier;
        }

        @Override // com.google.common.base.Supplier
        public Object get() {
            Supplier supplier = this.delegate;
            Supplier supplier2 = SUCCESSFULLY_COMPUTED;
            if (supplier != supplier2) {
                synchronized (this.lock) {
                    try {
                        if (this.delegate != supplier2) {
                            Object obj = this.delegate.get();
                            this.value = obj;
                            this.delegate = supplier2;
                            return obj;
                        }
                    } finally {
                    }
                }
            }
            return NullnessCasts.uncheckedCastNullableTToT(this.value);
        }

        public String toString() {
            Object obj = this.delegate;
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoize(");
            if (obj == SUCCESSFULLY_COMPUTED) {
                obj = "<supplier that returned " + this.value + ">";
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    public static Supplier memoize(Supplier supplier) {
        return ((supplier instanceof NonSerializableMemoizingSupplier) || (supplier instanceof MemoizingSupplier)) ? supplier : supplier instanceof Serializable ? new MemoizingSupplier(supplier) : new NonSerializableMemoizingSupplier(supplier);
    }
}
