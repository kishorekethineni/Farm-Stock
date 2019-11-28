package com.example.farmstock.ui.tools;
//Developed By Pranshu Ranjan
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmstock.R;

import java.util.ArrayList;
import java.util.Objects;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int positon);
        void onDeleteClick(int position);
    }
    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mtext1, mtext2;
        public ImageView ItemRemover;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mtext1 =(TextView) itemView.findViewById(R.id.LText1);
            mtext2 = (TextView) itemView.findViewById(R.id.LText2);
            ItemRemover=(ImageView)itemView.findViewById(R.id.DeleteItemBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int positon =getAdapterPosition();
                        if(positon!=RecyclerView.NO_POSITION){
                            listener.onItemClick(positon);
                        }
                    }
                }
            });
            ItemRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int positon =getAdapterPosition();
                        if(positon!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(positon);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        Objects.requireNonNull(holder).mtext1.setText(currentItem.getmText1());
        Objects.requireNonNull(holder).mtext2.setText(currentItem.getmText2());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
