package com.bedu.proyectofinalhsbcbedu.entity;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_funko_id", nullable = false)
    private AwsImageFunkoEntity awsImageFunko;
}
