package com.aakin.libraryservice.client;

import com.aakin.libraryservice.dto.BookDto;
import com.aakin.libraryservice.dto.BookIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", path = "/v1/book")
public interface BookServiceClient {
    @GetMapping("/isbn/{isbn}")
    ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable(value = "isbn") String isbn);

    @GetMapping("/book/{bookId}")
    ResponseEntity<BookDto> getBookById(@PathVariable(value = "bookId") String bookId);



}
