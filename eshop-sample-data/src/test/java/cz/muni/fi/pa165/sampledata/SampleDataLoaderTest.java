package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoaderTest extends AbstractTestNGSpringContextTests {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoaderTest.class);

    @Autowired
    public ProductDao productDao;

    @Autowired
    public OrderDao orderDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createSampleData() throws IOException {
        log.debug("starting test");
        SampleDataLoader sampleDataLoader = new SampleDataLoader();
        sampleDataLoader.setEntityManagerFactory(em.getEntityManagerFactory());
        sampleDataLoader.fillData();
        Assert.assertEquals(productDao.findAll().size(), 3, "number of products wrong ");
        Assert.assertEquals(orderDao.findAll().size(), 6, "number of orders wrong ");

    }

}