package com.sobrelanches.repository;

import com.sobrelanches.model.ProdutoRemovido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRemovidoRepository extends JpaRepository<ProdutoRemovido, Long> {
}
