package okio;

import java.io.Closeable;

/* JADX INFO: compiled from: Source.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface Source extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    void close();

    long read(Buffer buffer, long j);
}
