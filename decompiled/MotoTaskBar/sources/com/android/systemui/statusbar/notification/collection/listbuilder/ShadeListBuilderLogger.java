package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.util.Compile;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ShadeListBuilderLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeListBuilderLogger {
    private final LogBuffer buffer;
    private final boolean logRankInFinalList;

    public ShadeListBuilderLogger(NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        notifPipelineFlags.getClass();
        logBuffer.getClass();
        this.buffer = logBuffer;
        this.logRankInFinalList = Compile.IS_DEBUG && notifPipelineFlags.isDevLoggingEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDuplicateSummary$lambda$6(int i, GroupEntry groupEntry, NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr3(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDuplicateSummary$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ") Duplicate summary for group \"" + logMessage.getStr1() + "\": \"" + logMessage.getStr2() + "\" vs. \"" + logMessage.getStr3() + "\"";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDuplicateTopLevelKey$lambda$8(int i, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDuplicateTopLevelKey$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ") Duplicate top-level key: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEndBuildList$lambda$2(int i, int i2, int i3, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setInt1(i2);
        logMessage.setInt2(i3);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEndBuildList$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ") Build complete (" + logMessage.getInt1() + " top-level entries, " + logMessage.getInt2() + " children) enforcedVisualStability=" + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEntryAttachStateChanged$lambda$10(int i, ListEntry listEntry, GroupEntry groupEntry, GroupEntry groupEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(NotificationUtilsKt.getLogKey(listEntry));
        logMessage.setStr2(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        logMessage.setStr3(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryAttachStateChanged$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        String str = (logMessage.getStr2() != null || logMessage.getStr3() == null) ? (logMessage.getStr2() == null || logMessage.getStr3() != null) ? (logMessage.getStr2() == null && logMessage.getStr3() == null) ? "MODIFIED (DETACHED)" : "MODIFIED (ATTACHED)" : "DETACHED" : "ATTACHED";
        return "(Build " + logMessage.getLong1() + ") " + str + " {" + logMessage.getStr1() + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFilterChanged$lambda$22(int i, NotifFilter notifFilter, NotifFilter notifFilter2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(notifFilter != null ? notifFilter.getName() : null);
        logMessage.setStr2(notifFilter2 != null ? notifFilter2.getName() : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFilterChanged$lambda$23(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Filter changed: " + logMessage.getStr1() + " -> " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFinalList$lambda$30(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFinalList$lambda$31(LogMessage logMessage) {
        logMessage.getClass();
        return "(empty list)";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFinalList$lambda$32(int i, ListEntry listEntry, ShadeListBuilderLogger shadeListBuilderLogger, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setStr1(NotificationUtilsKt.getLogKey(listEntry));
        logMessage.setBool1(shadeListBuilderLogger.logRankInFinalList);
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        representativeEntry.getClass();
        logMessage.setInt2(representativeEntry.getRanking().getRank());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFinalList$lambda$34(LogMessage logMessage) {
        logMessage.getClass();
        String str = "[" + logMessage.getInt1() + "] " + logMessage.getStr1();
        if (!logMessage.getBool1()) {
            return str;
        }
        return str + " rank=" + logMessage.getInt2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFinalList$lambda$38$lambda$35(NotificationEntry notificationEntry, ShadeListBuilderLogger shadeListBuilderLogger, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setBool1(shadeListBuilderLogger.logRankInFinalList);
        logMessage.setInt2(notificationEntry.getRanking().getRank());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFinalList$lambda$38$lambda$37(LogMessage logMessage) {
        logMessage.getClass();
        String str = "  [*] " + logMessage.getStr1() + " (summary)";
        if (!logMessage.getBool1()) {
            return str;
        }
        return str + " rank=" + logMessage.getInt2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFinalList$lambda$39(int i, NotificationEntry notificationEntry, ShadeListBuilderLogger shadeListBuilderLogger, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setBool1(shadeListBuilderLogger.logRankInFinalList);
        logMessage.setInt2(notificationEntry.getRanking().getRank());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFinalList$lambda$41(LogMessage logMessage) {
        logMessage.getClass();
        String str = "  [" + logMessage.getInt1() + "] " + logMessage.getStr1();
        if (!logMessage.getBool1()) {
            return str;
        }
        return str + " rank=" + logMessage.getInt2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logGroupPruningSuppressed$lambda$18(int i, GroupEntry groupEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logGroupPruningSuppressed$lambda$19(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Group pruning suppressed; keeping parent '" + logMessage.getStr1() + "'";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logOnBuildList$lambda$0(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logOnBuildList$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Request received from NotifCollection for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logParentChangeSuppressedStarted$lambda$14(int i, GroupEntry groupEntry, GroupEntry groupEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        logMessage.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logParentChangeSuppressedStarted$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Change of parent to '" + logMessage.getStr1() + "' suppressed; keeping parent '" + logMessage.getStr2() + "'";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logParentChangeSuppressedStopped$lambda$16(int i, GroupEntry groupEntry, GroupEntry groupEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        logMessage.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logParentChangeSuppressedStopped$lambda$17(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Change of parent to '" + logMessage.getStr1() + "' no longer suppressed; replaced parent '" + logMessage.getStr2() + "'";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logParentChanged$lambda$12(int i, GroupEntry groupEntry, GroupEntry groupEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        logMessage.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logParentChanged$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        if (logMessage.getStr1() == null && logMessage.getStr2() != null) {
            return "(Build " + logMessage.getLong1() + ")     Parent is {" + logMessage.getStr2() + "}";
        }
        if (logMessage.getStr1() != null && logMessage.getStr2() == null) {
            return "(Build " + logMessage.getLong1() + ")     Parent was {" + logMessage.getStr1() + "}";
        }
        return "(Build " + logMessage.getLong1() + ")     Reparent: {" + logMessage.getStr1() + "} -> {" + logMessage.getStr2() + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPipelineRunSuppressed$lambda$42(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPipelineRunSuppressed$lambda$43(LogMessage logMessage) {
        logMessage.getClass();
        return "Suppressing pipeline run during animation.";
    }

    private final void logPluggableInvalidated(final String str, final Pluggable pluggable, final int i, final String str2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPluggableInvalidated$lambda$4(str, pluggable, i, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPluggableInvalidated$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPluggableInvalidated$lambda$4(String str, Pluggable pluggable, int i, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(pluggable.getName());
        logMessage.setInt1(i);
        logMessage.setStr3(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPluggableInvalidated$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Invalidated while " + PipelineState.getStateName(logMessage.getInt1()) + " by " + logMessage.getStr1() + " \"" + logMessage.getStr2() + "\" because " + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPromoterChanged$lambda$24(int i, NotifPromoter notifPromoter, NotifPromoter notifPromoter2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(notifPromoter != null ? notifPromoter.getName() : null);
        logMessage.setStr2(notifPromoter2 != null ? notifPromoter2.getName() : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPromoterChanged$lambda$25(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Promoter changed: " + logMessage.getStr1() + " -> " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPrunedReasonChanged$lambda$20(int i, String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPrunedReasonChanged$lambda$21(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Pruned reason changed: " + logMessage.getStr1() + " -> " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSectionChangeSuppressed$lambda$28(int i, NotifSection notifSection, NotifSection notifSection2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(notifSection != null ? notifSection.getLabel() : null);
        logMessage.setStr2(notifSection2 != null ? notifSection2.getLabel() : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSectionChangeSuppressed$lambda$29(LogMessage logMessage) {
        logMessage.getClass();
        return "(Build " + logMessage.getLong1() + ")     Suppressing section change to " + logMessage.getStr1() + " (staying at " + logMessage.getStr2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSectionChanged$lambda$26(int i, NotifSection notifSection, NotifSection notifSection2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setLong1(i);
        logMessage.setStr1(notifSection != null ? notifSection.getLabel() : null);
        logMessage.setStr2(notifSection2 != null ? notifSection2.getLabel() : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSectionChanged$lambda$27(LogMessage logMessage) {
        logMessage.getClass();
        if (logMessage.getStr1() == null) {
            return "(Build " + logMessage.getLong1() + ")     Section assigned: " + logMessage.getStr2();
        }
        return "(Build " + logMessage.getLong1() + ")     Section changed: " + logMessage.getStr1() + " -> " + logMessage.getStr2();
    }

    public final void logDuplicateSummary(final int i, final GroupEntry groupEntry, final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        groupEntry.getClass();
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logDuplicateSummary$lambda$6(i, groupEntry, notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logDuplicateSummary$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDuplicateTopLevelKey(final int i, final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logDuplicateTopLevelKey$lambda$8(i, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logDuplicateTopLevelKey$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEndBuildList(final int i, final int i2, final int i3, final boolean z) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logEndBuildList$lambda$2(i, i2, i3, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logEndBuildList$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEntryAttachStateChanged(final int i, final ListEntry listEntry, final GroupEntry groupEntry, final GroupEntry groupEntry2) {
        listEntry.getClass();
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logEntryAttachStateChanged$lambda$10(i, listEntry, groupEntry, groupEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logEntryAttachStateChanged$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFilterChanged(final int i, final NotifFilter notifFilter, final NotifFilter notifFilter2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logFilterChanged$lambda$22(i, notifFilter, notifFilter2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logFilterChanged$lambda$23((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFinalList(List list) {
        list.getClass();
        if (list.isEmpty()) {
            LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ShadeListBuilderLogger.logFinalList$lambda$30((LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ShadeListBuilderLogger.logFinalList$lambda$31((LogMessage) obj);
                }
            }, null, 16, null);
        }
        int size = list.size();
        for (final int i = 0; i < size; i++) {
            final ListEntry listEntry = (ListEntry) list.get(i);
            LogBuffer logBuffer = this.buffer;
            LogLevel logLevel = LogLevel.DEBUG;
            LogBuffer.log$default(logBuffer, "ShadeListBuilder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ShadeListBuilderLogger.logFinalList$lambda$32(i, listEntry, this, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ShadeListBuilderLogger.logFinalList$lambda$34((LogMessage) obj);
                }
            }, null, 16, null);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                final NotificationEntry summary = groupEntry.getSummary();
                if (summary != null) {
                    LogBuffer.log$default(this.buffer, "ShadeListBuilder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda18
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return ShadeListBuilderLogger.logFinalList$lambda$38$lambda$35(summary, this, (LogMessage) obj);
                        }
                    }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda19
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return ShadeListBuilderLogger.logFinalList$lambda$38$lambda$37((LogMessage) obj);
                        }
                    }, null, 16, null);
                }
                int size2 = groupEntry.getChildren().size();
                for (final int i2 = 0; i2 < size2; i2++) {
                    final NotificationEntry notificationEntry = (NotificationEntry) groupEntry.getChildren().get(i2);
                    LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda20
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return ShadeListBuilderLogger.logFinalList$lambda$39(i2, notificationEntry, this, (LogMessage) obj);
                        }
                    }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda21
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return ShadeListBuilderLogger.logFinalList$lambda$41((LogMessage) obj);
                        }
                    }, null, 16, null);
                }
            }
        }
    }

    public final void logFinalizeFilterInvalidated(NotifFilter notifFilter, int i, String str) {
        notifFilter.getClass();
        logPluggableInvalidated("Finalize NotifFilter", notifFilter, i, str);
    }

    public final void logGroupPruningSuppressed(final int i, final GroupEntry groupEntry) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logGroupPruningSuppressed$lambda$18(i, groupEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logGroupPruningSuppressed$lambda$19((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifComparatorInvalidated(NotifComparator notifComparator, int i, String str) {
        notifComparator.getClass();
        logPluggableInvalidated("NotifComparator", notifComparator, i, str);
    }

    public final void logNotifSectionInvalidated(NotifSectioner notifSectioner, int i, String str) {
        notifSectioner.getClass();
        logPluggableInvalidated("NotifSection", notifSectioner, i, str);
    }

    public final void logOnBuildList(final String str) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logOnBuildList$lambda$0(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logOnBuildList$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logParentChangeSuppressedStarted(final int i, final GroupEntry groupEntry, final GroupEntry groupEntry2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChangeSuppressedStarted$lambda$14(i, groupEntry, groupEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChangeSuppressedStarted$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logParentChangeSuppressedStopped(final int i, final GroupEntry groupEntry, final GroupEntry groupEntry2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChangeSuppressedStopped$lambda$16(i, groupEntry, groupEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChangeSuppressedStopped$lambda$17((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logParentChanged(final int i, final GroupEntry groupEntry, final GroupEntry groupEntry2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChanged$lambda$12(i, groupEntry, groupEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logParentChanged$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logPipelineRunSuppressed() {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPipelineRunSuppressed$lambda$42((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPipelineRunSuppressed$lambda$43((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logPreGroupFilterInvalidated(NotifFilter notifFilter, int i, String str) {
        notifFilter.getClass();
        logPluggableInvalidated("Pre-group NotifFilter", notifFilter, i, str);
    }

    public final void logPreRenderInvalidated(Invalidator invalidator, int i, String str) {
        invalidator.getClass();
        logPluggableInvalidated("Pre-render Invalidator", invalidator, i, str);
    }

    public final void logPromoterChanged(final int i, final NotifPromoter notifPromoter, final NotifPromoter notifPromoter2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPromoterChanged$lambda$24(i, notifPromoter, notifPromoter2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPromoterChanged$lambda$25((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logPromoterInvalidated(NotifPromoter notifPromoter, int i, String str) {
        notifPromoter.getClass();
        logPluggableInvalidated("NotifPromoter", notifPromoter, i, str);
    }

    public final void logPrunedReasonChanged(final int i, final String str, final String str2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPrunedReasonChanged$lambda$20(i, str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logPrunedReasonChanged$lambda$21((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logReorderingAllowedInvalidated(NotifStabilityManager notifStabilityManager, int i, String str) {
        notifStabilityManager.getClass();
        logPluggableInvalidated("ReorderingNowAllowed", notifStabilityManager, i, str);
    }

    public final void logSectionChangeSuppressed(final int i, final NotifSection notifSection, final NotifSection notifSection2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logSectionChangeSuppressed$lambda$28(i, notifSection, notifSection2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logSectionChangeSuppressed$lambda$29((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logSectionChanged(final int i, final NotifSection notifSection, final NotifSection notifSection2) {
        LogBuffer.log$default(this.buffer, "ShadeListBuilder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logSectionChanged$lambda$26(i, notifSection, notifSection2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeListBuilderLogger.logSectionChanged$lambda$27((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
