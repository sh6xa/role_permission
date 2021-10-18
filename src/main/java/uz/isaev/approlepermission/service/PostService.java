package uz.isaev.approlepermission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.isaev.approlepermission.entity.Post;
import uz.isaev.approlepermission.entity.User;
import uz.isaev.approlepermission.payload.PostDto;
import uz.isaev.approlepermission.repository.PostRepository;
import uz.isaev.approlepermission.response.ApiResponse;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ApiResponse add(PostDto postDto){
        Post post = new Post(postDto.getTitle(), postDto.getText(), postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Post added!", true);
    }

    public ApiResponse edit(PostDto postDto, Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }

        Post post = optionalPost.get();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!post.getCreatedBy().getUsername().equals(user.getUsername())) {
            return new ApiResponse("The post does not apply to you!", true);
        }
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());

        postRepository.save(post);
        return new ApiResponse("Post edited!", true);
    }

    public ApiResponse delete(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }
        postRepository.deleteById(id);
        return new ApiResponse("Post deleted!", true);
    }

    public ApiResponse getByUrl(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.map(post -> new ApiResponse("Post deleted!", true, post)).orElseGet(() -> new ApiResponse("Post not found!", false));
    }

    public ApiResponse getAll(){
        return new ApiResponse("Post List!", true,postRepository.findAll());
    }
}
