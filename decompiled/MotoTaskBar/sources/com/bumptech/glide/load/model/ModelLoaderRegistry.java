package com.bumptech.glide.load.model;

import androidx.core.util.Pools$Pool;
import com.bumptech.glide.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ModelLoaderRegistry {
    private final ModelLoaderCache cache;
    private final MultiModelLoaderFactory multiModelLoaderFactory;

    class ModelLoaderCache {
        private final Map cachedModelLoaders = new HashMap();

        class Entry {
            final List loaders;

            public Entry(List list) {
                this.loaders = list;
            }
        }

        ModelLoaderCache() {
        }

        public void clear() {
            this.cachedModelLoaders.clear();
        }

        public List get(Class cls) {
            Entry entry = (Entry) this.cachedModelLoaders.get(cls);
            if (entry == null) {
                return null;
            }
            return entry.loaders;
        }

        public void put(Class cls, List list) {
            if (((Entry) this.cachedModelLoaders.put(cls, new Entry(list))) == null) {
                return;
            }
            throw new IllegalStateException("Already cached loaders for model: " + cls);
        }
    }

    public ModelLoaderRegistry(Pools$Pool pools$Pool) {
        this(new MultiModelLoaderFactory(pools$Pool));
    }

    private ModelLoaderRegistry(MultiModelLoaderFactory multiModelLoaderFactory) {
        this.cache = new ModelLoaderCache();
        this.multiModelLoaderFactory = multiModelLoaderFactory;
    }

    private static Class getClass(Object obj) {
        return obj.getClass();
    }

    private synchronized List getModelLoadersForClass(Class cls) {
        List listUnmodifiableList;
        listUnmodifiableList = this.cache.get(cls);
        if (listUnmodifiableList == null) {
            listUnmodifiableList = Collections.unmodifiableList(this.multiModelLoaderFactory.build(cls));
            this.cache.put(cls, listUnmodifiableList);
        }
        return listUnmodifiableList;
    }

    public synchronized void append(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
        this.multiModelLoaderFactory.append(cls, cls2, modelLoaderFactory);
        this.cache.clear();
    }

    public synchronized List getDataClasses(Class cls) {
        return this.multiModelLoaderFactory.getDataClasses(cls);
    }

    public List getModelLoaders(Object obj) {
        List modelLoadersForClass = getModelLoadersForClass(getClass(obj));
        if (modelLoadersForClass.isEmpty()) {
            throw new Registry.NoModelLoaderAvailableException(obj);
        }
        int size = modelLoadersForClass.size();
        List arrayList = Collections.EMPTY_LIST;
        boolean z = true;
        for (int i = 0; i < size; i++) {
            ModelLoader modelLoader = (ModelLoader) modelLoadersForClass.get(i);
            if (modelLoader.handles(obj)) {
                if (z) {
                    arrayList = new ArrayList(size - i);
                    z = false;
                }
                arrayList.add(modelLoader);
            }
        }
        if (arrayList.isEmpty()) {
            throw new Registry.NoModelLoaderAvailableException(obj, modelLoadersForClass);
        }
        return arrayList;
    }
}
