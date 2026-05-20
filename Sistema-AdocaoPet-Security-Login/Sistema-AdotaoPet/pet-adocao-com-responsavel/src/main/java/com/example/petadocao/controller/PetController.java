package com.example.petadocao.controller;

import com.example.petadocao.model.Pet;
import com.example.petadocao.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/")
    public String paginaInicial() {
        return "Sistema de Adoção de Pets funcionando! Acesse /index.html";
    }

    @GetMapping("/pets")
    public List<Pet> listarPets() {
        return petService.listarPets();
    }

    @GetMapping("/pets/{id}")
    public Object buscarPetPorId(@PathVariable int id) {
        Pet pet = petService.buscarPetPorId(id);

        if (pet == null) {
            return "Pet não encontrado.";
        }

        return pet;
    }

    @PostMapping("/pets")
    public String cadastrarPet(@RequestBody Pet pet) {
        return petService.cadastrarPet(pet);
    }

    @PutMapping("/pets/{id}/adotar")
    public String adotarPet(@PathVariable int id, @RequestBody com.example.petadocao.model.Adotante adotante) {
        return petService.adotarPet(id, adotante);
    }

    @PutMapping("/pets/{id}")
    public String editarPet(@PathVariable int id, @RequestBody Pet petAtualizado) {
        return petService.editarPet(id, petAtualizado);
    }

    @DeleteMapping("/pets/{id}")
    public String removerPet(@PathVariable int id) {
        return petService.removerPet(id);
    }
}
