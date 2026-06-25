package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: ThreadContextElement.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface CopyableThreadContextElement extends ThreadContextElement {
    CopyableThreadContextElement copyForChild();

    CoroutineContext mergeForChild(CoroutineContext.Element element);
}
