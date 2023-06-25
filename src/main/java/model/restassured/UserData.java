package model.restassured;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class UserData {
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
