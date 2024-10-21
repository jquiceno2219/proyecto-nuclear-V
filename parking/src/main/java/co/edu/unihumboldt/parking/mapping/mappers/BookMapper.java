package co.edu.unihumboldt.parking.mapping.mappers;


import co.edu.unihumboldt.parking.domain.entities.Book;
import co.edu.unihumboldt.parking.mapping.dtos.BookDto;

import java.util.List;

public class BookMapper {
    public static BookDto mapFrom(Book source){
        return BookDto.builder().id(source.getId())
                .bookDate(source.getBookDate())
                .payDate(source.getPayDate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .payMethod(source.getPayMethod())
                .spot(source.getSpot())
                .fee(source.getFee())
                .build();
    }

    public static Book mapFrom(BookDto source){
        return new Book(source.getId(),
                source.getBookDate(),
                source.getPayDate(),
                source.getStartDate(),
                source.getEndDate(),
                source.getBookStatus(),
                source.getPayMethod(),
                source.getSpot(),
                source.getFee());
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
