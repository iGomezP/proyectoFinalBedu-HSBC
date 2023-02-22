package com.bedu.ProyectoFinalHsbcBedu.Repository;

import com.bedu.ProyectoFinalHsbcBedu.Entity.ProductoFunkoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoFunkoRepository extends JpaRepository<ProductoFunkoEntity, Long> {
    ProductoFunkoEntity findOneByName(String name);
}
