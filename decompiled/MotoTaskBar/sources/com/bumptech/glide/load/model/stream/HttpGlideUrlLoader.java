package com.bumptech.glide.load.model.stream;

import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

/* JADX INFO: loaded from: classes.dex */
public class HttpGlideUrlLoader implements ModelLoader {
    public static final Option TIMEOUT = Option.memory("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout", 2500);
    private final ModelCache modelCache;

    public class Factory implements ModelLoaderFactory {
        private final ModelCache modelCache = new ModelCache(500);

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new HttpGlideUrlLoader(this.modelCache);
        }
    }

    public HttpGlideUrlLoader(ModelCache modelCache) {
        this.modelCache = modelCache;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(GlideUrl glideUrl, int i, int i2, Options options) {
        ModelCache modelCache = this.modelCache;
        if (modelCache != null) {
            GlideUrl glideUrl2 = (GlideUrl) modelCache.get(glideUrl, 0, 0);
            if (glideUrl2 == null) {
                this.modelCache.put(glideUrl, 0, 0, glideUrl);
            } else {
                glideUrl = glideUrl2;
            }
        }
        return new ModelLoader.LoadData(glideUrl, new HttpUrlFetcher(glideUrl, ((Integer) options.get(TIMEOUT)).intValue()));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(GlideUrl glideUrl) {
        return true;
    }
}
