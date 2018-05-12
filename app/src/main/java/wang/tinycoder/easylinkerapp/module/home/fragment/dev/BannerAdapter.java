package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.bean.Banner;
import wang.tinycoder.easylinkerapp.net.imageloader.GlideApp;
import wang.tinycoder.easylinkerapp.widget.recyclerbanner.RecyclerHolder;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/5/6 8:54
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.Holder> {


    private Context mContext;
    private List<Banner> bannerData;


    public BannerAdapter(Context mContext, List<Banner> bannerData) {
        this.mContext = mContext;
        this.bannerData = bannerData;
    }

    @NonNull
    @Override
    public BannerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner_item, parent, false);
        return new BannerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.Holder holder, int position) {
        if (bannerData != null && bannerData.size() > position) {
            Banner banner = bannerData.get(position);
            String url = banner.getImg();
            // Banner图片
            GlideApp.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.ic_banner_holder)
                    .error(R.drawable.ic_banner_holder)
                    .into(holder.mIvBanner);

            // 标题
            String title = banner.getTitle();
            holder.mTvTitle.setText(TextUtils.isEmpty(title) ? "" : title);

            // 点击事件
            holder.itemView.setTag(R.id.holder_pos, position);
            holder.itemView.setOnClickListener(clicklistener);
            // 为了消费掉用户长按事件，防止出发点击事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bannerData == null ? 0 : bannerData.size();
    }

    static class Holder extends RecyclerHolder {
        @BindView(R.id.iv_banner)
        ImageView mIvBanner;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private View.OnClickListener clicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag(R.id.holder_pos);
            if (bannerData != null && bannerData.size() > pos) {
                Banner banner = bannerData.get(pos);
                Toast.makeText(mContext, banner.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
