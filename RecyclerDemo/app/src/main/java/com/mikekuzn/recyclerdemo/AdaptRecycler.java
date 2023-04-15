package com.mikekuzn.recyclerdemo;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;


import java.lang.reflect.Field;
import java.util.List;

// Adaptation of recycler view for foldable device
public class AdaptRecycler {
    final static String TAG = "RecyclerDemo";

    static void Invoke (View recycler) {
// Here we can not use:
//  androidx.recyclerview.widget.RecyclerView
//  android.support.v7.widget.RecyclerView
// because this cod will be use on framework

        ViewParent parent = recycler.getParent();
        if (!(parent instanceof ViewGroup)) {
            return;
        }
        ViewGroup group = (ViewGroup)parent;
        group.removeView(recycler);
        LinearLayout linearLayout = new LinearLayout(group.getContext());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.addView(recycler);
        try {
            View newRecycler = recycler
                    .getClass()
                    .getDeclaredConstructor(Context.class)
                    .newInstance((Context) group.getContext());
            LogSupers(newRecycler);
            Log.d(TAG, "newInstance=" + newRecycler + " base=" + recycler);
            Object adapter = recycler.getClass().getMethod("getAdapter").invoke(recycler);
            assert adapter != null;
            LogSupers(adapter);
            Log.d(TAG, "Adapter=" + adapter);
            newRecycler.getClass()
                    .getMethod("setAdapter", getPenultimateSuper(adapter))
                    .invoke(newRecycler, adapter);
            Field field = recycler.getClass().getDeclaredField("mLayout");
            field.setAccessible(true);
            Object baseLayoutManager = field.get(recycler);
            Object layoutManager = null;
            if (isSuperContain(baseLayoutManager, "LinearLayoutManager")) {
                layoutManager = baseLayoutManager.getClass()
                        .getDeclaredConstructor(Context.class)
                        .newInstance(group.getContext());
            } if (isSuperContain(baseLayoutManager, "StaggeredGridLayoutManager")) {
                layoutManager = baseLayoutManager.getClass()
                        .getDeclaredConstructor(
                                Context.class,
                                Class.forName("android.util.AttributeSet"),
                                int.class,
                                int.class)
                        .newInstance(group.getContext(), null, 2, 0);
            }else {
                //TODO("")
            }
            LogSupers(layoutManager);
            newRecycler.getClass()
                    .getMethod("setLayoutManager", getPenultimateSuper(layoutManager))
                    .invoke(newRecycler, layoutManager);
            newRecycler.setLayoutParams(recycler.getLayoutParams());
            linearLayout.addView(newRecycler);
        } catch (Exception e) {
            Log.e(TAG, "newInstance Exception " + e);
        }
        group.addView(linearLayout);
    }

    static void LogSupers(Object object) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Log.i(TAG, "LogSupers class=" + clazz.getName());
            clazz = clazz.getSuperclass();
        }
    }

    static boolean isSuperContain(Object object, String className){
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Log.i(TAG, "isSuperContain class=" + clazz.getName() + "=?=" + className);
            if (clazz.getName().contains(className)) {
                return true;
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    static Class<?> getPenultimateSuper(Object object) {
        if (object == null)
            return null;
        Class<?> clazz = object.getClass();
        Class<?> PenultimateClass = null;
        while (clazz != null) {
            if ("java.lang.Object".equals(clazz.getName())){
                return PenultimateClass;
            }
            PenultimateClass = clazz;
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}
