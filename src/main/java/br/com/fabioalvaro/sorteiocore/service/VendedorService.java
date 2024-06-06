package br.com.fabioalvaro.sorteiocore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.sorteiocore.dominio.Vendedor;
import br.com.fabioalvaro.sorteiocore.repository.VendedorRepository;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public Vendedor saveVendedor(Vendedor vendedor) {

        vendedor.setCreatedAt(LocalDateTime.now());
        return vendedorRepository.save(vendedor);
    }

    public Optional<Vendedor> buscarVendedorById(String id) {
        return vendedorRepository.findById(id);
    }

    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    public Vendedor updateVendedor(String id, Vendedor vendedorDetails) {
        Optional<Vendedor> optionalVendedor = vendedorRepository.findById(id);
        if (optionalVendedor.isPresent()) {
            Vendedor vendedor = optionalVendedor.get();
            vendedor.setNome(vendedorDetails.getNome());
            vendedor.setSaldoVendido(vendedorDetails.getSaldoVendido());
            return vendedorRepository.save(vendedor);
        } else {
            return null;
        }
    }

    public void deleteVendedor(String id) {
        vendedorRepository.deleteById(id);
    }
}