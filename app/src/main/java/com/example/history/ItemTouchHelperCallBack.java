package com.example.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;




public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback{


    public interface OnItemMoveListener {
        boolean onItemMove(int from, int to);
    };
    private final OnItemMoveListener itemMoveListener;

    public ItemTouchHelperCallBack(OnItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }


    @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
        return makeMovementFlags(drag,0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        itemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
