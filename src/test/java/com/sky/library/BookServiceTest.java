package com.sky.library;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    BookService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new BookServiceImpl(new BookRepositoryStub());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void retrieveBookInvalidText() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            classUnderTest.retrieveBook("INVALID-TEXT");
        });

        assertTrue(exception.getMessage().contains("Reference must begin with 'BOOK-'"));
    }

    @Test
    void retrieveBookNotFound() {
        assertThrows(BookNotFoundException.class, () -> {
            classUnderTest.retrieveBook("BOOK-999");
        });
    }

    @Test
    void retrieveBookValidText() throws Exception{
        Book book = classUnderTest.retrieveBook("BOOK-GRUFF472");

        assertNotNull(book);
        assertEquals("The Gruffalo", book.getTitle());
    }

    @Test
    void getBookSummaryInvalidText() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            classUnderTest.getBookSummary("INVALID-TEXT");
        });

        assertTrue(exception.getMessage().contains("Reference must begin with 'BOOK-'"));
    }

    @Test
    void getBookSummaryNotFound() {
        assertThrows(BookNotFoundException.class, () -> {
            classUnderTest.getBookSummary("BOOK-999");
        });
    }

    @Test
    void getBookSummaryShort() throws Exception{
        String summary = classUnderTest.getBookSummary("BOOK-GRUFF472");

        assertEquals("[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.", summary);
    }

    @Test
    void getBookSummaryMedium()  throws Exception{
        String summary = classUnderTest.getBookSummary("BOOK-POOH222");

        assertEquals("[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends...", summary);
    }

    @Test
    void getBookSummaryLong() throws Exception{

        String summary = classUnderTest.getBookSummary("BOOK-WILL987");

        assertEquals("[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside...", summary);
    }

    @Test
    void getBookSummaryNineWords()  throws Exception{

        //Given
        Book book = mock(Book.class);
        when(book.getReference()).thenReturn("Ref");
        when(book.getTitle()).thenReturn("Title");
        when(book.getReview()).thenReturn("One Two Three Four Five Six Seven Eight Nine.");
        BookRepository repo = mock(BookRepository.class);
        when(repo.retrieveBook(anyString())).thenReturn(book);

        BookServiceImpl mockedService = new BookServiceImpl(repo);

        //When
        String summary = mockedService.getBookSummary("BOOK-ANYTHINGWILLDO");

        //Then
        assertEquals("[Ref] Title - One Two Three Four Five Six Seven Eight Nine.", summary);
    }

    @Test
    void getBookSummaryTenWords()  throws Exception{

        //Given
        Book book = mock(Book.class);
        when(book.getReference()).thenReturn("Ref");
        when(book.getTitle()).thenReturn("Title");
        when(book.getReview()).thenReturn("One Two Three Four Five Six Seven Eight Nine Ten.");
        BookRepository repo = mock(BookRepository.class);
        when(repo.retrieveBook(anyString())).thenReturn(book);

        BookServiceImpl mockedService = new BookServiceImpl(repo);

        //When
        String summary = mockedService.getBookSummary("BOOK-ANYTHINGWILLDO");

        //Then
        assertEquals("[Ref] Title - One Two Three Four Five Six Seven Eight Nine...", summary);
    }
}