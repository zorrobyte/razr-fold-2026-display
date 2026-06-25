package androidx.activity;

import android.os.Handler;
import android.os.Looper;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: compiled from: ComponentActivity.kt */
/* JADX INFO: loaded from: classes.dex */
final class ComponentActivity$onBackPressedDispatcher$2 extends Lambda implements Function0 {
    final /* synthetic */ ComponentActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ComponentActivity$onBackPressedDispatcher$2(ComponentActivity componentActivity) {
        super(0);
        this.this$0 = componentActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ComponentActivity componentActivity) {
        try {
            super/*android.app.Activity*/.onBackPressed();
        } catch (IllegalStateException e) {
            if (!Intrinsics.areEqual(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                throw e;
            }
        } catch (NullPointerException e2) {
            if (!Intrinsics.areEqual(e2.getMessage(), "Attempt to invoke virtual method 'android.os.Handler android.app.FragmentHostCallback.getHandler()' on a null object reference")) {
                throw e2;
            }
        }
    }

    @Override // kotlin.jvm.functions.Function0
    /* JADX INFO: renamed from: invoke */
    public final OnBackPressedDispatcher mo2224invoke() {
        final ComponentActivity componentActivity = this.this$0;
        final OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: androidx.activity.ComponentActivity$onBackPressedDispatcher$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ComponentActivity$onBackPressedDispatcher$2.invoke$lambda$0(componentActivity);
            }
        });
        final ComponentActivity componentActivity2 = this.this$0;
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            componentActivity2.addObserverForBackInvoker(onBackPressedDispatcher);
            return onBackPressedDispatcher;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: androidx.activity.ComponentActivity$onBackPressedDispatcher$2$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentActivity.access$addObserverForBackInvoker(componentActivity2, onBackPressedDispatcher);
            }
        });
        return onBackPressedDispatcher;
    }
}
