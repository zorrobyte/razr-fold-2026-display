package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Iterators {

    final class SingletonIterator extends UnmodifiableIterator {
        private boolean done;
        private final Object value;

        SingletonIterator(Object obj) {
            this.value = obj;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.done;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.done) {
                throw new NoSuchElementException();
            }
            this.done = true;
            return this.value;
        }
    }

    public static boolean elementsEqual(Iterator it, Iterator it2) {
        while (it.hasNext()) {
            if (!it2.hasNext() || !Objects.equal(it.next(), it2.next())) {
                return false;
            }
        }
        return !it2.hasNext();
    }

    public static UnmodifiableIterator singletonIterator(Object obj) {
        return new SingletonIterator(obj);
    }
}
