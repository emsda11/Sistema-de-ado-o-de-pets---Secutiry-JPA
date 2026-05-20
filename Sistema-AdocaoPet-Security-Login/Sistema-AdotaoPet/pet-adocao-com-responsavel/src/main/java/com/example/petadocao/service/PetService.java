package com.example.petadocao.service;

import com.example.petadocao.model.Adotante;
import com.example.petadocao.model.Pet;
import com.example.petadocao.repository.AdotanteRepository;
import com.example.petadocao.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final AdotanteRepository adotanteRepository;

    public PetService(PetRepository petRepository, AdotanteRepository adotanteRepository) {
        this.petRepository = petRepository;
        this.adotanteRepository = adotanteRepository;
    }

    public List<Pet> listarPets() {
        return petRepository.findAll();
    }

    public Pet buscarPetPorId(int id) {
        return petRepository.findById(id).orElse(null);
    }

    public String cadastrarPet(Pet pet) {
        if (pet.getNome() == null || pet.getNome().isBlank()) {
            return "Informe o nome do pet.";
        }

        if (pet.getTipo() == null || pet.getTipo().isBlank()) {
            return "Informe o tipo do pet.";
        }

        pet.setId(null);
        pet.setStatus("Disponível");
        pet.setAdotadoPor(null);

        if (pet.getFotoUrl() == null || pet.getFotoUrl().isBlank()) {
            pet.setFotoUrl("https://placedog.net/500/350?id=" + System.currentTimeMillis());
        }

        petRepository.save(pet);
        return "Pet cadastrado com sucesso!";
    }

    public String adotarPet(int id, Adotante adotante) {
        Pet pet = buscarPetPorId(id);

        if (pet == null) {
            return "Pet não encontrado.";
        }

        if ("Adotado".equalsIgnoreCase(pet.getStatus())) {
            return "Esse pet já foi adotado por " + pet.getAdotadoPor() + ".";
        }

        if (adotante == null || adotante.getNome() == null || adotante.getNome().isBlank()) {
            return "Informe o nome do adotante.";
        }

        if (adotante.getTelefone() == null || adotante.getTelefone().isBlank()) {
            return "Informe o telefone do adotante.";
        }

        adotante.setId(null);
        adotante.setIdPetDesejado(pet.getId());
        adotanteRepository.save(adotante);

        pet.setStatus("Adotado");
        pet.setAdotadoPor(adotante.getNome());
        petRepository.save(pet);

        return "O pet " + pet.getNome() + " foi adotado por " + adotante.getNome() + "!";
    }

    public String editarPet(int id, Pet petAtualizado) {
        Pet pet = buscarPetPorId(id);

        if (pet == null) {
            return "Pet não encontrado.";
        }

        if (petAtualizado.getNome() == null || petAtualizado.getNome().isBlank()) {
            return "Informe o nome do pet.";
        }

        if (petAtualizado.getTipo() == null || petAtualizado.getTipo().isBlank()) {
            return "Informe o tipo do pet.";
        }

        pet.setNome(petAtualizado.getNome());
        pet.setTipo(petAtualizado.getTipo());
        pet.setIdade(petAtualizado.getIdade());

        if (petAtualizado.getFotoUrl() != null && !petAtualizado.getFotoUrl().isBlank()) {
            pet.setFotoUrl(petAtualizado.getFotoUrl());
        }

        if (petAtualizado.getStatus() != null && !petAtualizado.getStatus().isBlank()) {
            pet.setStatus(petAtualizado.getStatus());
        }

        petRepository.save(pet);
        return "Pet atualizado com sucesso!";
    }

    public String removerPet(int id) {
        Pet pet = buscarPetPorId(id);

        if (pet == null) {
            return "Pet não encontrado.";
        }

        petRepository.delete(pet);
        return "Pet removido com sucesso!";
    }
}
