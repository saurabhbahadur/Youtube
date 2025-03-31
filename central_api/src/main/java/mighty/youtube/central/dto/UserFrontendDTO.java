package mighty.youtube.central.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserFrontendDTO {
    private UUID Id;
    private String name;
    private String email;
    private LocalDate dob;
    private String gender;
    private String Country;
}
