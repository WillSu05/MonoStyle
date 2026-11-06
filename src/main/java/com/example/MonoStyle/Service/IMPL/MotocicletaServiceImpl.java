package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.MotocicletaDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Marca;
import com.example.MonoStyle.Entity.Motocicleta;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.MarcaRepository;
import com.example.MonoStyle.Repository.MotocicletaRepository;
import com.example.MonoStyle.Service.MotocicletaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MotocicletaServiceImpl implements MotocicletaService {

    private final MotocicletaRepository motocicletaRepository;
    private final MarcaRepository marcaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public List<MotocicletaDTO> listarMotocicletas() {
        return motocicletaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public MotocicletaDTO obtenerMotocicletaPorId(Long id) {
        Motocicleta moto = motocicletaRepository.findById(id).orElseThrow(() -> new RuntimeException("Motocicleta no encontrada"));
        return convertirADTO(moto);
    }

    @Override
    public MotocicletaDTO crearMotocicleta(MotocicletaDTO dto) {
        Marca marca = marcaRepository.findById(dto.getIdMarca()).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Motocicleta moto = Motocicleta.builder()
                .modelo(dto.getModelo()).año(dto.getAño())
                .placa(dto.getPlaca()).color(dto.getColor())
                .marca(marca).cliente(cliente).build();
        return convertirADTO(motocicletaRepository.save(moto));
    }

    @Override
    public MotocicletaDTO actualizarMotocicleta(Long id, MotocicletaDTO dto) {
        Motocicleta moto = motocicletaRepository.findById(id).orElseThrow(() -> new RuntimeException("Motocicleta no encontrada"));
        Marca marca = marcaRepository.findById(dto.getIdMarca()).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        moto.setModelo(dto.getModelo());
        moto.setAño(dto.getAño());
        moto.setPlaca(dto.getPlaca());
        moto.setColor(dto.getColor());
        moto.setMarca(marca);
        moto.setCliente(cliente);

        return convertirADTO(motocicletaRepository.save(moto));
    }

    @Override
    public void eliminarMotocicleta(Long id) {
        motocicletaRepository.deleteById(id);
    }

    private MotocicletaDTO convertirADTO(Motocicleta m) {
        return MotocicletaDTO.builder().idMotocicleta(m.getIdMotocicleta()).modelo(m.getModelo())
                .año(m.getAño()).placa(m.getPlaca()).color(m.getColor())
                .idMarca(m.getMarca().getIdMarca()).idCliente(m.getCliente().getIdCliente()).build();
    }
}
