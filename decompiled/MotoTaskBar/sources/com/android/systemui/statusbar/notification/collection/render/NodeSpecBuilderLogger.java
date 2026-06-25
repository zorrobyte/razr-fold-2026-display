package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.util.Compile;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NodeSpecBuilderLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NodeSpecBuilderLogger {
    private final LogBuffer buffer;
    private final Lazy devLoggingEnabled$delegate;

    public NodeSpecBuilderLogger(final NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        notifPipelineFlags.getClass();
        logBuffer.getClass();
        this.buffer = logBuffer;
        this.devLoggingEnabled$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Boolean.valueOf(notifPipelineFlags.isDevLoggingEnabled());
            }
        });
    }

    private final boolean getDevLoggingEnabled() {
        return ((Boolean) this.devLoggingEnabled$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logBuildNodeSpec$lambda$1(List list, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(list.size());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logBuildNodeSpec$lambda$2(LogMessage logMessage) {
        logMessage.getClass();
        return "buildNodeSpec finished with " + logMessage.getInt1() + " sections";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logBuildNodeSpec$lambda$3(NotifSection notifSection, Map map, Map map2, LogMessage logMessage) {
        String name;
        String nodeLabel;
        NotifSectioner sectioner;
        logMessage.getClass();
        if (notifSection == null || (sectioner = notifSection.getSectioner()) == null || (name = sectioner.getName()) == null) {
            name = "(null)";
        }
        logMessage.setStr1(name);
        NodeController nodeController = (NodeController) map.get(notifSection);
        if (nodeController == null || (nodeLabel = nodeController.getNodeLabel()) == null) {
            nodeLabel = "(none)";
        }
        logMessage.setStr2(nodeLabel);
        Integer num = (Integer) map2.get(notifSection);
        logMessage.setInt1(num != null ? num.intValue() : -1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logBuildNodeSpec$lambda$4(LogMessage logMessage) {
        logMessage.getClass();
        return "  section " + logMessage.getStr1() + " has header " + logMessage.getStr2() + ", " + logMessage.getInt1() + " entries";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logBuildNodeSpec$lambda$5(NotifSection notifSection, LogMessage logMessage) {
        String name;
        NotifSectioner sectioner;
        logMessage.getClass();
        if (notifSection == null || (sectioner = notifSection.getSectioner()) == null || (name = sectioner.getName()) == null) {
            name = "(null)";
        }
        logMessage.setStr1(name);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logBuildNodeSpec$lambda$6(LogMessage logMessage) {
        logMessage.getClass();
        return "  section " + logMessage.getStr1() + " was removed since last run";
    }

    public final void logBuildNodeSpec(Set set, final Map map, final Map map2, final List list) {
        set.getClass();
        map.getClass();
        map2.getClass();
        list.getClass();
        if (Compile.IS_DEBUG && getDevLoggingEnabled()) {
            LogBuffer.log$default(this.buffer, "NodeSpecBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$1(list, (LogMessage) obj);
                }
            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$2((LogMessage) obj);
                }
            }, null, 16, null);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                final NotifSection notifSection = (NotifSection) it.next();
                LogBuffer.log$default(this.buffer, "NodeSpecBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$3(notifSection, map, map2, (LogMessage) obj);
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$4((LogMessage) obj);
                    }
                }, null, 16, null);
            }
            for (final NotifSection notifSection2 : SetsKt.minus(set, CollectionsKt.toSet(list))) {
                LogBuffer.log$default(this.buffer, "NodeSpecBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$5(notifSection2, (LogMessage) obj);
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return NodeSpecBuilderLogger.logBuildNodeSpec$lambda$6((LogMessage) obj);
                    }
                }, null, 16, null);
            }
        }
    }
}
