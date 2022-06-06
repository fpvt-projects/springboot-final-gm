package com.gm.transaction.transactionservice.repository;

import com.gm.transaction.transactionservice.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    boolean existsByEmail(String email);
    Transaction findByEmail(String email);
}
