package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ShadeEventCoordinatorLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeEventCoordinatorLogger {
    private final LogBuffer buffer;

    public ShadeEventCoordinatorLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifRemovedByUser$lambda$2(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifRemovedByUser$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Notification removed by user";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logShadeEmptied$lambda$0(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logShadeEmptied$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Shade emptied";
    }

    public final void logNotifRemovedByUser() {
        LogBuffer.log$default(this.buffer, "ShadeEventCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeEventCoordinatorLogger.logNotifRemovedByUser$lambda$2((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeEventCoordinatorLogger.logNotifRemovedByUser$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logShadeEmptied() {
        LogBuffer.log$default(this.buffer, "ShadeEventCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeEventCoordinatorLogger.logShadeEmptied$lambda$0((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeEventCoordinatorLogger.logShadeEmptied$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
