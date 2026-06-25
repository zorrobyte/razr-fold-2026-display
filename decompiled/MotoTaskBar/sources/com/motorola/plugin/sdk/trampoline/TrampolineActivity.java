package com.motorola.plugin.sdk.trampoline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class TrampolineActivity extends Activity {
    public static final String EXTRA_KEY_TARGET_INTENT = "com.motorola.plugin.intent.extra.target_intent";
    public static final String TAG = "TrampolineActivity";

    public static Intent getLaunchIntent(Context context, StartActivityParams startActivityParams) {
        return new Intent(context, (Class<?>) TrampolineActivity.class).putExtra(EXTRA_KEY_TARGET_INTENT, startActivityParams).addFlags(270565376);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        TrampolineActivity trampolineActivity;
        Exception exc;
        super.onCreate(bundle);
        setVisible(false);
        StartActivityParams startActivityParams = (StartActivityParams) getIntent().getParcelableExtra(EXTRA_KEY_TARGET_INTENT);
        if (startActivityParams == null) {
            Log.d(TAG, "Trampoline activity started without params");
            finishAndRemoveTask();
            return;
        }
        try {
            Intent intent = startActivityParams.intent;
            if (intent != null) {
                try {
                    startActivity(intent, startActivityParams.options);
                    trampolineActivity = this;
                } catch (Exception e) {
                    exc = e;
                    trampolineActivity = this;
                    Log.e(TAG, "Trampoline activity started error", exc);
                }
            } else {
                IntentSender intentSender = startActivityParams.intentSender;
                try {
                    if (intentSender != null) {
                        trampolineActivity = this;
                        trampolineActivity.startIntentSender(intentSender, startActivityParams.fillInIntent, startActivityParams.flagsMask, startActivityParams.flagsValues, startActivityParams.extraFlags, startActivityParams.options);
                    } else {
                        trampolineActivity = this;
                        Log.w(TAG, "Trampoline activity started failed without intent");
                    }
                } catch (Exception e2) {
                    e = e2;
                    exc = e;
                    Log.e(TAG, "Trampoline activity started error", exc);
                }
            }
        } catch (Exception e3) {
            e = e3;
            trampolineActivity = this;
        }
        trampolineActivity.finish();
    }
}
