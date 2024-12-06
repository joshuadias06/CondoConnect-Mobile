package com.br.condoconnect_mobile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Obter a lista de produtos
    @GET("index.php")
    fun getProdutos(): Call<ProdutoResponse>

    // Incluir um produto
    @FormUrlEncoded
    @POST("criar_produto.php")
    fun incluirProduto(
        @Field("nome_produto") nome: String,
        @Field("desc_produto") descricao: String,
        @Field("preco_produto") preco: String,
        @Field("imagem_produto") imagem: String
    ): Call<Void>

    // Editar um produto
    @FormUrlEncoded
    @POST("editar_produto.php") // Altere para o endpoint correto
    fun editarProduto(
        @Field("id_produto") id: Int,
        @Field("nome_produto") nome: String,
        @Field("desc_produto") descricao: String,
        @Field("preco_produto") preco: String,
        @Field("imagem_produto") imagem: String // Adicione a imagem se necessário
    ): Call<RespostaEdit> // Use uma classe para a resposta

    // Deletar um produto
    @FormUrlEncoded
    @POST("index.php") // Altere para o endpoint correto
    fun deletarProduto(
        @Field("id_produto") id: Int
    ): Call<Void>


    interface ApiService {
        @POST("condominio/api/cadastro")
        fun cadastrarUsuario(@Body usuario: Usuario): Call<ResponseCadastro>
    }

    // Obter a lista de agendamentos
    @GET("condominio/api/listar_agendamentos.php")
    fun getAgendamentos(): Call<List<Agendamento>> // Modificado para lista

    // Incluir um agendamento
    @FormUrlEncoded
    @POST("condominio/api/criar_agendamento.php")
    fun incluirAgendamento(
        @Field("evento") evento: String,
        @Field("descricao") descricao: String,
        @Field("data") data: String,
        @Field("horario") horario: String
    ): Call<Void>

    // Editar um agendamento
    @FormUrlEncoded
    @POST("condominio/api/editar_agendamento.php")
    fun editarAgendamento(
        @Field("id") id: Int,
        @Field("evento") evento: String,
        @Field("descricao") descricao: String,
        @Field("data") data: String,
        @Field("horario") horario: String
    ): Call<RespostaEdit>

    // Remover um agendamento
    @FormUrlEncoded
    @POST("condominio/api/remover_agendamento.php")
    fun removerAgendamento(
        @Field("id") id: Int
    ): Call<Void>

}

// Classe para a resposta da edição do produto
data class RespostaEdit(
    val status: String,
    val error: String? // Caso haja algum erro, para facilitar a depuração
)
