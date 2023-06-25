package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SingleUserResponse {
    @Getter
    @Setter
    private UserResponse data;
    @Getter
    @Setter
    private Support support;
}
