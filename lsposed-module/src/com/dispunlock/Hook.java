package com.dispunlock;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Forces DisplayManagerFlags.isExternalDisplayLimitModeEnabled() -> false in
 * system_server, disabling Android's external-display resolution/refresh governor
 * (aconfig flag enable_mode_limit_for_external_display, read-only / unflippable).
 * DisplayModeDirector caches this at construction, which happens after this hook
 * is installed, so the limit vote is never applied.
 */
public class Hook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) {
        if (!"android".equals(lpparam.packageName)) return;
        try {
            XposedHelpers.findAndHookMethod(
                "com.android.server.display.feature.DisplayManagerFlags",
                lpparam.classLoader,
                "isExternalDisplayLimitModeEnabled",
                XC_MethodReplacement.returnConstant(Boolean.FALSE));
            XposedBridge.log("[DispUnlock] isExternalDisplayLimitModeEnabled -> false (external display cap removed)");
        } catch (Throwable t) {
            XposedBridge.log("[DispUnlock] hook FAILED: " + t);
        }
    }
}
