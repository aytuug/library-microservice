package com.aakin.libraryservice.service;

import com.aakin.libraryservice.client.BookServiceClient;
import com.aakin.libraryservice.dto.AddBookRequest;
import com.aakin.libraryservice.dto.LibraryDto;
import com.aakin.libraryservice.exception.BookNotFoundException;
import com.aakin.libraryservice.exception.LibraryNotFoundException;
import com.aakin.libraryservice.model.Library;
import com.aakin.libraryservice.repository.LibraryRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;


    public LibraryService(LibraryRepository libraryRepository, BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }


    public LibraryDto getAllBooksInLibraryById(String id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book).getBody())
                        .collect(Collectors.toList()));
        return libraryDto;
    }
    public LibraryDto createLibrary() {
        Library newLibrary = libraryRepository.save(new Library());
        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest addBookRequest){
        String bookId = bookServiceClient.getBookByIsbn(addBookRequest.getIsbn()).getBody().getBookId();
        Library library = libraryRepository.findById(addBookRequest.getId())
                .orElseThrow(() -> new BookNotFoundException("Kütüphanede id'ye ait kitap bulunamadi: " + addBookRequest.getId()));
        library.getUserBook().add(bookId);
        libraryRepository.save(library);





    }

}
