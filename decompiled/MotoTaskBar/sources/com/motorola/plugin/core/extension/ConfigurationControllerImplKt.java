package com.motorola.plugin.core.extension;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ConfigurationControllerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ConfigurationControllerImplKt {
    private static final void filterForEach(Collection collection, Function1 function1, Function1 function12) {
        Iterator it = SequencesKt.filter(CollectionsKt.asSequence(collection), function1).iterator();
        while (it.hasNext()) {
            function12.invoke(it.next());
        }
    }
}
