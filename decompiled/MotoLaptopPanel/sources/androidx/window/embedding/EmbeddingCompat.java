package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.ConsumerAdapter;
import androidx.window.embedding.EmbeddingCompat;
import androidx.window.embedding.EmbeddingInterfaceCompat;
import androidx.window.extensions.WindowExtensions;
import androidx.window.extensions.WindowExtensionsProvider;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.reflection.Consumer2;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: EmbeddingCompat.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmbeddingCompat implements EmbeddingInterfaceCompat {
    public static final Companion Companion = new Companion(null);
    private final ActivityWindowInfoCallbackController activityWindowInfoCallbackController;
    private final EmbeddingAdapter adapter;
    private final Context applicationContext;
    private final ConsumerAdapter consumerAdapter;
    private final ActivityEmbeddingComponent embeddingExtension;
    private final OverlayControllerImpl overlayController;
    private final WindowSdkExtensions windowSdkExtensions;

    /* JADX INFO: compiled from: EmbeddingCompat.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final ActivityEmbeddingComponent emptyActivityEmbeddingProxy() {
            Object objNewProxyInstance = Proxy.newProxyInstance(EmbeddingCompat.class.getClassLoader(), new Class[]{ActivityEmbeddingComponent.class}, new InvocationHandler() { // from class: androidx.window.embedding.EmbeddingCompat$Companion$$ExternalSyntheticLambda0
                @Override // java.lang.reflect.InvocationHandler
                public final Object invoke(Object obj, Method method, Object[] objArr) {
                    return EmbeddingCompat.Companion.emptyActivityEmbeddingProxy$lambda$2(obj, method, objArr);
                }
            });
            objNewProxyInstance.getClass();
            return (ActivityEmbeddingComponent) objNewProxyInstance;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit emptyActivityEmbeddingProxy$lambda$2(Object obj, Method method, Object[] objArr) {
            return Unit.INSTANCE;
        }

        public final ActivityEmbeddingComponent embeddingComponent() {
            if (!isEmbeddingAvailable()) {
                return emptyActivityEmbeddingProxy();
            }
            ClassLoader classLoader = EmbeddingCompat.class.getClassLoader();
            if (classLoader != null) {
                ConsumerAdapter consumerAdapter = new ConsumerAdapter(classLoader);
                WindowExtensions windowExtensions = WindowExtensionsProvider.getWindowExtensions();
                windowExtensions.getClass();
                ActivityEmbeddingComponent activityEmbeddingComponent = new SafeActivityEmbeddingComponentProvider(classLoader, consumerAdapter, windowExtensions).getActivityEmbeddingComponent();
                if (activityEmbeddingComponent != null) {
                    return activityEmbeddingComponent;
                }
            }
            return emptyActivityEmbeddingProxy();
        }

        public final boolean isEmbeddingAvailable() {
            try {
                ClassLoader classLoader = EmbeddingCompat.class.getClassLoader();
                if (classLoader != null) {
                    ConsumerAdapter consumerAdapter = new ConsumerAdapter(classLoader);
                    WindowExtensions windowExtensions = WindowExtensionsProvider.getWindowExtensions();
                    windowExtensions.getClass();
                    if (new SafeActivityEmbeddingComponentProvider(classLoader, consumerAdapter, windowExtensions).getActivityEmbeddingComponent() != null) {
                        return true;
                    }
                }
                return false;
            } catch (NoClassDefFoundError unused) {
                Log.d("EmbeddingCompat", "Embedding extension version not found");
                return false;
            } catch (UnsupportedOperationException unused2) {
                Log.d("EmbeddingCompat", "Stub Extension");
                return false;
            }
        }
    }

    public EmbeddingCompat(ActivityEmbeddingComponent activityEmbeddingComponent, EmbeddingAdapter embeddingAdapter, ConsumerAdapter consumerAdapter, Context context, OverlayControllerImpl overlayControllerImpl, ActivityWindowInfoCallbackController activityWindowInfoCallbackController) {
        activityEmbeddingComponent.getClass();
        embeddingAdapter.getClass();
        consumerAdapter.getClass();
        context.getClass();
        this.embeddingExtension = activityEmbeddingComponent;
        this.adapter = embeddingAdapter;
        this.consumerAdapter = consumerAdapter;
        this.applicationContext = context;
        this.overlayController = overlayControllerImpl;
        this.activityWindowInfoCallbackController = activityWindowInfoCallbackController;
        this.windowSdkExtensions = WindowSdkExtensions.Companion.getInstance();
    }

    private final void registerSplitInfoCallback(final EmbeddingInterfaceCompat.EmbeddingCallbackInterface embeddingCallbackInterface) {
        this.embeddingExtension.setSplitInfoCallback(new Consumer2() { // from class: androidx.window.embedding.EmbeddingCompat$$ExternalSyntheticLambda1
            @Override // androidx.window.reflection.Consumer2
            public final void accept(Object obj) {
                EmbeddingCompat.registerSplitInfoCallback$lambda$1(embeddingCallbackInterface, this, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerSplitInfoCallback$lambda$1(EmbeddingInterfaceCompat.EmbeddingCallbackInterface embeddingCallbackInterface, EmbeddingCompat embeddingCompat, List list) {
        list.getClass();
        embeddingCallbackInterface.onSplitInfoChanged(embeddingCompat.adapter.translate(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setEmbeddingCallback$lambda$0(EmbeddingInterfaceCompat.EmbeddingCallbackInterface embeddingCallbackInterface, EmbeddingCompat embeddingCompat, List list) {
        list.getClass();
        embeddingCallbackInterface.onActivityStackChanged(embeddingCompat.adapter.translate$window_release(list));
    }

    @Override // androidx.window.embedding.EmbeddingInterfaceCompat
    public boolean isActivityEmbedded(Activity activity) {
        activity.getClass();
        return this.embeddingExtension.isActivityEmbedded(activity);
    }

    @Override // androidx.window.embedding.EmbeddingInterfaceCompat
    public void setEmbeddingCallback(final EmbeddingInterfaceCompat.EmbeddingCallbackInterface embeddingCallbackInterface) throws IllegalAccessException, InvocationTargetException {
        embeddingCallbackInterface.getClass();
        int extensionVersion = this.windowSdkExtensions.getExtensionVersion();
        if (extensionVersion == 1) {
            this.consumerAdapter.addConsumer(this.embeddingExtension, Reflection.getOrCreateKotlinClass(List.class), "setSplitInfoCallback", new Function1() { // from class: androidx.window.embedding.EmbeddingCompat.setEmbeddingCallback.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((List) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(List list) {
                    list.getClass();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : list) {
                        if (obj instanceof androidx.window.extensions.embedding.SplitInfo) {
                            arrayList.add(obj);
                        }
                    }
                    embeddingCallbackInterface.onSplitInfoChanged(this.adapter.translate(arrayList));
                }
            });
            return;
        }
        if (2 <= extensionVersion && extensionVersion < 5) {
            registerSplitInfoCallback(embeddingCallbackInterface);
        } else {
            if (5 > extensionVersion || extensionVersion > Integer.MAX_VALUE) {
                return;
            }
            registerSplitInfoCallback(embeddingCallbackInterface);
            this.embeddingExtension.registerActivityStackCallback(new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new Consumer2() { // from class: androidx.window.embedding.EmbeddingCompat$$ExternalSyntheticLambda0
                @Override // androidx.window.reflection.Consumer2
                public final void accept(Object obj) {
                    EmbeddingCompat.setEmbeddingCallback$lambda$0(embeddingCallbackInterface, this, (List) obj);
                }
            });
        }
    }
}
