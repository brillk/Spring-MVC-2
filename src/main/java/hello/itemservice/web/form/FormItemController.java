package hello.itemservice.web.form;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
        // 도서 기타 음식 itemTypes 를 등록 폼, 조회, 수정 폼에서 모두 사용하므로 @ModelAttribute 의 특별한 사용법을
        //적용하자.
        //ItemType.values() 를 사용하면 해당 ENUM의 모든 정보를 배열로 반환
    }

    /*@ModelAttribute의 특별한 사용법
    등록 폼, 상세화면, 수정 폼에서 모두 서울, 부산, 제주라는 체크 박스를 반복해서 보여주어야 한다.
    이렇게 하려면 각각의 컨트롤러에서 model.addAttribute(...) 을 사용해서 체크 박스를 구성하는 데이터를
    반복해서 넣어주어야 한다.
    @ModelAttribute 는 이렇게 컨트롤러에 있는 별도의 메서드에 적용할 수 있다.
    이렇게하면 해당 컨트롤러를 요청할 때 regions 에서 반환한 값이 자동으로 모델( model )에 담기게 된다*/
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", item.getOpen()); // true, false
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";

        //실행 로그
        //FormItemController : item.open=true //체크 박스를 선택하는 경우
        //FormItemController : item.open=false //체크 박스를 선택하지 않는 경우

        //체크 박스 체크
        //open=on&_open=on
        //체크 박스를 체크하면 스프링 MVC가 open 에 값이 있는 것을 확인하고 사용한다. 이때 _open 은
        //무시한다.
        //체크 박스 미체크
        //_open=on
        //체크 박스를 체크하지 않으면 스프링 MVC가 _open 만 있는 것을 확인하고, open 의 값이 체크되지
        //않았다고 인식한다.
        //이 경우 서버에서 Boolean 타입을 찍어보면 결과가 null 이 아니라 false 인 것을 확인할 수 있다.
        //log.info("item.open={}", item.getOpen());
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

