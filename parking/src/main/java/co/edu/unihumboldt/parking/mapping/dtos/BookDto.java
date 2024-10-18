package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.domain.entities.Spot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * Clase {@code BookDto} que representa un objeto de transferencia de datos para una reserva.
 * Contiene atributos que describen la reserva, incluyendo su identificador, fechas de reserva,
 * pago, inicio y fin, así como referencias a su estado, método de pago, espacio reservado
 * y tarifa asociada.
 * Utiliza Lombok para generar constructores, métodos de acceso y un constructor de estilo "builder".
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int id;
    private Date bookDate;
    private Date payDate;
    private Date startDate;
    private Date endDate;
    private BookStatus bookStatus;
    private PayMethod payMethod;
    private Spot spot;
    private Fee fee;
}
