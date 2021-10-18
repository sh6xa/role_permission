package uz.isaev.approlepermission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.isaev.approlepermission.entity.Comment;
import uz.isaev.approlepermission.entity.Post;
import uz.isaev.approlepermission.entity.User;
import uz.isaev.approlepermission.payload.CommentDto;
import uz.isaev.approlepermission.repository.CommentRepository;
import uz.isaev.approlepermission.repository.PostRepository;
import uz.isaev.approlepermission.response.ApiResponse;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public ApiResponse add(CommentDto commentDto){
        Optional<Post> postOptional = postRepository.findById(commentDto.getPostId());
        if (!postOptional.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }
        Comment comment = new Comment();
        comment.setText(comment.getText());
        comment.setPost(postOptional.get());
        commentRepository.save(comment);
        return new ApiResponse("Comment added!", true);
    }

    public ApiResponse edit(CommentDto commentDto, Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (!optionalComment.isPresent())
            return new ApiResponse("Comment not found!", false);

        if (!optionalComment.get().getCreatedBy().getUsername().equals(user.getUsername()))
            return new ApiResponse("Siz faqat o'zingizga tegishli commentlarni o'zgartira olasiz!", false);

        Optional<Post> postOptional = postRepository.findById(commentDto.getPostId());
        if (!postOptional.isPresent()) {
            return new ApiResponse("Post not found!", false);
        }
        Comment comment = optionalComment.get();
        comment.setText(comment.getText());
        comment.setPost(postOptional.get());
        commentRepository.save(comment);
        return new ApiResponse("Comment added!", true);
    }

    public ApiResponse deleteMyComment(Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (!optionalComment.isPresent())
            return new ApiResponse("Comment not found!", false);

        if (!optionalComment.get().getCreatedBy().getUsername().equals(user.getUsername()))
            return new ApiResponse("Siz faqat o'zingizga tegishli commentlarni o'zgartira olasiz!", false);

        commentRepository.deleteById(id);
        return new ApiResponse("Commentingiz o'chirildi!", true);
    }

    public ApiResponse delete(Long id){

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (!optionalComment.isPresent())
            return new ApiResponse("Comment not found!", false);

        commentRepository.deleteById(id);
        return new ApiResponse("Commentingiz o'chirildi!", true);
    }

    public ApiResponse getById(Long id){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.map(post -> new ApiResponse("Comment deleted!", true, post)).orElseGet(() -> new ApiResponse("Comment not found!", false));
    }

    public ApiResponse getAll(){
        return new ApiResponse("Comment List!", true,commentRepository.findAll());
    }
}
