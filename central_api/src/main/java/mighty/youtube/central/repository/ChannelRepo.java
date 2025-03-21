package mighty.youtube.central.repository;


import mighty.youtube.central.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelRepo extends JpaRepository<Channel , UUID> {
}
