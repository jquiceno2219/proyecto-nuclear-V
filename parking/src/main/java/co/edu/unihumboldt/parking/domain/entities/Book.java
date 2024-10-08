package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books",schema = "project", indexes = @Index(columnList = "id"))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date BookDate;
    private Date PayDate;
    private Date StartDate;
    private Date EndDate;
    @ManyToOne
    private BookStatus bookStatus;
    @ManyToOne
    private PayMethod payMethod;
    @ManyToOne
    private Spot spot;
    @ManyToOne
    private Fee fee;
}
