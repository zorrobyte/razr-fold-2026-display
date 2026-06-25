package com.motorola.taskbar.recent;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarRecentsView extends FrameLayout implements ConfigurationController.ConfigurationListener {
    private static final boolean DEBUG = DebugConfig.DEBUG_RECENT_TASK_VIEW;
    private float mDarkIntensity;
    private int mInsertingPosition;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnRecentsStatusListener mOnRecentsStatusListener;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private RecyclerView mRecyclerView;
    private View.OnDragListener mRecyclerViewOnDragListener;
    private TaskBarSyncreticRecentsAdapter mTaskBarRecentsAdapter;
    private TaskSyncreticController mTaskSyncreticController;
    private Handler mUIHandler;

    class MyLayoutManager extends LinearLayoutManager {
        public MyLayoutManager(Context context, int i, boolean z) {
            super(context, i, z);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean isAutoMeasureEnabled() {
            return false;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
            super.onItemsAdded(recyclerView, i, i2);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsAdded");
            }
            TaskBarRecentsView.this.notifyItemsChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsChanged(RecyclerView recyclerView) {
            super.onItemsChanged(recyclerView);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsChanged");
            }
            TaskBarRecentsView.this.notifyItemsChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
            super.onItemsMoved(recyclerView, i, i2, i3);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsMoved");
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
            super.onItemsRemoved(recyclerView, i, i2);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsRemoved");
            }
            TaskBarRecentsView.this.notifyItemsChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
            super.onItemsUpdated(recyclerView, i, i2);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsUpdated");
            }
            TaskBarRecentsView.this.notifyItemsChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            super.onItemsUpdated(recyclerView, i, i2, obj);
            if (TaskBarRecentsView.DEBUG) {
                Log.d("TaskBarRecentsView", "onItemsUpdated 2");
            }
            TaskBarRecentsView.this.notifyItemsChanged();
        }
    }

    public interface OnRecentsStatusListener {
        void onItemsChanged();

        void onScrollIdle();
    }

    public TaskBarRecentsView(Context context) {
        super(context);
        this.mInsertingPosition = -1;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    if (TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                        TaskBarRecentsView.this.mOnRecentsStatusListener.onItemsChanged();
                    }
                } else if (i == 2 && TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                    TaskBarRecentsView.this.mOnRecentsStatusListener.onScrollIdle();
                }
            }
        };
        this.mRecyclerViewOnDragListener = new View.OnDragListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.2
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                if (action == 4 || dragEvent.getClipDescription() == null || !dragEvent.getClipDescription().hasMimeType("com.motorola.launcher3.sdl.drag_and_drop/taskbar")) {
                    return false;
                }
                if (TaskBarRecentsView.this.mTaskSyncreticController.getShortcutInfoCount() >= 10 && 1 != action) {
                    if (5 == action) {
                        Toast.makeText(TaskBarRecentsView.this.getContext(), R$string.pin_apps_exceeded, 0).show();
                    }
                    return false;
                }
                if (action == 2) {
                    float x = dragEvent.getX();
                    float y = dragEvent.getY();
                    int dragInsertPosition = TaskBarRecentsView.this.getDragInsertPosition(x, y);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag location: (" + x + "," + y + ") , insertPosition = " + dragInsertPosition);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(0, dragInsertPosition);
                } else if (action == 3) {
                    float x2 = dragEvent.getX();
                    float y2 = dragEvent.getY();
                    int dragInsertPosition2 = TaskBarRecentsView.this.getDragInsertPosition(x2, y2);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition2 > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition2--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition2;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrop drop: (" + x2 + "," + y2 + ") , insertPosition = " + dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(2, dragInsertPosition2);
                    if (dragInsertPosition2 >= 0) {
                        ClipData clipData = dragEvent.getClipData();
                        TaskBarRecentsView.this.mTaskBarRecentsAdapter.insertShortcut(Integer.parseInt(clipData.getItemAt(1).getText().toString()), ComponentName.unflattenFromString(clipData.getItemAt(0).getText().toString()), dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 5) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag entered");
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 6) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag exited");
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(1, -1);
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (TaskBarRecentsView.DEBUG) {
                    Log.d("TaskBarRecentsView", "onDrag unhandled action: " + action);
                }
                return true;
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    TaskBarRecentsView.this.notifyScrollIdle();
                }
            }
        };
    }

    public TaskBarRecentsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInsertingPosition = -1;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    if (TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                        TaskBarRecentsView.this.mOnRecentsStatusListener.onItemsChanged();
                    }
                } else if (i == 2 && TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                    TaskBarRecentsView.this.mOnRecentsStatusListener.onScrollIdle();
                }
            }
        };
        this.mRecyclerViewOnDragListener = new View.OnDragListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.2
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                if (action == 4 || dragEvent.getClipDescription() == null || !dragEvent.getClipDescription().hasMimeType("com.motorola.launcher3.sdl.drag_and_drop/taskbar")) {
                    return false;
                }
                if (TaskBarRecentsView.this.mTaskSyncreticController.getShortcutInfoCount() >= 10 && 1 != action) {
                    if (5 == action) {
                        Toast.makeText(TaskBarRecentsView.this.getContext(), R$string.pin_apps_exceeded, 0).show();
                    }
                    return false;
                }
                if (action == 2) {
                    float x = dragEvent.getX();
                    float y = dragEvent.getY();
                    int dragInsertPosition = TaskBarRecentsView.this.getDragInsertPosition(x, y);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag location: (" + x + "," + y + ") , insertPosition = " + dragInsertPosition);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(0, dragInsertPosition);
                } else if (action == 3) {
                    float x2 = dragEvent.getX();
                    float y2 = dragEvent.getY();
                    int dragInsertPosition2 = TaskBarRecentsView.this.getDragInsertPosition(x2, y2);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition2 > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition2--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition2;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrop drop: (" + x2 + "," + y2 + ") , insertPosition = " + dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(2, dragInsertPosition2);
                    if (dragInsertPosition2 >= 0) {
                        ClipData clipData = dragEvent.getClipData();
                        TaskBarRecentsView.this.mTaskBarRecentsAdapter.insertShortcut(Integer.parseInt(clipData.getItemAt(1).getText().toString()), ComponentName.unflattenFromString(clipData.getItemAt(0).getText().toString()), dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 5) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag entered");
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 6) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag exited");
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(1, -1);
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (TaskBarRecentsView.DEBUG) {
                    Log.d("TaskBarRecentsView", "onDrag unhandled action: " + action);
                }
                return true;
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    TaskBarRecentsView.this.notifyScrollIdle();
                }
            }
        };
    }

    public TaskBarRecentsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInsertingPosition = -1;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    if (TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                        TaskBarRecentsView.this.mOnRecentsStatusListener.onItemsChanged();
                    }
                } else if (i2 == 2 && TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                    TaskBarRecentsView.this.mOnRecentsStatusListener.onScrollIdle();
                }
            }
        };
        this.mRecyclerViewOnDragListener = new View.OnDragListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.2
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                if (action == 4 || dragEvent.getClipDescription() == null || !dragEvent.getClipDescription().hasMimeType("com.motorola.launcher3.sdl.drag_and_drop/taskbar")) {
                    return false;
                }
                if (TaskBarRecentsView.this.mTaskSyncreticController.getShortcutInfoCount() >= 10 && 1 != action) {
                    if (5 == action) {
                        Toast.makeText(TaskBarRecentsView.this.getContext(), R$string.pin_apps_exceeded, 0).show();
                    }
                    return false;
                }
                if (action == 2) {
                    float x = dragEvent.getX();
                    float y = dragEvent.getY();
                    int dragInsertPosition = TaskBarRecentsView.this.getDragInsertPosition(x, y);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag location: (" + x + "," + y + ") , insertPosition = " + dragInsertPosition);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(0, dragInsertPosition);
                } else if (action == 3) {
                    float x2 = dragEvent.getX();
                    float y2 = dragEvent.getY();
                    int dragInsertPosition2 = TaskBarRecentsView.this.getDragInsertPosition(x2, y2);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition2 > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition2--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition2;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrop drop: (" + x2 + "," + y2 + ") , insertPosition = " + dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(2, dragInsertPosition2);
                    if (dragInsertPosition2 >= 0) {
                        ClipData clipData = dragEvent.getClipData();
                        TaskBarRecentsView.this.mTaskBarRecentsAdapter.insertShortcut(Integer.parseInt(clipData.getItemAt(1).getText().toString()), ComponentName.unflattenFromString(clipData.getItemAt(0).getText().toString()), dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 5) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag entered");
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 6) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag exited");
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(1, -1);
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (TaskBarRecentsView.DEBUG) {
                    Log.d("TaskBarRecentsView", "onDrag unhandled action: " + action);
                }
                return true;
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                super.onScrollStateChanged(recyclerView, i2);
                if (i2 == 0) {
                    TaskBarRecentsView.this.notifyScrollIdle();
                }
            }
        };
    }

    public TaskBarRecentsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInsertingPosition = -1;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i22 = message.what;
                if (i22 == 1) {
                    if (TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                        TaskBarRecentsView.this.mOnRecentsStatusListener.onItemsChanged();
                    }
                } else if (i22 == 2 && TaskBarRecentsView.this.mOnRecentsStatusListener != null) {
                    TaskBarRecentsView.this.mOnRecentsStatusListener.onScrollIdle();
                }
            }
        };
        this.mRecyclerViewOnDragListener = new View.OnDragListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.2
            @Override // android.view.View.OnDragListener
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                if (action == 4 || dragEvent.getClipDescription() == null || !dragEvent.getClipDescription().hasMimeType("com.motorola.launcher3.sdl.drag_and_drop/taskbar")) {
                    return false;
                }
                if (TaskBarRecentsView.this.mTaskSyncreticController.getShortcutInfoCount() >= 10 && 1 != action) {
                    if (5 == action) {
                        Toast.makeText(TaskBarRecentsView.this.getContext(), R$string.pin_apps_exceeded, 0).show();
                    }
                    return false;
                }
                if (action == 2) {
                    float x = dragEvent.getX();
                    float y = dragEvent.getY();
                    int dragInsertPosition = TaskBarRecentsView.this.getDragInsertPosition(x, y);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag location: (" + x + "," + y + ") , insertPosition = " + dragInsertPosition);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(0, dragInsertPosition);
                } else if (action == 3) {
                    float x2 = dragEvent.getX();
                    float y2 = dragEvent.getY();
                    int dragInsertPosition2 = TaskBarRecentsView.this.getDragInsertPosition(x2, y2);
                    if (TaskBarRecentsView.this.mInsertingPosition >= 0 && dragInsertPosition2 > TaskBarRecentsView.this.mInsertingPosition) {
                        dragInsertPosition2--;
                    }
                    TaskBarRecentsView.this.mInsertingPosition = dragInsertPosition2;
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrop drop: (" + x2 + "," + y2 + ") , insertPosition = " + dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(2, dragInsertPosition2);
                    if (dragInsertPosition2 >= 0) {
                        ClipData clipData = dragEvent.getClipData();
                        TaskBarRecentsView.this.mTaskBarRecentsAdapter.insertShortcut(Integer.parseInt(clipData.getItemAt(1).getText().toString()), ComponentName.unflattenFromString(clipData.getItemAt(0).getText().toString()), dragInsertPosition2);
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 5) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag entered");
                    }
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (action == 6) {
                    if (TaskBarRecentsView.DEBUG) {
                        Log.d("TaskBarRecentsView", "onDrag exited");
                    }
                    TaskBarRecentsView.this.mTaskBarRecentsAdapter.updateInsertingStatus(1, -1);
                    TaskBarRecentsView.this.mInsertingPosition = -1;
                } else if (TaskBarRecentsView.DEBUG) {
                    Log.d("TaskBarRecentsView", "onDrag unhandled action: " + action);
                }
                return true;
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.motorola.taskbar.recent.TaskBarRecentsView.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i22) {
                super.onScrollStateChanged(recyclerView, i22);
                if (i22 == 0) {
                    TaskBarRecentsView.this.notifyScrollIdle();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDragInsertPosition(float f, float f2) {
        if (this.mRecyclerView.getChildCount() == 0) {
            return 0;
        }
        View viewFindChildViewUnder = this.mRecyclerView.findChildViewUnder(f, f2);
        if (viewFindChildViewUnder == null) {
            return (this.mInsertingPosition == 0 && this.mTaskBarRecentsAdapter.getShortcutsSize() == 1) ? 0 : -1;
        }
        int childLayoutPosition = this.mRecyclerView.getChildLayoutPosition(viewFindChildViewUnder);
        if (childLayoutPosition == -1) {
            return -1;
        }
        int itemViewType = this.mTaskBarRecentsAdapter.getItemViewType(childLayoutPosition);
        if (itemViewType != 0) {
            if (itemViewType == 1) {
                return (this.mTaskBarRecentsAdapter.getShortcutsSize() == 0 && childLayoutPosition == 0) ? 0 : -1;
            }
            if (itemViewType != 2) {
                return -1;
            }
        } else if (f >= viewFindChildViewUnder.getX() + (viewFindChildViewUnder.getWidth() / 2)) {
            return childLayoutPosition + 1;
        }
        return childLayoutPosition;
    }

    private void initView() {
        this.mRecyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R$layout.taskbar_recents, (ViewGroup) this, false);
        MyLayoutManager myLayoutManager = new MyLayoutManager(getContext(), 0, false);
        this.mLayoutManager = myLayoutManager;
        this.mRecyclerView.setLayoutManager(myLayoutManager);
        addView(this.mRecyclerView);
        this.mRecyclerView.setOnDragListener(this.mRecyclerViewOnDragListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyItemsChanged() {
        if (this.mOnRecentsStatusListener == null || this.mUIHandler.hasMessages(1)) {
            return;
        }
        this.mUIHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScrollIdle() {
        if (this.mOnRecentsStatusListener == null || this.mUIHandler.hasMessages(2)) {
            return;
        }
        this.mUIHandler.sendEmptyMessage(2);
    }

    public boolean canScrollHorizontally() {
        return this.mRecyclerView.canScrollHorizontally(-1) || this.mRecyclerView.canScrollHorizontally(1);
    }

    public boolean canScrollHorizontally(boolean z) {
        return this.mRecyclerView.canScrollHorizontally(z ? 1 : -1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.mTaskSyncreticController = (TaskSyncreticController) Dependency.get(TaskSyncreticController.class);
        TaskBarSyncreticRecentsAdapter taskBarSyncreticRecentsAdapter = new TaskBarSyncreticRecentsAdapter(getContext().getDisplayId(), this.mTaskSyncreticController, this.mDarkIntensity);
        this.mTaskBarRecentsAdapter = taskBarSyncreticRecentsAdapter;
        this.mRecyclerView.setAdapter(taskBarSyncreticRecentsAdapter);
        notifyItemsChanged();
        this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
        this.mTaskBarRecentsAdapter.destroy();
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        this.mRecyclerView.smoothScrollBy(i, i2);
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        TaskBarSyncreticRecentsAdapter taskBarSyncreticRecentsAdapter = this.mTaskBarRecentsAdapter;
        if (taskBarSyncreticRecentsAdapter != null) {
            taskBarSyncreticRecentsAdapter.setDarkIntensity(f);
        }
        for (int i = 0; i < this.mRecyclerView.getChildCount(); i++) {
            if (this.mRecyclerView.getChildAt(i) instanceof TaskItemView) {
                ((TaskItemView) this.mRecyclerView.getChildAt(i)).setDarkIntensity(f);
            } else if (this.mRecyclerView.getChildAt(i) instanceof TaskSyncreticItemView) {
                ((TaskSyncreticItemView) this.mRecyclerView.getChildAt(i)).setDarkIntensity(f);
            }
        }
    }

    public void setOnRecentsStatusListener(OnRecentsStatusListener onRecentsStatusListener) {
        this.mOnRecentsStatusListener = onRecentsStatusListener;
    }
}
