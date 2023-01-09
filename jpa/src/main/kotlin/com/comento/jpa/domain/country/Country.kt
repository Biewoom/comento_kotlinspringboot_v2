package com.comento.jpa.domain.country

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "country")
class Country(
    zipCode: Long,
    name: String,
    capitalCity: String,
): Serializable {
    @Id @Column(name = "zip_code", nullable = false, unique = true)
    private val zipCode: Long = zipCode

    @Column(name = "name", nullable = false, unique = true)
    var name: String = name
        protected set

    @Column(name = "capital_city", nullable =false)
    var capitalCity: String = capitalCity
        protected set

    override fun equals(other: Any?): Boolean = kotlinEquals(other, properties = arrayOf(Country::zipCode))
    override fun hashCode() = kotlinHashCode(properties = arrayOf(Country::zipCode))
    override fun toString(): String = kotlinToString(properties = arrayOf(
        Country::zipCode,
        Country::name,
        Country::capitalCity)
    )
}


