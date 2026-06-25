package com.motorola.taskbar.settings.helper;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.helper.TabLayoutMediator;
import com.motorola.taskbar.settings.utils.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardShortcutHelper extends FragmentActivity implements View.OnClickListener {
    private static boolean isShowing = false;
    private Context mContext;
    private TextView mDone;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.settings.helper.KeyboardShortcutHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            KeyboardShortcutHelper.this.finishAndRemoveTask();
        }
    };
    private TabLayout mTabLayout;

    public class PlatformPagerAdapter extends FragmentStateAdapter {
        private static final int[] TAB_TITLES = {R$string.tab_name_android, R$string.tab_name_windows, R$string.tab_name_mac};
        private final Context mContext;
        SparseArray mFragments;

        public PlatformPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.mFragments = new SparseArray();
            this.mContext = fragmentActivity;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public View getTabView(int i) {
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R$layout.item_tab, (ViewGroup) null);
            ((TextView) viewInflate.findViewById(R$id.text)).setText(TAB_TITLES[i]);
            return viewInflate;
        }

        public void attach(TabLayout tabLayout) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(this) { // from class: com.motorola.taskbar.settings.helper.KeyboardShortcutHelper.PlatformPagerAdapter.1
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                }
            });
        }

        @Override // androidx.viewpager2.adapter.FragmentStateAdapter
        public Fragment createFragment(int i) {
            Fragment fragmentNewInstance = (Fragment) this.mFragments.get(i);
            if (fragmentNewInstance == null) {
                fragmentNewInstance = i == 0 ? AndroidHelperFragment.newInstance() : i == 1 ? WindowsHelperFragment.newInstance() : MacHelperFragment.newInstance();
                this.mFragments.put(i, fragmentNewInstance);
            }
            return fragmentNewInstance;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return TAB_TITLES.length;
        }
    }

    private void initView() {
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R$id.view_pager);
        final PlatformPagerAdapter platformPagerAdapter = new PlatformPagerAdapter(this);
        viewPager2.setAdapter(platformPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R$id.tabs);
        this.mTabLayout = tabLayout;
        platformPagerAdapter.attach(tabLayout);
        new TabLayoutMediator(this.mTabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.motorola.taskbar.settings.helper.KeyboardShortcutHelper$$ExternalSyntheticLambda0
            @Override // com.motorola.taskbar.settings.helper.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                tab.setCustomView(platformPagerAdapter.getTabView(i));
            }
        }).attach();
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme.DeviceDefault.Settings);
        TextView textView = (TextView) findViewById(R$id.close_window);
        this.mDone = textView;
        textView.setTextColor(ColorStateList.valueOf(Utils.getColorAccent(contextThemeWrapper)));
        this.mDone.setOnClickListener(this);
        findViewById(R$id.parent).setOnClickListener(this);
    }

    public static boolean isShowing() {
        return isShowing;
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
        isShowing = true;
        getWindow().getDecorView().setSystemUiVisibility(260);
        this.mContext = this;
        setContentView(R$layout.keyboard_shortcuts_layout);
        initView();
        Utils.rejectShowingDecorCaptionView(getWindow());
        registerReceiver(this.mReceiver, new IntentFilter("com.motorola.taskbar.settings.DISMISS_HELPER"), 2);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        isShowing = false;
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }
}
