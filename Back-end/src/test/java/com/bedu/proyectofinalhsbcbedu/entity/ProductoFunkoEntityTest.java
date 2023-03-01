package com.bedu.proyectofinalhsbcbedu.entity;

import com.bedu.proyectofinalhsbcbedu.repository.IProductoFunkoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class ProductoFunkoEntityTest {
    private ProductoFunkoEntity funkoEntity;
    private ProductoFunkoEntity savedFunko;
    private ProductoFunkoEntity existFunko;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IProductoFunkoRepository funkoRepository;

    public AwsImageFunkoEntity implementaImage(){
        return AwsImageFunkoEntity.builder()
                .name("Mock Image")
                .awsUrl("Mock Url")
                .build();
    }

    public ProductoFunkoEntity implementaFunko(){
        return ProductoFunkoEntity.builder()
                .layaway(5)
                .name("Funko Test")
                .price(5000)
                .stock(10)
                .awsImageFunko(implementaImage())
                .build();
    }

    @BeforeEach
    void setUp() {
        savedFunko = new ProductoFunkoEntity();
        existFunko = new ProductoFunkoEntity();
        funkoEntity = new ProductoFunkoEntity();
    }

    @Test
    @DisplayName("Crear Funko")
    void testCrearFunko(){
        savedFunko = funkoRepository.save(implementaFunko());
        existFunko = entityManager.find(ProductoFunkoEntity.class, savedFunko.getId());
        assertEquals(existFunko.getName(), implementaFunko().getName());
    }

    @Test
    @DisplayName("Modifica Funko")
    void testModificaFunko(){
        funkoEntity.setName("Updated Test Funko");
        savedFunko = funkoRepository.save(implementaFunko());
        existFunko = entityManager.find(ProductoFunkoEntity.class, savedFunko.getId());
        assertNotEquals(existFunko.getName(), funkoEntity.getName());
    }
}