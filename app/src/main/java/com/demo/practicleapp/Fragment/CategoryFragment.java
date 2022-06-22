package com.demo.practicleapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.practicleapp.Adapter.CategoryAdapter;
import com.demo.practicleapp.ApiClasses.ApiPostViewModel;
import com.demo.practicleapp.ClsGlobal;
import com.demo.practicleapp.R;

public class CategoryFragment extends Fragment {

    RecyclerView rv_category;
    private ApiPostViewModel androidViewModel;
    private ProgressDialog pd;
    private CategoryAdapter mCategoriesAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rv_category = view.findViewById(R.id.rv_category);
        rv_category.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_category.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));
        androidViewModel = new ViewModelProvider(this).get(ApiPostViewModel.class);
        pd = new ProgressDialog(getActivity());
        pd.show();
        mCategoriesAdapter = new CategoryAdapter(requireActivity());
        callSaleCategory(view);
        rv_category.setAdapter(mCategoriesAdapter);

        return view;
    }


    void callSaleCategory(View view) {
        androidViewModel.getCategoriesAPIList().observe(requireActivity(), lst -> {
            if (lst.getCategories().size() > 0) {
                mCategoriesAdapter.addItemList(lst.getCategories());
            } else {
                Toast.makeText(requireActivity(), "No Categories Founds...!", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        });
    }

}
