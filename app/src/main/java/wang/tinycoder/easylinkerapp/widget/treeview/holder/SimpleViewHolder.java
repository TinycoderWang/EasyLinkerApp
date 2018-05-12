package wang.tinycoder.easylinkerapp.widget.treeview.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import wang.tinycoder.easylinkerapp.widget.treeview.model.TreeNode;


public class SimpleViewHolder extends TreeNode.BaseNodeViewHolder<Object> {

    public SimpleViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, Object value) {
        final TextView tv = new TextView(context);
        tv.setText(String.valueOf(value));
        return tv;
    }

    @Override
    public void toggle(boolean active) {

    }
}
