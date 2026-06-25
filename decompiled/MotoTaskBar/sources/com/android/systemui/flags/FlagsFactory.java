package com.android.systemui.flags;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: FlagsFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FlagsFactory {
    public static final FlagsFactory INSTANCE = new FlagsFactory();
    private static final Map flagMap = new LinkedHashMap();

    private FlagsFactory() {
    }

    public static /* synthetic */ ReleasedFlag releasedFlag$default(FlagsFactory flagsFactory, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "systemui";
        }
        return flagsFactory.releasedFlag(str, str2);
    }

    public static /* synthetic */ ResourceBooleanFlag resourceBooleanFlag$default(FlagsFactory flagsFactory, int i, String str, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "systemui";
        }
        return flagsFactory.resourceBooleanFlag(i, str, str2);
    }

    public static /* synthetic */ SysPropBooleanFlag sysPropBooleanFlag$default(FlagsFactory flagsFactory, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "systemui";
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return flagsFactory.sysPropBooleanFlag(str, str2, z);
    }

    public static /* synthetic */ UnreleasedFlag unreleasedFlag$default(FlagsFactory flagsFactory, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "systemui";
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return flagsFactory.unreleasedFlag(str, str2, z);
    }

    public final ReleasedFlag releasedFlag(String str, String str2) {
        str.getClass();
        str2.getClass();
        ReleasedFlag releasedFlag = new ReleasedFlag(str, str2, false, 4, null);
        flagMap.put(str, releasedFlag);
        return releasedFlag;
    }

    public final ResourceBooleanFlag resourceBooleanFlag(int i, String str, String str2) {
        str.getClass();
        str2.getClass();
        ResourceBooleanFlag resourceBooleanFlag = new ResourceBooleanFlag(str, str2, i);
        flagMap.put(str, resourceBooleanFlag);
        return resourceBooleanFlag;
    }

    public final SysPropBooleanFlag sysPropBooleanFlag(String str, String str2, boolean z) {
        str.getClass();
        str2.getClass();
        SysPropBooleanFlag sysPropBooleanFlag = new SysPropBooleanFlag(str, str2, z);
        flagMap.put(str, sysPropBooleanFlag);
        return sysPropBooleanFlag;
    }

    public final UnreleasedFlag unreleasedFlag(String str, String str2, boolean z) {
        str.getClass();
        str2.getClass();
        return new UnreleasedFlag(str, str2, false, false, 8, null);
    }
}
