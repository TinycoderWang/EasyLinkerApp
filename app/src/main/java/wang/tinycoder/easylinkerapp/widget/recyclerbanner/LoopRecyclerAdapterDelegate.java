package wang.tinycoder.easylinkerapp.widget.recyclerbanner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.widget.recyclerbanner
 * Desc：无限循环的适配器
 * Author：TinycoderWang
 * CreateTime：2018/5/6 8:45
 */
public class LoopRecyclerAdapterDelegate extends RecyclerView.Adapter<RecyclerHolder> {


    private RecyclerView.Adapter<RecyclerHolder> mAdapter;

    public LoopRecyclerAdapterDelegate(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, position % mAdapter.getItemCount());
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() == 0 ? 0 : Integer.MAX_VALUE / 100;
    }
}
