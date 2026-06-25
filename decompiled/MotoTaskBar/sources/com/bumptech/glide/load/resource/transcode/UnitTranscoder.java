package com.bumptech.glide.load.resource.transcode;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: classes.dex */
public class UnitTranscoder implements ResourceTranscoder {
    private static final UnitTranscoder UNIT_TRANSCODER = new UnitTranscoder();

    public static ResourceTranscoder get() {
        return UNIT_TRANSCODER;
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    public Resource transcode(Resource resource, Options options) {
        return resource;
    }
}
