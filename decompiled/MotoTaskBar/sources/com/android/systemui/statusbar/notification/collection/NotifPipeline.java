package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import java.util.Collection;
import java.util.List;

/* JADX INFO: compiled from: NotifPipeline.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifPipeline implements CommonNotifCollection {
    private final NotifCollection mNotifCollection;
    private final RenderStageManager mRenderStageManager;
    private final ShadeListBuilder mShadeListBuilder;

    public NotifPipeline(NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager) {
        notifCollection.getClass();
        shadeListBuilder.getClass();
        renderStageManager.getClass();
        this.mNotifCollection = notifCollection;
        this.mShadeListBuilder = shadeListBuilder;
        this.mRenderStageManager = renderStageManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection
    public void addCollectionListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.getClass();
        this.mNotifCollection.addCollectionListener(notifCollectionListener);
    }

    public final void addFinalizeFilter(NotifFilter notifFilter) {
        notifFilter.getClass();
        this.mShadeListBuilder.addFinalizeFilter(notifFilter);
    }

    public final void addNotificationLifetimeExtender(NotifLifetimeExtender notifLifetimeExtender) {
        notifLifetimeExtender.getClass();
        this.mNotifCollection.addNotificationLifetimeExtender(notifLifetimeExtender);
    }

    public final void addOnAfterRenderEntryListener(OnAfterRenderEntryListener onAfterRenderEntryListener) {
        onAfterRenderEntryListener.getClass();
        this.mRenderStageManager.addOnAfterRenderEntryListener(onAfterRenderEntryListener);
    }

    public final void addOnAfterRenderGroupListener(OnAfterRenderGroupListener onAfterRenderGroupListener) {
        onAfterRenderGroupListener.getClass();
        this.mRenderStageManager.addOnAfterRenderGroupListener(onAfterRenderGroupListener);
    }

    public final void addOnAfterRenderListListener(OnAfterRenderListListener onAfterRenderListListener) {
        onAfterRenderListListener.getClass();
        this.mRenderStageManager.addOnAfterRenderListListener(onAfterRenderListListener);
    }

    public final void addOnBeforeFinalizeFilterListener(OnBeforeFinalizeFilterListener onBeforeFinalizeFilterListener) {
        onBeforeFinalizeFilterListener.getClass();
        this.mShadeListBuilder.addOnBeforeFinalizeFilterListener(onBeforeFinalizeFilterListener);
    }

    public final void addOnBeforeRenderListListener(OnBeforeRenderListListener onBeforeRenderListListener) {
        onBeforeRenderListListener.getClass();
        this.mShadeListBuilder.addOnBeforeRenderListListener(onBeforeRenderListListener);
    }

    public final void addOnBeforeTransformGroupsListener(OnBeforeTransformGroupsListener onBeforeTransformGroupsListener) {
        onBeforeTransformGroupsListener.getClass();
        this.mShadeListBuilder.addOnBeforeTransformGroupsListener(onBeforeTransformGroupsListener);
    }

    public final void addPreGroupFilter(NotifFilter notifFilter) {
        notifFilter.getClass();
        this.mShadeListBuilder.addPreGroupFilter(notifFilter);
    }

    public final void addPreRenderInvalidator(Invalidator invalidator) {
        invalidator.getClass();
        this.mShadeListBuilder.addPreRenderInvalidator(invalidator);
    }

    public final void addPromoter(NotifPromoter notifPromoter) {
        notifPromoter.getClass();
        this.mShadeListBuilder.addPromoter(notifPromoter);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection
    public Collection getAllNotifs() {
        Collection allNotifs = this.mNotifCollection.getAllNotifs();
        allNotifs.getClass();
        return allNotifs;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection
    public NotificationEntry getEntry(String str) {
        str.getClass();
        return this.mNotifCollection.getEntry(str);
    }

    public final InternalNotifUpdater getInternalNotifUpdater(String str) {
        InternalNotifUpdater internalNotifUpdater = this.mNotifCollection.getInternalNotifUpdater(str);
        internalNotifUpdater.getClass();
        return internalNotifUpdater;
    }

    public final void setSections(List list) {
        list.getClass();
        this.mShadeListBuilder.setSectioners(list);
    }

    public final void setVisualStabilityManager(NotifStabilityManager notifStabilityManager) {
        notifStabilityManager.getClass();
        this.mShadeListBuilder.setNotifStabilityManager(notifStabilityManager);
    }
}
