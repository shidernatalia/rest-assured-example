package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String job;
    @Getter
    @Setter
    public Date updatedAt;
}
