package co.edu.unihumboldt.parking.mapping.mappers;

import co.edu.unihumboldt.parking.domain.entities.BookStatus;
import co.edu.unihumboldt.parking.mapping.dtos.BookStatusDto;

import java.util.List;

public class BookStatusMapper {
    public static BookStatusDto mapFrom(BookStatus source) {
        return BookStatusDto.builder()
                .id(source.getId())
                .name(source.getName())
                .status(source.isStatus())
                .build();
    }

    public static BookStatus mapFrom(BookStatusDto source){
        return new BookStatus(source.getId(),
                source.getName(),
                source.isStatus());
    }

    public static List<BookStatus> mapFrom(List<BookStatusDto> source){
        return source.parallelStream()
                .map(BookStatusMapper::mapFrom)
                .toList();

    }
    public static List<BookStatusDto> mapFromDto(List<BookStatus> source){
        return source.parallelStream()
                .map(BookStatusMapper::mapFrom)
                .toList();

    }
}
