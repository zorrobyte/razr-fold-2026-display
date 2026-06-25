package com.motorola.plugin.core.context;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;

/* JADX INFO: compiled from: ComponentCallbackContextWrapper.kt */
/* JADX INFO: loaded from: classes2.dex */
public class ComponentCallbackContextWrapper extends ContextWrapper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComponentCallbackContextWrapper(Context context) {
        super(context);
        context.getClass();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        getBaseContext().registerComponentCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        getBaseContext().unregisterComponentCallbacks(componentCallbacks);
    }
}
