package androidx.media3.common;

import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaLibraryInfo {
    private static final HashSet registeredModules = new HashSet();
    private static String registeredModulesString = "media3.common";

    public static synchronized void registerModule(String str) {
        if (registeredModules.add(str)) {
            registeredModulesString += ", " + str;
        }
    }

    public static synchronized String registeredModules() {
        return registeredModulesString;
    }
}
