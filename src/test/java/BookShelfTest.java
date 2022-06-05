import bookstore.Book;
import bookstore.BookShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
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
    private Book springSecurityInAction;

    @BeforeEach
    void init() throws Exception {
        bookShelf = new BookShelf();
        springInAction = new Book("Spring in Action, 6th Edition", "Craig Walls", LocalDate.of(2022, Month.JANUARY, 1));
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        halfAWar = new Book("Half a War", "Joe Abercrombie", LocalDate.of(2016, Month.MARCH, 8));
        springSecurityInAction = new Book("Spring Security in Action", "Laurentiu Spilca", LocalDate.of(2020,Month.JANUARY, 1));
    }

    /**
     * @param testInfo Contains information about the current test
     */
    @Test
    @DisplayName("bookshelf is empty when no book is added to it")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("bookshelf contains two books when two books are added to it")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        bookShelf.add(effectiveJava, halfAWar);
        List<Book> books = bookShelf.getBooks();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("bookshelf without books remains empty when add-method is called without parameters")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        bookShelf.add();
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("exception is thrown when book is added to existing book collection")
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
    @DisplayName("bookshelf is arranged lexicographically by title")
    void bookShelfArrangedByBookTitle() {
        bookShelf.add(effectiveJava, springInAction, halfAWar);
        List<Book> books = bookShelf.arrange();
        assertThat(books).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("bookshelf returns immutable collection of books to client")
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        bookShelf.add(halfAWar, effectiveJava, springInAction);
        bookShelf.arrange();
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEqualTo(asList(halfAWar, effectiveJava, springInAction));
    }

    @Test
    @DisplayName("books in bookshelf are arranged by user-provided criteria(reversed lexicographical order by title")
    void bookshelfArrangedByUserProvidedCriteria() {
        bookShelf.add(effectiveJava, halfAWar, springInAction);
        Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
        List<Book> books = bookShelf.arrange(reversed);
        assertThat(books).isSortedAccordingTo(reversed);
    }

    @Test
    @DisplayName("books inside bookshelf are grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear() {
        bookShelf.add(effectiveJava, halfAWar, springInAction, springSecurityInAction);
        Map<Year, List<Book>> booksByPublicationYear = bookShelf.groupByPublicationYear();

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2008))
                .containsValues(singletonList(effectiveJava));

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2016))
                .containsValues(singletonList(halfAWar));

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2020))
                .containsValues(singletonList(springSecurityInAction));

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2022))
                .containsValues(singletonList(springInAction));
    }

    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
    void groupBooksByUserProvidedCriteria() {
        bookShelf.add(effectiveJava, springInAction, springSecurityInAction, halfAWar);
        Map<String, List<Book>> booksByAuthor = bookShelf.groupBy(Book::getAuthor);

        assertThat(booksByAuthor)
                .containsKey("Joshua Bloch")
                .containsValues(singletonList(effectiveJava));

        assertThat(booksByAuthor)
                .containsKey("Craig Walls")
                .containsValues(singletonList(springInAction));

        assertThat(booksByAuthor)
                .containsKey("Laurentiu Spilca")
                .containsValues(singletonList(springSecurityInAction));

        assertThat(booksByAuthor)
                .containsKey("Joe Abercrombie")
                .containsValues(singletonList(halfAWar));
    }
}