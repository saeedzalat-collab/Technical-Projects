package com.example.multitenanthrapi.security;

import com.example.multitenanthrapi.TenantContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String tenant = req.getHeader("X-Tenant-ID");
        if (tenant != null && !tenant.isBlank()) {
            TenantContextHolder.setTenant(tenant);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
