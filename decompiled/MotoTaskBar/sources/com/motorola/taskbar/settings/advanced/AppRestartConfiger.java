package com.motorola.taskbar.settings.advanced;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AppRestartConfiger extends FragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    public static String TEXT_USER_CONTINUE;
    public static String TEXT_USER_RESTART;
    private AppAdapter mAdapter;
    private Context mContext;
    private TextView mDone;
    private LauncherApps mLauncherApps;
    private RecyclerView mListView;
    private SearchAdapter mSearchAdapter;
    private List mAllApps = new ArrayList();
    private List mAllPackages = new ArrayList();
    private List mAllValues = new ArrayList();
    private HashMap iconCache = new HashMap();
    private HashMap nameCache = new HashMap();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.settings.advanced.AppRestartConfiger.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            AppRestartConfiger.this.finishAndRemoveTask();
        }
    };
    private List mSearchPackages = new ArrayList();

    class AppAdapter extends RecyclerView.Adapter {
        AppAdapter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$0(boolean z, AppViewHolder appViewHolder, int i, View view) {
            if (z) {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_CONTINUE);
                appViewHolder.summary.setText(R$string.app_continue_detail);
                AppRestartConfiger.this.mAllValues.set(i, "user_continue");
            } else {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_RESTART);
                appViewHolder.summary.setText(R$string.app_restart_detail);
                AppRestartConfiger.this.mAllValues.set(i, "user_restart");
            }
            notifyItemChanged(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return AppRestartConfiger.this.mAllApps.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(final AppViewHolder appViewHolder, final int i) {
            String str = (String) AppRestartConfiger.this.mAllPackages.get(i);
            if (AppRestartConfiger.this.nameCache.containsKey(str)) {
                appViewHolder.name.setText((CharSequence) AppRestartConfiger.this.nameCache.get(str));
            } else {
                String string = ((LauncherActivityInfo) AppRestartConfiger.this.mAllApps.get(i)).getLabel().toString();
                AppRestartConfiger.this.nameCache.put(str, string);
                appViewHolder.name.setText(string);
            }
            if (AppRestartConfiger.this.iconCache.containsKey(str)) {
                appViewHolder.icon.setImageDrawable((Drawable) AppRestartConfiger.this.iconCache.get(str));
            } else {
                Drawable icon = ((LauncherActivityInfo) AppRestartConfiger.this.mAllApps.get(i)).getIcon(AppRestartConfiger.this.mContext.getResources().getDisplayMetrics().densityDpi);
                AppRestartConfiger.this.iconCache.put(str, icon);
                appViewHolder.icon.setImageDrawable(icon);
            }
            final boolean zContains = ((String) AppRestartConfiger.this.mAllValues.get(i)).contains("restart");
            if (zContains) {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_RESTART);
                appViewHolder.summary.setText(R$string.app_restart_detail);
            } else {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_CONTINUE);
                appViewHolder.summary.setText(R$string.app_continue_detail);
            }
            appViewHolder.status.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.advanced.AppRestartConfiger$AppAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$0(zContains, appViewHolder, i, view);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AppViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AppViewHolder(LayoutInflater.from(AppRestartConfiger.this.mContext).inflate(R$layout.app_restart_item, viewGroup, false));
        }
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView status;
        public TextView summary;

        public AppViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R$id.app_icon);
            this.name = (TextView) view.findViewById(R$id.app_name);
            this.summary = (TextView) view.findViewById(R$id.app_summary);
            this.status = (TextView) view.findViewById(R$id.app_status);
        }
    }

    class SearchAdapter extends RecyclerView.Adapter {
        SearchAdapter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$0(boolean z, AppViewHolder appViewHolder, int i, int i2, View view) {
            if (z) {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_CONTINUE);
                appViewHolder.summary.setText(R$string.app_continue_detail);
                AppRestartConfiger.this.mAllValues.set(i, "user_continue");
            } else {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_RESTART);
                appViewHolder.summary.setText(R$string.app_restart_detail);
                AppRestartConfiger.this.mAllValues.set(i, "user_restart");
            }
            notifyItemChanged(i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return AppRestartConfiger.this.mSearchPackages.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(final AppViewHolder appViewHolder, final int i) {
            String str = (String) AppRestartConfiger.this.mSearchPackages.get(i);
            appViewHolder.name.setText((CharSequence) AppRestartConfiger.this.nameCache.get(str));
            appViewHolder.icon.setImageDrawable((Drawable) AppRestartConfiger.this.iconCache.get(str));
            final int iIndexOf = AppRestartConfiger.this.mAllPackages.indexOf(str);
            final boolean zContains = ((String) AppRestartConfiger.this.mAllValues.get(iIndexOf)).contains("restart");
            if (zContains) {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_RESTART);
                appViewHolder.summary.setText(R$string.app_restart_detail);
            } else {
                appViewHolder.status.setText(AppRestartConfiger.TEXT_USER_CONTINUE);
                appViewHolder.summary.setText(R$string.app_continue_detail);
            }
            appViewHolder.status.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.advanced.AppRestartConfiger$SearchAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$0(zContains, appViewHolder, iIndexOf, i, view);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AppViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AppViewHolder(LayoutInflater.from(AppRestartConfiger.this.mContext).inflate(R$layout.app_restart_item, viewGroup, false));
        }
    }

    private void filterCloneApp() {
        int i = 0;
        while (i < this.mAllApps.size()) {
            if (((LauncherActivityInfo) this.mAllApps.get(i)).getUser().equals(Process.myUserHandle())) {
                i++;
            } else {
                this.mAllApps.remove(i);
            }
        }
    }

    private void initView() {
        Log.d("AppRestartConfiger", "initView: ");
        findViewById(R$id.parent).setOnClickListener(this);
        SearchView searchView = (SearchView) findViewById(R$id.app_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() { // from class: com.motorola.taskbar.settings.advanced.AppRestartConfiger$$ExternalSyntheticLambda0
            @Override // androidx.appcompat.widget.SearchView.OnCloseListener
            public final boolean onClose() {
                return this.f$0.lambda$initView$0();
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.advanced.AppRestartConfiger$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$1(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.app_list_view);
        this.mListView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppAdapter appAdapter = new AppAdapter();
        this.mAdapter = appAdapter;
        this.mListView.setAdapter(appAdapter);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme.DeviceDefault.Settings);
        TextView textView = (TextView) findViewById(R$id.close_window);
        this.mDone = textView;
        textView.setTextColor(ColorStateList.valueOf(Utils.getColorAccent(contextThemeWrapper)));
        this.mDone.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0() {
        Log.d("AppRestartConfiger", "onClose: ");
        this.mListView.setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        Log.d("AppRestartConfiger", "onClick: ");
        if (this.mSearchAdapter == null) {
            this.mSearchAdapter = new SearchAdapter();
        }
        this.mListView.setAdapter(this.mSearchAdapter);
        this.mSearchAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.close_window || id == R$id.parent) {
            finishAndRemoveTask();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("AppRestartConfiger", "onCreate: ");
        this.mContext = this;
        this.mLauncherApps = (LauncherApps) getSystemService(LauncherApps.class);
        getWindow().getDecorView().setSystemUiVisibility(260);
        setContentView(R$layout.app_restart_layout);
        initView();
        Utils.rejectShowingDecorCaptionView(getWindow());
        registerReceiver(this.mReceiver, new IntentFilter("com.motorola.taskbar.settings.closeAppConfiger"), 2);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        try {
            ActivityManager.getService().setDesktopRestartModeByPackages(this.mAllPackages, this.mAllValues);
        } catch (RemoteException e) {
            Log.v("AppRestartConfiger", "Could not talk to activity manager.", e);
            finishAndRemoveTask();
        }
        this.iconCache.clear();
        this.nameCache.clear();
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        Log.d("AppRestartConfiger", "onQueryTextChange: newText = " + str);
        if (this.mSearchAdapter == null) {
            return false;
        }
        this.mSearchPackages.clear();
        String lowerCase = str.toLowerCase();
        for (LauncherActivityInfo launcherActivityInfo : this.mAllApps) {
            if (launcherActivityInfo.getLabel().toString().toLowerCase().contains(lowerCase)) {
                this.mSearchPackages.add((String) this.mAllPackages.get(this.mAllApps.indexOf(launcherActivityInfo)));
            }
        }
        this.mSearchAdapter.notifyDataSetChanged();
        return false;
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        Log.d("AppRestartConfiger", "onQueryTextSubmit: query = " + str);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.d("AppRestartConfiger", "onResume: ");
        this.iconCache.clear();
        this.nameCache.clear();
        TEXT_USER_RESTART = getString(R$string.app_restart);
        TEXT_USER_CONTINUE = getString(R$string.app_continue);
        int i = this.mContext.getResources().getDisplayMetrics().densityDpi;
        List<LauncherActivityInfo> activityList = this.mLauncherApps.getActivityList(null, Process.myUserHandle());
        this.mAllApps.clear();
        this.mAllApps.addAll(activityList);
        filterCloneApp();
        Collections.sort(this.mAllApps, new DisplayNameComparator(this));
        this.mAllPackages.clear();
        if (this.mAllApps.size() > 0) {
            Iterator it = this.mAllApps.iterator();
            while (it.hasNext()) {
                this.mAllPackages.add(((LauncherActivityInfo) it.next()).getApplicationInfo().packageName);
            }
        }
        this.mAllValues.clear();
        try {
            this.mAllValues.addAll(ActivityManager.getService().getDesktopRestartModeByPackages(this.mAllPackages));
            int i2 = 0;
            while (i2 < this.mAllPackages.size()) {
                if (((String) this.mAllValues.get(i2)).contains("force")) {
                    this.mAllPackages.remove(i2);
                    this.mAllApps.remove(i2);
                    this.mAllValues.remove(i2);
                } else {
                    i2++;
                }
            }
            for (LauncherActivityInfo launcherActivityInfo : this.mAllApps) {
                String str = launcherActivityInfo.getApplicationInfo().packageName;
                this.nameCache.put(str, launcherActivityInfo.getLabel().toString());
                this.iconCache.put(str, launcherActivityInfo.getIcon(i));
            }
            this.mAdapter.notifyDataSetChanged();
        } catch (RemoteException e) {
            Log.v("AppRestartConfiger", "Could not talk to activity manager.", e);
            finishAndRemoveTask();
        }
    }
}
