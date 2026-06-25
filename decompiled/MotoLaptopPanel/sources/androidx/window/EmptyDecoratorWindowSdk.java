package androidx.window;

/* JADX INFO: compiled from: WindowSdkExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
final class EmptyDecoratorWindowSdk implements WindowSdkExtensionsDecorator {
    public static final EmptyDecoratorWindowSdk INSTANCE = new EmptyDecoratorWindowSdk();

    private EmptyDecoratorWindowSdk() {
    }

    @Override // androidx.window.WindowSdkExtensionsDecorator
    public WindowSdkExtensions decorate(WindowSdkExtensions windowSdkExtensions) {
        windowSdkExtensions.getClass();
        return windowSdkExtensions;
    }
}
