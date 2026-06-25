package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: Strings.kt */
/* JADX INFO: loaded from: classes.dex */
final class DelimitedRangesSequence implements Sequence {
    private final Function2 getNextMatch;
    private final CharSequence input;
    private final int limit;
    private final int startIndex;

    /* JADX INFO: renamed from: kotlin.text.DelimitedRangesSequence$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: Strings.kt */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        private int counter;
        private int currentStartIndex;
        private IntRange nextItem;
        private int nextSearchIndex;
        private int nextState = -1;

        AnonymousClass1() {
            int iCoerceIn = RangesKt.coerceIn(DelimitedRangesSequence.this.startIndex, 0, DelimitedRangesSequence.this.input.length());
            this.currentStartIndex = iCoerceIn;
            this.nextSearchIndex = iCoerceIn;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final void calcNext() {
            /*
                r6 = this;
                int r0 = r6.nextSearchIndex
                r1 = 0
                if (r0 >= 0) goto Lb
                r6.nextState = r1
                r0 = 0
                r6.nextItem = r0
                return
            Lb:
                kotlin.text.DelimitedRangesSequence r0 = kotlin.text.DelimitedRangesSequence.this
                int r0 = kotlin.text.DelimitedRangesSequence.access$getLimit$p(r0)
                r2 = -1
                r3 = 1
                if (r0 <= 0) goto L22
                int r0 = r6.counter
                int r0 = r0 + r3
                r6.counter = r0
                kotlin.text.DelimitedRangesSequence r4 = kotlin.text.DelimitedRangesSequence.this
                int r4 = kotlin.text.DelimitedRangesSequence.access$getLimit$p(r4)
                if (r0 >= r4) goto L30
            L22:
                int r0 = r6.nextSearchIndex
                kotlin.text.DelimitedRangesSequence r4 = kotlin.text.DelimitedRangesSequence.this
                java.lang.CharSequence r4 = kotlin.text.DelimitedRangesSequence.access$getInput$p(r4)
                int r4 = r4.length()
                if (r0 <= r4) goto L46
            L30:
                kotlin.ranges.IntRange r0 = new kotlin.ranges.IntRange
                int r1 = r6.currentStartIndex
                kotlin.text.DelimitedRangesSequence r4 = kotlin.text.DelimitedRangesSequence.this
                java.lang.CharSequence r4 = kotlin.text.DelimitedRangesSequence.access$getInput$p(r4)
                int r4 = kotlin.text.StringsKt__StringsKt.getLastIndex(r4)
                r0.<init>(r1, r4)
                r6.nextItem = r0
                r6.nextSearchIndex = r2
                goto L9b
            L46:
                kotlin.text.DelimitedRangesSequence r0 = kotlin.text.DelimitedRangesSequence.this
                kotlin.jvm.functions.Function2 r0 = kotlin.text.DelimitedRangesSequence.access$getGetNextMatch$p(r0)
                kotlin.text.DelimitedRangesSequence r4 = kotlin.text.DelimitedRangesSequence.this
                java.lang.CharSequence r4 = kotlin.text.DelimitedRangesSequence.access$getInput$p(r4)
                int r5 = r6.nextSearchIndex
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                java.lang.Object r0 = r0.invoke(r4, r5)
                kotlin.Pair r0 = (kotlin.Pair) r0
                if (r0 != 0) goto L76
                kotlin.ranges.IntRange r0 = new kotlin.ranges.IntRange
                int r1 = r6.currentStartIndex
                kotlin.text.DelimitedRangesSequence r4 = kotlin.text.DelimitedRangesSequence.this
                java.lang.CharSequence r4 = kotlin.text.DelimitedRangesSequence.access$getInput$p(r4)
                int r4 = kotlin.text.StringsKt__StringsKt.getLastIndex(r4)
                r0.<init>(r1, r4)
                r6.nextItem = r0
                r6.nextSearchIndex = r2
                goto L9b
            L76:
                java.lang.Object r2 = r0.component1()
                java.lang.Number r2 = (java.lang.Number) r2
                int r2 = r2.intValue()
                java.lang.Object r0 = r0.component2()
                java.lang.Number r0 = (java.lang.Number) r0
                int r0 = r0.intValue()
                int r4 = r6.currentStartIndex
                kotlin.ranges.IntRange r4 = kotlin.ranges.RangesKt.until(r4, r2)
                r6.nextItem = r4
                int r2 = r2 + r0
                r6.currentStartIndex = r2
                if (r0 != 0) goto L98
                r1 = r3
            L98:
                int r2 = r2 + r1
                r6.nextSearchIndex = r2
            L9b:
                r6.nextState = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.text.DelimitedRangesSequence.AnonymousClass1.calcNext():void");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextState == -1) {
                calcNext();
            }
            return this.nextState == 1;
        }

        @Override // java.util.Iterator
        public IntRange next() {
            if (this.nextState == -1) {
                calcNext();
            }
            if (this.nextState == 0) {
                throw new NoSuchElementException();
            }
            IntRange intRange = this.nextItem;
            intRange.getClass();
            this.nextItem = null;
            this.nextState = -1;
            return intRange;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public DelimitedRangesSequence(CharSequence charSequence, int i, int i2, Function2 function2) {
        charSequence.getClass();
        function2.getClass();
        this.input = charSequence;
        this.startIndex = i;
        this.limit = i2;
        this.getNextMatch = function2;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator iterator() {
        return new AnonymousClass1();
    }
}
