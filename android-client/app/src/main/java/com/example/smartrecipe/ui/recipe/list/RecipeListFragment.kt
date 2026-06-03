package com.example.smartrecipe.ui.recipe.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartrecipe.R
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentRecipeListBinding
import com.example.smartrecipe.ui.common.adapters.RecipeAdapter
import com.example.smartrecipe.ui.nav.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<FragmentRecipeListBinding>() {

    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter // Khai báo Adapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeListBinding
        get() = FragmentRecipeListBinding::inflate

    override fun setupViews() {
        // 1. Khởi tạo Adapter và xử lý khi click vào 1 công thức
        recipeAdapter = RecipeAdapter { selectedRecipe ->
            val bundle = android.os.Bundle().apply {
                putLong("recipeId", selectedRecipe.id)
            }
            findNavController().navigate(R.id.action_recipeList_to_recipeDetail, bundle)
        }

        // 2. Gắn Adapter vào RecyclerView
        binding.recyclerViewRecipes.adapter = recipeAdapter

        // 3. Xử lý nút Thêm mới
        binding.fabAddRecipe.setOnClickListener {
            findNavController().safeNavigate(R.id.action_recipeList_to_recipeEdit)
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipes.collect { recipes ->
                    // 4. Bơm dữ liệu mới vào Adapter mỗi khi Database thay đổi
                    recipeAdapter.submitList(recipes)
                }
            }
        }
    }
}