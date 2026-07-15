package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.request_dto.CreatingPostDto;
import com.blog.blog_app.request_dto.PostDto;
import com.blog.blog_app.entity.Cateogary;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.response_dto.CreatedPostResponse;
import com.blog.blog_app.response_dto.PostCateogaryResponse;
import com.blog.blog_app.response_dto.PostResponse;
import com.blog.blog_app.response_dto.PostResponseByUerId;
import com.blog.blog_app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {

    private final ModelMapper modelMapper;

    private final PostRepo postRepo;

    private final UserRepo userRepo;

    private final CategoryRepo categoryRepo;

    @Autowired
    PostImpl(ModelMapper modelMapper, PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CreatedPostResponse createPost(CreatingPostDto creatingPostDto, Integer userId, Integer cateogaryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("uderId", "id", userId));

        Cateogary cateogary = this.categoryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("cateogaryId", "id", cateogaryId));

        Post post = this.modelMapper.map(creatingPostDto, Post.class);

        post.setUser(user);
        post.setCateogary(cateogary);
        post.setImageName("default.png");
        /*post.setPostContent(postDto.getPostContent());
        post.setPostTitle(post.getPostTitle());
        post.setAddedDate(post.getAddedDate());
*/
        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost, CreatedPostResponse.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postid) {

        //first validating post id
        Post post = this.postRepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post", "id", postid));


        //now we update post by particular id
        //here also model mapper do automatically but we do manaully for only some field updation
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setAddedDate(postDto.getAddedDate());
        post.setImageName(postDto.getImageName());

        Post save = this.postRepo.save(post);

        return this.modelMapper.map(save, PostDto.class);
    }

    @Override
    public PostDto getPostByID(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {

        List<Post> posts = this.postRepo.findAll();

        for (Post post : posts) {
            System.out.println("Post ID = " + post.getPostId());
            System.out.println("Comment Count = " + post.getPostcommentList().size());
        }

        return posts.stream().map(onebyonePost -> this.modelMapper.map(onebyonePost, PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponseByUerId getPostByUserId(Integer userId, Integer PageNumber, Integer PageSize, String sortBy, String sortDir) {

        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        //validating use id
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId", "id", userId));

        Pageable pageable = PageRequest.of(PageNumber, PageSize, sort);

        Page<Post> allPage = this.postRepo.findByUser(user, pageable);


        List<Post> allPost = allPage.getContent();

        List<PostDto> postDto = allPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

//mapStruct 
        PostResponseByUerId postResponseByUerId = new PostResponseByUerId();
        postResponseByUerId.setAllPost(postDto);
        postResponseByUerId.setUserID(userId);
        postResponseByUerId.setPageNumber(allPage.getNumber());
        postResponseByUerId.setPageSize(allPage.getSize());
        postResponseByUerId.setTotalElement((int) allPage.getTotalElements());
        postResponseByUerId.setLastPage(allPage.isLast());
        postResponseByUerId.setTotalPages(allPage.getTotalPages());


        return postResponseByUerId;
    }

    @Override
    public PostCateogaryResponse getPostByCateogaryId(Integer cateogaryId, Integer PageNumber, Integer PageSize, String sortBy, String sortDir) {


        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Cateogary cateogaryID = this.categoryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("cateogaryId", "id", cateogaryId));

        Pageable pageable = PageRequest.of(PageNumber, PageSize, sort);


        Page<Post> allPage = this.postRepo.findByCateogary(cateogaryID, pageable);

        List<Post> allPost = allPage.getContent();

        List<PostDto> postDto = allPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostCateogaryResponse postCateogaryResponse = new PostCateogaryResponse();

        postCateogaryResponse.setAllPost(postDto);
        postCateogaryResponse.setCateogaryId(cateogaryId);
        postCateogaryResponse.setPageNumber(allPage.getNumber());
        postCateogaryResponse.setPageSize(allPage.getSize());
        postCateogaryResponse.setTotalElement((int) allPage.getTotalElements());
        postCateogaryResponse.setLastPage(allPage.isLast());
        postCateogaryResponse.setTotalPages(allPage.getTotalPages());


        return postCateogaryResponse;
    }

    @Override
    public List<PostDto> searchPost(String KeyWord) {

        List<Post> posts = this.postRepo.findByPostTitleContaining(KeyWord);
        return posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }


    @Override
    public PostResponse getAllPostInPaginationAndSorting(Integer PageNumber, Integer PageSize, String sortBy, String sortDir) {

        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        //i found the object of pageble by passing pagenumber and pagesize
        //Pageable pageable = PageRequest.of(PageNumber, PageSize);
        //here i took of method of pageRequest which also took sortBy field also

        Pageable pageable = PageRequest.of(PageNumber, PageSize, sort);
        //then i found whole page where there has many post
        Page<Post> allPage = this.postRepo.findAll(pageable);

        //then i found content which i want from page ...is all post
        List<Post> allPosts = allPage.getContent();


        List<PostDto> allPostDto = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        //now we get all post data i dont return that particular data i make my own resonse class and return those data which i want

        PostResponse postResponse = new PostResponse();
        postResponse.setAllPost(allPostDto);
        postResponse.setPageNumber(allPage.getNumber());
        postResponse.setPageSize(allPage.getSize());
        postResponse.setTotalElement((int) allPage.getTotalElements());
        postResponse.setLastPage(allPage.isLast());
        postResponse.setTotalPages(allPage.getTotalPages());

        //return the Response that client want to see


        return postResponse;

    }


}
