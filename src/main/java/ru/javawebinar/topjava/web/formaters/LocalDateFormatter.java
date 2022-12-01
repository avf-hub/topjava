package ru.javawebinar.topjava.web.formaters;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalDate(text);
    }

    @Override
    public String print(LocalDate ld, Locale locale) {
        return ld.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
