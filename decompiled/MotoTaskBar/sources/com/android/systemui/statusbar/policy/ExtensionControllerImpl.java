package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionControllerImpl implements ExtensionController {
    private final ConfigurationController mConfigurationController;
    private final Context mDefaultContext;

    class ExtensionBuilder implements ExtensionController.ExtensionBuilder {
        private ExtensionImpl mExtension;

        private ExtensionBuilder() {
            this.mExtension = new ExtensionImpl();
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.Extension build() {
            Collections.sort(this.mExtension.mProducers, Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.statusbar.policy.ExtensionControllerImpl$ExtensionBuilder$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((ExtensionControllerImpl.Item) obj).sortOrder();
                }
            }));
            this.mExtension.notifyChanged();
            return this.mExtension;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withCallback(Consumer consumer) {
            this.mExtension.mCallbacks.add(consumer);
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder withPlugin(Class cls) {
            return withPlugin(cls, PluginManager.Helper.getAction(cls));
        }

        public ExtensionController.ExtensionBuilder withPlugin(Class cls, String str) {
            return withPlugin(cls, str, null);
        }

        public ExtensionController.ExtensionBuilder withPlugin(Class cls, String str, ExtensionController.PluginConverter pluginConverter) {
            this.mExtension.addPlugin(str, cls, pluginConverter);
            return this;
        }
    }

    class ExtensionImpl implements ExtensionController.Extension {
        private final ArrayList mCallbacks;
        private Object mItem;
        private Context mPluginContext;
        private final ArrayList mProducers;

        class PluginItem implements Item, PluginListener {
            private Object mItem;

            public PluginItem(String str, Class cls, ExtensionController.PluginConverter pluginConverter) {
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public Object get() {
                return this.mItem;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginConnected(Plugin plugin, Context context) {
                ExtensionImpl.this.mPluginContext = context;
                this.mItem = plugin;
                ExtensionImpl.this.notifyChanged();
            }

            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginDisconnected(Plugin plugin) {
                ExtensionImpl.this.mPluginContext = null;
                this.mItem = null;
                ExtensionImpl.this.notifyChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 0;
            }
        }

        private ExtensionImpl() {
            this.mProducers = new ArrayList();
            this.mCallbacks = new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyChanged() {
            this.mItem = null;
            int i = 0;
            while (true) {
                if (i >= this.mProducers.size()) {
                    break;
                }
                Object obj = ((Item) this.mProducers.get(i)).get();
                if (obj != null) {
                    this.mItem = obj;
                    break;
                }
                i++;
            }
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                ((Consumer) this.mCallbacks.get(i2)).accept(this.mItem);
            }
        }

        public void addPlugin(String str, Class cls, ExtensionController.PluginConverter pluginConverter) {
            this.mProducers.add(new PluginItem(str, cls, pluginConverter));
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public Object get() {
            return this.mItem;
        }
    }

    interface Item extends Producer {
        int sortOrder();
    }

    interface Producer {
        Object get();
    }

    public ExtensionControllerImpl(Context context, ConfigurationController configurationController) {
        this.mDefaultContext = context;
        this.mConfigurationController = configurationController;
    }

    @Override // com.android.systemui.statusbar.policy.ExtensionController
    public ExtensionBuilder newExtension(Class cls) {
        return new ExtensionBuilder();
    }
}
