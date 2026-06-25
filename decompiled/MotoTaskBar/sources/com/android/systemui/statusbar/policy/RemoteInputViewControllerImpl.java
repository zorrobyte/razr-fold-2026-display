package com.android.systemui.statusbar.policy;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.ArrayIteratorKt;

/* JADX INFO: compiled from: RemoteInputViewController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputViewControllerImpl implements RemoteInputViewController {
    private NotificationRemoteInputManager.BouncerChecker bouncerChecker;
    private final NotificationEntry entry;
    private boolean isBound;
    private final FeatureFlags mFlags;
    private final View.OnFocusChangeListener onFocusChangeListener;
    private final ArraySet onSendListeners;
    private final Runnable onSendRemoteInputListener;
    private PendingIntent pendingIntent;
    private RemoteInput remoteInput;
    private final RemoteInputController remoteInputController;
    private RemoteInput[] remoteInputs;
    private final ShortcutManager shortcutManager;
    private final UiEventLogger uiEventLogger;
    private final RemoteInputView view;

    public RemoteInputViewControllerImpl(RemoteInputView remoteInputView, NotificationEntry notificationEntry, RemoteInputController remoteInputController, ShortcutManager shortcutManager, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        remoteInputView.getClass();
        notificationEntry.getClass();
        remoteInputController.getClass();
        shortcutManager.getClass();
        uiEventLogger.getClass();
        featureFlags.getClass();
        this.view = remoteInputView;
        this.entry = notificationEntry;
        this.remoteInputController = remoteInputController;
        this.shortcutManager = shortcutManager;
        this.uiEventLogger = uiEventLogger;
        this.mFlags = featureFlags;
        this.onSendListeners = new ArraySet();
        this.onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onFocusChangeListener$1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
            }
        };
        this.onSendRemoteInputListener = new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onSendRemoteInputListener$1
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInput remoteInput = this.this$0.getRemoteInput();
                if (remoteInput == null) {
                    Log.e("RemoteInput", "cannot send remote input, RemoteInput data is null");
                    return;
                }
                PendingIntent pendingIntent = this.this$0.getPendingIntent();
                if (pendingIntent == null) {
                    Log.e("RemoteInput", "cannot send remote input, PendingIntent is null");
                } else {
                    this.this$0.sendRemoteInput(pendingIntent, this.this$0.prepareRemoteInput(remoteInput));
                }
            }
        };
    }

    private final int getRemoteInputResultsSource() {
        return this.entry.editedSuggestionInfo != null ? 1 : 0;
    }

    private final Resources getResources() {
        return this.view.getResources();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Intent prepareRemoteInput(RemoteInput remoteInput) {
        NotificationEntry notificationEntry = this.entry;
        if (notificationEntry.remoteInputAttachment == null) {
            return prepareRemoteInputFromText(remoteInput);
        }
        String str = notificationEntry.remoteInputMimeType;
        str.getClass();
        Uri uri = this.entry.remoteInputUri;
        uri.getClass();
        return prepareRemoteInputFromData(remoteInput, str, uri);
    }

    private final Intent prepareRemoteInputFromData(RemoteInput remoteInput, String str, Uri uri) {
        HashMap map = new HashMap();
        map.put(str, uri);
        this.remoteInputController.grantInlineReplyUriPermission(this.entry.getSbn(), uri);
        Intent intentAddFlags = new Intent().addFlags(268435456);
        intentAddFlags.getClass();
        RemoteInput.addDataResultToIntent(remoteInput, intentAddFlags, map);
        Bundle bundle = new Bundle();
        bundle.putString(remoteInput.getResultKey(), this.view.getText().toString());
        RemoteInput.addResultsToIntent(getRemoteInputs(), intentAddFlags, bundle);
        CharSequence label = this.entry.remoteInputAttachment.getClip().getDescription().getLabel();
        label.getClass();
        if (TextUtils.isEmpty(label)) {
            label = getResources().getString(R$string.remote_input_image_insertion_text);
            label.getClass();
        }
        if (!TextUtils.isEmpty(this.view.getText())) {
            label = "\"" + ((Object) label) + "\" " + ((Object) this.view.getText());
        }
        this.entry.remoteInputText = label;
        RemoteInput.setResultsSource(intentAddFlags, getRemoteInputResultsSource());
        return intentAddFlags;
    }

    private final Intent prepareRemoteInputFromText(RemoteInput remoteInput) {
        Bundle bundle = new Bundle();
        bundle.putString(remoteInput.getResultKey(), this.view.getText().toString());
        Intent intentAddFlags = new Intent().addFlags(268435456);
        intentAddFlags.getClass();
        RemoteInput.addResultsToIntent(getRemoteInputs(), intentAddFlags, bundle);
        this.entry.remoteInputText = this.view.getText();
        this.view.clearAttachment();
        NotificationEntry notificationEntry = this.entry;
        notificationEntry.remoteInputUri = null;
        notificationEntry.remoteInputMimeType = null;
        RemoteInput.setResultsSource(intentAddFlags, getRemoteInputResultsSource());
        return intentAddFlags;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendRemoteInput(PendingIntent pendingIntent, Intent intent) {
        NotificationRemoteInputManager.BouncerChecker bouncerChecker = getBouncerChecker();
        if (bouncerChecker != null && bouncerChecker.showBouncerIfNecessary()) {
            this.view.hideIme();
            Iterator it = CollectionsKt.toList(this.onSendListeners).iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            return;
        }
        this.view.startSending();
        this.entry.lastRemoteInputSent = SystemClock.elapsedRealtime();
        NotificationEntry notificationEntry = this.entry;
        notificationEntry.mRemoteEditImeAnimatingAway = true;
        this.remoteInputController.addSpinning(notificationEntry.getKey(), this.view.mToken);
        this.remoteInputController.removeRemoteInput(this.entry, this.view.mToken, "RemoteInputViewController#sendRemoteInput");
        this.remoteInputController.remoteInputSent(this.entry);
        this.entry.setHasSentReply();
        Iterator it2 = CollectionsKt.toList(this.onSendListeners).iterator();
        if (it2.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
            throw null;
        }
        this.shortcutManager.onApplicationActive(this.entry.getSbn().getPackageName(), this.entry.getSbn().getUser().getIdentifier());
        this.uiEventLogger.logWithInstanceId(RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_SEND, this.entry.getSbn().getUid(), this.entry.getSbn().getPackageName(), this.entry.getSbn().getInstanceId());
        try {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(this.view.getContext(), 0, intent, null, null, null, activityOptionsMakeBasic.toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.i("RemoteInput", "Unable to send remote input result", e);
            this.uiEventLogger.logWithInstanceId(RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_FAILURE, this.entry.getSbn().getUid(), this.entry.getSbn().getPackageName(), this.entry.getSbn().getInstanceId());
        }
        this.view.clearAttachment();
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void bind() {
        if (this.isBound) {
            return;
        }
        this.isBound = true;
        RemoteInput remoteInput = getRemoteInput();
        if (remoteInput != null) {
            this.view.setHintText(remoteInput.getLabel());
            this.view.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
        }
        this.view.addOnEditTextFocusChangedListener(this.onFocusChangeListener);
        this.view.addOnSendRemoteInputListener(this.onSendRemoteInputListener);
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void close() {
        this.view.close();
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void focus() {
        this.view.focus();
    }

    public NotificationRemoteInputManager.BouncerChecker getBouncerChecker() {
        return this.bouncerChecker;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public RemoteInput getRemoteInput() {
        return this.remoteInput;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public RemoteInput[] getRemoteInputs() {
        return this.remoteInputs;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public boolean isActive() {
        return this.view.isActive();
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void setBouncerChecker(NotificationRemoteInputManager.BouncerChecker bouncerChecker) {
        this.bouncerChecker = bouncerChecker;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void setEditedSuggestionInfo(NotificationEntry.EditedSuggestionInfo editedSuggestionInfo) {
        NotificationEntry notificationEntry = this.entry;
        notificationEntry.editedSuggestionInfo = editedSuggestionInfo;
        if (editedSuggestionInfo != null) {
            notificationEntry.remoteInputText = editedSuggestionInfo.originalText;
            notificationEntry.remoteInputAttachment = null;
        }
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void setRemoteInput(RemoteInput remoteInput) {
        this.remoteInput = remoteInput;
        if (remoteInput != null) {
            if (!this.isBound) {
                remoteInput = null;
            }
            if (remoteInput != null) {
                this.view.setHintText(remoteInput.getLabel());
                this.view.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void setRemoteInputs(RemoteInput[] remoteInputArr) {
        this.remoteInputs = remoteInputArr;
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public void unbind() {
        if (this.isBound) {
            this.isBound = false;
            this.view.removeOnEditTextFocusChangedListener(this.onFocusChangeListener);
            this.view.removeOnSendRemoteInputListener(this.onSendRemoteInputListener);
        }
    }

    @Override // com.android.systemui.statusbar.policy.RemoteInputViewController
    public boolean updatePendingIntentFromActions(Notification.Action[] actionArr) {
        PendingIntent pendingIntent;
        Intent intent;
        RemoteInput[] remoteInputs;
        RemoteInput remoteInput;
        if (actionArr != null && (pendingIntent = getPendingIntent()) != null && (intent = pendingIntent.getIntent()) != null) {
            Iterator it = ArrayIteratorKt.iterator(actionArr);
            while (it.hasNext()) {
                Notification.Action action = (Notification.Action) it.next();
                PendingIntent pendingIntent2 = action.actionIntent;
                if (pendingIntent2 != null && (remoteInputs = action.getRemoteInputs()) != null && intent.filterEquals(pendingIntent2.getIntent())) {
                    int length = remoteInputs.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            remoteInput = null;
                            break;
                        }
                        remoteInput = remoteInputs[i];
                        if (remoteInput.getAllowFreeFormInput()) {
                            break;
                        }
                        i++;
                    }
                    if (remoteInput != null) {
                        setPendingIntent(pendingIntent2);
                        setRemoteInput(remoteInput);
                        setRemoteInputs(remoteInputs);
                        setEditedSuggestionInfo(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
