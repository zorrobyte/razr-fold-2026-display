package okio;

import java.util.List;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Options.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Options extends AbstractList implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    /* JADX INFO: compiled from: Options.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final void buildTrieRecursive(long j, Buffer buffer, int i, List list, int i2, int i3, List list2) {
            int i4;
            int i5;
            int i6;
            long j2;
            int i7 = i;
            if (i2 >= i3) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            for (int i8 = i2; i8 < i3; i8++) {
                if (((ByteString) list.get(i8)).size() < i7) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
            }
            ByteString byteString = (ByteString) list.get(i2);
            ByteString byteString2 = (ByteString) list.get(i3 - 1);
            if (i7 == byteString.size()) {
                int iIntValue = ((Number) list2.get(i2)).intValue();
                int i9 = i2 + 1;
                ByteString byteString3 = (ByteString) list.get(i9);
                i4 = i9;
                i5 = iIntValue;
                byteString = byteString3;
            } else {
                i4 = i2;
                i5 = -1;
            }
            if (byteString.getByte(i7) == byteString2.getByte(i7)) {
                int iMin = Math.min(byteString.size(), byteString2.size());
                int i10 = 0;
                for (int i11 = i7; i11 < iMin && byteString.getByte(i11) == byteString2.getByte(i11); i11++) {
                    i10++;
                }
                long intCount = j + getIntCount(buffer) + ((long) 2) + ((long) i10) + 1;
                buffer.writeInt(-i10);
                buffer.writeInt(i5);
                int i12 = i7 + i10;
                while (i7 < i12) {
                    buffer.writeInt(byteString.getByte(i7) & 255);
                    i7++;
                }
                if (i4 + 1 == i3) {
                    if (i12 != ((ByteString) list.get(i4)).size()) {
                        throw new IllegalStateException("Check failed.");
                    }
                    buffer.writeInt(((Number) list2.get(i4)).intValue());
                    return;
                } else {
                    Buffer buffer2 = new Buffer();
                    buffer.writeInt(((int) (getIntCount(buffer2) + intCount)) * (-1));
                    buildTrieRecursive(intCount, buffer2, i12, list, i4, i3, list2);
                    buffer.writeAll(buffer2);
                    return;
                }
            }
            int i13 = 1;
            for (int i14 = i4 + 1; i14 < i3; i14++) {
                if (((ByteString) list.get(i14 - 1)).getByte(i7) != ((ByteString) list.get(i14)).getByte(i7)) {
                    i13++;
                }
            }
            long intCount2 = j + getIntCount(buffer) + ((long) 2) + ((long) (i13 * 2));
            buffer.writeInt(i13);
            buffer.writeInt(i5);
            for (int i15 = i4; i15 < i3; i15++) {
                byte b = ((ByteString) list.get(i15)).getByte(i7);
                if (i15 == i4 || b != ((ByteString) list.get(i15 - 1)).getByte(i7)) {
                    buffer.writeInt(b & 255);
                }
            }
            Buffer buffer3 = new Buffer();
            while (i4 < i3) {
                byte b2 = ((ByteString) list.get(i4)).getByte(i7);
                int i16 = i4 + 1;
                int i17 = i16;
                while (true) {
                    if (i17 >= i3) {
                        i6 = i3;
                        break;
                    } else {
                        if (b2 != ((ByteString) list.get(i17)).getByte(i7)) {
                            i6 = i17;
                            break;
                        }
                        i17++;
                    }
                }
                if (i16 == i6 && i7 + 1 == ((ByteString) list.get(i4)).size()) {
                    buffer.writeInt(((Number) list2.get(i4)).intValue());
                    j2 = intCount2;
                } else {
                    buffer.writeInt(((int) (getIntCount(buffer3) + intCount2)) * (-1));
                    j2 = intCount2;
                    buildTrieRecursive(j2, buffer3, i7 + 1, list, i4, i6, list2);
                }
                intCount2 = j2;
                i4 = i6;
            }
            buffer.writeAll(buffer3);
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                j = 0;
            }
            companion.buildTrieRecursive(j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / ((long) 4);
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x00c9, code lost:
        
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final okio.Options of(okio.ByteString... r17) {
            /*
                Method dump skipped, instruction units count: 264
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.of(okio.ByteString[]):okio.Options");
        }
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    public static final Options of(ByteString... byteStringArr) {
        return Companion.of(byteStringArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    public final ByteString[] getByteStrings$external__okio__android_common__okio_lib() {
        return this.byteStrings;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    public final int[] getTrie$external__okio__android_common__okio_lib() {
        return this.trie;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }
}
