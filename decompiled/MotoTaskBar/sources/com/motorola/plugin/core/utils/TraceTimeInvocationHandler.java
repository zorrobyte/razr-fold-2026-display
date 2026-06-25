package com.motorola.plugin.core.utils;

import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: TraceTimeInvocationHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public class TraceTimeInvocationHandler extends AbstractInvocationHandler {
    private final Object target;
    private final String targetInterfaceName;

    public TraceTimeInvocationHandler(Object obj, String str) {
        obj.getClass();
        str.getClass();
        this.target = obj;
        this.targetInterfaceName = str;
    }

    @Override // com.motorola.plugin.core.utils.AbstractInvocationHandler
    protected Object handleInvocation(Object obj, Method method, Object[] objArr) throws IllegalAccessException, InvocationTargetException {
        obj.getClass();
        method.getClass();
        objArr.getClass();
        final String name = method.getName();
        long jCurrentTimeMillis = System.currentTimeMillis();
        Object objInvoke = method.invoke(this.target, Arrays.copyOf(objArr, objArr.length));
        final long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        final boolean z = jCurrentTimeMillis2 >= 300;
        PluginConfigKt.trace$default(null, z ? Level.WARN : Level.DEBUG, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TraceTimeInvocationHandler.handleInvocation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                String str;
                if (z) {
                    return "Transact -> " + this.targetInterfaceName + '#' + ((Object) name) + " cost: " + jCurrentTimeMillis2 + " ms";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Transact -> ");
                sb.append(this.targetInterfaceName);
                sb.append('#');
                sb.append((Object) name);
                sb.append(' ');
                if (jCurrentTimeMillis2 == 0) {
                    str = "";
                } else {
                    str = " cost: " + jCurrentTimeMillis2 + " ms";
                }
                sb.append(str);
                return sb.toString();
            }
        }, 61, null);
        return objInvoke;
    }
}
