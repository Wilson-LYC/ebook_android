package com.ebook.app.view.catalogue;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.config.DataMock;
import com.ebook.app.databinding.PageCatalogueBinding;
import com.ebook.app.model.Function;
import com.ebook.app.model.FunctionCategory;

import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity {
    private PageCatalogueBinding binding;
    private RecyclerView rvCategory,rvFunction;
    private CatalogueCategoryAdapter categoryAdapter;
    private CatalogueFunctionAdapter functionAdapter;
    private List<FunctionCategory> categoryList;
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
        //绑定控件
        rvCategory=binding.catalogueRvCategory;
        rvFunction=binding.catalogueRvFunction;
        //配置分类列表
        categoryList = DataMock.catalogue;
        categoryAdapter = new CatalogueCategoryAdapter(categoryList, position -> {
            changeIndex(position);
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setAdapter(categoryAdapter);
        //配置函数列表
        functionAdapter = new CatalogueFunctionAdapter(categoryList.get(0).getFunctionList());
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(functionAdapter);
    }

    private void changeIndex(int position){
        categoryAdapter.changeIndex(position);
        functionAdapter.setList(categoryList.get(position).getFunctionList());
    }

}