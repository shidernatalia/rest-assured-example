package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResponse {
    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String job;
    @Getter
    @Setter
    public String id;
    @Getter
    @Setter
    public Date createdAt;
}
