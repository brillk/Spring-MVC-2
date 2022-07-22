package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {

    //에러 페이지 보여주기
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest req, HttpServletResponse res) {
        log.info("errorPage 404");
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest req, HttpServletResponse res) {
        log.info("errorPage 500");
        return "error-page/500";
    }
}
