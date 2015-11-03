package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.service.CategoryService;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.ProductService;
import cz.fi.muni.pa165.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

/**
 * Loads some sample data to populate the eshop database.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    public static final String JPEG = "image/jpeg";

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() throws IOException {
        Category food = category("Food");
        Category office = category("Office");
        Category flowers = category("Flowers");
        Category toys = category("Toys");
        Category presents = category("Presents");

        Product amber = product("Amber", "", "amber.jpg", JPEG, 10000, Color.UNDEFINED, presents);
        Product blackberries = product("Blackberries", "", "blackberries.jpg", JPEG, 20, Color.BLACK, food);
        Product blueberries = product("Blueberries", "", "blueberries.jpg", JPEG, 25, Color.BLUE, food);
        Product chilli = product("Chilli", "", "chilli.jpg", JPEG, 15, Color.RED, food);
        Product clamps = product("Clamps", "", "clamps.jpg", JPEG, 5, Color.UNDEFINED, office);
        Product cofee = product("Coffee", "", "coffee.jpg", JPEG, 100, Color.BROWN, food, office, presents);
        Product mouse = product("Mouse", "", "computer-mouse.jpg", JPEG, 200, Color.BLACK, office);
        Product cow = product("Cow", "", "cow.jpg", JPEG, 199, Color.BROWN, toys);
        Product crayons = product("Crayons", "", "crayons.jpg", JPEG, 10, Color.BLACK, office);
        Product diamonds = product("Diamond", "", "diamond.jpg", JPEG, 50000, Color.WHITE, presents);
        Product figs = product("Figs", "", "figs.jpg", JPEG, 100, Color.BROWN, food);
        Product gold = product("Gold", "", "gold.jpg", JPEG, 15000, Color.YELLOW, presents);
        Product horse = product("Horse", "", "horse.jpg", JPEG, 299, Color.BROWN, toys);
        Product limes = product("Limes", "", "limes.jpg", JPEG, 60, Color.GREEN, food);
        Product mixedFlowers = product("Mixed flowers", "", "mixed-flowers.jpg", JPEG, 300, Color.UNDEFINED, flowers);
        Product monitor = product("Monitor", "", "monitor.jpg", JPEG, 10000, Color.BLACK, office);
        Product narcissus = product("Narcissus", "", "narcissus.jpg", JPEG, 250, Color.YELLOW, flowers);
        Product notebook = product("Notebook", "", "notebook.jpg", JPEG, 20000, Color.BLACK, office);
        Product oranges = product("Oranges", "", "oranges.jpg", JPEG, 70, Color.ORANGE, food);
        Product pears = product("Pears", "", "pears.jpg", JPEG, 85, Color.GREEN, food);
        Product peppers = product("Peppers", "", "peppers.jpg", JPEG, 60, Color.UNDEFINED, food);
        Product pins = product("Pins", "", "pins.jpg", JPEG, 30, Color.UNDEFINED, office);
        Product raspberries = product("Raspberries", "", "raspberries.jpg", JPEG, 90, Color.PINK, food);
        Product duck = product("Rubber ducks", "", "rubber-duckies.jpg", JPEG, 99, Color.YELLOW, toys);
        Product strawberries = product("Strawberries", "Very tasty and exceptionally red strawberries.", "strawberries.jpg", JPEG, 80, Color.RED, food);
        Product tulip = product("Tulip", "", "tulip.jpg", JPEG, 220, Color.RED, flowers);
        log.info("Loaded eShop categories and products.");
        User pepa = user("heslo", "Pepa", "Novák", "pepa@novak.cz", "603123456", toDate(2015, 5, 12), "Horní Kotěhůlky 12");
        User jiri = user("heslo", "Jiří", "Dvořák", "jiri@dvorak.cz", "603789123", toDate(2015, 3, 5), "Dolní Lhota 56");
        User eva = user("heslo", "Eva", "Adamová", "eva@adamova.cz", "603457890", toDate(2015, 6, 5), "Zadní Polná 44");
        User admin = user("admin", "Josef", "Administrátor", "admin@eshop.com", "9999999999", toDate(2014, 12, 31), "Šumavská 15, Brno");
        log.info("Loaded eShop users.");
        order(pepa, daysBeforeNow(10), OrderState.DONE, orderItem(duck, 5), orderItem(cow, 1));
        order(pepa, daysBeforeNow(6), OrderState.SHIPPED, orderItem(horse, 3), orderItem(cow, 3));
        order(pepa, daysBeforeNow(3), OrderState.CANCELED, orderItem(duck, 10), orderItem(horse, 1));
        order(pepa, daysBeforeNow(2), OrderState.RECEIVED, orderItem(duck, 10), orderItem(horse, 1));
        order(jiri, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 1), orderItem(horse, 1), orderItem(cow, 1));
        order(eva, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 15), orderItem(horse, 7), orderItem(cow, 2));
        log.info("Loaded eShop orders.");
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
        orderService.createOrder(o);
        return o;
    }

    private OrderItem orderItem(Product product, int amount) {
        OrderItem oi = new OrderItem();
        oi.setProduct(product);
        oi.setPricePerItem(product.getCurrentPrice());
        oi.setAmount(amount);
        return oi;
    }

    private User user(String password, String givenName, String surname, String email, String phone, Date joined, String address) {
        User u = new User();
        u.setGivenName(givenName);
        u.setSurname(surname);
        u.setEmail(email);
        u.setPhone(phone);
        u.setAddress(address);
        u.setJoinedDate(joined);
        if(password.equals("admin")) u.setAdmin(true);
        userService.registerUser(u, password);
        return u;
    }

    private Category category(String name) {
        Category c = new Category();
        c.setName(name);
        categoryService.create(c);
        return c;
    }

    private static Random random = new Random();

    private Price price(long price, ZonedDateTime priceStart) {
        Price p = new Price();
        p.setCurrency(Currency.CZK);
        p.setPriceStart(Date.from(priceStart.toInstant()));
        p.setValue(BigDecimal.valueOf(price));
        return p;
    }

    private Product product(String name, String description, String imageFile, String mimeType, long price, Color color, Category... categories) throws IOException {
        Product pr = new Product();
        for (Category category : categories) {
            pr.addCategory(category);
        }
        pr.setColor(color);
        pr.setName(name);
        pr.setDescription(description);

        //set curent price as 7 days ago
        ZonedDateTime day = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(7);
        Price p = price(price, day);
        pr.setCurrentPrice(p);
        pr.addHistoricalPrice(p);
        //generate randomly higher historical prices
        for (int i = 0, n = 1 + random.nextInt(8); i <= n; i++) {
            day = day.minusMonths(1);
            price = price + 1 + random.nextInt((int) (price / 5l));
            pr.addHistoricalPrice(price(price, day));
        }
        pr.setAddedDate(Date.from(day.toInstant()));

        pr.setImage(readImage(imageFile));
        pr.setImageMimeType(mimeType);
        productService.createProduct(pr);
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
