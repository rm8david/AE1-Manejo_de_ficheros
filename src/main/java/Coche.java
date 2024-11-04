import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Coche implements Serializable {
    private static final long serialVersionUID = 12345L;
    private int id;
    private String matricula, marca, modelo, color;

}
