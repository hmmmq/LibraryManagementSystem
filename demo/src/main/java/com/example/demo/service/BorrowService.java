package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import com.example.demo.entity.BorrowItem;
import com.example.demo.entity.User;
import com.example.demo.mapper.BorrowItemMapper;
import com.example.demo.mapper.BorrowMapper;
import com.example.demo.util.DateCalculator;
import com.example.demo.util.IdCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    BorrowItemMapper borrowItemMapper;
    @Autowired
    IdCalculator IdCalculator;
    @Autowired
    DateCalculator dateCalculator;

    public void borrowBook(User user, List<Book> books) {
        Borrow borrow = new Borrow();
        borrow.setUserid(user.getIduser());
        borrow.setUsername(user.getName());
        String date = dateCalculator.calculateDate();
        borrow.setBorrowdate(date);
        borrow.setIdborrow(IdCalculator.calculateUserId(user.getName(), date));
        int total_amount = 0;
        for (Book b : books) {
            BorrowItem borrowItem = new BorrowItem();
            borrowItem.setIdborrowitem(IdCalculator.calculateUserId(String.valueOf(borrow.getIdborrow()), String.valueOf(b.getIdbook())));
            borrowItem.setBookid(b.getIdbook());
            borrowItem.setBookname(b.getBookname());
            borrowItem.setBookdescription(b.getDescription());
            borrowItem.setAmount(1);
            borrowItem.setBookdescription(b.getDescription());
            borrowItem.setParentid(borrow.getIdborrow());
            borrowItem.setUsername(user.getName());
            borrowItem.setUserid(user.getIduser());
            borrowItem.setBorrowdate(date);
            borrowItemMapper.insert(borrowItem);
            total_amount+= 1;
        }
        borrow.setTotalamount(total_amount);
        borrowMapper.insert(new Borrow());
        System.out.println("Book borrowed");
    }
    public void returnBook(User user,List<Book> books){
        List<BorrowItem> borrowItems = borrowItemMapper.selectAll();
        for(BorrowItem borrowItem:borrowItems){
            if(borrowItem.getUserid() == user.getIduser()){
                for(Book book:books){
                    if(borrowItem.getBookid() == book.getIdbook()){
                        borrowItemMapper.deleteByPrimaryKey(borrowItem.getIdborrowitem());
                        System.out.println("Book returned");
                    }
                }
            }
        }
    }
    public void checkBorrow(User user){
        List<BorrowItem> borrowItems = borrowItemMapper.selectAll();
        for(BorrowItem borrowItem:borrowItems){
            if(borrowItem.getUserid() == user.getIduser()){
                System.out.println(borrowItem);
            }
        }

    }

}

