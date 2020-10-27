package cn.mvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获得请求的URL
        String url = httpServletRequest.getRequestURI();
        if (url.contains("/ApiKanBantable")||url.contains("/ApiKanBanModel")||url.contains("/autoRedirect")||url.contains("/reguser")||url.contains("/loginmian")||url.contains("/login")||url.contains("/css/")||url.contains("/js/")||url.contains("/images/")||url.contains("/lib/") ||url.contains("/data/") ||url.contains("/pages/")) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("EmpyNo")!=null) {
            String user = (String) session.getAttribute("EmpyNo");
            if (user != null) {
                return true;
            }
          //  System.err.println("###############################################"+user);

            //不合条件的给提示信息，并转到登录页面
            httpServletRequest.setAttribute("msg", "您还没登录，请先登录！");
            httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
            return false;
        }

        String user = (String) session.getAttribute("EmpyNo");
        if (user != null) {
            return true;
        }
        httpServletRequest.setAttribute("msg", "您还没登录，请先登录！");
        httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
