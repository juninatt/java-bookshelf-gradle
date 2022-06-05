import bookstore.Book;
import bookstore.BookShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

// TODO: Improve exception tests

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
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("should have two books when two books added to it")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        bookShelf.add(effectiveJava, halfAWar);
        List<Book> books = bookShelf.getBooks();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("should be empty when add method empty")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        bookShelf.add();
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEmpty();
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
        List<Book> books = bookShelf.arrange();
        assertThat(books).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("books should be in insertion order")
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        bookShelf.add(halfAWar, effectiveJava, springInAction);
        bookShelf.arrange();
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEqualTo(asList(halfAWar, effectiveJava, springInAction));
    }

    @Test
    @DisplayName("books should be in descending order")
    void bookshelfArrangedByUserProvidedCriteria() {
        bookShelf.add(effectiveJava, halfAWar, springInAction);
        Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
        List<Book> books = bookShelf.arrange(reversed);
        assertThat(books).isSortedAccordingTo(reversed);
    }
}