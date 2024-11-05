package com.br.condoconnect_mobile

import retrofit2.Call
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
    @POST("incluir_produto.php")
    fun incluirProduto(
        @Field("PRODUTO_NOME") nome: String,
        @Field("PRODUTO_DESC") descricao: String,
        @Field("PRODUTO_PRECO") preco: String,
        @Field("PRODUTO_IMAGEM") imagem: String
    ): Call<Void>

    // Editar um produto
    @FormUrlEncoded
    @POST("editar_produto.php") // Altere para o endpoint correto
    fun editarProduto(
        @Field("PRODUTO_ID") id: Int,
        @Field("PRODUTO_NOME") nome: String,
        @Field("PRODUTO_DESC") descricao: String,
        @Field("PRODUTO_PRECO") preco: String,
        @Field("PRODUTO_IMAGEM") imagem: String // Adicione a imagem se necessário
    ): Call<RespostaEdit> // Use uma classe para a resposta

    // Deletar um produto
    @FormUrlEncoded
    @POST("deletar_produto.php") // Altere para o endpoint correto
    fun deletarProduto(
        @Field("PRODUTO_ID") id: Int
    ): Call<Void>
}

// Classe para a resposta da edição do produto
data class RespostaEdit(
    val status: String,
    val error: String? // Caso haja algum erro, para facilitar a depuração
)
