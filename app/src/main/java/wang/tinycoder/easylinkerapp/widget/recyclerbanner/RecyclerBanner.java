package wang.tinycoder.easylinkerapp.widget.recyclerbanner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import wang.tinycoder.easylinkerapp.R;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author WangYh
 * @version V1.0
 * @Name: RecyclerBanner
 * @Package wang.tinycoder.easylinkerapp.widget.recyclerbanner
 * @Description: 用RecyclerView实现的Banner
 * @date 2018/5/5 0005
 */
public class RecyclerBanner extends FrameLayout {

    // 条目改变的监听
    private OnBannerItemChangedListener mItemChangedListener;
    // 刷新间隔时间
    private int autoPlayDuration;
    // 是否显示指示器
    private boolean showIndicator;
    // 指示器的RecyclerView
    private RecyclerView indicatorContainer;
    // 选中的指示点
    private Drawable mSelectedDrawable;
    // 未选中的指示点
    private Drawable mUnselectedDrawable;
    // 指示点的适配器
    private IndicatorAdapter indicatorAdapter;
    // 指示器间距
    private int indicatorMargin;
    // 要显示内容的RecyclerView
    private RecyclerView mRecyclerView;

    // 指示器颜色
    private int indicatorSelectColor;
    private int indicatorUnSelectColor;
    // 指示器的大小
    private int indicatorSize = 6;

    // 要显示内容的LayoutManager
    private LinearLayoutManager mLayoutManager;
    // 自动播放的标识
    private int WHAT_AUTO_PLAY = 1000;
    // 是否进行了初始化
    private boolean hasInit;
    // 条目数量
    private int bannerSize = 0;
    // 当前的banner位置
    private int currentIndex;
    // 是否在播放
    private boolean isPlaying = false;
    // 是否自动播放
    private boolean isAutoPlaying = true;

    // 用于控制自动播放的handler
    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_AUTO_PLAY) {
                if (currentIndex == mLayoutManager.findFirstVisibleItemPosition()) {
                    ++currentIndex;
                    mRecyclerView.smoothScrollToPosition(currentIndex);
                    mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                    refreshIndicator();
                }
            }
            return true;
        }
    });

    public RecyclerBanner(Context context) {
        this(context, null);
    }

    public RecyclerBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerBanner);
        showIndicator = a.getBoolean(R.styleable.RecyclerBanner_showIndicator, true);
        autoPlayDuration = a.getInt(R.styleable.RecyclerBanner_interval, 4000);
        isAutoPlaying = a.getBoolean(R.styleable.RecyclerBanner_autoPlaying, true);
        indicatorSelectColor = a.getColor(R.styleable.RecyclerBanner_selectColor, 0xFFFFFFFF);
        indicatorUnSelectColor = a.getColor(R.styleable.RecyclerBanner_unSelectColor, 0xFF888888);

        // 创建指示点
        if (mSelectedDrawable == null) {
            //绘制默认选中状态图形
            GradientDrawable selectedGradientDrawable = new GradientDrawable();
            selectedGradientDrawable.setShape(GradientDrawable.OVAL);
            selectedGradientDrawable.setColor(indicatorSelectColor);
            selectedGradientDrawable.setSize(dp2px(indicatorSize), dp2px(indicatorSize));
            selectedGradientDrawable.setCornerRadius(dp2px(indicatorSize) / 2);
            mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        }
        if (mUnselectedDrawable == null) {
            //绘制默认未选中状态图形
            GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
            unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
            unSelectedGradientDrawable.setColor(indicatorUnSelectColor);
            unSelectedGradientDrawable.setSize(dp2px(indicatorSize), dp2px(indicatorSize));
            unSelectedGradientDrawable.setCornerRadius(dp2px(indicatorSize) / 2);
            mUnselectedDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        }

        // 指示点的边距
        indicatorMargin = dp2px(4);
        int marginTop = dp2px(4);
        int marginLeft = dp2px(16);
        int marginRight = dp2px(16);
        int marginBottom = dp2px(4);
        int o = a.getInt(R.styleable.RecyclerBanner_orientation, 0);
        int orientation = 0;
        if (o == 0) {
            orientation = OrientationHelper.HORIZONTAL;
        } else if (o == 1) {
            orientation = OrientationHelper.VERTICAL;
        }
        a.recycle();

        //轮播图部分
        mRecyclerView = new RecyclerView(context);
        FrameLayout.LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, vpLayoutParams);
        mLayoutManager = new LinearLayoutManager(context, orientation, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        //指示器部分
        indicatorContainer = new RecyclerView(context);
        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(context, orientation, false);
        indicatorContainer.setLayoutManager(indicatorLayoutManager);
        indicatorAdapter = new IndicatorAdapter();
        indicatorContainer.setAdapter(indicatorAdapter);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        addView(indicatorContainer, params);
        if (!showIndicator) {
            indicatorContainer.setVisibility(GONE);
        }
    }

    // 设置是否禁止滚动播放
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.isAutoPlaying = isAutoPlaying;
        setPlaying(this.isAutoPlaying);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    //设置是否显示指示器
    public void setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
        indicatorContainer.setVisibility(showIndicator ? VISIBLE : GONE);
    }

    /**
     * 设置轮播间隔时间
     *
     * @param autoPlayDuration 时间毫秒
     */
    public void setAutoPlayDuration(int autoPlayDuration) {
        this.autoPlayDuration = autoPlayDuration;
    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    public synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying && hasInit) {
            if (!isPlaying && playing) {
                mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                mHandler.removeMessages(WHAT_AUTO_PLAY);
                isPlaying = false;
            }
        }
    }


    /**
     * 设置轮播数据集
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        hasInit = false;
        LoopRecyclerAdapterDelegate loopRecyclerAdapterDelegate = new LoopRecyclerAdapterDelegate(adapter);
        mRecyclerView.setAdapter(loopRecyclerAdapterDelegate);
        bannerSize = adapter.getItemCount();
        if (bannerSize > 0) {
            int middle = loopRecyclerAdapterDelegate.getItemCount() / 2;
            currentIndex = middle - (middle % bannerSize);
            mRecyclerView.scrollToPosition(currentIndex);
        }
        // 刷新指示点
        refreshIndicator();
        setPlaying(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == SCROLL_STATE_IDLE) {
                    int first = mLayoutManager.findFirstVisibleItemPosition();
                    refreshIndicator(first);
                    currentIndex = first;
                    // banner条目改变的监听
                    if (mItemChangedListener != null) {
                        if (bannerSize > 0) {
                            mItemChangedListener.onItemChanged(first % bannerSize);
                            Log.d("xxx", "onScrollStateChanged  ---  position : " + (first % bannerSize));
                        }
                    }

                }
            }
        });
        hasInit = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isPlaying){
                    setPlaying(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(!isPlaying){
                    setPlaying(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setPlaying(true);
        } else {
            setPlaying(false);
        }
    }

    /**
     * 标示点适配器
     */
    protected class IndicatorAdapter extends RecyclerView.Adapter {

        int currentPosition = 0;

        public void setPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ImageView bannerPoint = new ImageView(getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(indicatorMargin, 0, indicatorMargin, 0);
            bannerPoint.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(bannerPoint) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView bannerPoint = (ImageView) holder.itemView;
            bannerPoint.setImageDrawable(currentPosition == position ? mSelectedDrawable : mUnselectedDrawable);

        }

        @Override
        public int getItemCount() {
            return bannerSize;
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 改变导航的指示点
     */
    protected synchronized void refreshIndicator() {
        if (showIndicator && bannerSize > 1) {
            indicatorAdapter.setPosition(currentIndex % bannerSize);
            indicatorAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 改变导航的指示点
     */
    protected synchronized void refreshIndicator(int position) {
        if (showIndicator && bannerSize > 1) {
            indicatorAdapter.setPosition(position % bannerSize);
            indicatorAdapter.notifyDataSetChanged();
        }
    }

    // 条目改变
    public interface OnBannerItemChangedListener {
        void onItemChanged(int position);
    }

    public void setOnBannerItemChangedListener(OnBannerItemChangedListener onBannerItemChangedListener) {
        this.mItemChangedListener = onBannerItemChangedListener;
    }
}
