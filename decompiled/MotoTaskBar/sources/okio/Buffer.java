package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import kotlin.collections.ArraysKt;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: Buffer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Buffer implements BufferedSource, BufferedSink, Cloneable, ByteChannel {
    public Segment head;
    private long size;

    /* JADX INFO: compiled from: Buffer.kt */
    public final class UnsafeCursor implements Closeable {
        public Buffer buffer;
        public byte[] data;
        private Segment segment;
        public long offset = -1;
        public int start = -1;
        public int end = -1;

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.buffer = null;
            setSegment$external__okio__android_common__okio_lib(null);
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }

        public final void setSegment$external__okio__android_common__okio_lib(Segment segment) {
            this.segment = segment;
        }
    }

    public static /* synthetic */ Buffer writeTo$default(Buffer buffer, OutputStream outputStream, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = buffer.size;
        }
        return buffer.writeTo(outputStream, j);
    }

    @Override // okio.BufferedSource
    public Buffer buffer() {
        return this;
    }

    public final void clear() {
        skip(size());
    }

    public Buffer clone() {
        return copy();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public void close() {
    }

    public final Buffer copy() {
        Buffer buffer = new Buffer();
        if (size() == 0) {
            return buffer;
        }
        Segment segment = this.head;
        segment.getClass();
        Segment segmentSharedCopy = segment.sharedCopy();
        buffer.head = segmentSharedCopy;
        segmentSharedCopy.prev = segmentSharedCopy;
        segmentSharedCopy.next = segmentSharedCopy;
        for (Segment segment2 = segment.next; segment2 != segment; segment2 = segment2.next) {
            Segment segment3 = segmentSharedCopy.prev;
            segment3.getClass();
            segment2.getClass();
            segment3.push(segment2.sharedCopy());
        }
        buffer.setSize$external__okio__android_common__okio_lib(size());
        return buffer;
    }

    public final Buffer copyTo(Buffer buffer, long j, long j2) {
        buffer.getClass();
        long j3 = j;
        SegmentedByteString.checkOffsetAndCount(size(), j3, j2);
        if (j2 != 0) {
            buffer.setSize$external__okio__android_common__okio_lib(buffer.size() + j2);
            Segment segment = this.head;
            while (true) {
                segment.getClass();
                int i = segment.limit;
                int i2 = segment.pos;
                if (j3 < i - i2) {
                    break;
                }
                j3 -= (long) (i - i2);
                segment = segment.next;
            }
            Segment segment2 = segment;
            long j4 = j2;
            while (j4 > 0) {
                segment2.getClass();
                Segment segmentSharedCopy = segment2.sharedCopy();
                int i3 = segmentSharedCopy.pos + ((int) j3);
                segmentSharedCopy.pos = i3;
                segmentSharedCopy.limit = Math.min(i3 + ((int) j4), segmentSharedCopy.limit);
                Segment segment3 = buffer.head;
                if (segment3 == null) {
                    segmentSharedCopy.prev = segmentSharedCopy;
                    segmentSharedCopy.next = segmentSharedCopy;
                    buffer.head = segmentSharedCopy;
                } else {
                    segment3.getClass();
                    Segment segment4 = segment3.prev;
                    segment4.getClass();
                    segment4.push(segmentSharedCopy);
                }
                j4 -= (long) (segmentSharedCopy.limit - segmentSharedCopy.pos);
                segment2 = segment2.next;
                j3 = 0;
            }
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if (size() != buffer.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        Segment segment = this.head;
        segment.getClass();
        Segment segment2 = buffer.head;
        segment2.getClass();
        int i = segment.pos;
        int i2 = segment2.pos;
        long j = 0;
        while (j < size()) {
            long jMin = Math.min(segment.limit - i, segment2.limit - i2);
            long j2 = 0;
            while (j2 < jMin) {
                int i3 = i + 1;
                int i4 = i2 + 1;
                if (segment.data[i] != segment2.data[i2]) {
                    return false;
                }
                j2++;
                i = i3;
                i2 = i4;
            }
            if (i == segment.limit) {
                segment = segment.next;
                segment.getClass();
                i = segment.pos;
            }
            if (i2 == segment2.limit) {
                segment2 = segment2.next;
                segment2.getClass();
                i2 = segment2.pos;
            }
            j += jMin;
        }
        return true;
    }

    @Override // java.io.Flushable
    public void flush() {
    }

    @Override // okio.BufferedSource
    public Buffer getBuffer() {
        return this;
    }

    public final byte getByte(long j) {
        SegmentedByteString.checkOffsetAndCount(size(), j, 1L);
        Segment segment = this.head;
        segment.getClass();
        if (size() - j < j) {
            long size = size();
            while (size > j) {
                segment = segment.prev;
                segment.getClass();
                size -= (long) (segment.limit - segment.pos);
            }
            return segment.data[(int) ((((long) segment.pos) + j) - size)];
        }
        long j2 = 0;
        while (true) {
            int i = segment.limit;
            int i2 = segment.pos;
            long j3 = ((long) (i - i2)) + j2;
            if (j3 > j) {
                return segment.data[(int) ((((long) i2) + j) - j2)];
            }
            segment = segment.next;
            segment.getClass();
            j2 = j3;
        }
    }

    public int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = segment.limit;
            for (int i3 = segment.pos; i3 < i2; i3++) {
                i = (i * 31) + segment.data[i3];
            }
            segment = segment.next;
            segment.getClass();
        } while (segment != this.head);
        return i;
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString) {
        byteString.getClass();
        return indexOf(byteString, 0L);
    }

    public long indexOf(ByteString byteString, long j) {
        int i;
        long j2 = j;
        byteString.getClass();
        if (byteString.size() <= 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long size = 0;
        if (j2 < 0) {
            throw new IllegalArgumentException(("fromIndex < 0: " + j2).toString());
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1L;
        }
        if (size() - j2 < j2) {
            size = size();
            while (size > j2) {
                segment = segment.prev;
                segment.getClass();
                size -= (long) (segment.limit - segment.pos);
            }
            byte[] bArrInternalArray$external__okio__android_common__okio_lib = byteString.internalArray$external__okio__android_common__okio_lib();
            byte b = bArrInternalArray$external__okio__android_common__okio_lib[0];
            int size2 = byteString.size();
            long size3 = (size() - ((long) size2)) + 1;
            while (size < size3) {
                byte[] bArr = segment.data;
                int iMin = (int) Math.min(segment.limit, (((long) segment.pos) + size3) - size);
                i = (int) ((((long) segment.pos) + j2) - size);
                while (i < iMin) {
                    if (bArr[i] != b || !okio.internal.Buffer.rangeEquals(segment, i + 1, bArrInternalArray$external__okio__android_common__okio_lib, 1, size2)) {
                        i++;
                    }
                }
                size += (long) (segment.limit - segment.pos);
                segment = segment.next;
                segment.getClass();
                j2 = size;
            }
            return -1L;
        }
        while (true) {
            long j3 = ((long) (segment.limit - segment.pos)) + size;
            if (j3 > j2) {
                break;
            }
            segment = segment.next;
            segment.getClass();
            size = j3;
        }
        byte[] bArrInternalArray$external__okio__android_common__okio_lib2 = byteString.internalArray$external__okio__android_common__okio_lib();
        byte b2 = bArrInternalArray$external__okio__android_common__okio_lib2[0];
        int size4 = byteString.size();
        long size5 = (size() - ((long) size4)) + 1;
        while (size < size5) {
            byte[] bArr2 = segment.data;
            long j4 = size5;
            int iMin2 = (int) Math.min(segment.limit, (((long) segment.pos) + size5) - size);
            i = (int) ((((long) segment.pos) + j2) - size);
            while (i < iMin2) {
                if (bArr2[i] == b2 && okio.internal.Buffer.rangeEquals(segment, i + 1, bArrInternalArray$external__okio__android_common__okio_lib2, 1, size4)) {
                }
                i++;
            }
            size += (long) (segment.limit - segment.pos);
            segment = segment.next;
            segment.getClass();
            size5 = j4;
            j2 = size;
        }
        return -1L;
        return ((long) (i - segment.pos)) + size;
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString) {
        byteString.getClass();
        return indexOfElement(byteString, 0L);
    }

    public long indexOfElement(ByteString byteString, long j) {
        int i;
        int i2;
        byteString.getClass();
        long size = 0;
        if (j < 0) {
            throw new IllegalArgumentException(("fromIndex < 0: " + j).toString());
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1L;
        }
        if (size() - j < j) {
            size = size();
            while (size > j) {
                segment = segment.prev;
                segment.getClass();
                size -= (long) (segment.limit - segment.pos);
            }
            if (byteString.size() == 2) {
                byte b = byteString.getByte(0);
                byte b2 = byteString.getByte(1);
                while (size < size()) {
                    byte[] bArr = segment.data;
                    i = (int) ((((long) segment.pos) + j) - size);
                    int i3 = segment.limit;
                    while (i < i3) {
                        byte b3 = bArr[i];
                        if (b3 == b || b3 == b2) {
                            i2 = segment.pos;
                        } else {
                            i++;
                        }
                    }
                    size += (long) (segment.limit - segment.pos);
                    segment = segment.next;
                    segment.getClass();
                    j = size;
                }
            } else {
                byte[] bArrInternalArray$external__okio__android_common__okio_lib = byteString.internalArray$external__okio__android_common__okio_lib();
                while (size < size()) {
                    byte[] bArr2 = segment.data;
                    i = (int) ((((long) segment.pos) + j) - size);
                    int i4 = segment.limit;
                    while (i < i4) {
                        byte b4 = bArr2[i];
                        for (byte b5 : bArrInternalArray$external__okio__android_common__okio_lib) {
                            if (b4 == b5) {
                                i2 = segment.pos;
                            }
                        }
                        i++;
                    }
                    size += (long) (segment.limit - segment.pos);
                    segment = segment.next;
                    segment.getClass();
                    j = size;
                }
            }
            return -1L;
        }
        while (true) {
            long j2 = ((long) (segment.limit - segment.pos)) + size;
            if (j2 > j) {
                break;
            }
            segment = segment.next;
            segment.getClass();
            size = j2;
        }
        if (byteString.size() == 2) {
            byte b6 = byteString.getByte(0);
            byte b7 = byteString.getByte(1);
            while (size < size()) {
                byte[] bArr3 = segment.data;
                i = (int) ((((long) segment.pos) + j) - size);
                int i5 = segment.limit;
                while (i < i5) {
                    byte b8 = bArr3[i];
                    if (b8 == b6 || b8 == b7) {
                        i2 = segment.pos;
                    } else {
                        i++;
                    }
                }
                size += (long) (segment.limit - segment.pos);
                segment = segment.next;
                segment.getClass();
                j = size;
            }
        } else {
            byte[] bArrInternalArray$external__okio__android_common__okio_lib2 = byteString.internalArray$external__okio__android_common__okio_lib();
            while (size < size()) {
                byte[] bArr4 = segment.data;
                i = (int) ((((long) segment.pos) + j) - size);
                int i6 = segment.limit;
                while (i < i6) {
                    byte b9 = bArr4[i];
                    for (byte b10 : bArrInternalArray$external__okio__android_common__okio_lib2) {
                        if (b9 == b10) {
                            i2 = segment.pos;
                        }
                    }
                    i++;
                }
                size += (long) (segment.limit - segment.pos);
                segment = segment.next;
                segment.getClass();
                j = size;
            }
        }
        return -1L;
        return ((long) (i - i2)) + size;
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: okio.Buffer.inputStream.1
            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(Buffer.this.size(), Integer.MAX_VALUE);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() {
                if (Buffer.this.size() > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) {
                bArr.getClass();
                return Buffer.this.read(bArr, i, i2);
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return true;
    }

    @Override // okio.BufferedSource
    public BufferedSource peek() {
        return Okio.buffer(new PeekSource(this));
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) {
        byteBuffer.getClass();
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(byteBuffer.remaining(), segment.limit - segment.pos);
        byteBuffer.put(segment.data, segment.pos, iMin);
        int i = segment.pos + iMin;
        segment.pos = i;
        this.size -= (long) iMin;
        if (i == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return iMin;
    }

    public int read(byte[] bArr, int i, int i2) {
        bArr.getClass();
        SegmentedByteString.checkOffsetAndCount(bArr.length, i, i2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(i2, segment.limit - segment.pos);
        byte[] bArr2 = segment.data;
        int i3 = segment.pos;
        ArraysKt.copyInto(bArr2, bArr, i, i3, i3 + iMin);
        segment.pos += iMin;
        setSize$external__okio__android_common__okio_lib(size() - ((long) iMin));
        if (segment.pos == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return iMin;
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) {
        buffer.getClass();
        if (j < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        }
        if (size() == 0) {
            return -1L;
        }
        if (j > size()) {
            j = size();
        }
        buffer.write(this, j);
        return j;
    }

    @Override // okio.BufferedSource
    public byte readByte() {
        if (size() == 0) {
            throw new EOFException();
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        int i3 = i + 1;
        byte b = segment.data[i];
        setSize$external__okio__android_common__okio_lib(size() - 1);
        if (i3 != i2) {
            segment.pos = i3;
            return b;
        }
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return b;
    }

    public byte[] readByteArray(long j) throws EOFException {
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(("byteCount: " + j).toString());
        }
        if (size() < j) {
            throw new EOFException();
        }
        byte[] bArr = new byte[(int) j];
        readFully(bArr);
        return bArr;
    }

    public ByteString readByteString() {
        return readByteString(size());
    }

    public ByteString readByteString(long j) throws EOFException {
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(("byteCount: " + j).toString());
        }
        if (size() < j) {
            throw new EOFException();
        }
        if (j < 4096) {
            return new ByteString(readByteArray(j));
        }
        ByteString byteStringSnapshot = snapshot((int) j);
        skip(j);
        return byteStringSnapshot;
    }

    public void readFully(byte[] bArr) throws EOFException {
        bArr.getClass();
        int i = 0;
        while (i < bArr.length) {
            int i2 = read(bArr, i, bArr.length - i);
            if (i2 == -1) {
                throw new EOFException();
            }
            i += i2;
        }
    }

    public int readInt() throws EOFException {
        if (size() < 4) {
            throw new EOFException();
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 4) {
            return (readByte() & 255) | ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8);
        }
        byte[] bArr = segment.data;
        int i3 = i + 3;
        int i4 = ((bArr[i + 1] & 255) << 16) | ((bArr[i] & 255) << 24) | ((bArr[i + 2] & 255) << 8);
        int i5 = i + 4;
        int i6 = (bArr[i3] & 255) | i4;
        setSize$external__okio__android_common__okio_lib(size() - 4);
        if (i5 != i2) {
            segment.pos = i5;
            return i6;
        }
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return i6;
    }

    public String readString(long j, Charset charset) throws EOFException {
        charset.getClass();
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(("byteCount: " + j).toString());
        }
        if (this.size < j) {
            throw new EOFException();
        }
        if (j == 0) {
            return "";
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        if (((long) i) + j > segment.limit) {
            return new String(readByteArray(j), charset);
        }
        int i2 = (int) j;
        String str = new String(segment.data, i, i2, charset);
        int i3 = segment.pos + i2;
        segment.pos = i3;
        this.size -= j;
        if (i3 == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return str;
    }

    public String readUtf8() {
        return readString(this.size, Charsets.UTF_8);
    }

    public String readUtf8(long j) {
        return readString(j, Charsets.UTF_8);
    }

    @Override // okio.BufferedSource
    public boolean request(long j) {
        return this.size >= j;
    }

    @Override // okio.BufferedSource
    public int select(Options options) {
        options.getClass();
        int iSelectPrefix$default = okio.internal.Buffer.selectPrefix$default(this, options, false, 2, null);
        if (iSelectPrefix$default == -1) {
            return -1;
        }
        skip(options.getByteStrings$external__okio__android_common__okio_lib()[iSelectPrefix$default].size());
        return iSelectPrefix$default;
    }

    public final void setSize$external__okio__android_common__okio_lib(long j) {
        this.size = j;
    }

    public final long size() {
        return this.size;
    }

    public void skip(long j) {
        while (j > 0) {
            Segment segment = this.head;
            if (segment == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j, segment.limit - segment.pos);
            long j2 = iMin;
            setSize$external__okio__android_common__okio_lib(size() - j2);
            j -= j2;
            int i = segment.pos + iMin;
            segment.pos = i;
            if (i == segment.limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    public final ByteString snapshot() {
        if (size() <= 2147483647L) {
            return snapshot((int) size());
        }
        throw new IllegalStateException(("size > Int.MAX_VALUE: " + size()).toString());
    }

    public final ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        SegmentedByteString.checkOffsetAndCount(size(), 0L, i);
        Segment segment = this.head;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            segment.getClass();
            int i5 = segment.limit;
            int i6 = segment.pos;
            if (i5 == i6) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += i5 - i6;
            i4++;
            segment = segment.next;
        }
        byte[][] bArr = new byte[i4][];
        int[] iArr = new int[i4 * 2];
        Segment segment2 = this.head;
        int i7 = 0;
        while (i2 < i) {
            segment2.getClass();
            bArr[i7] = segment2.data;
            i2 += segment2.limit - segment2.pos;
            iArr[i7] = Math.min(i2, i);
            iArr[i7 + i4] = segment2.pos;
            segment2.shared = true;
            i7++;
            segment2 = segment2.next;
        }
        return new C0207SegmentedByteString(bArr, iArr);
    }

    public String toString() {
        return snapshot().toString();
    }

    public final Segment writableSegment$external__okio__android_common__okio_lib(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException("unexpected capacity");
        }
        Segment segment = this.head;
        if (segment != null) {
            segment.getClass();
            Segment segment2 = segment.prev;
            segment2.getClass();
            return (segment2.limit + i > 8192 || !segment2.owner) ? segment2.push(SegmentPool.take()) : segment2;
        }
        Segment segmentTake = SegmentPool.take();
        this.head = segmentTake;
        segmentTake.prev = segmentTake;
        segmentTake.next = segmentTake;
        return segmentTake;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) {
        byteBuffer.getClass();
        int iRemaining = byteBuffer.remaining();
        int i = iRemaining;
        while (i > 0) {
            Segment segmentWritableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
            int iMin = Math.min(i, 8192 - segmentWritableSegment$external__okio__android_common__okio_lib.limit);
            byteBuffer.get(segmentWritableSegment$external__okio__android_common__okio_lib.data, segmentWritableSegment$external__okio__android_common__okio_lib.limit, iMin);
            i -= iMin;
            segmentWritableSegment$external__okio__android_common__okio_lib.limit += iMin;
        }
        this.size += (long) iRemaining;
        return iRemaining;
    }

    public void write(Buffer buffer, long j) {
        Segment segment;
        buffer.getClass();
        if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        }
        SegmentedByteString.checkOffsetAndCount(buffer.size(), 0L, j);
        while (j > 0) {
            Segment segment2 = buffer.head;
            segment2.getClass();
            int i = segment2.limit;
            buffer.head.getClass();
            if (j < i - r1.pos) {
                Segment segment3 = this.head;
                if (segment3 != null) {
                    segment3.getClass();
                    segment = segment3.prev;
                } else {
                    segment = null;
                }
                if (segment != null && segment.owner) {
                    if ((((long) segment.limit) + j) - ((long) (segment.shared ? 0 : segment.pos)) <= 8192) {
                        Segment segment4 = buffer.head;
                        segment4.getClass();
                        segment4.writeTo(segment, (int) j);
                        buffer.setSize$external__okio__android_common__okio_lib(buffer.size() - j);
                        setSize$external__okio__android_common__okio_lib(size() + j);
                        return;
                    }
                }
                Segment segment5 = buffer.head;
                segment5.getClass();
                buffer.head = segment5.split((int) j);
            }
            Segment segment6 = buffer.head;
            segment6.getClass();
            long j2 = segment6.limit - segment6.pos;
            buffer.head = segment6.pop();
            Segment segment7 = this.head;
            if (segment7 == null) {
                this.head = segment6;
                segment6.prev = segment6;
                segment6.next = segment6;
            } else {
                segment7.getClass();
                Segment segment8 = segment7.prev;
                segment8.getClass();
                segment8.push(segment6).compact();
            }
            buffer.setSize$external__okio__android_common__okio_lib(buffer.size() - j2);
            setSize$external__okio__android_common__okio_lib(size() + j2);
            j -= j2;
        }
    }

    public long writeAll(Source source) {
        source.getClass();
        long j = 0;
        while (true) {
            long j2 = source.read(this, 8192L);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    @Override // okio.BufferedSink
    public Buffer writeByte(int i) {
        Segment segmentWritableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
        byte[] bArr = segmentWritableSegment$external__okio__android_common__okio_lib.data;
        int i2 = segmentWritableSegment$external__okio__android_common__okio_lib.limit;
        segmentWritableSegment$external__okio__android_common__okio_lib.limit = i2 + 1;
        bArr[i2] = (byte) i;
        setSize$external__okio__android_common__okio_lib(size() + 1);
        return this;
    }

    public Buffer writeInt(int i) {
        Segment segmentWritableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(4);
        byte[] bArr = segmentWritableSegment$external__okio__android_common__okio_lib.data;
        int i2 = segmentWritableSegment$external__okio__android_common__okio_lib.limit;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >>> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >>> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
        segmentWritableSegment$external__okio__android_common__okio_lib.limit = i2 + 4;
        setSize$external__okio__android_common__okio_lib(size() + 4);
        return this;
    }

    public final Buffer writeTo(OutputStream outputStream, long j) throws IOException {
        outputStream.getClass();
        SegmentedByteString.checkOffsetAndCount(this.size, 0L, j);
        Segment segment = this.head;
        long j2 = j;
        while (j2 > 0) {
            segment.getClass();
            int iMin = (int) Math.min(j2, segment.limit - segment.pos);
            outputStream.write(segment.data, segment.pos, iMin);
            int i = segment.pos + iMin;
            segment.pos = i;
            long j3 = iMin;
            this.size -= j3;
            j2 -= j3;
            if (i == segment.limit) {
                Segment segmentPop = segment.pop();
                this.head = segmentPop;
                SegmentPool.recycle(segment);
                segment = segmentPop;
            }
        }
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str) {
        str.getClass();
        return writeUtf8(str, 0, str.length());
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str, int i, int i2) {
        char cCharAt;
        str.getClass();
        if (i < 0) {
            throw new IllegalArgumentException(("beginIndex < 0: " + i).toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + i2 + " < " + i).toString());
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException(("endIndex > string.length: " + i2 + " > " + str.length()).toString());
        }
        while (i < i2) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 < 128) {
                Segment segmentWritableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
                byte[] bArr = segmentWritableSegment$external__okio__android_common__okio_lib.data;
                int i3 = segmentWritableSegment$external__okio__android_common__okio_lib.limit - i;
                int iMin = Math.min(i2, 8192 - i3);
                int i4 = i + 1;
                bArr[i + i3] = (byte) cCharAt2;
                while (true) {
                    i = i4;
                    if (i >= iMin || (cCharAt = str.charAt(i)) >= 128) {
                        break;
                    }
                    i4 = i + 1;
                    bArr[i + i3] = (byte) cCharAt;
                }
                int i5 = segmentWritableSegment$external__okio__android_common__okio_lib.limit;
                int i6 = (i3 + i) - i5;
                segmentWritableSegment$external__okio__android_common__okio_lib.limit = i5 + i6;
                setSize$external__okio__android_common__okio_lib(size() + ((long) i6));
            } else {
                if (cCharAt2 < 2048) {
                    Segment segmentWritableSegment$external__okio__android_common__okio_lib2 = writableSegment$external__okio__android_common__okio_lib(2);
                    byte[] bArr2 = segmentWritableSegment$external__okio__android_common__okio_lib2.data;
                    int i7 = segmentWritableSegment$external__okio__android_common__okio_lib2.limit;
                    bArr2[i7] = (byte) ((cCharAt2 >> 6) | 192);
                    bArr2[i7 + 1] = (byte) ((cCharAt2 & '?') | 128);
                    segmentWritableSegment$external__okio__android_common__okio_lib2.limit = i7 + 2;
                    setSize$external__okio__android_common__okio_lib(size() + 2);
                } else if (cCharAt2 < 55296 || cCharAt2 > 57343) {
                    Segment segmentWritableSegment$external__okio__android_common__okio_lib3 = writableSegment$external__okio__android_common__okio_lib(3);
                    byte[] bArr3 = segmentWritableSegment$external__okio__android_common__okio_lib3.data;
                    int i8 = segmentWritableSegment$external__okio__android_common__okio_lib3.limit;
                    bArr3[i8] = (byte) ((cCharAt2 >> '\f') | 224);
                    bArr3[i8 + 1] = (byte) ((63 & (cCharAt2 >> 6)) | 128);
                    bArr3[i8 + 2] = (byte) ((cCharAt2 & '?') | 128);
                    segmentWritableSegment$external__okio__android_common__okio_lib3.limit = i8 + 3;
                    setSize$external__okio__android_common__okio_lib(size() + 3);
                } else {
                    int i9 = i + 1;
                    char cCharAt3 = i9 < i2 ? str.charAt(i9) : (char) 0;
                    if (cCharAt2 > 56319 || 56320 > cCharAt3 || cCharAt3 >= 57344) {
                        writeByte(63);
                        i = i9;
                    } else {
                        int i10 = (((cCharAt2 & 1023) << 10) | (cCharAt3 & 1023)) + 65536;
                        Segment segmentWritableSegment$external__okio__android_common__okio_lib4 = writableSegment$external__okio__android_common__okio_lib(4);
                        byte[] bArr4 = segmentWritableSegment$external__okio__android_common__okio_lib4.data;
                        int i11 = segmentWritableSegment$external__okio__android_common__okio_lib4.limit;
                        bArr4[i11] = (byte) ((i10 >> 18) | 240);
                        bArr4[i11 + 1] = (byte) (((i10 >> 12) & 63) | 128);
                        bArr4[i11 + 2] = (byte) (((i10 >> 6) & 63) | 128);
                        bArr4[i11 + 3] = (byte) ((i10 & 63) | 128);
                        segmentWritableSegment$external__okio__android_common__okio_lib4.limit = i11 + 4;
                        setSize$external__okio__android_common__okio_lib(size() + 4);
                        i += 2;
                    }
                }
                i++;
            }
        }
        return this;
    }
}
