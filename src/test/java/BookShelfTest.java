

import bookstore.BookShelf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("the bookshelf")
class BookShelfTest {

    /**
     * @param testInfo Contains information about the current test
     */
    @Test
    @DisplayName("is empty when no books added to it")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should have two books when two books added to it")
    public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.getBooks();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    @DisplayName("should be empty when add method empty")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        BookShelf shelf = new BookShelf();
        shelf.add();
        List<String> books = shelf.getBooks();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("should throw exception if book is added to existing book list")
    void booksReturnedFromBookShelfIsImmutableForClient() {
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.getBooks();
        try {
            books.add("The Mythical Man-Month");
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }
    }
}