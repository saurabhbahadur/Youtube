package mighty.youtube.central.repository;


import mighty.youtube.central.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepo extends JpaRepository<AppUser , UUID> {



}
