package com.motorola.plugin.core.rule;

import android.content.Context;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PackageNameRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PackageNameRule extends AbsMultiValuesRule {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: PackageNameRule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IRule parse(String str) {
            str.getClass();
            return new PackageNameRule(SequencesKt.filter(StringsKt.splitToSequence$default(str, new char[]{','}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.PackageNameRule$Companion$parse$multiValues$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Boolean.valueOf(invoke((String) obj));
                }

                public final boolean invoke(String str2) {
                    str2.getClass();
                    return !StringsKt.isBlank(str2);
                }
            }));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageNameRule(Sequence sequence) {
        super(sequence);
        sequence.getClass();
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public int getPriority() {
        return 5;
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public boolean match(Context context) {
        context.getClass();
        Iterator it = getMultiValues().iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((String) it.next(), context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
