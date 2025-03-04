package com.javarush.balyuk.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //return new Class[] {WebConfig.class};
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //return new Class[0];
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
