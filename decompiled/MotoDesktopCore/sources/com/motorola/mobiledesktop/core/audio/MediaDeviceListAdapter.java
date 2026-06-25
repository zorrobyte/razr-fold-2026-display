package com.motorola.mobiledesktop.core.audio;

import J.b;
import Y.r;
import Y.t;
import Y.u;
import Y.v;
import Y.w;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.motorola.mobiledesktop.core.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediaDeviceListAdapter extends ArrayAdapter<t> {
    private int mResourceId;
    private int mSelect;
    private t mSelectMediaDevice;
    private boolean mTrackingTouch;
    private boolean mUserClick;

    public MediaDeviceListAdapter(Context context, int i2, List<t> list) {
        super(context, i2, list);
        this.mSelect = 0;
        this.mTrackingTouch = false;
        this.mUserClick = false;
        this.mResourceId = i2;
    }

    public boolean IsInTrackingTouch() {
        return this.mTrackingTouch;
    }

    public void changeSelected(int i2, boolean z2) {
        if (i2 == this.mSelect && this.mUserClick == z2) {
            return;
        }
        synchronized (MediaDeviceListAdapter.class) {
            this.mSelect = i2;
            this.mUserClick = z2;
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        w wVar;
        synchronized (MediaDeviceListAdapter.class) {
            try {
                try {
                    t item = getItem(i2);
                    if (view == null) {
                        view = LayoutInflater.from(getContext()).inflate(this.mResourceId, viewGroup, false);
                        wVar = new w();
                        wVar.f449a = (ImageView) view.findViewById(R.id.media_device_icon);
                        wVar.f450b = (TextView) view.findViewById(R.id.media_device_name);
                        wVar.f451c = (SeekBar) view.findViewById(R.id.media_device_seek_bar);
                        wVar.f452d = (TextView) view.findViewById(R.id.media_device_select_item);
                        wVar.f453e = (TextView) view.findViewById(R.id.media_device_no_select_item);
                        wVar.f454f = (CheckBox) view.findViewById(R.id.media_device_checkbox);
                        view.setTag(wVar);
                    } else {
                        wVar = (w) view.getTag();
                    }
                    wVar.f449a.setImageResource(item.d());
                    wVar.f450b.setText(item.f());
                    if (this.mSelect == i2) {
                        String strA = r.N() ? item.a() : "";
                        if (strA == null || strA.isEmpty()) {
                            wVar.f454f.setVisibility(8);
                        } else {
                            wVar.f454f.setVisibility(0);
                            wVar.f454f.setText(strA);
                            wVar.f454f.setChecked(item.f446g);
                            wVar.f454f.setOnCheckedChangeListener(new u(this));
                        }
                        this.mSelectMediaDevice = item;
                        wVar.f451c.setVisibility(0);
                        wVar.f452d.setVisibility(0);
                        wVar.f453e.setVisibility(8);
                        wVar.f451c.setMax(item.e());
                        if (!this.mUserClick) {
                            new Thread(new b(wVar, item)).start();
                        }
                        wVar.f451c.setOnSeekBarChangeListener(new v(this));
                    } else {
                        wVar.f451c.setVisibility(8);
                        wVar.f453e.setVisibility(0);
                        wVar.f452d.setVisibility(8);
                        wVar.f454f.setVisibility(8);
                    }
                } catch (Exception unused) {
                    if (view == null) {
                        view = LayoutInflater.from(getContext()).inflate(this.mResourceId, viewGroup, false);
                    }
                    return view;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return view;
    }
}
