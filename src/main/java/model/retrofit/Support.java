package model.retrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Support {
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String text;
}
