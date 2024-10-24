package model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private String emp_ID;
    private String email;
    private String password;
}
