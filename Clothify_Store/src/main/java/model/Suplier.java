package model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Suplier {
    private String sup_ID;
    private String name;
    private String email;
    private String company;
    private String item_ID;
}
