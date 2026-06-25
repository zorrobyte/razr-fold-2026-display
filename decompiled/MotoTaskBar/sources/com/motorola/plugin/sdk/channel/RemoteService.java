package com.motorola.plugin.sdk.channel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(version = 3101)
public abstract class RemoteService extends Service {
    public static final String ACTION_PLUGIN_DISCOVERY = "com.motorola.plugin.action.PLUGIN_DISCOVERY";
    private static final boolean DEBUG = false;
    public static final String EXTRA_BUNDLE_DATA = "com.motorola.plugin.extra.EXTRA_BUNDLE_DATA";
    public static final String EXTRA_METHOD = "com.motorola.plugin.extra.METHOD";
    public static final String EXTRA_NOTIFY_URIS = "com.motorola.plugin.extra.NOTIFY_URIS";
    public static final String EXTRA_RECEIPT = "com.motorola.plugin.extra.RECEIPT";
    public static final String EXTRA_SEQUENCE = "com.motorola.plugin.extra.SEQUENCE";
    public static final String META_DATA_KEY_PROVIDER = "com.motorola.plugin.provider";
    public static final String METHOD_CLEAR_CACHE = "com.motorola.plugin.extra.METHOD_CLEAR_CACHE";
    public static final String METHOD_CLEAR_DATA = "com.motorola.plugin.extra.METHOD_CLEAR_DATA";
    public static final String METHOD_NOTIFY_DATASET_CHANGE = "com.motorola.plugin.extra.METHOD_NOTIFY_DATASET_CHANGE";
    public static final String METHOD_SEND_EXTRA_DATA = "com.motorola.plugin.extra.METHOD_SEND_EXTRA_DATA";
    public static final String PLUGIN_CHANNEL_BIND_PERMISSION = "com.motorola.myscreen.permission.ACCESS_MYSCREEN";
    public static final String TAG = "RemoteService";
    private Handler mHandler;
    private RemoteCallbackList mRemoteCallbackList;

    public class RemoteAppResolutionCallback {
        private final IRemoteCallback callback;
        private final int sequence;

        public RemoteAppResolutionCallback(int i, IRemoteCallback iRemoteCallback) {
            this.sequence = i;
            this.callback = iRemoteCallback;
        }

        public void onReplyToRemoteApp(Bundle bundle) {
            bundle.putInt(RemoteService.EXTRA_SEQUENCE, this.sequence);
            try {
                if (Log.isLoggable(RemoteService.TAG, 2)) {
                    Log.v(RemoteService.TAG, "[" + this.sequence + "] reply to remote client");
                }
                this.callback.sendResult(bundle);
                if (Log.isLoggable(RemoteService.TAG, 3)) {
                    Log.d(RemoteService.TAG, "[" + this.sequence + "] reply to remote client done");
                }
            } catch (RemoteException e) {
                if (Log.isLoggable(RemoteService.TAG, 5)) {
                    Log.w(RemoteService.TAG, "[" + this.sequence + "] Error reply to client", e);
                }
            }
        }
    }

    class ServiceHandler extends Handler {
        public static final int MSG_NOTIFY_DATASET_CHANGE = 3;
        public static final int MSG_REMOTE_CALL = 1;
        public static final int MSG_REMOTE_CONNECT = 0;
        public static final int MSG_REMOTE_DISCONNECT = 2;
        public static final int MSG_REQUEST_SEND_EXTRA_DATA = 4;
        private final RemoteService service;

        private ServiceHandler(RemoteService remoteService) {
            super(Looper.getMainLooper());
            this.service = remoteService;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                ClientId clientId = (ClientId) someArgs.arg1;
                IRemoteCallback iRemoteCallback = (IRemoteCallback) someArgs.arg2;
                someArgs.recycle();
                if (Log.isLoggable(RemoteService.TAG, 3)) {
                    Log.d(RemoteService.TAG, "[" + clientId + "] remote client connected");
                }
                this.service.handleRemoteConnect(clientId, iRemoteCallback);
                return;
            }
            if (i == 1) {
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                RemoteChannelRequestInfo remoteChannelRequestInfo = (RemoteChannelRequestInfo) someArgs2.arg1;
                IRemoteCallback iRemoteCallback2 = (IRemoteCallback) someArgs2.arg2;
                int i2 = someArgs2.argI1;
                someArgs2.recycle();
                if (Log.isLoggable(RemoteService.TAG, 3)) {
                    Log.d(RemoteService.TAG, "[" + remoteChannelRequestInfo.callingClientId + "][" + i2 + "] transfer request");
                }
                this.service.handleRemoteRequest(remoteChannelRequestInfo, new RemoteAppResolutionCallback(i2, iRemoteCallback2));
                return;
            }
            if (i == 2) {
                SomeArgs someArgs3 = (SomeArgs) message.obj;
                ClientId clientId2 = (ClientId) someArgs3.arg1;
                boolean zBooleanValue = ((Boolean) someArgs3.arg2).booleanValue();
                someArgs3.recycle();
                if (!zBooleanValue && Log.isLoggable(RemoteService.TAG, 3)) {
                    Log.d(RemoteService.TAG, "[" + clientId2 + "] remote client disconnected");
                }
                this.service.handleRemoteDisconnect(clientId2, zBooleanValue);
                return;
            }
            if (i == 3) {
                SomeArgs someArgs4 = (SomeArgs) message.obj;
                Uri[] uriArr = (Uri[]) someArgs4.arg1;
                Bundle bundle = (Bundle) someArgs4.arg2;
                someArgs4.recycle();
                if (Log.isLoggable(RemoteService.TAG, 3)) {
                    Log.d(RemoteService.TAG, "request notify dataset change for " + Arrays.toString(uriArr));
                }
                this.service.handleNotifyDataChanged(uriArr, bundle);
                return;
            }
            if (i != 4) {
                return;
            }
            SomeArgs someArgs5 = (SomeArgs) message.obj;
            ClientId clientId3 = (ClientId) someArgs5.arg1;
            Bundle bundle2 = (Bundle) someArgs5.arg2;
            someArgs5.recycle();
            if (Log.isLoggable(RemoteService.TAG, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("request send extra data to ");
                sb.append(clientId3 != null ? clientId3 : "all clients");
                Log.d(RemoteService.TAG, sb.toString());
            }
            this.service.handleSendExtraDataToRemoteClient(clientId3, bundle2);
        }
    }

    final class SomeArgs {
        private static final int MAX_POOL_SIZE = 10;
        static final int WAIT_FINISHED = 2;
        static final int WAIT_NONE = 0;
        static final int WAIT_WAITING = 1;
        private static SomeArgs sPool;
        private static final Object sPoolLock = new Object();
        private static int sPoolSize;
        public Object arg1;
        public Object arg2;
        public int argI1;
        public int argI2;
        private boolean mInPool;
        private SomeArgs mNext;
        int mWaitState = 0;

        private SomeArgs() {
        }

        private void clear() {
            this.arg1 = null;
            this.arg2 = null;
            this.argI1 = 0;
            this.argI2 = 0;
        }

        public static SomeArgs obtain() {
            synchronized (sPoolLock) {
                try {
                    int i = sPoolSize;
                    if (i <= 0) {
                        return new SomeArgs();
                    }
                    SomeArgs someArgs = sPool;
                    sPool = someArgs.mNext;
                    someArgs.mNext = null;
                    someArgs.mInPool = false;
                    sPoolSize = i - 1;
                    return someArgs;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void complete() {
            synchronized (this) {
                try {
                    if (this.mWaitState != 1) {
                        throw new IllegalStateException("Not waiting");
                    }
                    this.mWaitState = 2;
                    notifyAll();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void recycle() {
            if (this.mInPool) {
                throw new IllegalStateException("Already recycled.");
            }
            if (this.mWaitState != 0) {
                return;
            }
            synchronized (sPoolLock) {
                try {
                    clear();
                    int i = sPoolSize;
                    if (i < 10) {
                        this.mNext = sPool;
                        this.mInPool = true;
                        sPool = this;
                        sPoolSize = i + 1;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$46kXulUfFGoOnAQN_ZXgsVoSc1U(Uri[] uriArr, Bundle bundle, IRemoteCallback iRemoteCallback, Object obj) throws RemoteException {
        Bundle bundle2 = new Bundle();
        bundle2.putString(EXTRA_METHOD, METHOD_NOTIFY_DATASET_CHANGE);
        bundle2.putParcelableArrayList(EXTRA_NOTIFY_URIS, new ArrayList<>(Arrays.asList(uriArr)));
        if (bundle != null) {
            bundle2.putBundle(EXTRA_BUNDLE_DATA, bundle);
        }
        iRemoteCallback.sendResult(bundle2);
    }

    public static /* synthetic */ void $r8$lambda$dusQsLsrG4zkTvSZGZlzU5qqhd4(PrintWriter printWriter, IRemoteCallback iRemoteCallback, Object obj) {
        printWriter.println("  " + obj);
        printWriter.println("    " + iRemoteCallback);
    }

    public static /* synthetic */ void $r8$lambda$sYWmOxUL29IxASFPzkG_ZcZnSZA(ClientId clientId, Bundle bundle, IRemoteCallback iRemoteCallback, Object obj) throws RemoteException {
        if (clientId == null || clientId == obj) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(EXTRA_METHOD, METHOD_SEND_EXTRA_DATA);
            bundle2.putBundle(EXTRA_BUNDLE_DATA, bundle);
            iRemoteCallback.sendResult(bundle2);
        }
    }

    public static /* synthetic */ void $r8$lambda$slsPEmHcIQz7fSVtuASo75F5FvE(ClientId clientId, List list, IRemoteCallback iRemoteCallback, Object obj) {
        if (obj == clientId) {
            list.add(iRemoteCallback);
        }
    }

    private void broadcastSafely(RemoteCallbackList remoteCallbackList, ThrowingBiConsumer throwingBiConsumer) {
        int iBeginBroadcast = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < iBeginBroadcast; i++) {
            Object broadcastCookie = remoteCallbackList.getBroadcastCookie(i);
            try {
                throwingBiConsumer.accept(remoteCallbackList.getBroadcastItem(i), broadcastCookie);
            } catch (Exception e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "[" + broadcastCookie + "] Error invoking a remote callback", e);
                }
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private void forEach(RemoteCallbackList remoteCallbackList, BiConsumer biConsumer) {
        int registeredCallbackCount = remoteCallbackList.getRegisteredCallbackCount();
        for (int i = 0; i < registeredCallbackCount; i++) {
            biConsumer.accept(remoteCallbackList.getRegisteredCallbackItem(i), remoteCallbackList.getRegisteredCallbackCookie(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyDataChanged(final Uri[] uriArr, final Bundle bundle) {
        broadcastSafely(this.mRemoteCallbackList, new ThrowingBiConsumer() { // from class: com.motorola.plugin.sdk.channel.RemoteService$$ExternalSyntheticLambda0
            @Override // com.motorola.plugin.sdk.channel.ThrowingBiConsumer
            public final void acceptOrThrow(Object obj, Object obj2) throws RemoteException {
                RemoteService.$r8$lambda$46kXulUfFGoOnAQN_ZXgsVoSc1U(uriArr, bundle, (IRemoteCallback) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoteConnect(ClientId clientId, IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback != null) {
            this.mRemoteCallbackList.register(iRemoteCallback, clientId);
        }
        onConnected(clientId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoteDisconnect(final ClientId clientId, boolean z) {
        if (!z) {
            final ArrayList arrayList = new ArrayList();
            forEach(this.mRemoteCallbackList, new BiConsumer() { // from class: com.motorola.plugin.sdk.channel.RemoteService$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    RemoteService.$r8$lambda$slsPEmHcIQz7fSVtuASo75F5FvE(clientId, arrayList, (IRemoteCallback) obj, obj2);
                }
            });
            arrayList.forEach(new Consumer() { // from class: com.motorola.plugin.sdk.channel.RemoteService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.mRemoteCallbackList.unregister((IRemoteCallback) obj);
                }
            });
        }
        onDisconnected(clientId.uniqueId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoteRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, RemoteAppResolutionCallback remoteAppResolutionCallback) {
        String string = remoteChannelRequestInfo.bundle.getString(EXTRA_METHOD, "");
        string.getClass();
        if (!string.equals(METHOD_CLEAR_CACHE) && !string.equals(METHOD_CLEAR_DATA)) {
            onRemoteRequest(remoteChannelRequestInfo, remoteAppResolutionCallback);
            return;
        }
        boolean zOnRequestClearPluginData = onRequestClearPluginData(remoteChannelRequestInfo.callingClientId, remoteChannelRequestInfo.pluginId, string.equals(METHOD_CLEAR_CACHE));
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_RECEIPT, zOnRequestClearPluginData);
        remoteAppResolutionCallback.onReplyToRemoteApp(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendExtraDataToRemoteClient(final ClientId clientId, final Bundle bundle) {
        broadcastSafely(this.mRemoteCallbackList, new ThrowingBiConsumer() { // from class: com.motorola.plugin.sdk.channel.RemoteService$$ExternalSyntheticLambda2
            @Override // com.motorola.plugin.sdk.channel.ThrowingBiConsumer
            public final void acceptOrThrow(Object obj, Object obj2) throws RemoteException {
                RemoteService.$r8$lambda$sYWmOxUL29IxASFPzkG_ZcZnSZA(clientId, bundle, (IRemoteCallback) obj, obj2);
            }
        });
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new ServiceHandler();
        this.mRemoteCallbackList = new RemoteCallbackList() { // from class: com.motorola.plugin.sdk.channel.RemoteService.1
            @Override // android.os.RemoteCallbackList
            public void onCallbackDied(IRemoteCallback iRemoteCallback, Object obj) {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = obj;
                someArgsObtain.arg2 = Boolean.TRUE;
                RemoteService.this.mHandler.obtainMessage(2, someArgsObtain).sendToTarget();
            }
        };
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "[" + hashCode() + "] onAttach");
        }
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        printWriter.println("RemoteService:[3101]");
        if (Log.isLoggable(TAG, 3)) {
            printWriter.println("  observers:");
            forEach(this.mRemoteCallbackList, new BiConsumer() { // from class: com.motorola.plugin.sdk.channel.RemoteService$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    RemoteService.$r8$lambda$dusQsLsrG4zkTvSZGZlzU5qqhd4(printWriter, (IRemoteCallback) obj, obj2);
                }
            });
        }
    }

    public void notifyDataChanged(Uri... uriArr) {
        notifyDataChanged(uriArr, null);
    }

    public void notifyDataChanged(Uri[] uriArr, Bundle bundle) {
        if (uriArr.length == 0) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "ignore empty dataset change request");
            }
        } else {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = uriArr;
            someArgsObtain.arg2 = bundle;
            this.mHandler.obtainMessage(3, someArgsObtain).sendToTarget();
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IRemoteChannelTransfer.Stub() { // from class: com.motorola.plugin.sdk.channel.RemoteService.2
            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void onConnect(ClientId clientId, IRemoteCallback iRemoteCallback) throws RemoteException {
                int callingUid = Binder.getCallingUid();
                String[] packagesForUid = RemoteService.this.getPackageManager().getPackagesForUid(callingUid);
                if (packagesForUid == null) {
                    throw new SecurityException("[" + clientId + "] Connect from unknown uid " + callingUid);
                }
                if (Arrays.binarySearch(packagesForUid, clientId.packageName) < 0) {
                    throw new SecurityException("[" + clientId + "] Connect from unmatched client uid " + callingUid);
                }
                if (Log.isLoggable(RemoteService.TAG, 2)) {
                    Log.v(RemoteService.TAG, "[" + clientId + "] onConnect called, posting");
                }
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = clientId;
                someArgsObtain.arg2 = iRemoteCallback;
                RemoteService.this.mHandler.obtainMessage(0, someArgsObtain).sendToTarget();
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void onDisconnect(ClientId clientId) throws RemoteException {
                int callingUid = Binder.getCallingUid();
                String[] packagesForUid = RemoteService.this.getPackageManager().getPackagesForUid(callingUid);
                if (packagesForUid == null) {
                    throw new SecurityException("[" + clientId + "] Disconnect from unknown uid " + callingUid);
                }
                if (Arrays.binarySearch(packagesForUid, clientId.packageName) < 0) {
                    throw new SecurityException("[" + clientId + "] Disconnect from unmatched client uid " + callingUid);
                }
                if (Log.isLoggable(RemoteService.TAG, 2)) {
                    Log.v(RemoteService.TAG, "[" + clientId + "] onDisconnect called, posting");
                }
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = clientId;
                someArgsObtain.arg2 = Boolean.FALSE;
                RemoteService.this.mHandler.obtainMessage(2, someArgsObtain).sendToTarget();
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void transferRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                try {
                    if (RemoteService.this.getPackageManager().getPackageUid(remoteChannelRequestInfo.callingClientId.packageName, 0) != Binder.getCallingUid()) {
                        throw new SecurityException("Calling uid " + remoteChannelRequestInfo.callingClientId + " did not match the uid");
                    }
                    if (Log.isLoggable(RemoteService.TAG, 2)) {
                        Log.v(RemoteService.TAG, "[" + remoteChannelRequestInfo.callingClientId + "][" + i + "] transfer request called; posting");
                    }
                    SomeArgs someArgsObtain = SomeArgs.obtain();
                    someArgsObtain.arg1 = remoteChannelRequestInfo;
                    someArgsObtain.arg2 = iRemoteCallback;
                    someArgsObtain.argI1 = i;
                    RemoteService.this.mHandler.obtainMessage(1, someArgsObtain).sendToTarget();
                } catch (PackageManager.NameNotFoundException unused) {
                    throw new SecurityException("Error uid for package " + remoteChannelRequestInfo.callingClientId);
                }
            }
        };
    }

    public void onConnected(ClientId clientId) {
        onConnected(clientId.uniqueId);
    }

    public void onConnected(String str) {
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "[" + hashCode() + "] onDestroy");
        }
        this.mRemoteCallbackList.kill();
        super.onDestroy();
    }

    public void onDisconnected(ClientId clientId) {
        onDisconnected(clientId.uniqueId);
    }

    public void onDisconnected(String str) {
    }

    public abstract void onRemoteRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, RemoteAppResolutionCallback remoteAppResolutionCallback);

    public boolean onRequestClearPluginData(ClientId clientId, ClientId clientId2, boolean z) {
        return false;
    }

    public void sendExtraDataToClient(ClientId clientId, Bundle bundle) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = clientId;
        someArgsObtain.arg2 = bundle;
        this.mHandler.obtainMessage(4, someArgsObtain).sendToTarget();
    }
}
