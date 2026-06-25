package com.motorola.taskbar.settings.helper;

import android.R;
import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.motorola.taskbar.guide.R$color;
import com.motorola.taskbar.guide.R$drawable;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.SettingsActivity;
import com.motorola.taskbar.settings.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardShortcutHelperFragment extends Fragment {
    List keyboardShortcutGroups;
    private KeyCharacterMap mBackupKeyCharacterMap;
    protected Context mContext;
    protected KeyCharacterMap mKeyCharacterMap;
    public IPackageManager mPackageManager;
    protected final SparseArray mSpecialCharacterNames = new SparseArray();
    protected final SparseArray mModifierNames = new SparseArray();
    protected final SparseArray mSpecialCharacterDrawables = new SparseArray();
    protected final SparseArray mModifierDrawables = new SparseArray();
    protected final int[] mModifierList = {65536, 4096, 2, 1, 4, 8};
    protected int mKeyboardType = 0;
    private final Comparator mApplicationItemsComparator = new Comparator(this) { // from class: com.motorola.taskbar.settings.helper.KeyboardShortcutHelperFragment.1
        @Override // java.util.Comparator
        public int compare(KeyboardShortcutInfo keyboardShortcutInfo, KeyboardShortcutInfo keyboardShortcutInfo2) {
            boolean z = keyboardShortcutInfo.getLabel() == null || keyboardShortcutInfo.getLabel().toString().isEmpty();
            boolean z2 = keyboardShortcutInfo2.getLabel() == null || keyboardShortcutInfo2.getLabel().toString().isEmpty();
            if (z && z2) {
                return 0;
            }
            if (z) {
                return 1;
            }
            if (z2) {
                return -1;
            }
            return keyboardShortcutInfo.getLabel().toString().compareToIgnoreCase(keyboardShortcutInfo2.getLabel().toString());
        }
    };

    public final class ShortcutKeyAccessibilityDelegate extends View.AccessibilityDelegate {
        private String mContentDescription;

        ShortcutKeyAccessibilityDelegate(String str) {
            this.mContentDescription = str;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            String str = this.mContentDescription;
            if (str != null) {
                accessibilityNodeInfo.setContentDescription(str.toLowerCase());
            }
        }
    }

    public final class StringDrawableContainer {
        public Drawable mDrawable;
        public String mString;

        StringDrawableContainer(String str, Drawable drawable) {
            this.mString = str;
            this.mDrawable = drawable;
        }
    }

    private KeyboardShortcutGroup getDefaultApplicationShortcuts() {
        PackageInfo packageInfo;
        Icon iconForIntentCategory;
        if (this.mContext == null) {
            this.mContext = getContext();
        }
        if (this.mContext == null) {
            this.mContext = getActivity().getApplicationContext();
        }
        int userId = this.mContext.getUserId();
        ArrayList arrayList = new ArrayList();
        Icon iconForIntentCategory2 = getIconForIntentCategory("android.intent.category.APP_BROWSER", userId);
        if (iconForIntentCategory2 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_browser), iconForIntentCategory2, 30, 65536));
        }
        Icon iconForIntentCategory3 = getIconForIntentCategory("android.intent.category.APP_CONTACTS", userId);
        if (iconForIntentCategory3 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_contacts), iconForIntentCategory3, 31, 65536));
        }
        Icon iconForIntentCategory4 = getIconForIntentCategory("android.intent.category.APP_EMAIL", userId);
        if (iconForIntentCategory4 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_email), iconForIntentCategory4, 33, 65536));
        }
        Icon iconForIntentCategory5 = getIconForIntentCategory("android.intent.category.APP_MESSAGING", userId);
        if (iconForIntentCategory5 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_sms), iconForIntentCategory5, 47, 65536));
        }
        Icon iconForIntentCategory6 = getIconForIntentCategory("android.intent.category.APP_MUSIC", userId);
        if (iconForIntentCategory6 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_music), iconForIntentCategory6, 44, 65536));
        }
        try {
            packageInfo = this.mPackageManager.getPackageInfo("com.google.android.youtube", 0L, userId);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        if (packageInfo != null) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_youtube), Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon), 53, 65536));
        }
        Icon iconForIntentCategory7 = getIconForIntentCategory("android.intent.category.APP_CALCULATOR", userId);
        if (iconForIntentCategory7 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_calculate), iconForIntentCategory7, 29, 65536));
        }
        Icon iconForIntentCategory8 = getIconForIntentCategory("android.intent.category.APP_MAPS", userId);
        if (iconForIntentCategory8 != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_google_map), iconForIntentCategory8, 41, 65536));
        }
        if (Utils.isHDMI(this.mContext, SettingsActivity.sDisplayId) && (iconForIntentCategory = getIconForIntentCategory("android.intent.category.APP_CALENDAR", userId)) != null) {
            arrayList.add(new KeyboardShortcutInfo(this.mContext.getString(R$string.keyboard_shortcut_group_applications_calendar), iconForIntentCategory, 40, 65536));
        }
        if (arrayList.size() == 0) {
            return null;
        }
        Collections.sort(arrayList, this.mApplicationItemsComparator);
        return new KeyboardShortcutGroup(this.mContext.getString(R$string.keyboard_shortcut_group_applications), arrayList, true);
    }

    private Icon getIconForIntentCategory(String str, int i) {
        ApplicationInfo applicationInfo;
        int i2;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(str);
        PackageInfo packageInfoForIntent = getPackageInfoForIntent(intent, i);
        if (packageInfoForIntent == null || (i2 = (applicationInfo = packageInfoForIntent.applicationInfo).icon) == 0) {
            return null;
        }
        return Icon.createWithResource(applicationInfo.packageName, i2);
    }

    private PackageInfo getPackageInfoForIntent(Intent intent, int i) {
        ActivityInfo activityInfo;
        try {
            ResolveInfo resolveInfoResolveIntent = this.mPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0L, i);
            if (resolveInfoResolveIntent != null && (activityInfo = resolveInfoResolveIntent.activityInfo) != null) {
                return this.mPackageManager.getPackageInfo(activityInfo.packageName, 0L, i);
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    private KeyboardShortcutGroup getSystemShortcuts() {
        Context contextRequireContext = requireContext();
        KeyboardShortcutGroup keyboardShortcutGroup = new KeyboardShortcutGroup((CharSequence) contextRequireContext.getString(R$string.keyboard_shortcut_group_system), true);
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_shortcut_group_system_home), 66, 65536));
        int i = R$string.keyboard_shortcut_group_system_back;
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(i), 67, 65536));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(i), 111, 0));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_shortcut_group_system_recents), 61, 2));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_shortcut_group_system_notifications), 42, 65536));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_shortcut_group_system_shortcuts_helper), 76, 65536));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_shortcut_group_system_switch_input), 62, 65536));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_screenshot_tool), 47, 69632));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_screenshot_window), 47, 69633));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_screenshot_screen), 120, 0));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_app_tray), 0, 65536));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_copy), 31, this.mKeyboardType == 1 ? 65536 : 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_paste), 50, this.mKeyboardType == 1 ? 65536 : 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(contextRequireContext.getString(R$string.keyboard_key_cut), 52, this.mKeyboardType != 1 ? 4096 : 65536));
        return keyboardShortcutGroup;
    }

    private void loadResources(Context context) {
        this.mSpecialCharacterNames.put(3, context.getString(R$string.keyboard_key_home));
        this.mSpecialCharacterNames.put(4, context.getString(R$string.keyboard_key_back));
        this.mSpecialCharacterNames.put(19, context.getString(R$string.keyboard_key_dpad_up));
        this.mSpecialCharacterNames.put(20, context.getString(R$string.keyboard_key_dpad_down));
        this.mSpecialCharacterNames.put(21, context.getString(R$string.keyboard_key_dpad_left));
        this.mSpecialCharacterNames.put(22, context.getString(R$string.keyboard_key_dpad_right));
        this.mSpecialCharacterNames.put(23, context.getString(R$string.keyboard_key_dpad_center));
        this.mSpecialCharacterNames.put(56, ".");
        this.mSpecialCharacterNames.put(61, context.getString(R$string.keyboard_key_tab));
        this.mSpecialCharacterNames.put(62, context.getString(R$string.keyboard_key_space));
        SparseArray sparseArray = this.mSpecialCharacterNames;
        int i = R$string.keyboard_key_enter;
        sparseArray.put(66, context.getString(i));
        this.mSpecialCharacterNames.put(67, context.getString(R$string.keyboard_key_backspace));
        this.mSpecialCharacterNames.put(85, context.getString(R$string.keyboard_key_media_play_pause));
        this.mSpecialCharacterNames.put(86, context.getString(R$string.keyboard_key_media_stop));
        this.mSpecialCharacterNames.put(87, context.getString(R$string.keyboard_key_media_next));
        this.mSpecialCharacterNames.put(88, context.getString(R$string.keyboard_key_media_previous));
        this.mSpecialCharacterNames.put(89, context.getString(R$string.keyboard_key_media_rewind));
        this.mSpecialCharacterNames.put(90, context.getString(R$string.keyboard_key_media_fast_forward));
        this.mSpecialCharacterNames.put(92, context.getString(R$string.keyboard_key_page_up));
        this.mSpecialCharacterNames.put(93, context.getString(R$string.keyboard_key_page_down));
        SparseArray sparseArray2 = this.mSpecialCharacterNames;
        int i2 = R$string.keyboard_key_button_template;
        sparseArray2.put(96, context.getString(i2, "A"));
        this.mSpecialCharacterNames.put(97, context.getString(i2, "B"));
        this.mSpecialCharacterNames.put(98, context.getString(i2, "C"));
        this.mSpecialCharacterNames.put(99, context.getString(i2, "X"));
        this.mSpecialCharacterNames.put(100, context.getString(i2, "Y"));
        this.mSpecialCharacterNames.put(101, context.getString(i2, "Z"));
        this.mSpecialCharacterNames.put(102, context.getString(i2, "L1"));
        this.mSpecialCharacterNames.put(103, context.getString(i2, "R1"));
        this.mSpecialCharacterNames.put(104, context.getString(i2, "L2"));
        this.mSpecialCharacterNames.put(105, context.getString(i2, "R2"));
        this.mSpecialCharacterNames.put(108, context.getString(i2, "Start"));
        this.mSpecialCharacterNames.put(109, context.getString(i2, "Select"));
        this.mSpecialCharacterNames.put(110, context.getString(i2, "Mode"));
        this.mSpecialCharacterNames.put(112, context.getString(R$string.keyboard_key_forward_del));
        this.mSpecialCharacterNames.put(111, "Esc");
        this.mSpecialCharacterNames.put(120, "SysRq");
        this.mSpecialCharacterNames.put(121, "Break");
        this.mSpecialCharacterNames.put(116, "Scroll Lock");
        this.mSpecialCharacterNames.put(122, context.getString(R$string.keyboard_key_move_home));
        this.mSpecialCharacterNames.put(123, context.getString(R$string.keyboard_key_move_end));
        this.mSpecialCharacterNames.put(124, context.getString(R$string.keyboard_key_insert));
        this.mSpecialCharacterNames.put(131, "F1");
        this.mSpecialCharacterNames.put(132, "F2");
        this.mSpecialCharacterNames.put(133, "F3");
        this.mSpecialCharacterNames.put(134, "F4");
        this.mSpecialCharacterNames.put(135, "F5");
        this.mSpecialCharacterNames.put(136, "F6");
        this.mSpecialCharacterNames.put(137, "F7");
        this.mSpecialCharacterNames.put(138, "F8");
        this.mSpecialCharacterNames.put(139, "F9");
        this.mSpecialCharacterNames.put(140, "F10");
        this.mSpecialCharacterNames.put(141, "F11");
        this.mSpecialCharacterNames.put(142, "F12");
        this.mSpecialCharacterNames.put(143, context.getString(R$string.keyboard_key_num_lock));
        SparseArray sparseArray3 = this.mSpecialCharacterNames;
        int i3 = R$string.keyboard_key_numpad_template;
        sparseArray3.put(144, context.getString(i3, "0"));
        this.mSpecialCharacterNames.put(145, context.getString(i3, "1"));
        this.mSpecialCharacterNames.put(146, context.getString(i3, "2"));
        this.mSpecialCharacterNames.put(147, context.getString(i3, "3"));
        this.mSpecialCharacterNames.put(148, context.getString(i3, "4"));
        this.mSpecialCharacterNames.put(149, context.getString(i3, "5"));
        this.mSpecialCharacterNames.put(150, context.getString(i3, "6"));
        this.mSpecialCharacterNames.put(151, context.getString(i3, "7"));
        this.mSpecialCharacterNames.put(152, context.getString(i3, "8"));
        this.mSpecialCharacterNames.put(153, context.getString(i3, "9"));
        this.mSpecialCharacterNames.put(154, context.getString(i3, "/"));
        this.mSpecialCharacterNames.put(155, context.getString(i3, "*"));
        this.mSpecialCharacterNames.put(156, context.getString(i3, "-"));
        this.mSpecialCharacterNames.put(157, context.getString(i3, "+"));
        this.mSpecialCharacterNames.put(158, context.getString(i3, "."));
        this.mSpecialCharacterNames.put(159, context.getString(i3, ","));
        this.mSpecialCharacterNames.put(160, context.getString(i3, context.getString(i)));
        this.mSpecialCharacterNames.put(161, context.getString(i3, "="));
        this.mSpecialCharacterNames.put(162, context.getString(i3, "("));
        this.mSpecialCharacterNames.put(163, context.getString(i3, ")"));
        this.mSpecialCharacterNames.put(211, "半角/全角");
        this.mSpecialCharacterNames.put(212, "英数");
        this.mSpecialCharacterNames.put(213, "無変換");
        this.mSpecialCharacterNames.put(214, "変換");
        this.mSpecialCharacterNames.put(215, "かな");
        this.mModifierNames.put(65536, "Meta");
        this.mModifierNames.put(4096, "Ctrl");
        this.mModifierNames.put(2, "Alt");
        this.mModifierNames.put(1, "Shift");
        this.mModifierNames.put(4, "Sym");
        this.mModifierNames.put(8, "Fn");
        this.mSpecialCharacterDrawables.put(67, context.getDrawable(R$drawable.ic_ksh_key_backspace));
        this.mSpecialCharacterDrawables.put(66, context.getDrawable(R$drawable.ic_ksh_key_enter));
        this.mSpecialCharacterDrawables.put(19, context.getDrawable(R$drawable.ic_ksh_key_up));
        this.mSpecialCharacterDrawables.put(22, context.getDrawable(R$drawable.ic_ksh_key_right));
        this.mSpecialCharacterDrawables.put(20, context.getDrawable(R$drawable.ic_ksh_key_down));
        this.mSpecialCharacterDrawables.put(21, context.getDrawable(R$drawable.ic_ksh_key_left));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r22v0 */
    /* JADX WARN: Type inference failed for: r22v1 */
    /* JADX WARN: Type inference failed for: r22v2 */
    /* JADX WARN: Type inference failed for: r22v3 */
    /* JADX WARN: Type inference failed for: r22v4, types: [int] */
    /* JADX WARN: Type inference failed for: r22v5 */
    /* JADX WARN: Type inference failed for: r25v0, types: [android.view.ViewGroup, android.widget.LinearLayout] */
    private void populateKeyboardShortcuts(LinearLayout linearLayout, List list) {
        boolean z;
        int i;
        int i2;
        KeyboardShortcutGroup keyboardShortcutGroup;
        int i3;
        ?? r22;
        int i4;
        KeyboardShortcutGroup keyboardShortcutGroup2;
        int i5;
        ?? r222;
        int i6;
        KeyboardShortcutHelperFragment keyboardShortcutHelperFragment = this;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(keyboardShortcutHelperFragment.mContext);
        int size = list.size();
        boolean z2 = false;
        TextView textView = (TextView) layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_key_view, (ViewGroup) null, false);
        textView.measure(0, 0);
        int measuredHeight = textView.getMeasuredHeight();
        int measuredHeight2 = (textView.getMeasuredHeight() - textView.getPaddingTop()) - textView.getPaddingBottom();
        int i7 = 0;
        while (i7 < size) {
            KeyboardShortcutGroup keyboardShortcutGroup3 = (KeyboardShortcutGroup) list.get(i7);
            TextView textView2 = (TextView) layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_category_title, (ViewGroup) linearLayout, z2);
            textView2.setText(keyboardShortcutGroup3.getLabel());
            textView2.setTextColor(keyboardShortcutGroup3.isSystemGroup() ? ColorStateList.valueOf(Utils.getColorAccent(keyboardShortcutHelperFragment.mContext)) : ColorStateList.valueOf(keyboardShortcutHelperFragment.mContext.getColor(R$color.ksh_application_group_color)));
            linearLayout.addView(textView2);
            LinearLayout linearLayout2 = (LinearLayout) layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_container, (ViewGroup) linearLayout, z2);
            int size2 = keyboardShortcutGroup3.getItems().size();
            ?? r12 = z2;
            while (r12 < size2) {
                KeyboardShortcutInfo keyboardShortcutInfo = keyboardShortcutGroup3.getItems().get(r12 == true ? 1 : 0);
                List humanReadableShortcutKeys = keyboardShortcutHelperFragment.getHumanReadableShortcutKeys(keyboardShortcutInfo);
                if (humanReadableShortcutKeys == null) {
                    Log.w("KeyboardShortcutHelperFragment", "Keyboard Shortcut contains unsupported keys, skipping.");
                    i = size;
                    i2 = measuredHeight2;
                    keyboardShortcutGroup = keyboardShortcutGroup3;
                    i3 = size2;
                    r22 = r12 == true ? 1 : 0;
                } else {
                    View viewInflate = layoutInflaterFrom.inflate(R$layout.keyboard_shortcut_app_item, linearLayout2, z2);
                    if (keyboardShortcutInfo.getIcon() != null) {
                        ImageView imageView = (ImageView) viewInflate.findViewById(R$id.keyboard_shortcuts_icon);
                        imageView.setImageIcon(keyboardShortcutInfo.getIcon());
                        imageView.setVisibility(0);
                    }
                    TextView textView3 = (TextView) viewInflate.findViewById(R$id.keyboard_shortcuts_keyword);
                    textView3.setText(keyboardShortcutInfo.getLabel());
                    if (keyboardShortcutInfo.getIcon() != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView3.getLayoutParams();
                        layoutParams.removeRule(20);
                        textView3.setLayoutParams(layoutParams);
                    }
                    ViewGroup viewGroup = (ViewGroup) viewInflate.findViewById(R$id.keyboard_shortcuts_item_container);
                    int size3 = humanReadableShortcutKeys.size();
                    int i8 = 0;
                    ?? r122 = r12;
                    while (i8 < size3) {
                        int i9 = size;
                        StringDrawableContainer stringDrawableContainer = (StringDrawableContainer) humanReadableShortcutKeys.get(i8);
                        int i10 = size3;
                        if (stringDrawableContainer.mDrawable != null) {
                            ImageView imageView2 = (ImageView) layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_key_icon_view, viewGroup, false);
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredHeight2, measuredHeight2, Bitmap.Config.ARGB_8888);
                            i4 = measuredHeight2;
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            keyboardShortcutGroup2 = keyboardShortcutGroup3;
                            i5 = size2;
                            r222 = r122;
                            i6 = i8;
                            stringDrawableContainer.mDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            stringDrawableContainer.mDrawable.draw(canvas);
                            imageView2.setImageBitmap(bitmapCreateBitmap);
                            imageView2.setImportantForAccessibility(1);
                            imageView2.setAccessibilityDelegate(new ShortcutKeyAccessibilityDelegate(stringDrawableContainer.mString));
                            viewGroup.addView(imageView2);
                        } else {
                            i4 = measuredHeight2;
                            keyboardShortcutGroup2 = keyboardShortcutGroup3;
                            i5 = size2;
                            r222 = r122;
                            i6 = i8;
                            if (stringDrawableContainer.mString != null) {
                                TextView textView4 = (TextView) layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_key_view, viewGroup, false);
                                textView4.setMinimumWidth(measuredHeight);
                                textView4.setText(stringDrawableContainer.mString);
                                textView4.setAccessibilityDelegate(new ShortcutKeyAccessibilityDelegate(stringDrawableContainer.mString));
                                viewGroup.addView(textView4);
                            }
                        }
                        i8 = i6 + 1;
                        size3 = i10;
                        size = i9;
                        measuredHeight2 = i4;
                        keyboardShortcutGroup3 = keyboardShortcutGroup2;
                        size2 = i5;
                        r122 = r222;
                    }
                    i = size;
                    i2 = measuredHeight2;
                    keyboardShortcutGroup = keyboardShortcutGroup3;
                    i3 = size2;
                    r22 = r122;
                    linearLayout2.addView(viewInflate);
                }
                keyboardShortcutHelperFragment = this;
                size = i;
                measuredHeight2 = i2;
                keyboardShortcutGroup3 = keyboardShortcutGroup;
                size2 = i3;
                z2 = false;
                r12 = r22 + 1;
            }
            int i11 = size;
            int i12 = measuredHeight2;
            linearLayout.addView(linearLayout2);
            if (i7 < i11 - 1) {
                z = false;
                linearLayout.addView(layoutInflaterFrom.inflate(R$layout.keyboard_shortcuts_category_separator, (ViewGroup) linearLayout, false));
            } else {
                z = false;
            }
            i7++;
            keyboardShortcutHelperFragment = this;
            z2 = z;
            size = i11;
            measuredHeight2 = i12;
        }
    }

    private void retrieveKeyCharacterMap(int i) {
        InputDevice inputDevice;
        InputManager inputManager = InputManager.getInstance();
        this.mBackupKeyCharacterMap = inputManager.getInputDevice(-1).getKeyCharacterMap();
        if (i != -1 && (inputDevice = inputManager.getInputDevice(i)) != null) {
            this.mKeyCharacterMap = inputDevice.getKeyCharacterMap();
            return;
        }
        for (int i2 : inputManager.getInputDeviceIds()) {
            InputDevice inputDevice2 = inputManager.getInputDevice(i2);
            if (inputDevice2.getId() != -1 && inputDevice2.isFullKeyboard()) {
                this.mKeyCharacterMap = inputDevice2.getKeyCharacterMap();
                return;
            }
        }
        this.mKeyCharacterMap = this.mBackupKeyCharacterMap;
    }

    private void showKeyboardShortcuts(int i) {
        retrieveKeyCharacterMap(i);
        ArrayList arrayList = new ArrayList();
        this.keyboardShortcutGroups = arrayList;
        arrayList.add(getSystemShortcuts());
        KeyboardShortcutGroup defaultApplicationShortcuts = getDefaultApplicationShortcuts();
        if (defaultApplicationShortcuts != null) {
            this.keyboardShortcutGroups.add(defaultApplicationShortcuts);
        }
    }

    protected List getHumanReadableModifiers(KeyboardShortcutInfo keyboardShortcutInfo) {
        ArrayList arrayList = new ArrayList();
        int modifiers = keyboardShortcutInfo.getModifiers();
        if (modifiers != 0) {
            int i = 0;
            while (true) {
                int[] iArr = this.mModifierList;
                if (i >= iArr.length) {
                    break;
                }
                int i2 = iArr[i];
                if ((modifiers & i2) != 0) {
                    arrayList.add(new StringDrawableContainer((String) this.mModifierNames.get(i2), (Drawable) this.mModifierDrawables.get(i2)));
                    modifiers &= ~i2;
                }
                i++;
            }
            if (modifiers != 0) {
                return null;
            }
        }
        return arrayList;
    }

    protected List getHumanReadableShortcutKeys(KeyboardShortcutInfo keyboardShortcutInfo) {
        String strValueOf;
        List humanReadableModifiers = getHumanReadableModifiers(keyboardShortcutInfo);
        Drawable drawable = null;
        if (humanReadableModifiers == null) {
            return null;
        }
        if (keyboardShortcutInfo.getBaseCharacter() > 0) {
            strValueOf = String.valueOf(keyboardShortcutInfo.getBaseCharacter());
        } else if (this.mSpecialCharacterDrawables.get(keyboardShortcutInfo.getKeycode()) != null) {
            drawable = (Drawable) this.mSpecialCharacterDrawables.get(keyboardShortcutInfo.getKeycode());
            strValueOf = (String) this.mSpecialCharacterNames.get(keyboardShortcutInfo.getKeycode());
        } else if (this.mSpecialCharacterNames.get(keyboardShortcutInfo.getKeycode()) != null) {
            strValueOf = (String) this.mSpecialCharacterNames.get(keyboardShortcutInfo.getKeycode());
        } else {
            if (keyboardShortcutInfo.getKeycode() == 0) {
                return humanReadableModifiers;
            }
            char displayLabel = this.mKeyCharacterMap.getDisplayLabel(keyboardShortcutInfo.getKeycode());
            if (displayLabel != 0) {
                strValueOf = String.valueOf(displayLabel);
            } else {
                char displayLabel2 = this.mBackupKeyCharacterMap.getDisplayLabel(keyboardShortcutInfo.getKeycode());
                if (displayLabel2 == 0) {
                    return null;
                }
                strValueOf = String.valueOf(displayLabel2);
            }
        }
        if (strValueOf != null) {
            humanReadableModifiers.add(new StringDrawableContainer(strValueOf, drawable));
            return humanReadableModifiers;
        }
        Log.w("KeyboardShortcutHelperFragment", "Keyboard Shortcut does not have a text representation, skipping.");
        return humanReadableModifiers;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mContext == null) {
            this.mContext = getContext();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContext = new ContextThemeWrapper(getContext(), R.style.Theme.DeviceDefault.Settings);
        this.mPackageManager = AppGlobals.getPackageManager();
        loadResources(getContext());
        showKeyboardShortcuts(-1);
        View viewInflate = layoutInflater.inflate(R$layout.keyboard_shortcuts_view, viewGroup, false);
        populateKeyboardShortcuts((LinearLayout) viewInflate.findViewById(R$id.keyboard_shortcuts_container), this.keyboardShortcutGroups);
        return viewInflate;
    }
}
