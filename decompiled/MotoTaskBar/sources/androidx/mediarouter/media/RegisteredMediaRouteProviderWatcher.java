package androidx.mediarouter.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.RegisteredMediaRouteProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class RegisteredMediaRouteProviderWatcher {
    final Callback mCallback;
    private final Context mContext;
    private final PackageManager mPackageManager;
    private boolean mRunning;
    private final ArrayList mProviders = new ArrayList();
    private final BroadcastReceiver mScanPackagesReceiver = new BroadcastReceiver() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    };
    private final Runnable mScanPackagesRunnable = new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.2
        @Override // java.lang.Runnable
        public void run() {
            RegisteredMediaRouteProviderWatcher.this.scanPackages();
        }
    };
    private final Handler mHandler = new Handler();

    abstract class Api33 {
        static void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler, int i) {
            context.registerReceiver(broadcastReceiver, intentFilter, null, handler, i);
        }
    }

    public interface Callback {
        void addProvider(MediaRouteProvider mediaRouteProvider);

        void releaseProviderController(RegisteredMediaRouteProvider registeredMediaRouteProvider, MediaRouteProvider.RouteController routeController);

        void removeProvider(MediaRouteProvider mediaRouteProvider);
    }

    RegisteredMediaRouteProviderWatcher(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mPackageManager = context.getPackageManager();
    }

    private int findProvider(String str, String str2) {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            if (((RegisteredMediaRouteProvider) this.mProviders.get(i)).hasComponentName(str, str2)) {
                return i;
            }
        }
        return -1;
    }

    static boolean listContainsServiceInfo(List list, ServiceInfo serviceInfo) {
        if (serviceInfo != null && list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo2 = (ServiceInfo) it.next();
                if (serviceInfo.packageName.equals(serviceInfo2.packageName) && serviceInfo.name.equals(serviceInfo2.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    List getMediaRoute2ProviderServices() {
        Intent intent = new Intent("android.media.MediaRoute2ProviderService");
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = this.mPackageManager.queryIntentServices(intent, 0).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().serviceInfo);
        }
        return arrayList;
    }

    void scanPackages() {
        int i;
        if (this.mRunning) {
            new ArrayList();
            List mediaRoute2ProviderServices = getMediaRoute2ProviderServices();
            int i2 = 0;
            Iterator<ResolveInfo> it = this.mPackageManager.queryIntentServices(new Intent("android.media.MediaRouteProviderService"), 0).iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = it.next().serviceInfo;
                if (serviceInfo != null && (!MediaRouter.isMediaTransferEnabled() || !listContainsServiceInfo(mediaRoute2ProviderServices, serviceInfo))) {
                    int iFindProvider = findProvider(serviceInfo.packageName, serviceInfo.name);
                    if (iFindProvider < 0) {
                        final RegisteredMediaRouteProvider registeredMediaRouteProvider = new RegisteredMediaRouteProvider(this.mContext, new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        registeredMediaRouteProvider.setControllerCallback(new RegisteredMediaRouteProvider.ControllerCallback() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0
                            @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerCallback
                            public final void onControllerReleasedByProvider(MediaRouteProvider.RouteController routeController) {
                                this.f$0.mCallback.releaseProviderController(registeredMediaRouteProvider, routeController);
                            }
                        });
                        registeredMediaRouteProvider.start();
                        i = i2 + 1;
                        this.mProviders.add(i2, registeredMediaRouteProvider);
                        this.mCallback.addProvider(registeredMediaRouteProvider);
                    } else if (iFindProvider >= i2) {
                        RegisteredMediaRouteProvider registeredMediaRouteProvider2 = (RegisteredMediaRouteProvider) this.mProviders.get(iFindProvider);
                        registeredMediaRouteProvider2.start();
                        registeredMediaRouteProvider2.rebindIfDisconnected();
                        i = i2 + 1;
                        Collections.swap(this.mProviders, iFindProvider, i2);
                    }
                    i2 = i;
                }
            }
            if (i2 < this.mProviders.size()) {
                for (int size = this.mProviders.size() - 1; size >= i2; size--) {
                    RegisteredMediaRouteProvider registeredMediaRouteProvider3 = (RegisteredMediaRouteProvider) this.mProviders.get(size);
                    this.mCallback.removeProvider(registeredMediaRouteProvider3);
                    this.mProviders.remove(registeredMediaRouteProvider3);
                    registeredMediaRouteProvider3.setControllerCallback(null);
                    registeredMediaRouteProvider3.stop();
                }
            }
        }
    }

    public void start() {
        if (this.mRunning) {
            return;
        }
        this.mRunning = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        Api33.registerReceiver(this.mContext, this.mScanPackagesReceiver, intentFilter, this.mHandler, 4);
        this.mHandler.post(this.mScanPackagesRunnable);
    }
}
