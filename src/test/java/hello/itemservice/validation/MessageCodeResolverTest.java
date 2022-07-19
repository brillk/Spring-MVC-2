package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodeResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    //MessageCodesResolver
    //검증 오류 코드로 메시지 코드들을 생성한다.
    //MessageCodesResolver 인터페이스이고 DefaultMessageCodesResolver 는 기본 구현체이다.
    //주로 다음과 함께 사용 ObjectError , FieldError

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
       assertThat(messageCodes).containsExactly(
               "required.item.itemName",
               "required.itemName",
               "required.java.lang.String",
               "required"
       );
    }
    //객체 오류의 경우 다음 순서로 2가지 생성
    //1.: code + "." + object name
    //2.: code

    //예) 오류 코드: required, object name: item
    //1.: required.item
    //2.: required

    //필드 오류
    //필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
    //1.: code + "." + object name + "." + field
    //2.: code + "." + field
    //3.: code + "." + field type
    //4.: code
}
