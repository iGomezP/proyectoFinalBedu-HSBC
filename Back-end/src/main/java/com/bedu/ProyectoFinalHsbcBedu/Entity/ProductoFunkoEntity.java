package com.bedu.ProyectoFinalHsbcBedu.Entity;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "funkos")
public class ProductoFunkoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(nullable = false, length = 80, unique = true)
    @CsvBindByName(column = "Nombre")
    private String name;
    @CsvBindByName(column = "Precio")
    private int price;
    @CsvBindByName(column = "Piezas")
    private int stock;
    @CsvBindByName(column = "Apartado")
    private int layaway;
    @CreationTimestamp
    private LocalDateTime create_date;
    @UpdateTimestamp
    private LocalDateTime last_update;
}
