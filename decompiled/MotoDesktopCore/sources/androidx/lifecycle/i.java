package androidx.lifecycle;

import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public final class i extends Fragment {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f1715a = 0;

    public final void a(a aVar) {
        ComponentCallbacks2 activity = getActivity();
        if (activity instanceof d) {
            c lifecycle = ((d) activity).getLifecycle();
            if (lifecycle instanceof f) {
                ((f) lifecycle).a(aVar);
            }
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        a(a.ON_CREATE);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        a(a.ON_DESTROY);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        a(a.ON_PAUSE);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        a(a.ON_RESUME);
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        a(a.ON_START);
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        a(a.ON_STOP);
    }
}
