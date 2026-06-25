package com.motorola.plugin.core.channel.local;

/* JADX INFO: compiled from: LocalChannelTransferRegistryFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface LocalChannelTransferRegistryFactory {
    public static final Factory Factory = Factory.$$INSTANCE;

    /* JADX INFO: compiled from: LocalChannelTransferRegistryFactory.kt */
    public final class Factory {
        static final /* synthetic */ Factory $$INSTANCE = new Factory();

        private Factory() {
        }

        public final ILocalChannelTransferRegistry getOrCreate() {
            return LocalChannelTransferRegistry.INSTANCE;
        }
    }
}
