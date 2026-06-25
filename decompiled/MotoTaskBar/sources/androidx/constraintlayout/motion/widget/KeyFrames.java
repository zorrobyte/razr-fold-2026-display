package androidx.constraintlayout.motion.widget;

import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class KeyFrames {
    static HashMap sKeyMakers;
    private HashMap mFramesMap = new HashMap();

    static {
        HashMap map = new HashMap();
        sKeyMakers = map;
        try {
            Class[] clsArr = new Class[0];
            map.put("KeyAttribute", KeyAttributes.class.getConstructor(null));
            Class[] clsArr2 = new Class[0];
            sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor(null));
            Class[] clsArr3 = new Class[0];
            sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor(null));
            Class[] clsArr4 = new Class[0];
            sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(null));
            Class[] clsArr5 = new Class[0];
            sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor(null));
        } catch (NoSuchMethodException e) {
            Log.e("KeyFrames", "unable to load", e);
        }
    }

    public KeyFrames() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public KeyFrames(android.content.Context r9, org.xmlpull.v1.XmlPullParser r10) {
        /*
            Method dump skipped, instruction units count: 272
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyFrames.<init>(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public void addAllFrames(MotionController motionController) {
        ArrayList arrayList = (ArrayList) this.mFramesMap.get(-1);
        if (arrayList != null) {
            motionController.addKeys(arrayList);
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList arrayList = (ArrayList) this.mFramesMap.get(Integer.valueOf(motionController.mId));
        if (arrayList != null) {
            motionController.addKeys(arrayList);
        }
        ArrayList arrayList2 = (ArrayList) this.mFramesMap.get(-1);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                Key key = (Key) obj;
                if (key.matches(((ConstraintLayout.LayoutParams) motionController.mView.getLayoutParams()).constraintTag)) {
                    motionController.addKey(key);
                }
            }
        }
    }

    public void addKey(Key key) {
        if (!this.mFramesMap.containsKey(Integer.valueOf(key.mTargetId))) {
            this.mFramesMap.put(Integer.valueOf(key.mTargetId), new ArrayList());
        }
        ArrayList arrayList = (ArrayList) this.mFramesMap.get(Integer.valueOf(key.mTargetId));
        if (arrayList != null) {
            arrayList.add(key);
        }
    }

    public ArrayList getKeyFramesForView(int i) {
        return (ArrayList) this.mFramesMap.get(Integer.valueOf(i));
    }
}
