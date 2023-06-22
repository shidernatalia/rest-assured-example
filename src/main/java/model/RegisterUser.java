package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
}
