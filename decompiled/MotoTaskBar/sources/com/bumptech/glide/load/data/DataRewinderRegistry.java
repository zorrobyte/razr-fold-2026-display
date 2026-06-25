package com.bumptech.glide.load.data;

import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DataRewinderRegistry {
    private static final DataRewinder.Factory DEFAULT_FACTORY = new DataRewinder.Factory() { // from class: com.bumptech.glide.load.data.DataRewinderRegistry.1
        @Override // com.bumptech.glide.load.data.DataRewinder.Factory
        public DataRewinder build(Object obj) {
            return new DefaultRewinder(obj);
        }

        @Override // com.bumptech.glide.load.data.DataRewinder.Factory
        public Class getDataClass() {
            throw new UnsupportedOperationException("Not implemented");
        }
    };
    private final Map rewinders = new HashMap();

    final class DefaultRewinder implements DataRewinder {
        private final Object data;

        DefaultRewinder(Object obj) {
            this.data = obj;
        }

        @Override // com.bumptech.glide.load.data.DataRewinder
        public void cleanup() {
        }

        @Override // com.bumptech.glide.load.data.DataRewinder
        public Object rewindAndGet() {
            return this.data;
        }
    }

    public synchronized DataRewinder build(Object obj) {
        DataRewinder.Factory factory;
        try {
            Preconditions.checkNotNull(obj);
            factory = (DataRewinder.Factory) this.rewinders.get(obj.getClass());
            if (factory == null) {
                Iterator it = this.rewinders.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    DataRewinder.Factory factory2 = (DataRewinder.Factory) it.next();
                    if (factory2.getDataClass().isAssignableFrom(obj.getClass())) {
                        factory = factory2;
                        break;
                    }
                }
            }
            if (factory == null) {
                factory = DEFAULT_FACTORY;
            }
        } catch (Throwable th) {
            throw th;
        }
        return factory.build(obj);
    }

    public synchronized void register(DataRewinder.Factory factory) {
        this.rewinders.put(factory.getDataClass(), factory);
    }
}
