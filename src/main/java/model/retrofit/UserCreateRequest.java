package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String job;
}
