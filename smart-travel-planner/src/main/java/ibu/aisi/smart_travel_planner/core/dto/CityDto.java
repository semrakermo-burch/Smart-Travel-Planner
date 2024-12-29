package ibu.aisi.smart_travel_planner.core.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CityDto {
    private Long id;
    private String name;
    private String country;
}
