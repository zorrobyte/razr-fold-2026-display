package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3 extends FunctionReferenceImpl implements Function1 {
    HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(Object obj) {
        super(1, obj, HeadsUpCoordinatorKt.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final GroupLocation invoke(String str) {
        str.getClass();
        return HeadsUpCoordinatorKt.getLocation((Map) this.receiver, str);
    }
}
