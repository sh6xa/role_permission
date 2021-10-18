package uz.isaev.approlepermission.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.isaev.approlepermission.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
