package com.sky.library;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookService classUnderTest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        classUnderTest = new BookServiceImpl(new BookRepositoryStub());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void retrieveBookInvalidText() throws Exception{
        classUnderTest.retrieveBook("INVALID-TEXT");
    }

    @org.junit.jupiter.api.Test
    void retrieveBookNotFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> {
            classUnderTest.retrieveBook("BOOK-999");
        });
    }

    @org.junit.jupiter.api.Test
    void retrieveBookValidText() throws Exception{
        Book book = classUnderTest.retrieveBook("BOOK-GRUFF472");

        Assertions.assertNotNull(book);
        Assertions.assertEquals("The Gruffalo", book.getTitle());
    }

    @org.junit.jupiter.api.Test
    void getBookSummary() {
        fail();
    }
}