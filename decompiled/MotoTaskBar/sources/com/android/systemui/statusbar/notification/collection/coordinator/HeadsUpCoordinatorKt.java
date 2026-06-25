package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class HeadsUpCoordinatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final GroupLocation getLocation(Map map, String str) {
        return (GroupLocation) map.getOrDefault(str, GroupLocation.Detached);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object modifyHuns(HeadsUpManager headsUpManager, Function1 function1) {
        HunMutatorImpl hunMutatorImpl = new HunMutatorImpl(headsUpManager);
        Object objInvoke = function1.invoke(hunMutatorImpl);
        hunMutatorImpl.commitModifications();
        return objInvoke;
    }
}
