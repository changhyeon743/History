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
        int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipe = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(drag,swipe);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        itemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
