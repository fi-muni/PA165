package cz.fi.muni.pa165.ws.app;

import java.util.List;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Our configuration class note @EnableWs for the usage of Spring-WS
 * http://docs.spring.io/spring-ws/docs/current/api/org/springframework/ws/config/annotation/EnableWs.html
 */
@EnableWs
@Configuration
@ComponentScan("cz.fi.muni.pa165.ws")
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * Creation of the MessageDispatcherServlet, note that it is different from
     * a DispatcherServlet see
     * http://docs.spring.io/spring-ws/site/reference/html/server.html in
     * particular if you need to use it in a standard DispatcherServlet (section
     * 5.3.2)
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/*");
    }

    /**
     *
     * WSDL definition from the provided schema (in our case products) when the
     * application is run you can find the WSDL file at
     * http://localhost:8080/spring-ws-seminar/products.wsdl
     *
     * See
     * http://docs.spring.io/spring-ws/docs/current/reference/html/server.html
     *
     */
    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("productsPort");
        wsdl11Definition.setLocationUri("/");
        wsdl11Definition.setTargetNamespace("http://muni.fi.cz/pa165/ws/entities/products");
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    /**
     * Setting the schema for the products see
     * http://docs.spring.io/spring-ws/site/spring-xml/apidocs/org/springframework/xml/xsd/SimpleXsdSchema.html
     *
     */
    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
    }

    /**
     * adding the payload interceptor
     *
     * @param interceptors
     */
    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(this.myPayLoadInterceptor());
    }

    /**
     * We are setting here a payload interceptor to validate both requests and
     * responses See
     * http://docs.spring.io/spring-ws/site/apidocs/org/springframework/ws/soap/server/endpoint/interceptor/PayloadValidatingInterceptor.html
     *
     * @return
     */
    @Bean
    public PayloadValidatingInterceptor myPayLoadInterceptor() {
        final PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        interceptor.setXsdSchema(this.productsSchema());
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(true);
        return interceptor;
    }
}
