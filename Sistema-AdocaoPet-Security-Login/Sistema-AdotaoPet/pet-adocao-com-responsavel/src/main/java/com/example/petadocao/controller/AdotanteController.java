package com.example.petadocao.controller;

import com.example.petadocao.model.Adotante;
import com.example.petadocao.service.AdotanteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdotanteController {

    private final AdotanteService adotanteService;

    public AdotanteController(AdotanteService adotanteService) {
        this.adotanteService = adotanteService;
    }

    @GetMapping("/adotantes")
    public List<Adotante> listarAdotantes() {
        return adotanteService.listarAdotantes();
    }

    @GetMapping("/adotantes/{id}")
    public Object buscarAdotantePorId(@PathVariable int id) {
        Adotante adotante = adotanteService.buscarAdotantePorId(id);

        if (adotante == null) {
            return "Adotante não encontrado.";
        }

        return adotante;
    }

    @PostMapping("/adotantes")
    public String cadastrarAdotante(@RequestBody Adotante adotante) {
        return adotanteService.cadastrarAdotante(adotante);
    }

    @PutMapping("/adotantes/{id}")
    public String editarAdotante(@PathVariable int id, @RequestBody Adotante adotanteAtualizado) {
        return adotanteService.editarAdotante(id, adotanteAtualizado);
    }

    @DeleteMapping("/adotantes/{id}")
    public String removerAdotante(@PathVariable int id) {
        return adotanteService.removerAdotante(id);
    }
}
