package com.demo.practicleapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.demo.practicleapp.ApiClasses.Category;
import com.demo.practicleapp.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> lstCategory = new ArrayList<>();

    public CategoryAdapter(Context context) {
        this.mContext = context;
    }

    public void addItemList(List<Category> updateList) {
        this.lstCategory = updateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentObj = lstCategory.get(position);

        Glide.with(mContext)
                .load(lstCategory.get(position).getImage())
                .centerInside()
                .into(holder.iv_image);

        Log.d("--Test--", "onBindViewHolder: " + currentObj.getTitle());
        holder.txt_label.setText(currentObj.getTitle());
    }

    @Override
    public int getItemCount() {
        return lstCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_label;
        private ImageView iv_image;
        private LinearLayout ll_header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            txt_label = itemView.findViewById(R.id.txt_label);
            iv_image = itemView.findViewById(R.id.iv_image);
        }


    }


}
