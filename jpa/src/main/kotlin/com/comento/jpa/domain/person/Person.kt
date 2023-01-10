package com.comento.jpa.domain.person

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.comento.jpa.domain.common.enums.Gender
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import kotlin.RuntimeException

@Entity
@Table(name = "person")
class Person(
    name: String,
    gender: Gender,
    country: String
): Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private var _id: Long? = null

    @Column(name = "name")
    var name: String = name
        protected set

    @Column(name = "country", nullable = false)
    var country: String = country
        protected set

    @Column(name = "company", nullable = true)
    var company: String? = null
        protected set

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false)
    var gender: Gender = gender
        protected set

    @Column(name = "age", nullable = true)
    var age: Int? = null
        protected set

    @Column(name = "height", nullable = true)
    var height: Int? = null

    @Column(name= "weight", nullable = true)
    var weight: Int? = null

    @Column(name = "is_married", nullable = true)
    var isMarried: Boolean? = null
        protected set

    val ageNotNull: Int
        get() = age ?: 0
    val id: Long
        get() = _id ?: throw RuntimeException()

    fun updateId(newId: Long) = run { this._id = newId }
    fun updateCountry(country: String) = run {this.country = country }
    fun changeAge(age: Int) = run { this.age = age }
    fun changeGender(gender: Gender) = run { this.gender = gender }
    fun changeCompany(newCompany: String?) = run { this.company = newCompany }
    fun updateIsMarried(new: Boolean) = run { this.isMarried = true }

    override fun equals(other: Any?): Boolean = kotlinEquals(other, properties = arrayOf(Person::_id))
    override fun hashCode() = kotlinHashCode(properties = arrayOf(Person::_id))
    override fun toString(): String = kotlinToString(properties = arrayOf(
        Person::_id,
        Person::name,
        Person::country)
    )

    companion object
}
