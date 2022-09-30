package br.com.alura.orgs.extensions

import android.content.Context
import android.content.Intent

fun Context.vaiPara(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {} // pode receber ou nao uma intent via construtor modificada
) {
    Intent(this, clazz)
        .apply {
            intent() //modifica a intent recebida
            startActivity(this)
        }
}