package com.demo.practicleapp.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.practicleapp.Adapter.ItemDisplayAdapter;
import com.demo.practicleapp.R;
import com.demo.practicleapp.db.Post;
import com.demo.practicleapp.db.PostViewModel;


public class ItemFragment extends Fragment {

    RecyclerView rv_category;
    ItemDisplayAdapter mAdapter;
    private PostViewModel postViewModel;
    CheckBox ckb_in_stock;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item, container, false);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        ckb_in_stock = view.findViewById(R.id.ckb_in_stock);
        rv_category = view.findViewById(R.id.rv_category);
        rv_category.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_category.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));

        prepareRecyclerView();

        mAdapter.setOnDeleteItem(new ItemDisplayAdapter.ItemClickListener() {
            @Override
            public void OnClick(Post listResponse, int position) {
                postViewModel.deletePost(listResponse);
            }
        });

        return view;
    }

    private void prepareRecyclerView() {
        mAdapter = new ItemDisplayAdapter(requireActivity());
        postViewModel.getAllPosts().observe(requireActivity(),
                posts -> mAdapter.addItemList(posts));
        rv_category.setAdapter(mAdapter);
    }

}
