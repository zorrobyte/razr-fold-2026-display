package com.bumptech.glide.load.engine;

import androidx.core.util.Pools$Pool;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;

/* JADX INFO: loaded from: classes.dex */
final class LockedResource implements Resource, FactoryPools.Poolable {
    private static final Pools$Pool POOL = FactoryPools.threadSafe(20, new FactoryPools.Factory() { // from class: com.bumptech.glide.load.engine.LockedResource.1
        @Override // com.bumptech.glide.util.pool.FactoryPools.Factory
        public LockedResource create() {
            return new LockedResource();
        }
    });
    private boolean isLocked;
    private boolean isRecycled;
    private final StateVerifier stateVerifier = StateVerifier.newInstance();
    private Resource toWrap;

    LockedResource() {
    }

    private void init(Resource resource) {
        this.isRecycled = false;
        this.isLocked = true;
        this.toWrap = resource;
    }

    static LockedResource obtain(Resource resource) {
        LockedResource lockedResource = (LockedResource) Preconditions.checkNotNull((LockedResource) POOL.acquire());
        lockedResource.init(resource);
        return lockedResource;
    }

    private void release() {
        this.toWrap = null;
        POOL.release(this);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public Object get() {
        return this.toWrap.get();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public Class getResourceClass() {
        return this.toWrap.getResourceClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.toWrap.getSize();
    }

    @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public synchronized void recycle() {
        this.stateVerifier.throwIfRecycled();
        this.isRecycled = true;
        if (!this.isLocked) {
            this.toWrap.recycle();
            release();
        }
    }

    synchronized void unlock() {
        this.stateVerifier.throwIfRecycled();
        if (!this.isLocked) {
            throw new IllegalStateException("Already unlocked");
        }
        this.isLocked = false;
        if (this.isRecycled) {
            recycle();
        }
    }
}
