package com.motorola.plugin.core.rule;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.core.content.ContextCompat;
import com.motorola.plugin.core.PluginConfigKt;
import java.util.Locale;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: UserRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class UserRule extends AbsMultiValuesRule {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: UserRule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IRule parse(String str) {
            str.getClass();
            return new UserRule(SequencesKt.filter(StringsKt.splitToSequence$default(str, new char[]{','}, false, 30, 2, null), new Function1() { // from class: com.motorola.plugin.core.rule.UserRule$Companion$parse$multiValues$1
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

    /* JADX INFO: compiled from: UserRule.kt */
    enum UserAlias {
        DEFAULT,
        WORK_PROFILE,
        GUEST,
        APP_CLONE
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRule(Sequence sequence) {
        super(sequence);
        sequence.getClass();
    }

    private final int getUserId(UserHandle userHandle) {
        return userHandle.hashCode();
    }

    private final boolean matchSingleUser(String str, String str2, UserManager userManager, boolean z) {
        Integer intOrNull = StringsKt.toIntOrNull(str2);
        if (intOrNull != null) {
            if (matchUserId(intOrNull.intValue(), userManager, z)) {
                return true;
            }
        } else if (StringsKt.startsWith$default((CharSequence) str2, '!', false, 2, (Object) null) && !StringsKt.startsWith$default(str2, "!!", false, 2, (Object) null) && str2.length() >= 2) {
            String strSubstring = str2.substring(1);
            strSubstring.getClass();
            if (matchSingleUser(str2, strSubstring, userManager, true)) {
                return true;
            }
        } else if (matchUserAlias(str, str2, userManager, z)) {
            return true;
        }
        return false;
    }

    static /* synthetic */ boolean matchSingleUser$default(UserRule userRule, String str, String str2, UserManager userManager, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return userRule.matchSingleUser(str, str2, userManager, z);
    }

    private final boolean matchUserAlias(final String str, String str2, UserManager userManager, boolean z) {
        Locale locale = Locale.getDefault();
        locale.getClass();
        if (str2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = str2.toUpperCase(locale);
        upperCase.getClass();
        if (Intrinsics.areEqual(upperCase, UserAlias.DEFAULT.name())) {
            if (z) {
                UserHandle userHandleMyUserHandle = Process.myUserHandle();
                userHandleMyUserHandle.getClass();
                return getUserId(userHandleMyUserHandle) != 0;
            }
            UserHandle userHandleMyUserHandle2 = Process.myUserHandle();
            userHandleMyUserHandle2.getClass();
            return getUserId(userHandleMyUserHandle2) == 0;
        }
        if (Intrinsics.areEqual(upperCase, UserAlias.WORK_PROFILE.name())) {
            return z ? !userManager.isManagedProfile() : userManager.isManagedProfile();
        }
        if (Intrinsics.areEqual(upperCase, UserAlias.GUEST.name())) {
            return z ? !userManager.isDemoUser() : userManager.isDemoUser();
        }
        if (!Intrinsics.areEqual(upperCase, UserAlias.APP_CLONE.name())) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.rule.UserRule$matchUserAlias$match$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "match: ignore the user rule cause the unknown key >>" + str + "<<";
                }
            }, 62, null);
            return false;
        }
        if (z) {
            UserHandle userHandleMyUserHandle3 = Process.myUserHandle();
            userHandleMyUserHandle3.getClass();
            int userId = getUserId(userHandleMyUserHandle3);
            return 900 > userId || userId > 909;
        }
        UserHandle userHandleMyUserHandle4 = Process.myUserHandle();
        userHandleMyUserHandle4.getClass();
        int userId2 = getUserId(userHandleMyUserHandle4);
        return 900 <= userId2 && userId2 <= 909;
    }

    static /* synthetic */ boolean matchUserAlias$default(UserRule userRule, String str, String str2, UserManager userManager, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return userRule.matchUserAlias(str, str2, userManager, z);
    }

    private final boolean matchUserId(int i, UserManager userManager, boolean z) {
        if (z) {
            UserHandle userHandleMyUserHandle = Process.myUserHandle();
            userHandleMyUserHandle.getClass();
            return getUserId(userHandleMyUserHandle) != i;
        }
        UserHandle userHandleMyUserHandle2 = Process.myUserHandle();
        userHandleMyUserHandle2.getClass();
        return getUserId(userHandleMyUserHandle2) == i;
    }

    static /* synthetic */ boolean matchUserId$default(UserRule userRule, int i, UserManager userManager, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return userRule.matchUserId(i, userManager, z);
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public int getPriority() {
        return 3;
    }

    @Override // com.motorola.plugin.core.rule.IRule
    public boolean match(Context context) {
        context.getClass();
        UserManager userManager = (UserManager) ContextCompat.getSystemService(context, UserManager.class);
        if (userManager == null) {
            return false;
        }
        for (String str : getMultiValues()) {
            UserRule userRule = this;
            if (matchSingleUser$default(userRule, str, str, userManager, false, 8, null)) {
                return true;
            }
            this = userRule;
        }
        return false;
    }
}
