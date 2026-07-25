package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Category;
import com.blog.blog_app.exceptions.DuplicateEntryException;
import com.blog.blog_app.request_dto.CateogaryDto;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.request_dto.CreateCateogaryDtoRequest;
import com.blog.blog_app.response_dto.CreateCateogaryResponse;
import com.blog.blog_app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CateogaryImpl implements CategoryService {

    private final ModelMapper modelMapper;

    private final CategoryRepo categoryRepo;

    @Autowired
    CateogaryImpl(ModelMapper modelMapper, CategoryRepo categoryRepo) {
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CreateCateogaryResponse createCategory(CreateCateogaryDtoRequest createCateogaryDtoRequest) {

        if(this.categoryRepo.existsByCategoryTitle(createCateogaryDtoRequest.getCategoryTitle()))
        {
            throw new DuplicateEntryException("A category with this title","!!", createCateogaryDtoRequest.getCategoryTitle());
        }

        //mapping into Category class
        //Gett from Request set them into Category Entity
        Category cateogary = this.modelMapper.map(createCateogaryDtoRequest, Category.class);

        //save them to db
        Category savedCateogary = categoryRepo.save(cateogary);

        //return an Response
         return this.modelMapper.map(savedCateogary, CreateCateogaryResponse.class);
    }

    @Override
    public CreateCateogaryResponse updateCategory(CreateCateogaryDtoRequest createCateogaryDtoRequest, Integer categoryId) {

        //first we do verification that is there an category with this id
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", categoryId));

        category.setCategoryTitle(createCateogaryDtoRequest.getCategoryTitle());
        category.setCategoryDescription(createCateogaryDtoRequest.getCategoryDescription());

        Category save = this.categoryRepo.save(category);

        //then after we generate a response
        return this.modelMapper.map(save, CreateCateogaryResponse.class);
    }

    @Override
    public CreateCateogaryResponse CategoryGetByID(Integer id) {

        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        return this.modelMapper.map(category, CreateCateogaryResponse.class);
    }

    @Override
    public List<CreateCateogaryResponse> getAllCategories() {

        List<Category> listData = this.categoryRepo.findAll();

        return listData.stream().map(cateogary -> this.modelMapper.map(cateogary, CreateCateogaryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        Category cateogary = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepo.delete(cateogary);
    }
}
