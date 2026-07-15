package com.blog.blog_app.services;

import com.blog.blog_app.request_dto.CateogaryDto;

import java.util.List;

public interface CateogaryService {
    CateogaryDto createCateogary(CateogaryDto cateogaryDto);

    CateogaryDto updateCateogary(CateogaryDto cateogaryDto, Integer cateogaryId);

    CateogaryDto CateogaryGetByID(Integer id);

    List<CateogaryDto> getAllCateogaries();

    void deleteById(Integer id);


}
