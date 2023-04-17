package com.example.springbootpetstore.service;

import com.example.springbootpetstore.dao.TransactionMapper;
import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 皮皮皮
 * @date 2023/3/31 12:59
 */
@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionMapper transactionMapper;

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionMapper.getAllTransaction();
    }

    @Override
    public List<Transaction> getTransactionsByUserID(User user) {
        return  transactionMapper.getTransactionsByUserID(user);
    }

    @Override
    public void createTransaction(Transaction transaction) {

    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transactionMapper.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionMapper.deleteTransaction(transaction);
    }
}
