package co.edu.unihumboldt.parking.mapping.mappers;


import co.edu.unihumboldt.parking.domain.entities.Book;
import co.edu.unihumboldt.parking.mapping.dtos.BookDto;

import java.util.List;

public class BookMapper {
    public static BookDto mapFrom(Book source){
        return new BookDto(source.getId(),
                source.getBookDate(),
                source.getPayDate(),
                source.getStartDate(),
                source.getEndDate(),
                source.getBookStatus(),
                source.getPayMethod(),
                source.getSpot(),
                source.getFee());
    }

    public static Book mapFrom(BookDto source){
        return new Book(source.id(),
                source.bookDate(),
                source.payDate(),
                source.startDate(),
                source.endDate(),
                source.bookStatus(),
                source.payMethod(),
                source.spot(),
                source.fee());
    }

    public static List<Book> mapFrom(List<BookDto> source){
        return source.parallelStream()
                .map(BookMapper::mapFrom)
                .toList();

    }
    public static List<BookDto> mapFromDto(List<Book> source){
        return source.parallelStream()
                .map(BookMapper::mapFrom)
                .toList();

    }
}
