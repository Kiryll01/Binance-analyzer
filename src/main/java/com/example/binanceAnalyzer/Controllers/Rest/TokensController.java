package com.example.binanceAnalyzer.Controllers.Rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
    public class TokensController {
        @GetMapping("/csrf")
        public @ResponseBody String getCsrfToken(HttpServletRequest request) {
            CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            return csrf.getToken();
        }
        @GetMapping("/auth/token")
    public @ResponseBody String getAuthToken(HttpServletRequest request){
          return request.getHeader("Authorization");
        }
    }

