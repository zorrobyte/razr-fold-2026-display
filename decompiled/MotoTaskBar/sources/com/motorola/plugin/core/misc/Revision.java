package com.motorola.plugin.core.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Revision.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Revision {
    public static final Companion Companion = new Companion(null);
    private static final Revision EMPTY = new Revision(0, 0, 0);
    private final int major;
    private final Integer micro;
    private final Integer minor;

    /* JADX INFO: compiled from: Revision.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Revision getEMPTY() {
            return Revision.EMPTY;
        }

        public final Revision parse(String str) {
            str.getClass();
            List listSplit$default = StringsKt.split$default((CharSequence) str, new char[]{'.'}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(StringsKt.toIntOrNull((String) it.next()));
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (((Integer) obj) == null) {
                        return null;
                    }
                }
            }
            Object obj2 = arrayList.get(0);
            obj2.getClass();
            return new Revision(((Number) obj2).intValue(), (Integer) CollectionsKt.getOrNull(arrayList, 1), (Integer) CollectionsKt.getOrNull(arrayList, 2));
        }
    }

    public Revision(int i, Integer num, Integer num2) {
        this.major = i;
        this.minor = num;
        this.micro = num2;
    }

    public static /* synthetic */ Revision copy$default(Revision revision, int i, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = revision.major;
        }
        if ((i2 & 2) != 0) {
            num = revision.minor;
        }
        if ((i2 & 4) != 0) {
            num2 = revision.micro;
        }
        return revision.copy(i, num, num2);
    }

    public final int compareTo(Revision revision) {
        revision.getClass();
        int iCompare = Intrinsics.compare(this.major, revision.major);
        if (iCompare == 0) {
            Integer num = this.minor;
            if (num == null) {
                iCompare = 0;
            } else {
                int iIntValue = num.intValue();
                Integer num2 = revision.minor;
                iCompare = Intrinsics.compare(iIntValue, num2 == null ? 0 : num2.intValue());
            }
            if (iCompare == 0) {
                Integer num3 = this.micro;
                if (num3 == null) {
                    return 0;
                }
                int iIntValue2 = num3.intValue();
                Integer num4 = revision.micro;
                return Intrinsics.compare(iIntValue2, num4 != null ? num4.intValue() : 0);
            }
        }
        return iCompare;
    }

    public final int compareTo(String str) {
        str.getClass();
        Revision revision = Companion.parse(str);
        if (revision == null) {
            return 0;
        }
        return compareTo(revision);
    }

    public final int component1() {
        return this.major;
    }

    public final Integer component2() {
        return this.minor;
    }

    public final Integer component3() {
        return this.micro;
    }

    public final Revision copy(int i, Integer num, Integer num2) {
        return new Revision(i, num, num2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Revision)) {
            return false;
        }
        Revision revision = (Revision) obj;
        return this.major == revision.major && Intrinsics.areEqual(this.minor, revision.minor) && Intrinsics.areEqual(this.micro, revision.micro);
    }

    public final int getMajor() {
        return this.major;
    }

    public final Integer getMicro() {
        return this.micro;
    }

    public final Integer getMinor() {
        return this.minor;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.major) * 31;
        Integer num = this.minor;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.micro;
        return iHashCode2 + (num2 != null ? num2.hashCode() : 0);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(this.major));
        Integer num = this.minor;
        if (num != null) {
            stringBuffer.append(Intrinsics.stringPlus(".", Integer.valueOf(num.intValue())));
        }
        Integer num2 = this.micro;
        if (num2 != null) {
            stringBuffer.append(Intrinsics.stringPlus(".", Integer.valueOf(num2.intValue())));
        }
        String string = stringBuffer.toString();
        string.getClass();
        return string;
    }
}
