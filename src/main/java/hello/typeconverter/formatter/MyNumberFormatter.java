package hello.typeconverter.formatter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {


    // Formatter Formatter 는 문자에 특화(객체 문자, 문자 객체) + 현지화(Locale)
    //Converter 의 특별한 버전

    //String print(T object, Locale locale) : 객체를 문자로 변경한다.
    //T parse(String text, Locale locale) : 문자를 객체로 변경한다.

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        return  NumberFormat.getInstance(locale).format(object);

    }
}
