package okio;

import java.io.Closeable;
import java.io.Flushable;
import java.nio.channels.WritableByteChannel;

/* JADX INFO: compiled from: BufferedSink.kt */
/* JADX INFO: loaded from: classes.dex */
public interface BufferedSink extends Closeable, Flushable, WritableByteChannel {
    BufferedSink writeByte(int i);

    BufferedSink writeUtf8(String str);

    BufferedSink writeUtf8(String str, int i, int i2);
}
