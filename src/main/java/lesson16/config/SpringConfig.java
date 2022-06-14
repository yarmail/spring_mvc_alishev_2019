package lesson16.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;


/**
 * Урок 29
 * Добавляем Аннотацию (собака)PropertySource
 * для подключения файла property c данными базы данных
 * и подключаем Environment для использования
 * этими свойствами
 */
@PropertySource("classpath:database.properties")
@Configuration
@ComponentScan ({"lesson16", "lesson21"})
// Изменения 2 папки - 17, 20, 21
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    /**
     * Урок 27 создаем бин DataSource, чтобы
     * использовать JdbcTemplate
     * Как я понимаю в этом классе мы описывем нашу базу данных
     * Переносим данные, которые раньше были в PersonDAO
     * ---
     * Урок 29 переносим все чувствительные данные
     * в resource в качестве property-файлов
     *
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("user_name"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }

    /**
     * Урок 27 создаем бин JdbcTemplate,
     * который будет использовать
     * бин DataSource
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

/*
Интерфейс WebMvcConfigurer реализуется,
когда мы под себя хотим настроить Spring MVC
В данном случае мы хотим использовать
Thymeleaf и используем configureViewResolvers метод
для этого

Также мы с помощью @Autowired
внедряем applicationContext.
applicationContext мы используем,
чтобы настроить в методе
templateResolver() Thymeleaf

эта конфигурация полностью идентична
applicationContextMVC.xml

Переделываем конфиг с 17 на 20 урок
Будем писать контроллер другим способом
 */