package com.motorola.taskbar.recent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityManager;
import com.android.launcher3.Utilities;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconProvider;
import com.android.launcher3.util.Preconditions;
import com.android.settingslib.Utils;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$integer;
import com.motorola.taskbar.recent.TaskSyncreticItem;
import com.motorola.taskbar.util.MainThreadExecutor;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskSyncreticIconCache {
    private final AccessibilityManager mAccessibilityManager;
    private final Handler mBackgroundHandler;
    private final Context mContext;
    private final SyncreticTaskKeyLruCache mIconCache;
    private BaseIconFactory mIconFactory;
    private final IconProvider mIconProvider;
    private final PackageManager mPackageManager;
    private final SparseArray mDefaultIcons = new SparseArray();
    private MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.TaskSyncreticIconCache$1, reason: invalid class name */
    class AnonymousClass1 extends IconLoadRequest {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ TaskSyncreticItem val$task;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Handler handler, TaskSyncreticItem taskSyncreticItem, Consumer consumer) {
            super(handler);
            this.val$task = taskSyncreticItem;
            this.val$callback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(TaskSyncreticItem taskSyncreticItem, TaskCacheEntry taskCacheEntry, Consumer consumer) {
            taskSyncreticItem.setIcon(taskCacheEntry.icon);
            taskSyncreticItem.setTitleDescription(taskCacheEntry.contentDescription);
            consumer.accept(taskSyncreticItem);
            onEnd();
        }

        @Override // java.lang.Runnable
        public void run() {
            final TaskCacheEntry cacheEntry = TaskSyncreticIconCache.this.getCacheEntry(this.val$task);
            if (isCanceled()) {
                return;
            }
            MainThreadExecutor mainThreadExecutor = TaskSyncreticIconCache.this.mMainThreadExecutor;
            final TaskSyncreticItem taskSyncreticItem = this.val$task;
            final Consumer consumer = this.val$callback;
            mainThreadExecutor.execute(new Runnable() { // from class: com.motorola.taskbar.recent.TaskSyncreticIconCache$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0(taskSyncreticItem, cacheEntry, consumer);
                }
            });
        }
    }

    public abstract class IconLoadRequest extends CancellablHandlerRunnable {
        IconLoadRequest(Handler handler) {
            super(handler, null);
        }
    }

    class TaskCacheEntry {
        public String contentDescription;
        public Drawable icon;

        private TaskCacheEntry() {
            this.contentDescription = "";
        }
    }

    public TaskSyncreticIconCache(Context context, Looper looper) {
        this.mContext = context;
        this.mBackgroundHandler = new Handler(looper);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mIconCache = new SyncreticTaskKeyLruCache(context.getResources().getInteger(R$integer.recentsIconCacheSize));
        this.mIconProvider = new IconProvider(context);
        this.mPackageManager = context.getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.motorola.taskbar.recent.TaskSyncreticIconCache-IA] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public TaskCacheEntry getCacheEntry(TaskSyncreticItem taskSyncreticItem) {
        TaskCacheEntry taskCacheEntry = (TaskCacheEntry) this.mIconCache.getAndInvalidateIfModified(taskSyncreticItem.iconCacheKey);
        if (taskCacheEntry != null) {
            return taskCacheEntry;
        }
        TaskSyncreticItem.TaskIconCacheKey taskIconCacheKey = taskSyncreticItem.iconCacheKey;
        ActivityInfo activityInfo = 0;
        activityInfo = 0;
        TaskCacheEntry taskCacheEntry2 = new TaskCacheEntry();
        List listQueryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(taskSyncreticItem.getShortcutInfo() != null ? new Intent().setComponent(taskSyncreticItem.getShortcutInfo().getComponentName()) : new Intent().setAction("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(taskSyncreticItem.getPackageName()), 0, taskSyncreticItem.getUserId());
        if (listQueryIntentActivitiesAsUser != null && listQueryIntentActivitiesAsUser.size() > 0) {
            activityInfo = ((ResolveInfo) listQueryIntentActivitiesAsUser.get(0)).activityInfo;
        }
        if (activityInfo != 0) {
            Drawable icon = this.mIconProvider.getIcon(activityInfo);
            taskCacheEntry2.icon = icon;
            taskCacheEntry2.icon = Utils.getBadgedIcon(this.mContext, icon, UserHandle.of(taskIconCacheKey.getUserId()));
        } else {
            Drawable packageIcon = getPackageIcon(taskIconCacheKey.getPackageName(), taskIconCacheKey.getUserId());
            taskCacheEntry2.icon = packageIcon;
            if (packageIcon == null) {
                taskCacheEntry2.icon = getDefaultIcon(taskIconCacheKey.getUserId());
            }
        }
        taskCacheEntry2.contentDescription = getPackageLable(taskIconCacheKey.getPackageName(), taskIconCacheKey.getUserId());
        this.mIconCache.put(taskIconCacheKey, taskCacheEntry2);
        return taskCacheEntry2;
    }

    private Drawable getDefaultIcon(int i) {
        FastBitmapDrawable fastBitmapDrawableNewIcon;
        synchronized (this.mDefaultIcons) {
            BitmapInfo bitmapInfo = (BitmapInfo) this.mDefaultIcons.get(i);
            if (bitmapInfo == null) {
                BaseIconFactory iconFactory = getIconFactory();
                try {
                    BitmapInfo bitmapInfoMakeDefaultIcon = iconFactory.makeDefaultIcon(this.mIconProvider);
                    iconFactory.close();
                    this.mDefaultIcons.put(i, bitmapInfoMakeDefaultIcon);
                    bitmapInfo = bitmapInfoMakeDefaultIcon;
                } finally {
                }
            }
            fastBitmapDrawableNewIcon = bitmapInfo.newIcon(this.mContext);
        }
        return fastBitmapDrawableNewIcon;
    }

    private BaseIconFactory getIconFactory() {
        if (this.mIconFactory == null) {
            Context context = this.mContext;
            this.mIconFactory = new BaseIconFactory(context, 640, context.getResources().getDimensionPixelSize(R$dimen.taskbar_icon_size));
        }
        return this.mIconFactory;
    }

    private Drawable getPackageIcon(String str, int i) {
        try {
            return Utils.getBadgedIcon(this.mContext, this.mPackageManager.getApplicationInfoAsUser(str, 0, i));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getPackageLable(String str, int i) {
        try {
            return this.mPackageManager.getApplicationLabel(this.mPackageManager.getApplicationInfoAsUser(str, 0, i)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IconLoadRequest updateIconInBackground(TaskSyncreticItem taskSyncreticItem, Consumer consumer) {
        Preconditions.assertUIThread();
        if (taskSyncreticItem.getIcon() != null) {
            consumer.accept(taskSyncreticItem);
            return null;
        }
        if (taskSyncreticItem.isEmptyShortcutItem()) {
            return null;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.mBackgroundHandler, taskSyncreticItem, consumer);
        Utilities.postAsyncCallback(this.mBackgroundHandler, anonymousClass1);
        return anonymousClass1;
    }
}
