package cz.fi.muni.pa165.service.facade;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class FacadeUtils
{
	//TODO what about using dependenci injection!?
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public static <T> List<T> mapTo(List<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    public static <T> T mapTo(Object u, Class<T> mapToClass)
    {
        return dozer.map(u,mapToClass);
    }
}
