package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ByteString.kt */
/* JADX INFO: loaded from: classes2.dex */
public class ByteString implements Serializable, Comparable {
    public static final Companion Companion = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    private transient int hashCode;
    private transient String utf8;

    /* JADX INFO: compiled from: ByteString.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ByteString encodeUtf8(String str) {
            str.getClass();
            ByteString byteString = new ByteString(_JvmPlatformKt.asUtf8ToByteArray(str));
            byteString.setUtf8$external__okio__android_common__okio_lib(str);
            return byteString;
        }

        public final ByteString read(InputStream inputStream, int i) throws IOException {
            inputStream.getClass();
            if (i < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + i).toString());
            }
            byte[] bArr = new byte[i];
            int i2 = 0;
            while (i2 < i) {
                int i3 = inputStream.read(bArr, i2, i - i2);
                if (i3 == -1) {
                    throw new EOFException();
                }
                i2 += i3;
            }
            return new ByteString(bArr);
        }
    }

    public ByteString(byte[] bArr) {
        bArr.getClass();
        this.data = bArr;
    }

    public static final ByteString encodeUtf8(String str) {
        return Companion.encodeUtf8(str);
    }

    private final void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, NoSuchFieldException, IOException {
        ByteString byteString = Companion.read(objectInputStream, objectInputStream.readInt());
        Field declaredField = ByteString.class.getDeclaredField("data");
        declaredField.setAccessible(true);
        declaredField.set(this, byteString.data);
    }

    private final void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    @Override // java.lang.Comparable
    public int compareTo(ByteString byteString) {
        byteString.getClass();
        int size = size();
        int size2 = byteString.size();
        int iMin = Math.min(size, size2);
        for (int i = 0; i < iMin; i++) {
            int i2 = getByte(i) & 255;
            int i3 = byteString.getByte(i) & 255;
            if (i2 != i3) {
                return i2 < i3 ? -1 : 1;
            }
        }
        if (size == size2) {
            return 0;
        }
        return size < size2 ? -1 : 1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == getData$external__okio__android_common__okio_lib().length && byteString.rangeEquals(0, getData$external__okio__android_common__okio_lib(), 0, getData$external__okio__android_common__okio_lib().length)) {
                return true;
            }
        }
        return false;
    }

    public final byte getByte(int i) {
        return internalGet$external__okio__android_common__okio_lib(i);
    }

    public final byte[] getData$external__okio__android_common__okio_lib() {
        return this.data;
    }

    public final int getHashCode$external__okio__android_common__okio_lib() {
        return this.hashCode;
    }

    public int getSize$external__okio__android_common__okio_lib() {
        return getData$external__okio__android_common__okio_lib().length;
    }

    public final String getUtf8$external__okio__android_common__okio_lib() {
        return this.utf8;
    }

    public int hashCode() {
        int hashCode$external__okio__android_common__okio_lib = getHashCode$external__okio__android_common__okio_lib();
        if (hashCode$external__okio__android_common__okio_lib != 0) {
            return hashCode$external__okio__android_common__okio_lib;
        }
        int iHashCode = Arrays.hashCode(getData$external__okio__android_common__okio_lib());
        setHashCode$external__okio__android_common__okio_lib(iHashCode);
        return iHashCode;
    }

    public String hex() {
        char[] cArr = new char[getData$external__okio__android_common__okio_lib().length * 2];
        int i = 0;
        for (byte b : getData$external__okio__android_common__okio_lib()) {
            int i2 = i + 1;
            cArr[i] = okio.internal.ByteString.getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i += 2;
            cArr[i2] = okio.internal.ByteString.getHEX_DIGIT_CHARS()[b & 15];
        }
        return StringsKt.concatToString(cArr);
    }

    public byte[] internalArray$external__okio__android_common__okio_lib() {
        return getData$external__okio__android_common__okio_lib();
    }

    public byte internalGet$external__okio__android_common__okio_lib(int i) {
        return getData$external__okio__android_common__okio_lib()[i];
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        byteString.getClass();
        return byteString.rangeEquals(i2, getData$external__okio__android_common__okio_lib(), i, i3);
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        bArr.getClass();
        return i >= 0 && i <= getData$external__okio__android_common__okio_lib().length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && SegmentedByteString.arrayRangeEquals(getData$external__okio__android_common__okio_lib(), i, bArr, i2, i3);
    }

    public final void setHashCode$external__okio__android_common__okio_lib(int i) {
        this.hashCode = i;
    }

    public final void setUtf8$external__okio__android_common__okio_lib(String str) {
        this.utf8 = str;
    }

    public final int size() {
        return getSize$external__okio__android_common__okio_lib();
    }

    public final boolean startsWith(ByteString byteString) {
        byteString.getClass();
        return rangeEquals(0, byteString, 0, byteString.size());
    }

    public String toString() {
        if (getData$external__okio__android_common__okio_lib().length == 0) {
            return "[size=0]";
        }
        int iCodePointIndexToCharIndex = okio.internal.ByteString.codePointIndexToCharIndex(getData$external__okio__android_common__okio_lib(), 64);
        if (iCodePointIndexToCharIndex != -1) {
            String strUtf8 = utf8();
            String strSubstring = strUtf8.substring(0, iCodePointIndexToCharIndex);
            strSubstring.getClass();
            String strReplace$default = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(strSubstring, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null), "\r", "\\r", false, 4, null);
            if (iCodePointIndexToCharIndex >= strUtf8.length()) {
                return "[text=" + strReplace$default + "]";
            }
            return "[size=" + getData$external__okio__android_common__okio_lib().length + " text=" + strReplace$default + "…]";
        }
        if (getData$external__okio__android_common__okio_lib().length <= 64) {
            return "[hex=" + hex() + "]";
        }
        int length = getData$external__okio__android_common__okio_lib().length;
        ByteString byteString = this;
        int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(byteString, 64);
        if (iResolveDefaultParameter > byteString.getData$external__okio__android_common__okio_lib().length) {
            throw new IllegalArgumentException(("endIndex > length(" + byteString.getData$external__okio__android_common__okio_lib().length + ")").toString());
        }
        if (iResolveDefaultParameter < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (iResolveDefaultParameter != byteString.getData$external__okio__android_common__okio_lib().length) {
            byteString = new ByteString(ArraysKt.copyOfRange(byteString.getData$external__okio__android_common__okio_lib(), 0, iResolveDefaultParameter));
        }
        return "[size=" + length + " hex=" + byteString.hex() + "…]";
    }

    public String utf8() {
        String utf8$external__okio__android_common__okio_lib = getUtf8$external__okio__android_common__okio_lib();
        if (utf8$external__okio__android_common__okio_lib != null) {
            return utf8$external__okio__android_common__okio_lib;
        }
        String utf8String = _JvmPlatformKt.toUtf8String(internalArray$external__okio__android_common__okio_lib());
        setUtf8$external__okio__android_common__okio_lib(utf8String);
        return utf8String;
    }
}
