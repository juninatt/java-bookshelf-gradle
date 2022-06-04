

import bookstore.BookShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("the bookshelf")
class BookShelfTest {

    private BookShelf bookShelf;

    @BeforeEach
    void init() throws Exception {
        bookShelf = new BookShelf();
    }

    /**
     * @param testInfo Contains information about the current test
     */
    @Test
    @DisplayName("is empty when no books added to it")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        List<String> books = bookShelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should have two books when two books added to it")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        bookShelf.add("Effective Java", "Code Complete");
        List<String> books = bookShelf.getBooks();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    @DisplayName("should be empty when add method empty")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        bookShelf.add();
        List<String> books = bookShelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should throw exception if book is added to existing book list")
    void booksReturnedFromBookShelfIsImmutableForClient() {
        bookShelf.add("Effective Java", "Code Complete");
        List<String> books = bookShelf.getBooks();
        try {
            books.add("The Mythical Man-Month");
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }
    }

    @Test
    @DisplayName("should arrange books lexicographically by title")
    void bookShelfArrangedByBookTitle() {
        bookShelf.add("Effective Java", "Code Complete","Spring in Action" );
        List<String> books = bookShelf.arrangeByTitle();
        assertEquals(Arrays.asList( "Code Complete", "Effective Java", "Spring in Action"), books,
                () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        bookShelf.add("Effective Java", "Code Complete", "Spring in Action");
        bookShelf.arrangeByTitle();
        List<String> books = bookShelf.getBooks();
        assertEquals(Arrays.asList("Effective Java", "Code Complete", "Spring in Action"), books,
                () -> "Books in bookshelf are in insertion order");
    }
}