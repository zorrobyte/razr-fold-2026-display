package com.motorola.plugin.core.rule;

import android.content.Context;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.utils.HiddenApiKt;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SystemPropRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SystemPropRule extends AbsMultiValuesRule {
    public static final Companion Companion = new Companion(null);
    private final Function1 systemPropGetter;

    /* JADX INFO: compiled from: SystemPropRule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IRule parse(String str) {
            str.getClass();
            return new SystemPropRule(SequencesKt.filter(StringsKt.splitToSequence$default(str, new char[]{','}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.SystemPropRule$Companion$parse$multiValues$1
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
    public SystemPropRule(Sequence sequence) {
        super(sequence);
        sequence.getClass();
        this.systemPropGetter = new Function1() { // from class: com.motorola.plugin.core.rule.SystemPropRule$systemPropGetter$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String str) {
                str.getClass();
                return HiddenApiKt.getSysProp(str, str);
            }
        };
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public int getPriority() {
        return 1;
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public boolean match(Context context) {
        boolean z;
        context.getClass();
        for (String str : getMultiValues()) {
            boolean z2 = StringsKt.contains$default((CharSequence) str, (CharSequence) "!=", false, 2, (Object) null) && !StringsKt.endsWith$default(str, "!=", false, 2, null);
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            if (z2) {
                final List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{"!="}, false, 2, 2, (Object) null);
                if (listSplit$default.size() != 2) {
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.rule.SystemPropRule.match.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "match: ignore the system prop rule cause the value >>" + listSplit$default + "<< is invalid";
                        }
                    }, 62, null);
                } else {
                    ref$ObjectRef.element = listSplit$default;
                    z = true;
                }
            } else {
                ref$ObjectRef.element = StringsKt.split$default((CharSequence) str, new char[]{'='}, false, 2, 2, (Object) null);
                z = false;
            }
            if (((List) ref$ObjectRef.element).size() == 2) {
                String str2 = (String) ((List) ref$ObjectRef.element).get(0);
                String str3 = (String) ((List) ref$ObjectRef.element).get(1);
                String str4 = (String) this.systemPropGetter.invoke(str2);
                if (z ? !Intrinsics.areEqual(str4, str3) : Intrinsics.areEqual(str4, str3)) {
                    return true;
                }
            } else {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.rule.SystemPropRule.match.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "match: ignore the system prop rule cause the value >>" + ref$ObjectRef.element + "<< is invalid";
                    }
                }, 62, null);
            }
        }
        return false;
    }
}
