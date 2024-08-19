package com.example.demo;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import com.example.demo.util.IdCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        boolean login_flag = false;
        User user = new User();
        while (!login_flag) {
            System.out.println("欢迎来到图书管理系统");
            System.out.println("1.用户登录");
            System.out.println("2.用户注册");
            System.out.println("3.退出");
            System.out.println("请输入你的选择：");
            UserService userService = new UserService();
            IdCalculator userIdCalculator = new IdCalculator();
            // 读取用户输入
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入用户名和密码：");
                    String name = scanner.next();
                    String password = scanner.next();
                    System.out.println("用户登录");
                    // 调用UserService的login方法
                    user.setName(name);
                    user.setPassword(password);
                    user.setIduser(userIdCalculator.calculateUserId(name, password));
                    boolean result = userService.login(user);
                    if (result) {
                        login_flag = true;
                        System.out.println("登录成功");
                    } else {
                        System.out.println("登录失败");
                    }

                    break;
                case 2:
                    System.out.println("用户注册");
                    System.out.println("请输入用户名和密码：");
                    name = scanner.next();
                    password = scanner.next();
                    user.setName(name);
                    user.setPassword(password);
                    System.out.println("是否为管理员？输入数字(1.是 0.否)");
                    int admin = scanner.nextInt();
                    //检验admin是不是0或1
                    while (admin != 0 && admin != 1) {
                        System.out.println("输入错误，请重新输入");
                        admin = scanner.nextInt();
                    }
                    user.setAdmin(admin);
                    user.setIduser(userIdCalculator.calculateUserId(name, password));
                    userService = new UserService();
                    login_flag = userService.register(user);
                    if (login_flag) {
                        System.out.println("注册成功");
                    } else {
                        System.out.println("注册失败,用户已存在");
                    }
                    break;
                case 3:
                    System.out.println("退出");
                    break;
                default:
                    System.out.println("输入错误");
            }
        }

        BookService bookService = new BookService();
        BorrowService borrowService = new BorrowService();
        List<Book> booklist = bookService.getAllBooks();
        HashMap<Integer,Book> booklist2 = new HashMap<>();
        for (Book book : booklist) {
            booklist2.put(book.getIdbook(),book);
        }

        while(true) {
            System.out.println("成功登录图书管理系统");
            System.out.println("1.查看图书");
            System.out.println("2.借阅图书");
            System.out.println("3.归还图书");
            System.out.println("4.退出");
            System.out.println("请输入你的选择：");
            // 读取用户输入
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("查看图书列表");
                    for (Book book : booklist) {
                        System.out.println("图书编号：" + book.getIdbook() + " 书名：" + book.getBookname() + " 详情：" + book.getDescription());
                    }
                    break;
                case 2:
                    System.out.println("借阅图书");
                    System.out.println("请输入要借阅的图书编号：用分号分割");
                    String[] bookIds = scanner.next().split(";");
                    List<Book> books = new ArrayList<>();
                    for (String bookId : bookIds) {
                        books.add(booklist2.get(Integer.parseInt(bookId)));
                    }
                    borrowService.borrowBook(user, books);
                    break;
                case 3:
                    System.out.println("归还图书");
                    borrowService.checkBorrow(user);
                    System.out.println("请输入要借阅的图书编号：用分号分割");
                    String[] bookIds2 = scanner.next().split(";");
                    List<Book> books2 = new ArrayList<>();
                    for (String bookId : bookIds2) {
                        books2.add(booklist2.get(Integer.parseInt(bookId)));
                    }
                    borrowService.returnBook(user, books2);
                    break;
                case 4:
                    System.out.println("退出");
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入错误");
            }

        }



    }

}
