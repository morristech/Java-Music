package com.nihk.github.musictheoryromannumeralanalysisquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MusicTextView extends TextView {
    public MusicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public MusicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MusicTextView(Context context) {
        super(context);
        init();
    }
    private void init() {
        String freeSerifPath = "fonts/FreeSerif.ttf";
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), freeSerifPath);
        setTypeface(tf);
    }
}