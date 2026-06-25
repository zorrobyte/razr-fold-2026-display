package androidx.activity;

import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.core.util.Consumer;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    private boolean backInvokedCallbackRegistered;
    private final Runnable fallbackOnBackPressed;
    private boolean hasEnabledCallbacks;
    private OnBackPressedCallback inProgressCallback;
    private OnBackInvokedDispatcher invokedDispatcher;
    private OnBackInvokedCallback onBackInvokedCallback;
    private final ArrayDeque onBackPressedCallbacks;
    private final Consumer onHasEnabledCallbacksChanged;

    /* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
    public final class Api33Impl {
        public static final Api33Impl INSTANCE = new Api33Impl();

        private Api33Impl() {
        }

        public final void registerOnBackInvokedCallback(Object obj, int i, Object obj2) {
            obj.getClass();
            obj2.getClass();
            ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(i, (OnBackInvokedCallback) obj2);
        }

        public final void unregisterOnBackInvokedCallback(Object obj, Object obj2) {
            obj.getClass();
            obj2.getClass();
            ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
        }
    }

    /* JADX INFO: compiled from: OnBackPressedDispatcher.kt */
    public final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }

        public final OnBackInvokedCallback createOnBackAnimationCallback(final Function1 function1, final Function1 function12, final Function0 function0, final Function0 function02) {
            function1.getClass();
            function12.getClass();
            function0.getClass();
            function02.getClass();
            return new OnBackAnimationCallback() { // from class: androidx.activity.OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1
                @Override // android.window.OnBackAnimationCallback
                public void onBackCancelled() {
                    function02.invoke();
                }

                @Override // android.window.OnBackInvokedCallback
                public void onBackInvoked() {
                    function0.invoke();
                }

                @Override // android.window.OnBackAnimationCallback
                public void onBackProgressed(BackEvent backEvent) {
                    backEvent.getClass();
                    function12.invoke(new BackEventCompat(backEvent));
                }

                @Override // android.window.OnBackAnimationCallback
                public void onBackStarted(BackEvent backEvent) {
                    backEvent.getClass();
                    function1.invoke(new BackEventCompat(backEvent));
                }
            };
        }
    }

    public OnBackPressedDispatcher(Runnable runnable) {
        this(runnable, null);
    }

    public OnBackPressedDispatcher(Runnable runnable, Consumer consumer) {
        this.fallbackOnBackPressed = runnable;
        this.onHasEnabledCallbacksChanged = consumer;
        this.onBackPressedCallbacks = new ArrayDeque();
        this.onBackInvokedCallback = Api34Impl.INSTANCE.createOnBackAnimationCallback(new Function1() { // from class: androidx.activity.OnBackPressedDispatcher.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((BackEventCompat) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(BackEventCompat backEventCompat) {
                backEventCompat.getClass();
                OnBackPressedDispatcher.this.onBackStarted(backEventCompat);
            }
        }, new Function1() { // from class: androidx.activity.OnBackPressedDispatcher.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((BackEventCompat) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(BackEventCompat backEventCompat) {
                backEventCompat.getClass();
                OnBackPressedDispatcher.this.onBackProgressed(backEventCompat);
            }
        }, new Function0() { // from class: androidx.activity.OnBackPressedDispatcher.3
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Object invoke() {
                m6invoke();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public final void m6invoke() {
                OnBackPressedDispatcher.this.onBackPressed();
            }
        }, new Function0() { // from class: androidx.activity.OnBackPressedDispatcher.4
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Object invoke() {
                m7invoke();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public final void m7invoke() {
                OnBackPressedDispatcher.this.onBackCancelled();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBackCancelled() {
        Object objPrevious;
        OnBackPressedCallback onBackPressedCallback = this.inProgressCallback;
        if (onBackPressedCallback == null) {
            ArrayDeque arrayDeque = this.onBackPressedCallbacks;
            ListIterator<E> listIterator = arrayDeque.listIterator(arrayDeque.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    objPrevious = null;
                    break;
                } else {
                    objPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) objPrevious).isEnabled()) {
                        break;
                    }
                }
            }
            onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        }
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackCancelled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBackProgressed(BackEventCompat backEventCompat) {
        Object objPrevious;
        OnBackPressedCallback onBackPressedCallback = this.inProgressCallback;
        if (onBackPressedCallback == null) {
            ArrayDeque arrayDeque = this.onBackPressedCallbacks;
            ListIterator<E> listIterator = arrayDeque.listIterator(arrayDeque.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    objPrevious = null;
                    break;
                } else {
                    objPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) objPrevious).isEnabled()) {
                        break;
                    }
                }
            }
            onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        }
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackProgressed(backEventCompat);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBackStarted(BackEventCompat backEventCompat) {
        Object objPrevious;
        ArrayDeque arrayDeque = this.onBackPressedCallbacks;
        ListIterator<E> listIterator = arrayDeque.listIterator(arrayDeque.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            } else {
                objPrevious = listIterator.previous();
                if (((OnBackPressedCallback) objPrevious).isEnabled()) {
                    break;
                }
            }
        }
        OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        if (this.inProgressCallback != null) {
            onBackCancelled();
        }
        this.inProgressCallback = onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackStarted(backEventCompat);
        }
    }

    private final void updateBackInvokedCallbackState(boolean z) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.invokedDispatcher;
        OnBackInvokedCallback onBackInvokedCallback = this.onBackInvokedCallback;
        if (onBackInvokedDispatcher == null || onBackInvokedCallback == null) {
            return;
        }
        if (z && !this.backInvokedCallbackRegistered) {
            Api33Impl.INSTANCE.registerOnBackInvokedCallback(onBackInvokedDispatcher, 0, onBackInvokedCallback);
            this.backInvokedCallbackRegistered = true;
        } else {
            if (z || !this.backInvokedCallbackRegistered) {
                return;
            }
            Api33Impl.INSTANCE.unregisterOnBackInvokedCallback(onBackInvokedDispatcher, onBackInvokedCallback);
            this.backInvokedCallbackRegistered = false;
        }
    }

    public final void onBackPressed() {
        Object objPrevious;
        OnBackPressedCallback onBackPressedCallback = this.inProgressCallback;
        if (onBackPressedCallback == null) {
            ArrayDeque arrayDeque = this.onBackPressedCallbacks;
            ListIterator<E> listIterator = arrayDeque.listIterator(arrayDeque.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    objPrevious = null;
                    break;
                } else {
                    objPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) objPrevious).isEnabled()) {
                        break;
                    }
                }
            }
            onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        }
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackPressed();
            return;
        }
        Runnable runnable = this.fallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void setOnBackInvokedDispatcher(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        onBackInvokedDispatcher.getClass();
        this.invokedDispatcher = onBackInvokedDispatcher;
        updateBackInvokedCallbackState(this.hasEnabledCallbacks);
    }
}
