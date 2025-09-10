package org.easywork.console.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;
import java.util.Locale;

/**
 * 国际化配置
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {
    
    /**
     * 语言解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        
        // 设置默认语言
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        
        // 设置支持的语言列表
        resolver.setSupportedLocales(List.of(
            Locale.SIMPLIFIED_CHINESE,
            Locale.US
        ));
        
        return resolver;
    }
    
    /**
     * 语言切换拦截器
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // 设置参数名，前端可以通过?lang=en来切换语言
        interceptor.setParamName("lang");
        return interceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}