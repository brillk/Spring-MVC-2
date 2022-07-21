package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static  final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();


    /**
     *세션 관리는 크게 다음 3가지 기능을 제공하면 된다.
     *
     * 1. 세션 생성
     * sessionId 생성 (임의의 추정 불가능한 랜덤 값)
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     *
     * 2. 세션 조회
     * 클라이언트가 요청한 sessionId 쿠키의 값으로, 세션 저장소에 보관한 값 조회
     *
     * 3. 세션 만료
     * 클라이언트가 요청한 sessionId 쿠키의 값으로, 세션 저장소에 보관한 sessionId와 값 제거
     *
     */

    public void createSession(Object value, HttpServletResponse res) {


        //세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);


        //쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        res.addCookie(cookie);
    }

    /**
     * 세션 조회
     */

    public Object getSession(HttpServletRequest req) {
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if(sessionCookie == null) {
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }


    /**
     *
     * 세션 만료
     */
    public void expire(HttpServletRequest req) {
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if(sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest req, String cookieName) {

        if(req.getCookies() == null) {
            return null;
        }
        //array를 stream으로 만든다
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
