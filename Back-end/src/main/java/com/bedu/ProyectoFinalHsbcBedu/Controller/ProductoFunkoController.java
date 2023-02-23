package com.bedu.proyectofinalhsbcbedu.controller;

import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.service.IProductoFunkoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos/funkos")
@RequiredArgsConstructor
public class ProductoFunkoController {
    private final IProductoFunkoService funkoService;

    @GetMapping
    public ResponseEntity<List<ProductoFunkoDTO>> getAllFunkos(){
        return ResponseEntity.ok().body(funkoService.getAllFunkos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductoFunkoDTO>> getFunkoById(@PathVariable("id") long id) throws Exception{
        return ResponseEntity.ok().body(funkoService.getFunkoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoFunkoDTO> createFunko(@Valid @RequestBody ProductoFunkoDTO funkoDTO) throws Exception {
        funkoService.createFunko(funkoDTO);
        return ResponseEntity.created(URI.create("0")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoFunkoDTO> updateFunko(@Valid @PathVariable("id") long id, @Valid @RequestBody ProductoFunkoDTO funkoDTO) throws Exception{
        funkoService.updateFunko(id, funkoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoFunkoDTO> deleteFunko(@PathVariable("id") long id) throws Exception{
        funkoService.deleteFunko(id);
        return ResponseEntity.noContent().build();
    }

}
