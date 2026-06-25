package com.motorola.taskbar.panel;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class VolumePanelLayout extends LinearLayout {
    private ContentObserver mContentObserver;
    private Context mCurrentUserContext;
    protected TextView mDone;
    private VolumeChannelInfo mFocusVolumeChannelInfo;
    private HandlerThread mHandlerThread;
    private boolean mIsReInflateChannelsLayoutNeeded;
    private boolean mIsTrackingTouch;
    private int mRetryInflateCount;
    protected TextView mSubTitleView;
    protected ImageView mTitleIconView;
    protected TextView mTitleView;
    private Handler mUIHandler;
    private CurrentUserTracker mUserTracker;
    protected ViewGroup mVolumeChannelsGroup;
    private WorkBgHandle mWorkBgHandle;
    public static final Uri CONTENT_URI_DEVICES = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/Device");
    private static final Uri CONTENT_URI_UPDATE_FOCUS = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateFocus");
    private static final Uri CONTENT_URI_UPDATE_VOLUME = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateVolume");
    public static final Uri CONTENT_URI_SHOW_AUDIO_ICON = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/ShowAudioIcon");
    public static final Uri CONTENT_URI_TOAST_USE_CLIENT_MIC = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/ToastUseClientMic");
    private static final Uri CONTENT_URI_UPDATE_CHECKBOX_STATE = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/UpdateCheckboxState");

    class VolumeChannelInfo {
        final CheckBox mCheckBox;
        final int mCheckBoxState;
        final String mCheckboxInfo;
        final int mCurrentVolume;
        final TextView mDeviceNameView;
        final String mDeviceSubInfo;
        final TextView mDeviceSubInfoView;
        final int mIconId;
        final int mIndex;
        boolean mIsForceDevice;
        final ImageView mItemIconView;
        final ViewGroup mItemView;
        final int mMaxVolume;
        final String mName;
        final SeekBar mSeekBar;

        public VolumeChannelInfo(Cursor cursor, int i) {
            this.mIndex = i;
            int i2 = cursor.getInt(cursor.getColumnIndex("DeviceIconId"));
            this.mIconId = i2;
            String string = cursor.getString(cursor.getColumnIndex("DeviceName"));
            this.mName = string;
            String string2 = cursor.getString(cursor.getColumnIndex("DeviceSubInfo"));
            this.mDeviceSubInfo = string2;
            this.mIsForceDevice = cursor.getInt(cursor.getColumnIndex("isFocusDevice")) == 1;
            int i3 = cursor.getInt(cursor.getColumnIndex("MaxVolume"));
            this.mMaxVolume = i3;
            int i4 = cursor.getInt(cursor.getColumnIndex("CurrentVolume"));
            this.mCurrentVolume = i4;
            String string3 = cursor.getString(cursor.getColumnIndex("DeviceCheckboxInfo"));
            this.mCheckboxInfo = string3;
            int i5 = cursor.getInt(cursor.getColumnIndex("DeviceCheckboxState"));
            this.mCheckBoxState = i5;
            ViewGroup viewGroup = (ViewGroup) LinearLayout.inflate(VolumePanelLayout.this.getContext(), VolumePanelLayout.this.mediaOutputItem(), null);
            this.mItemView = viewGroup;
            ImageView imageView = (ImageView) viewGroup.findViewById(R$id.media_device_icon);
            this.mItemIconView = imageView;
            TextView textView = (TextView) viewGroup.findViewById(R$id.media_device_name);
            this.mDeviceNameView = textView;
            SeekBar seekBar = (SeekBar) viewGroup.findViewById(R$id.media_device_seek_bar);
            this.mSeekBar = seekBar;
            TextView textView2 = (TextView) viewGroup.findViewById(R$id.media_sub_info);
            this.mDeviceSubInfoView = textView2;
            CheckBox checkBox = (CheckBox) viewGroup.findViewById(R$id.media_device_checkbox);
            this.mCheckBox = checkBox;
            Icon iconCreateWithResource = Icon.createWithResource("com.motorola.mobiledesktop.core", i2);
            iconCreateWithResource.setTint(VolumePanelLayout.this.itemIconColor());
            imageView.setImageDrawable(iconCreateWithResource.loadDrawable(VolumePanelLayout.this.getContext()));
            textView.setText(string);
            textView2.setText(TextUtils.isEmpty(string2) ? "" : string2);
            seekBar.setMax(i3);
            boolean z = false;
            seekBar.setMin(0);
            seekBar.setProgress(i4);
            if (this.mIsForceDevice) {
                seekBar.setVisibility(0);
                imageView.setActivated(true);
                textView2.setVisibility(TextUtils.isEmpty(string2) ? 8 : 0);
                checkBox.setVisibility(TextUtils.isEmpty(string3) ? 8 : 0);
                z = false;
            } else {
                imageView.setActivated(false);
                seekBar.setVisibility(8);
                textView2.setVisibility(8);
            }
            viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.panel.VolumePanelLayout$VolumeChannelInfo$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.motorola.taskbar.panel.VolumePanelLayout.VolumeChannelInfo.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i6, boolean z2) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DeviceIndex", Integer.valueOf(VolumeChannelInfo.this.mIndex));
                    contentValues.put("CurrentVolume", Integer.valueOf(i6));
                    VolumePanelLayout.this.mWorkBgHandle.removeMessages(3);
                    VolumePanelLayout.this.mWorkBgHandle.sendMessage(VolumePanelLayout.this.mWorkBgHandle.obtainMessage(3, contentValues));
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                    VolumePanelLayout.this.mIsTrackingTouch = true;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                    VolumePanelLayout.this.mIsTrackingTouch = false;
                    if (VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded) {
                        VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                    }
                }
            });
            checkBox.setChecked(i5 == 1 ? true : z);
            checkBox.setText(string3);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.motorola.taskbar.panel.VolumePanelLayout.VolumeChannelInfo.2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DeviceCheckboxUpdateState", Boolean.valueOf(z2));
                    VolumePanelLayout.this.mWorkBgHandle.removeMessages(4);
                    VolumePanelLayout.this.mWorkBgHandle.sendMessage(VolumePanelLayout.this.mWorkBgHandle.obtainMessage(4, contentValues));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            if (VolumePanelLayout.this.mFocusVolumeChannelInfo == null || VolumePanelLayout.this.mFocusVolumeChannelInfo != this) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("DeviceIndex", Integer.valueOf(this.mIndex));
                VolumePanelLayout.this.mWorkBgHandle.removeMessages(2);
                VolumePanelLayout.this.mWorkBgHandle.sendMessage(VolumePanelLayout.this.mWorkBgHandle.obtainMessage(2, contentValues));
                if (VolumePanelLayout.this.mFocusVolumeChannelInfo != null) {
                    VolumePanelLayout.this.mFocusVolumeChannelInfo.setForce(false);
                }
                setForce(true);
                VolumePanelLayout.this.mFocusVolumeChannelInfo = this;
            }
        }

        public void setForce(boolean z) {
            if (this.mIsForceDevice == z) {
                return;
            }
            this.mIsForceDevice = z;
            if (z) {
                this.mItemIconView.setActivated(true);
                this.mSeekBar.setVisibility(0);
            } else {
                this.mItemIconView.setActivated(false);
                this.mSeekBar.setVisibility(8);
            }
            ViewGroup.LayoutParams layoutParams = this.mItemView.getLayoutParams();
            if (this.mIsForceDevice) {
                this.mItemView.setMinimumHeight(VolumePanelLayout.this.getContext().getResources().getDimensionPixelSize(R$dimen.media_route_list_view_select_item_height));
            } else {
                this.mItemView.setMinimumHeight(VolumePanelLayout.this.getContext().getResources().getDimensionPixelSize(R$dimen.media_route_list_view_no_select_item_height));
            }
            this.mItemView.setLayoutParams(layoutParams);
        }
    }

    class WorkBgHandle extends Handler {
        public WorkBgHandle(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                VolumePanelLayout.this.reLoadChannelsForLayoutThenReInflateChannelsLayout();
                return;
            }
            if (i == 2) {
                VolumePanelLayout.this.mCurrentUserContext.getContentResolver().update(VolumePanelLayout.CONTENT_URI_UPDATE_FOCUS, (ContentValues) message.obj, null, null);
            } else if (i == 3) {
                VolumePanelLayout.this.mCurrentUserContext.getContentResolver().update(VolumePanelLayout.CONTENT_URI_UPDATE_VOLUME, (ContentValues) message.obj, null, null);
            } else {
                if (i != 4) {
                    return;
                }
                VolumePanelLayout.this.mCurrentUserContext.getContentResolver().update(VolumePanelLayout.CONTENT_URI_UPDATE_CHECKBOX_STATE, (ContentValues) message.obj, null, null);
            }
        }
    }

    public VolumePanelLayout(Context context) {
        super(context);
        this.mRetryInflateCount = 0;
        this.mIsTrackingTouch = false;
        this.mIsReInflateChannelsLayoutNeeded = false;
        this.mUIHandler = new Handler();
        this.mContentObserver = new ContentObserver(this.mUIHandler) { // from class: com.motorola.taskbar.panel.VolumePanelLayout.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (VolumePanelLayout.this.mIsTrackingTouch) {
                    VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded = true;
                } else {
                    VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                }
            }
        };
    }

    public VolumePanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRetryInflateCount = 0;
        this.mIsTrackingTouch = false;
        this.mIsReInflateChannelsLayoutNeeded = false;
        this.mUIHandler = new Handler();
        this.mContentObserver = new ContentObserver(this.mUIHandler) { // from class: com.motorola.taskbar.panel.VolumePanelLayout.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (VolumePanelLayout.this.mIsTrackingTouch) {
                    VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded = true;
                } else {
                    VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                }
            }
        };
    }

    public VolumePanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRetryInflateCount = 0;
        this.mIsTrackingTouch = false;
        this.mIsReInflateChannelsLayoutNeeded = false;
        this.mUIHandler = new Handler();
        this.mContentObserver = new ContentObserver(this.mUIHandler) { // from class: com.motorola.taskbar.panel.VolumePanelLayout.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (VolumePanelLayout.this.mIsTrackingTouch) {
                    VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded = true;
                } else {
                    VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                }
            }
        };
    }

    public VolumePanelLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRetryInflateCount = 0;
        this.mIsTrackingTouch = false;
        this.mIsReInflateChannelsLayoutNeeded = false;
        this.mUIHandler = new Handler();
        this.mContentObserver = new ContentObserver(this.mUIHandler) { // from class: com.motorola.taskbar.panel.VolumePanelLayout.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (VolumePanelLayout.this.mIsTrackingTouch) {
                    VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded = true;
                } else {
                    VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reLoadChannelsForLayoutThenReInflateChannelsLayout$0(String str, String str2, List list) {
        this.mVolumeChannelsGroup.removeAllViews();
        if (!TextUtils.isEmpty(str)) {
            this.mTitleView.setText(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            this.mSubTitleView.setText(str2);
        }
        ImageView imageView = this.mTitleIconView;
        if (imageView != null) {
            imageView.getDrawable().setTint(titleIconColor());
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            this.mVolumeChannelsGroup.addView(((VolumeChannelInfo) pair.first).mItemView, (ViewGroup.LayoutParams) pair.second);
            Object obj = pair.first;
            if (((VolumeChannelInfo) obj).mIsForceDevice) {
                this.mFocusVolumeChannelInfo = (VolumeChannelInfo) obj;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reLoadChannelsForLayoutThenReInflateChannelsLayout() {
        int i = 0;
        this.mIsReInflateChannelsLayoutNeeded = false;
        final String string = null;
        Cursor cursorQuery = this.mCurrentUserContext.getContentResolver().query(CONTENT_URI_DEVICES, null, null, null);
        if (cursorQuery == null) {
            Log.e("VolumePanelLayout", "reInflateChannelsLayout cursor is null");
            return;
        }
        if (!cursorQuery.moveToFirst()) {
            cursorQuery.close();
            Log.e("VolumePanelLayout", "reInflateChannelsLayout cursor is empty, request reinflate mRetryInflateCount = " + this.mRetryInflateCount);
            int i2 = this.mRetryInflateCount + 1;
            this.mRetryInflateCount = i2;
            if (i2 < 3) {
                requestReInflateChannelsLayout(100L);
                return;
            }
            return;
        }
        this.mRetryInflateCount = 0;
        final ArrayList arrayList = new ArrayList();
        final String string2 = null;
        do {
            if (i == 0) {
                string = cursorQuery.getString(cursorQuery.getColumnIndex("Title"));
                string2 = cursorQuery.getString(cursorQuery.getColumnIndex("SubTitle"));
                int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex("TitleIconId"));
                if (i3 > 0) {
                    Icon iconCreateWithResource = Icon.createWithResource("com.motorola.mobiledesktop.core", i3);
                    iconCreateWithResource.setTint(getResources().getColor(R$color.media_route_title_icon_color));
                    iconCreateWithResource.loadDrawable(getContext());
                }
            }
            VolumeChannelInfo volumeChannelInfo = new VolumeChannelInfo(cursorQuery, i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            if (volumeChannelInfo.mIsForceDevice) {
                volumeChannelInfo.mItemView.setMinimumHeight(getContext().getResources().getDimensionPixelSize(R$dimen.media_route_list_view_select_item_height));
            } else {
                volumeChannelInfo.mItemView.setMinimumHeight(getContext().getResources().getDimensionPixelSize(R$dimen.media_route_list_view_no_select_item_height));
            }
            arrayList.add(new Pair(volumeChannelInfo, layoutParams));
            i++;
        } while (cursorQuery.moveToNext());
        cursorQuery.close();
        this.mUIHandler.post(new Runnable() { // from class: com.motorola.taskbar.panel.VolumePanelLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$reLoadChannelsForLayoutThenReInflateChannelsLayout$0(string, string2, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserContext(int i) {
        this.mCurrentUserContext = getContext().createContextAsUser(UserHandle.of(i), 0);
    }

    protected int itemIconColor() {
        return getResources().getColor(R$color.media_panel_icon_color);
    }

    protected int mediaOutputItem() {
        return R$layout.view_media_output_item;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        HandlerThread handlerThread = new HandlerThread("VolumePanelLayout");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mWorkBgHandle = new WorkBgHandle(this.mHandlerThread.getLooper());
        this.mRetryInflateCount = 0;
        this.mUserTracker.startTracking();
        updateUserContext(this.mUserTracker.getCurrentUserId());
        try {
            this.mCurrentUserContext.getContentResolver().registerContentObserver(CONTENT_URI_DEVICES, true, this.mContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestReInflateChannelsLayout(0L);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mCurrentUserContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mUserTracker.stopTracking();
        this.mUIHandler.removeCallbacksAndMessages(null);
        this.mHandlerThread.quit();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mVolumeChannelsGroup = (ViewGroup) findViewById(R$id.volume_channels);
        this.mTitleView = (TextView) findViewById(R$id.title);
        this.mSubTitleView = (TextView) findViewById(R$id.sub_title);
        this.mTitleIconView = (ImageView) findViewById(R$id.title_icon);
        this.mDone = (TextView) findViewById(R$id.done);
        this.mUserTracker = new CurrentUserTracker((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)) { // from class: com.motorola.taskbar.panel.VolumePanelLayout.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i) {
                boolean zIsAttachedToWindow = VolumePanelLayout.this.isAttachedToWindow();
                if (zIsAttachedToWindow) {
                    VolumePanelLayout.this.mCurrentUserContext.getContentResolver().unregisterContentObserver(VolumePanelLayout.this.mContentObserver);
                }
                VolumePanelLayout.this.updateUserContext(i);
                if (zIsAttachedToWindow) {
                    VolumePanelLayout.this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_DEVICES, true, VolumePanelLayout.this.mContentObserver);
                    if (VolumePanelLayout.this.mIsTrackingTouch) {
                        VolumePanelLayout.this.mIsReInflateChannelsLayoutNeeded = true;
                    } else {
                        VolumePanelLayout.this.requestReInflateChannelsLayout(0L);
                    }
                }
            }
        };
    }

    public void onUiModeChanged() {
        if (this.mVolumeChannelsGroup == null) {
            return;
        }
        setBackground(getContext().getDrawable(R$drawable.taskbar_floating_panel_background));
        this.mTitleView.setTextColor(getContext().getColor(R$color.media_route_title_color));
        this.mSubTitleView.setTextColor(getContext().getColor(R$color.media_route_subtitle_color));
        this.mDone.setTextColor(getContext().getColor(R$color.media_route_done_color));
        this.mTitleIconView.getDrawable().setTint(titleIconColor());
        if (isAttachedToWindow()) {
            requestReInflateChannelsLayout(0L);
        }
    }

    protected void requestReInflateChannelsLayout(long j) {
        if (this.mWorkBgHandle.hasMessages(1)) {
            return;
        }
        this.mWorkBgHandle.sendEmptyMessageDelayed(1, j);
    }

    protected int titleIconColor() {
        return getResources().getColor(R$color.media_route_title_icon_color);
    }
}
