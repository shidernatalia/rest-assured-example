package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public class ListUsersRootResponse {
    @Getter
    @Setter
    private Integer page;
    @Getter
    @Setter
    private Integer per_page;
    @Getter
    @Setter
    private Integer total;
    @Getter
    @Setter
    private Integer total_pages;
    @Getter
    @Setter
    private ArrayList<UserResponse> data;
    @Getter
    @Setter
    private Support support;
}
