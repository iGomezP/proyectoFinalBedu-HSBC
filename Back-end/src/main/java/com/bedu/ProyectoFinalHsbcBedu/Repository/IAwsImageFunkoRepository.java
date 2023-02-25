package com.bedu.proyectofinalhsbcbedu.repository;

import com.bedu.proyectofinalhsbcbedu.entity.AwsImageFunkoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAwsImageFunkoRepository extends JpaRepository<AwsImageFunkoEntity, Long> {
     void deleteAllByName(String name);
}
