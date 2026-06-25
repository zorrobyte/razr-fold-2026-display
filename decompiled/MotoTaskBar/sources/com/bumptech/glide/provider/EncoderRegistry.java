package com.bumptech.glide.provider;

import com.bumptech.glide.load.Encoder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EncoderRegistry {
    private final List encoders = new ArrayList();

    final class Entry {
        private final Class dataClass;
        final Encoder encoder;

        Entry(Class cls, Encoder encoder) {
            this.dataClass = cls;
            this.encoder = encoder;
        }

        boolean handles(Class cls) {
            return this.dataClass.isAssignableFrom(cls);
        }
    }

    public synchronized void append(Class cls, Encoder encoder) {
        this.encoders.add(new Entry(cls, encoder));
    }

    public synchronized Encoder getEncoder(Class cls) {
        for (Entry entry : this.encoders) {
            if (entry.handles(cls)) {
                return entry.encoder;
            }
        }
        return null;
    }
}
