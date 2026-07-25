package com.blog.blog_app.services;

import com.blog.blog_app.request_dto.CateogaryDto;
import com.blog.blog_app.request_dto.CreateCateogaryDtoRequest;
import com.blog.blog_app.response_dto.CreateCateogaryResponse;

import java.util.List;

public interface CategoryService {
    CreateCateogaryResponse createCategory(CreateCateogaryDtoRequest createCateogaryDtoRequest);

    CreateCateogaryResponse updateCategory(CreateCateogaryDtoRequest categoryDto, Integer categoryId);

    CreateCateogaryResponse CategoryGetByID(Integer id);

    List<CreateCateogaryResponse> getAllCategories();

    void deleteById(Integer id);
}
