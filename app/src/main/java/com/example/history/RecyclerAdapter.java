package com.example.history;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ExampleViewHolder> implements ItemTouchHelperCallBack.OnItemMoveListener {

    private List<String> examples;


    private int type;

    public RecyclerAdapter(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onStartDrag(ExampleViewHolder holder);
        void answer(String text);
    }

    private final Listener listener;

    public List<String> getExamples() {
        return examples;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getAnswerText() {
        return String.join(" / ",examples);
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }



    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int i) {
        holder.onBind(examples.get(i));

        holder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (type == 1) {
                    if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                        listener.onStartDrag(holder);
                    }
                } else {
                    listener.answer(holder.textView.getText().toString());
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return examples.size();
    }



    @Override
    public boolean onItemMove(int from, int to) {

        String prev = examples.remove(from);
        examples.add(to > from ? to - 1 : to,prev);
        notifyItemMoved(from,to);

//        if (from < to) {
//            for (int i = from; i < to; i++) {
//                Collections.swap(examples, i, i + 1);
//            }
//        } else {
//            for (int i = from; i > to; i--) {
//                Collections.swap(examples, i, i - 1);
//            }
//        }

        return true;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        ExampleViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }

        void onBind(String example) {
            textView.setText(example);
        }
    }
}


