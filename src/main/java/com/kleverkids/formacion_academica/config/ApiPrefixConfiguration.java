package com.kleverkids.formacion_academica.config;

import org.springframework.boot.webmvc.autoconfigure.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Set;

@Configuration
public class ApiPrefixConfiguration implements org.springframework.boot.webmvc.autoconfigure.WebMvcRegistrations {

    private static final String API_PREFIX = "/api/kleverkids";
    private static final String SPRINGDOC_BASE_PACKAGE = "org.springdoc";

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
                if (shouldApplyPrefix(method, mapping)) {
                    mapping = RequestMappingInfo.paths(API_PREFIX).build().combine(mapping);
                }
                super.registerHandlerMethod(handler, method, mapping);
            }
        };
    }

    private boolean shouldApplyPrefix(Method method, RequestMappingInfo mappingInfo) {
        Class<?> declaringClass = method.getDeclaringClass();
        String packageName = declaringClass.getPackageName();
        if (!packageName.startsWith("com.kleverkids")) {
            return false;
        }
        if (packageName.startsWith(SPRINGDOC_BASE_PACKAGE)) {
            return false;
        }

        Set<String> patterns = mappingInfo.getPatternValues();
        return patterns.stream().noneMatch(pattern -> pattern.startsWith(API_PREFIX));
    }
}
