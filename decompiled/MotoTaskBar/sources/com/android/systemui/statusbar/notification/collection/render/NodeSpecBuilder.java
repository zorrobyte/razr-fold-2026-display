package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.util.Compile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NodeSpecBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NodeSpecBuilder {
    private Set lastSections;
    private final NodeSpecBuilderLogger logger;
    private final MediaContainerController mediaContainerController;
    private final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    private final NotificationSectionsFeatureManager sectionsFeatureManager;
    private final NotifViewBarn viewBarn;

    public NodeSpecBuilder(MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NotifViewBarn notifViewBarn, NodeSpecBuilderLogger nodeSpecBuilderLogger) {
        mediaContainerController.getClass();
        notificationSectionsFeatureManager.getClass();
        sectionHeaderVisibilityProvider.getClass();
        notifViewBarn.getClass();
        nodeSpecBuilderLogger.getClass();
        this.mediaContainerController = mediaContainerController;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.viewBarn = notifViewBarn;
        this.logger = nodeSpecBuilderLogger;
        this.lastSections = SetsKt.emptySet();
    }

    private final NodeSpec buildNotifNode(NodeSpec nodeSpec, ListEntry listEntry) {
        if (listEntry instanceof NotificationEntry) {
            return new NodeSpecImpl(nodeSpec, this.viewBarn.requireNodeController(listEntry));
        }
        if (!(listEntry instanceof GroupEntry)) {
            throw new RuntimeException("Unexpected entry: " + listEntry);
        }
        NotifViewBarn notifViewBarn = this.viewBarn;
        GroupEntry groupEntry = (GroupEntry) listEntry;
        NotificationEntry summary = groupEntry.getSummary();
        if (summary == null) {
            throw new IllegalStateException("Required value was null.");
        }
        NodeSpecImpl nodeSpecImpl = new NodeSpecImpl(nodeSpec, notifViewBarn.requireNodeController(summary));
        List<NotificationEntry> children = groupEntry.getChildren();
        children.getClass();
        for (NotificationEntry notificationEntry : children) {
            List children2 = nodeSpecImpl.getChildren();
            notificationEntry.getClass();
            children2.add(buildNotifNode(nodeSpecImpl, notificationEntry));
        }
        return nodeSpecImpl;
    }

    public final NodeSpec buildNodeSpec(NodeController nodeController, List list) {
        NodeController headerController;
        nodeController.getClass();
        list.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NodeSpecBuilder.buildNodeSpec");
        }
        try {
            NodeSpecImpl nodeSpecImpl = new NodeSpecImpl(null, nodeController);
            if (this.sectionsFeatureManager.isMediaControlsEnabled()) {
                nodeSpecImpl.getChildren().add(new NodeSpecImpl(nodeSpecImpl, this.mediaContainerController));
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            boolean sectionHeadersVisible = this.sectionHeaderVisibilityProvider.getSectionHeadersVisible();
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            Iterator it = list.iterator();
            NotifSection notifSection = null;
            while (it.hasNext()) {
                ListEntry listEntry = (ListEntry) it.next();
                NotifSection section = listEntry.getSection();
                section.getClass();
                if (linkedHashSet.contains(section)) {
                    throw new RuntimeException("Section " + section.getLabel() + " has been duplicated");
                }
                if (!Intrinsics.areEqual(section, notifSection)) {
                    if (!Intrinsics.areEqual(section.getHeaderController(), notifSection != null ? notifSection.getHeaderController() : null) && sectionHeadersVisible && (headerController = section.getHeaderController()) != null) {
                        nodeSpecImpl.getChildren().add(new NodeSpecImpl(nodeSpecImpl, headerController));
                        if (Compile.IS_DEBUG) {
                            linkedHashMap.put(section, headerController);
                        }
                    }
                    linkedHashSet.add(notifSection);
                    if (Compile.IS_DEBUG) {
                        arrayList.add(section);
                    }
                    notifSection = section;
                }
                nodeSpecImpl.getChildren().add(buildNotifNode(nodeSpecImpl, listEntry));
                if (Compile.IS_DEBUG) {
                    linkedHashMap2.put(section, Integer.valueOf(((Number) linkedHashMap2.getOrDefault(section, 0)).intValue() + 1));
                }
            }
            if (Compile.IS_DEBUG) {
                this.logger.logBuildNodeSpec(this.lastSections, linkedHashMap, linkedHashMap2, arrayList);
                this.lastSections = linkedHashMap2.keySet();
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return nodeSpecImpl;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
