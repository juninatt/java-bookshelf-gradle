import bookstore.Book;
import bookstore.BookShelf;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

//  TODO: Improve exception tests

@DisplayName("the bookshelf")
@ExtendWith(BooksParameterResolver.class)
class BookShelfTest {

    private BookShelf bookShelf;

    private Book springInAction;
    private Book effectiveJava;
    private Book halfAWar;
    private Book springSecurityInAction;

    @BeforeEach
    void init(Map<String, Book> books) {
        bookShelf = new BookShelf();
        this.springInAction = books.get("Spring in Action");
        this.effectiveJava = books.get("Effective Java");
        this.halfAWar = books.get("Half a War");
        this.springSecurityInAction = books.get("Spring Security in Action");
    }

    @Nested
    @DisplayName("is empty")
    class BookShelfIsEmptyTests {

        @Test
        @DisplayName("when no book is added to it")
        void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEmpty();
    }

        @Test
        @DisplayName("when add-method is called without books")
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        bookShelf.add();
        List<Book> books = bookShelf.getBooks();
        assertThat(books).isEmpty();
    }
}
    @Nested
    @DisplayName("after adding books")
    class BooksAreAddedTests {

        @Test
        @DisplayName("contains two books")
        public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            bookShelf.add(effectiveJava, halfAWar);
            List<Book> books = bookShelf.getBooks();
            assertThat(books.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("returns immutable collection of books to client")
        void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            bookShelf.add(halfAWar, effectiveJava, springInAction);
            bookShelf.arrange();
            List<Book> books = bookShelf.getBooks();
            assertThat(books).isEqualTo(asList(halfAWar, effectiveJava, springInAction));
        }
    }

    @Nested
    @DisplayName("is arranged")
    class BooksAreArrangedTests {

        @Test
        @DisplayName("lexicographically by title")
        void bookShelfArrangedByBookTitle() {
            bookShelf.add(effectiveJava, springInAction, halfAWar);
            List<Book> books = bookShelf.arrange();
            assertThat(books).isSortedAccordingTo(Comparator.naturalOrder());
        }

        @Test
        @DisplayName("by user-provided criteria(reversed lexicographical order by title")
        void bookshelfArrangedByUserProvidedCriteria() {
            bookShelf.add(effectiveJava, halfAWar, springInAction);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = bookShelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }
    }

    @Nested
    @DisplayName("is grouped")
    class BooksByGroupTests {

        @Test
        @DisplayName("by publication year")
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
        @DisplayName("according to user provided criteria(group by author name)")
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
}