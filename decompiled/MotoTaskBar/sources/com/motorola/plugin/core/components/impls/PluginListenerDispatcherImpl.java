package com.motorola.plugin.core.components.impls;

import android.content.Context;
import android.os.UserHandle;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.components.PluginListenerDispatcher;
import com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.sdk.Plugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginListenerDispatcherImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginListenerDispatcherImpl implements PluginListenerDispatcher, PluginListener, ISnapshotAware, Disposable {
    private final Set mPluginListenerList = new LinkedHashSet();

    /* JADX INFO: compiled from: PluginListenerDispatcherImpl.kt */
    final class ListenerWithClassType implements PluginListener {
        private final Class expectClass;
        private final PluginListener listener;

        public ListenerWithClassType(Class cls, PluginListener pluginListener) {
            cls.getClass();
            pluginListener.getClass();
            this.expectClass = cls;
            this.listener = pluginListener;
        }

        public static /* synthetic */ ListenerWithClassType copy$default(ListenerWithClassType listenerWithClassType, Class cls, PluginListener pluginListener, int i, Object obj) {
            if ((i & 1) != 0) {
                cls = listenerWithClassType.expectClass;
            }
            if ((i & 2) != 0) {
                pluginListener = listenerWithClassType.listener;
            }
            return listenerWithClassType.copy(cls, pluginListener);
        }

        public final Class component1() {
            return this.expectClass;
        }

        public final PluginListener component2() {
            return this.listener;
        }

        public final ListenerWithClassType copy(Class cls, PluginListener pluginListener) {
            cls.getClass();
            pluginListener.getClass();
            return new ListenerWithClassType(cls, pluginListener);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!Intrinsics.areEqual(ListenerWithClassType.class, obj == null ? null : obj.getClass())) {
                return false;
            }
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl.ListenerWithClassType<*>");
            }
            ListenerWithClassType listenerWithClassType = (ListenerWithClassType) obj;
            return Intrinsics.areEqual(this.expectClass, listenerWithClassType.expectClass) && Intrinsics.areEqual(this.listener, listenerWithClassType.listener);
        }

        public final Class getExpectClass() {
            return this.expectClass;
        }

        public final PluginListener getListener() {
            return this.listener;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        @Override // com.motorola.plugin.core.PluginListener
        public void onPluginConnected(String str, String str2, Plugin plugin, Context context) {
            str.getClass();
            str2.getClass();
            plugin.getClass();
            context.getClass();
            if (this.expectClass.isInstance(plugin)) {
                this.listener.onPluginConnected(str, str2, plugin, context);
            }
        }

        @Override // com.motorola.plugin.core.PluginListener
        public void onPluginDisconnected(String str, String str2, Plugin plugin) {
            str.getClass();
            str2.getClass();
            plugin.getClass();
            if (this.expectClass.isInstance(plugin)) {
                this.listener.onPluginDisconnected(str, str2, plugin);
            }
        }

        @Override // com.motorola.plugin.core.PluginListener
        public void onPluginFailedToLoad(String str, String str2) {
            str.getClass();
            str2.getClass();
            this.listener.onPluginFailedToLoad(str, str2);
        }

        @Override // com.motorola.plugin.core.PluginListener
        public void onPluginPackageChanged(String str, UserHandle userHandle, MarkFlag markFlag) {
            str.getClass();
            userHandle.getClass();
            markFlag.getClass();
            this.listener.onPluginPackageChanged(str, userHandle, markFlag);
        }

        public String toString() {
            return "ListenerWithClassType(expectClass=" + this.expectClass + ", listener=" + this.listener + ')';
        }
    }

    /* JADX INFO: compiled from: PluginListenerDispatcherImpl.kt */
    final class PluginListenerDispatcherSnapshot extends AbstractSnapshot {
        private int myPluginListenerDispatcherInstance;
        private Map myPluginListenerSnapshots;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginListenerDispatcherSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.myPluginListenerSnapshots = new LinkedHashMap();
        }

        public final int getMyPluginListenerDispatcherInstance() {
            return this.myPluginListenerDispatcherInstance;
        }

        public final Map getMyPluginListenerSnapshots() {
            return this.myPluginListenerSnapshots;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginListenerDispatcher", getMyPluginListenerDispatcherInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printSingle("Listener map:");
            iPrinter.printPair("size", Integer.valueOf(getMyPluginListenerSnapshots().size()));
            iPrinter.newLine();
            for (Map.Entry entry : getMyPluginListenerSnapshots().entrySet()) {
                iPrinter.increaseIndent();
                iPrinter.printPair("class", entry.getKey());
                int i = 0;
                if (((List) entry.getValue()).size() != 1) {
                    iPrinter.newLine();
                    Iterator it = ((Iterable) entry.getValue()).iterator();
                    while (true) {
                        int i2 = i;
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        i = i2 + 1;
                        if (i2 < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        iPrinter.increaseIndent();
                        IPrinter iPrinter2 = iPrinter;
                        IPrinter.DefaultImpls.printIndex$default(iPrinter2, i2, (String) next, null, 4, null);
                        iPrinter2.newLine();
                        iPrinter2.decreaseIndent();
                    }
                } else {
                    iPrinter.printPair("listener", ((List) entry.getValue()).get(0));
                }
                IPrinter iPrinter3 = iPrinter;
                iPrinter3.newLine();
                iPrinter3.decreaseIndent();
                iPrinter = iPrinter3;
            }
            IPrinter iPrinter4 = iPrinter;
            iPrinter4.decreaseIndent();
            iPrinter4.newLine();
        }

        public final void setMyPluginListenerDispatcherInstance(int i) {
            this.myPluginListenerDispatcherInstance = i;
        }

        public final void setMyPluginListenerSnapshots(Map map) {
            map.getClass();
            this.myPluginListenerSnapshots = map;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: removePluginListener$lambda-0, reason: not valid java name */
    public static final boolean m2211removePluginListener$lambda0(PluginListener pluginListener, ListenerWithClassType listenerWithClassType) {
        pluginListener.getClass();
        listenerWithClassType.getClass();
        return Intrinsics.areEqual(listenerWithClassType.getListener(), pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void addPluginListener(Class cls, PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
        this.mPluginListenerList.add(new ListenerWithClassType(cls, pluginListener));
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void dispatchPluginConnected(String str, String str2, Plugin plugin, Context context) {
        str.getClass();
        str2.getClass();
        plugin.getClass();
        context.getClass();
        onPluginConnected(str, str2, plugin, context);
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void dispatchPluginDisconnected(String str, String str2, Plugin plugin) {
        str.getClass();
        str2.getClass();
        plugin.getClass();
        onPluginDisconnected(str, str2, plugin);
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void dispatchPluginFailedLoad(String str, String str2) {
        str.getClass();
        str2.getClass();
        onPluginFailedToLoad(str, str2);
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void dispatchPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        onPluginPackageChanged(pluginPackage.getPluginId().getId(), pluginPackage.getUserHandle(), markFlag);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.mPluginListenerList.clear();
    }

    @Override // com.motorola.plugin.core.PluginListener
    public void onPluginConnected(String str, String str2, final Plugin plugin, Context context) {
        str.getClass();
        str2.getClass();
        plugin.getClass();
        context.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl.onPluginConnected.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("plugin connected ", plugin);
            }
        }, 56, null);
        Iterator it = this.mPluginListenerList.iterator();
        while (it.hasNext()) {
            ((ListenerWithClassType) it.next()).onPluginConnected(str, str2, plugin, context);
        }
    }

    @Override // com.motorola.plugin.core.PluginListener
    public void onPluginDisconnected(String str, String str2, final Plugin plugin) {
        str.getClass();
        str2.getClass();
        plugin.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl.onPluginDisconnected.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("plugin disconnected ", plugin);
            }
        }, 56, null);
        Iterator it = this.mPluginListenerList.iterator();
        while (it.hasNext()) {
            ((ListenerWithClassType) it.next()).onPluginDisconnected(str, str2, plugin);
        }
    }

    @Override // com.motorola.plugin.core.PluginListener
    public void onPluginFailedToLoad(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl.onPluginFailedToLoad.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "plugin [" + str2 + "] failed to load for " + str;
            }
        }, 56, null);
        Iterator it = this.mPluginListenerList.iterator();
        while (it.hasNext()) {
            ((ListenerWithClassType) it.next()).onPluginFailedToLoad(str, str2);
        }
    }

    @Override // com.motorola.plugin.core.PluginListener
    public void onPluginPackageChanged(final String str, final UserHandle userHandle, final MarkFlag markFlag) {
        str.getClass();
        userHandle.getClass();
        markFlag.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl.onPluginPackageChanged.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "plugin package changed " + str + '#' + userHandle + ", " + markFlag;
            }
        }, 56, null);
        Iterator it = this.mPluginListenerList.iterator();
        while (it.hasNext()) {
            ((ListenerWithClassType) it.next()).onPluginPackageChanged(str, userHandle, markFlag);
        }
    }

    @Override // com.motorola.plugin.core.components.PluginListenerDispatcher
    public void removePluginListener(Class cls, final PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
        this.mPluginListenerList.removeIf(new Predicate() { // from class: com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PluginListenerDispatcherImpl.m2211removePluginListener$lambda0(pluginListener, (PluginListenerDispatcherImpl.ListenerWithClassType) obj);
            }
        });
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginListenerDispatcherSnapshot pluginListenerDispatcherSnapshot = new PluginListenerDispatcherSnapshot(iSnapshot);
        pluginListenerDispatcherSnapshot.setMyPluginListenerDispatcherInstance(hashCode());
        for (ListenerWithClassType listenerWithClassType : this.mPluginListenerList) {
            Map myPluginListenerSnapshots = pluginListenerDispatcherSnapshot.getMyPluginListenerSnapshots();
            String name = listenerWithClassType.getExpectClass().getName();
            Object arrayList = myPluginListenerSnapshots.get(name);
            if (arrayList == null) {
                arrayList = new ArrayList();
                myPluginListenerSnapshots.put(name, arrayList);
            }
            ((List) arrayList).add(listenerWithClassType.getListener().toString());
        }
        return pluginListenerDispatcherSnapshot;
    }
}
