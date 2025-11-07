package co.edu.veterinaria.mascotas.infrastructure.web.mapper;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.infrastructure.web.dto.MascotaDTO;
import org.springframework.stereotype.Component;

@Component
public class MascotaMapper {
    
    public MascotaDTO toDTO(Mascota mascota) {
        if (mascota == null) {
            return null;
        }
        
        return MascotaDTO.builder()
            .id(mascota.getId())
            .idPropietario(mascota.getIdPropietario())
            .idEspecie(mascota.getIdEspecie())
            .idRaza(mascota.getIdRaza())
            .nombre(mascota.getNombre())
            .fechaNacimiento(mascota.getFechaNacimiento())
            .sexo(mascota.getSexo())
            .color(mascota.getColor())
            .microchip(mascota.getMicrochip())
            .pesoKg(mascota.getPesoKg())
            .esterilizado(mascota.getEsterilizado())
            .activo(mascota.getActivo())
            .build();
    }
    
    public Mascota toEntity(MascotaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Mascota.builder()
            .id(dto.getId())
            .idPropietario(dto.getIdPropietario())
            .idEspecie(dto.getIdEspecie())
            .idRaza(dto.getIdRaza())
            .nombre(dto.getNombre())
            .fechaNacimiento(dto.getFechaNacimiento())
            .sexo(dto.getSexo())
            .color(dto.getColor())
            .microchip(dto.getMicrochip())
            .pesoKg(dto.getPesoKg())
            .esterilizado(dto.getEsterilizado())
            .activo(dto.getActivo() != null ? dto.getActivo() : true)
            .build();
    }
}

