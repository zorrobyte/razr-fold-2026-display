package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: HeadsUpCoordinatorLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpCoordinatorLogger {
    private final LogBuffer buffer;
    private final boolean verbose;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinatorLogger(LogBuffer logBuffer) {
        this(logBuffer, Log.isLoggable("HeadsUpCoordinator", 2));
        logBuffer.getClass();
    }

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer, boolean z) {
        logBuffer.getClass();
        this.buffer = logBuffer;
        this.verbose = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEntryDisqualifiedFromFullScreen$lambda$12(String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryDisqualifiedFromFullScreen$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "updated entry no longer qualifies for full screen intent: " + logMessage.getStr1() + " because " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEntryUpdatedByRanking$lambda$8(String str, boolean z, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setBool1(z);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryUpdatedByRanking$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "updating entry via ranking applied: " + logMessage.getStr1() + " updated shouldHeadsUp=" + logMessage.getBool1() + " because " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEntryUpdatedToFullScreen$lambda$10(String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryUpdatedToFullScreen$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "updating entry to launch full screen intent: " + logMessage.getStr1() + " because " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEvaluatingGroup$lambda$6(String str, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEvaluatingGroup$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "evaluating group for alert transfer: " + logMessage.getStr1() + " numPostedEntries=" + logMessage.getInt1() + " logicalGroupSize=" + logMessage.getInt2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEvaluatingGroups$lambda$4(int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEvaluatingGroups$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "evaluating groups for alert transfer: " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPostedEntryWillEvaluate$lambda$0(HeadsUpCoordinator.PostedEntry postedEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(postedEntry.getKey());
        logMessage.setStr2(str);
        logMessage.setBool1(postedEntry.getShouldHeadsUpEver());
        logMessage.setBool2(postedEntry.getShouldHeadsUpAgain());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPostedEntryWillEvaluate$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "will evaluate posted entry " + logMessage.getStr1() + ": reason=" + logMessage.getStr2() + " shouldHeadsUpEver=" + logMessage.getBool1() + " shouldHeadsUpAgain=" + logMessage.getBool2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPostedEntryWillNotEvaluate$lambda$2(HeadsUpCoordinator.PostedEntry postedEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(postedEntry.getKey());
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPostedEntryWillNotEvaluate$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "will not evaluate posted entry " + logMessage.getStr1() + ": reason=" + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSummaryMarkedInterrupted$lambda$14(String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSummaryMarkedInterrupted$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "marked group summary as interrupted: " + logMessage.getStr1() + " for alert transfer to child: " + logMessage.getStr2();
    }

    public final void logEntryDisqualifiedFromFullScreen(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryDisqualifiedFromFullScreen$lambda$12(str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryDisqualifiedFromFullScreen$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEntryUpdatedByRanking(final String str, final boolean z, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryUpdatedByRanking$lambda$8(str, z, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryUpdatedByRanking$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEntryUpdatedToFullScreen(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryUpdatedToFullScreen$lambda$10(str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logEntryUpdatedToFullScreen$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEvaluatingGroup(final String str, final int i, final int i2) {
        str.getClass();
        if (this.verbose) {
            LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logEvaluatingGroup$lambda$6(str, i, i2, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logEvaluatingGroup$lambda$7((LogMessage) obj);
                }
            }, null, 16, null);
        }
    }

    public final void logEvaluatingGroups(final int i) {
        if (this.verbose) {
            LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logEvaluatingGroups$lambda$4(i, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logEvaluatingGroups$lambda$5((LogMessage) obj);
                }
            }, null, 16, null);
        }
    }

    public final void logPostedEntryWillEvaluate(final HeadsUpCoordinator.PostedEntry postedEntry, final String str) {
        postedEntry.getClass();
        str.getClass();
        if (this.verbose) {
            LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logPostedEntryWillEvaluate$lambda$0(postedEntry, str, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logPostedEntryWillEvaluate$lambda$1((LogMessage) obj);
                }
            }, null, 16, null);
        }
    }

    public final void logPostedEntryWillNotEvaluate(final HeadsUpCoordinator.PostedEntry postedEntry, final String str) {
        postedEntry.getClass();
        str.getClass();
        if (this.verbose) {
            LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logPostedEntryWillNotEvaluate$lambda$2(postedEntry, str, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HeadsUpCoordinatorLogger.logPostedEntryWillNotEvaluate$lambda$3((LogMessage) obj);
                }
            }, null, 16, null);
        }
    }

    public final void logSummaryMarkedInterrupted(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logSummaryMarkedInterrupted$lambda$14(str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinatorLogger.logSummaryMarkedInterrupted$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
