# CondoConnect Mobile

CondoConnect Mobile é um aplicativo Android desenvolvido para gerenciar produtos em um condomínio. O aplicativo permite que os usuários visualizem uma lista de produtos, adicionem novos produtos, editem e excluam produtos existentes.

## Funcionalidades

- Listagem de produtos com detalhes (nome, descrição, preço e imagem).
- Adição de novos produtos.
- Edição de produtos existentes.
- Exclusão de produtos.
- Interface amigável e responsiva.

## Tecnologias Utilizadas

[![My Skills](https://skillicons.dev/icons?i=kotlin,gradle&perline=17)](https://skillicons.dev)
- **Framework**: Retrofit para comunicação com a API RESTful.
- **Layout**: XML para o design das interfaces.

## Estrutura do Projeto

### Atividades Principais

- **MainActivity**: Tela inicial do aplicativo onde os usuários podem navegar para a tela de login.
- **LoginActivity**: Tela de login onde os usuários inserem suas credenciais.
- **ListagemActivity**: Tela que exibe a lista de produtos recuperados da API.
- **ServiceDetailActivity**: Tela para adicionar ou editar produtos.

### API

A aplicação se conecta a uma API PHP que fornece as seguintes funcionalidades:

- **GET /index.php**: Retorna a lista de produtos ativos em formato JSON.
- **POST /incluir_produto.php**: Inclui um novo produto na base de dados.
- **POST /editar_produto.php**: Edita um produto existente.
- **POST /deletar_produto.php**: Deleta um produto da base de dados.
- **GET /listagem_agendamento.php**: Retorna a lista de agendamento ativos em formato JSON.
- **POST /incluir_agendamento.php**: Inclui um novo agendamento na base de dados.
- **POST /editar_agendamento.php**: Edita um agendamento existente.
- **POST /deletar_agendamento.php**: Deleta um agendamento da base de dados.

### Exemplo de Resposta da API

A resposta da API para a listagem de produtos tem o seguinte formato:

```json
{
    "total": 3,
    "produtos": [
        {
            "id_produto": 1,
            "nome_produto": "Produto 1",
            "desc_produto": "Descrição do Produto 1",
            "preco_produto": "10.00",
            "imagem_produto": "url_da_imagem_1"
        },
        {
            "id_produto": 2,
            "nome_produto": "Produto 2",
            "desc_produto": "Descrição do Produto 2",
            "preco_produto": "20.00",
            "imagem_produto": "url_da_imagem_2"
        }
    ]
}



