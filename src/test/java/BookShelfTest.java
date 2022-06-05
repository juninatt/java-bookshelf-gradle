

import bookstore.Book;
import bookstore.BookShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("the bookshelf")
class BookShelfTest {

    private BookShelf bookShelf;
    private Book springInAction;
    private Book effectiveJava;
    private Book halfAWar;

    @BeforeEach
    void init() throws Exception {
        bookShelf = new BookShelf();
        springInAction = new Book("Spring in Action, 6th Edition", "Craig Walls", LocalDate.of(2022, Month.JANUARY, 1));
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        halfAWar = new Book("Half a War", "Joe Abercrombie", LocalDate.of(2016, Month.MARCH, 8));
    }

    /**
     * @param testInfo Contains information about the current test
     */
    @Test
    @DisplayName("is empty when no books added to it")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        List<Book> books = bookShelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should have two books when two books added to it")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        bookShelf.add(effectiveJava, halfAWar);
        List<Book> books = bookShelf.getBooks();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    @DisplayName("should be empty when add method empty")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        bookShelf.add();
        List<Book> books = bookShelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should throw exception if book is added to existing book list")
    void booksReturnedFromBookShelfIsImmutableForClient() {
        bookShelf.add(halfAWar, effectiveJava);
        List<Book> books = bookShelf.getBooks();
        try {
            books.add(springInAction);
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }
    }

    @Test
    @DisplayName("should arrange books lexicographically by title")
    void bookShelfArrangedByBookTitle() {
        bookShelf.add(effectiveJava, springInAction, halfAWar);
        List<Book> books = bookShelf.arrangeByTitle();
        assertEquals(asList(effectiveJava, halfAWar, springInAction), books,
                () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    @DisplayName("books should be in insertion order")
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        bookShelf.add(halfAWar, effectiveJava, springInAction);
        bookShelf.arrangeByTitle();
        List<Book> books = bookShelf.getBooks();
        assertEquals(asList(halfAWar, effectiveJava, springInAction), books,
                () -> "Books in bookshelf are in insertion order");
    }

    @Test
    @DisplayName("books should be in descending order")
    void bookshelfArrangedByUserProvidedCriteria() {
        bookShelf.add(effectiveJava, halfAWar, springInAction);
        List<Book> books = bookShelf.arrangeByCriteria(Comparator.<Book>naturalOrder().reversed());
        assertEquals(asList(springInAction, halfAWar, effectiveJava), books,
                () -> "Books in a bookshelf are arranged in descending order of book title");
    }
}