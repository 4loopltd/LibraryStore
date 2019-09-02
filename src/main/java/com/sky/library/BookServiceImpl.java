package com.sky.library;

/**
 * @author <a href="mailto:email@phil-milne.co.uk">Phil Milne</a>
 */
public class BookServiceImpl implements BookService {

    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private static final int MAX_WORDS = 9;
    private static final String ELLIPSIS = "...";

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    public Book retrieveBook(String bookReference) throws BookNotFoundException {

        if(!bookReference.startsWith(BOOK_REFERENCE_PREFIX)){
            throw new IllegalArgumentException("Reference must begin with 'BOOK-' : " + bookReference);
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

        //Summary format '[Reference] Title - Review'
        StringBuilder summary = new StringBuilder();
        summary.append("[").append(book.getReference()).append("] ");
        summary.append(book.getTitle()).append(" - ");

        // Max nine words
        StringBuilder reviewTrunc = new StringBuilder();
        String[] words = book.getReview().split("\\s+");
        int i=0;
        for(; i<words.length && i <MAX_WORDS; i++){
            if(i!=0){
                reviewTrunc.append(" ");
            }
            reviewTrunc.append(words[i]);
        }

        if (i < words.length){
            //Truncate and remove trailing non-alphanumeric chars
            if(!reviewTrunc.substring(reviewTrunc.length()-1).matches("[a-zA-Z0-9]")){
                reviewTrunc.deleteCharAt(reviewTrunc.length()-1);
            }
            reviewTrunc.append(ELLIPSIS);
        }

        summary.append(reviewTrunc);

        return summary.toString();
    }
}
