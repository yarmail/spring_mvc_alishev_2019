package lesson16.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

    /**
     * К уроку 23
     * настойки по работе фильтра
     * Запуск приватного метода
     * registerHiddenFieldFilter
     * при старте системы
     */
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    /**
     * К уроку 23
     * настойки по работе фильтра
     * Запускаем фильтр который работает со скрытыми полями
     * в форме (для PATCH ....)
     * HiddenHttpMethodFilter
     * Работиает по всем запросам
     */
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
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

Урок 23
Добавляем фильтр для того, чтобы
POST
запросы со скрытым полем PATCH
перенаправлялись на метод, который обрабатывает
PATCH запросы
 */