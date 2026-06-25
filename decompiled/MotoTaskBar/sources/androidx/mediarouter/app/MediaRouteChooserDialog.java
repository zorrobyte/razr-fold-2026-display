package androidx.mediarouter.app;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.mediarouter.R$attr;
import androidx.mediarouter.R$id;
import androidx.mediarouter.R$layout;
import androidx.mediarouter.R$string;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediaRouteChooserDialog extends AppCompatDialog {
    private RouteAdapter mAdapter;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private RelativeLayout mEmptyView;
    private FrameLayout mFooterView;
    private final Handler mHandler;
    private long mLastUpdateTime;
    private ListView mListView;
    private FrameLayout mNoRoutesView;
    private final MediaRouter mRouter;
    private ArrayList mRoutes;
    private LinearLayout mSearchingRoutesView;
    private MediaRouteSelector mSelector;
    private TextView mTitleView;
    private FrameLayout mWifiWarningView;

    final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.dismiss();
        }
    }

    final class RouteAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
        private final Drawable mDefaultIcon;
        private final LayoutInflater mInflater;
        private final Drawable mSpeakerGroupIcon;
        private final Drawable mSpeakerIcon;
        private final Drawable mTvIcon;

        public RouteAdapter(Context context, List list) {
            super(context, 0, list);
            this.mInflater = LayoutInflater.from(context);
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R$attr.mediaRouteDefaultIconDrawable, R$attr.mediaRouteTvIconDrawable, R$attr.mediaRouteSpeakerIconDrawable, R$attr.mediaRouteSpeakerGroupIconDrawable});
            this.mDefaultIcon = AppCompatResources.getDrawable(context, typedArrayObtainStyledAttributes.getResourceId(0, 0));
            this.mTvIcon = AppCompatResources.getDrawable(context, typedArrayObtainStyledAttributes.getResourceId(1, 0));
            this.mSpeakerIcon = AppCompatResources.getDrawable(context, typedArrayObtainStyledAttributes.getResourceId(2, 0));
            this.mSpeakerGroupIcon = AppCompatResources.getDrawable(context, typedArrayObtainStyledAttributes.getResourceId(3, 0));
            typedArrayObtainStyledAttributes.recycle();
        }

        private Drawable getDefaultIconDrawable(MediaRouter.RouteInfo routeInfo) {
            int deviceType = routeInfo.getDeviceType();
            return deviceType != 1 ? deviceType != 2 ? routeInfo.isGroup() ? this.mSpeakerGroupIcon : this.mDefaultIcon : this.mSpeakerIcon : this.mTvIcon;
        }

        private Drawable getIconDrawable(MediaRouter.RouteInfo routeInfo) {
            Uri iconUri = routeInfo.getIconUri();
            if (iconUri != null) {
                try {
                    Drawable drawableCreateFromStream = Drawable.createFromStream(getContext().getContentResolver().openInputStream(iconUri), null);
                    if (drawableCreateFromStream != null) {
                        return drawableCreateFromStream;
                    }
                } catch (IOException e) {
                    Log.w("MediaRouteChooserDialog", "Failed to load " + iconUri, e);
                }
            }
            return getDefaultIconDrawable(routeInfo);
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(R$layout.mr_chooser_list_item, viewGroup, false);
            }
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            TextView textView = (TextView) view.findViewById(R$id.mr_chooser_route_name);
            TextView textView2 = (TextView) view.findViewById(R$id.mr_chooser_route_desc);
            textView.setText(routeInfo.getName());
            String description = routeInfo.getDescription();
            if ((routeInfo.getConnectionState() == 2 || routeInfo.getConnectionState() == 1) && !TextUtils.isEmpty(description)) {
                textView.setGravity(80);
                textView2.setVisibility(0);
                textView2.setText(description);
            } else {
                textView.setGravity(16);
                textView2.setVisibility(8);
                textView2.setText("");
            }
            view.setEnabled(routeInfo.isEnabled());
            ImageView imageView = (ImageView) view.findViewById(R$id.mr_chooser_route_icon);
            if (imageView != null) {
                imageView.setImageDrawable(getIconDrawable(routeInfo));
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return ((MediaRouter.RouteInfo) getItem(i)).isEnabled();
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            if (routeInfo.isEnabled()) {
                ImageView imageView = (ImageView) view.findViewById(R$id.mr_chooser_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R$id.mr_chooser_route_progress_bar);
                if (imageView != null && progressBar != null) {
                    imageView.setVisibility(8);
                    progressBar.setVisibility(0);
                }
                routeInfo.select();
            }
        }
    }

    final class RouteComparator implements Comparator {
        public static final RouteComparator sInstance = new RouteComparator();

        RouteComparator() {
        }

        @Override // java.util.Comparator
        public int compare(MediaRouter.RouteInfo routeInfo, MediaRouter.RouteInfo routeInfo2) {
            return routeInfo.getName().compareToIgnoreCase(routeInfo2.getName());
        }
    }

    public MediaRouteChooserDialog(Context context) {
        this(context, 0);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaRouteChooserDialog(Context context, int i) {
        Context contextCreateThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        super(contextCreateThemedDialogContext, MediaRouterThemeHelper.createThemedDialogStyle(contextCreateThemedDialogContext));
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mHandler = new Handler() { // from class: androidx.mediarouter.app.MediaRouteChooserDialog.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    MediaRouteChooserDialog.this.handleUpdateRoutes((List) message.obj);
                } else if (i2 == 2) {
                    MediaRouteChooserDialog.this.handleShowNoWifiWarning();
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    MediaRouteChooserDialog.this.handleShowNoRoutes();
                }
            }
        };
        this.mRouter = MediaRouter.getInstance(getContext());
        this.mCallback = new MediaRouterCallback();
    }

    private void updateViewForFindingDevices() {
        setTitle(R$string.mr_chooser_title);
        this.mTitleView.setVisibility(0);
        this.mListView.setVisibility(8);
        this.mEmptyView.setVisibility(0);
        this.mFooterView.setVisibility(8);
        this.mNoRoutesView.setVisibility(8);
        this.mSearchingRoutesView.setVisibility(0);
        this.mWifiWarningView.setVisibility(8);
    }

    private void updateViewForNoDevicesNoWifiHint() {
        setTitle(R$string.mr_chooser_title);
        this.mTitleView.setVisibility(0);
        this.mListView.setVisibility(8);
        this.mEmptyView.setVisibility(0);
        this.mFooterView.setVisibility(8);
        this.mNoRoutesView.setVisibility(8);
        this.mSearchingRoutesView.setVisibility(0);
        this.mWifiWarningView.setVisibility(0);
    }

    private void updateViewForNoRoutes() {
        setTitle(R$string.mr_chooser_zero_routes_found_title);
        this.mTitleView.setVisibility(0);
        this.mListView.setVisibility(8);
        this.mEmptyView.setVisibility(0);
        this.mFooterView.setVisibility(0);
        this.mNoRoutesView.setVisibility(0);
        this.mSearchingRoutesView.setVisibility(8);
        this.mWifiWarningView.setVisibility(8);
    }

    private void updateViewForShowingRoutes() {
        setTitle(R$string.mr_chooser_title);
        this.mTitleView.setVisibility(0);
        this.mListView.setVisibility(0);
        this.mEmptyView.setVisibility(8);
        this.mFooterView.setVisibility(8);
        this.mNoRoutesView.setVisibility(8);
        this.mSearchingRoutesView.setVisibility(8);
        this.mWifiWarningView.setVisibility(8);
    }

    void handleShowNoRoutes() {
        if (this.mRoutes.isEmpty()) {
            updateViewForState(3);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(1);
            this.mRouter.removeCallback(this.mCallback);
        }
    }

    void handleShowNoWifiWarning() {
        if (this.mRoutes.isEmpty()) {
            updateViewForState(2);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(3), 15000L);
        }
    }

    void handleUpdateRoutes(List list) {
        this.mLastUpdateTime = SystemClock.uptimeMillis();
        this.mRoutes.clear();
        this.mRoutes.addAll(list);
        this.mAdapter.notifyDataSetChanged();
        this.mHandler.removeMessages(3);
        this.mHandler.removeMessages(2);
        if (!list.isEmpty()) {
            updateViewForState(1);
            return;
        }
        updateViewForState(0);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2), TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mHandler.removeMessages(1);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2), TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.mr_chooser_dialog);
        this.mRoutes = new ArrayList();
        this.mAdapter = new RouteAdapter(getContext(), this.mRoutes);
        ListView listView = (ListView) findViewById(R$id.mr_chooser_list);
        this.mListView = listView;
        listView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this.mAdapter);
        this.mListView.setEmptyView(findViewById(R.id.empty));
        this.mTitleView = (TextView) findViewById(R$id.mr_chooser_title);
        this.mEmptyView = (RelativeLayout) findViewById(R$id.mr_empty_view);
        this.mSearchingRoutesView = (LinearLayout) findViewById(R$id.mr_chooser_searching);
        this.mNoRoutesView = (FrameLayout) findViewById(R$id.mr_chooser_no_routes);
        this.mWifiWarningView = (FrameLayout) findViewById(R$id.mr_chooser_wifi_warning);
        this.mFooterView = (FrameLayout) findViewById(R$id.mr_chooser_footer);
        TextView textView = (TextView) findViewById(R$id.mr_chooser_zero_routes_description);
        TextView textView2 = (TextView) findViewById(R$id.mr_chooser_wifi_warning_description);
        Button button = (Button) findViewById(R$id.mr_chooser_done_button);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        button.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteChooserDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.dismiss();
            }
        });
        updateLayout();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        super.onDetachedFromWindow();
    }

    public boolean onFilterRoute(MediaRouter.RouteInfo routeInfo) {
        return !routeInfo.isDefaultOrBluetooth() && routeInfo.isEnabled() && routeInfo.matchesSelector(this.mSelector);
    }

    public void onFilterRoutes(List list) {
        int size = list.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            if (!onFilterRoute((MediaRouter.RouteInfo) list.get(i))) {
                list.remove(i);
            }
            size = i;
        }
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            ArrayList arrayList = new ArrayList(this.mRouter.getRoutes());
            onFilterRoutes(arrayList);
            Collections.sort(arrayList, RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= 300) {
                handleUpdateRoutes(arrayList);
                return;
            }
            this.mHandler.removeMessages(1);
            Handler handler = this.mHandler;
            handler.sendMessageAtTime(handler.obtainMessage(1, arrayList), this.mLastUpdateTime + 300);
        }
    }

    public void setRouteSelector(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        if (this.mSelector.equals(mediaRouteSelector)) {
            return;
        }
        this.mSelector = mediaRouteSelector;
        if (this.mAttachedToWindow) {
            this.mRouter.removeCallback(this.mCallback);
            this.mRouter.addCallback(mediaRouteSelector, this.mCallback, 1);
        }
        refreshRoutes();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(int i) {
        this.mTitleView.setText(i);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        this.mTitleView.setText(charSequence);
    }

    void updateLayout() {
        getWindow().setLayout(MediaRouteDialogHelper.getDialogWidth(getContext()), -2);
    }

    void updateViewForState(int i) {
        if (i == 0) {
            updateViewForFindingDevices();
            return;
        }
        if (i == 1) {
            updateViewForShowingRoutes();
        } else if (i == 2) {
            updateViewForNoDevicesNoWifiHint();
        } else {
            if (i != 3) {
                return;
            }
            updateViewForNoRoutes();
        }
    }
}
