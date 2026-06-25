package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationStatsLoggerCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0 implements Consumer {
    private final /* synthetic */ Function1 function;

    NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(Function1 function1) {
        function1.getClass();
        this.function = function1;
    }

    @Override // java.util.function.Consumer
    public final /* synthetic */ void accept(Object obj) {
        this.function.invoke(obj);
    }
}
