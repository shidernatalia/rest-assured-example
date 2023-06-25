package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String first_name;
    @Getter
    @Setter
    private String last_name;
    @Getter
    @Setter
    private String avatar;
}
