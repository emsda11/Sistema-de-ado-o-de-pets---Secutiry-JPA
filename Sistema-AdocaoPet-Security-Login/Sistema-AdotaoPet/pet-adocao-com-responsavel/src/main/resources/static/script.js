const apiPets = "http://localhost:8080/pets";
const apiAdotantes = "http://localhost:8080/adotantes";

let listaDePets = [];

function listarPets() {
    fetch(apiPets)
        .then(resposta => resposta.json())
        .then(pets => {
            listaDePets = pets;

            const lista = document.getElementById("listaPets");
            const selectPet = document.getElementById("idPetDesejado");

            lista.innerHTML = "";
            selectPet.innerHTML = '<option value="">Selecione um pet</option>';

            pets.forEach(pet => {
                const classe = pet.status === "Adotado" ? "card-pet adotado" : "card-pet";
                const foto = pet.fotoUrl || "https://placedog.net/500/350";
                const textoAdocao = pet.status === "Adotado" && pet.adotadoPor
                    ? `<p class="adotado-info">Adotado por ${pet.adotadoPor}</p>`
                    : "";
                const botaoAdotar = pet.status === "Adotado"
                    ? `<button disabled>Já adotado</button>`
                    : `<button onclick="abrirModalAdocao(${pet.id}, '${escapar(pet.nome)}')">Adotar</button>`;

                lista.innerHTML += `
                    <div class="${classe}">
                        <img src="${foto}" alt="Foto do pet ${pet.nome}">
                        <div class="card-conteudo">
                            <h3>${pet.nome}</h3>
                            <p><strong>ID:</strong> ${pet.id}</p>
                            <p><strong>Tipo:</strong> ${pet.tipo}</p>
                            <p><strong>Idade:</strong> ${pet.idade} ano(s)</p>
                            <p><strong>Status:</strong> ${pet.status}</p>
                            ${textoAdocao}

                            ${botaoAdotar}
                            <button class="btn-editar" onclick="prepararEdicaoPet(${pet.id}, '${escapar(pet.nome)}', '${escapar(pet.tipo)}', ${pet.idade}, '${escapar(pet.fotoUrl || "")}')">Editar</button>
                            <button class="btn-remover" onclick="removerPet(${pet.id})">Remover</button>
                        </div>
                    </div>
                `;

                if (pet.status !== "Adotado") {
                    selectPet.innerHTML += `
                        <option value="${pet.id}">
                            ${pet.id} - ${pet.nome} (${pet.tipo}) - ${pet.status}
                        </option>
                    `;
                }
            });
        });
}

function salvarPet() {
    const id = document.getElementById("idPet").value;

    const pet = {
        nome: document.getElementById("nome").value,
        tipo: document.getElementById("tipo").value,
        idade: Number(document.getElementById("idade").value),
        fotoUrl: document.getElementById("fotoUrl").value
    };

    let metodo = "POST";
    let url = apiPets;

    if (id) {
        metodo = "PUT";
        url = `${apiPets}/${id}`;
    }

    fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pet)
    })
        .then(resposta => resposta.text())
        .then(mensagem => {
            document.getElementById("mensagemPet").innerText = mensagem;
            limparFormularioPet();
            listarPets();
        });
}

function prepararEdicaoPet(id, nome, tipo, idade, fotoUrl) {
    document.getElementById("idPet").value = id;
    document.getElementById("nome").value = nome;
    document.getElementById("tipo").value = tipo;
    document.getElementById("idade").value = idade;
    document.getElementById("fotoUrl").value = fotoUrl;
    document.getElementById("mensagemPet").innerText = "Editando pet ID " + id;
    window.scrollTo({ top: 0, behavior: "smooth" });
}

function abrirModalAdocao(id, nomePet) {
    document.getElementById("idPetAdocao").value = id;
    document.getElementById("textoPetAdocao").innerText = `Você está adotando o pet ${nomePet}. Informe os dados do adotante.`;
    document.getElementById("modalAdocao").classList.remove("escondido");
}

function fecharModalAdocao() {
    document.getElementById("modalAdocao").classList.add("escondido");
    document.getElementById("idPetAdocao").value = "";
    document.getElementById("nomeAdocao").value = "";
    document.getElementById("telefoneAdocao").value = "";
    document.getElementById("cidadeAdocao").value = "";
}

function confirmarAdocao() {
    const id = document.getElementById("idPetAdocao").value;

    const adotante = {
        nome: document.getElementById("nomeAdocao").value,
        telefone: document.getElementById("telefoneAdocao").value,
        cidade: document.getElementById("cidadeAdocao").value
    };

    fetch(`${apiPets}/${id}/adotar`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(adotante)
    })
        .then(resposta => resposta.text())
        .then(mensagem => {
            document.getElementById("mensagemPet").innerText = mensagem;
            alert(mensagem);
            fecharModalAdocao();
            listarPets();
            listarAdotantes();
        });
}

function removerPet(id) {
    if (!confirm("Deseja remover este pet?")) return;

    fetch(`${apiPets}/${id}`, { method: "DELETE" })
        .then(resposta => resposta.text())
        .then(mensagem => {
            document.getElementById("mensagemPet").innerText = mensagem;
            listarPets();
        });
}

function limparFormularioPet() {
    document.getElementById("idPet").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("tipo").value = "";
    document.getElementById("idade").value = "";
    document.getElementById("fotoUrl").value = "";
}

function listarAdotantes() {
    fetch(apiAdotantes)
        .then(resposta => resposta.json())
        .then(adotantes => {
            const lista = document.getElementById("listaAdotantes");
            lista.innerHTML = "";

            adotantes.forEach(adotante => {
                const pet = listaDePets.find(p => p.id === adotante.idPetDesejado);
                const nomePet = pet ? pet.nome : "Pet não encontrado";

                lista.innerHTML += `
                    <div class="card">
                        <h3>${adotante.nome}</h3>
                        <p><strong>ID:</strong> ${adotante.id}</p>
                        <p><strong>Telefone:</strong> ${adotante.telefone}</p>
                        <p><strong>Cidade:</strong> ${adotante.cidade || "Não informada"}</p>
                        <p><strong>Pet:</strong> ${nomePet} (ID ${adotante.idPetDesejado})</p>

                        <button class="btn-editar" onclick="prepararEdicaoAdotante(${adotante.id}, '${escapar(adotante.nome)}', '${escapar(adotante.telefone)}', '${escapar(adotante.cidade || "")}', ${adotante.idPetDesejado})">Editar</button>
                        <button class="btn-remover" onclick="removerAdotante(${adotante.id})">Remover</button>
                    </div>
                `;
            });
        });
}

function salvarAdotante() {
    const id = document.getElementById("idAdotante").value;
    const idPetSelecionado = document.getElementById("idPetDesejado").value;

    const adotante = {
        nome: document.getElementById("nomeAdotante").value,
        telefone: document.getElementById("telefoneAdotante").value,
        cidade: document.getElementById("cidadeAdotante").value,
        idPetDesejado: Number(idPetSelecionado)
    };

    let metodo = "POST";
    let url = apiAdotantes;

    if (id) {
        metodo = "PUT";
        url = `${apiAdotantes}/${id}`;
    }

    fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(adotante)
    })
        .then(resposta => resposta.text())
        .then(mensagem => {
            document.getElementById("mensagemAdotante").innerText = mensagem;
            limparFormularioAdotante();
            listarAdotantes();
        });
}

function prepararEdicaoAdotante(id, nome, telefone, cidade, idPetDesejado) {
    document.getElementById("idAdotante").value = id;
    document.getElementById("nomeAdotante").value = nome;
    document.getElementById("telefoneAdotante").value = telefone;
    document.getElementById("cidadeAdotante").value = cidade;
    document.getElementById("idPetDesejado").value = idPetDesejado;
    document.getElementById("mensagemAdotante").innerText = "Editando adotante ID " + id;
}

function removerAdotante(id) {
    if (!confirm("Deseja remover este adotante?")) return;

    fetch(`${apiAdotantes}/${id}`, { method: "DELETE" })
        .then(resposta => resposta.text())
        .then(mensagem => {
            document.getElementById("mensagemAdotante").innerText = mensagem;
            listarAdotantes();
        });
}

function limparFormularioAdotante() {
    document.getElementById("idAdotante").value = "";
    document.getElementById("nomeAdotante").value = "";
    document.getElementById("telefoneAdotante").value = "";
    document.getElementById("cidadeAdotante").value = "";
    document.getElementById("idPetDesejado").value = "";
}

function escapar(texto) {
    return String(texto).replace(/'/g, "\\'").replace(/"/g, "&quot;");
}

listarPets();
setTimeout(listarAdotantes, 300);
