package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.SlotReader;
import androidx.compose.runtime.SlotTable;
import androidx.compose.runtime.SlotWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ComposeStackTraceBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ComposeStackTraceBuilderKt {
    private static final int[] EmptyIntArray = new int[0];

    public static final List buildTrace(SlotReader slotReader) {
        if (slotReader.getClosed() || slotReader.getSize() == 0) {
            return CollectionsKt.emptyList();
        }
        ReaderTraceBuilder readerTraceBuilder = new ReaderTraceBuilder(slotReader);
        int parent = slotReader.getParent();
        Object objValueOf = Integer.valueOf(slotReader.getSlot());
        while (parent >= 0) {
            readerTraceBuilder.processEdge(slotReader.getTable$runtime_release().sourceInformationOf(parent), objValueOf);
            objValueOf = slotReader.anchor(parent);
            parent = slotReader.parent(parent);
        }
        return readerTraceBuilder.trace();
    }

    public static final List buildTrace(SlotWriter slotWriter, Object obj, int i, Integer num) {
        if (slotWriter.getClosed() || slotWriter.getSize$runtime_release() == 0) {
            return CollectionsKt.emptyList();
        }
        WriterTraceBuilder writerTraceBuilder = new WriterTraceBuilder(slotWriter);
        int iIntValue = num != null ? num.intValue() : slotWriter.getParent() < 0 ? slotWriter.parent(i) : slotWriter.getParent();
        if (obj == null) {
            obj = Integer.valueOf(slotWriter.groupSlotIndex(i));
        }
        while (i >= 0) {
            writerTraceBuilder.processEdge(slotWriter.sourceInformationOf$runtime_release(i), obj);
            obj = slotWriter.anchor(i);
            if (iIntValue >= 0) {
                int i2 = iIntValue;
                iIntValue = slotWriter.parent(iIntValue);
                i = i2;
            } else {
                i = iIntValue;
            }
        }
        return writerTraceBuilder.trace();
    }

    public static /* synthetic */ List buildTrace$default(SlotWriter slotWriter, Object obj, int i, Integer num, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            obj = null;
        }
        if ((i2 & 2) != 0) {
            i = slotWriter.getCurrentGroup();
        }
        if ((i2 & 4) != 0) {
            num = null;
        }
        return buildTrace(slotWriter, obj, i, num);
    }

    public static final ObjectLocation findLocation(SlotTable slotTable, Function1 function1) {
        SlotReader slotReaderOpenReader = slotTable.openReader();
        try {
            Ref$IntRef ref$IntRef = new Ref$IntRef();
            while (ref$IntRef.element < slotTable.getGroupsSize()) {
                if (slotReaderOpenReader.isNode(ref$IntRef.element) && ((Boolean) function1.invoke(slotReaderOpenReader.node(ref$IntRef.element))).booleanValue()) {
                    return new ObjectLocation(ref$IntRef.element, null);
                }
                int iSlotSize = slotReaderOpenReader.slotSize(ref$IntRef.element);
                for (int i = 0; i < iSlotSize; i++) {
                    if (((Boolean) function1.invoke(slotReaderOpenReader.groupGet(ref$IntRef.element, i))).booleanValue()) {
                        return new ObjectLocation(ref$IntRef.element, Integer.valueOf(i));
                    }
                }
                ref$IntRef.element++;
            }
            Unit unit = Unit.INSTANCE;
            return null;
        } finally {
            slotReaderOpenReader.close();
        }
    }

    public static final Integer findSubcompositionContextGroup(SlotTable slotTable, CompositionContext compositionContext) {
        SlotReader slotReaderOpenReader = slotTable.openReader();
        try {
            return findSubcompositionContextGroup$lambda$4$scanGroup(slotReaderOpenReader, compositionContext, 0, slotReaderOpenReader.getSize());
        } finally {
            slotReaderOpenReader.close();
        }
    }

    private static final Integer findSubcompositionContextGroup$lambda$4$scanGroup(SlotReader slotReader, CompositionContext compositionContext, int i, int i2) {
        Integer numFindSubcompositionContextGroup$lambda$4$scanGroup;
        while (i < i2) {
            int iGroupSize = slotReader.groupSize(i) + i;
            if (slotReader.hasMark(i) && slotReader.groupKey(i) == 206 && Intrinsics.areEqual(slotReader.groupObjectKey(i), ComposerKt.getReference())) {
                slotReader.groupGet(i, 0);
            }
            if (slotReader.containsMark(i) && (numFindSubcompositionContextGroup$lambda$4$scanGroup = findSubcompositionContextGroup$lambda$4$scanGroup(slotReader, compositionContext, i + 1, iGroupSize)) != null) {
                return Integer.valueOf(numFindSubcompositionContextGroup$lambda$4$scanGroup.intValue());
            }
            i = iGroupSize;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ParsedSourceInformation parseSourceInfo(String str) {
        int i;
        boolean z;
        String str2;
        int[] intArray;
        String strSubstring;
        String strSubstring2 = null;
        if (str.length() == 0) {
            return null;
        }
        if (str.charAt(0) == 'C') {
            i = str.charAt(1) == 'C' ? 2 : 1;
            if (str.charAt(i) == '(') {
                int i2 = i + 1;
                int i3 = i2;
                while (str.charAt(i3) != ')') {
                    i3++;
                }
                String strSubstring3 = str.substring(i2, i3);
                strSubstring3.getClass();
                int i4 = i3 + 1;
                str2 = strSubstring3;
                i = i4;
            } else {
                str2 = "<lambda>";
            }
            if (str.charAt(i) == 'P') {
                do {
                    i++;
                } while (str.charAt(i) != ')');
                i++;
            }
            z = true;
        } else {
            i = 0;
            z = false;
            str2 = null;
        }
        int i5 = i;
        while (i5 < str.length() && str.charAt(i5) != ':') {
            i5++;
        }
        if (i < i5) {
            String strSubstring4 = str.substring(i, i5);
            strSubstring4.getClass();
            List listSplit$default = StringsKt.split$default((CharSequence) strSubstring4, new char[]{','}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(Integer.parseInt(StringsKt.substringAfter$default(StringsKt.substringBefore$default((String) it.next(), '@', null, 2, null), '*', (String) null, 2, (Object) null)) + 1));
            }
            intArray = CollectionsKt.toIntArray(arrayList);
        } else {
            intArray = EmptyIntArray;
        }
        int[] iArr = intArray;
        if (i5 < str.length()) {
            int i6 = i5 + 1;
            int i7 = i6;
            while (str.charAt(i7) != '#') {
                i7++;
            }
            strSubstring = str.substring(i6, i7);
            strSubstring.getClass();
            i5 = i7;
        } else {
            strSubstring = null;
        }
        if (i5 < str.length()) {
            strSubstring2 = str.substring(i5 + 1);
            strSubstring2.getClass();
        }
        return new ParsedSourceInformation(z, str2, strSubstring, strSubstring2, iArr, str);
    }

    public static final List traceForGroup(SlotReader slotReader, int i, Object obj) {
        ReaderTraceBuilder readerTraceBuilder = new ReaderTraceBuilder(slotReader);
        int iParent = slotReader.parent(i);
        Anchor anchor = slotReader.anchor(i);
        while (i >= 0) {
            readerTraceBuilder.processEdge(slotReader.getTable$runtime_release().sourceInformationOf(i), obj);
            if (iParent >= 0) {
                Anchor anchor2 = anchor;
                anchor = slotReader.anchor(iParent);
                i = iParent;
                iParent = slotReader.parent(iParent);
                obj = anchor2;
            } else {
                i = iParent;
                obj = anchor;
            }
        }
        return readerTraceBuilder.trace();
    }
}
