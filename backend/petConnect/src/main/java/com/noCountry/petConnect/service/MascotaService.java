package com.noCountry.petConnect.service;

import com.noCountry.petConnect.model.Mascota;
import com.noCountry.petConnect.dto.MascotaDTO;
import com.noCountry.petConnect.model.Usuario;
import com.noCountry.petConnect.repository.MascotaRepository;
import com.noCountry.petConnect.repository.UsuarioRepository;
import com.noCountry.petConnect.infra.errores.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Mascota> getAllMascotas() {
        List<Mascota> mascotas = mascotaRepository.findAll();
        if (mascotas.isEmpty()) {
            throw new ResourceNotFoundException("No hay mascotas para mostrar");
        }
        return mascotas;
    }

    public Mascota getMascotaById(long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota con el id: " + id + " no encontrada"));
    }

    @Transactional
    public Mascota createMascota(MascotaDTO mascotaDTO) {
        Mascota mascota = mapToEntity(mascotaDTO);

        if (mascotaDTO.getPropietarioId() != null) {
            Usuario propietario = usuarioRepository.findById(mascotaDTO.getPropietarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + mascotaDTO.getPropietarioId()));
            mascota.setDueño(propietario);
        } else {
            throw new IllegalArgumentException("Se requiere un ID de propietario para crear una mascota");
        }

        return mascotaRepository.save(mascota);
    }

    @Transactional
    public Mascota updateMascota(long id, MascotaDTO mascotaDTO) {
        Mascota existingMascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota con el id: " + id + " no encontrada"));

        existingMascota.setNombre(mascotaDTO.getNombre());
        existingMascota.setEspecie(mascotaDTO.getEspecie());
        existingMascota.setRaza(mascotaDTO.getRaza());
        existingMascota.setEdad(mascotaDTO.getEdad());
        existingMascota.setSexo(mascotaDTO.getSexo());
        existingMascota.setColor(mascotaDTO.getColor());
        existingMascota.setNecesidadesEspeciales(mascotaDTO.getNecesidadesEspeciales());
        existingMascota.setVacunado(mascotaDTO.getVacunado());
        existingMascota.setEsterilizado(mascotaDTO.getEsterilizado());
        existingMascota.setEstado(mascotaDTO.getEstado());
        existingMascota.setFotoPrincipalUrl(mascotaDTO.getFotoPrincipalUrl());
        existingMascota.setFotosExtra(mascotaDTO.getFotosExtra());

        return mascotaRepository.save(existingMascota);
    }

    public boolean deleteMascota(long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("Mascota no encontrada");
        }
    }

    private Mascota mapToEntity(MascotaDTO mascotaDTO) {
        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setEdad(mascotaDTO.getEdad());
        mascota.setSexo(mascotaDTO.getSexo());
        mascota.setColor(mascotaDTO.getColor());
        mascota.setNecesidadesEspeciales(mascotaDTO.getNecesidadesEspeciales());
        mascota.setVacunado(mascotaDTO.getVacunado());
        mascota.setEsterilizado(mascotaDTO.getEsterilizado());
        mascota.setEstado(mascotaDTO.getEstado());
        mascota.setFotoPrincipalUrl(mascotaDTO.getFotoPrincipalUrl());
        mascota.setFotosExtra(mascotaDTO.getFotosExtra());

        return mascota;
    }
}
