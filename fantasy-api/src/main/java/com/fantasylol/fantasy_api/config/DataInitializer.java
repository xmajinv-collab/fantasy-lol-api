package com.fantasylol.fantasy_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Role;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.LigaRepository;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;
import com.fantasylol.fantasy_api.service.PandaScoreService;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(DataInitializer.class);

    private final LigaRepository ligaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PandaScoreService pandaScoreService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(LigaRepository ligaRepository,
                           UsuarioRepository usuarioRepository,
                           PandaScoreService pandaScoreService,
                           PasswordEncoder passwordEncoder) {

        this.ligaRepository = ligaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pandaScoreService = pandaScoreService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        logger.info("Inicializando sistema Fantasy LoL...");

        // ==============================
        // CREAR ADMIN
        // ==============================

        Usuario admin = usuarioRepository
                .findByUsername("admin")
                .orElseGet(() -> {

                    logger.info("Creando usuario administrador...");

                    Usuario nuevo = new Usuario(
                            "admin",
                            passwordEncoder.encode("admin"),
                            Role.ROLE_ADMIN
                    );

                    return usuarioRepository.save(nuevo);
                });

        // ==============================
        // CREAR LIGA BASE
        // ==============================

        if (ligaRepository.count() == 0) {

            logger.info("Creando liga inicial...");

            Liga liga = new Liga();
            liga.setNombre("Liga LEC Oficial");
            liga.setUsuario(admin);

            ligaRepository.save(liga);

            // ==============================
            // CARGAR JUGADORES LEC
            // ==============================

            logger.info("Cargando jugadores desde PandaScore...");

            //pandaScoreService.cargarJugadoresLEC(liga);

            logger.info("Sistema inicializado correctamente.");
        } else {

            logger.info("La base de datos ya contiene ligas. No se inicializa.");
        }
    }
}