package com.blog.blog_app.controllers;

import com.blog.blog_app.request_dto.CateogaryDto;
import com.blog.blog_app.payloads.ApiResponse;
import com.blog.blog_app.services.CateogaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cateogary")
public class CateogaryController {


    private final CateogaryService cateogaryService;

    @Autowired
    public CateogaryController(CateogaryService cateogaryService) {
        this.cateogaryService = cateogaryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CateogaryDto> creatingCateogaries(@Valid @RequestBody CateogaryDto cateogaryDto) {
        CateogaryDto cateogaryDto1 = cateogaryService.createCateogary(cateogaryDto);
        return new ResponseEntity<>(cateogaryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CateogaryDto> updatingCateogaries(@Valid @RequestBody CateogaryDto cateogaryDto, @PathVariable Integer id) {
        CateogaryDto cateogaryDto1 = cateogaryService.updateCateogary(cateogaryDto, id);
        return new ResponseEntity<>(cateogaryDto1, HttpStatus.OK);
    }

    @GetMapping("/getByCateogaryId/{id}")
    public ResponseEntity<CateogaryDto> gettingCateogaryById(@Valid @PathVariable Integer id) {
        CateogaryDto cateogaryDto = cateogaryService.CateogaryGetByID(id);
        return new ResponseEntity<>(cateogaryDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CateogaryDto>> gettAllCateogaries() {
        List<CateogaryDto> cateogaryDtoList = this.cateogaryService.getAllCateogaries();
        return new ResponseEntity<>(cateogaryDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCateogaries/{id}")
    public ResponseEntity<ApiResponse> deletingCateogaries(@Valid @PathVariable Integer id) {
        this.cateogaryService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Cateogary deleted succesfully", true), HttpStatus.OK);
    }

}
