package com.ebook.app.view.catalogue;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.config.DataMock;
import com.ebook.app.databinding.PageCatalogueBinding;
import com.ebook.app.model.Category;
import com.ebook.app.view.function.FunctionActivity;
import com.ebook.app.view.function.FunctionListAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class CatalogueActivity extends AppCompatActivity {
    private PageCatalogueBinding binding;
    private RecyclerView rvCategory,rvFunction;
    private CatalogueCategoryAdapter categoryAdapter;
    private FunctionListAdapter functionAdapter;
    private List<Category> categoryList;
    private MaterialToolbar topAppBar;
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        if(getIntent().hasExtra("index")){
            index=getIntent().getIntExtra("index",0);
        }
        initTopAppBar();
        initCategory();
        initFunction();
        changeIndex(index);
    }

    private void initCategory(){
        rvCategory=binding.catalogueRvCategory;
        categoryList = DataMock.catalogue;
        categoryAdapter = new CatalogueCategoryAdapter(categoryList, position -> {
            changeIndex(position);
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setAdapter(categoryAdapter);
    }
    private void initFunction(){
        rvFunction=binding.catalogueRvFunction;
        functionAdapter = new FunctionListAdapter(null, (position, fid) -> {
            //跳转到教程页面
            Intent intent = new Intent(CatalogueActivity.this, FunctionActivity.class);
            intent.putExtra("fid", fid);
            startActivity(intent);
        },R.layout.item_function_card);
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(functionAdapter);
    }
    private void changeIndex(int position){
        categoryAdapter.changeIndex(position);
        functionAdapter.setList(categoryList.get(position).getFunctionList());
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }

}