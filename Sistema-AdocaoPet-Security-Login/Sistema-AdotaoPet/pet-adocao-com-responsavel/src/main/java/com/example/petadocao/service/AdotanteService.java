package com.example.petadocao.service;

import com.example.petadocao.model.Adotante;
import com.example.petadocao.model.Pet;
import com.example.petadocao.repository.AdotanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdotanteService {

    private final AdotanteRepository adotanteRepository;
    private final PetService petService;

    public AdotanteService(AdotanteRepository adotanteRepository, PetService petService) {
        this.adotanteRepository = adotanteRepository;
        this.petService = petService;
    }

    public List<Adotante> listarAdotantes() {
        return adotanteRepository.findAll();
    }

    public Adotante buscarAdotantePorId(int id) {
        return adotanteRepository.findById(id).orElse(null);
    }

    public String cadastrarAdotante(Adotante adotante) {
        if (adotante.getNome() == null || adotante.getNome().isBlank()) {
            return "Informe o nome do adotante.";
        }

        if (adotante.getTelefone() == null || adotante.getTelefone().isBlank()) {
            return "Informe o telefone do adotante.";
        }

        if (adotante.getIdPetDesejado() == null) {
            return "Informe o ID do pet desejado.";
        }

        Pet pet = petService.buscarPetPorId(adotante.getIdPetDesejado());

        if (pet == null) {
            return "Pet desejado não encontrado.";
        }

        adotante.setId(null);
        adotanteRepository.save(adotante);

        return "Adotante cadastrado com sucesso!";
    }

    public String editarAdotante(int id, Adotante adotanteAtualizado) {
        Adotante adotante = buscarAdotantePorId(id);

        if (adotante == null) {
            return "Adotante não encontrado.";
        }

        if (adotanteAtualizado.getNome() == null || adotanteAtualizado.getNome().isBlank()) {
            return "Informe o nome do adotante.";
        }

        if (adotanteAtualizado.getTelefone() == null || adotanteAtualizado.getTelefone().isBlank()) {
            return "Informe o telefone do adotante.";
        }

        if (adotanteAtualizado.getIdPetDesejado() == null) {
            return "Informe o ID do pet desejado.";
        }

        Pet pet = petService.buscarPetPorId(adotanteAtualizado.getIdPetDesejado());

        if (pet == null) {
            return "Pet desejado não encontrado.";
        }

        adotante.setNome(adotanteAtualizado.getNome());
        adotante.setTelefone(adotanteAtualizado.getTelefone());
        adotante.setCidade(adotanteAtualizado.getCidade());
        adotante.setIdPetDesejado(adotanteAtualizado.getIdPetDesejado());

        adotanteRepository.save(adotante);
        return "Adotante atualizado com sucesso!";
    }

    public String removerAdotante(int id) {
        Adotante adotante = buscarAdotantePorId(id);

        if (adotante == null) {
            return "Adotante não encontrado.";
        }

        adotanteRepository.delete(adotante);
        return "Adotante removido com sucesso!";
    }
}
