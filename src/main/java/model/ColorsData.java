package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* GET List<Resource>
* Request
* /api/unknown
* Response
* 200
* */
@AllArgsConstructor
@NoArgsConstructor
public class ColorsData {
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Integer year;
    @Getter @Setter
    private String color;
    @Getter @Setter
    private String pantone_value;
}
