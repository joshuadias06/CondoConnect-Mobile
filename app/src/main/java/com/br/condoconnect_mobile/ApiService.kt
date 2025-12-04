package com.br.condoconnect_mobile

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // ---------------- PRODUTOS ----------------
    @GET("index.php")
    fun getProdutos(): Call<ProdutoResponse>

    @FormUrlEncoded
    @POST("criar_produto.php")
    fun incluirProduto(
        @Field("nome_produto") nome: String,
        @Field("desc_produto") descricao: String,
        @Field("preco_produto") preco: String,
        @Field("imagem_produto") imagem: String
    ): Call<Void>

    @FormUrlEncoded
    @POST("editar_produto.php")
    fun editarProduto(
        @Field("id_produto") id: Int,
        @Field("nome_produto") nome: String,
        @Field("desc_produto") descricao: String,
        @Field("preco_produto") preco: String,
        @Field("imagem_produto") imagem: String
    ): Call<RespostaEdit>

    @FormUrlEncoded
    @POST("index.php")
    fun deletarProduto(
        @Field("id_produto") id: Int
    ): Call<Void>


    // ---------------- CADASTRO DE USUÁRIO ----------------
    @POST("condominio/api/cadastro")
    fun cadastrarUsuario(@Body usuario: Usuario): Call<ResponseCadastro>


    // ---------------- AGENDAMENTOS ----------------
    @GET("condominio/api/listar_agendamentos.php")
    fun getAgendamentos(): Call<List<Agendamento>>

    @FormUrlEncoded
    @POST("condominio/api/criar_agendamento.php")
    fun incluirAgendamento(
        @Field("evento") evento: String,
        @Field("descricao") descricao: String,
        @Field("data") data: String,
        @Field("horario") horario: String
    ): Call<Void>

    @FormUrlEncoded
    @POST("condominio/api/editar_agendamento.php")
    fun editarAgendamento(
        @Field("id") id: Int,
        @Field("evento") evento: String,
        @Field("descricao") descricao: String,
        @Field("data") data: String,
        @Field("horario") horario: String
    ): Call<RespostaEdit>

    @FormUrlEncoded
    @POST("condominio/api/remover_agendamento.php")
    fun removerAgendamento(
        @Field("id") id: Int
    ): Call<Void>
}

data class RespostaEdit(
    val status: String,
    val error: String?
)