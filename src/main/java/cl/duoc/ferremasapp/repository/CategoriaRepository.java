package cl.duoc.ferremasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ferremasapp.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {}
