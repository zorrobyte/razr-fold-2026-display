package androidx.lifecycle;

import android.app.Application;

/* JADX INFO: compiled from: AndroidViewModel.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidViewModel extends ViewModel {
    private final Application application;

    public AndroidViewModel(Application application) {
        application.getClass();
        this.application = application;
    }

    public Application getApplication() {
        Application application = this.application;
        application.getClass();
        return application;
    }
}
