package com.motorola.plugin.core.extension;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.components.PluginManager;
import com.motorola.plugin.core.components.PluginSubscriberAbility;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.core.extension.ExtensionControllerImpl;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.sdk.Plugin;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ExtensionControllerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ExtensionControllerImpl implements ExtensionController {
    public static final Companion Companion = new Companion(null);
    private static final int SORT_ORDER_DEFAULT = 3;
    private static final int SORT_ORDER_FEATURE = 1;
    private static final int SORT_ORDER_PLUGIN = 0;
    private static final int SORT_ORDER_UI_MODE = 2;
    private final ConfigurationController mConfigurationController;
    private final Context mDefaultContext;
    private final PluginManager mPluginManager;

    /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
    final class ExtensionBuilder implements ExtensionController.ExtensionBuilder {
        private final ExtensionImpl mExtension;

        public ExtensionBuilder(Context context, PluginManager pluginManager, ConfigurationController configurationController) {
            context.getClass();
            pluginManager.getClass();
            this.mExtension = new ExtensionImpl(context, pluginManager, configurationController);
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.Extension build() {
            ArrayList mProducers = this.mExtension.getMProducers();
            if (mProducers.size() > 1) {
                CollectionsKt.sortWith(mProducers, new Comparator() { // from class: com.motorola.plugin.core.extension.ExtensionControllerImpl$ExtensionBuilder$build$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((ExtensionControllerImpl.Item) obj).sortOrder()), Integer.valueOf(((ExtensionControllerImpl.Item) obj2).sortOrder()));
                    }
                });
            }
            this.mExtension.notifyChanged();
            return this.mExtension;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withCallback(Function1 function1) {
            function1.getClass();
            this.mExtension.getMCallbacks().add(function1);
            return this;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withDefault(Function0 function0) {
            function0.getClass();
            this.mExtension.addDefault(function0);
            return this;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withFeature(String str, Function0 function0) {
            str.getClass();
            function0.getClass();
            this.mExtension.addFeature(str, function0);
            return this;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withFeature(Function0 function0, Function0 function02) {
            function0.getClass();
            function02.getClass();
            this.mExtension.addFeature(function0, function02);
            return this;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withPlugin(Class cls) {
            return ExtensionController.ExtensionBuilder.DefaultImpls.withPlugin(this, cls);
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withPlugin(Class cls, Function1 function1) {
            cls.getClass();
            function1.getClass();
            this.mExtension.addPlugin(cls, function1);
            return this;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withUiMode(int i, Function0 function0) {
            function0.getClass();
            this.mExtension.addUiMode(i, function0);
            return this;
        }
    }

    /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
    final class ExtensionImpl implements ExtensionController.Extension {
        private final ArrayList mCallbacks;
        private final ConfigurationController mConfigurationController;
        private final Context mDefaultContext;
        private Object mItem;
        private Context mPluginContext;
        private final PluginManager mPluginManager;
        private final ArrayList mProducers;

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        abstract class BoolFeatureItem implements Item {
            private final Function0 mSupplier;

            public BoolFeatureItem(Function0 function0) {
                function0.getClass();
                this.mSupplier = function0;
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Producer
            public void destroy() {
            }

            @Override // java.util.function.Supplier
            public Object get() {
                if (validToProvider()) {
                    return this.mSupplier.mo2224invoke();
                }
                return null;
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 1;
            }

            protected abstract boolean validToProvider();
        }

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        final class Default implements Item {
            private final Function0 mSupplier;

            public Default(Function0 function0) {
                function0.getClass();
                this.mSupplier = function0;
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Producer
            public void destroy() {
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return this.mSupplier.mo2224invoke();
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 3;
            }
        }

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        final class FeatureFileItem extends BoolFeatureItem {
            private final Context mDefaultContext;
            private final String mFeature;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FeatureFileItem(String str, Function0 function0, Context context) {
                super(function0);
                str.getClass();
                function0.getClass();
                context.getClass();
                this.mFeature = str;
                this.mDefaultContext = context;
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.ExtensionImpl.BoolFeatureItem
            protected boolean validToProvider() {
                return this.mDefaultContext.getPackageManager().hasSystemFeature(this.mFeature);
            }
        }

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        final class FeatureFlagItem extends BoolFeatureItem {
            private final Function0 mBoolProvider;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FeatureFlagItem(Function0 function0, Function0 function02) {
                super(function02);
                function0.getClass();
                function02.getClass();
                this.mBoolProvider = function0;
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.ExtensionImpl.BoolFeatureItem
            protected boolean validToProvider() {
                return ((Boolean) this.mBoolProvider.mo2224invoke()).booleanValue();
            }
        }

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        final class PluginItem implements Item, PluginListener {
            private final Class cls;
            private final Function1 mConverter;
            private Object mItem;
            final /* synthetic */ ExtensionImpl this$0;

            public PluginItem(ExtensionImpl extensionImpl, Class cls, Function1 function1) {
                extensionImpl.getClass();
                cls.getClass();
                function1.getClass();
                this.this$0 = extensionImpl;
                this.cls = cls;
                this.mConverter = function1;
                extensionImpl.mPluginManager.addPluginListener(cls, this);
                PluginSubscriberAbility.DefaultImpls.subscribePlugin$default(extensionImpl.mPluginManager, cls, (PluginPackage) null, 2, (Object) null);
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Producer
            public void destroy() {
                PluginSubscriberAbility.DefaultImpls.unsubscribePlugin$default(this.this$0.mPluginManager, this.cls, (PluginPackage) null, 2, (Object) null);
                this.this$0.mPluginManager.removePluginListener(this.cls, this);
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return this.mItem;
            }

            @Override // com.motorola.plugin.core.PluginListener
            public void onPluginConnected(String str, String str2, Plugin plugin, Context context) {
                str.getClass();
                str2.getClass();
                plugin.getClass();
                context.getClass();
                this.this$0.mPluginContext = context;
                this.mItem = this.mConverter.invoke(plugin);
                this.this$0.notifyChanged();
            }

            @Override // com.motorola.plugin.core.PluginListener
            public void onPluginDisconnected(String str, String str2, Plugin plugin) {
                str.getClass();
                str2.getClass();
                plugin.getClass();
                this.this$0.mPluginContext = null;
                this.mItem = null;
                this.this$0.notifyChanged();
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 0;
            }
        }

        /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
        final class UiModeItem implements Item, ConfigurationController.ConfigurationListener {
            private final int mDesiredUiMode;
            private final Handler mHandler;
            private final Function0 mSupplier;
            private int mUiMode;
            final /* synthetic */ ExtensionImpl this$0;

            public UiModeItem(ExtensionImpl extensionImpl, int i, Function0 function0) {
                extensionImpl.getClass();
                function0.getClass();
                this.this$0 = extensionImpl;
                this.mDesiredUiMode = i;
                this.mSupplier = function0;
                this.mUiMode = extensionImpl.mDefaultContext.getResources().getConfiguration().uiMode & 15;
                this.mHandler = new Handler(Looper.getMainLooper());
                ConfigurationController configurationController = extensionImpl.mConfigurationController;
                if (configurationController == null) {
                    return;
                }
                configurationController.addCallback(this);
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* JADX INFO: renamed from: onConfigChanged$lambda-0, reason: not valid java name */
            public static final void m2218onConfigChanged$lambda0(ExtensionImpl extensionImpl) {
                extensionImpl.getClass();
                extensionImpl.notifyChanged();
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Producer
            public void destroy() {
                ConfigurationController configurationController = this.this$0.mConfigurationController;
                if (configurationController == null) {
                    return;
                }
                configurationController.removeCallback(this);
            }

            @Override // java.util.function.Supplier
            public Object get() {
                if (this.mUiMode == this.mDesiredUiMode) {
                    return this.mSupplier.mo2224invoke();
                }
                return null;
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration, BitFlag bitFlag) {
                configuration.getClass();
                bitFlag.getClass();
                int i = configuration.uiMode & 15;
                if (i != this.mUiMode) {
                    this.mUiMode = i;
                    Handler handler = this.mHandler;
                    final ExtensionImpl extensionImpl = this.this$0;
                    handler.post(new Runnable() { // from class: com.motorola.plugin.core.extension.ExtensionControllerImpl$ExtensionImpl$UiModeItem$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExtensionControllerImpl.ExtensionImpl.UiModeItem.m2218onConfigChanged$lambda0(extensionImpl);
                        }
                    });
                }
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
            public void onLowMemory() {
                ConfigurationController.ConfigurationListener.DefaultImpls.onLowMemory(this);
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
            public void onTrimMemory(int i) {
                ConfigurationController.ConfigurationListener.DefaultImpls.onTrimMemory(this, i);
            }

            @Override // com.motorola.plugin.core.extension.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 2;
            }
        }

        public ExtensionImpl(Context context, PluginManager pluginManager, ConfigurationController configurationController) {
            context.getClass();
            pluginManager.getClass();
            this.mDefaultContext = context;
            this.mPluginManager = pluginManager;
            this.mConfigurationController = configurationController;
            this.mProducers = new ArrayList();
            this.mCallbacks = new ArrayList();
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public void addCallback(Function1 function1) {
            function1.getClass();
            this.mCallbacks.add(function1);
        }

        public final void addDefault(Function0 function0) {
            function0.getClass();
            this.mProducers.add(new Default(function0));
        }

        public final void addFeature(String str, Function0 function0) {
            str.getClass();
            function0.getClass();
            this.mProducers.add(new FeatureFileItem(str, function0, this.mDefaultContext));
        }

        public final void addFeature(Function0 function0, Function0 function02) {
            function0.getClass();
            function02.getClass();
            this.mProducers.add(new FeatureFlagItem(function0, function02));
        }

        public final void addPlugin(Class cls, Function1 function1) {
            cls.getClass();
            function1.getClass();
            this.mProducers.add(new PluginItem(this, cls, function1));
        }

        public final void addUiMode(int i, Function0 function0) {
            function0.getClass();
            this.mProducers.add(new UiModeItem(this, i, function0));
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public void clearItem() {
            this.mItem = null;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public void destroy() {
            ArrayList arrayList = this.mProducers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Item) obj).destroy();
            }
        }

        @Override // com.motorola.plugin.core.misc.Disposable
        public void dispose() {
            destroy();
            clearItem();
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public Object get() {
            return this.mItem;
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public Context getContext() {
            Context context = this.mPluginContext;
            return context == null ? this.mDefaultContext : context;
        }

        public final ArrayList getMCallbacks() {
            return this.mCallbacks;
        }

        public final ArrayList getMProducers() {
            return this.mProducers;
        }

        public final void notifyChanged() {
            Object obj;
            ArrayList arrayList = this.mProducers;
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    obj = null;
                    break;
                }
                obj = arrayList.get(i2);
                i2++;
                if (((Item) obj).get() != null) {
                    break;
                }
            }
            Item item = (Item) obj;
            this.mItem = item != null ? item.get() : null;
            ArrayList arrayList2 = this.mCallbacks;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((Function1) obj2).invoke(this.mItem);
            }
        }

        @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
        public Object reload() {
            notifyChanged();
            return get();
        }
    }

    /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
    interface Item extends Producer {
        int sortOrder();
    }

    /* JADX INFO: compiled from: ExtensionControllerImpl.kt */
    interface Producer extends Supplier {
        void destroy();
    }

    public ExtensionControllerImpl(@DisplayContext Context context, PluginManager pluginManager, ConfigurationController configurationController) {
        context.getClass();
        pluginManager.getClass();
        this.mDefaultContext = context;
        this.mPluginManager = pluginManager;
        this.mConfigurationController = configurationController;
    }

    @Override // com.motorola.plugin.core.extension.ExtensionController
    public ExtensionController.ExtensionBuilder newExtension(Class cls) {
        cls.getClass();
        return new ExtensionBuilder(this.mDefaultContext, this.mPluginManager, this.mConfigurationController);
    }
}
