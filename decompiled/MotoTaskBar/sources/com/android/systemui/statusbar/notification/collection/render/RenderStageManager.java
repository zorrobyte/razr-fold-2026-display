package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: RenderStageManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RenderStageManager {
    private NotifViewRenderer viewRenderer;
    private final List onAfterRenderListListeners = new ArrayList();
    private final List onAfterRenderGroupListeners = new ArrayList();
    private final List onAfterRenderEntryListeners = new ArrayList();

    private final void dispatchOnAfterRenderEntries(NotifViewRenderer notifViewRenderer, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderEntries");
        }
        try {
            if (this.onAfterRenderEntryListeners.isEmpty()) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ListEntry listEntry = (ListEntry) it.next();
                if (listEntry instanceof NotificationEntry) {
                    NotificationEntry notificationEntry = (NotificationEntry) listEntry;
                    NotifRowController rowController = notifViewRenderer.getRowController(notificationEntry);
                    Iterator it2 = this.onAfterRenderEntryListeners.iterator();
                    while (it2.hasNext()) {
                        ((OnAfterRenderEntryListener) it2.next()).onAfterRenderEntry(notificationEntry, rowController);
                    }
                } else {
                    if (!(listEntry instanceof GroupEntry)) {
                        throw new IllegalStateException(("Unhandled entry: " + listEntry).toString());
                    }
                    GroupEntry groupEntry = (GroupEntry) listEntry;
                    NotificationEntry summary = groupEntry.getSummary();
                    if (summary == null) {
                        throw new IllegalStateException(("No Summary: " + groupEntry).toString());
                    }
                    NotifRowController rowController2 = notifViewRenderer.getRowController(summary);
                    Iterator it3 = this.onAfterRenderEntryListeners.iterator();
                    while (it3.hasNext()) {
                        ((OnAfterRenderEntryListener) it3.next()).onAfterRenderEntry(summary, rowController2);
                    }
                    List<NotificationEntry> children = ((GroupEntry) listEntry).getChildren();
                    children.getClass();
                    for (NotificationEntry notificationEntry2 : children) {
                        NotifRowController rowController3 = notifViewRenderer.getRowController(notificationEntry2);
                        Iterator it4 = this.onAfterRenderEntryListeners.iterator();
                        while (it4.hasNext()) {
                            ((OnAfterRenderEntryListener) it4.next()).onAfterRenderEntry(notificationEntry2, rowController3);
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    private final void dispatchOnAfterRenderGroups(NotifViewRenderer notifViewRenderer, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderGroups");
        }
        try {
            if (this.onAfterRenderGroupListeners.isEmpty()) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            Sequence<GroupEntry> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.RenderStageManager$dispatchOnAfterRenderGroups$lambda$6$$inlined$filterIsInstance$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof GroupEntry);
                }
            });
            sequenceFilter.getClass();
            for (GroupEntry groupEntry : sequenceFilter) {
                NotifGroupController groupController = notifViewRenderer.getGroupController(groupEntry);
                Iterator it = this.onAfterRenderGroupListeners.iterator();
                while (it.hasNext()) {
                    ((OnAfterRenderGroupListener) it.next()).onAfterRenderGroup(groupEntry, groupController);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    private final void dispatchOnAfterRenderList(NotifViewRenderer notifViewRenderer, List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderList");
        }
        try {
            NotifStackController stackController = notifViewRenderer.getStackController();
            Iterator it = this.onAfterRenderListListeners.iterator();
            while (it.hasNext()) {
                ((OnAfterRenderListListener) it.next()).onAfterRenderList(list, stackController);
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRenderList(List list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderStageManager.onRenderList");
        }
        try {
            NotifViewRenderer notifViewRenderer = this.viewRenderer;
            if (notifViewRenderer == null) {
                if (zIsEnabled) {
                    return;
                } else {
                    return;
                }
            }
            notifViewRenderer.onRenderList(list);
            dispatchOnAfterRenderList(notifViewRenderer, list);
            dispatchOnAfterRenderGroups(notifViewRenderer, list);
            dispatchOnAfterRenderEntries(notifViewRenderer, list);
            notifViewRenderer.onDispatchComplete();
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final void addOnAfterRenderEntryListener(OnAfterRenderEntryListener onAfterRenderEntryListener) {
        onAfterRenderEntryListener.getClass();
        this.onAfterRenderEntryListeners.add(onAfterRenderEntryListener);
    }

    public final void addOnAfterRenderGroupListener(OnAfterRenderGroupListener onAfterRenderGroupListener) {
        onAfterRenderGroupListener.getClass();
        this.onAfterRenderGroupListeners.add(onAfterRenderGroupListener);
    }

    public final void addOnAfterRenderListListener(OnAfterRenderListListener onAfterRenderListListener) {
        onAfterRenderListListener.getClass();
        this.onAfterRenderListListeners.add(onAfterRenderListListener);
    }

    public final void attach(ShadeListBuilder shadeListBuilder) {
        shadeListBuilder.getClass();
        shadeListBuilder.setOnRenderListListener(new ShadeListBuilder.OnRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.render.RenderStageManager.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.ShadeListBuilder.OnRenderListListener
            public final void onRenderList(List list) {
                list.getClass();
                RenderStageManager.this.onRenderList(list);
            }
        });
    }

    public final void setViewRenderer(NotifViewRenderer notifViewRenderer) {
        notifViewRenderer.getClass();
        this.viewRenderer = notifViewRenderer;
    }
}
