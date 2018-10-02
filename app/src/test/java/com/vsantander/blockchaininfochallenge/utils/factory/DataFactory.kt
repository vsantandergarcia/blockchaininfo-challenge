package com.vsantander.blockchaininfochallenge.utils.factory

class DataFactory {

    companion object Factory {

        fun randomInt(): Int {
            return Math.random().toInt()
        }

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomDouble(): Double {
            return Math.random()
        }

        fun randomFloat(): Float {
            return Math.random().toFloat()
        }
    }

}