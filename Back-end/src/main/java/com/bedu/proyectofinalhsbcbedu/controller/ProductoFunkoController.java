package com.bedu.proyectofinalhsbcbedu.controller;

import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.service.IProductoFunkoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductoFunkoDTO> createFunko(@RequestParam("dataFunko") String funkoJson, @RequestParam("imageFunko") MultipartFile imageFunko) throws Exception {
        funkoService.createFunko(funkoJson, imageFunko);
        return ResponseEntity.created(URI.create("0")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoFunkoDTO> updateFunko(@Valid @PathVariable("id") long id, @RequestParam("dataFunko") String funkoJson, @RequestParam("imageFunko") MultipartFile imageFunko) throws Exception{
        funkoService.updateFunko(id, funkoJson,imageFunko);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoFunkoDTO> deleteFunko(@PathVariable("id") long id) throws Exception{
        funkoService.deleteFunko(id);
        return ResponseEntity.noContent().build();
    }

}
