package model;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String id;
    private String name;
    private String size;
    private Double price;
    private Integer qty;
    private String suplierID;
}
