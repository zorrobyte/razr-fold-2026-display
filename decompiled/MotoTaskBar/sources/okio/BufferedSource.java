package okio;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/* JADX INFO: compiled from: BufferedSource.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface BufferedSource extends Source, ReadableByteChannel {
    Buffer buffer();

    Buffer getBuffer();

    long indexOf(ByteString byteString);

    long indexOfElement(ByteString byteString);

    InputStream inputStream();

    BufferedSource peek();

    byte readByte();

    boolean request(long j);

    int select(Options options);
}
