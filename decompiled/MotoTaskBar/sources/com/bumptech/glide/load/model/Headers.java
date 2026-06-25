package com.bumptech.glide.load.model;

import com.bumptech.glide.load.model.LazyHeaders;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface Headers {
    public static final Headers NONE = new Headers() { // from class: com.bumptech.glide.load.model.Headers.1
        @Override // com.bumptech.glide.load.model.Headers
        public Map getHeaders() {
            return Collections.EMPTY_MAP;
        }
    };
    public static final Headers DEFAULT = new LazyHeaders.Builder().build();

    Map getHeaders();
}
