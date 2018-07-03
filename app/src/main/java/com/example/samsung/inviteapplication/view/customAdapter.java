package com.example.samsung.inviteapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.example.samsung.inviteapplication.R;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ExampleViewHolder> {

    private ArrayList<gridItems> itemList; //list of gifs & captions

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public static  class ExampleViewHolder extends RecyclerView.ViewHolder{

        //public GifView gifView;
        public ImageView imgView;
        public TextView textView;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //gifView = itemView.findViewById(R.id.gif);
            imgView = itemView.findViewById(R.id.gif);
            textView = itemView.findViewById(R.id.caption);

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

    public customAdapter(ArrayList<gridItems> listItem)
    {
        itemList = listItem;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        gridItems currentItem = itemList.get(position);
        //holder.gifView.setGifResource(currentItem.getGifView());
        if(Grid_Activity.byteImageFromUser)
        {
            byte[] userImg = currentItem.getImgByte();
            Bitmap bitmap = BitmapFactory.decodeByteArray(userImg,0,userImg.length);
            holder.imgView.setImageBitmap(bitmap);
            Grid_Activity.byteImageFromUser=false;
        }else {
            holder.imgView.setImageResource(currentItem.getImageView());
        }

        holder.textView.setText(currentItem.getCaption());
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, listener);
        return evh;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
