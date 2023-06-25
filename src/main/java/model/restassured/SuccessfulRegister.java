package model.restassured;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulRegister {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String token;
}
