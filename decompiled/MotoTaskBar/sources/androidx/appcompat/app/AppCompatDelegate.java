package androidx.appcompat.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArraySet;
import androidx.core.os.LocaleListCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public abstract class AppCompatDelegate {
    static SerialExecutor sSerialExecutorForLocalesStorage = new SerialExecutor(new ThreadPerTaskExecutor());
    private static int sDefaultNightMode = -100;
    private static LocaleListCompat sRequestedAppLocales = null;
    private static LocaleListCompat sStoredAppLocales = null;
    private static Boolean sIsAutoStoreLocalesOptedIn = null;
    private static boolean sIsFrameworkSyncChecked = false;
    private static final ArraySet sActivityDelegates = new ArraySet();
    private static final Object sActivityDelegatesLock = new Object();
    private static final Object sAppLocalesStorageSyncLock = new Object();

    class SerialExecutor implements Executor {
        Runnable mActive;
        final Executor mExecutor;
        private final Object mLock = new Object();
        final Queue mTasks = new ArrayDeque();

        public static /* synthetic */ void $r8$lambda$TIOuFfuIY7It2pdRDpb_0gu8hwg(SerialExecutor serialExecutor, Runnable runnable) {
            serialExecutor.getClass();
            try {
                runnable.run();
            } finally {
                serialExecutor.scheduleNext();
            }
        }

        SerialExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        @Override // java.util.concurrent.Executor
        public void execute(final Runnable runnable) {
            synchronized (this.mLock) {
                try {
                    this.mTasks.add(new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegate$SerialExecutor$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppCompatDelegate.SerialExecutor.$r8$lambda$TIOuFfuIY7It2pdRDpb_0gu8hwg(this.f$0, runnable);
                        }
                    });
                    if (this.mActive == null) {
                        scheduleNext();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        protected void scheduleNext() {
            synchronized (this.mLock) {
                try {
                    Runnable runnable = (Runnable) this.mTasks.poll();
                    this.mActive = runnable;
                    if (runnable != null) {
                        this.mExecutor.execute(runnable);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    class ThreadPerTaskExecutor implements Executor {
        ThreadPerTaskExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    AppCompatDelegate() {
    }

    static void addActiveDelegate(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            removeDelegateFromActives(appCompatDelegate);
            sActivityDelegates.add(new WeakReference(appCompatDelegate));
        }
    }

    public static AppCompatDelegate create(Dialog dialog, AppCompatCallback appCompatCallback) {
        return new AppCompatDelegateImpl(dialog, appCompatCallback);
    }

    public static int getDefaultNightMode() {
        return sDefaultNightMode;
    }

    static void removeActivityDelegate(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            removeDelegateFromActives(appCompatDelegate);
        }
    }

    private static void removeDelegateFromActives(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            try {
                Iterator it = sActivityDelegates.iterator();
                while (it.hasNext()) {
                    AppCompatDelegate appCompatDelegate2 = (AppCompatDelegate) ((WeakReference) it.next()).get();
                    if (appCompatDelegate2 == appCompatDelegate || appCompatDelegate2 == null) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract View findViewById(int i);

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onCreate(Bundle bundle);

    public abstract void onDestroy();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public void setOnBackInvokedDispatcher(OnBackInvokedDispatcher onBackInvokedDispatcher) {
    }

    public abstract void setTheme(int i);

    public abstract void setTitle(CharSequence charSequence);
}
