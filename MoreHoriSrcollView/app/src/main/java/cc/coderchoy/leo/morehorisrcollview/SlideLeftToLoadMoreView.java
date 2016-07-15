package cc.coderchoy.leo.morehorisrcollview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by leo
 * on 2016/7/13.
 */
public class SlideLeftToLoadMoreView extends FrameLayout {

    private static final float DEFAULT_RIGHT_VIEW_WIDTH = 60;

    private float mTouchStartX;
    private int rightViewWidth;
    public OnShowMoreListener onShowMoreListener;

    private View mChildView;
    private EdgeEffectTextView mRightView;

    public SlideLeftToLoadMoreView(Context context) {
        super(context);
        init();
    }

    public SlideLeftToLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideLeftToLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addRightView();
        rightViewWidth = dp2px(getContext(), DEFAULT_RIGHT_VIEW_WIDTH);
    }

    private void addRightView() {
        mRightView = new EdgeEffectTextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.RIGHT;
        mRightView.setLayoutParams(params);
        mRightView.setVisibility(GONE);
        mRightView.setTipText("查看更多");
        addView(mRightView);
    }

    /**
     * 设置左滑到底监听
     */
    public void setOnShowMoreListener(OnShowMoreListener onShowMoreListener) {
        this.onShowMoreListener = onShowMoreListener;
    }

    /**
     * 设置左滑距离
     */
    public void setRightViewWidth(int rightViewWidth) {
        this.rightViewWidth = dp2px(getContext(), rightViewWidth);
    }

    /**
     * 设置左滑提示文字
     */
    public void setRightViewText(String text) {
        mRightView.setTipText(text);
    }

    /**
     * 设置左滑波浪颜色
     */
    public void setRightViewEdgeEffectColor(int color) {
        mRightView.setEdgeEffectColor(color);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 2) {
            throw new RuntimeException("You can only attach one child");
        }
        if (getChildCount() == 1) {
            if (child instanceof HorizontalScrollView || child instanceof RecyclerView) {
                mChildView = child;
            } else {
                throw new RuntimeException("You child must be HorizontalScrollView or RecyclerView");
            }
        }
        super.addView(child, index, params);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mTouchStartX - ev.getX();
                if (dx > 0 && mChildView != null && isChildViewCanScroll()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isChildViewCanScroll() {
        if (mChildView instanceof RecyclerView) {
            LinearLayoutManager manager = (LinearLayoutManager) ((RecyclerView) mChildView).getLayoutManager();
            return manager.findLastCompletelyVisibleItemPosition() == manager.getItemCount()-1;
        }
        if (mChildView instanceof HorizontalScrollView) {
            View view = ((HorizontalScrollView) mChildView).getChildAt(0);
            return mChildView.getScrollX() + mChildView.getMeasuredWidth() >= view.getWidth();
        }
        return ViewCompat.canScrollHorizontally(mChildView, -1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = (mTouchStartX - ev.getX()) > rightViewWidth ? rightViewWidth : mTouchStartX - ev.getX();
                if (mChildView != null) {
                    mChildView.setTranslationX(-dx);
                }
                mRightView.setVisibility(VISIBLE);
                mRightView.setEdgeEffectWidth((int) dx);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mRightView.getEdgeEffectWidth() >= rightViewWidth && onShowMoreListener != null) {
                    onShowMoreListener.onShowMore();
                }
                if (mChildView != null) {
                    mChildView.animate().translationX(0).start();
                }
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mRightView, "edgeEffectWidth", mRightView.getEdgeEffectWidth(), 0);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRightView.setVisibility(GONE);
                    }
                });
                objectAnimator.start();
                return true;
        }
        return super.onTouchEvent(ev);
    }

    interface OnShowMoreListener {
        void onShowMore();
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
