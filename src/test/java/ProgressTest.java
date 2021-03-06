import bookstore.Book;
import bookstore.BookShelf;
import bookstore.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Progress")
@ExtendWith(BooksParameterResolver.class)
public class ProgressTest {

    private BookShelf bookShelf;

    private Book springInAction;
    private Book effectiveJava;
    private Book halfAWar;
    private Book springSecurityInAction;
    private Book springBootInAction;

    @BeforeEach
    void init(Map<String, Book> books) {
        this.bookShelf = new BookShelf();
        this.springInAction = books.get("Spring in Action");
        this.effectiveJava = books.get("Effective Java");
        this.halfAWar = books.get("Half a War");
        this.springSecurityInAction = books.get("Spring Security in Action");
        this.springBootInAction = books.get("Spring Boot in Action");
    }

    @Test
    @DisplayName("is 0% completed and 100% to-read when no book is read yet")
    void progress100PercentUnread() {
        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(0);
        assertThat(progress.toRead()).isEqualTo(100);
    }

    @Test
    @DisplayName("is 100% completed and 0% to-read when no book is read yet")
    void progressZeroPercentUnread() {
        bookShelf.add(halfAWar);
        halfAWar.startedReadingOn(LocalDate.of(2016, Month.JUNE, 1));
        halfAWar.finishedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(100);
        assertThat(progress.toRead()).isEqualTo(0);
    }

    @Test
    @DisplayName("is 40% and 60% to-read when 2 books are finished and 3 books not read yet")
    void progressWithCompletedAndToReadPercentages() {
        bookShelf.add(effectiveJava, halfAWar, springInAction, springSecurityInAction, springBootInAction);
        effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
        halfAWar.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        halfAWar.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
        Progress progress = bookShelf.progress();
        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.toRead()).isEqualTo(60);
    }
}
