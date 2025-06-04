package ru.pro.zakupki.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pro.zakupki.model.Image;
import ru.pro.zakupki.model.Manufacturer;
import ru.pro.zakupki.model.Product;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.requests.ManufacturerAddRequest;
import ru.pro.zakupki.service.ManufacturerService;
import ru.pro.zakupki.service.ProductService;
import ru.pro.zakupki.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final ManufacturerService manufacturerService;

    @Override
    public void run(String... args) {

        ManufacturerAddRequest manufacturer1 = new ManufacturerAddRequest();
        manufacturer1.setName("IKEA");
        manufacturer1.setCountry("Sweden");
        manufacturer1.setDescription("Одна из крупнейших в мире торговых сетей по продаже мебели и товаров для дома.");

        ManufacturerAddRequest manufacturer2 = new ManufacturerAddRequest();
        manufacturer2.setName("Tim Hortons");
        manufacturer2.setCountry("Canada");
        manufacturer2.setDescription("Канадская сеть закусочных построенная по системе фаст-фуд.");

        manufacturerService.createManufacturer(manufacturer1);
        manufacturerService.createManufacturer(manufacturer2);
        Manufacturer manufacturer11 = manufacturerService.findManufacturerById(1L);

        Image bedImage = ImageUtils.createTestImage("src/main/resources/images/bed.jpg");
        Image closetImage = ImageUtils.createTestImage("src/main/resources/images/closet.jpg");
        Image chairImage = ImageUtils.createTestImage("src/main/resources/images/chair.jpg");
        Image tvImage = ImageUtils.createTestImage("src/main/resources/images/tv.jpg");
        Image doorImage = ImageUtils.createTestImage("src/main/resources/images/door.jpg");
        Image chandelierImage = ImageUtils.createTestImage("src/main/resources/images/chandelier.jpg");

        Product bed = new Product();
        bed.setName("Кровать");
        bed.setPrice(15000L);
        bed.setImage(bedImage);
        bed.setDescription("Уютная двуспальная кровать с деревянным каркасом и ортопедическим основанием для комфортного сна");
        bed.setManufacturer(manufacturer11);

        Product closet = new Product();
        closet.setName("Шкаф");
        closet.setPrice(10000L);
        closet.setImage(closetImage);
        closet.setDescription("Просторный гардероб с деревянными полками и штангой для вешалок, идеально подходящий для хранения одежды и аксессуаров");
        closet.setManufacturer(manufacturer11);

        Product chair = new Product();
        chair.setName("Кресло");
        chair.setPrice(6000L);
        chair.setImage(chairImage);
        chair.setDescription("Мягкое кресло с эргономичной спинкой, удобными подлокотниками и стильным дизайном для комфортного отдыха");
        chair.setManufacturer(manufacturer11);

        Product tv = new Product();
        tv.setName("Телевизор");
        tv.setPrice(45000L);
        tv.setImage(tvImage);
        tv.setDescription("Современный 4K-телевизор с тонкими рамками, HDR-поддержкой и встроенным Smart TV для комфортного просмотра фильмов и сериалов");
        tv.setManufacturer(manufacturer11);


        Product door = new Product();
        door.setName("Дверь");
        door.setPrice(17000L);
        door.setImage(doorImage);
        door.setDescription("Прочная межкомнатная дверь из массива дерева с фрезерованным узором и бесшумным доводчиком для плавного закрывания");
        door.setManufacturer(manufacturer11);

        Product chandelier = new Product();
        chandelier.setName("Люстра");
        chandelier.setPrice(12000L);
        chandelier.setImage(chandelierImage);
        chandelier.setDescription("Элегантная потолочная люстра с хрустальными подвесками и регулируемой яркостью, создающая уютное освещение в интерьере");
        chandelier.setManufacturer(manufacturer11);

        productService.createProduct(bed);
        productService.createProduct(closet);
        productService.createProduct(chair);
        productService.createProduct(tv);
        productService.createProduct(door);
        productService.createProduct(chandelier);


        User user1 = new User();
        user1.setUsername("Дмитрий");
        user1.setEmail("Bequitebtw@mail.ru");
        user1.setPassword("123123123");
        user1.setEnabled(true);

        User user2 = new User();
        user2.setUsername("Алексей");
        user2.setEmail("comeback@mail.ru");
        user2.setPassword("123123123");
        user2.setEnabled(true);

        User user3 = new User();
        user3.setUsername("Лиза");
        user3.setEmail("danilkina25.09@gmail.com");
        user3.setPassword("123123123");
        user3.setEnabled(true);

        userService.createAdmin(user1);
        userService.createAdmin(user2);
        userService.createUser(user3);


    }
}
