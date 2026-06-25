package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import javax.inject.Provider;

/* JADX INFO: compiled from: NotificationSectionHeadersModule.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule {
    public static final NotificationSectionHeadersModule INSTANCE = new NotificationSectionHeadersModule();

    private NotificationSectionHeadersModule() {
    }

    public static final SectionHeaderController providesAlertingHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getHeaderController();
    }

    public static final NodeController providesAlertingHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getNodeController();
    }

    public static final SectionHeaderControllerSubcomponent providesAlertingHeaderSubcomponent(Provider provider) {
        provider.getClass();
        return ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("alerting header").headerText(R$string.notification_section_header_alerting).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
    }

    public static final SectionHeaderController providesIncomingHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getHeaderController();
    }

    public static final NodeController providesIncomingHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getNodeController();
    }

    public static final SectionHeaderControllerSubcomponent providesIncomingHeaderSubcomponent(Provider provider) {
        provider.getClass();
        return ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("incoming header").headerText(R$string.notification_section_header_incoming).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
    }

    public static final SectionHeaderController providesPeopleHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getHeaderController();
    }

    public static final NodeController providesPeopleHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getNodeController();
    }

    public static final SectionHeaderControllerSubcomponent providesPeopleHeaderSubcomponent(Provider provider) {
        provider.getClass();
        return ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("people header").headerText(R$string.notification_section_header_conversations).clickIntentAction("android.settings.CONVERSATION_SETTINGS").build();
    }

    public static final SectionHeaderController providesSilentHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getHeaderController();
    }

    public static final NodeController providesSilentHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        sectionHeaderControllerSubcomponent.getClass();
        return sectionHeaderControllerSubcomponent.getNodeController();
    }

    public static final SectionHeaderControllerSubcomponent providesSilentHeaderSubcomponent(Provider provider) {
        provider.getClass();
        return ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("silent header").headerText(R$string.notification_section_header_gentle).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
    }
}
