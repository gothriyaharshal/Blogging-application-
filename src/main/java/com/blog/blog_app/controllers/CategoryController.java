package com.blog.blog_app.controllers;

import com.blog.blog_app.payloads.ApiResponse;
import com.blog.blog_app.request_dto.CreateCateogaryDtoRequest;
import com.blog.blog_app.response_dto.CreateCateogaryResponse;
import com.blog.blog_app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cateogary")
public class CategoryController {


    private final CategoryService cateogaryService;

    @Autowired
    public CategoryController(CategoryService cateogaryService) {
        this.cateogaryService = cateogaryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CreateCateogaryResponse> creatingCateogaries(@Valid @RequestBody CreateCateogaryDtoRequest createCateogaryDtoRequest) {

        CreateCateogaryResponse response = cateogaryService.createCategory(createCateogaryDtoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CreateCateogaryResponse> updatingCateogaries(@Valid @RequestBody CreateCateogaryDtoRequest createCateogaryDtoRequest, @PathVariable Integer id) {
        CreateCateogaryResponse response = cateogaryService.updateCategory(createCateogaryDtoRequest, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/getByCateogaryId/{id}")
    public ResponseEntity<CreateCateogaryResponse> gettingCateogaryById(@Valid @PathVariable Integer id) {
        CreateCateogaryResponse response = cateogaryService.CategoryGetByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreateCateogaryResponse>> gettAllCateogaries() {
        List<CreateCateogaryResponse> allCategories = this.cateogaryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCateogaries/{id}")
    public ResponseEntity<ApiResponse> deletingCateogaries(@Valid @PathVariable Integer id) {
        this.cateogaryService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Category deleted succesfully", true), HttpStatus.OK);
    }

}
