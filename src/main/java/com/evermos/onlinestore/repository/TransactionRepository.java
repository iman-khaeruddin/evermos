package com.evermos.onlinestore.repository;

import com.evermos.onlinestore.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
