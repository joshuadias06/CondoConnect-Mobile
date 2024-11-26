package com.br.condoconnect_mobile

import android.os.Parcel
import android.os.Parcelable

data class Agendamento(
    val id: Int,
    val evento: String?,
    val horario: String?,
    val data: String?,
    val descricao: String?,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(evento)
        parcel.writeString(horario)
        parcel.writeString(data)
        parcel.writeString(descricao)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Agendamento> {
        override fun createFromParcel(parcel: Parcel): Agendamento {
            return Agendamento(parcel)
        }

        override fun newArray(size: Int): Array<Agendamento?> {
            return arrayOfNulls(size)
        }
    }
}
