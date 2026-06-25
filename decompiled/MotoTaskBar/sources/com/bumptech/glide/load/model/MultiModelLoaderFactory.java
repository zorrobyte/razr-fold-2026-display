package com.bumptech.glide.load.model;

import androidx.core.util.Pools$Pool;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class MultiModelLoaderFactory {
    private static final Factory DEFAULT_FACTORY = new Factory();
    private static final ModelLoader EMPTY_MODEL_LOADER = new EmptyModelLoader();
    private final Set alreadyUsedEntries;
    private final List entries;
    private final Factory factory;
    private final Pools$Pool throwableListPool;

    class EmptyModelLoader implements ModelLoader {
        EmptyModelLoader() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoader
        public ModelLoader.LoadData buildLoadData(Object obj, int i, int i2, Options options) {
            return null;
        }

        @Override // com.bumptech.glide.load.model.ModelLoader
        public boolean handles(Object obj) {
            return false;
        }
    }

    class Entry {
        final Class dataClass;
        final ModelLoaderFactory factory;
        private final Class modelClass;

        public Entry(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
            this.modelClass = cls;
            this.dataClass = cls2;
            this.factory = modelLoaderFactory;
        }

        public boolean handles(Class cls) {
            return this.modelClass.isAssignableFrom(cls);
        }

        public boolean handles(Class cls, Class cls2) {
            return handles(cls) && this.dataClass.isAssignableFrom(cls2);
        }
    }

    class Factory {
        Factory() {
        }

        public MultiModelLoader build(List list, Pools$Pool pools$Pool) {
            return new MultiModelLoader(list, pools$Pool);
        }
    }

    public MultiModelLoaderFactory(Pools$Pool pools$Pool) {
        this(pools$Pool, DEFAULT_FACTORY);
    }

    MultiModelLoaderFactory(Pools$Pool pools$Pool, Factory factory) {
        this.entries = new ArrayList();
        this.alreadyUsedEntries = new HashSet();
        this.throwableListPool = pools$Pool;
        this.factory = factory;
    }

    private void add(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory, boolean z) {
        Entry entry = new Entry(cls, cls2, modelLoaderFactory);
        List list = this.entries;
        list.add(z ? list.size() : 0, entry);
    }

    private ModelLoader build(Entry entry) {
        return (ModelLoader) Preconditions.checkNotNull(entry.factory.build(this));
    }

    private static ModelLoader emptyModelLoader() {
        return EMPTY_MODEL_LOADER;
    }

    synchronized void append(Class cls, Class cls2, ModelLoaderFactory modelLoaderFactory) {
        add(cls, cls2, modelLoaderFactory, true);
    }

    public synchronized ModelLoader build(Class cls, Class cls2) {
        try {
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (Entry entry : this.entries) {
                if (this.alreadyUsedEntries.contains(entry)) {
                    z = true;
                } else if (entry.handles(cls, cls2)) {
                    this.alreadyUsedEntries.add(entry);
                    arrayList.add(build(entry));
                    this.alreadyUsedEntries.remove(entry);
                }
            }
            if (arrayList.size() > 1) {
                return this.factory.build(arrayList, this.throwableListPool);
            }
            if (arrayList.size() == 1) {
                return (ModelLoader) arrayList.get(0);
            }
            if (!z) {
                throw new Registry.NoModelLoaderAvailableException(cls, cls2);
            }
            return emptyModelLoader();
        } catch (Throwable th) {
            this.alreadyUsedEntries.clear();
            throw th;
        }
    }

    synchronized List build(Class cls) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (Entry entry : this.entries) {
                if (!this.alreadyUsedEntries.contains(entry) && entry.handles(cls)) {
                    this.alreadyUsedEntries.add(entry);
                    arrayList.add(build(entry));
                    this.alreadyUsedEntries.remove(entry);
                }
            }
        } finally {
        }
        return arrayList;
    }

    synchronized List getDataClasses(Class cls) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (Entry entry : this.entries) {
            if (!arrayList.contains(entry.dataClass) && entry.handles(cls)) {
                arrayList.add(entry.dataClass);
            }
        }
        return arrayList;
    }
}
