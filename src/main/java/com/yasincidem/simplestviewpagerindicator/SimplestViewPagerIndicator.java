package com.yasincidem.simplestviewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasin_000 on 18.9.2017.
 */

public class SimplestViewPagerIndicator extends LinearLayout {

    private static final float SCALE = 1.6f;
    private static final int NO_SCALE = 1;
    private static final int DEFAULT_VALUE = 10;

    private int mPageCount;
    private int mSelectedIndex;
    private int mItemSize = DEFAULT_VALUE;
    private int mDelimiterSize = DEFAULT_VALUE;

    @NonNull
    private final List<ImageView> images = new ArrayList<>();
    @Nullable
    private ViewPager.OnPageChangeListener pageChangeListener;

    public SimplestViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimplestViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplestViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator, 0, 0);
        try {
            mItemSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerIndicator_itemSize, DEFAULT_VALUE);
            mDelimiterSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerIndicator_delimiterSize, DEFAULT_VALUE);
        }finally {
            attributes.recycle();
        }

        if (isInEditMode())
            createEditLayout();
    }

    private void createEditLayout() {
        for (int i = 0; i < 3; i++) {
            final FrameLayout boxedItem = createBoxedItem(i);
            addView(boxedItem);
            if (i == 1){
                final View view  = boxedItem.getChildAt(0);
                final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height *= SCALE;
                layoutParams.width *= SCALE;
                view.setLayoutParams(layoutParams);
            }
        }
    }


    @NonNull
    private
    FrameLayout createBoxedItem(final int position) {
        final FrameLayout box = new FrameLayout(getContext());
        final ImageView item = createItem();
        box.addView(item);
        images.add(item);

        final LinearLayoutCompat.LayoutParams boxParams = new LinearLayoutCompat.LayoutParams(
                (int) (mItemSize * SCALE),
                (int) (mItemSize * SCALE)
        );
        if (position > 0) {
            boxParams.setMargins(mDelimiterSize, 0, 0, 0);
        }
        box.setLayoutParams(boxParams);
        return box;
    }

    @NonNull
    private
    ImageView createItem() {
        final ImageView index = new ImageView(getContext());
        final FrameLayout.LayoutParams indexParams = new FrameLayout.LayoutParams(
                mItemSize,
                mItemSize
        );
        indexParams.gravity = Gravity.CENTER;
        index.setLayoutParams(indexParams);
        index.setImageResource(R.drawable.circle);
        index.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return index;
    }

    public void setupWithViewPager(@NonNull final ViewPager viewPager){
        setPageCount(viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void setPageCount(int count) {
        mPageCount = count;
        mSelectedIndex = 0;
        removeAllViews();
        images.clear();

        for (int i = 0; i < mPageCount; i++) {
            addView(createBoxedItem(i));
        }
        setSelectedIndex(mSelectedIndex);
    }

    private
    void setSelectedIndex(final int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex > mPageCount - 1) {
            return;
        }

        final ImageView unselectedView = images.get(mSelectedIndex);
        unselectedView.animate().scaleX(NO_SCALE).scaleY(NO_SCALE).setDuration(300).start();

        final ImageView selectedView = images.get(selectedIndex);
        selectedView.animate().scaleX(SCALE).scaleY(SCALE).setDuration(300).start();

        mSelectedIndex = selectedIndex;
    }

    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener){
        pageChangeListener = onPageChangeListener;
    }


    private
    class MyOnPageChangeListener
            implements ViewPager.OnPageChangeListener
    {
        @Override
        public
        void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            if (pageChangeListener != null) {
                pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public
        void onPageSelected(final int position) {
            setSelectedIndex(position);
            if (pageChangeListener != null) {
                pageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public
        void onPageScrollStateChanged(final int state) {
            if (pageChangeListener != null) {
                pageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }
}
