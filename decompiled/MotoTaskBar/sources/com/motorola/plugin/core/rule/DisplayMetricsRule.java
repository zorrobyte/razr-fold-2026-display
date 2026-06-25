package com.motorola.plugin.core.rule;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DisplayMetricsRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DisplayMetricsRule implements IRule {
    public static final Companion Companion = new Companion(null);
    private final Sequence metricsValuesGroup;

    /* JADX INFO: compiled from: DisplayMetricsRule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IRule parse(String str) {
            str.getClass();
            return new DisplayMetricsRule(SequencesKt.map(SequencesKt.filter(StringsKt.splitToSequence$default(str, new char[]{';'}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.DisplayMetricsRule$Companion$parse$valueGroup$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Boolean.valueOf(invoke((String) obj));
                }

                public final boolean invoke(String str2) {
                    str2.getClass();
                    return !StringsKt.isBlank(str2);
                }
            }), new Function1() { // from class: com.motorola.plugin.core.rule.DisplayMetricsRule$Companion$parse$values$1
                @Override // kotlin.jvm.functions.Function1
                public final Sequence invoke(String str2) {
                    str2.getClass();
                    return SequencesKt.filter(StringsKt.splitToSequence$default(str2, new char[]{','}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.DisplayMetricsRule$Companion$parse$values$1.1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return Boolean.valueOf(invoke((String) obj));
                        }

                        public final boolean invoke(String str3) {
                            str3.getClass();
                            return !StringsKt.isBlank(str3);
                        }
                    });
                }
            }));
        }
    }

    public DisplayMetricsRule(Sequence sequence) {
        sequence.getClass();
        this.metricsValuesGroup = sequence;
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public int getPriority() {
        return 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x010b A[EDGE_INSN: B:67:0x010b->B:60:0x010b BREAK  A[LOOP:1: B:9:0x0034->B:71:0x0034], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0034 A[SYNTHETIC] */
    @Override // com.motorola.plugin.core.rule.IRule
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean match(android.content.Context r18) {
        /*
            Method dump skipped, instruction units count: 271
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.rule.DisplayMetricsRule.match(android.content.Context):boolean");
    }
}
