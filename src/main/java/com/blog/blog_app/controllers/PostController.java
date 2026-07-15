package com.blog.blog_app.controllers;

import com.blog.blog_app.payloads.AppConstants;
import com.blog.blog_app.request_dto.CreatingPostDto;
import com.blog.blog_app.request_dto.PostDto;
import com.blog.blog_app.payloads.ApiResponse;
import com.blog.blog_app.response_dto.CreatedPostResponse;
import com.blog.blog_app.response_dto.PostCateogaryResponse;
import com.blog.blog_app.response_dto.PostResponse;
import com.blog.blog_app.response_dto.PostResponseByUerId;
import com.blog.blog_app.services.FileServieForThisApplication;
import com.blog.blog_app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {


    private final PostService postService;


    private final FileServieForThisApplication fileServieForThisApplication;


    @Autowired
    PostController(PostService postService, FileServieForThisApplication fileServieForThisApplication) {
        this.postService = postService;
        this.fileServieForThisApplication = fileServieForThisApplication;
    }


    @Value("${project.ThisImageApp}")
    private String path;

    @PostMapping("/create/user/{userId}/cateogary/{cateogaryId}")
    public ResponseEntity<CreatedPostResponse> creatingPostWithController(@RequestBody CreatingPostDto creatingPostDto, @PathVariable Integer userId, @PathVariable Integer cateogaryId) {
        CreatedPostResponse createdPostResponse = this.postService.createPost(creatingPostDto, userId, cateogaryId);
        return new ResponseEntity<>(createdPostResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer Id) {
        PostDto postDto1 = this.postService.updatePost(postDto, Id);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostByIDs(@PathVariable Integer id) {
        PostDto postDto = this.postService.getPostByID(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/getAllpost/")
    public ResponseEntity<List<PostDto>> getAllPost() {
        List<PostDto> allPost = this.postService.getAllPost();
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deletPost(@PathVariable Integer id) {
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted Succesfully", true), HttpStatus.OK);
    }

    @GetMapping("/post/byUserId/{id}")
    public ResponseEntity<PostResponseByUerId> getAllPostByUserId(@PathVariable Integer id
            , @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber   //particular user in which page number
            , @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize      //pageSize means how many post i want in one page
            , @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy
            , @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponseByUerId postResponseByUerId = this.postService.getPostByUserId(id, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(postResponseByUerId, HttpStatus.OK);
    }

    @GetMapping("/post/byCateogary/{id}")
    public ResponseEntity<PostCateogaryResponse> getPostByCateogaryId(@PathVariable Integer id

            , @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,   //particular user in which page number
                                                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize      //pageSize means how many post i want in one page
            , @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy
            , @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostCateogaryResponse postCateogaryResponse = this.postService.getPostByCateogaryId(id, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postCateogaryResponse, HttpStatus.OK);
    }

    //http://localhost:8080/posts?pageNumber=0&pageSize=10   here we took @RequestParam because here we are doing programm on query parameters and like what the value after ? and pathvarible is for specific data we were passing and getting
    @GetMapping("/getAllPostInPagination")
    // http://localhost:8080/getAllPostInPagination?pageNumber=0&pageSize=10     i hit this api for pagenumber or page size for all post without page number and page size i hit api till before ?
    public ResponseEntity<PostResponse> gettingAllPostIntPaginationandSortingFortmate(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,   //particular user in which page number
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,      //pageSize means how many post i want in one page
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy
            , @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

    ) {

        PostResponse allPostInPaginationAndSorting = this.postService.getAllPostInPaginationAndSorting(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allPostInPaginationAndSorting, HttpStatus.OK);
    }


    @GetMapping("/post/search/{keyWords}")
    public ResponseEntity<List<PostDto>> getTingPostByTitle(@PathVariable String keyWords) {
        List<PostDto> postDtos = this.postService.searchPost(keyWords);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }


    //post image upload
    @PostMapping("/image/{postId}")

    public ResponseEntity<PostDto> updloadImage(
            @RequestParam("imagename") MultipartFile imagename,
            @PathVariable Integer postId

    ) {
        System.out.println(imagename.getOriginalFilename());
        System.out.println(imagename.getSize());
        System.out.println(imagename.isEmpty());

        PostDto postDto = this.postService.getPostByID(postId);

        String fileName = this.fileServieForThisApplication.creatingImage(path, imagename);
        System.out.println(fileName);
        postDto.setImageName(fileName);


        PostDto updatedPost = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.CREATED);

    }

    @GetMapping("/image/{imagename}")
    public void downloadImage(
            @PathVariable String imagename,
            HttpServletResponse httpServletResponse
    ) throws IOException {

        String FullPath = path + File.separator + imagename;
        System.out.println(FullPath);
        InputStream inputStream = this.fileServieForThisApplication.generatingFile(path, imagename);

        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());

    }
}
