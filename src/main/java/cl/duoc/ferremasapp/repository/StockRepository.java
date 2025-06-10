package cl.duoc.ferremasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ferremasapp.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {}
