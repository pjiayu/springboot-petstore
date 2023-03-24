package com.example.springbootpetstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @auther 齿轮
 * @create 2022-10-26-16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    private long TransactionID;
    private long UserId;
    private String specificInfo;
    private String state;
}
