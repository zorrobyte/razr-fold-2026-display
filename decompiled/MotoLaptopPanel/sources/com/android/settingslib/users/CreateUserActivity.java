package com.android.settingslib.users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import com.android.settingslib.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class CreateUserActivity extends Activity {
    private CreateUserDialogController mCreateUserDialogController;
    Dialog mSetupUserDialog;

    private Dialog createDialog(boolean z) {
        return this.mCreateUserDialogController.createDialog(this, new ActivityStarter() { // from class: com.android.settingslib.users.CreateUserActivity$$ExternalSyntheticLambda0
            @Override // com.android.settingslib.users.ActivityStarter
            public final void startActivityForResult(Intent intent, int i) {
                this.f$0.startActivity(intent, i);
            }
        }, z, new NewUserData() { // from class: com.android.settingslib.users.CreateUserActivity$$ExternalSyntheticLambda1
            @Override // com.android.settingslib.users.NewUserData
            public final void onSuccess(String str, Drawable drawable, String str2, Boolean bool) {
                this.f$0.setSuccessResult(str, drawable, str2, bool);
            }
        }, new Runnable() { // from class: com.android.settingslib.users.CreateUserActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.cancel();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startActivity(Intent intent, int i) {
        startActivityForResult(intent, i);
        this.mCreateUserDialogController.startingActivityForResult();
    }

    void cancel() {
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        this.mCreateUserDialogController.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mCreateUserDialogController = new CreateUserDialogController(intent.getStringExtra("file_authority"));
        setContentView(R$layout.activity_create_new_user);
        if (bundle != null) {
            this.mCreateUserDialogController.onRestoreInstanceState(bundle);
        }
        Dialog dialogCreateDialog = createDialog(intent.getBooleanExtra("can_create_admin", false));
        this.mSetupUserDialog = dialogCreateDialog;
        dialogCreateDialog.show();
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        Dialog dialog;
        super.onRestoreInstanceState(bundle);
        Bundle bundle2 = bundle.getBundle("create_user_dialog_state");
        if (bundle2 == null || (dialog = this.mSetupUserDialog) == null) {
            return;
        }
        dialog.onRestoreInstanceState(bundle2);
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        Dialog dialog = this.mSetupUserDialog;
        if (dialog != null && dialog.isShowing()) {
            bundle.putBundle("create_user_dialog_state", this.mSetupUserDialog.onSaveInstanceState());
        }
        this.mCreateUserDialogController.onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        cancel();
        return super.onTouchEvent(motionEvent);
    }

    void setSuccessResult(String str, Drawable drawable, String str2, Boolean bool) {
        Intent intent = new Intent(this, (Class<?>) CreateUserActivity.class);
        intent.putExtra("new_user_name", str);
        intent.putExtra("is_admin", bool);
        intent.putExtra("user_icon_path", str2);
        setResult(-1, intent);
        finish();
    }
}
