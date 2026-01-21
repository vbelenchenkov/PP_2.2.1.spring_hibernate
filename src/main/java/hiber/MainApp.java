package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Toyota", 789);
      Car car2 = new Car("Audi", 110);
      Car car3 = new Car("KIA",456);
      Car car4 = new Car("GMC", 1876);


      userService.addUser(new User("User1", "Lastname1", "user1@mail.ru", car1));
      userService.addUser(new User("User2", "Lastname2", "user2@mail.ru", car2));
      userService.addUser(new User("User3", "Lastname3", "user3@mail.ru", car3));
      userService.addUser(new User("User4", "Lastname4", "user4@mail.ru", car4));

      List<User> users = userService.findUsers();
      for (User user : users) {
          System.out.println(user);
         if (user.getCar() != null) {
             System.out.println(user.getCar());
         }
         System.out.println();
      }

      String searchModel = "Toyota";
      int searchSeries = 789;

      User user = userService.getUserByCar(searchModel, searchSeries);
      if(user != null) {
          System.out.println(user + " is having the car: " + searchModel + ", " +searchSeries);
      } else {
          System.out.println("The user with car model: " + searchModel
                  + " and the car series: " + searchSeries + " has not be found");
      }
      context.close();
   }
}
