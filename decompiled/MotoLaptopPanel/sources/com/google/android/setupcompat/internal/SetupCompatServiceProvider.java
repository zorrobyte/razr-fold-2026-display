package com.google.android.setupcompat.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class SetupCompatServiceProvider {
    private static volatile SetupCompatServiceProvider instance;
    private final Context context;
    private static final Logger LOG = new Logger("SetupCompatServiceProvider");
    static final Intent COMPAT_SERVICE_INTENT = new Intent().setPackage(PartnerConfigHelper.SUW_PACKAGE_NAME).setAction("com.google.android.setupcompat.SetupCompatService.BIND");
    static boolean disableLooperCheckForTesting = false;
    final ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.google.android.setupcompat.internal.SetupCompatServiceProvider.1
        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.REBIND_REQUIRED));
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.SERVICE_NOT_USABLE));
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            State state = State.CONNECTED;
            if (iBinder == null) {
                state = State.DISCONNECTED;
                SetupCompatServiceProvider.LOG.w("Binder is null when onServiceConnected was called!");
            }
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(state, ISetupCompatService.Stub.asInterface(iBinder)));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.DISCONNECTED));
        }
    };
    private volatile ServiceContext serviceContext = new ServiceContext(State.NOT_STARTED);
    private final AtomicReference connectedConditionRef = new AtomicReference();

    final class ServiceContext {
        final ISetupCompatService compatService;
        final State state;

        ServiceContext(State state) {
            this(state, null);
        }

        private ServiceContext(State state, ISetupCompatService iSetupCompatService) {
            this.state = state;
            this.compatService = iSetupCompatService;
            if (state == State.CONNECTED) {
                Preconditions.checkNotNull(iSetupCompatService, "CompatService cannot be null when state is connected");
            }
        }
    }

    enum State {
        NOT_STARTED,
        BIND_FAILED,
        BINDING,
        CONNECTED,
        DISCONNECTED,
        SERVICE_NOT_USABLE,
        REBIND_REQUIRED
    }

    SetupCompatServiceProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    public static ISetupCompatService get(Context context, long j, TimeUnit timeUnit) {
        return getInstance(context).getService(j, timeUnit);
    }

    private CountDownLatch getAndClearConnectedCondition() {
        return (CountDownLatch) this.connectedConditionRef.getAndSet(null);
    }

    private CountDownLatch getConnectedCondition() {
        CountDownLatch countDownLatchCreateCountDownLatch;
        do {
            CountDownLatch countDownLatch = (CountDownLatch) this.connectedConditionRef.get();
            if (countDownLatch != null) {
                return countDownLatch;
            }
            countDownLatchCreateCountDownLatch = createCountDownLatch();
        } while (!this.connectedConditionRef.compareAndSet(null, countDownLatchCreateCountDownLatch));
        return countDownLatchCreateCountDownLatch;
    }

    private synchronized ServiceContext getCurrentServiceState() {
        return this.serviceContext;
    }

    static SetupCompatServiceProvider getInstance(Context context) {
        SetupCompatServiceProvider setupCompatServiceProvider;
        Preconditions.checkNotNull(context, "Context object cannot be null.");
        SetupCompatServiceProvider setupCompatServiceProvider2 = instance;
        if (setupCompatServiceProvider2 != null) {
            return setupCompatServiceProvider2;
        }
        synchronized (SetupCompatServiceProvider.class) {
            try {
                setupCompatServiceProvider = instance;
                if (setupCompatServiceProvider == null) {
                    setupCompatServiceProvider = new SetupCompatServiceProvider(context.getApplicationContext());
                    instance = setupCompatServiceProvider;
                    instance.requestServiceBind();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return setupCompatServiceProvider;
    }

    private synchronized void requestServiceBind() {
        boolean zBindService;
        State state = getCurrentServiceState().state;
        if (state == State.CONNECTED) {
            LOG.atInfo("Refusing to rebind since current state is already connected");
            return;
        }
        if (state != State.NOT_STARTED) {
            LOG.atInfo("Unbinding existing service connection.");
            this.context.unbindService(this.serviceConnection);
        }
        try {
            zBindService = this.context.bindService(COMPAT_SERVICE_INTENT, this.serviceConnection, 1);
        } catch (SecurityException e) {
            LOG.e("Unable to bind to compat service. " + e);
            zBindService = false;
        }
        if (!zBindService) {
            swapServiceContextAndNotify(new ServiceContext(State.BIND_FAILED));
            LOG.e("Context#bindService did not succeed.");
        } else if (getCurrentState() != State.CONNECTED) {
            swapServiceContextAndNotify(new ServiceContext(State.BINDING));
            LOG.atInfo("Context#bindService went through, now waiting for service connection");
        }
    }

    public static void setInstanceForTesting(SetupCompatServiceProvider setupCompatServiceProvider) {
        instance = setupCompatServiceProvider;
    }

    private ISetupCompatService waitForConnection(long j, TimeUnit timeUnit) throws TimeoutException {
        ServiceContext currentServiceState = getCurrentServiceState();
        if (currentServiceState.state == State.CONNECTED) {
            return currentServiceState.compatService;
        }
        CountDownLatch connectedCondition = getConnectedCondition();
        Logger logger = LOG;
        logger.atInfo("Waiting for service to get connected");
        if (!connectedCondition.await(j, timeUnit)) {
            requestServiceBind();
            throw new TimeoutException(String.format("Failed to acquire connection after [%s %s]", Long.valueOf(j), timeUnit));
        }
        ServiceContext currentServiceState2 = getCurrentServiceState();
        logger.atInfo(String.format("Finished waiting for service to get connected. Current state = %s", currentServiceState2.state));
        return currentServiceState2.compatService;
    }

    protected CountDownLatch createCountDownLatch() {
        return new CountDownLatch(1);
    }

    State getCurrentState() {
        return this.serviceContext.state;
    }

    public ISetupCompatService getService(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        Preconditions.checkState(disableLooperCheckForTesting || Looper.getMainLooper() != Looper.myLooper(), "getService blocks and should not be called from the main thread.");
        ServiceContext currentServiceState = getCurrentServiceState();
        switch (currentServiceState.state) {
            case NOT_STARTED:
                LOG.w("NOT_STARTED state only possible before instance is created.");
                return null;
            case BIND_FAILED:
            case SERVICE_NOT_USABLE:
                return null;
            case BINDING:
            case DISCONNECTED:
                return waitForConnection(j, timeUnit);
            case CONNECTED:
                return currentServiceState.compatService;
            case REBIND_REQUIRED:
                requestServiceBind();
                return waitForConnection(j, timeUnit);
            default:
                throw new IllegalStateException("Unknown state = " + currentServiceState.state);
        }
    }

    void swapServiceContextAndNotify(ServiceContext serviceContext) {
        LOG.atInfo(String.format("State changed: %s -> %s", this.serviceContext.state, serviceContext.state));
        this.serviceContext = serviceContext;
        CountDownLatch andClearConnectedCondition = getAndClearConnectedCondition();
        if (andClearConnectedCondition != null) {
            andClearConnectedCondition.countDown();
        }
    }
}
