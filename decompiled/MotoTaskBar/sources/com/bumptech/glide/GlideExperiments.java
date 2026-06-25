package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GlideExperiments {
    private final Map experiments;

    final class Builder {
        private final Map experiments = new HashMap();

        Builder() {
        }

        GlideExperiments build() {
            return new GlideExperiments(this);
        }
    }

    GlideExperiments(Builder builder) {
        this.experiments = Collections.unmodifiableMap(new HashMap(builder.experiments));
    }

    public boolean isEnabled(Class cls) {
        return this.experiments.containsKey(cls);
    }
}
