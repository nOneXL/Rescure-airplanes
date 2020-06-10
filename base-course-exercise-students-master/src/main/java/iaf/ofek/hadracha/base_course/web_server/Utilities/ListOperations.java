package iaf.ofek.hadracha.base_course.web_server.Utilities;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ListOperations {
    public interface Equalizer<T>{
        boolean isEqual(T o1, T o2);
    }

    /**
     * Returns all the elements that are in list1 but not in list2. Objects are compared
     * using the equalizer lambda function
     */
    public <T> List<T> subtract(List<T> list1, List<T> list2, Equalizer<T> equalizer){
        List<T> remaining = new ArrayList<>();
        list1.forEach(item -> {
            if (list2.stream().noneMatch(item2 -> equalizer.isEqual(item2, item)))
                remaining.add(item);
        });
        return remaining;
    }

    /**
     * Returns all the elements that are in list1 but not in list2. Objects are compared
     * using {@code Object.equals} method
     */
    public <T> List<T> subtract(@NotNull List<T> list1, @NotNull List<T> list2){
        return subtract(list1, list2, Objects::equals);
    }
}
