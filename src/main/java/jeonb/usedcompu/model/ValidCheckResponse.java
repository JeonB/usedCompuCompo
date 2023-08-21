package jeonb.usedcompu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidCheckResponse {

    private Boolean status;
    private String field;
    private String message;
}
