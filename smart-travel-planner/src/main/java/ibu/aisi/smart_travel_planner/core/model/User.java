package ibu.aisi.smart_travel_planner.core.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}

