package com.motorola.taskbar.reflect.com.android.internal;

import android.util.Log;
import com.android.internal.R;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes2.dex */
public class RR {
    public static final boolean IS_INITIALIZED;
    private static final String TAG = "RR";

    public class dimen {
        public static int navigation_bar_frame_height;
        public static int navigation_bar_gesture_height;
        public static int status_bar_height;
        public static int status_bar_height_landscape;

        static {
            Field field = null;
            try {
                Field[] fields = dimen.class.getFields();
                Field field2 = null;
                for (int i = 0; i < fields.length; i++) {
                    try {
                        field2 = fields[i];
                        field2.setInt(null, R.dimen.class.getField(field2.getName()).getInt(null));
                    } catch (IllegalAccessException | NoSuchFieldException unused) {
                        field = field2;
                        Log.e(RR.TAG, "Unable to initialize " + dimen.class + "; field: " + field.getName());
                        return;
                    }
                }
            } catch (IllegalAccessException | NoSuchFieldException unused2) {
            }
        }
    }

    public class integer {
        public static int config_navBarInteractionMode;

        static {
            Field field = null;
            try {
                Field[] fields = integer.class.getFields();
                Field field2 = null;
                for (int i = 0; i < fields.length; i++) {
                    try {
                        field2 = fields[i];
                        field2.setInt(null, R.integer.class.getField(field2.getName()).getInt(null));
                    } catch (IllegalAccessException | NoSuchFieldException unused) {
                        field = field2;
                        Log.e(RR.TAG, "Unable to initialize " + integer.class + "; field: " + field.getName());
                        return;
                    }
                }
            } catch (IllegalAccessException | NoSuchFieldException unused2) {
            }
        }
    }

    static {
        boolean z = integer.config_navBarInteractionMode > 0;
        if (dimen.status_bar_height <= 0) {
            z = false;
        }
        if (dimen.status_bar_height_landscape <= 0) {
            z = false;
        }
        if (dimen.navigation_bar_frame_height <= 0) {
            z = false;
        }
        IS_INITIALIZED = dimen.navigation_bar_gesture_height > 0 ? z : false;
    }
}
