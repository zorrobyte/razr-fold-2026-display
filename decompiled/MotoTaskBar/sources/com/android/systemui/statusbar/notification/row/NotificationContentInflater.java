package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.app.tracing.TraceUtils;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.SingleLineConversationViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.SingleLineViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.util.Assert;
import java.util.HashMap;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* JADX INFO: loaded from: classes.dex */
public class NotificationContentInflater implements NotificationRowContentBinder {
    private final ConversationNotificationProcessor mConversationProcessor;
    private final HeadsUpStyleProvider mHeadsUpStyleProvider;
    private boolean mInflateSynchronously = false;
    private final Executor mInflationExecutor;
    private final boolean mIsMediaInQS;
    private final NotificationContentInflaterLogger mLogger;
    private final NotifLayoutInflaterFactory.Provider mNotifLayoutInflaterFactoryProvider;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final NotifRemoteViewCache mRemoteViewCache;
    private final SmartReplyStateInflater mSmartReplyStateInflater;

    abstract class ApplyCallback {
        ApplyCallback() {
        }

        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    public class AsyncInflationTask extends AsyncTask implements NotificationRowContentBinder.InflationCallback, InflationTask {
        private final NotificationRowContentBinder.InflationCallback mCallback;
        private CancellationSignal mCancellationSignal;
        private final Context mContext;
        private final ConversationNotificationProcessor mConversationProcessor;
        private final NotificationEntry mEntry;
        private Exception mError;
        private final HeadsUpStyleProvider mHeadsUpStyleProvider;
        private final boolean mInflateSynchronously;
        private final Executor mInflationExecutor;
        private final boolean mIsMediaInQS;
        private final boolean mIsMinimized;
        private final NotificationContentInflaterLogger mLogger;
        private final NotifLayoutInflaterFactory.Provider mNotifLayoutInflaterFactoryProvider;
        private final int mReInflateFlags;
        private final NotifRemoteViewCache mRemoteViewCache;
        private RemoteViews.InteractionHandler mRemoteViewClickHandler;
        private ExpandableNotificationRow mRow;
        private final SmartReplyStateInflater mSmartRepliesInflater;
        private final boolean mUsesIncreasedHeadsUpHeight;
        private final boolean mUsesIncreasedHeight;

        class RtlEnabledContext extends ContextWrapper {
            private RtlEnabledContext(Context context) {
                super(context);
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = new ApplicationInfo(super.getApplicationInfo());
                applicationInfo.flags |= 4194304;
                return applicationInfo;
            }
        }

        private AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, NotificationRowContentBinder.InflationCallback inflationCallback, RemoteViews.InteractionHandler interactionHandler, boolean z5, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationContentInflaterLogger notificationContentInflaterLogger) {
            this.mEntry = notificationEntry;
            this.mRow = expandableNotificationRow;
            this.mInflationExecutor = executor;
            this.mInflateSynchronously = z;
            this.mReInflateFlags = i;
            this.mRemoteViewCache = notifRemoteViewCache;
            this.mSmartRepliesInflater = smartReplyStateInflater;
            this.mContext = expandableNotificationRow.getContext();
            this.mIsMinimized = z2;
            this.mUsesIncreasedHeight = z3;
            this.mUsesIncreasedHeadsUpHeight = z4;
            this.mRemoteViewClickHandler = interactionHandler;
            this.mCallback = inflationCallback;
            this.mConversationProcessor = conversationNotificationProcessor;
            this.mIsMediaInQS = z5;
            this.mNotifLayoutInflaterFactoryProvider = provider;
            this.mHeadsUpStyleProvider = headsUpStyleProvider;
            this.mLogger = notificationContentInflaterLogger;
            notificationEntry.setInflationTask(this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private InflationProgress doInBackgroundInternal() {
            StatusBarNotification sbn = this.mEntry.getSbn();
            updateApplicationInfo(sbn);
            Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(this.mContext, sbn.getNotification());
            Context packageContext = sbn.getPackageContext(this.mContext);
            Context rtlEnabledContext = builderRecoverBuilder.usesTemplate() ? new RtlEnabledContext(packageContext) : packageContext;
            boolean zIsConversation = this.mEntry.getRanking().isConversation();
            Notification.MessagingStyle messagingStyleProcessNotification = zIsConversation ? this.mConversationProcessor.processNotification(this.mEntry, builderRecoverBuilder, this.mLogger) : null;
            InflationProgress inflationProgressCreateRemoteViews = NotificationContentInflater.createRemoteViews(this.mReInflateFlags, builderRecoverBuilder, this.mIsMinimized, this.mUsesIncreasedHeight, this.mUsesIncreasedHeadsUpHeight, rtlEnabledContext, this.mRow, this.mNotifLayoutInflaterFactoryProvider, this.mHeadsUpStyleProvider, this.mLogger);
            this.mLogger.logAsyncTaskProgress(this.mEntry, "getting existing smart reply state (on wrong thread!)");
            InflatedSmartReplyState existingSmartReplyState = this.mRow.getExistingSmartReplyState();
            this.mLogger.logAsyncTaskProgress(this.mEntry, "inflating smart reply views");
            InflationProgress inflationProgressInflateSmartReplyViews = NotificationContentInflater.inflateSmartReplyViews(inflationProgressCreateRemoteViews, this.mReInflateFlags, this.mEntry, this.mContext, rtlEnabledContext, existingSmartReplyState, this.mSmartRepliesInflater, this.mLogger);
            if (AsyncHybridViewInflation.isEnabled()) {
                inflationProgressInflateSmartReplyViews.mInflatedSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(this.mEntry.getSbn().getNotification(), messagingStyleProcessNotification, builderRecoverBuilder, this.mContext);
                inflationProgressInflateSmartReplyViews.mInflatedSingleLineViewHolder = SingleLineViewInflater.inflateSingleLineViewHolder(zIsConversation, this.mReInflateFlags, this.mEntry, this.mContext, this.mLogger);
            }
            this.mLogger.logAsyncTaskProgress(this.mEntry, "getting row image resolver (on wrong thread!)");
            NotificationInlineImageResolver imageResolver = this.mRow.getImageResolver();
            this.mLogger.logAsyncTaskProgress(this.mEntry, "waiting for preloaded images");
            imageResolver.waitForPreloadedImages(1000L);
            return inflationProgressInflateSmartReplyViews;
        }

        private void handleError(Exception exc) {
            this.mEntry.onInflationTaskFinished();
            StatusBarNotification sbn = this.mEntry.getSbn();
            Log.e("NotifContentInflater", "couldn't inflate view for notification " + (sbn.getPackageName() + "/0x" + Integer.toHexString(sbn.getId())), exc);
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(this.mRow.getEntry(), new InflationException("Couldn't inflate contentViews" + exc));
            }
            this.mRow.getImageResolver().cancelRunningTasks();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ InflationProgress lambda$doInBackground$0() {
            try {
                return doInBackgroundInternal();
            } catch (Exception e) {
                this.mError = e;
                this.mLogger.logAsyncTaskException(this.mEntry, "inflating", e);
                return null;
            }
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public void abort() {
            this.mLogger.logAsyncTaskProgress(this.mEntry, "cancelling inflate");
            cancel(true);
            if (this.mCancellationSignal != null) {
                this.mLogger.logAsyncTaskProgress(this.mEntry, "cancelling apply");
                this.mCancellationSignal.cancel();
            }
            this.mLogger.logAsyncTaskProgress(this.mEntry, "aborted");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public InflationProgress doInBackground(Void... voidArr) {
            return (InflationProgress) TraceUtils.trace("NotificationContentInflater.AsyncInflationTask#doInBackground", new Function0() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$AsyncInflationTask$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return this.f$0.lambda$doInBackground$0();
                }
            });
        }

        public int getReInflateFlags() {
            return this.mReInflateFlags;
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleError(exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            this.mEntry.onInflationTaskFinished();
            this.mRow.onNotificationUpdated();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.mEntry);
            }
            this.mRow.getImageResolver().purgeCache();
            this.mRow.getImageResolver().cancelRunningTasks();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(InflationProgress inflationProgress) {
            Trace.endAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(InflationProgress inflationProgress) {
            Trace.endAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
            Exception exc = this.mError;
            if (exc == null) {
                this.mCancellationSignal = NotificationContentInflater.apply(this.mInflationExecutor, this.mInflateSynchronously, this.mIsMinimized, inflationProgress, this.mReInflateFlags, this.mRemoteViewCache, this.mEntry, this.mRow, this.mRemoteViewClickHandler, this, this.mLogger);
            } else {
                handleError(exc);
            }
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            Trace.beginAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
        }

        void updateApplicationInfo(StatusBarNotification statusBarNotification) {
            try {
                Notification.addFieldsFromContext(this.mContext.getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }

    class InflationProgress {
        private InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        private InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        private CharSequence headsUpStatusBarText;
        private CharSequence headsUpStatusBarTextPublic;
        private View inflatedContentView;
        private View inflatedExpandedView;
        private View inflatedHeadsUpView;
        private View inflatedPublicView;
        private InflatedSmartReplyState inflatedSmartReplyState;
        private NotificationHeaderView mInflatedGroupHeaderView;
        private NotificationHeaderView mInflatedMinimizedGroupHeaderView;
        HybridNotificationView mInflatedSingleLineViewHolder;
        SingleLineViewModel mInflatedSingleLineViewModel;
        private RemoteViews mNewGroupHeaderView;
        private RemoteViews mNewMinimizedGroupHeaderView;
        private RemoteViews newContentView;
        private RemoteViews newExpandedView;
        private RemoteViews newHeadsUpView;
        private RemoteViews newPublicView;
        Context packageContext;

        InflationProgress() {
        }
    }

    public static /* synthetic */ Boolean $r8$lambda$G4IgZz04wuQ5tAKBI1c4xrifc9E(Resources resources, View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(R$dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        return Boolean.valueOf(view.getMeasuredHeight() >= resources.getDimensionPixelSize(R$dimen.notification_validation_minimum_allowed_height));
    }

    public static /* synthetic */ void $r8$lambda$m8Knfpi8RDk5uOfwsFxwmAtDMvo(NotificationContentInflaterLogger notificationContentInflaterLogger, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, HashMap map) {
        notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "apply cancelled");
        Trace.endAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow));
        map.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda3());
    }

    public static /* synthetic */ InflationProgress $r8$lambda$s37PxTT3bH7gkE15A9DrjNulZD0(ExpandableNotificationRow expandableNotificationRow, int i, NotificationContentInflaterLogger notificationContentInflaterLogger, Notification.Builder builder, boolean z, boolean z2, HeadsUpStyleProvider headsUpStyleProvider, NotifLayoutInflaterFactory.Provider provider, Context context) {
        InflationProgress inflationProgress = new InflationProgress();
        NotificationEntry entry = expandableNotificationRow.getEntry();
        if ((i & 1) != 0) {
            notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating contracted remote view");
            inflationProgress.newContentView = createContentView(builder, z, z2);
        }
        if ((i & 2) != 0) {
            notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating expanded remote view");
            inflationProgress.newExpandedView = createExpandedView(builder, z);
        }
        if ((i & 4) != 0) {
            notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating heads up remote view");
            if (headsUpStyleProvider.shouldApplyCompactStyle()) {
                inflationProgress.newHeadsUpView = builder.createCompactHeadsUpContentView();
            } else {
                inflationProgress.newHeadsUpView = builder.createHeadsUpContentView();
            }
        }
        if ((i & 8) != 0) {
            notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating public remote view");
            inflationProgress.newPublicView = builder.makePublicContentView(z);
        }
        if (AsyncGroupHeaderViewInflation.isEnabled()) {
            if ((i & 32) != 0) {
                notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating group summary remote view");
                inflationProgress.mNewGroupHeaderView = builder.makeNotificationGroupHeader();
            }
            if ((i & 64) != 0) {
                notificationContentInflaterLogger.logAsyncTaskProgress(entry, "creating low-priority group summary remote view");
                inflationProgress.mNewMinimizedGroupHeaderView = builder.makeLowPriorityContentView(true);
            }
        }
        setNotifsViewsInflaterFactory(inflationProgress, expandableNotificationRow, provider);
        inflationProgress.packageContext = context;
        inflationProgress.headsUpStatusBarText = builder.getHeadsUpStatusBarText(false);
        inflationProgress.headsUpStatusBarTextPublic = builder.getHeadsUpStatusBarText(true);
        return inflationProgress;
    }

    NotificationContentInflater(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        this.mRemoteViewCache = notifRemoteViewCache;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mConversationProcessor = conversationNotificationProcessor;
        this.mIsMediaInQS = mediaFeatureFlag.getEnabled();
        this.mInflationExecutor = executor;
        this.mSmartReplyStateInflater = smartReplyStateInflater;
        this.mNotifLayoutInflaterFactoryProvider = provider;
        this.mHeadsUpStyleProvider = headsUpStyleProvider;
        this.mLogger = notificationContentInflaterLogger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CancellationSignal apply(Executor executor, boolean z, boolean z2, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, final NotificationContentInflaterLogger notificationContentInflaterLogger) {
        NotificationContentView notificationContentView;
        final NotificationContentInflaterLogger notificationContentInflaterLogger2 = notificationContentInflaterLogger;
        Trace.beginAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow));
        NotificationContentView privateLayout = expandableNotificationRow.getPrivateLayout();
        NotificationContentView publicLayout = expandableNotificationRow.getPublicLayout();
        final HashMap map = new HashMap();
        if ((i & 1) != 0) {
            boolean z3 = !canReapplyRemoteView(inflationProgress.newContentView, notifRemoteViewCache.getCachedView(notificationEntry, 1));
            ApplyCallback applyCallback = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.1
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return inflationProgress.newContentView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public void setResultView(View view) {
                    notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "contracted view applied");
                    inflationProgress.inflatedContentView = view;
                }
            };
            notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying contracted view");
            notificationContentView = publicLayout;
            applyRemoteView(executor, z, z2, inflationProgress, i, 1, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z3, interactionHandler, inflationCallback, privateLayout, privateLayout.getContractedChild(), privateLayout.getVisibleWrapper(0), map, applyCallback, notificationContentInflaterLogger2);
            notificationContentInflaterLogger2 = notificationContentInflaterLogger2;
        } else {
            notificationContentView = publicLayout;
        }
        if ((i & 2) != 0 && inflationProgress.newExpandedView != null) {
            boolean z4 = !canReapplyRemoteView(inflationProgress.newExpandedView, notifRemoteViewCache.getCachedView(notificationEntry, 2));
            ApplyCallback applyCallback2 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.2
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return inflationProgress.newExpandedView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public void setResultView(View view) {
                    notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "expanded view applied");
                    inflationProgress.inflatedExpandedView = view;
                }
            };
            notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying expanded view");
            NotificationContentInflaterLogger notificationContentInflaterLogger3 = notificationContentInflaterLogger2;
            applyRemoteView(executor, z, z2, inflationProgress, i, 2, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z4, interactionHandler, inflationCallback, privateLayout, privateLayout.getExpandedChild(), privateLayout.getVisibleWrapper(1), map, applyCallback2, notificationContentInflaterLogger3);
            notificationContentInflaterLogger2 = notificationContentInflaterLogger3;
        }
        if ((i & 4) != 0 && inflationProgress.newHeadsUpView != null) {
            boolean z5 = !canReapplyRemoteView(inflationProgress.newHeadsUpView, notifRemoteViewCache.getCachedView(notificationEntry, 4));
            ApplyCallback applyCallback3 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.3
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return inflationProgress.newHeadsUpView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public void setResultView(View view) {
                    notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "heads up view applied");
                    inflationProgress.inflatedHeadsUpView = view;
                }
            };
            notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying heads up view");
            NotificationContentInflaterLogger notificationContentInflaterLogger4 = notificationContentInflaterLogger2;
            applyRemoteView(executor, z, z2, inflationProgress, i, 4, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z5, interactionHandler, inflationCallback, privateLayout, privateLayout.getHeadsUpChild(), privateLayout.getVisibleWrapper(2), map, applyCallback3, notificationContentInflaterLogger4);
            notificationContentInflaterLogger2 = notificationContentInflaterLogger4;
        }
        if ((i & 8) != 0) {
            boolean z6 = !canReapplyRemoteView(inflationProgress.newPublicView, notifRemoteViewCache.getCachedView(notificationEntry, 8));
            ApplyCallback applyCallback4 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.4
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public RemoteViews getRemoteView() {
                    return inflationProgress.newPublicView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public void setResultView(View view) {
                    notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "public view applied");
                    inflationProgress.inflatedPublicView = view;
                }
            };
            notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying public view");
            NotificationContentView notificationContentView2 = notificationContentView;
            NotificationContentInflaterLogger notificationContentInflaterLogger5 = notificationContentInflaterLogger2;
            applyRemoteView(executor, z, z2, inflationProgress, i, 8, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z6, interactionHandler, inflationCallback, notificationContentView2, notificationContentView.getContractedChild(), notificationContentView2.getVisibleWrapper(0), map, applyCallback4, notificationContentInflaterLogger5);
            notificationContentInflaterLogger2 = notificationContentInflaterLogger5;
        }
        if (AsyncGroupHeaderViewInflation.isEnabled()) {
            NotificationChildrenContainer childrenContainerNonNull = expandableNotificationRow.getChildrenContainerNonNull();
            if ((i & 32) != 0) {
                boolean z7 = !canReapplyRemoteView(inflationProgress.mNewGroupHeaderView, notifRemoteViewCache.getCachedView(notificationEntry, 32));
                ApplyCallback applyCallback5 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.5
                    @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                    public RemoteViews getRemoteView() {
                        return inflationProgress.mNewGroupHeaderView;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                    public void setResultView(View view) {
                        notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "group header view applied");
                        inflationProgress.mInflatedGroupHeaderView = (NotificationHeaderView) view;
                    }
                };
                notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying group header view");
                NotificationContentInflaterLogger notificationContentInflaterLogger6 = notificationContentInflaterLogger2;
                applyRemoteView(executor, z, z2, inflationProgress, i, 32, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z7, interactionHandler, inflationCallback, childrenContainerNonNull, childrenContainerNonNull.getGroupHeader(), childrenContainerNonNull.getNotificationHeaderWrapper(), map, applyCallback5, notificationContentInflaterLogger6);
                notificationContentInflaterLogger2 = notificationContentInflaterLogger6;
            }
            if ((i & 64) != 0) {
                boolean z8 = !canReapplyRemoteView(inflationProgress.mNewMinimizedGroupHeaderView, notifRemoteViewCache.getCachedView(notificationEntry, 64));
                ApplyCallback applyCallback6 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.6
                    @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                    public RemoteViews getRemoteView() {
                        return inflationProgress.mNewMinimizedGroupHeaderView;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                    public void setResultView(View view) {
                        notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "low-priority group header view applied");
                        inflationProgress.mInflatedMinimizedGroupHeaderView = (NotificationHeaderView) view;
                    }
                };
                notificationContentInflaterLogger2.logAsyncTaskProgress(notificationEntry, "applying low priority group header view");
                applyRemoteView(executor, z, z2, inflationProgress, i, 64, notifRemoteViewCache, notificationEntry, expandableNotificationRow, z8, interactionHandler, inflationCallback, childrenContainerNonNull, childrenContainerNonNull.getMinimizedNotificationHeader(), childrenContainerNonNull.getMinimizedGroupHeaderWrapper(), map, applyCallback6, notificationContentInflaterLogger2);
            }
        }
        finishIfDone(inflationProgress, z2, i, notifRemoteViewCache, map, inflationCallback, notificationEntry, expandableNotificationRow, notificationContentInflaterLogger);
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda2
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                NotificationContentInflater.$r8$lambda$m8Knfpi8RDk5uOfwsFxwmAtDMvo(notificationContentInflaterLogger, notificationEntry, expandableNotificationRow, map);
            }
        });
        return cancellationSignal;
    }

    static void applyRemoteView(Executor executor, boolean z, final boolean z2, final InflationProgress inflationProgress, final int i, final int i2, final NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final boolean z3, final RemoteViews.InteractionHandler interactionHandler, final NotificationRowContentBinder.InflationCallback inflationCallback, final ViewGroup viewGroup, final View view, final NotificationViewWrapper notificationViewWrapper, final HashMap map, final ApplyCallback applyCallback, final NotificationContentInflaterLogger notificationContentInflaterLogger) {
        final RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.7
                public void onError(Exception exc) {
                    try {
                        View viewApply = view;
                        if (z3) {
                            viewApply = remoteView.apply(inflationProgress.packageContext, viewGroup, interactionHandler);
                        } else {
                            remoteView.reapply(inflationProgress.packageContext, viewApply, interactionHandler);
                        }
                        Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                        onViewApplied(viewApply);
                    } catch (Exception unused) {
                        map.remove(Integer.valueOf(i2));
                        NotificationContentInflater.handleInflationError(map, exc, expandableNotificationRow.getEntry(), inflationCallback, notificationContentInflaterLogger, "applying view");
                    }
                }

                public void onViewApplied(View view2) {
                    String strIsValidView = NotificationContentInflater.isValidView(view2, notificationEntry, expandableNotificationRow.getResources());
                    if (strIsValidView != null) {
                        NotificationContentInflater.handleInflationError(map, new InflationException(strIsValidView), expandableNotificationRow.getEntry(), inflationCallback, notificationContentInflaterLogger, "applied invalid view");
                        map.remove(Integer.valueOf(i2));
                        return;
                    }
                    if (z3) {
                        applyCallback.setResultView(view2);
                    } else {
                        NotificationViewWrapper notificationViewWrapper2 = notificationViewWrapper;
                        if (notificationViewWrapper2 != null) {
                            notificationViewWrapper2.onReinflated();
                        }
                    }
                    map.remove(Integer.valueOf(i2));
                    NotificationContentInflater.finishIfDone(inflationProgress, z2, i, notifRemoteViewCache, map, inflationCallback, notificationEntry, expandableNotificationRow, notificationContentInflaterLogger);
                }

                public void onViewInflated(View view2) {
                    if (view2 instanceof ImageMessageConsumer) {
                        ((ImageMessageConsumer) view2).setImageResolver(expandableNotificationRow.getImageResolver());
                    }
                }
            };
            map.put(Integer.valueOf(i2), z3 ? remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler) : remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler));
            return;
        }
        try {
            if (z3) {
                View viewApply = remoteView.apply(inflationProgress.packageContext, viewGroup, interactionHandler);
                validateView(viewApply, notificationEntry, expandableNotificationRow.getResources());
                applyCallback.setResultView(viewApply);
            } else {
                remoteView.reapply(inflationProgress.packageContext, view, interactionHandler);
                validateView(view, notificationEntry, expandableNotificationRow.getResources());
                notificationViewWrapper.onReinflated();
            }
        } catch (Exception e) {
            handleInflationError(map, e, expandableNotificationRow.getEntry(), inflationCallback, notificationContentInflaterLogger, "applying view synchronously");
            map.put(Integer.valueOf(i2), new CancellationSignal());
        }
    }

    static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        return (remoteViews == null && remoteViews2 == null) || !(remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1));
    }

    private void cancelContentViewFrees(ExpandableNotificationRow expandableNotificationRow, int i) {
        if ((i & 1) != 0) {
            expandableNotificationRow.getPrivateLayout().removeContentInactiveRunnable(0);
        }
        if ((i & 2) != 0) {
            expandableNotificationRow.getPrivateLayout().removeContentInactiveRunnable(1);
        }
        if ((i & 4) != 0) {
            expandableNotificationRow.getPrivateLayout().removeContentInactiveRunnable(2);
        }
        if ((i & 8) != 0) {
            expandableNotificationRow.getPublicLayout().removeContentInactiveRunnable(0);
        }
        if (!AsyncHybridViewInflation.isEnabled() || (i & 16) == 0) {
            return;
        }
        expandableNotificationRow.getPrivateLayout().removeContentInactiveRunnable(3);
    }

    private static RemoteViews createContentView(Notification.Builder builder, boolean z, boolean z2) {
        return z ? builder.makeLowPriorityContentView(false) : builder.createContentView();
    }

    private static RemoteViews createExpandedView(Notification.Builder builder, boolean z) {
        RemoteViews remoteViewsCreateBigContentView = builder.createBigContentView();
        if (remoteViewsCreateBigContentView != null) {
            return remoteViewsCreateBigContentView;
        }
        if (!z) {
            return null;
        }
        RemoteViews remoteViewsCreateContentView = builder.createContentView();
        Notification.Builder.makeHeaderExpanded(remoteViewsCreateContentView);
        return remoteViewsCreateContentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InflationProgress createRemoteViews(final int i, final Notification.Builder builder, final boolean z, final boolean z2, boolean z3, final Context context, final ExpandableNotificationRow expandableNotificationRow, final NotifLayoutInflaterFactory.Provider provider, final HeadsUpStyleProvider headsUpStyleProvider, final NotificationContentInflaterLogger notificationContentInflaterLogger) {
        return (InflationProgress) TraceUtils.trace("NotificationContentInflater.createRemoteViews", new Function0() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationContentInflater.$r8$lambda$s37PxTT3bH7gkE15A9DrjNulZD0(expandableNotificationRow, i, notificationContentInflaterLogger, builder, z, z2, headsUpStyleProvider, provider, context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean finishIfDone(InflationProgress inflationProgress, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap map, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        Assert.isMainThread();
        if (!map.isEmpty()) {
            return false;
        }
        NotificationContentView privateLayout = expandableNotificationRow.getPrivateLayout();
        NotificationContentView publicLayout = expandableNotificationRow.getPublicLayout();
        notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "finishing");
        if ((i & 1) != 0) {
            if (inflationProgress.inflatedContentView != null) {
                privateLayout.setContractedChild(inflationProgress.inflatedContentView);
                notifRemoteViewCache.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 1)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            }
        }
        if ((i & 2) != 0) {
            if (inflationProgress.inflatedExpandedView != null) {
                privateLayout.setExpandedChild(inflationProgress.inflatedExpandedView);
                notifRemoteViewCache.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            } else if (inflationProgress.newExpandedView == null) {
                privateLayout.setExpandedChild(null);
                notifRemoteViewCache.removeCachedView(notificationEntry, 2);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 2)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            }
            if (inflationProgress.newExpandedView != null) {
                privateLayout.setExpandedInflatedSmartReplies(inflationProgress.expandedInflatedSmartReplies);
            } else {
                privateLayout.setExpandedInflatedSmartReplies(null);
            }
            expandableNotificationRow.setExpandable(inflationProgress.newExpandedView != null);
        }
        if ((i & 4) != 0) {
            if (inflationProgress.inflatedHeadsUpView != null) {
                privateLayout.setHeadsUpChild(inflationProgress.inflatedHeadsUpView);
                notifRemoteViewCache.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            } else if (inflationProgress.newHeadsUpView == null) {
                privateLayout.setHeadsUpChild(null);
                notifRemoteViewCache.removeCachedView(notificationEntry, 4);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 4)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            }
            if (inflationProgress.newHeadsUpView != null) {
                privateLayout.setHeadsUpInflatedSmartReplies(inflationProgress.headsUpInflatedSmartReplies);
            } else {
                privateLayout.setHeadsUpInflatedSmartReplies(null);
            }
        }
        if (AsyncHybridViewInflation.isEnabled() && (i & 16) != 0) {
            HybridNotificationView hybridNotificationView = inflationProgress.mInflatedSingleLineViewHolder;
            SingleLineViewModel singleLineViewModel = inflationProgress.mInflatedSingleLineViewModel;
            if (hybridNotificationView != null && singleLineViewModel != null) {
                if (singleLineViewModel.isConversation()) {
                    SingleLineConversationViewBinder.bind(inflationProgress.mInflatedSingleLineViewModel, inflationProgress.mInflatedSingleLineViewHolder);
                } else {
                    SingleLineViewBinder.bind(inflationProgress.mInflatedSingleLineViewModel, inflationProgress.mInflatedSingleLineViewHolder);
                }
                privateLayout.setSingleLineView(inflationProgress.mInflatedSingleLineViewHolder);
            }
        }
        privateLayout.setInflatedSmartReplyState(inflationProgress.inflatedSmartReplyState);
        if ((i & 8) != 0) {
            if (inflationProgress.inflatedPublicView != null) {
                publicLayout.setContractedChild(inflationProgress.inflatedPublicView);
                notifRemoteViewCache.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 8)) {
                notifRemoteViewCache.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            }
        }
        if (AsyncGroupHeaderViewInflation.isEnabled()) {
            if ((i & 32) != 0) {
                if (inflationProgress.mInflatedGroupHeaderView != null) {
                    expandableNotificationRow.setIsMinimized(z);
                    expandableNotificationRow.setGroupHeader(inflationProgress.mInflatedGroupHeaderView);
                    notifRemoteViewCache.putCachedView(notificationEntry, 32, inflationProgress.mNewGroupHeaderView);
                } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 32)) {
                    notifRemoteViewCache.putCachedView(notificationEntry, 32, inflationProgress.mNewGroupHeaderView);
                }
            }
            if ((i & 64) != 0) {
                if (inflationProgress.mInflatedMinimizedGroupHeaderView != null) {
                    expandableNotificationRow.setIsMinimized(z);
                    expandableNotificationRow.setMinimizedGroupHeader(inflationProgress.mInflatedMinimizedGroupHeaderView);
                    notifRemoteViewCache.putCachedView(notificationEntry, 64, inflationProgress.mNewMinimizedGroupHeaderView);
                } else if (notifRemoteViewCache.hasCachedView(notificationEntry, 64)) {
                    notifRemoteViewCache.putCachedView(notificationEntry, 64, inflationProgress.mNewGroupHeaderView);
                }
            }
        }
        notificationEntry.setHeadsUpStatusBarText(inflationProgress.headsUpStatusBarText);
        notificationEntry.setHeadsUpStatusBarTextPublic(inflationProgress.headsUpStatusBarTextPublic);
        Trace.endAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow));
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
        return true;
    }

    private void freeNotificationView(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, int i) {
        if (i == 1) {
            expandableNotificationRow.getPrivateLayout().performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$freeNotificationView$0(expandableNotificationRow, notificationEntry);
                }
            });
            return;
        }
        if (i == 2) {
            expandableNotificationRow.getPrivateLayout().performWhenContentInactive(1, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$freeNotificationView$1(expandableNotificationRow, notificationEntry);
                }
            });
            return;
        }
        if (i == 4) {
            expandableNotificationRow.getPrivateLayout().performWhenContentInactive(2, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$freeNotificationView$2(expandableNotificationRow, notificationEntry);
                }
            });
            return;
        }
        if (i == 8) {
            expandableNotificationRow.getPublicLayout().performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$freeNotificationView$3(expandableNotificationRow, notificationEntry);
                }
            });
        } else if (i == 16 && AsyncHybridViewInflation.isEnabled()) {
            expandableNotificationRow.getPrivateLayout().performWhenContentInactive(3, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    expandableNotificationRow.getPrivateLayout().setSingleLineView(null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleInflationError(HashMap map, Exception exc, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationContentInflaterLogger notificationContentInflaterLogger, String str) {
        Assert.isMainThread();
        notificationContentInflaterLogger.logAsyncTaskException(notificationEntry, str, exc);
        map.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda3());
        if (inflationCallback != null) {
            inflationCallback.handleInflationException(notificationEntry, exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InflationProgress inflateSmartReplyViews(InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        boolean z = false;
        boolean z2 = ((i & 1) == 0 || inflationProgress.newContentView == null) ? false : true;
        boolean z3 = ((i & 2) == 0 || inflationProgress.newExpandedView == null) ? false : true;
        if ((i & 4) != 0 && inflationProgress.newHeadsUpView != null) {
            z = true;
        }
        if (z2 || z3 || z) {
            notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "inflating contracted smart reply state");
            inflationProgress.inflatedSmartReplyState = smartReplyStateInflater.inflateSmartReplyState(notificationEntry);
        }
        if (z3) {
            notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "inflating expanded smart reply state");
            inflationProgress.expandedInflatedSmartReplies = smartReplyStateInflater.inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
        if (z) {
            notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "inflating heads up smart reply state");
            inflationProgress.headsUpInflatedSmartReplies = smartReplyStateInflater.inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
        return inflationProgress;
    }

    static String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
        if (satisfiesMinHeightRequirement(view, notificationEntry, resources)) {
            return null;
        }
        return "inflated notification does not meet minimum height requirement";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$freeNotificationView$0(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry) {
        expandableNotificationRow.getPrivateLayout().setContractedChild(null);
        this.mRemoteViewCache.removeCachedView(notificationEntry, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$freeNotificationView$1(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry) {
        expandableNotificationRow.getPrivateLayout().setExpandedChild(null);
        this.mRemoteViewCache.removeCachedView(notificationEntry, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$freeNotificationView$2(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry) {
        expandableNotificationRow.getPrivateLayout().setHeadsUpChild(null);
        this.mRemoteViewCache.removeCachedView(notificationEntry, 4);
        expandableNotificationRow.getPrivateLayout().setHeadsUpInflatedSmartReplies(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$freeNotificationView$3(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry) {
        expandableNotificationRow.getPublicLayout().setContractedChild(null);
        this.mRemoteViewCache.removeCachedView(notificationEntry, 8);
    }

    private static boolean requiresHeightCheck(NotificationEntry notificationEntry) {
        if (notificationEntry.targetSdk >= 31) {
            return false;
        }
        Notification notification = notificationEntry.getSbn().getNotification();
        return (notification.contentView == null && notification.bigContentView == null && notification.headsUpContentView == null) ? false : true;
    }

    private static boolean satisfiesMinHeightRequirement(final View view, NotificationEntry notificationEntry, final Resources resources) {
        if (requiresHeightCheck(notificationEntry)) {
            return ((Boolean) TraceUtils.trace("NotificationContentInflater#satisfiesMinHeightRequirement", new Function0() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return NotificationContentInflater.$r8$lambda$G4IgZz04wuQ5tAKBI1c4xrifc9E(resources, view);
                }
            })).booleanValue();
        }
        return true;
    }

    private static void setNotifsViewsInflaterFactory(InflationProgress inflationProgress, ExpandableNotificationRow expandableNotificationRow, NotifLayoutInflaterFactory.Provider provider) {
        setRemoteViewsInflaterFactory(inflationProgress.newContentView, provider.provide(expandableNotificationRow, 1));
        setRemoteViewsInflaterFactory(inflationProgress.newExpandedView, provider.provide(expandableNotificationRow, 2));
        setRemoteViewsInflaterFactory(inflationProgress.newHeadsUpView, provider.provide(expandableNotificationRow, 4));
        setRemoteViewsInflaterFactory(inflationProgress.newPublicView, provider.provide(expandableNotificationRow, 8));
    }

    private static void setRemoteViewsInflaterFactory(RemoteViews remoteViews, NotifLayoutInflaterFactory notifLayoutInflaterFactory) {
        if (remoteViews != null) {
            remoteViews.setLayoutInflaterFactory(notifLayoutInflaterFactory);
        }
    }

    private static void validateView(View view, NotificationEntry notificationEntry, Resources resources) throws InflationException {
        String strIsValidView = isValidView(view, notificationEntry, resources);
        if (strIsValidView != null) {
            throw new InflationException(strIsValidView);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, NotificationRowContentBinder.InflationCallback inflationCallback) {
        if (expandableNotificationRow.isRemoved()) {
            this.mLogger.logNotBindingRowWasRemoved(notificationEntry);
            return;
        }
        this.mLogger.logBinding(notificationEntry, i);
        expandableNotificationRow.getImageResolver().preloadImages(notificationEntry.getSbn().getNotification());
        if (z) {
            this.mRemoteViewCache.clearCache(notificationEntry);
        }
        cancelContentViewFrees(expandableNotificationRow, i);
        AsyncInflationTask asyncInflationTask = new AsyncInflationTask(this.mInflationExecutor, this.mInflateSynchronously, i, this.mRemoteViewCache, notificationEntry, this.mConversationProcessor, expandableNotificationRow, bindParams.isMinimized, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, inflationCallback, this.mRemoteInputManager.getRemoteViewsOnClickHandler(), this.mIsMediaInQS, this.mSmartReplyStateInflater, this.mNotifLayoutInflaterFactoryProvider, this.mHeadsUpStyleProvider, this.mLogger);
        if (this.mInflateSynchronously) {
            asyncInflationTask.onPostExecute(asyncInflationTask.doInBackground(new Void[0]));
        } else {
            asyncInflationTask.executeOnExecutor(this.mInflationExecutor, new Void[0]);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean zAbortTask = notificationEntry.abortTask();
        if (zAbortTask) {
            this.mLogger.logCancelBindAbortedTask(notificationEntry);
        }
        return zAbortTask;
    }

    InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater) {
        int i2;
        InflationProgress inflationProgressInflateSmartReplyViews = inflateSmartReplyViews(createRemoteViews(i, builder, bindParams.isMinimized, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, context, expandableNotificationRow, this.mNotifLayoutInflaterFactoryProvider, this.mHeadsUpStyleProvider, this.mLogger), i, notificationEntry, expandableNotificationRow.getContext(), context, expandableNotificationRow.getExistingSmartReplyState(), smartReplyStateInflater, this.mLogger);
        if (AsyncHybridViewInflation.isEnabled()) {
            boolean zIsConversation = notificationEntry.getRanking().isConversation();
            inflationProgressInflateSmartReplyViews.mInflatedSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry.getSbn().getNotification(), zIsConversation ? this.mConversationProcessor.processNotification(notificationEntry, builder, this.mLogger) : null, builder, expandableNotificationRow.getContext());
            i2 = i;
            inflationProgressInflateSmartReplyViews.mInflatedSingleLineViewHolder = SingleLineViewInflater.inflateSingleLineViewHolder(zIsConversation, i2, notificationEntry, expandableNotificationRow.getContext(), this.mLogger);
        } else {
            i2 = i;
        }
        apply(this.mInflationExecutor, z, bindParams.isMinimized, inflationProgressInflateSmartReplyViews, i2, this.mRemoteViewCache, notificationEntry, expandableNotificationRow, this.mRemoteInputManager.getRemoteViewsOnClickHandler(), null, this.mLogger);
        return inflationProgressInflateSmartReplyViews;
    }

    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void unbindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i) {
        this.mLogger.logUnbinding(notificationEntry, i);
        int i2 = 1;
        while (i != 0) {
            if ((i & i2) != 0) {
                freeNotificationView(notificationEntry, expandableNotificationRow, i2);
            }
            i &= ~i2;
            i2 <<= 1;
        }
    }
}
