package com.android.systemui.plugins;

import android.content.Context;
import android.text.TextUtils;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public interface PluginManager {
    public static final int CLOCK_FACE_PLUGIN = 0;
    public static final int END_PLUGIN = 3;
    public static final int LS_CLOCK_FACE_PLUGIN = 1;
    public static final int LS_MONETIZATION_PLUGIN = 2;
    public static final String NOTIFICATION_CHANNEL_ID = "ALR";
    public static final String PLUGIN_CHANGED = "com.android.systemui.action.PLUGIN_CHANGED";

    public class Helper {
        public static String getAction(Class cls) {
            ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
            if (providesInterface == null) {
                throw new RuntimeException(cls + " doesn't provide an interface");
            }
            if (!TextUtils.isEmpty(providesInterface.action())) {
                return providesInterface.action();
            }
            throw new RuntimeException(cls + " doesn't provide an action");
        }
    }

    public interface MotoPluginListener {
        void onPluginChanged(Plugin plugin);
    }

    void addPluginListener(PluginListener pluginListener, Class cls);

    void addPluginListener(PluginListener pluginListener, Class cls, boolean z);

    void addPluginListener(String str, PluginListener pluginListener, Class cls);

    void addPluginListener(String str, PluginListener pluginListener, Class cls, boolean z);

    boolean dependsOn(Plugin plugin, Class cls);

    default void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    default void endMotoPlugin(Context context, int i) {
    }

    String[] getPrivilegedPlugins();

    void removePluginListener(PluginListener pluginListener);

    default void startMotoPlugin(Context context, int i, MotoPluginListener motoPluginListener) {
    }
}
