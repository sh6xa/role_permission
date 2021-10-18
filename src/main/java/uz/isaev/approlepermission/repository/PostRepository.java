package uz.isaev.approlepermission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isaev.approlepermission.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
