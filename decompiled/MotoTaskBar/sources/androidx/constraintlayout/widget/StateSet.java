package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public class StateSet {
    int mDefaultState = -1;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray mStateList = new SparseArray();

    class State {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        ArrayList mVariants = new ArrayList();

        State(Context context, XmlPullParser xmlPullParser) {
            this.mConstraintID = -1;
            this.mIsLayout = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R$styleable.State_android_id) {
                    this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == R$styleable.State_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        this.mIsLayout = true;
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        void add(Variant variant) {
            this.mVariants.add(variant);
        }

        public int findMatch(float f, float f2) {
            for (int i = 0; i < this.mVariants.size(); i++) {
                if (((Variant) this.mVariants.get(i)).match(f, f2)) {
                    return i;
                }
            }
            return -1;
        }
    }

    class Variant {
        int mConstraintID;
        boolean mIsLayout;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        Variant(Context context, XmlPullParser xmlPullParser) {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            this.mIsLayout = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R$styleable.Variant_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        this.mIsLayout = true;
                    }
                } else if (index == R$styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxHeight);
                } else if (index == R$styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMinHeight);
                } else if (index == R$styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxWidth);
                } else if (index == R$styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        boolean match(float f, float f2) {
            if (!Float.isNaN(this.mMinWidth) && f < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && f2 < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || f <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || f2 <= this.mMaxHeight;
            }
            return false;
        }
    }

    public StateSet(Context context, XmlPullParser xmlPullParser) {
        load(context, xmlPullParser);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void load(android.content.Context r11, org.xmlpull.v1.XmlPullParser r12) {
        /*
            r10 = this;
            java.lang.String r0 = "Error parsing XML resource"
            java.lang.String r1 = "ConstraintLayoutStates"
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r12)
            int[] r3 = androidx.constraintlayout.widget.R$styleable.StateSet
            android.content.res.TypedArray r2 = r11.obtainStyledAttributes(r2, r3)
            int r3 = r2.getIndexCount()
            r4 = 0
            r5 = r4
        L14:
            if (r5 >= r3) goto L29
            int r6 = r2.getIndex(r5)
            int r7 = androidx.constraintlayout.widget.R$styleable.StateSet_defaultState
            if (r6 != r7) goto L26
            int r7 = r10.mDefaultState
            int r6 = r2.getResourceId(r6, r7)
            r10.mDefaultState = r6
        L26:
            int r5 = r5 + 1
            goto L14
        L29:
            r2.recycle()
            int r2 = r12.getEventType()     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            r3 = 0
        L31:
            r5 = 1
            if (r2 == r5) goto La8
            java.lang.String r6 = "StateSet"
            r7 = 3
            r8 = 2
            if (r2 == r8) goto L4e
            if (r2 == r7) goto L3e
            goto L9c
        L3e:
            java.lang.String r2 = r12.getName()     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            boolean r2 = r6.equals(r2)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r2 == 0) goto L9c
            goto La8
        L4a:
            r10 = move-exception
            goto La1
        L4c:
            r10 = move-exception
            goto La5
        L4e:
            java.lang.String r2 = r12.getName()     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            int r9 = r2.hashCode()     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            switch(r9) {
                case 80204913: goto L75;
                case 1301459538: goto L6b;
                case 1382829617: goto L64;
                case 1901439077: goto L5a;
                default: goto L59;
            }     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
        L59:
            goto L7f
        L5a:
            java.lang.String r5 = "Variant"
            boolean r2 = r2.equals(r5)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r2 == 0) goto L7f
            r5 = r7
            goto L80
        L64:
            boolean r2 = r2.equals(r6)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r2 == 0) goto L7f
            goto L80
        L6b:
            java.lang.String r5 = "LayoutDescription"
            boolean r2 = r2.equals(r5)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r2 == 0) goto L7f
            r5 = r4
            goto L80
        L75:
            java.lang.String r5 = "State"
            boolean r2 = r2.equals(r5)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r2 == 0) goto L7f
            r5 = r8
            goto L80
        L7f:
            r5 = -1
        L80:
            if (r5 == r8) goto L90
            if (r5 == r7) goto L85
            goto L9c
        L85:
            androidx.constraintlayout.widget.StateSet$Variant r2 = new androidx.constraintlayout.widget.StateSet$Variant     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            r2.<init>(r11, r12)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            if (r3 == 0) goto L9c
            r3.add(r2)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            goto L9c
        L90:
            androidx.constraintlayout.widget.StateSet$State r3 = new androidx.constraintlayout.widget.StateSet$State     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            r3.<init>(r11, r12)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            android.util.SparseArray r2 = r10.mStateList     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            int r5 = r3.mId     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            r2.put(r5, r3)     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
        L9c:
            int r2 = r12.next()     // Catch: java.io.IOException -> L4a org.xmlpull.v1.XmlPullParserException -> L4c
            goto L31
        La1:
            android.util.Log.e(r1, r0, r10)
            goto La8
        La5:
            android.util.Log.e(r1, r0, r10)
        La8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public int convertToConstraintSet(int i, int i2, float f, float f2) {
        State state = (State) this.mStateList.get(i2);
        if (state == null) {
            return i2;
        }
        int i3 = 0;
        if (f != -1.0f && f2 != -1.0f) {
            ArrayList arrayList = state.mVariants;
            int size = arrayList.size();
            Variant variant = null;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                Variant variant2 = (Variant) obj;
                if (variant2.match(f, f2)) {
                    if (i != variant2.mConstraintID) {
                        variant = variant2;
                    }
                }
            }
            return variant != null ? variant.mConstraintID : state.mConstraintID;
        }
        if (state.mConstraintID != i) {
            ArrayList arrayList2 = state.mVariants;
            int size2 = arrayList2.size();
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                if (i == ((Variant) obj2).mConstraintID) {
                }
            }
            return state.mConstraintID;
        }
        return i;
    }

    public int stateGetConstraintID(int i, int i2, int i3) {
        return updateConstraints(-1, i, i2, i3);
    }

    public int updateConstraints(int i, int i2, float f, float f2) {
        int iFindMatch;
        if (i == i2) {
            State state = i2 == -1 ? (State) this.mStateList.valueAt(0) : (State) this.mStateList.get(this.mCurrentStateId);
            if (state == null) {
                return -1;
            }
            return ((this.mCurrentConstraintNumber == -1 || !((Variant) state.mVariants.get(i)).match(f, f2)) && i != (iFindMatch = state.findMatch(f, f2))) ? iFindMatch == -1 ? state.mConstraintID : ((Variant) state.mVariants.get(iFindMatch)).mConstraintID : i;
        }
        State state2 = (State) this.mStateList.get(i2);
        if (state2 == null) {
            return -1;
        }
        int iFindMatch2 = state2.findMatch(f, f2);
        return iFindMatch2 == -1 ? state2.mConstraintID : ((Variant) state2.mVariants.get(iFindMatch2)).mConstraintID;
    }
}
