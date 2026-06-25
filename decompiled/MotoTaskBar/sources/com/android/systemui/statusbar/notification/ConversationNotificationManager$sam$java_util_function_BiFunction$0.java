package com.android.systemui.statusbar.notification;

import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: ConversationNotifications.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ConversationNotificationManager$sam$java_util_function_BiFunction$0 implements BiFunction {
    private final /* synthetic */ Function2 function;

    ConversationNotificationManager$sam$java_util_function_BiFunction$0(Function2 function2) {
        function2.getClass();
        this.function = function2;
    }

    @Override // java.util.function.BiFunction
    public final /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.function.invoke(obj, obj2);
    }
}
