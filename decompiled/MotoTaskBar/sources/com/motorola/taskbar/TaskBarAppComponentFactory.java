package com.motorola.taskbar;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.core.app.AppComponentFactory;
import com.android.systemui.SystemUIFactory;
import com.motorola.taskbar.reflect.ProxyInitializer;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarAppComponentFactory extends AppComponentFactory {

    public interface ContextAvailableCallback {
        void onContextAvailable(Context context);
    }

    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback contextAvailableCallback);
    }

    public TaskBarAppComponentFactory() {
        if (DebugConfig.DEBUG_APP_INITIATE) {
            Log.d("TaskBarAppCompFactory", "TaskBarAppComponentFactory");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.core.app.AppComponentFactory
    public Application instantiateApplicationCompat(ClassLoader classLoader, String str) {
        if (DebugConfig.DEBUG_APP_INITIATE) {
            Log.d("TaskBarAppCompFactory", "instantiateApplicationCompat");
        }
        if (!str.equals(TaskBarApplication.class.getName())) {
            str = TaskBarApplication.class.getName();
            Log.w("TaskBarAppCompFactory", "instantiateApplicationCompat override Application to TaskBarApplication: " + str);
        }
        Application applicationInstantiateApplicationCompat = super.instantiateApplicationCompat(classLoader, str);
        ProxyInitializer.initialize();
        if (applicationInstantiateApplicationCompat instanceof ContextInitializer) {
            ((ContextInitializer) applicationInstantiateApplicationCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.motorola.taskbar.TaskBarAppComponentFactory$$ExternalSyntheticLambda0
                @Override // com.motorola.taskbar.TaskBarAppComponentFactory.ContextAvailableCallback
                public final void onContextAvailable(Context context) {
                    SystemUIFactory.createFromConfig(context);
                }
            });
            return applicationInstantiateApplicationCompat;
        }
        Log.w("TaskBarAppCompFactory", "instantiateApplicationCompat app is not instanceof ContextInitializer: " + applicationInstantiateApplicationCompat);
        return applicationInstantiateApplicationCompat;
    }
}
