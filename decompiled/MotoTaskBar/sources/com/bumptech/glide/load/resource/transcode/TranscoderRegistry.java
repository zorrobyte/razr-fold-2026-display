package com.bumptech.glide.load.resource.transcode;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TranscoderRegistry {
    private final List transcoders = new ArrayList();

    final class Entry {
        final Class fromClass;
        final Class toClass;
        final ResourceTranscoder transcoder;

        Entry(Class cls, Class cls2, ResourceTranscoder resourceTranscoder) {
            this.fromClass = cls;
            this.toClass = cls2;
            this.transcoder = resourceTranscoder;
        }

        public boolean handles(Class cls, Class cls2) {
            return this.fromClass.isAssignableFrom(cls) && cls2.isAssignableFrom(this.toClass);
        }
    }

    public synchronized ResourceTranscoder get(Class cls, Class cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return UnitTranscoder.get();
        }
        for (Entry entry : this.transcoders) {
            if (entry.handles(cls, cls2)) {
                return entry.transcoder;
            }
        }
        throw new IllegalArgumentException("No transcoder registered to transcode from " + cls + " to " + cls2);
    }

    public synchronized List getTranscodeClasses(Class cls, Class cls2) {
        ArrayList arrayList = new ArrayList();
        if (cls2.isAssignableFrom(cls)) {
            arrayList.add(cls2);
            return arrayList;
        }
        for (Entry entry : this.transcoders) {
            if (entry.handles(cls, cls2) && !arrayList.contains(entry.toClass)) {
                arrayList.add(entry.toClass);
            }
        }
        return arrayList;
    }

    public synchronized void register(Class cls, Class cls2, ResourceTranscoder resourceTranscoder) {
        this.transcoders.add(new Entry(cls, cls2, resourceTranscoder));
    }
}
