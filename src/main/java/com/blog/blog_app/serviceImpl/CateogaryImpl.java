package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.request_dto.CateogaryDto;
import com.blog.blog_app.entity.Cateogary;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.services.CateogaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CateogaryImpl implements CateogaryService {

    private final ModelMapper modelMapper;

    private final CategoryRepo categoryRepo;

    @Autowired
    CateogaryImpl(ModelMapper modelMapper, CategoryRepo categoryRepo) {
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CateogaryDto createCateogary(CateogaryDto cateogaryDto) {

        Cateogary cateogary = this.modelMapper.map(cateogaryDto, Cateogary.class);
        Cateogary savedCateogary = categoryRepo.save(cateogary);

        return this.modelMapper.map(savedCateogary, CateogaryDto.class);
    }

    @Override
    public CateogaryDto updateCateogary(CateogaryDto cateogaryDto, Integer cateogaryId) {
        Cateogary cateogaryD = this.categoryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("cateogary", "Id", cateogaryId));
        cateogaryD.setCateogary_title(cateogaryDto.getCateogary_title());
        cateogaryD.setCateogary_Description(cateogaryDto.getCateogary_Description());
        return this.modelMapper.map(cateogaryD, CateogaryDto.class);
    }

    @Override
    public CateogaryDto CateogaryGetByID(Integer id) {

        Cateogary cateogary = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("cateogary", "id", id));
        return this.modelMapper.map(cateogary, CateogaryDto.class);
    }

    @Override
    public List<CateogaryDto> getAllCateogaries() {

        List<Cateogary> listData = this.categoryRepo.findAll();

        return listData.stream().map(cateogary -> this.modelMapper.map(cateogary, CateogaryDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        Cateogary cateogary = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cateogary", "id", id));
        categoryRepo.delete(cateogary);
    }
}
