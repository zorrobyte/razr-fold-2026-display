package com.android.settingslib.users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.internal.util.UserIcons;
import com.android.settingslib.R$drawable;
import com.android.settingslib.R$id;
import com.android.settingslib.R$layout;
import com.android.settingslib.R$string;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import java.io.File;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class CreateUserDialogController {
    private Activity mActivity;
    private ActivityStarter mActivityStarter;
    private String mCachedDrawablePath;
    private Runnable mCancelCallback;
    private int mCurrentState;
    private CustomDialogHelper mCustomDialogHelper;
    private View mEditUserInfoView;
    private EditUserPhotoController mEditUserPhotoController;
    private final String mFileAuthority;
    private View mGrantAdminView;
    private Boolean mIsAdmin;
    private Drawable mNewUserIcon;
    private Drawable mSavedDrawable;
    private String mSavedName;
    private Bitmap mSavedPhoto;
    private NewUserData mSuccessCallback;
    private Dialog mUserCreationDialog;
    private String mUserName;
    private EditText mUserNameView;
    private boolean mWaitingForActivityResult;

    public CreateUserDialogController(String str) {
        this.mFileAuthority = str;
    }

    private void addCustomViews(final boolean z) {
        addGrantAdminView();
        addUserInfoEditView();
        this.mCustomDialogHelper.setPositiveButton(R$string.next, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$addCustomViews$1(z, view);
            }
        });
        this.mCustomDialogHelper.setNegativeButton(R$string.back, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$addCustomViews$2(z, view);
            }
        });
    }

    private void addGrantAdminView() {
        View viewInflate = View.inflate(this.mActivity, R$layout.grant_admin_dialog_content, null);
        this.mGrantAdminView = viewInflate;
        this.mCustomDialogHelper.addCustomView(viewInflate);
        RadioGroup radioGroup = (RadioGroup) this.mGrantAdminView.findViewById(R$id.choose_admin);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda4
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                this.f$0.lambda$addGrantAdminView$5(radioGroup2, i);
            }
        });
        if (Boolean.TRUE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R$id.grant_admin_yes)).setChecked(true);
        } else if (Boolean.FALSE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R$id.grant_admin_no)).setChecked(true);
        }
    }

    private void addUserInfoEditView() {
        View viewInflate = View.inflate(this.mActivity, R$layout.edit_user_info_dialog_content, null);
        this.mEditUserInfoView = viewInflate;
        this.mCustomDialogHelper.addCustomView(viewInflate);
        setUserName();
        ImageView imageView = (ImageView) this.mEditUserInfoView.findViewById(R$id.user_photo);
        setUserIcon(UserIcons.getDefaultUserIcon(this.mActivity.getResources(), -10000, false), imageView);
        if (isChangePhotoRestrictedByBase(this.mActivity)) {
            this.mEditUserInfoView.findViewById(R$id.add_a_photo_icon).setVisibility(8);
            return;
        }
        final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = getChangePhotoAdminRestriction(this.mActivity);
        if (changePhotoAdminRestriction != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addUserInfoEditView$4(changePhotoAdminRestriction, view);
                }
            });
        } else {
            this.mEditUserPhotoController = createEditUserPhotoController(imageView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCustomViews$1(boolean z, View view) {
        int i = this.mCurrentState;
        int i2 = i + 1;
        this.mCurrentState = i2;
        if (i2 == 1 && !z) {
            this.mCurrentState = i + 2;
        }
        updateLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCustomViews$2(boolean z, View view) {
        int i = this.mCurrentState;
        int i2 = i - 1;
        this.mCurrentState = i2;
        if (i2 == 1 && !z) {
            this.mCurrentState = i - 2;
        }
        updateLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGrantAdminView$5(RadioGroup radioGroup, int i) {
        this.mCustomDialogHelper.setButtonEnabled(true);
        this.mIsAdmin = Boolean.valueOf(i == R$id.grant_admin_yes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addUserInfoEditView$4(RestrictedLockUtils.EnforcedAdmin enforcedAdmin, View view) {
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mActivity, enforcedAdmin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDialog$0(DialogInterface dialogInterface) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Drawable lambda$setUserIcon$3() {
        Bitmap bitmapLoadNewUserPhotoBitmap = EditUserPhotoController.loadNewUserPhotoBitmap(new File(this.mCachedDrawablePath));
        this.mSavedPhoto = bitmapLoadNewUserPhotoBitmap;
        CircleFramedDrawable circleFramedDrawable = CircleFramedDrawable.getInstance(this.mActivity, bitmapLoadNewUserPhotoBitmap);
        this.mSavedDrawable = circleFramedDrawable;
        return circleFramedDrawable;
    }

    private void setUserIcon(Drawable drawable, final ImageView imageView) {
        if (this.mCachedDrawablePath != null) {
            Futures.addCallback(ThreadUtils.getBackgroundExecutor().submit(new Callable() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda5
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$setUserIcon$3();
                }
            }), new FutureCallback(this) { // from class: com.android.settingslib.users.CreateUserDialogController.1
                @Override // com.google.common.util.concurrent.FutureCallback
                public void onFailure(Throwable th) {
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public void onSuccess(Drawable drawable2) {
                    imageView.setImageDrawable(drawable2);
                }
            }, this.mActivity.getMainExecutor());
        } else {
            imageView.setImageDrawable(drawable);
        }
    }

    private void setUserName() {
        EditText editText = (EditText) this.mEditUserInfoView.findViewById(R$id.user_name);
        this.mUserNameView = editText;
        String str = this.mSavedName;
        if (str == null) {
            editText.setText(R$string.user_new_user_name);
        } else {
            editText.setText(str);
        }
    }

    private void updateLayout() {
        int i = this.mCurrentState;
        if (i == -1) {
            this.mUserCreationDialog.dismiss();
            return;
        }
        if (i == 0) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(8);
            SharedPreferences preferences = this.mActivity.getPreferences(0);
            boolean z = preferences.getBoolean("key_add_user_long_message_displayed", false);
            int i2 = z ? R$string.user_add_user_message_short : R$string.user_add_user_message_long;
            if (!z) {
                preferences.edit().putBoolean("key_add_user_long_message_displayed", true).apply();
            }
            this.mCustomDialogHelper.setVisibility(0, true).setVisibility(2, true).setIcon(this.mActivity.getDrawable(R$drawable.ic_person_add)).setButtonEnabled(true).setTitle(R$string.user_add_user_title).setMessage(i2).setNegativeButtonText(R$string.cancel).setPositiveButtonText(R$string.next);
            focus();
            return;
        }
        if (i == 1) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(0);
            this.mCustomDialogHelper.setVisibility(0, true).setVisibility(2, true).setIcon(this.mActivity.getDrawable(R$drawable.ic_admin_panel_settings)).setTitle(R$string.user_grant_admin_title).setMessage(R$string.user_grant_admin_message).setNegativeButtonText(R$string.back).setPositiveButtonText(R$string.next);
            focus();
            if (this.mIsAdmin == null) {
                this.mCustomDialogHelper.setButtonEnabled(false);
                return;
            }
            return;
        }
        if (i == 2) {
            this.mCustomDialogHelper.setVisibility(0, false).setVisibility(2, false).setTitle(R$string.user_info_settings_title).setNegativeButtonText(R$string.back).setPositiveButtonText(R$string.done);
            focus();
            this.mEditUserInfoView.setVisibility(0);
            this.mGrantAdminView.setVisibility(8);
            return;
        }
        if (i != 3) {
            if (i < -1) {
                this.mCurrentState = -1;
                updateLayout();
                return;
            } else {
                this.mCurrentState = 3;
                updateLayout();
                return;
            }
        }
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        this.mNewUserIcon = (editUserPhotoController == null || editUserPhotoController.getNewUserPhotoDrawable() == null) ? this.mSavedDrawable : this.mEditUserPhotoController.getNewUserPhotoDrawable();
        String strTrim = this.mUserNameView.getText().toString().trim();
        String string = this.mActivity.getString(R$string.user_new_user_name);
        if (strTrim.isEmpty()) {
            strTrim = string;
        }
        this.mUserName = strTrim;
        this.mCustomDialogHelper.getDialog().dismiss();
    }

    public void clear() {
        this.mUserCreationDialog = null;
        this.mCustomDialogHelper = null;
        this.mEditUserPhotoController = null;
        this.mSavedPhoto = null;
        this.mSavedName = null;
        this.mSavedDrawable = null;
        this.mIsAdmin = null;
        this.mActivity = null;
        this.mActivityStarter = null;
        this.mGrantAdminView = null;
        this.mEditUserInfoView = null;
        this.mUserNameView = null;
        this.mSuccessCallback = null;
        this.mCancelCallback = null;
        this.mCachedDrawablePath = null;
        this.mCurrentState = 0;
    }

    public Dialog createDialog(Activity activity, ActivityStarter activityStarter, boolean z, NewUserData newUserData, Runnable runnable) {
        this.mActivity = activity;
        this.mCustomDialogHelper = new CustomDialogHelper(activity);
        this.mSuccessCallback = newUserData;
        this.mCancelCallback = runnable;
        this.mActivityStarter = activityStarter;
        addCustomViews(z);
        this.mUserCreationDialog = this.mCustomDialogHelper.getDialog();
        updateLayout();
        this.mUserCreationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createDialog$0(dialogInterface);
            }
        });
        this.mCustomDialogHelper.setMessagePadding(10);
        this.mUserCreationDialog.setCanceledOnTouchOutside(true);
        return this.mUserCreationDialog;
    }

    EditUserPhotoController createEditUserPhotoController(ImageView imageView) {
        return new EditUserPhotoController(this.mActivity, this.mActivityStarter, imageView, this.mSavedPhoto, this.mSavedDrawable, this.mFileAuthority);
    }

    public void finish() {
        if (this.mCurrentState != 3) {
            Runnable runnable = this.mCancelCallback;
            if (runnable != null) {
                runnable.run();
            }
        } else if (this.mSuccessCallback != null) {
            EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
            if (editUserPhotoController != null && this.mCachedDrawablePath == null) {
                this.mCachedDrawablePath = editUserPhotoController.getCachedDrawablePath();
            }
            this.mSuccessCallback.onSuccess(this.mUserName, this.mNewUserIcon, this.mCachedDrawablePath, Boolean.valueOf(Boolean.TRUE.equals(this.mIsAdmin)));
        }
        clear();
    }

    void focus() {
        this.mCustomDialogHelper.requestFocusOnTitle();
    }

    RestrictedLockUtils.EnforcedAdmin getChangePhotoAdminRestriction(Context context) {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_set_user_icon", UserHandle.myUserId());
    }

    boolean isChangePhotoRestrictedByBase(Context context) {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(context, "no_set_user_icon", UserHandle.myUserId());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mWaitingForActivityResult = false;
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController != null) {
            editUserPhotoController.onActivityResult(i, i2, intent);
        }
    }

    public void onRestoreInstanceState(Bundle bundle) {
        this.mCachedDrawablePath = bundle.getString("pending_photo");
        this.mCurrentState = bundle.getInt("current_state");
        if (bundle.containsKey("admin_status")) {
            this.mIsAdmin = Boolean.valueOf(bundle.getBoolean("admin_status"));
        }
        this.mSavedName = bundle.getString("saved_name");
        this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
    }

    public void onSaveInstanceState(Bundle bundle) {
        EditUserPhotoController editUserPhotoController;
        if (this.mUserCreationDialog != null && (editUserPhotoController = this.mEditUserPhotoController) != null && this.mCachedDrawablePath == null) {
            this.mCachedDrawablePath = editUserPhotoController.getCachedDrawablePath();
        }
        String str = this.mCachedDrawablePath;
        if (str != null) {
            bundle.putString("pending_photo", str);
        }
        Boolean bool = this.mIsAdmin;
        if (bool != null) {
            bundle.putBoolean("admin_status", Boolean.TRUE.equals(bool));
        }
        bundle.putString("saved_name", this.mUserNameView.getText().toString().trim());
        bundle.putInt("current_state", this.mCurrentState);
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
    }

    public void startingActivityForResult() {
        this.mWaitingForActivityResult = true;
    }
}
