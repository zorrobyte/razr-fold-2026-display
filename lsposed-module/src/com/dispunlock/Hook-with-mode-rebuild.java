package com.dispunlock;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * system_server hooks to unlock the external/desktop display on the Razr Fold 2026.
 *
 * Hook 1: DisplayManagerFlags.isExternalDisplayLimitModeEnabled() -> false
 *   removes the AOSP external-display pixel-budget governor (so getSupportedModes
 *   exposes 5120x2160 etc).
 *
 * Hook 2: LocalDisplayAdapter$LocalDisplayDevice.getDisplayDeviceInfoLocked()
 *   Moto's "ReadyFor"/desktop mode list (DisplayInfo.supportedReadyForModes) is built
 *   by getMaxResolution() which caps to <= the CURRENT active SF mode (default 3440x1440)
 *   AND hardcodes every entry to 60Hz. Smart Connect only offers / the framework only
 *   applies modes in this list -> 5K2K is locked out and refresh is pinned to 60.
 *   We rebuild supportedReadyForModes from the device's real mSupportedModes: every
 *   distinct resolution at its TRUE max refresh. -> 5120x2160@60, 3440x1440@100, 1080@120...
 */
public class Hook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) {
        if (!"android".equals(lpparam.packageName)) return;
        final ClassLoader cl = lpparam.classLoader;

        try {
            XposedHelpers.findAndHookMethod(
                "com.android.server.display.feature.DisplayManagerFlags", cl,
                "isExternalDisplayLimitModeEnabled",
                XC_MethodReplacement.returnConstant(Boolean.FALSE));
            XposedBridge.log("[DispUnlock] isExternalDisplayLimitModeEnabled -> false");
        } catch (Throwable t) {
            XposedBridge.log("[DispUnlock] hook1 FAILED: " + t);
        }

        try {
            final Class<?> modeCls = XposedHelpers.findClass("android.view.Display$Mode", cl);
            XposedHelpers.findAndHookMethod(
                "com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice", cl,
                "getDisplayDeviceInfoLocked", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        try {
                            Object info = param.getResult();
                            if (info == null) return;
                            Object cur = XposedHelpers.getObjectField(info, "supportedReadyForModes");
                            // only touch desktop/ReadyFor displays (where Moto populated the list)
                            if (cur == null || Array.getLength(cur) == 0) return;

                            Object sm = XposedHelpers.getObjectField(param.thisObject, "mSupportedModes");
                            int n = (Integer) XposedHelpers.callMethod(sm, "size");
                            LinkedHashMap<Long, float[]> best = new LinkedHashMap<Long, float[]>();
                            for (int i = 0; i < n; i++) {
                                Object rec = XposedHelpers.callMethod(sm, "valueAt", i);
                                Object m = XposedHelpers.getObjectField(rec, "mMode");
                                int w = (Integer) XposedHelpers.callMethod(m, "getPhysicalWidth");
                                int h = (Integer) XposedHelpers.callMethod(m, "getPhysicalHeight");
                                float r = (Float) XposedHelpers.callMethod(m, "getRefreshRate");
                                long key = (((long) w) << 20) | (h & 0xFFFFFL);
                                float[] v = best.get(key);
                                if (v == null || r > v[2]) best.put(key, new float[]{w, h, r});
                            }
                            if (best.isEmpty()) return;
                            List<float[]> list = new ArrayList<float[]>(best.values());
                            Collections.sort(list, new Comparator<float[]>() {
                                public int compare(float[] a, float[] b) {
                                    return Float.compare(b[0] * b[1], a[0] * a[1]);
                                }
                            });
                            Object arr = Array.newInstance(modeCls, list.size());
                            for (int i = 0; i < list.size(); i++) {
                                float[] v = list.get(i);
                                Object mode = XposedHelpers.newInstance(modeCls,
                                    new Class[]{int.class, int.class, int.class, float.class},
                                    0, (int) v[0], (int) v[1], v[2]);
                                Array.set(arr, i, mode);
                            }
                            XposedHelpers.setObjectField(info, "supportedReadyForModes", arr);
                            XposedBridge.log("[DispUnlock] supportedReadyForModes rebuilt: " + list.size()
                                + " resolutions @ true max refresh");
                        } catch (Throwable t) {
                            XposedBridge.log("[DispUnlock] readyForModes rebuild FAILED: " + t);
                        }
                    }
                });
            XposedBridge.log("[DispUnlock] getDisplayDeviceInfoLocked hooked");
        } catch (Throwable t) {
            XposedBridge.log("[DispUnlock] hook2 FAILED: " + t);
        }
    }
}
