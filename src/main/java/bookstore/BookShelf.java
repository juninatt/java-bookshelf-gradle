package bookstore;

import java.util.*;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<Book> books = new ArrayList<>();

    public void add(Book... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }
    public List<Book> arrangeByTitle() {
        return books.stream().sorted().collect(Collectors.toList());
    }

    public List<Book> arrangeByCriteria(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }
}
