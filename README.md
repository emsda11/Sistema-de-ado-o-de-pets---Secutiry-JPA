# Sistema de Adoção de Pets

Projeto Spring Boot com:

- API REST
- Spring Data JPA
- Repository
- Banco H2
- H2 Console
- Spring Security com tela de login
- Interface HTML/CSS/JS
- Fotos dos pets
- Adoção vinculando o pet ao nome do adotante

## Como executar

Na pasta do projeto:

```bash
mvn spring-boot:run
```

Depois acesse:

```text
http://localhost:8080/login.html
```

## Login

```text
Usuário: professora
Senha: 123456
```

## H2 Console

```text
http://localhost:8080/h2-console
```

Dados de acesso:

```text
JDBC URL: jdbc:h2:file:./data/pet_adocao_db
User Name: sa
Password: deixar vazio
```

## Fluxo de adoção

1. Faça login.
2. Cadastre ou visualize os pets.
3. Cada pet possui foto, status e botão Adotar.
4. Ao clicar em Adotar, informe nome, telefone e cidade do adotante.
5. O sistema salva o adotante no banco e altera o pet para status `Adotado`.
6. Na tela aparece a mensagem: `O pet X foi adotado por Fulano!`.

## Endpoints principais

### Pets

```text
GET    /pets
GET    /pets/{id}
POST   /pets
PUT    /pets/{id}
PUT    /pets/{id}/adotar
DELETE /pets/{id}
```

### Adotantes

```text
GET    /adotantes
GET    /adotantes/{id}
POST   /adotantes
PUT    /adotantes/{id}
DELETE /adotantes/{id}
```

## Exemplo de cadastro de pet

```json
{
  "nome": "Mel",
  "tipo": "Cachorro",
  "idade": 2,
  "fotoUrl": "https://images.unsplash.com/photo-1552053831-71594a27632d?w=600"
}
```

## Exemplo de adoção pelo Thunder Client

Método:

```text
PUT
```

URL:

```text
http://localhost:8080/pets/1/adotar
```

Body JSON:

```json
{
  "nome": "Maria Silva",
  "telefone": "84999999999",
  "cidade": "Natal"
}
```
