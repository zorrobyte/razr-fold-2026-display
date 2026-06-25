package com.android.systemui.util;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.NamedListenerSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NamedListenerSet.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NamedListenerSet implements Set, KMappedMarker {
    private final Function1 getName;
    private final CopyOnWriteArrayList listeners;

    /* JADX INFO: compiled from: NamedListenerSet.kt */
    public final class NamedListener {
        private final Object listener;
        private final String name;
        final /* synthetic */ NamedListenerSet this$0;

        public NamedListener(NamedListenerSet namedListenerSet, Object obj) {
            obj.getClass();
            this.this$0 = namedListenerSet;
            this.listener = obj;
            this.name = (String) namedListenerSet.getName.invoke(obj);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            return (obj instanceof NamedListener) && Intrinsics.areEqual(this.listener, ((NamedListener) obj).listener);
        }

        public final Object getListener() {
            return this.listener;
        }

        public final String getName() {
            return this.name;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.util.NamedListenerSet$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: NamedListenerSet.kt */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = NamedListenerSet.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Iterator it;
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                it = NamedListenerSet.this.listeners.iterator();
                it.getClass();
                sequenceScope = sequenceScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (it.hasNext()) {
                Object listener = ((NamedListener) it.next()).getListener();
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 1;
                if (sequenceScope.yield(listener, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public NamedListenerSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public NamedListenerSet(Function1 function1) {
        function1.getClass();
        this.getName = function1;
        this.listeners = new CopyOnWriteArrayList();
    }

    public /* synthetic */ NamedListenerSet(Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1() { // from class: com.android.systemui.util.NamedListenerSet$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NamedListenerSet._init_$lambda$0(obj);
            }
        } : function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _init_$lambda$0(Object obj) {
        obj.getClass();
        return obj.getClass().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean remove$lambda$3(Object obj, NamedListener namedListener) {
        return Intrinsics.areEqual(namedListener.getListener(), obj);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addIfAbsent(Object obj) {
        obj.getClass();
        return this.listeners.addIfAbsent(new NamedListener(this, obj));
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        Object next;
        if (obj == null) {
            return false;
        }
        Iterator it = this.listeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((NamedListener) next).getListener(), obj)) {
                break;
            }
        }
        return next != null;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        int i;
        collection.getClass();
        CopyOnWriteArrayList copyOnWriteArrayList = this.listeners;
        if (copyOnWriteArrayList == null || !copyOnWriteArrayList.isEmpty()) {
            Iterator it = copyOnWriteArrayList.iterator();
            i = 0;
            while (it.hasNext()) {
                if (collection.contains(((NamedListener) it.next()).getListener()) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        } else {
            i = 0;
        }
        return i == collection.size();
    }

    public final void forEachTraced(Consumer consumer) {
        consumer.getClass();
        Iterator itNamedIterator = namedIterator();
        while (itNamedIterator.hasNext()) {
            NamedListener namedListener = (NamedListener) itNamedIterator.next();
            String name = namedListener.getName();
            Object listener = namedListener.getListener();
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice(name);
            }
            try {
                consumer.accept(listener);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    public int getSize() {
        return this.listeners.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.listeners.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return SequencesKt.iterator(new AnonymousClass1(null));
    }

    public final Iterator namedIterator() {
        Iterator it = this.listeners.iterator();
        it.getClass();
        return it;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(final Object obj) {
        if (obj == null) {
            return false;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = this.listeners;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.util.NamedListenerSet$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return Boolean.valueOf(NamedListenerSet.remove$lambda$3(obj, (NamedListenerSet.NamedListener) obj2));
            }
        };
        return copyOnWriteArrayList.removeIf(new Predicate(function1) { // from class: com.android.systemui.util.NamedListenerSet$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function1.getClass();
                this.function = function1;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj2) {
                return ((Boolean) this.function.invoke(obj2)).booleanValue();
            }
        });
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        objArr.getClass();
        return CollectionToArray.toArray(this, objArr);
    }
}
