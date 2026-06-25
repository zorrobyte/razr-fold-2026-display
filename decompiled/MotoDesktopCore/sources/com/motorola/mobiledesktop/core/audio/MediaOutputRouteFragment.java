package com.motorola.mobiledesktop.core.audio;

import K.c;
import X.v0;
import Y.A;
import Y.B;
import Y.i;
import Y.t;
import Y.x;
import Y.y;
import Y.z;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.AbstractComponentCallbacksC0098j;
import com.motorola.mobiledesktop.core.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MediaOutputRouteFragment extends AbstractComponentCallbacksC0098j {
    private static final int LISTVIEW_MAX_ITEM_NUMBER_HORIZONTAL = 2;
    private static final int LISTVIEW_MAX_ITEM_NUMBER_VERTICAL = 5;
    private static final int MSG_UPDATE_LISTVIEW = 1000;
    private static final String SETTINGS_BLUETOOTH_ADD_FRAGMENT_NAME = "com.android.settings.bluetooth.BluetoothPairingDetail";
    private static final String SETTINGS_PACKAGE_NAME = "com.android.settings";
    private static final String SETTINGS_SHOW_FRAGMENT = ":settings:show_fragment";
    private static final String SETTINGS_SUB_ACTIVITY_NAME = "com.android.settings.SubSettings";
    private static final String TAG = "MediaOutputRouteFragment";
    private static final boolean debug = true;
    private View icon;
    private i mAudioOutputRouteControl;
    private Handler mHandler;
    private MediaDeviceListAdapter mListAdapter;
    private ListView mMediaDeviceListView;
    private LinearLayout mPairNewDevice;
    private TextView subTitle;
    private TextView title;
    private List<t> mMediaDevices = new ArrayList();
    private final Object mMediaDevicesLock = new Object();
    private int mCurrentSelect = 0;
    private int mConfiguration = 0;
    private boolean mIsInCallMode = false;
    private final B mMediaDeviceUpdateCallback = new B(this);

    private void getMediaDevices() {
        synchronized (this.mMediaDevicesLock) {
            this.mMediaDevices.clear();
            this.mMediaDevices.addAll(this.mAudioOutputRouteControl.f395h);
        }
    }

    private void resetFragmentLayout() {
        Window window = getActivity().getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
    }

    private void setListViewMaxHeight(MediaDeviceListAdapter mediaDeviceListAdapter) {
        int count = mediaDeviceListAdapter.getCount();
        if (count <= 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = null;
        View view = mediaDeviceListAdapter.getView(this.mCurrentSelect, null, this.mMediaDeviceListView);
        view.measure(0, 0);
        int measuredHeight = view.getMeasuredHeight();
        if (count == 1) {
            this.mMediaDeviceListView.setLayoutParams(new LinearLayout.LayoutParams(-1, measuredHeight));
            return;
        }
        View view2 = mediaDeviceListAdapter.getView(this.mCurrentSelect == 0 ? 1 : 0, null, this.mMediaDeviceListView);
        view2.measure(0, 0);
        int measuredHeight2 = view2.getMeasuredHeight();
        int i2 = this.mConfiguration != 2 ? 5 : 2;
        if (count <= i2) {
            layoutParams = new LinearLayout.LayoutParams(-1, ((count - 1) * measuredHeight2) + measuredHeight);
        } else if (count > i2) {
            layoutParams = new LinearLayout.LayoutParams(-1, ((i2 - 1) * measuredHeight2) + measuredHeight);
        }
        this.mMediaDeviceListView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateList() {
        v0.a(TAG, "mCurrentSelect = " + this.mCurrentSelect);
        TextView textView = this.title;
        if (textView != null && this.icon != null) {
            if (this.mIsInCallMode) {
                textView.setText(R.string.call_output_route_fragment_title);
                this.subTitle.setText(R.string.call_output_route_fragment_sub_title);
                this.icon.setBackgroundResource(R.drawable.ic_call_title);
            } else {
                textView.setText(R.string.media_output_route_fragment_title);
                this.subTitle.setText(R.string.media_output_route_fragment_sub_title);
                this.icon.setBackgroundResource(R.drawable.ic_media_title);
            }
        }
        synchronized (this.mMediaDevicesLock) {
            try {
                int i2 = this.mCurrentSelect;
                if (i2 >= 0) {
                    this.mListAdapter.changeSelected(i2, false);
                }
                if (!this.mListAdapter.IsInTrackingTouch()) {
                    this.mListAdapter.notifyDataSetChanged();
                }
                setListViewMaxHeight(this.mListAdapter);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mConfiguration = configuration.orientation;
        resetFragmentLayout();
        updateList();
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        v0.a(TAG, "onCreate");
        this.mHandler = new x(0, this);
        i iVarU = i.u(getActivity());
        this.mAudioOutputRouteControl = iVarU;
        B b2 = this.mMediaDeviceUpdateCallback;
        iVarU.getClass();
        Objects.requireNonNull(b2, "callback must not be null");
        iVarU.f391d.add(b2);
        this.mAudioOutputRouteControl.x();
        getMediaDevices();
        MediaDeviceListAdapter mediaDeviceListAdapter = new MediaDeviceListAdapter(getActivity(), R.layout.view_media_output_item, this.mMediaDevices);
        this.mListAdapter = mediaDeviceListAdapter;
        int i2 = this.mAudioOutputRouteControl.f397j;
        if (i2 >= 0) {
            mediaDeviceListAdapter.changeSelected(i2, false);
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_media_output_route, (ViewGroup) null);
        ListView listView = (ListView) viewInflate.findViewById(R.id.media_device_list);
        this.mMediaDeviceListView = listView;
        listView.setAdapter((ListAdapter) this.mListAdapter);
        int i2 = 0;
        this.mMediaDeviceListView.setOnItemClickListener(new y(i2, this));
        this.mMediaDeviceListView.setOnItemSelectedListener(new z(i2, this));
        ((TextView) viewInflate.findViewById(R.id.done)).setOnClickListener(new c(1, this));
        this.title = (TextView) viewInflate.findViewById(R.id.audio_route_title);
        this.subTitle = (TextView) viewInflate.findViewById(R.id.audio_route_sub_title);
        this.icon = viewInflate.findViewById(R.id.audio_route_icon);
        if (this.mAudioOutputRouteControl.f399l) {
            this.title.setText(R.string.call_output_route_fragment_title);
            this.subTitle.setText(R.string.call_output_route_fragment_sub_title);
            this.icon.setBackgroundResource(R.drawable.ic_call_title);
        } else {
            this.title.setText(R.string.media_output_route_fragment_title);
            this.subTitle.setText(R.string.media_output_route_fragment_sub_title);
            this.icon.setBackgroundResource(R.drawable.ic_media_title);
        }
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.pair_new_device);
        this.mPairNewDevice = linearLayout;
        linearLayout.setOnClickListener(new A());
        return viewInflate;
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onDestroy() {
        super.onDestroy();
        v0.a(TAG, "onDestroy");
        i iVar = this.mAudioOutputRouteControl;
        B b2 = this.mMediaDeviceUpdateCallback;
        iVar.getClass();
        Objects.requireNonNull(b2, "callback must not be null");
        iVar.f391d.remove(b2);
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onStart() {
        super.onStart();
        v0.a(TAG, "onStart");
        resetFragmentLayout();
    }

    public void setConfiguration(int i2) {
        this.mConfiguration = i2;
    }
}
