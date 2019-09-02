package com.sky.library;

public class BookServiceImpl implements BookService {

    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    public Book retrieveBook(String bookReference) throws BookNotFoundException {

        if(!bookReference.startsWith(BOOK_REFERENCE_PREFIX)){
            throw new BookNotFoundException("Reference must begin with 'BOOK-' ");
        }

        Book book = repository.retrieveBook(bookReference);
        if(book == null){
            throw new BookNotFoundException("Book reference not found");
        }
        return book;
    }

    /**
     * Provide a book summary that concatenates the reference, the title, and the first nine words of the review. If the
     * review is longer than nine words put an ellipsis (‘...’) at the end
     * @param bookReference
     * @return summary
     * @throws BookNotFoundException
     */
    public String getBookSummary(String bookReference) throws BookNotFoundException {

        Book book = this.retrieveBook(bookReference);

        StringBuilder summary = new StringBuilder();
        summary.append(book.getReference());
        summary.append(book.getTitle());

        StringBuilder reviewTrunc = new StringBuilder();
        String[] words = book.getReview().split("\\s");
        int i=0;
        for(; i<words.length && i <9; i++){
            reviewTrunc.append(words[i]);
            if (i < words.length-1){
                reviewTrunc.append(" ");
            }
        }
        if (i < words.length-1){
            reviewTrunc.append("...");
        }

        if(book.getReview().length() <= 9) {
            summary.append(book.getReview());
        }
        else{
            summary.append(book.getReview().substring(0,9));
            summary.append("...");
        }

        return summary.toString();
    }
}
