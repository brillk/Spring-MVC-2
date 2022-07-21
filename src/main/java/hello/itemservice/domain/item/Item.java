package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총합이 10000을 넘겨주세요")
public class Item {

    //Bean Validation에서 특정 필드( FieldError )가 아닌 해당 오브젝트 관련 오류( ObjectError )는
    //어떻게 처리할 수 있을까? => 원래는 @ScriptAssert 쓰지만 제약이 많다

    //따라서 오브젝트 오류(글로벌 오류)의 경우 @ScriptAssert 을 억지로 사용하는 것 보다는 다음과 같이
    //오브젝트 오류 관련 부분만 직접 자바 코드로 작성하는 것을 권장한다.


    // 현재 등록, 수정을 따로 적용시킨다
    @NotNull(groups = UpdateCheck.class)
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    //문제점
    //데이터를 등록할 때와 수정할 때는 요구사항이 다를 수 있다.
    //그런데 수정은 잘 동작하지만 등록에서 문제가 발생한다.
    //등록시에는 id 에 값도 없고, quantity 수량 제한 최대 값인 9999도 적용되지 않는 문제가 발생한다.
    //등록시 화면이 넘어가지 않으면서 다음과 같은 오류를 볼 수 있다.
    //'id': rejected value [null];
    //왜냐하면 등록시에는 id 에 값이 없다. 따라서 @NotNull id 를 적용한 것 때문에 검증에 실패하고 다시
    //폼 화면으로 넘어온다. 결국 등록 자체도 불가능하고, 수량 제한도 걸지 못한다.


    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
