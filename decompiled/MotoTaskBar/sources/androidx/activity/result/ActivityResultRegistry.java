package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.os.BundleCompat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ActivityResultRegistry.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    private static final Companion Companion = new Companion(null);
    private final Map rcToKey = new LinkedHashMap();
    private final Map keyToRc = new LinkedHashMap();
    private final Map keyToLifecycleContainers = new LinkedHashMap();
    private final List launchedKeys = new ArrayList();
    private final transient Map keyToCallback = new LinkedHashMap();
    private final Map parsedPendingResults = new LinkedHashMap();
    private final Bundle pendingResults = new Bundle();

    /* JADX INFO: compiled from: ActivityResultRegistry.kt */
    final class CallbackAndContract {
        private final ActivityResultCallback callback;
        private final ActivityResultContract contract;

        public CallbackAndContract(ActivityResultCallback activityResultCallback, ActivityResultContract activityResultContract) {
            activityResultCallback.getClass();
            activityResultContract.getClass();
            this.callback = activityResultCallback;
            this.contract = activityResultContract;
        }

        public final ActivityResultCallback getCallback() {
            return this.callback;
        }

        public final ActivityResultContract getContract() {
            return this.contract;
        }
    }

    /* JADX INFO: compiled from: ActivityResultRegistry.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final void bindRcKey(int i, String str) {
        this.rcToKey.put(Integer.valueOf(i), str);
        this.keyToRc.put(str, Integer.valueOf(i));
    }

    private final void doDispatch(String str, int i, Intent intent, CallbackAndContract callbackAndContract) {
        if ((callbackAndContract != null ? callbackAndContract.getCallback() : null) == null || !this.launchedKeys.contains(str)) {
            this.parsedPendingResults.remove(str);
            this.pendingResults.putParcelable(str, new ActivityResult(i, intent));
        } else {
            callbackAndContract.getCallback().onActivityResult(callbackAndContract.getContract().parseResult(i, intent));
            this.launchedKeys.remove(str);
        }
    }

    private final int generateRandomNumber() {
        for (Number number : SequencesKt.generateSequence(new Function0() { // from class: androidx.activity.result.ActivityResultRegistry.generateRandomNumber.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Integer mo2224invoke() {
                return Integer.valueOf(Random.Default.nextInt(2147418112) + 65536);
            }
        })) {
            if (!this.rcToKey.containsKey(Integer.valueOf(number.intValue()))) {
                return number.intValue();
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final void registerKey(String str) {
        if (((Integer) this.keyToRc.get(str)) != null) {
            return;
        }
        bindRcKey(generateRandomNumber(), str);
    }

    public final boolean dispatchResult(int i, int i2, Intent intent) {
        String str = (String) this.rcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        doDispatch(str, i2, intent, (CallbackAndContract) this.keyToCallback.get(str));
        return true;
    }

    public final boolean dispatchResult(int i, Object obj) {
        String str = (String) this.rcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        CallbackAndContract callbackAndContract = (CallbackAndContract) this.keyToCallback.get(str);
        if ((callbackAndContract != null ? callbackAndContract.getCallback() : null) == null) {
            this.pendingResults.remove(str);
            this.parsedPendingResults.put(str, obj);
            return true;
        }
        ActivityResultCallback callback = callbackAndContract.getCallback();
        callback.getClass();
        if (!this.launchedKeys.remove(str)) {
            return true;
        }
        callback.onActivityResult(obj);
        return true;
    }

    public abstract void onLaunch(int i, ActivityResultContract activityResultContract, Object obj, ActivityOptionsCompat activityOptionsCompat);

    public final void onRestoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
        ArrayList<String> stringArrayList = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
        if (stringArrayList == null || integerArrayList == null) {
            return;
        }
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
        if (stringArrayList2 != null) {
            this.launchedKeys.addAll(stringArrayList2);
        }
        Bundle bundle2 = bundle.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT");
        if (bundle2 != null) {
            this.pendingResults.putAll(bundle2);
        }
        int size = stringArrayList.size();
        for (int i = 0; i < size; i++) {
            String str = stringArrayList.get(i);
            if (this.keyToRc.containsKey(str)) {
                Integer num = (Integer) this.keyToRc.remove(str);
                if (!this.pendingResults.containsKey(str)) {
                    TypeIntrinsics.asMutableMap(this.rcToKey).remove(num);
                }
            }
            Integer num2 = integerArrayList.get(i);
            num2.getClass();
            int iIntValue = num2.intValue();
            String str2 = stringArrayList.get(i);
            str2.getClass();
            bindRcKey(iIntValue, str2);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.getClass();
        bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(this.keyToRc.values()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(this.keyToRc.keySet()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(this.launchedKeys));
        bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", new Bundle(this.pendingResults));
    }

    public final ActivityResultLauncher register(final String str, final ActivityResultContract activityResultContract, ActivityResultCallback activityResultCallback) {
        str.getClass();
        activityResultContract.getClass();
        activityResultCallback.getClass();
        registerKey(str);
        this.keyToCallback.put(str, new CallbackAndContract(activityResultCallback, activityResultContract));
        if (this.parsedPendingResults.containsKey(str)) {
            Object obj = this.parsedPendingResults.get(str);
            this.parsedPendingResults.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) BundleCompat.getParcelable(this.pendingResults, str, ActivityResult.class);
        if (activityResult != null) {
            this.pendingResults.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
        }
        return new ActivityResultLauncher() { // from class: androidx.activity.result.ActivityResultRegistry.register.3
            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(Object obj2, ActivityOptionsCompat activityOptionsCompat) throws Exception {
                Object obj3 = ActivityResultRegistry.this.keyToRc.get(str);
                ActivityResultContract activityResultContract2 = activityResultContract;
                if (obj3 != null) {
                    int iIntValue = ((Number) obj3).intValue();
                    ActivityResultRegistry.this.launchedKeys.add(str);
                    try {
                        ActivityResultRegistry.this.onLaunch(iIntValue, activityResultContract, obj2, activityOptionsCompat);
                        return;
                    } catch (Exception e) {
                        ActivityResultRegistry.this.launchedKeys.remove(str);
                        throw e;
                    }
                }
                throw new IllegalStateException(("Attempting to launch an unregistered ActivityResultLauncher with contract " + activityResultContract2 + " and input " + obj2 + ". You must ensure the ActivityResultLauncher is registered before calling launch().").toString());
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.unregister$activity_release(str);
            }
        };
    }

    public final void unregister$activity_release(String str) {
        Integer num;
        str.getClass();
        if (!this.launchedKeys.contains(str) && (num = (Integer) this.keyToRc.remove(str)) != null) {
            this.rcToKey.remove(num);
        }
        this.keyToCallback.remove(str);
        if (this.parsedPendingResults.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + this.parsedPendingResults.get(str));
            this.parsedPendingResults.remove(str);
        }
        if (this.pendingResults.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + ((ActivityResult) BundleCompat.getParcelable(this.pendingResults, str, ActivityResult.class)));
            this.pendingResults.remove(str);
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.keyToLifecycleContainers.get(str));
    }
}
