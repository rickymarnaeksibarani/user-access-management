package com.gmf.user_management.core.utils;

import com.gmf.user_management.core.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
@SuppressWarnings({ "rawtypes" })
public class JpaResultHelperUtil {
    public static Object getSingleResultOrNull(Query query) {
        List results = query.getResultList();
        if (results.isEmpty())
            return null;
        else if (results.size() == 1)
            return results.get(0);
        throw new NonUniqueResultException();
    }

    public static <T> T getSingleResultOrNull(List<T> data) throws NotFoundException {
        Optional _first = data.stream().findFirst();
        if (_first.isEmpty()) {
            throw new NotFoundException("Data not found");
        }
        return data.get(0);
    }

    public static <T> T getSingleResultFromOptional(Optional<T> data) {
        return data.orElse(null);
    }
}