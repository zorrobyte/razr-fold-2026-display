package androidx.compose.ui.text.platform;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: Dispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DispatcherKt {
    private static final CoroutineDispatcher FontCacheManagementDispatcher = Dispatchers.getMain();

    public static final CoroutineDispatcher getFontCacheManagementDispatcher() {
        return FontCacheManagementDispatcher;
    }
}
