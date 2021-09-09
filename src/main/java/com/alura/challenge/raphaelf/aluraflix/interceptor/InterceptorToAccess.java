package com.alura.challenge.raphaelf.aluraflix.interceptor;

import com.alura.challenge.raphaelf.aluraflix.entities.AccessLogs;
import com.alura.challenge.raphaelf.aluraflix.services.AccessLogsService;
import com.alura.challenge.raphaelf.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;


public class InterceptorToAccess implements HandlerInterceptor {

    @Autowired
    private AccessLogsService service;
    private AccessLogs acesso;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        acesso = new AccessLogs();
        acesso.setPath(request.getRequestURI());
        acesso.setRemoteAddr(request.getRemoteAddr());
        acesso.setUsername(request.getUserPrincipal() == null ? "anonymous" : request.getUserPrincipal().getName());
        acesso.setData(LocalDateTime.now());

        request.setAttribute("acesso", acesso);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        acesso = (AccessLogs) request.getAttribute("acesso");
        if (acesso == null) {
            throw new ResourceNotFoundException("sem dados de acesso");
        }
        acesso.setDuracao(Duration.between(acesso.getData(), LocalDateTime.now()));
        service.save(acesso);
    }

}
