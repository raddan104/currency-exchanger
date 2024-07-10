package edu.raddan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "name")
    private String fullName;
    private String sign;

    public Currency(String code, String name, String sign) {
        this.code = code;
        this.fullName = name;
        this.sign = sign;
    }
}
