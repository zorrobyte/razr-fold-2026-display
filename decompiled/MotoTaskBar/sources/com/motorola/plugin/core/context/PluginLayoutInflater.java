package com.motorola.plugin.core.context;

import android.content.Context;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PluginLayoutInflater.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginLayoutInflater extends LayoutInflater implements LayoutInflater.Factory2 {
    private final Object[] myConstructorArgs;
    private final Map myPeerPluginConstructorMap;
    public static final Companion Companion = new Companion(null);
    private static final Class[] VIEW_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private static final String[] SYSTEM_CLASS_PREFIX_LIST = {"android.widget.", "android.webkit.", "android.app.", "com.android.internal.widget."};

    /* JADX INFO: compiled from: PluginLayoutInflater.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginLayoutInflater(Context context) {
        super(context);
        context.getClass();
        this.myPeerPluginConstructorMap = new LinkedHashMap();
        this.myConstructorArgs = new Object[2];
        setFactory2(this);
    }

    private PluginLayoutInflater(LayoutInflater layoutInflater, Context context) {
        super(layoutInflater, context);
        this.myPeerPluginConstructorMap = new LinkedHashMap();
        this.myConstructorArgs = new Object[2];
    }

    private final View createView(View view, final String str, Context context, AttributeSet attributeSet) {
        View view2 = null;
        if (-1 != StringsKt.indexOf$default((CharSequence) str, ".", 0, false, 6, (Object) null)) {
            for (String str2 : SYSTEM_CLASS_PREFIX_LIST) {
                if (!StringsKt.startsWith$default(str, str2, false, 2, (Object) null)) {
                }
            }
            Constructor constructor = (Constructor) this.myPeerPluginConstructorMap.get(str);
            try {
                try {
                    Trace.beginSection('[' + ((Object) context.getPackageName()) + "]plugin view");
                    Level level = Level.DEBUG;
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginLayoutInflater.createView.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return Intrinsics.stringPlus("Create plugin view ", str);
                        }
                    }, 60, null);
                    if (constructor == null) {
                        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginLayoutInflater.createView.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* JADX INFO: renamed from: invoke */
                            public final Object mo2224invoke() {
                                return "Create plugin view " + str + ", try find the init params";
                            }
                        }, 60, null);
                        Class<? extends U> clsAsSubclass = Class.forName(str, false, context.getClassLoader()).asSubclass(View.class);
                        if (clsAsSubclass == 0) {
                            constructor = null;
                        } else {
                            Class[] clsArr = VIEW_CONSTRUCTOR_SIGNATURE;
                            constructor = clsAsSubclass.getConstructor((Class[]) Arrays.copyOf(clsArr, clsArr.length));
                        }
                        if (constructor != null) {
                            constructor.setAccessible(true);
                        }
                        this.myPeerPluginConstructorMap.put(str, constructor);
                    }
                    Object[] objArr = this.myConstructorArgs;
                    objArr[0] = context;
                    objArr[1] = attributeSet;
                    if (constructor != null) {
                        view2 = (View) constructor.newInstance(Arrays.copyOf(objArr, objArr.length));
                    }
                    if (view2 instanceof ViewStub) {
                        ((ViewStub) view2).setLayoutInflater(cloneInContext(context));
                    }
                    return view2;
                } catch (Exception e) {
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, e, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginLayoutInflater.createView.4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return Intrinsics.stringPlus("Failed to create plugin view ", str);
                        }
                    }, 52, null);
                    throw e;
                }
            } finally {
                Trace.endSection();
            }
        }
        return null;
    }

    @Override // android.view.LayoutInflater
    public LayoutInflater cloneInContext(Context context) {
        context.getClass();
        return Intrinsics.areEqual(context, getContext()) ? this : new PluginLayoutInflater(this, context);
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        return createView(view, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        return onCreateView((View) null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater
    protected View onCreateView(String str, AttributeSet attributeSet) throws ClassNotFoundException {
        View viewCreateView;
        str.getClass();
        attributeSet.getClass();
        String[] strArr = SYSTEM_CLASS_PREFIX_LIST;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str2 = strArr[i];
            i++;
            try {
                viewCreateView = createView(str, str2, attributeSet);
            } catch (ClassNotFoundException unused) {
            }
            if (viewCreateView != null) {
                return viewCreateView;
            }
        }
        View viewOnCreateView = super.onCreateView(str, attributeSet);
        viewOnCreateView.getClass();
        return viewOnCreateView;
    }
}
