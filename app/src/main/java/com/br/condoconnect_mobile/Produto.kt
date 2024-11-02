package com.br.condoconnect_mobile

import android.os.Parcel
import android.os.Parcelable

data class Produto(
    val id_produto: Int,
    val nome_produto: String,
    val preco_produto: Double,
    val desc_produto: String,
    val imagem_produto: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_produto)
        parcel.writeString(nome_produto)
        parcel.writeDouble(preco_produto)
        parcel.writeString(desc_produto)
        parcel.writeString(imagem_produto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Produto> {
        override fun createFromParcel(parcel: Parcel): Produto {
            return Produto(parcel)
        }

        override fun newArray(size: Int): Array<Produto?> {
            return arrayOfNulls(size)
        }
    }
}

