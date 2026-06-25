package com.motorola.plugin.core.context;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.Disposable;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginContext extends ContextThemeWrapper implements Disposable {
    private boolean isDisposed;
    private final ComponentCallbacksController mCallbacksController;
    private final ClassLoader mClassLoader;
    private final Lazy mInflater$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginContext(Context context, Resources.Theme theme, ConfigurationController configurationController, ClassLoader classLoader) {
        super(context, theme);
        context.getClass();
        theme.getClass();
        classLoader.getClass();
        this.mClassLoader = classLoader;
        this.mInflater$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.context.PluginContext$mInflater$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final PluginLayoutInflater mo2224invoke() {
                return new PluginLayoutInflater(this.this$0);
            }
        });
        this.mCallbacksController = new ComponentCallbacksController(configurationController);
    }

    private final LayoutInflater getMInflater() {
        return (LayoutInflater) this.mInflater$delegate.getValue();
    }

    public final void destroy() {
        dispose();
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.isDisposed = true;
        this.mCallbacksController.dispose();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context getApplicationContext() {
        return this;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        str.getClass();
        return Intrinsics.areEqual("layout_inflater", str) ? getMInflater() : getBaseContext().getSystemService(str);
    }

    public final boolean isDisposed() {
        return this.isDisposed;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        componentCallbacks.getClass();
        this.mCallbacksController.registerComponentCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        componentCallbacks.getClass();
        this.mCallbacksController.unregisterComponentCallbacks(componentCallbacks);
    }
}
