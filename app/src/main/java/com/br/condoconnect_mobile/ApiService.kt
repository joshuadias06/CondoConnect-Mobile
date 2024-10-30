package com.br.condoconnect_mobile

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Obter a lista de produtos
    @GET("produtos.php")
    fun getProdutos(): Call<List<Produto>>

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
    @POST("editar_produto.php")
    fun editarProduto(
        @Field("PRODUTO_ID") id: Int,
        @Field("PRODUTO_NOME") nome: String,
        @Field("PRODUTO_DESC") descricao: String,
        @Field("PRODUTO_PRECO") preco: String,
        @Field("PRODUTO_IMAGEM") imagem: String
    ): Call<Void>

    // Deletar um produto
    @FormUrlEncoded
    @POST("deletar_produto.php")
    fun deletarProduto(
        @Field("PRODUTO_ID") id: Int
    ): Call<Void>
}
