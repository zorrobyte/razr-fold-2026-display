package com.android.systemui;

import android.content.Context;
import com.android.systemui.dagger.DaggerSystemUIRootComponent;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dagger.SystemUIRootComponent;

/* JADX INFO: loaded from: classes.dex */
public class SystemUIFactory {
    static SystemUIFactory mFactory;
    private SystemUIRootComponent mRootComponent;

    public class ContextHolder {
        private Context mContext;

        public ContextHolder(Context context) {
            this.mContext = context;
        }

        public Context provideContext() {
            return this.mContext;
        }
    }

    static void cleanup() {
        mFactory = null;
    }

    public static void createFromConfig(Context context) {
        if (mFactory != null) {
            return;
        }
        SystemUIFactory systemUIFactory = new SystemUIFactory();
        mFactory = systemUIFactory;
        systemUIFactory.init(context);
    }

    public static SystemUIFactory getInstance() {
        return mFactory;
    }

    private void init(Context context) {
        this.mRootComponent = buildSystemUIRootComponent(context);
        Dependency dependency = new Dependency();
        this.mRootComponent.createDependency().createSystemUI(dependency);
        dependency.start();
    }

    protected SystemUIRootComponent buildSystemUIRootComponent(Context context) {
        return DaggerSystemUIRootComponent.builder().dependencyProvider(new DependencyProvider()).contextHolder(new ContextHolder(context)).build();
    }

    public SystemUIRootComponent getRootComponent() {
        return this.mRootComponent;
    }
}
