package androidx.lifecycle;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Transformations.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Transformations {
    public static final LiveData map(LiveData liveData, final Function1 function1) {
        liveData.getClass();
        function1.getClass();
        final MediatorLiveData mediatorLiveData = liveData.isInitialized() ? new MediatorLiveData(function1.invoke(liveData.getValue())) : new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Transformations$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: androidx.lifecycle.Transformations.map.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                m1092invoke(obj);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public final void m1092invoke(Object obj) {
                mediatorLiveData.setValue(function1.invoke(obj));
            }
        }));
        return mediatorLiveData;
    }
}
