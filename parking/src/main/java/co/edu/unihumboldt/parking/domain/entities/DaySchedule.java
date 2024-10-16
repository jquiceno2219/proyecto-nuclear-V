package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "days_schedule", schema = "public")
public class DaySchedule implements Serializable {

    @EmbeddedId
    private DayScheduleId id;

    @MapsId("scheduleId")
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @MapsId("dayWeekId")
    @ManyToOne
    @JoinColumn(name = "day_week_id")
    private DayWeek dayWeek;

    @MapsId("carParkId")
    @ManyToOne
    @JoinColumn(name = "car_park_id")
    private CarPark carPark;
}
