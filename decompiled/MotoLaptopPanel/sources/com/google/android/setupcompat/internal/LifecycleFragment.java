package com.google.android.setupcompat.internal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class LifecycleFragment extends Fragment {
    private static final Logger LOG;
    private static final String LOG_TAG;
    private long durationInNanos = 0;
    private OnFragmentLifecycleChangeListener lifecycleChangeListener;
    private MetricKey metricKey;
    private long startInNanos;

    public interface OnFragmentLifecycleChangeListener {
        void onStop(PersistableBundle persistableBundle);
    }

    static {
        String simpleName = LifecycleFragment.class.getSimpleName();
        LOG_TAG = simpleName;
        LOG = new Logger(simpleName);
    }

    public LifecycleFragment() {
        setRetainInstance(true);
    }

    public static LifecycleFragment attachNow(Activity activity, OnFragmentLifecycleChangeListener onFragmentLifecycleChangeListener) {
        FragmentManager fragmentManager;
        if (!WizardManagerHelper.isAnySetupWizard(activity.getIntent()) || (fragmentManager = activity.getFragmentManager()) == null || fragmentManager.isDestroyed()) {
            LOG.atDebug("Skip attach " + activity.getClass().getSimpleName() + " because it's not in SUW flow.");
            return null;
        }
        Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag("lifecycle_monitor");
        if (fragmentFindFragmentByTag == null) {
            LifecycleFragment lifecycleFragment = new LifecycleFragment();
            if (onFragmentLifecycleChangeListener != null) {
                lifecycleFragment.setOnFragmentLifecycleChangeListener(onFragmentLifecycleChangeListener);
            }
            try {
                fragmentManager.beginTransaction().add(lifecycleFragment, "lifecycle_monitor").commitNow();
                fragmentFindFragmentByTag = lifecycleFragment;
            } catch (IllegalStateException e) {
                LOG.e("Error occurred when attach to Activity:" + activity.getComponentName(), e);
            }
        } else {
            if (!(fragmentFindFragmentByTag instanceof LifecycleFragment)) {
                Log.wtf(LOG_TAG, activity.getClass().getSimpleName() + " Incorrect instance on lifecycle fragment.");
                return null;
            }
            LOG.atDebug("Find an existing fragment that belongs to " + activity.getClass().getSimpleName());
        }
        return (LifecycleFragment) fragmentFindFragmentByTag;
    }

    private void logScreenResume() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong("onScreenResume", System.nanoTime());
        SetupMetricsLogger.logCustomEvent(getActivity(), CustomEvent.create(MetricKey.get("ScreenActivity", getActivity()), persistableBundle));
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        LOG.atDebug("onAttach host=" + getActivity().getClass().getSimpleName());
        this.metricKey = MetricKey.get("ScreenDuration", getActivity());
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        LOG.atDebug("onDetach host=" + getActivity().getClass().getSimpleName());
        SetupMetricsLogger.logDuration(getActivity(), this.metricKey, TimeUnit.NANOSECONDS.toMillis(this.durationInNanos));
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        LOG.atDebug("onPause host=" + getActivity().getClass().getSimpleName());
        this.durationInNanos = this.durationInNanos + (ClockProvider.timeInNanos() - this.startInNanos);
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.startInNanos = ClockProvider.timeInNanos();
        LOG.atDebug("onResume host=" + getActivity().getClass().getSimpleName() + ", startInNanos=" + this.startInNanos);
        logScreenResume();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        long jNanoTime = System.nanoTime();
        LOG.atDebug("onStop host=" + getActivity().getClass().getSimpleName() + ", onStopTimestamp=" + jNanoTime);
        if (this.lifecycleChangeListener != null) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putLong("onScreenStop", jNanoTime);
            this.lifecycleChangeListener.onStop(persistableBundle);
        }
    }

    public void setOnFragmentLifecycleChangeListener(OnFragmentLifecycleChangeListener onFragmentLifecycleChangeListener) {
        if (onFragmentLifecycleChangeListener != null) {
            this.lifecycleChangeListener = onFragmentLifecycleChangeListener;
        }
    }
}
