package cz.fi.muni.pa165.cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
public class UserFacadeTest
{
    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }

}
