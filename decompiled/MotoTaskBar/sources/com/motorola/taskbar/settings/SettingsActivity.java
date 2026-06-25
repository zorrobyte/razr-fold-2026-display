package com.motorola.taskbar.settings;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.guide.R$color;
import com.motorola.taskbar.guide.R$dimen;
import com.motorola.taskbar.guide.R$drawable;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;
import com.motorola.taskbar.settings.advanced.AdvancedFragment;
import com.motorola.taskbar.settings.di.SettingsComponent;
import com.motorola.taskbar.settings.di.SettingsComponentFactoryProvider;
import com.motorola.taskbar.settings.fragment.DongleFragment;
import com.motorola.taskbar.settings.fragment.SoundFragment;
import com.motorola.taskbar.settings.utils.SettingsUtils;
import com.motorola.taskbar.settings.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class SettingsActivity extends FragmentActivity {
    public static final String TAG = "SettingsActivity";
    private AdvancedFragment advancedFragment;
    private int defaultBackColor;
    private int defaultTextColor;
    private int disableTextColor;
    private DisplayFragment displayFragment;
    private DongleFragment dongleFragment;
    private int iconSize;
    private ItemAdapter itemAdapter;
    private String mCurrentFragmentTag = "";
    private Resources mRes;
    private int mSettingItemTintCheck;
    SettingsComponent mSettingsComponent;
    private LinearLayout mSwitchTab;
    private MKFragment mkFragment;
    private ModeFragment modeFragment;
    private int redDotIconSize;
    private SoundFragment soundFragment;
    private Toolbar toolBar;
    private WallpaperFragment wallpaperFragment;
    private static Item[] items = {new Item(R$string.settings_screen_wallpaper, R$drawable.ic_wallpaper_uncheck, R$drawable.ic_wallpaper_check, WallpaperFragment.class.getSimpleName()), new Item(R$string.settings_screen_timeout, R$drawable.ic_time_out_unchecked, R$drawable.ic_time_out_checked, ScreenTimeoutFragment.class.getSimpleName()), new Item(R$string.settings_display_and_font, R$drawable.ic_display_size_uncheck, R$drawable.ic_display_size_check, DisplayFragment.class.getSimpleName()), new Item(R$string.settings_mouse_and_keyboard, R$drawable.ic_keyboard_uncheck, R$drawable.ic_keyboard_check, MKFragment.class.getSimpleName()), new Item(R$string.sound_setting, R$drawable.ic_sound, R$drawable.ic_sound_checked, SoundFragment.class.getSimpleName()), new Item(R$string.mode_setting, R$drawable.ic_mode_resolution, R$drawable.ic_mode_resolution_checked, ModeFragment.class.getSimpleName()), new Item(R$string.settings_advanced, R$drawable.ic_advanced, R$drawable.ic_advanced_check, AdvancedFragment.class.getSimpleName()), new Item(R$string.dongle_setting, R$drawable.ic_adapter, R$drawable.ic_adapter_check, DongleFragment.class.getSimpleName())};
    private static List itemList = new ArrayList();
    private static int sDongleItemIndex = 0;
    private static final String[] SUPPORT_TAGS = {ScreenTimeoutFragment.class.getSimpleName(), DisplayFragment.class.getSimpleName(), WallpaperFragment.class.getSimpleName(), MKFragment.class.getSimpleName(), AdvancedFragment.class.getSimpleName(), DongleFragment.class.getSimpleName(), SoundFragment.class.getSimpleName(), ModeFragment.class.getSimpleName()};
    public static int sDisplayId = 0;
    public static MotorolaSettingsSystem.ConnectType sConnectType = MotorolaSettingsSystem.ConnectType.NONE;

    public class Item {
        public int icRes;
        public int icRes_check;
        public int nameRes;
        public String tag;
        public boolean enable = true;
        public boolean showRedDot = false;

        public Item(int i, int i2, int i3, String str) {
            this.nameRes = i;
            this.icRes = i2;
            this.icRes_check = i3;
            this.tag = str;
        }

        public void setEnable(boolean z) {
            this.enable = z;
        }

        public void setShowRedDot(boolean z) {
            this.showRedDot = z;
        }
    }

    class ItemAdapter extends RecyclerView.Adapter {
        private ItemAdapter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$0(Item item, View view) {
            if (!SettingsActivity.this.mCurrentFragmentTag.equals(item.tag)) {
                SettingsActivity.this.switchFragmentByTag(item.tag);
                notifyDataSetChanged();
            }
            if (Utils.isMobileUIAndPortrait(SettingsActivity.this)) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.hideSwitchTab(settingsActivity.getResources().getString(item.nameRes));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return SettingsActivity.itemList.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(ItemVH itemVH, int i) {
            Drawable drawable;
            Drawable drawable2;
            final Item item = (Item) SettingsActivity.itemList.get(i);
            if (!item.tag.equals(SettingsActivity.this.mCurrentFragmentTag) || Utils.isMobileUIAndPortrait(SettingsActivity.this)) {
                drawable = SettingsActivity.this.getResources().getDrawable(item.icRes, SettingsActivity.this.getTheme());
                itemVH.title.setTextColor(SettingsActivity.this.defaultTextColor);
                itemVH.title.setBackgroundColor(SettingsActivity.this.defaultBackColor);
            } else {
                drawable = SettingsActivity.this.getResources().getDrawable(item.icRes_check, SettingsActivity.this.getTheme());
                itemVH.title.setTextColor(SettingsActivity.this.mSettingItemTintCheck);
                itemVH.title.setBackgroundResource(R$drawable.bg_rectangle_setting_item);
            }
            if (!item.enable) {
                drawable.setTint(SettingsActivity.this.disableTextColor);
                itemVH.title.setTextColor(SettingsActivity.this.disableTextColor);
                itemVH.title.setBackgroundColor(SettingsActivity.this.defaultBackColor);
            }
            itemVH.title.setText(item.nameRes);
            drawable.setBounds(0, 0, SettingsActivity.this.iconSize, SettingsActivity.this.iconSize);
            if (item.showRedDot) {
                drawable2 = SettingsActivity.this.getResources().getDrawable(R$drawable.red_dot, SettingsActivity.this.getTheme());
                drawable2.setBounds(0, 0, SettingsActivity.this.redDotIconSize, SettingsActivity.this.redDotIconSize);
            } else {
                drawable2 = null;
            }
            if (SettingsActivity.isRtl()) {
                itemVH.title.setCompoundDrawables(drawable2, null, drawable, null);
            } else {
                itemVH.title.setCompoundDrawables(drawable, null, drawable2, null);
            }
            if (item.enable) {
                itemVH.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.SettingsActivity$ItemAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$0(item, view);
                    }
                });
            } else {
                itemVH.itemView.setOnClickListener(null);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public ItemVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ItemVH(LayoutInflater.from(SettingsActivity.this).inflate(R$layout.item_layout, viewGroup, false));
        }
    }

    class ItemVH extends RecyclerView.ViewHolder {
        public TextView title;

        public ItemVH(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R$id.title);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideSwitchTab(String str) {
        this.mSwitchTab.setVisibility(8);
        findViewById(R$id.fragment_container).setVisibility(0);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.toolBar.setTitle(str);
    }

    private void initAccentColor() {
        this.mSettingItemTintCheck = this.mRes.getColor(R$color.setting_item_icon_tint_check, getTheme());
        this.iconSize = this.mRes.getDimensionPixelOffset(R$dimen.icon_size);
        this.redDotIconSize = this.mRes.getDimensionPixelOffset(R$dimen.red_dot_icon_size);
        this.defaultBackColor = this.mRes.getColor(R$color.alpha_0, getTheme());
        this.defaultTextColor = this.mRes.getColor(R$color.setting_text_color, getTheme());
        this.disableTextColor = this.mRes.getColor(R$color.setting_text_color_disable, getTheme());
    }

    public static boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (Utils.isMobileUIAndPortrait(this) && this.mSwitchTab.getVisibility() == 8) {
            showSwitchTab();
        } else {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        Intent intent = new Intent("com.motorola.help.VIEW_HELP");
        intent.putExtra("topic", "help_url_mobile_desktop");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        Intent intent = new Intent("com.motorola.help.VIEW_HELP");
        intent.putExtra("topic", "help_url_mobile_desktop");
        startActivity(intent);
    }

    private void setDisplayId(Intent intent) {
        if (intent != null) {
            try {
                int intExtra = intent.getIntExtra("display_id", -1);
                sDisplayId = intExtra;
                sConnectType = Utils.getConnectType(this, intExtra);
                Log.d(TAG, "setDisplayId: mConnectType = " + sConnectType.name());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchFragment(Fragment fragment) {
        this.mCurrentFragmentTag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R$id.fragment_container, fragment, fragment.getClass().getName()).show(fragment);
        fragmentTransactionBeginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchFragmentByTag(String str) {
        Log.d(TAG, "switchFragmentByTag: " + str);
        if (Utils.isMobileUIAndPortrait(this) && TextUtils.isEmpty(str)) {
            return;
        }
        getIntent().putExtra("current_tag", str);
        if (!Arrays.asList(SUPPORT_TAGS).contains(str)) {
            switchFragment(ScreenTimeoutFragment.getInstance());
            return;
        }
        if (str.contains(ScreenTimeoutFragment.class.getSimpleName())) {
            switchFragment(ScreenTimeoutFragment.getInstance());
            return;
        }
        if (str.contains(DisplayFragment.class.getSimpleName())) {
            switchFragment(this.displayFragment);
            return;
        }
        if (str.contains(WallpaperFragment.class.getSimpleName())) {
            switchFragment(this.wallpaperFragment);
            return;
        }
        if (str.contains(MKFragment.class.getSimpleName())) {
            switchFragment(this.mkFragment);
            return;
        }
        if (str.contains(AdvancedFragment.class.getSimpleName())) {
            switchFragment(this.advancedFragment);
            return;
        }
        if (str.contains(DongleFragment.class.getSimpleName())) {
            switchFragment(this.dongleFragment);
        } else if (str.contains(SoundFragment.class.getSimpleName())) {
            switchFragment(this.soundFragment);
        } else if (str.contains(ModeFragment.class.getSimpleName())) {
            switchFragment(this.modeFragment);
        }
    }

    private void updateDongleItem() {
        if (sConnectType == MotorolaSettingsSystem.ConnectType.DP || sConnectType == MotorolaSettingsSystem.ConnectType.RDP) {
            ((Item) itemList.get(sDongleItemIndex)).setEnable(false);
            return;
        }
        boolean zIsDongleConnected = SettingsUtils.isDongleConnected();
        ((Item) itemList.get(sDongleItemIndex)).setEnable(zIsDongleConnected);
        if (zIsDongleConnected) {
            ((Item) itemList.get(sDongleItemIndex)).setShowRedDot(SettingsUtils.isDongleUpgradeAvailable());
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (Utils.isMobileUIAndPortrait(this) && this.mSwitchTab.getVisibility() == 8) {
            showSwitchTab();
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SettingsComponent settingsComponentCreate = ((SettingsComponentFactoryProvider) getApplication()).providerSettingsComponentFactory().create(this);
        this.mSettingsComponent = settingsComponentCreate;
        settingsComponentCreate.inject(this);
        itemList = new ArrayList(Arrays.asList(items));
        if (Utils.isTablet(this)) {
            itemList.remove(3);
            itemList.remove(4);
        }
        sDongleItemIndex = itemList.size() - 1;
        Log.d(TAG, "onCreate: show " + itemList.size() + "options");
        updateDongleItem();
        super.onCreate(bundle);
        setContentView(R$layout.setting_layout);
        this.toolBar = (Toolbar) findViewById(R$id.toolbar);
        if (MotoDesktopManager.isMobileUiMode(getDisplay())) {
            this.toolBar.setVisibility(0);
        }
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.SettingsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(view);
            }
        });
        setDisplayId(getIntent());
        this.mRes = getResources();
        initAccentColor();
        this.displayFragment = DisplayFragment.getInstance();
        this.wallpaperFragment = WallpaperFragment.newInstance();
        this.mkFragment = MKFragment.getInstance();
        this.advancedFragment = AdvancedFragment.getInstance();
        this.dongleFragment = DongleFragment.getInstance();
        this.soundFragment = SoundFragment.getInstance();
        this.modeFragment = ModeFragment.getInstance();
        String stringExtra = getIntent().getStringExtra("current_tag");
        if (TextUtils.isEmpty(stringExtra) && bundle != null) {
            stringExtra = bundle.getString("current_tag");
        }
        switchFragmentByTag(stringExtra);
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.settings_list);
        this.mSwitchTab = (LinearLayout) findViewById(R$id.switch_tab);
        if (Utils.isMobileUIAndPortrait(this)) {
            if (TextUtils.isEmpty(stringExtra)) {
                findViewById(R$id.fragment_container).setVisibility(8);
                findViewById(R$id.settings_help).setVisibility(8);
            } else {
                hideSwitchTab("");
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemAdapter itemAdapter = new ItemAdapter();
        this.itemAdapter = itemAdapter;
        recyclerView.setAdapter(itemAdapter);
        TextView textView = (TextView) findViewById(R$id.settings_help);
        ((ImageView) findViewById(R$id.settings_help_mobile)).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$1(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.SettingsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$2(view);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        InjectorProvider.releaseInjector();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setDisplayId(intent);
        setIntent(intent);
        String stringExtra = getIntent().getStringExtra("current_tag");
        if (TextUtils.isEmpty(stringExtra)) {
            stringExtra = this.mCurrentFragmentTag;
        }
        switchFragmentByTag(stringExtra);
        ItemAdapter itemAdapter = this.itemAdapter;
        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }
        sendBroadcast(new Intent("com.motorola.taskbar.settings.closeAppConfiger"));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateDongleItem();
        this.itemAdapter.notifyItemChanged(sDongleItemIndex);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("current_tag", this.mCurrentFragmentTag);
    }

    public void showSwitchTab() {
        this.mSwitchTab.setVisibility(0);
        findViewById(R$id.fragment_container).setVisibility(8);
        this.toolBar.setTitle(R$string.setting_mobile_title);
        this.mCurrentFragmentTag = "";
        getIntent().putExtra("current_tag", "");
    }
}
