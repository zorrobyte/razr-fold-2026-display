package com.bumptech.glide.provider;

import androidx.collection.ArrayMap;
import com.bumptech.glide.load.engine.DecodePath;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.resource.transcode.UnitTranscoder;
import com.bumptech.glide.util.MultiClassKey;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class LoadPathCache {
    private static final LoadPath NO_PATHS_SIGNAL = new LoadPath(Object.class, Object.class, Object.class, Collections.singletonList(new DecodePath(Object.class, Object.class, Object.class, Collections.EMPTY_LIST, new UnitTranscoder(), null)), null);
    private final ArrayMap cache = new ArrayMap();
    private final AtomicReference keyRef = new AtomicReference();

    private MultiClassKey getKey(Class cls, Class cls2, Class cls3) {
        MultiClassKey multiClassKey = (MultiClassKey) this.keyRef.getAndSet(null);
        if (multiClassKey == null) {
            multiClassKey = new MultiClassKey();
        }
        multiClassKey.set(cls, cls2, cls3);
        return multiClassKey;
    }

    public LoadPath get(Class cls, Class cls2, Class cls3) {
        LoadPath loadPath;
        MultiClassKey key = getKey(cls, cls2, cls3);
        synchronized (this.cache) {
            loadPath = (LoadPath) this.cache.get(key);
        }
        this.keyRef.set(key);
        return loadPath;
    }

    public boolean isEmptyLoadPath(LoadPath loadPath) {
        return NO_PATHS_SIGNAL.equals(loadPath);
    }

    public void put(Class cls, Class cls2, Class cls3, LoadPath loadPath) {
        synchronized (this.cache) {
            ArrayMap arrayMap = this.cache;
            MultiClassKey multiClassKey = new MultiClassKey(cls, cls2, cls3);
            if (loadPath == null) {
                loadPath = NO_PATHS_SIGNAL;
            }
            arrayMap.put(multiClassKey, loadPath);
        }
    }
}
