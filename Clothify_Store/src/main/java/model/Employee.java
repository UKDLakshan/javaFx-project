package model;

import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private String id;
    private String name;
    private String email;
    private String company;
}
