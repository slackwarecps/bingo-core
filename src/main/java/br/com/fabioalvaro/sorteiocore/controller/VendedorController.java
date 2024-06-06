package br.com.fabioalvaro.sorteiocore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fabioalvaro.sorteiocore.dominio.Vendedor;
import br.com.fabioalvaro.sorteiocore.service.VendedorService;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @PostMapping
    public ResponseEntity<Vendedor> createVendedor(@RequestBody Vendedor vendedor) {
        Vendedor createdVendedor = vendedorService.saveVendedor(vendedor);
        return ResponseEntity.ok(createdVendedor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedorById(@PathVariable String id) {
        Vendedor vendedor = vendedorService.getVendedorById(id);
        return ResponseEntity.ok(vendedor);
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> getAllVendedores() {
        List<Vendedor> vendedores = vendedorService.getAllVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable String id, @RequestBody Vendedor vendedorDetails) {
        Vendedor updatedVendedor = vendedorService.updateVendedor(id, vendedorDetails);
        return ResponseEntity.ok(updatedVendedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        vendedorService.deleteVendedor(id);
        return ResponseEntity.noContent().build();
    }
}