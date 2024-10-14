package co.edu.unihumboldt.parking.mapping.dtos;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import co.edu.unihumboldt.parking.domain.entities.Fee;
import co.edu.unihumboldt.parking.domain.entities.PayMethod;
import co.edu.unihumboldt.parking.domain.entities.Spot;
import lombok.Builder;

import java.util.Date;

@Builder
public record BookDto( int id,
         Date bookDate,
         Date payDate,
         Date startDate,
         Date endDate,
         BookStatus bookStatus,
         PayMethod payMethod,
         Spot spot,
         Fee fee) {
}
