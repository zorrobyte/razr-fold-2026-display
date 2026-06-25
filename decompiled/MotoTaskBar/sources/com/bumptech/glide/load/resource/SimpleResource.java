package com.bumptech.glide.load.resource;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public abstract class SimpleResource implements Resource {
    protected final Object data;

    public SimpleResource(Object obj) {
        this.data = Preconditions.checkNotNull(obj);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public final Object get() {
        return this.data;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public Class getResourceClass() {
        return this.data.getClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public final int getSize() {
        return 1;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
    }
}
