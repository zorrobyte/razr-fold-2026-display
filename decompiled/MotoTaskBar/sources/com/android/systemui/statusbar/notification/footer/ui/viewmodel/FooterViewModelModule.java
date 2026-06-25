package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import java.util.Optional;
import javax.inject.Provider;

/* JADX INFO: compiled from: FooterViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FooterViewModelModule {
    public static final FooterViewModelModule INSTANCE = new FooterViewModelModule();

    private FooterViewModelModule() {
    }

    public final Optional provideOptional(Provider provider, Provider provider2) {
        provider.getClass();
        provider2.getClass();
        if (!Flags.notificationsFooterViewRefactor()) {
            Optional optionalEmpty = Optional.empty();
            optionalEmpty.getClass();
            return optionalEmpty;
        }
        Object obj = provider.get();
        obj.getClass();
        Object obj2 = provider2.get();
        obj2.getClass();
        Optional optionalOf = Optional.of(new FooterViewModel((ActiveNotificationsInteractor) obj, (SeenNotificationsInteractor) obj2));
        optionalOf.getClass();
        return optionalOf;
    }
}
