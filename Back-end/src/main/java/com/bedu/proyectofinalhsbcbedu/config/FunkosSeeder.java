package com.bedu.proyectofinalhsbcbedu.config;


import com.bedu.proyectofinalhsbcbedu.entity.ProductoFunkoEntity;
import com.bedu.proyectofinalhsbcbedu.repository.IProductoFunkoRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class FunkosSeeder implements CommandLineRunner {

    private final IProductoFunkoRepository funkoRepository;

    @Override
    public void run(String... args) throws Exception {
    // Generar lista a partir del CSV
        // Ruta para servidor remoto: /opt/tomcat/apache-tomcat/webapps/docs/Anime.csv
        // Ruta para servidor local y jar: ./csv/Anime.csv
        var file = new FileReader("/opt/tomcat/apache-tomcat/webapps/docs/Anime.csv");
        var csvBuilder = new CsvToBeanBuilder<ProductoFunkoEntity>(file);
        List<ProductoFunkoEntity> nuevosFunkos = csvBuilder
                .withType(ProductoFunkoEntity.class)
                .build()
                .parse();
        log.info("Insertando valores iniciales...");

        for (ProductoFunkoEntity funko : new ArrayList<>(nuevosFunkos)){
            // Buscar por nombre
            ProductoFunkoEntity existeFunko = funkoRepository.findOneByName(funko.getName());
            // Si existe, quitar de la lista original
            if(existeFunko != null){
                nuevosFunkos.remove(funko);
            }
        }
        if (nuevosFunkos.isEmpty()){
            log.warn("Los productos ya se encuentran agregados a la BD.");
        } else {
            log.info("Productos agregados exitosamente.");
        }
        funkoRepository.saveAll(nuevosFunkos);
    }
}
