package com.bumptech.glide.load.model;

import androidx.core.util.Pools$Pool;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class MultiModelLoader implements ModelLoader {
    private final Pools$Pool exceptionListPool;
    private final List modelLoaders;

    class MultiFetcher implements DataFetcher, DataFetcher.DataCallback {
        private DataFetcher.DataCallback callback;
        private int currentIndex;
        private List exceptions;
        private final List fetchers;
        private boolean isCancelled;
        private Priority priority;
        private final Pools$Pool throwableListPool;

        MultiFetcher(List list, Pools$Pool pools$Pool) {
            this.throwableListPool = pools$Pool;
            Preconditions.checkNotEmpty(list);
            this.fetchers = list;
            this.currentIndex = 0;
        }

        private void startNextOrFail() {
            if (this.isCancelled) {
                return;
            }
            if (this.currentIndex < this.fetchers.size() - 1) {
                this.currentIndex++;
                loadData(this.priority, this.callback);
            } else {
                Preconditions.checkNotNull(this.exceptions);
                this.callback.onLoadFailed(new GlideException("Fetch failed", new ArrayList(this.exceptions)));
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cancel() {
            this.isCancelled = true;
            Iterator it = this.fetchers.iterator();
            while (it.hasNext()) {
                ((DataFetcher) it.next()).cancel();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cleanup() {
            List list = this.exceptions;
            if (list != null) {
                this.throwableListPool.release(list);
            }
            this.exceptions = null;
            Iterator it = this.fetchers.iterator();
            while (it.hasNext()) {
                ((DataFetcher) it.next()).cleanup();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public Class getDataClass() {
            return ((DataFetcher) this.fetchers.get(0)).getDataClass();
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public DataSource getDataSource() {
            return ((DataFetcher) this.fetchers.get(0)).getDataSource();
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void loadData(Priority priority, DataFetcher.DataCallback dataCallback) {
            this.priority = priority;
            this.callback = dataCallback;
            this.exceptions = (List) this.throwableListPool.acquire();
            ((DataFetcher) this.fetchers.get(this.currentIndex)).loadData(priority, this);
            if (this.isCancelled) {
                cancel();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
        public void onDataReady(Object obj) {
            if (obj != null) {
                this.callback.onDataReady(obj);
            } else {
                startNextOrFail();
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher.DataCallback
        public void onLoadFailed(Exception exc) {
            ((List) Preconditions.checkNotNull(this.exceptions)).add(exc);
            startNextOrFail();
        }
    }

    MultiModelLoader(List list, Pools$Pool pools$Pool) {
        this.modelLoaders = list;
        this.exceptionListPool = pools$Pool;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(Object obj, int i, int i2, Options options) {
        ModelLoader.LoadData loadDataBuildLoadData;
        int size = this.modelLoaders.size();
        ArrayList arrayList = new ArrayList(size);
        Key key = null;
        for (int i3 = 0; i3 < size; i3++) {
            ModelLoader modelLoader = (ModelLoader) this.modelLoaders.get(i3);
            if (modelLoader.handles(obj) && (loadDataBuildLoadData = modelLoader.buildLoadData(obj, i, i2, options)) != null) {
                key = loadDataBuildLoadData.sourceKey;
                arrayList.add(loadDataBuildLoadData.fetcher);
            }
        }
        if (arrayList.isEmpty() || key == null) {
            return null;
        }
        return new ModelLoader.LoadData(key, new MultiFetcher(arrayList, this.exceptionListPool));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(Object obj) {
        Iterator it = this.modelLoaders.iterator();
        while (it.hasNext()) {
            if (((ModelLoader) it.next()).handles(obj)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "MultiModelLoader{modelLoaders=" + Arrays.toString(this.modelLoaders.toArray()) + '}';
    }
}
