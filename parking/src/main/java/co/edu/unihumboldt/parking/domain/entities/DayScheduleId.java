package co.edu.unihumboldt.parking.domain.entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DayScheduleId implements Serializable {
    private Integer scheduleId;
    private Integer dayWeekId;
    private Integer carParkId;
}