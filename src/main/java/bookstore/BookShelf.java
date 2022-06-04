package bookstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<String> books = new ArrayList<>();

    public void add(String... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }
    public List<String> getBooks() {
        return Collections.unmodifiableList(books);
    }
    public List<String> arrangeByTitle() {
        return books.stream().sorted().collect(Collectors.toList());
    }
}
