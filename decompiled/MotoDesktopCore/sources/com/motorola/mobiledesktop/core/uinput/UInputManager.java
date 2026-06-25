package com.motorola.mobiledesktop.core.uinput;

import X.v0;
import android.content.Context;
import android.os.IBinder;
import android.util.SparseArray;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class UInputManager {
    private static final String TAG = "UInputManager";
    private static UInputManager sInstance;
    private final Context mContext;
    private final SparseArray<IClientToken> mIClientTokens = new SparseArray<>();
    private final SparseArray<HashSet<Integer>> mOpenedFds = new SparseArray<>();
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.motorola.mobiledesktop.core.uinput.UInputManager.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(IBinder iBinder) {
            v0.b(UInputManager.TAG, "binderDied");
            synchronized (UInputManager.this.mIClientTokens) {
                int iKeyAt = 0;
                int i2 = 0;
                while (true) {
                    try {
                        if (i2 >= UInputManager.this.mIClientTokens.size()) {
                            break;
                        }
                        if (((IClientToken) UInputManager.this.mIClientTokens.valueAt(i2)).asBinder() == iBinder) {
                            iKeyAt = UInputManager.this.mIClientTokens.keyAt(i2);
                            break;
                        }
                        i2++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (iKeyAt != 0) {
                    UInputManager.this.cleanUpClient(iKeyAt);
                }
            }
        }
    };

    static {
        System.loadLibrary("CoreNative");
    }

    private UInputManager(Context context) {
        this.mContext = context.getApplicationContext();
        nativeInit();
    }

    private void addOpenedFd(int i2, int i3) {
        synchronized (this.mOpenedFds) {
            try {
                HashSet<Integer> hashSet = this.mOpenedFds.get(i2);
                if (hashSet == null) {
                    hashSet = new HashSet<>();
                    this.mOpenedFds.put(i2, hashSet);
                }
                hashSet.add(Integer.valueOf(i3));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanUpClient(int i2) {
        HashSet<Integer> hashSet;
        synchronized (this.mIClientTokens) {
            this.mIClientTokens.remove(i2);
        }
        synchronized (this.mOpenedFds) {
            hashSet = this.mOpenedFds.get(i2, null);
            this.mOpenedFds.remove(i2);
        }
        if (hashSet != null) {
            for (Integer num : hashSet) {
                synchronized (num) {
                    nativeDeviceClose(num.intValue());
                }
            }
        }
    }

    private Integer getFdObject(int i2, int i3) {
        synchronized (this.mOpenedFds) {
            try {
                HashSet<Integer> hashSet = this.mOpenedFds.get(i2);
                if (hashSet != null) {
                    for (Integer num : hashSet) {
                        if (num.intValue() == i3) {
                            return num;
                        }
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static UInputManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (UInputManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new UInputManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    private boolean isFdOpenedByClient(int i2, int i3) {
        synchronized (this.mOpenedFds) {
            try {
                HashSet<Integer> hashSet = this.mOpenedFds.get(i3);
                if (hashSet == null) {
                    return false;
                }
                return hashSet.contains(Integer.valueOf(i2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private native boolean nativeAbsSetup(int i2, AbsSetup absSetup);

    private native boolean nativeCreate(int i2, InputSetup inputSetup);

    private native boolean nativeDeviceClose(int i2);

    private native int nativeDeviceOpen();

    private native boolean nativeDoIoctl(int i2, int i3, int i4);

    private native boolean nativeDoStrIoctl(int i2, int i3, String str);

    private native boolean nativeEmit(int i2, InputEvent[] inputEventArr);

    private native boolean nativeEnable(int i2, int i3, char[] cArr);

    private native void nativeInit();

    private Integer removeOpenedFd(int i2, int i3) {
        Integer num;
        synchronized (this.mOpenedFds) {
            try {
                HashSet<Integer> hashSet = this.mOpenedFds.get(i2);
                num = null;
                if (hashSet != null) {
                    Iterator<Integer> it = hashSet.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Integer next = it.next();
                        if (next.intValue() == i3) {
                            num = next;
                            break;
                        }
                    }
                    hashSet.remove(Integer.valueOf(i3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return num;
    }

    public boolean absSetup(int i2, int i3, AbsSetup absSetup) {
        boolean zNativeAbsSetup;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeAbsSetup = nativeAbsSetup(i3, absSetup);
            }
            return zNativeAbsSetup;
        }
        v0.g(TAG, "absSetup error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public boolean create(int i2, int i3, InputSetup inputSetup) {
        boolean zNativeCreate;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeCreate = nativeCreate(i3, inputSetup);
            }
            return zNativeCreate;
        }
        v0.g(TAG, "create error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public boolean deviceClose(int i2, int i3) {
        boolean zNativeDeviceClose;
        Integer numRemoveOpenedFd = removeOpenedFd(i2, i3);
        if (numRemoveOpenedFd != null) {
            synchronized (numRemoveOpenedFd) {
                zNativeDeviceClose = nativeDeviceClose(i3);
            }
            return zNativeDeviceClose;
        }
        v0.g(TAG, "deviceClose error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public int deviceOpen(int i2, IClientToken iClientToken) {
        if (iClientToken == null) {
            return -1;
        }
        int iNativeDeviceOpen = nativeDeviceOpen();
        if (iNativeDeviceOpen != -1) {
            synchronized (this.mIClientTokens) {
                this.mIClientTokens.put(i2, iClientToken);
            }
            try {
                iClientToken.asBinder().linkToDeath(this.mDeathRecipient, 0);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            addOpenedFd(i2, iNativeDeviceOpen);
        }
        return iNativeDeviceOpen;
    }

    public boolean doIoctl(int i2, int i3, int i4, int i5) {
        boolean zNativeDoIoctl;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeDoIoctl = nativeDoIoctl(i3, i4, i5);
            }
            return zNativeDoIoctl;
        }
        v0.g(TAG, "doIoctl error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public boolean doStrIoctl(int i2, int i3, int i4, String str) {
        boolean zNativeDoStrIoctl;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeDoStrIoctl = nativeDoStrIoctl(i3, i4, str);
            }
            return zNativeDoStrIoctl;
        }
        v0.g(TAG, "doStrIoctl error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public boolean emit(int i2, int i3, InputEvent[] inputEventArr) {
        boolean zNativeEmit;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeEmit = nativeEmit(i3, inputEventArr);
            }
            return zNativeEmit;
        }
        v0.g(TAG, "emit error: client " + i2 + " doesn't open device " + i3);
        return false;
    }

    public boolean enable(int i2, int i3, int i4, char[] cArr) {
        boolean zNativeEnable;
        Integer fdObject = getFdObject(i2, i3);
        if (fdObject != null) {
            synchronized (fdObject) {
                zNativeEnable = nativeEnable(i3, i4, cArr);
            }
            return zNativeEnable;
        }
        v0.g(TAG, "enable error: client " + i2 + " doesn't open device " + i3);
        return false;
    }
}
