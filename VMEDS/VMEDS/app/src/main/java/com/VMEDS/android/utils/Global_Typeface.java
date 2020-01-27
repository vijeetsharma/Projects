package com.VMEDS.android.utils;

import android.content.Context;
import android.graphics.Typeface;

public class Global_Typeface {

    public static Typeface Roboto_Bold, Roboto_Regular;
    Context context;

    public Global_Typeface(Context cntx) {

        this.context = cntx;

        try {
            Roboto_Bold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Bold.ttf");

            Roboto_Regular = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Regular.ttf");
        } catch (Exception e) {
        }
    }

    public Typeface TypeFace_Roboto_Bold() {
        return Roboto_Bold;
    }

    public Typeface TypeFace_Roboto_Regular() {
        return Roboto_Regular;
    }
}
