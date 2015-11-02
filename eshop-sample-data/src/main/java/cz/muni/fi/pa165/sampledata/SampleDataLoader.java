package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.enums.OrderState;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Loads some sample data to populate the eshop database.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class SampleDataLoader {

    public static final String JPEG = "image/jpeg";
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void fillData() throws IOException {
        this.em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Category electronics = category("Electronics");
        Category computers = category("Computers");
        Category books = category("Books");
        Category movies = category("Movies");
        Category home = category("Home & garden");
        Category toys = category("Toys");
        Category sports = category("Sports");

        Product duck = product(toys, "Duck", Color.YELLOW, 99, toDate(2015, 1, 1), "rubber-duckies.jpg", JPEG);
        Product cow = product(toys, "Cow", Color.BROWN, 199, toDate(2015, 1, 1), "bull.jpg", JPEG);
        Product horse = product(toys, "Horse", Color.BROWN, 299, toDate(2015, 1, 1), "horse.jpg", JPEG);

        User pepa = user("Pepa", "Novák", "pepa@novak.cz", "603123456", toDate(2015, 5, 12));
        User jiri = user("Jiří", "Dvořák", "jiri@dvorak.cz", "603789123", toDate(2015, 3, 5));
        User eva = user("Eva", "Adamová", "eva@adamova.cz", "603457890", toDate(2015, 6, 5));
        User admin = user("admin", "Admin", "admin@eshop.com", "9999999999", toDate(2014, 12, 31));


        order(pepa, daysBeforeNow(10), OrderState.DONE, orderItem(duck, 5), orderItem(cow, 1));
        order(pepa, daysBeforeNow(6), OrderState.SHIPPED, orderItem(horse, 3), orderItem(cow, 3));
        order(pepa, daysBeforeNow(3), OrderState.CANCELED, orderItem(duck, 10), orderItem(horse, 1));
        order(pepa, daysBeforeNow(2), OrderState.RECEIVED, orderItem(duck, 10), orderItem(horse, 1));
        order(jiri, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 1), orderItem(horse, 1), orderItem(cow, 1));
        order(eva, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 15), orderItem(horse, 7), orderItem(cow, 2));

        em.getTransaction().commit();
        em.close();
    }

    private static Date daysBeforeNow(int days) {
        return Date.from(ZonedDateTime.now().minusDays(days).toInstant());
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Order order(User user, Date created, OrderState state, OrderItem... items) {
        Order o = new Order();
        o.setUser(user);
        o.setCreated(created);
        o.setState(state);
        for (OrderItem item : items) {
            o.addOrderItem(item);
        }
        em.persist(o);
        return o;
    }

    private OrderItem orderItem(Product product, int amount) {
        OrderItem oi = new OrderItem();
        oi.setProduct(product);
        oi.setPricePerItem(product.getCurrentPrice());
        oi.setAmount(amount);
        em.persist(oi);
        return oi;
    }

    private User user(String givenName, String surname, String email, String phone, Date joined) {
        User u = new User();
        u.setGivenName(givenName);
        u.setSurname(surname);
        u.setEmail(email);
        u.setPhone(phone);
        u.setJoinedDate(joined);
        em.persist(u);
        return u;
    }

    private Category category(String name) {
        Category c = new Category();
        c.setName(name);
        em.persist(c);
        return c;
    }

    private Product product(Category category, String name, Color color, long price, Date added,
                            String imageFile, String mimeType) throws IOException {
        Price p = new Price();
        p.setCurrency(Currency.CZK);
        p.setPriceStart(added);
        p.setValue(BigDecimal.valueOf(price));
        em.persist(p);

        Product pr = new Product();
        pr.addCategory(category);
        pr.setCurrentPrice(p);
        pr.addHistoricalPrice(p);
        pr.setAddedDate(added);
        pr.setColor(color);
        pr.setName(name);

        pr.setImage(readImage(imageFile));
        pr.setImageMimeType(mimeType);
        em.persist(pr);
        return pr;
    }

    private byte[] readImage(String file) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream("/" + file)) {
            int nRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }
    }
}
