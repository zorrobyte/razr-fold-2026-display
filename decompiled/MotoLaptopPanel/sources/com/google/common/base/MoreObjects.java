package com.google.common.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class MoreObjects {

    public final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitEmptyValues;
        private boolean omitNullValues;

        class ValueHolder {
            String name;
            ValueHolder next;
            Object value;

            ValueHolder() {
            }
        }

        private ToStringHelper(String str) {
            ValueHolder valueHolder = new ValueHolder();
            this.holderHead = valueHolder;
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.omitEmptyValues = false;
            str.getClass();
            this.className = str;
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(Object obj) {
            addHolder().value = obj;
            return this;
        }

        private static boolean isEmpty(Object obj) {
            return obj instanceof CharSequence ? ((CharSequence) obj).length() == 0 : obj instanceof Collection ? ((Collection) obj).isEmpty() : obj instanceof Map ? ((Map) obj).isEmpty() : obj.getClass().isArray() && Array.getLength(obj) == 0;
        }

        public ToStringHelper addValue(Object obj) {
            return addHolder(obj);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x002c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.lang.String toString() {
            /*
                r6 = this;
                boolean r0 = r6.omitNullValues
                boolean r1 = r6.omitEmptyValues
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r3 = 32
                r2.<init>(r3)
                java.lang.String r3 = r6.className
                r2.append(r3)
                r3 = 123(0x7b, float:1.72E-43)
                r2.append(r3)
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r6 = r6.holderHead
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r6 = r6.next
                java.lang.String r3 = ""
            L1b:
                if (r6 == 0) goto L61
                java.lang.Object r4 = r6.value
                if (r4 != 0) goto L24
                if (r0 != 0) goto L5e
                goto L2c
            L24:
                if (r1 == 0) goto L2c
                boolean r5 = isEmpty(r4)
                if (r5 != 0) goto L5e
            L2c:
                r2.append(r3)
                java.lang.String r3 = r6.name
                if (r3 == 0) goto L3b
                r2.append(r3)
                r3 = 61
                r2.append(r3)
            L3b:
                if (r4 == 0) goto L59
                java.lang.Class r3 = r4.getClass()
                boolean r3 = r3.isArray()
                if (r3 == 0) goto L59
                java.lang.Object[] r3 = new java.lang.Object[]{r4}
                java.lang.String r3 = java.util.Arrays.deepToString(r3)
                int r4 = r3.length()
                r5 = 1
                int r4 = r4 - r5
                r2.append(r3, r5, r4)
                goto L5c
            L59:
                r2.append(r4)
            L5c:
                java.lang.String r3 = ", "
            L5e:
                com.google.common.base.MoreObjects$ToStringHelper$ValueHolder r6 = r6.next
                goto L1b
            L61:
                r6 = 125(0x7d, float:1.75E-43)
                r2.append(r6)
                java.lang.String r6 = r2.toString()
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.MoreObjects.ToStringHelper.toString():java.lang.String");
        }
    }

    public static Object firstNonNull(Object obj, Object obj2) {
        if (obj != null) {
            return obj;
        }
        if (obj2 != null) {
            return obj2;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }
}
