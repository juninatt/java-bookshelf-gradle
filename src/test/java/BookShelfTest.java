

import bookstore.BookShelf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("<= BookShelf Tests =>")
class BookShelfTest {

    /**
     * @param testInfo Contains information about the current test
     */
    @Test
    @DisplayName("<= Shelf should return empty when no books added =>")
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }
}