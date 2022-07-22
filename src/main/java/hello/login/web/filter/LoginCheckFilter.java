package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class LoginCheckFilter implements Filter {

    // 애네 빼고 다 로그인 인증 다 할거임
    private static final String[] whitelist = {"/", "/members/add", "/login", "logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse)  res;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);

                //세션 찾기
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청 {}", requestURI);

                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            // 화이트 리스트면 다음으로 넘어감
            chain.doFilter(req, res);
        } catch (Exception e) {
            throw e; // 예외 로깅 가능하지만, 톰캣까지 예외를 보내줌
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }
    /**
     * 화이트 리스트의 경우, 인증 체크 x
     *
     */

    // 로그인 체크
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
