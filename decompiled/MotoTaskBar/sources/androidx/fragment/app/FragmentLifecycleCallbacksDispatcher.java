package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;

/* JADX INFO: compiled from: FragmentLifecycleCallbacksDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FragmentLifecycleCallbacksDispatcher {
    private final FragmentManager fragmentManager;
    private final CopyOnWriteArrayList lifecycleCallbacks;

    /* JADX INFO: compiled from: FragmentLifecycleCallbacksDispatcher.kt */
    final class FragmentLifecycleCallbacksHolder {
        private final FragmentManager.FragmentLifecycleCallbacks callback;
        private final boolean recursive;

        public FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
            fragmentLifecycleCallbacks.getClass();
            this.callback = fragmentLifecycleCallbacks;
            this.recursive = z;
        }

        public final FragmentManager.FragmentLifecycleCallbacks getCallback() {
            return this.callback;
        }

        public final boolean getRecursive() {
            return this.recursive;
        }
    }

    public FragmentLifecycleCallbacksDispatcher(FragmentManager fragmentManager) {
        fragmentManager.getClass();
        this.fragmentManager = fragmentManager;
        this.lifecycleCallbacks = new CopyOnWriteArrayList();
    }

    public final void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentActivityCreated(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentActivityCreated(this.fragmentManager, fragment, bundle);
            }
        }
    }

    public final void dispatchOnFragmentAttached(Fragment fragment, boolean z) {
        fragment.getClass();
        Context context = this.fragmentManager.getHost().getContext();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentAttached(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentAttached(this.fragmentManager, fragment, context);
            }
        }
    }

    public final void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentCreated(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentCreated(this.fragmentManager, fragment, bundle);
            }
        }
    }

    public final void dispatchOnFragmentDestroyed(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentDestroyed(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentDestroyed(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentDetached(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentDetached(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentDetached(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentPaused(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentPaused(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentPaused(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentPreAttached(Fragment fragment, boolean z) {
        fragment.getClass();
        Context context = this.fragmentManager.getHost().getContext();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentPreAttached(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentPreAttached(this.fragmentManager, fragment, context);
            }
        }
    }

    public final void dispatchOnFragmentPreCreated(Fragment fragment, Bundle bundle, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentPreCreated(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentPreCreated(this.fragmentManager, fragment, bundle);
            }
        }
    }

    public final void dispatchOnFragmentResumed(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentResumed(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentResumed(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean z) {
        fragment.getClass();
        bundle.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentSaveInstanceState(this.fragmentManager, fragment, bundle);
            }
        }
    }

    public final void dispatchOnFragmentStarted(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentStarted(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentStarted(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentStopped(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentStopped(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentStopped(this.fragmentManager, fragment);
            }
        }
    }

    public final void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean z) {
        fragment.getClass();
        view.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentViewCreated(fragment, view, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentViewCreated(this.fragmentManager, fragment, view, bundle);
            }
        }
    }

    public final void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean z) {
        fragment.getClass();
        Fragment parent = this.fragmentManager.getParent();
        if (parent != null) {
            FragmentManager parentFragmentManager = parent.getParentFragmentManager();
            parentFragmentManager.getClass();
            parentFragmentManager.getLifecycleCallbacksDispatcher().dispatchOnFragmentViewDestroyed(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.lifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.getRecursive()) {
                fragmentLifecycleCallbacksHolder.getCallback().onFragmentViewDestroyed(this.fragmentManager, fragment);
            }
        }
    }

    public final void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        fragmentLifecycleCallbacks.getClass();
        this.lifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(fragmentLifecycleCallbacks, z));
    }

    public final void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        fragmentLifecycleCallbacks.getClass();
        synchronized (this.lifecycleCallbacks) {
            try {
                int size = this.lifecycleCallbacks.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    if (((FragmentLifecycleCallbacksHolder) this.lifecycleCallbacks.get(i)).getCallback() == fragmentLifecycleCallbacks) {
                        this.lifecycleCallbacks.remove(i);
                        break;
                    }
                    i++;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
