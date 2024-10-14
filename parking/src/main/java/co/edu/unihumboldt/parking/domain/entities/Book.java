package co.edu.unihumboldt.parking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books",schema = "public", indexes = @Index(columnList = "id"))
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date bookDate;
    private Date payDate;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private BookStatus bookStatus;
    @ManyToOne
    private PayMethod payMethod;
    @ManyToOne
    private Spot spot;
    @ManyToOne
    private Fee fee;
}
