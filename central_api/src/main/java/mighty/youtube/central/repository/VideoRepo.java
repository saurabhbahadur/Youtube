package mighty.youtube.central.repository;


import mighty.youtube.central.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VideoRepo extends JpaRepository<Video , String> {

}
