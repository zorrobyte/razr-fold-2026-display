package com.dispunlock;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Injected into LocalDisplayAdapter$LocalDisplayDevice.getDisplayDeviceInfoLocked().
 * Rebuilds DisplayDeviceInfo.supportedReadyForModes (Moto's desktop/ReadyFor mode list)
 * from the device's real mSupportedModes: every distinct resolution at its TRUE max
 * refresh. Removes Moto's active-mode resolution ceiling AND the hardcoded 60Hz.
 * Pure reflection; never throws into system_server.
 */
public final class R4 {
    public static void fix(Object device) {
        try {
            Object info = getField(device, "mInfo");
            if (info == null) return;
            Object cur = getField(info, "supportedReadyForModes");
            if (cur == null || Array.getLength(cur) == 0) return; // not a desktop display
            Object sm = getField(device, "mSupportedModes");
            if (sm == null) return;
            int n = (Integer) call(sm, "size");
            LinkedHashMap<Long, float[]> best = new LinkedHashMap<Long, float[]>();
            for (int i = 0; i < n; i++) {
                Object rec = call(sm, "valueAt", new Class[]{int.class}, Integer.valueOf(i));
                Object m = getField(rec, "mMode");
                int w = (Integer) call(m, "getPhysicalWidth");
                int h = (Integer) call(m, "getPhysicalHeight");
                float r = (Float) call(m, "getRefreshRate");
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
            Class<?> modeCls = cur.getClass().getComponentType();
            Constructor<?> ctor = modeCls.getConstructor(int.class, int.class, int.class, float.class);
            Object arr = Array.newInstance(modeCls, list.size());
            for (int i = 0; i < list.size(); i++) {
                float[] v = list.get(i);
                Array.set(arr, i, ctor.newInstance(Integer.valueOf(0), Integer.valueOf((int) v[0]),
                        Integer.valueOf((int) v[1]), Float.valueOf(v[2])));
            }
            setField(info, "supportedReadyForModes", arr);
        } catch (Throwable t) {
            // never crash system_server
        }
    }

    private static Object getField(Object o, String n) throws Exception {
        Field f = findField(o.getClass(), n);
        f.setAccessible(true);
        return f.get(o);
    }

    private static void setField(Object o, String n, Object val) throws Exception {
        Field f = findField(o.getClass(), n);
        f.setAccessible(true);
        f.set(o, val);
    }

    private static Field findField(Class<?> c, String n) throws Exception {
        for (; c != null; c = c.getSuperclass()) {
            try { return c.getDeclaredField(n); } catch (NoSuchFieldException e) { /* up */ }
        }
        throw new NoSuchFieldException(n);
    }

    private static Object call(Object o, String n) throws Exception {
        Method m = o.getClass().getMethod(n);
        return m.invoke(o);
    }

    private static Object call(Object o, String n, Class<?>[] pt, Object... args) throws Exception {
        Method m = o.getClass().getMethod(n, pt);
        return m.invoke(o, args);
    }
}
