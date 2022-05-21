package lesson16.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
/*
Заменяет web.xml
в методе getServletConfigClasses()
мы прописываем путь к нашему классу,
SpringConfig
заменяющему applicationContextMVC.xml

В методе getServletMappings()
мы прописываем зону действия - все файлы
как в web.xml
<servlet-name>dispatcher</servlet-name>
<url-pattern>/</url-pattern>
 */