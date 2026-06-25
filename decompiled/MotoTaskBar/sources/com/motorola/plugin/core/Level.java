package com.motorola.plugin.core;

/* JADX INFO: compiled from: PluginConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum Level {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ASSERT(7);

    private final int level;

    Level(int i) {
        this.level = i;
    }

    public final int getLevel() {
        return this.level;
    }
}
