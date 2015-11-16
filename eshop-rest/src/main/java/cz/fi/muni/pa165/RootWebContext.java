package cz.fi.muni.pa165;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cz.fi.muni.pa165.service.CategoryServiceImpl;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import cz.fi.muni.pa165.service.facade.CategoryFacadeImpl;
import cz.fi.muni.pa165.service.facade.OrderFacadeImpl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@Import({ServiceConfiguration.class,SpringMVCConfig.class})
public class RootWebContext extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllowOriginInterceptor()); 
    }
    
    

    
}
