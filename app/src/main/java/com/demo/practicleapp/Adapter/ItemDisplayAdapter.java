package com.demo.practicleapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.practicleapp.R;
import com.demo.practicleapp.db.Post;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ItemDisplayAdapter extends RecyclerView.Adapter<ItemDisplayAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> lstCategory = new ArrayList<>();

    public ItemDisplayAdapter(Context context) {
        this.mContext = context;
    }


    public interface ItemClickListener {
        void OnClick(Post listResponse, int position);
    }

    private ItemClickListener itemClickListener;

    public void setOnDeleteItem(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addItemList(List<Post> updateList) {

/*
        if (lstCategory != null) {
            lstCategory.clear();
            lstCategory.addAll(updateList);
            notifyDataSetChanged();
        } else {
            lstCategory = updateList;
        }
        */
        this.lstCategory = updateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_add_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currentObj = lstCategory.get(position);
        Bitmap myBitmap = BitmapFactory.decodeFile(lstCategory.get(position).getItem_image_path());

        Glide.with(mContext)
                .load(myBitmap)
                .centerInside()
                .into(holder.iv_image);

        holder.txt_label_1.setText(currentObj.getItem_title());
        holder.txt_label_2.setText(currentObj.getItem_category());
        holder.txt_label_3.setText("$" + currentObj.getItem_price().toString());

        if (currentObj.getItem_is_stock()) {
            holder.txt_label_4.setText("In stock");
        } else {
            holder.txt_label_4.setText("Out of stock");
        }
        holder.Bind(lstCategory.get(position), itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return lstCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_label_1, txt_label_2, txt_label_3, txt_label_4;
        private ImageView iv_image;
        private ImageView iv_delete;
        private LinearLayout ll_header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            txt_label_1 = itemView.findViewById(R.id.txt_label_1);
            txt_label_2 = itemView.findViewById(R.id.txt_label_2);
            txt_label_3 = itemView.findViewById(R.id.txt_label_3);
            txt_label_4 = itemView.findViewById(R.id.txt_label_4);
            iv_image = itemView.findViewById(R.id.iv_image);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }

        void Bind(Post obj, ItemClickListener itemClickListener, int position) {
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnClick(obj, position);
                }
            });
        }

    }


}
