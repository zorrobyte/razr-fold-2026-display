package okio;

import kotlin.collections.ArraysKt;

/* JADX INFO: renamed from: okio.SegmentedByteString, reason: case insensitive filesystem */
/* JADX INFO: compiled from: SegmentedByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class C0207SegmentedByteString extends ByteString {
    private final transient int[] directory;
    private final transient byte[][] segments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0207SegmentedByteString(byte[][] bArr, int[] iArr) {
        super(ByteString.EMPTY.getData$external__okio__android_common__okio_lib());
        bArr.getClass();
        iArr.getClass();
        this.segments = bArr;
        this.directory = iArr;
    }

    private final ByteString toByteString() {
        return new ByteString(toByteArray());
    }

    private final Object writeReplace() {
        ByteString byteString = toByteString();
        byteString.getClass();
        return byteString;
    }

    @Override // okio.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(0, byteString, 0, size())) {
                return true;
            }
        }
        return false;
    }

    public final int[] getDirectory$external__okio__android_common__okio_lib() {
        return this.directory;
    }

    public final byte[][] getSegments$external__okio__android_common__okio_lib() {
        return this.segments;
    }

    @Override // okio.ByteString
    public int getSize$external__okio__android_common__okio_lib() {
        return getDirectory$external__okio__android_common__okio_lib()[getSegments$external__okio__android_common__okio_lib().length - 1];
    }

    @Override // okio.ByteString
    public int hashCode() {
        int hashCode$external__okio__android_common__okio_lib = getHashCode$external__okio__android_common__okio_lib();
        if (hashCode$external__okio__android_common__okio_lib != 0) {
            return hashCode$external__okio__android_common__okio_lib;
        }
        int length = getSegments$external__okio__android_common__okio_lib().length;
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        while (i < length) {
            int i4 = getDirectory$external__okio__android_common__okio_lib()[length + i];
            int i5 = getDirectory$external__okio__android_common__okio_lib()[i];
            byte[] bArr = getSegments$external__okio__android_common__okio_lib()[i];
            int i6 = (i5 - i3) + i4;
            while (i4 < i6) {
                i2 = (i2 * 31) + bArr[i4];
                i4++;
            }
            i++;
            i3 = i5;
        }
        setHashCode$external__okio__android_common__okio_lib(i2);
        return i2;
    }

    @Override // okio.ByteString
    public String hex() {
        return toByteString().hex();
    }

    @Override // okio.ByteString
    public byte[] internalArray$external__okio__android_common__okio_lib() {
        return toByteArray();
    }

    @Override // okio.ByteString
    public byte internalGet$external__okio__android_common__okio_lib(int i) {
        SegmentedByteString.checkOffsetAndCount(getDirectory$external__okio__android_common__okio_lib()[getSegments$external__okio__android_common__okio_lib().length - 1], i, 1L);
        int iSegment = okio.internal.SegmentedByteString.segment(this, i);
        return getSegments$external__okio__android_common__okio_lib()[iSegment][(i - (iSegment == 0 ? 0 : getDirectory$external__okio__android_common__okio_lib()[iSegment - 1])) + getDirectory$external__okio__android_common__okio_lib()[getSegments$external__okio__android_common__okio_lib().length + iSegment]];
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        byteString.getClass();
        if (i < 0 || i > size() - i3) {
            return false;
        }
        int i4 = i3 + i;
        int iSegment = okio.internal.SegmentedByteString.segment(this, i);
        while (i < i4) {
            int i5 = iSegment == 0 ? 0 : getDirectory$external__okio__android_common__okio_lib()[iSegment - 1];
            int i6 = getDirectory$external__okio__android_common__okio_lib()[iSegment] - i5;
            int i7 = getDirectory$external__okio__android_common__okio_lib()[getSegments$external__okio__android_common__okio_lib().length + iSegment];
            int iMin = Math.min(i4, i6 + i5) - i;
            if (!byteString.rangeEquals(i2, getSegments$external__okio__android_common__okio_lib()[iSegment], i7 + (i - i5), iMin)) {
                return false;
            }
            i2 += iMin;
            i += iMin;
            iSegment++;
        }
        return true;
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        bArr.getClass();
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i4 = i3 + i;
        int iSegment = okio.internal.SegmentedByteString.segment(this, i);
        while (i < i4) {
            int i5 = iSegment == 0 ? 0 : getDirectory$external__okio__android_common__okio_lib()[iSegment - 1];
            int i6 = getDirectory$external__okio__android_common__okio_lib()[iSegment] - i5;
            int i7 = getDirectory$external__okio__android_common__okio_lib()[getSegments$external__okio__android_common__okio_lib().length + iSegment];
            int iMin = Math.min(i4, i6 + i5) - i;
            if (!SegmentedByteString.arrayRangeEquals(getSegments$external__okio__android_common__okio_lib()[iSegment], i7 + (i - i5), bArr, i2, iMin)) {
                return false;
            }
            i2 += iMin;
            i += iMin;
            iSegment++;
        }
        return true;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[size()];
        int length = getSegments$external__okio__android_common__okio_lib().length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int i4 = getDirectory$external__okio__android_common__okio_lib()[length + i];
            int i5 = getDirectory$external__okio__android_common__okio_lib()[i];
            int i6 = i5 - i2;
            ArraysKt.copyInto(getSegments$external__okio__android_common__okio_lib()[i], bArr, i3, i4, i4 + i6);
            i3 += i6;
            i++;
            i2 = i5;
        }
        return bArr;
    }

    @Override // okio.ByteString
    public String toString() {
        return toByteString().toString();
    }
}
