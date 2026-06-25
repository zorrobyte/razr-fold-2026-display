package com.motorola.taskbar.model;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.window.WindowContext;
import com.android.systemui.Dependency;
import com.motorola.taskbar.util.DebugConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DisplayWindowManager {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String TAG = DisplayWindowManager.class.getSimpleName();
    private Handler mBgHandler;
    private Context mContext;
    private DisplayManager mDisplayManager;
    private SparseArray mDisplayWindowViewManagerArray = new SparseArray();
    private DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.motorola.taskbar.model.DisplayWindowManager.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            synchronized (DisplayWindowManager.this.mDisplayWindowViewManagerArray) {
                try {
                    SparseArray sparseArray = (SparseArray) DisplayWindowManager.this.mDisplayWindowViewManagerArray.get(i);
                    if (sparseArray == null) {
                        return;
                    }
                    int size = sparseArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ((WindowViewManager) sparseArray.valueAt(i2)).release();
                    }
                    DisplayWindowManager.this.mDisplayWindowViewManagerArray.remove(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };

    public interface ConfigurationChangedCallback {
        void onConfigurationChanged(Configuration configuration);
    }

    public class WindowViewManager {
        private final List mConfigurationChangedCallbacks = new ArrayList();
        private ComponentCallbacks mWindowComponentCallbacks;
        public final WindowContext windowContext;
        public final WindowManager windowManager;

        public WindowViewManager(WindowContext windowContext, WindowManager windowManager) {
            ComponentCallbacks componentCallbacks = new ComponentCallbacks() { // from class: com.motorola.taskbar.model.DisplayWindowManager.WindowViewManager.1
                @Override // android.content.ComponentCallbacks
                public void onConfigurationChanged(Configuration configuration) {
                    if (DisplayWindowManager.DEBUG) {
                        Log.d(DisplayWindowManager.TAG, "onConfigurationChanged: " + configuration);
                    }
                    synchronized (WindowViewManager.this.mConfigurationChangedCallbacks) {
                        try {
                            Iterator it = WindowViewManager.this.mConfigurationChangedCallbacks.iterator();
                            while (it.hasNext()) {
                                ((ConfigurationChangedCallback) it.next()).onConfigurationChanged(configuration);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ComponentCallbacks
                public void onLowMemory() {
                }
            };
            this.mWindowComponentCallbacks = componentCallbacks;
            this.windowContext = windowContext;
            this.windowManager = windowManager;
            windowContext.registerComponentCallbacks(componentCallbacks);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void release() {
            synchronized (this.mConfigurationChangedCallbacks) {
                try {
                    WindowContext windowContext = this.windowContext;
                    if (windowContext != null) {
                        windowContext.unregisterComponentCallbacks(this.mWindowComponentCallbacks);
                        this.mConfigurationChangedCallbacks.clear();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void addConfigurationChangedCallbacks(ConfigurationChangedCallback configurationChangedCallback) {
            synchronized (this.mConfigurationChangedCallbacks) {
                this.mConfigurationChangedCallbacks.add(configurationChangedCallback);
            }
        }

        public void addView(View view, ViewGroup.LayoutParams layoutParams) {
            this.windowManager.addView(view, layoutParams);
        }

        public Context getContext() {
            return this.windowContext;
        }

        public WindowManager getWindowManager() {
            return this.windowManager;
        }

        public View inflate(int i, ViewGroup viewGroup) {
            return ((LayoutInflater) this.windowContext.getSystemService("layout_inflater")).inflate(i, viewGroup);
        }

        public void removeConfigurationChangedCallbacks(ConfigurationChangedCallback configurationChangedCallback) {
            synchronized (this.mConfigurationChangedCallbacks) {
                this.mConfigurationChangedCallbacks.remove(configurationChangedCallback);
            }
        }

        public void removeView(View view) {
            this.windowManager.removeView(view);
        }

        public void removeViewImmediate(View view) {
            this.windowManager.removeViewImmediate(view);
        }

        public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
            this.windowManager.updateViewLayout(view, layoutParams);
        }
    }

    public DisplayWindowManager(Context context, Handler handler) {
        this.mContext = context;
        this.mBgHandler = handler;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, this.mBgHandler);
    }

    private WindowViewManager createWindowViewManager(int i, int i2) {
        try {
            if (DEBUG) {
                Log.d(TAG, "createWindowViewManager displayId: " + i + " windowType :" + i2);
            }
            Context contextCreateDisplayContext = this.mContext.createDisplayContext(this.mDisplayManager.getDisplay(i));
            return new WindowViewManager(contextCreateDisplayContext.createWindowContext(i2, null), (WindowManager) contextCreateDisplayContext.getSystemService("window"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WindowViewManager getWindowViewManager(int i, int i2) {
        return ((DisplayWindowManager) Dependency.get(DisplayWindowManager.class)).getWindowViewManagerInternal(i, i2);
    }

    private WindowViewManager getWindowViewManagerInternal(int i, int i2) {
        WindowViewManager windowViewManagerCreateWindowViewManager;
        synchronized (this.mDisplayWindowViewManagerArray) {
            try {
                SparseArray sparseArray = (SparseArray) this.mDisplayWindowViewManagerArray.get(i);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mDisplayWindowViewManagerArray.put(i, sparseArray);
                }
                windowViewManagerCreateWindowViewManager = (WindowViewManager) sparseArray.get(i2);
                if (windowViewManagerCreateWindowViewManager == null && (windowViewManagerCreateWindowViewManager = createWindowViewManager(i, i2)) != null) {
                    sparseArray.put(i2, windowViewManagerCreateWindowViewManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return windowViewManagerCreateWindowViewManager;
    }
}
