package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {

        try {
            if(ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = req.getHeader("accept");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // Accept의 상태가 */*, 또는 application/json일때의 오류 두개를 다 쓰자
                if("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();

                    //어떤 exception이 터졌나 보여준다
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    //json을 문자로 바꾸고 넘겨준다
                    String result = objectMapper.writeValueAsString(errorResult);

                    // ModelAndView라서 만들어서 보내줘야 됨 ㅋㅋ
                    res.setContentType("application/json");
                    res.setCharacterEncoding("utf-8");
                    res.getWriter().write(result);
                    return new ModelAndView();
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500");
                }

            }

        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
