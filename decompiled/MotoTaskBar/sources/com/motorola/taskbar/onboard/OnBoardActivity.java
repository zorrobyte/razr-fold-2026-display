package com.motorola.taskbar.onboard;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager3.widget.ViewPager3;
import com.motorola.taskbar.guide.R$array;
import com.motorola.taskbar.guide.R$dimen;
import com.motorola.taskbar.guide.R$drawable;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.utils.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class OnBoardActivity extends FragmentActivity {
    private static final String TAG = "OnBoardActivity";
    private String[] descs;
    private TypedArray iconIds;
    private OnBoardAdapter mAdapter;
    private Button mBack;
    private final ViewPager3.OnPageChangeCallback mCallback = new ViewPager3.OnPageChangeCallback() { // from class: com.motorola.taskbar.onboard.OnBoardActivity.1
        @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
        public void onPageScrollStateChanged(int i) {
            super.onPageScrollStateChanged(i);
        }

        @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
        public void onPageScrolled(int i, float f, int i2) {
            super.onPageScrolled(i, f, i2);
        }

        @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
        public void onPageSelected(int i) {
            Log.d(OnBoardActivity.TAG, "onPageSelected: position = " + i);
            OnBoardActivity.this.mHelperIndicator.getChildAt(OnBoardActivity.this.mCurIndicatorIndex).setBackgroundResource(R$drawable.ic_indicator_no_select);
            OnBoardActivity.this.mCurIndicatorIndex = i;
            OnBoardActivity.this.mHelperIndicator.getChildAt(OnBoardActivity.this.mCurIndicatorIndex).setBackgroundResource(R$drawable.ic_indicator_select);
            if (i == 0) {
                OnBoardActivity.this.mBack.setVisibility(8);
                OnBoardActivity.this.mNext.setVisibility(0);
                OnBoardActivity.this.mNext.setText(R$string.next);
            } else if (i == OnBoardActivity.pageSize - 1) {
                OnBoardActivity.this.mNext.setVisibility(0);
                OnBoardActivity.this.mNext.setText(R$string.done);
            } else {
                OnBoardActivity.this.mBack.setVisibility(0);
                OnBoardActivity.this.mNext.setVisibility(0);
                OnBoardActivity.this.mNext.setText(R$string.next);
            }
        }
    };
    private int mCurIndicatorIndex;
    private LinearLayout mHelperIndicator;
    private Button mNext;
    private ViewPager3 pagerView;
    private String[] titles;
    public static final String ACTION_ON_BOARD_ACTIVITY_START = OnBoardActivity.class.getName() + ".start";
    public static final String ACTION_ON_BOARD_ACTIVITY_STOP = OnBoardActivity.class.getName() + ".stop";
    private static int pageSize = 0;

    class OnBoardAdapter extends RecyclerView.Adapter {
        OnBoardAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (OnBoardActivity.this.titles == null) {
                return 0;
            }
            return OnBoardActivity.this.titles.length;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(OnBoardVH onBoardVH, int i) {
            ((ImageView) onBoardVH.itemView.findViewById(R$id.ob_icon)).setImageResource(OnBoardActivity.this.iconIds.getResourceId(i, 0));
            ((TextView) onBoardVH.itemView.findViewById(R$id.ob_title)).setText(OnBoardActivity.this.titles[i]);
            ((TextView) onBoardVH.itemView.findViewById(R$id.ob_desc)).setText(OnBoardActivity.this.descs[i]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public OnBoardVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new OnBoardVH(LayoutInflater.from(OnBoardActivity.this).inflate(R$layout.onboard_item_view, viewGroup, false));
        }
    }

    class OnBoardVH extends RecyclerView.ViewHolder {
        public OnBoardVH(View view) {
            super(view);
        }
    }

    private int getDisplayType(Display display) {
        try {
            Class[] clsArr = new Class[0];
            return Integer.parseInt(Class.forName("android.view.Display").getMethod("getType", null).invoke(display, null).toString());
        } catch (Exception e) {
            Log.w(TAG, "getDisplayType error:" + e.toString());
            return -1;
        }
    }

    private void initViews(boolean z) {
        if (z) {
            this.iconIds = getResources().obtainTypedArray(R$array.onboarding_icons_rdp);
            this.titles = getResources().getStringArray(R$array.onboarding_titles_rdp);
            this.descs = getResources().getStringArray(R$array.onboarding_messages_rdp);
        } else {
            this.iconIds = getResources().obtainTypedArray(R$array.onboarding_icons_hdmi);
            this.titles = getResources().getStringArray(R$array.onboarding_titles_hdmi);
            this.descs = getResources().getStringArray(R$array.onboarding_messages_hdmi);
        }
        pageSize = this.titles.length;
        findViewById(R$id.back_icon).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.onboard.OnBoardActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$0(view);
            }
        });
        this.pagerView = (ViewPager3) findViewById(R$id.on_board_view_pager);
        if (this.mAdapter == null) {
            OnBoardAdapter onBoardAdapter = new OnBoardAdapter();
            this.mAdapter = onBoardAdapter;
            this.pagerView.setAdapter(onBoardAdapter);
        }
        this.mAdapter.notifyDataSetChanged();
        this.pagerView.post(new Runnable() { // from class: com.motorola.taskbar.onboard.OnBoardActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initViews$1();
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.on_board_indicator);
        this.mHelperIndicator = linearLayout;
        linearLayout.removeAllViews();
        int dimension = (int) getResources().getDimension(R$dimen.indicator_size);
        int dimension2 = (int) getResources().getDimension(R$dimen.indicator_space);
        for (int i = 0; i < pageSize; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimension, dimension);
            ImageView imageView = new ImageView(this);
            if (i != 0) {
                layoutParams.setMarginStart(dimension2);
            }
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(R$drawable.ic_indicator_no_select);
            this.mHelperIndicator.addView(imageView);
        }
        this.mHelperIndicator.getChildAt(this.mCurIndicatorIndex).setBackgroundResource(R$drawable.ic_indicator_select);
        Button button = (Button) findViewById(R$id.btn_page_back);
        this.mBack = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.onboard.OnBoardActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$2(view);
            }
        });
        this.mBack.setVisibility(8);
        Button button2 = (Button) findViewById(R$id.btn_page_next);
        this.mNext = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.onboard.OnBoardActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViews$3(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1() {
        this.pagerView.unregisterOnPageChangeCallback(this.mCallback);
        this.pagerView.setCurrentItem(0, false);
        this.mCurIndicatorIndex = 0;
        this.pagerView.registerOnPageChangeCallback(this.mCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        previous();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (this.mCurIndicatorIndex == this.titles.length - 1) {
            finish();
        } else {
            next();
        }
    }

    private void loadOnBoardingContent() {
        int displayType = getDisplayType(getDisplay());
        Log.d(TAG, "loadOnBoardingContent: displayType = " + displayType);
        initViews(displayType == 5);
    }

    private void next() {
        int i = this.mCurIndicatorIndex;
        if (i != pageSize - 1) {
            this.pagerView.setCurrentItem(i + 1);
        }
    }

    private void previous() {
        int i = this.mCurIndicatorIndex;
        if (i != 0) {
            this.pagerView.setCurrentItem(i - 1);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getDecorView().setSystemUiVisibility(260);
        setContentView(R$layout.activity_on_board);
        loadOnBoardingContent();
        Utils.rejectShowingDecorCaptionView(getWindow());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.pagerView.unregisterOnPageChangeCallback(this.mCallback);
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadOnBoardingContent();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (getDisplay().getDisplayId() == 0) {
            Toast.makeText(this, R$string.toast_tutorial, 0).show();
            finishAndRemoveTask();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(ACTION_ON_BOARD_ACTIVITY_START);
        intent.putExtra("displayId", getDisplay().getDisplayId());
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(ACTION_ON_BOARD_ACTIVITY_STOP);
        intent.putExtra("displayId", getDisplay().getDisplayId());
        localBroadcastManager.sendBroadcast(intent);
    }
}
