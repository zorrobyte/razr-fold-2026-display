package com.motorola.laptoppanel.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LaptopScreenshotHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopScreenshotHelper {
    private final Context appContext;
    private ServiceConnection connRef;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopScreenshotHelper.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LaptopScreenshotHelper(Context context) {
        context.getClass();
        this.appContext = context;
    }

    public final boolean takeScreenshot() {
        if (this.connRef != null) {
            return false;
        }
        Intent component = new Intent().setComponent(new ComponentName("com.android.systemui", "com.android.systemui.screenshot.ScreenshotInputService"));
        component.getClass();
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.motorola.laptoppanel.activity.LaptopScreenshotHelper$takeScreenshot$conn$1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                componentName.getClass();
                iBinder.getClass();
                Log.d("LaptopScreenshotHelper", "SYSUI_SCREENSHOT_SERVICE onServiceConnected");
                Messenger messenger = new Messenger(iBinder);
                final Looper mainLooper = Looper.getMainLooper();
                final LaptopScreenshotHelper laptopScreenshotHelper = this.this$0;
                Handler handler = new Handler(mainLooper) { // from class: com.motorola.laptoppanel.activity.LaptopScreenshotHelper$takeScreenshot$conn$1$onServiceConnected$handler$1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        message.getClass();
                        ServiceConnection serviceConnection2 = laptopScreenshotHelper.connRef;
                        LaptopScreenshotHelper$takeScreenshot$conn$1 laptopScreenshotHelper$takeScreenshot$conn$1 = this;
                        if (serviceConnection2 == laptopScreenshotHelper$takeScreenshot$conn$1) {
                            LaptopScreenshotHelper laptopScreenshotHelper2 = laptopScreenshotHelper;
                            try {
                                Result.Companion companion = Result.Companion;
                                laptopScreenshotHelper2.appContext.unbindService(laptopScreenshotHelper$takeScreenshot$conn$1);
                                Result.m2192constructorimpl(Unit.INSTANCE);
                            } catch (Throwable th) {
                                Result.Companion companion2 = Result.Companion;
                                Result.m2192constructorimpl(ResultKt.createFailure(th));
                            }
                            laptopScreenshotHelper.connRef = null;
                        }
                    }
                };
                Bundle bundle = new Bundle();
                bundle.putInt("extraType", 0);
                bundle.putBoolean("extraLongScreenshot", true);
                bundle.putInt("extraPartialScreenshot", 1);
                Message messageObtain = Message.obtain((Handler) null, 1);
                messageObtain.replyTo = new Messenger(handler);
                messageObtain.setData(bundle);
                messageObtain.obj = bundle;
                try {
                    Log.d("LaptopScreenshotHelper", "Sending message to screenshot service");
                    messenger.send(messageObtain);
                } catch (RemoteException e) {
                    Log.w("LaptopScreenshotHelper", "send() failed", e);
                    LaptopScreenshotHelper laptopScreenshotHelper2 = this.this$0;
                    try {
                        Result.Companion companion = Result.Companion;
                        laptopScreenshotHelper2.appContext.unbindService(this);
                        Result.m2192constructorimpl(Unit.INSTANCE);
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        Result.m2192constructorimpl(ResultKt.createFailure(th));
                    }
                    this.this$0.connRef = null;
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                componentName.getClass();
                this.this$0.connRef = null;
            }
        };
        boolean zBindService = this.appContext.bindService(component, serviceConnection, 65);
        if (zBindService) {
            this.connRef = serviceConnection;
        }
        return zBindService;
    }
}
