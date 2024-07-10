package edu.raddan.entity;

import edu.raddan.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "currencies")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends Entity {

    private Long id;
    private String code;
    private String fullName;
    private String sign;

}
