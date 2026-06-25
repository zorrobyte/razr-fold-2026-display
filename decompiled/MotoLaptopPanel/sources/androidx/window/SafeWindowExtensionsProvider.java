package androidx.window;

import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Method;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SafeWindowExtensionsProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SafeWindowExtensionsProvider {
    private final ClassLoader loader;

    public SafeWindowExtensionsProvider(ClassLoader classLoader) {
        classLoader.getClass();
        this.loader = classLoader;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getWindowExtensionsProviderClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    private final boolean isWindowExtensionsPresent() {
        return ReflectionUtils.INSTANCE.checkIsPresent$window_release(new Function0() { // from class: androidx.window.SafeWindowExtensionsProvider.isWindowExtensionsPresent.1
            @Override // kotlin.jvm.functions.Function0
            public final Class invoke() throws ClassNotFoundException {
                Class<?> clsLoadClass = SafeWindowExtensionsProvider.this.loader.loadClass("androidx.window.extensions.WindowExtensionsProvider");
                clsLoadClass.getClass();
                return clsLoadClass;
            }
        });
    }

    public final Class getWindowExtensionsClass$window_release() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.WindowExtensions");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    public final boolean isWindowExtensionsValid$window_release() {
        return isWindowExtensionsPresent() && ReflectionUtils.validateReflection$window_release("WindowExtensionsProvider#getWindowExtensions is not valid", new Function0() { // from class: androidx.window.SafeWindowExtensionsProvider$isWindowExtensionsValid$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method declaredMethod = this.this$0.getWindowExtensionsProviderClass().getDeclaredMethod("getWindowExtensions", null);
                Class windowExtensionsClass$window_release = this.this$0.getWindowExtensionsClass$window_release();
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                declaredMethod.getClass();
                if (reflectionUtils.doesReturn$window_release(declaredMethod, windowExtensionsClass$window_release) && reflectionUtils.isPublic$window_release(declaredMethod)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }
}
