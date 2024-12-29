package ibu.aisi.smart_travel_planner.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status; // "Upcoming" or "Completed"
    private Long userId;   // User ID for the trip owner
    private Long cityId;   // City ID for the trip destination
}
