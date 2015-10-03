package cz.fi.muni.pa165.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.service.OrderServiceImpl;
import cz.fi.muni.pa165.service.facade.CategoryFacadeImpl;


@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses={OrderServiceImpl.class, CategoryFacadeImpl.class})
public class ServiceConfiguration {
	
	
}
