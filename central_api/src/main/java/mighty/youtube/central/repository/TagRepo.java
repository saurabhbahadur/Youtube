package mighty.youtube.central.repository;

import mighty.youtube.central.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
    public Tag findByName(String tagName);
}
