package com.android.systemui.statusbar;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dumpable;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationRemoteInputManager implements Dumpable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", true);
    protected Callback mCallback;
    private final NotificationClickNotifier mClickNotifier;
    protected final Context mContext;
    private final JavaAdapter mJavaAdapter;
    private final KeyguardManager mKeyguardManager;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    protected final NotifPipelineFlags mNotifPipelineFlags;
    protected RemoteInputController mRemoteInputController;
    private final RemoteInputControllerLogger mRemoteInputControllerLogger;
    private RemoteInputListener mRemoteInputListener;
    private final RemoteInputUriController mRemoteInputUriController;
    private final SmartReplyController mSmartReplyController;
    private final UserManager mUserManager;
    private final NotificationVisibilityProvider mVisibilityProvider;
    private final List mControllerCallbacks = new ArrayList();
    private final ListenerSet mActionPressListeners = new ListenerSet();
    private final RemoteViews.InteractionHandler mInteractionHandler = new AnonymousClass1();
    protected IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    /* JADX INFO: renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$1, reason: invalid class name */
    class AnonymousClass1 implements RemoteViews.InteractionHandler {
        AnonymousClass1() {
        }

        private Notification.Action getActionFromView(View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
            Integer num = (Integer) view.getTag(R.id.notification_material_reply_text_2);
            if (num == null) {
                return null;
            }
            if (notificationEntry == null) {
                Log.w("NotifRemoteInputManager", "Couldn't determine notification for click.");
                return null;
            }
            StatusBarNotification sbn = notificationEntry.getSbn();
            Notification.Action[] actionArr = sbn.getNotification().actions;
            if (actionArr == null || num.intValue() >= actionArr.length) {
                Log.w("NotifRemoteInputManager", "statusBarNotification.getNotification().actions is null or invalid");
                return null;
            }
            Notification.Action action = sbn.getNotification().actions[num.intValue()];
            if (Objects.equals(action.actionIntent, pendingIntent)) {
                return action;
            }
            Log.w("NotifRemoteInputManager", "actionIntent does not match");
            return null;
        }

        private NotificationEntry getNotificationForParent(ViewParent viewParent) {
            while (viewParent != null) {
                if (viewParent instanceof ExpandableNotificationRow) {
                    return ((ExpandableNotificationRow) viewParent).getEntry();
                }
                viewParent = viewParent.getParent();
            }
            return null;
        }

        private boolean handleRemoteInput(View view, PendingIntent pendingIntent) {
            if (NotificationRemoteInputManager.this.mCallback.shouldHandleRemoteInput(view, pendingIntent)) {
                return true;
            }
            Object tag = view.getTag(R.id.resolver_empty_state_title);
            RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
            if (remoteInputArr == null) {
                return false;
            }
            RemoteInput remoteInput = null;
            for (RemoteInput remoteInput2 : remoteInputArr) {
                if (remoteInput2.getAllowFreeFormInput()) {
                    remoteInput = remoteInput2;
                }
            }
            if (remoteInput == null) {
                return false;
            }
            return NotificationRemoteInputManager.this.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onInteraction$0(RemoteViews.RemoteResponse remoteResponse, View view, PendingIntent pendingIntent, NotificationEntry notificationEntry) {
            boolean zStartPendingIntent = RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
            if (zStartPendingIntent) {
                NotificationRemoteInputManager.this.releaseNotificationIfKeptForRemoteInputHistory(notificationEntry);
            }
            return zStartPendingIntent;
        }

        private void logActionClick(View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
            Notification.Action actionFromView = getActionFromView(view, notificationEntry, pendingIntent);
            if (actionFromView == null) {
                return;
            }
            ViewParent parent = view.getParent();
            NotificationRemoteInputManager.this.mClickNotifier.onNotificationActionClick(notificationEntry.getSbn().getKey(), (view.getId() == 16908758 && parent != null && (parent instanceof ViewGroup)) ? ((ViewGroup) parent).indexOfChild(view) : -1, actionFromView, NotificationRemoteInputManager.this.mVisibilityProvider.obtain(notificationEntry, true), false);
        }

        public boolean onInteraction(final View view, final PendingIntent pendingIntent, final RemoteViews.RemoteResponse remoteResponse) {
            Integer num = (Integer) view.getTag(R.id.notification_material_reply_text_2);
            final NotificationEntry notificationForParent = getNotificationForParent(view.getParent());
            if (handleRemoteInput(view, pendingIntent)) {
                return true;
            }
            logActionClick(view, notificationForParent, pendingIntent);
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused) {
            }
            Notification.Action actionFromView = getActionFromView(view, notificationForParent, pendingIntent);
            return NotificationRemoteInputManager.this.mCallback.handleRemoteViewClick(view, pendingIntent, actionFromView == null ? false : actionFromView.isAuthenticationRequired(), num, new ClickHandler() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$1$$ExternalSyntheticLambda0
                @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.ClickHandler
                public final boolean handleClick() {
                    return this.f$0.lambda$onInteraction$0(remoteResponse, view, pendingIntent, notificationForParent);
                }
            });
        }
    }

    public interface AuthBypassPredicate {
        boolean canSendRemoteInputWithoutBouncer();
    }

    public interface BouncerChecker {
        boolean showBouncerIfNecessary();
    }

    public interface Callback {
        boolean handleRemoteViewClick(View view, PendingIntent pendingIntent, boolean z, Integer num, ClickHandler clickHandler);

        void onLockedWorkRemoteInput(int i, ExpandableNotificationRow expandableNotificationRow, View view);

        void onMakeExpandedVisibleForRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view, boolean z, Runnable runnable);

        boolean shouldHandleRemoteInput(View view, PendingIntent pendingIntent);
    }

    public interface ClickHandler {
        boolean handleClick();
    }

    public interface RemoteInputListener {
        boolean isNotificationKeptForRemoteInputHistory(String str);

        void onRemoteInputSent(NotificationEntry notificationEntry);

        void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry);

        void setRemoteInputController(RemoteInputController remoteInputController);
    }

    public NotificationRemoteInputManager(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger, NotificationClickNotifier notificationClickNotifier, JavaAdapter javaAdapter) {
        this.mContext = context;
        this.mNotifPipelineFlags = notifPipelineFlags;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mSmartReplyController = smartReplyController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mJavaAdapter = javaAdapter;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mRemoteInputUriController = remoteInputUriController;
        this.mRemoteInputControllerLogger = remoteInputControllerLogger;
        this.mClickNotifier = notificationClickNotifier;
    }

    private RemoteInputView findRemoteInputView(View view) {
        if (view == null) {
            return null;
        }
        return (RemoteInputView) view.findViewWithTag(RemoteInputView.VIEW_TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$activateRemoteInput$1(AuthBypassPredicate authBypassPredicate, View view, PendingIntent pendingIntent, ExpandableNotificationRow expandableNotificationRow) {
        return !authBypassPredicate.canSendRemoteInputWithoutBouncer() && showBouncerForRemoteInput(view, pendingIntent, expandableNotificationRow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return;
        }
        RemoteInputListener remoteInputListener = this.mRemoteInputListener;
        if (remoteInputListener != null) {
            remoteInputListener.releaseNotificationIfKeptForRemoteInputHistory(notificationEntry);
        }
        Iterator it = this.mActionPressListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(notificationEntry);
        }
    }

    private boolean showBouncerForRemoteInput(View view, PendingIntent pendingIntent, ExpandableNotificationRow expandableNotificationRow) {
        UserInfo profileParent;
        int identifier = pendingIntent.getCreatorUserHandle().getIdentifier();
        boolean z = this.mUserManager.getUserInfo(identifier).isManagedProfile() && this.mKeyguardManager.isDeviceLocked(identifier);
        if (z && (profileParent = this.mUserManager.getProfileParent(identifier)) != null) {
            this.mKeyguardManager.isDeviceLocked(profileParent.id);
        }
        if (!z) {
            return false;
        }
        this.mCallback.onLockedWorkRemoteInput(identifier, expandableNotificationRow, view);
        return true;
    }

    public boolean activateRemoteInput(View view, RemoteInput[] remoteInputArr, RemoteInput remoteInput, PendingIntent pendingIntent, NotificationEntry.EditedSuggestionInfo editedSuggestionInfo) {
        return lambda$activateRemoteInput$0(view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo, null, null);
    }

    /* JADX INFO: renamed from: activateRemoteInput, reason: merged with bridge method [inline-methods] */
    public boolean lambda$activateRemoteInput$0(final View view, final RemoteInput[] remoteInputArr, final RemoteInput remoteInput, final PendingIntent pendingIntent, final NotificationEntry.EditedSuggestionInfo editedSuggestionInfo, final String str, final AuthBypassPredicate authBypassPredicate) {
        RemoteInputView remoteInputViewFindRemoteInputView;
        ExpandableNotificationRow expandableNotificationRow;
        ViewParent parent = view.getParent();
        while (true) {
            if (parent == null) {
                remoteInputViewFindRemoteInputView = null;
                expandableNotificationRow = null;
                break;
            }
            if (parent instanceof View) {
                View view2 = (View) parent;
                if (view2.getId() == 16909670) {
                    remoteInputViewFindRemoteInputView = findRemoteInputView(view2);
                    expandableNotificationRow = (ExpandableNotificationRow) view2.getTag(R$id.row_tag_for_content_view);
                    break;
                }
            }
            parent = parent.getParent();
        }
        if (expandableNotificationRow == null) {
            return false;
        }
        expandableNotificationRow.setUserExpanded(true);
        boolean z = authBypassPredicate != null;
        if (!z && showBouncerForRemoteInput(view, pendingIntent, expandableNotificationRow)) {
            return true;
        }
        RemoteInputView remoteInputViewFindRemoteInputView2 = (remoteInputViewFindRemoteInputView == null || remoteInputViewFindRemoteInputView.isAttachedToWindow()) ? remoteInputViewFindRemoteInputView : null;
        if (remoteInputViewFindRemoteInputView2 == null && (remoteInputViewFindRemoteInputView2 = findRemoteInputView(expandableNotificationRow.getPrivateLayout().getExpandedChild())) == null) {
            return false;
        }
        if (remoteInputViewFindRemoteInputView2 == expandableNotificationRow.getPrivateLayout().getExpandedRemoteInput() && !expandableNotificationRow.getPrivateLayout().getExpandedChild().isShown()) {
            this.mCallback.onMakeExpandedVisibleForRemoteInput(expandableNotificationRow, view, z, new Runnable(view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo, str, authBypassPredicate) { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0
                public final /* synthetic */ View f$1;
                public final /* synthetic */ RemoteInput[] f$2;
                public final /* synthetic */ RemoteInput f$3;
                public final /* synthetic */ PendingIntent f$4;
                public final /* synthetic */ NotificationEntry.EditedSuggestionInfo f$5;
                public final /* synthetic */ String f$6;

                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$activateRemoteInput$0(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, null);
                }
            });
            return true;
        }
        if (!remoteInputViewFindRemoteInputView2.isAttachedToWindow()) {
            return false;
        }
        remoteInputViewFindRemoteInputView2.getController().setPendingIntent(pendingIntent);
        remoteInputViewFindRemoteInputView2.getController().setRemoteInput(remoteInput);
        remoteInputViewFindRemoteInputView2.getController().setRemoteInputs(remoteInputArr);
        remoteInputViewFindRemoteInputView2.getController().setEditedSuggestionInfo(editedSuggestionInfo);
        remoteInputViewFindRemoteInputView2.focusAnimated();
        if (str != null) {
            remoteInputViewFindRemoteInputView2.setEditTextContent(str);
        }
        if (z) {
            final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
            remoteInputViewFindRemoteInputView2.getController().setBouncerChecker(new BouncerChecker(authBypassPredicate, view, pendingIntent, expandableNotificationRow2) { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda1
                public final /* synthetic */ View f$2;
                public final /* synthetic */ PendingIntent f$3;
                public final /* synthetic */ ExpandableNotificationRow f$4;

                {
                    this.f$2 = view;
                    this.f$3 = pendingIntent;
                    this.f$4 = expandableNotificationRow2;
                }

                @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.BouncerChecker
                public final boolean showBouncerIfNecessary() {
                    return this.f$0.lambda$activateRemoteInput$1(null, this.f$2, this.f$3, this.f$4);
                }
            });
        }
        return true;
    }

    public void addActionPressListener(Consumer consumer) {
        this.mActionPressListeners.addIfAbsent(consumer);
    }

    public void addControllerCallback(RemoteInputController.Callback callback) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            remoteInputController.addCallback(callback);
        } else {
            this.mControllerCallbacks.add(callback);
        }
    }

    public void bindRow(ExpandableNotificationRow expandableNotificationRow) {
        expandableNotificationRow.setRemoteInputController(this.mRemoteInputController);
    }

    public void cleanUpRemoteInputForUserRemoval(NotificationEntry notificationEntry) {
        if (isRemoteInputActive(notificationEntry)) {
            notificationEntry.mRemoteEditImeVisible = false;
            this.mRemoteInputController.removeRemoteInput(notificationEntry, null, "RemoteInputManager#cleanUpRemoteInputForUserRemoval");
        }
    }

    public void closeRemoteInputs() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            remoteInputController.closeRemoteInputs();
        }
    }

    public RemoteViews.InteractionHandler getRemoteViewsOnClickHandler() {
        return this.mInteractionHandler;
    }

    public boolean isNotificationKeptForRemoteInputHistory(String str) {
        RemoteInputListener remoteInputListener = this.mRemoteInputListener;
        return remoteInputListener != null && remoteInputListener.isNotificationKeptForRemoteInputHistory(str);
    }

    public boolean isRemoteInputActive() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive();
    }

    public boolean isRemoteInputActive(NotificationEntry notificationEntry) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive(notificationEntry);
    }

    public boolean isSpinning(String str) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isSpinning(str);
    }

    public void removeControllerCallback(RemoteInputController.Callback callback) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            remoteInputController.removeCallback(callback);
        } else {
            this.mControllerCallbacks.remove(callback);
        }
    }

    public void setRemoteInputListener(RemoteInputListener remoteInputListener) {
        if (this.mRemoteInputListener != null) {
            throw new IllegalStateException("mRemoteInputListener is already set");
        }
        this.mRemoteInputListener = remoteInputListener;
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            remoteInputListener.setRemoteInputController(remoteInputController);
        }
    }

    public void setUpWithCallback(Callback callback, RemoteInputController.Delegate delegate) {
        this.mCallback = callback;
        RemoteInputController remoteInputController = new RemoteInputController(delegate, this.mRemoteInputUriController, this.mRemoteInputControllerLogger);
        this.mRemoteInputController = remoteInputController;
        RemoteInputListener remoteInputListener = this.mRemoteInputListener;
        if (remoteInputListener != null) {
            remoteInputListener.setRemoteInputController(remoteInputController);
        }
        Iterator it = this.mControllerCallbacks.iterator();
        while (it.hasNext()) {
            this.mRemoteInputController.addCallback((RemoteInputController.Callback) it.next());
        }
        this.mControllerCallbacks.clear();
        this.mRemoteInputController.addCallback(new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager.2
            @Override // com.android.systemui.statusbar.RemoteInputController.Callback
            public void onRemoteInputSent(NotificationEntry notificationEntry) {
                if (NotificationRemoteInputManager.this.mRemoteInputListener != null) {
                    NotificationRemoteInputManager.this.mRemoteInputListener.onRemoteInputSent(notificationEntry);
                }
                try {
                    NotificationRemoteInputManager.this.mBarService.onNotificationDirectReplied(notificationEntry.getSbn().getKey());
                    NotificationEntry.EditedSuggestionInfo editedSuggestionInfo = notificationEntry.editedSuggestionInfo;
                    if (editedSuggestionInfo != null) {
                        boolean z = !TextUtils.equals(notificationEntry.remoteInputText, editedSuggestionInfo.originalText);
                        IStatusBarService iStatusBarService = NotificationRemoteInputManager.this.mBarService;
                        String key = notificationEntry.getSbn().getKey();
                        NotificationEntry.EditedSuggestionInfo editedSuggestionInfo2 = notificationEntry.editedSuggestionInfo;
                        iStatusBarService.onNotificationSmartReplySent(key, editedSuggestionInfo2.index, editedSuggestionInfo2.originalText, NotificationLogger.getNotificationLocation(notificationEntry).toMetricsEventEnum(), z);
                    }
                } catch (RemoteException unused) {
                }
            }
        });
    }

    public boolean shouldKeepForRemoteInputHistory(NotificationEntry notificationEntry) {
        if (FORCE_REMOTE_INPUT_HISTORY) {
            return isSpinning(notificationEntry.getKey()) || notificationEntry.hasJustSentRemoteInput();
        }
        return false;
    }

    public boolean shouldKeepForSmartReplyHistory(NotificationEntry notificationEntry) {
        if (FORCE_REMOTE_INPUT_HISTORY) {
            return this.mSmartReplyController.isSendingSmartReply(notificationEntry.getKey());
        }
        return false;
    }
}
