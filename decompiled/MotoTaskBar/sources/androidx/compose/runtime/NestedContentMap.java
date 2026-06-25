package androidx.compose.runtime;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectList;
import androidx.compose.runtime.collection.MultiValueMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Recomposer.kt */
/* JADX INFO: loaded from: classes.dex */
final class NestedContentMap {
    private final MutableScatterMap contentMap = MultiValueMap.m70constructorimpl$default(null, 1, null);
    private final MutableScatterMap containerMap = MultiValueMap.m70constructorimpl$default(null, 1, null);

    public final void clear() {
        MultiValueMap.m68clearimpl(this.contentMap);
        MultiValueMap.m68clearimpl(this.containerMap);
    }

    public final boolean contains(MovableContent movableContent) {
        return MultiValueMap.m71containsimpl(this.contentMap, movableContent);
    }

    public final NestedMovableContent removeLast(MovableContent movableContent) {
        NestedMovableContent nestedMovableContent = (NestedMovableContent) MultiValueMap.m77removeLastimpl(this.contentMap, movableContent);
        if (MultiValueMap.m74isEmptyimpl(this.contentMap)) {
            MultiValueMap.m68clearimpl(this.containerMap);
        }
        return nestedMovableContent;
    }

    public final void usedContainer(final MovableContentStateReference movableContentStateReference) {
        Object obj = this.containerMap.get(movableContentStateReference);
        if (obj != null) {
            if (!(obj instanceof MutableObjectList)) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                MultiValueMap.m78removeValueIfimpl(this.contentMap, null, new Function1() { // from class: androidx.compose.runtime.NestedContentMap$usedContainer$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(NestedMovableContent nestedMovableContent) {
                        return Boolean.valueOf(Intrinsics.areEqual(nestedMovableContent.getContainer(), movableContentStateReference));
                    }
                });
                return;
            }
            ObjectList objectList = (ObjectList) obj;
            Object[] objArr = objectList.content;
            int i = objectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                Object obj2 = objArr[i2];
                obj2.getClass();
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj2);
                MultiValueMap.m78removeValueIfimpl(this.contentMap, null, new Function1() { // from class: androidx.compose.runtime.NestedContentMap$usedContainer$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(NestedMovableContent nestedMovableContent) {
                        return Boolean.valueOf(Intrinsics.areEqual(nestedMovableContent.getContainer(), movableContentStateReference));
                    }
                });
            }
        }
    }
}
