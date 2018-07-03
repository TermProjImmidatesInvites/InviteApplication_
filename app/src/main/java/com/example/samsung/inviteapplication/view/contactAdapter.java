package com.example.samsung.inviteapplication.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsung.inviteapplication.R;

import java.util.ArrayList;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ExampleViewHolder> {

    private ArrayList<item> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        //void onChecked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public static  class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public TextView textView1;
        public ImageView imgView;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ex_img);
            textView = itemView.findViewById(R.id.ex_text);
            textView1 = itemView.findViewById(R.id.ex_text1);
            imgView = itemView.findViewById(R.id.checked);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public contactAdapter(ArrayList<item> listItem)
    {
        itemList = listItem;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        item currentItem = itemList.get(position);
        holder.imageView.setImageResource(currentItem.getImgResource());
        holder.textView.setText(currentItem.getLine());
        holder.textView1.setText(currentItem.getLine1());
        holder.imgView.setImageResource(currentItem.getImgResource2());
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_adapter_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, listener);
        return evh;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
