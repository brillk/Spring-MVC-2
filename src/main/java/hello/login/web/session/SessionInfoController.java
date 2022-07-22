package hello.login.web.session;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        if(session == null) {
            return "세션이 없습니다";
        }

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        //sessionId=0556EB7C7EEAE8143470F2012D8F0043
        //getMaxInactiveInterval=1800
        //creationTime=Fri Jul 22 09:08:50 KST 2022
        //lastAccessedTime=Fri Jul 22 09:08:50 KST 2022
        //isNew=false

        return "세션 출력";
    }

}
