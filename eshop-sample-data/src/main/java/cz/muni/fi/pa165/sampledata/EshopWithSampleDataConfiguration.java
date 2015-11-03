package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Takes ServiceConfiguration and adds the SampleDataLoadingFacade bean.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class EshopWithSampleDataConfiguration {

}
