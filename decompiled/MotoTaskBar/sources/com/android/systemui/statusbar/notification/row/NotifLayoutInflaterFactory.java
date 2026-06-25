package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotifLayoutInflaterFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifLayoutInflaterFactory implements LayoutInflater.Factory2 {
    private static final Companion Companion = new Companion(null);
    private static final boolean SPEW = Log.isLoggable("NotifLayoutInflaterFac", 2);
    private final int layoutType;
    private final NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer;
    private final ExpandableNotificationRow row;

    /* JADX INFO: compiled from: NotifLayoutInflaterFactory.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: NotifLayoutInflaterFactory.kt */
    public interface Provider {
        NotifLayoutInflaterFactory provide(ExpandableNotificationRow expandableNotificationRow, int i);
    }

    public NotifLayoutInflaterFactory(ExpandableNotificationRow expandableNotificationRow, int i, NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer) {
        expandableNotificationRow.getClass();
        notifRemoteViewsFactoryContainer.getClass();
        this.row = expandableNotificationRow;
        this.layoutType = i;
        this.notifRemoteViewsFactoryContainer = notifRemoteViewsFactoryContainer;
    }

    private final void logOnCreateView(String str, View view, NotifRemoteViewsFactory notifRemoteViewsFactory) {
        if (!SPEW || view == null || notifRemoteViewsFactory == null) {
            return;
        }
        Log.d("NotifLayoutInflaterFac", notifRemoteViewsFactory + " produced " + view + " for name:" + str + " with type:" + this.layoutType);
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        View view2 = null;
        NotifRemoteViewsFactory notifRemoteViewsFactory = null;
        for (NotifRemoteViewsFactory notifRemoteViewsFactory2 : this.notifRemoteViewsFactoryContainer.getFactories()) {
            View view3 = view;
            String str2 = str;
            Context context2 = context;
            AttributeSet attributeSet2 = attributeSet;
            View viewInstantiate = notifRemoteViewsFactory2.instantiate(this.row, this.layoutType, view3, str2, context2, attributeSet2);
            if (viewInstantiate != null) {
                if (notifRemoteViewsFactory != null) {
                    throw new IllegalStateException((notifRemoteViewsFactory2 + " tries to produce name:" + str2 + " with type:" + this.layoutType + ". However, " + notifRemoteViewsFactory + " produced view for " + str2 + " before.").toString());
                }
                view2 = viewInstantiate;
                notifRemoteViewsFactory = notifRemoteViewsFactory2;
            }
            view = view3;
            str = str2;
            context = context2;
            attributeSet = attributeSet2;
        }
        logOnCreateView(str, view2, notifRemoteViewsFactory);
        return view2;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        return onCreateView(null, str, context, attributeSet);
    }
}
