package okio;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: JvmOkio.kt */
/* JADX INFO: loaded from: classes.dex */
class InputStreamSource implements Source {
    private final InputStream input;
    private final Timeout timeout;

    public InputStreamSource(InputStream inputStream, Timeout timeout) {
        inputStream.getClass();
        timeout.getClass();
        this.input = inputStream;
        this.timeout = timeout;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() throws IOException {
        this.input.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        buffer.getClass();
        if (j == 0) {
            return 0L;
        }
        if (j < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        }
        try {
            this.timeout.throwIfReached();
            Segment segmentWritableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
            int i = this.input.read(segmentWritableSegment$external__okio__android_common__okio_lib.data, segmentWritableSegment$external__okio__android_common__okio_lib.limit, (int) Math.min(j, 8192 - segmentWritableSegment$external__okio__android_common__okio_lib.limit));
            if (i != -1) {
                segmentWritableSegment$external__okio__android_common__okio_lib.limit += i;
                long j2 = i;
                buffer.setSize$external__okio__android_common__okio_lib(buffer.size() + j2);
                return j2;
            }
            if (segmentWritableSegment$external__okio__android_common__okio_lib.pos != segmentWritableSegment$external__okio__android_common__okio_lib.limit) {
                return -1L;
            }
            buffer.head = segmentWritableSegment$external__okio__android_common__okio_lib.pop();
            SegmentPool.recycle(segmentWritableSegment$external__okio__android_common__okio_lib);
            return -1L;
        } catch (AssertionError e) {
            if (Okio.isAndroidGetsocknameError(e)) {
                throw new IOException(e);
            }
            throw e;
        }
    }

    public String toString() {
        return "source(" + this.input + ")";
    }
}
