package com.motorola.plugin.core.rule;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import com.motorola.plugin.core.PluginConfigKt;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DisplayIdRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DisplayIdRule extends AbsMultiValuesRule {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: DisplayIdRule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IRule parse(String str) {
            str.getClass();
            return new DisplayIdRule(SequencesKt.filter(StringsKt.splitToSequence$default(str, new char[]{','}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.DisplayIdRule$Companion$parse$multiValues$1
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

    /* JADX INFO: compiled from: DisplayIdRule.kt */
    enum DisplayAlias {
        DEFAULT,
        CLI,
        EXTERNAL,
        WIFI,
        OVERLAY,
        VIRTUAL,
        DESKTOP,
        MOBILE
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayIdRule(Sequence sequence) {
        super(sequence);
        sequence.getClass();
    }

    private final boolean matchDisplayAlias(final String str, String str2, Display display, boolean z) {
        Locale locale = Locale.US;
        locale.getClass();
        if (str2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = str2.toUpperCase(locale);
        upperCase.getClass();
        if (Intrinsics.areEqual(upperCase, DisplayAlias.DEFAULT.name())) {
            return z ? display == null || display.getDisplayId() != 0 : display != null && display.getDisplayId() == 0;
        }
        if (Intrinsics.areEqual(upperCase, DisplayAlias.CLI.name())) {
            return z ? display == null || display.getDisplayId() != 1 : display != null && display.getDisplayId() == 1;
        }
        if (Intrinsics.areEqual(upperCase, DisplayAlias.DESKTOP.name())) {
            return z ? (display != null && (display.getFlags() & 512) == 0) || (display != null && (display.getFlags() & 1024) == 0) : (display == null || (display.getFlags() & 512) != 0) && (display == null || (display.getFlags() & 1024) != 0);
        }
        if (Intrinsics.areEqual(upperCase, DisplayAlias.MOBILE.name())) {
            return z ? display == null || (display.getFlags() & 512) != 0 || (display.getFlags() & 1024) == 0 : (display == null || (display.getFlags() & 512) != 0 || (display.getFlags() & 1024) == 0) ? false : true;
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.rule.DisplayIdRule$matchDisplayAlias$match$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "match: ignore the display rule cause the unknown alias >>" + str + "<<";
            }
        }, 62, null);
        return false;
    }

    static /* synthetic */ boolean matchDisplayAlias$default(DisplayIdRule displayIdRule, String str, String str2, Display display, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return displayIdRule.matchDisplayAlias(str, str2, display, z);
    }

    private final boolean matchDisplayId(int i, Display display, boolean z) {
        return z ? display == null || display.getDisplayId() != i : display != null && display.getDisplayId() == i;
    }

    static /* synthetic */ boolean matchDisplayId$default(DisplayIdRule displayIdRule, int i, Display display, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return displayIdRule.matchDisplayId(i, display, z);
    }

    private final boolean matchSingleDisplay(String str, String str2, Display display, boolean z) {
        Integer intOrNull = StringsKt.toIntOrNull(str2);
        if (intOrNull != null) {
            if (matchDisplayId(intOrNull.intValue(), display, z)) {
                return true;
            }
        } else if (StringsKt.startsWith$default((CharSequence) str2, '!', false, 2, (Object) null) && !StringsKt.startsWith$default(str2, "!!", false, 2, (Object) null) && str2.length() >= 2) {
            String strSubstring = str2.substring(1);
            strSubstring.getClass();
            if (matchSingleDisplay(str2, strSubstring, display, true)) {
                return true;
            }
        } else if (matchDisplayAlias(str, str2, display, z)) {
            return true;
        }
        return false;
    }

    static /* synthetic */ boolean matchSingleDisplay$default(DisplayIdRule displayIdRule, String str, String str2, Display display, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return displayIdRule.matchSingleDisplay(str, str2, display, z);
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public int getPriority() {
        return 2;
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public boolean match(Context context) {
        Object objM2707constructorimpl;
        context.getClass();
        WindowManager windowManager = (WindowManager) ContextCompat.getSystemService(context, WindowManager.class);
        if (windowManager == null) {
            return false;
        }
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(context.getDisplay());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) != null) {
            objM2707constructorimpl = windowManager.getDefaultDisplay();
        }
        Display display = (Display) objM2707constructorimpl;
        for (String str : getMultiValues()) {
            DisplayIdRule displayIdRule = this;
            if (matchSingleDisplay$default(displayIdRule, str, str, display, false, 8, null)) {
                return true;
            }
            this = displayIdRule;
        }
        return false;
    }
}
