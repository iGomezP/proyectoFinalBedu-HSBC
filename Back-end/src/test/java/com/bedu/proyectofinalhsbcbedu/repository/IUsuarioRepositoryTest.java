package com.bedu.proyectofinalhsbcbedu.repository;

import com.bedu.proyectofinalhsbcbedu.entity.ERole;
import com.bedu.proyectofinalhsbcbedu.entity.UsuarioEntity;
import com.bedu.proyectofinalhsbcbedu.entity.DireccionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class IUsuarioRepositoryTest {
    private UsuarioEntity usuarioEntity;
    private UsuarioEntity existUsuario;
    private UsuarioEntity savedUsuario;
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public DireccionEntity implementaDireccion(){
        DireccionEntity newDireccion = new DireccionEntity();
        newDireccion.setCalle("Calle 1");
        newDireccion.setCiudad("Neza");
        newDireccion.setNumeroExterior("500");
        newDireccion.setNumeroInterior("n/a");
        newDireccion.setEstado("Mexico");
        newDireccion.setCodigoPostal(57800);
        newDireccion.setColonia("Siempre Viva");

        return newDireccion;
    }

    public UsuarioEntity implementaUsuario(){
        return UsuarioEntity.builder()
                .direccion(implementaDireccion())
                .email("mail@mail.com")
                .name("Ignacio")
                .rol(ERole.USER)
                .userPassword("Contra@123")
                .build();
    }

    @BeforeEach
    void setUp() {
        savedUsuario = new UsuarioEntity();
        existUsuario = new UsuarioEntity();
        usuarioEntity = new UsuarioEntity();
    }

    @Test
    @DisplayName("Crea Cliente")
    void testCrearCliente(){
        savedUsuario = usuarioRepository.save(implementaUsuario());
        existUsuario = entityManager.find(UsuarioEntity.class, savedUsuario.getId());
        assertEquals(existUsuario.getName(), implementaUsuario().getName());
    }

    @Test
    @DisplayName("Modifica Cliente Correo")
    void testModificaClienteCorreo(){
        usuarioEntity.setEmail("correo@correo.com");
        savedUsuario = usuarioRepository.save(implementaUsuario());
        existUsuario = entityManager.find(UsuarioEntity.class, savedUsuario.getId());
        assertNotEquals(existUsuario.getEmail(), usuarioEntity.getEmail());
    }

    @Test
    @DisplayName("Verifica email null")
    void testNullEmail(){
        var newUsuario = UsuarioEntity.builder()
                .direccion(implementaUsuario().getDireccion())
                .email(null)
                .name(implementaUsuario().getName())
                .userPassword("Contra@123")
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> savedUsuario = usuarioRepository.save(newUsuario));
    }

    @Test
    @DisplayName("Verifica nombre null")
    void testNullName(){
        var newUsuario = UsuarioEntity.builder()
                .direccion(implementaUsuario().getDireccion())
                .email(implementaUsuario().getEmail())
                .name(null)
                .userPassword("Contra@123")
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> savedUsuario = usuarioRepository.save(newUsuario));
    }

    @Test
    @DisplayName("Verifica password null")
    void testNullPassword(){
        var newUsuario = UsuarioEntity.builder()
                .direccion(implementaUsuario().getDireccion())
                .email(implementaUsuario().getEmail())
                .name(implementaUsuario().getName())
                .userPassword(null)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> savedUsuario = usuarioRepository.save(newUsuario));
    }
}