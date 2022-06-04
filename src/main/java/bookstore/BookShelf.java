package bookstore;

import java.util.*;

public class BookShelf {

    private final List<String> books = new ArrayList<>();

    public void add(String... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }
    public List<String> getBooks() {
        return Collections.unmodifiableList(books);
    }
    public List<String> arrangeByTitle() {
        books.sort(Comparator.naturalOrder());
        return books;
    }
}
