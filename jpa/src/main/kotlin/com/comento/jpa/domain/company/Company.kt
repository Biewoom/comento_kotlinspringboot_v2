package com.comento.jpa.domain.company

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.comento.jpa.domain.common.vo.YMD
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import kotlin.RuntimeException

@Entity
@Table(name = "company")
class Company (
    name: String,
    foundingYMD: YMD,
    country: String
): Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private var _id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "country", nullable = false)
    var country: String = country
        protected set

    @Column(name = "founding_date", nullable = false, updatable = false)
    val foundingYMD: YMD = foundingYMD

    val id
        get() = _id ?: throw RuntimeException()

    override fun equals(other: Any?): Boolean = kotlinEquals(other, properties = arrayOf(Company::_id))
    override fun hashCode() = kotlinHashCode(properties = arrayOf(Company::_id))
    override fun toString(): String = kotlinToString(properties = arrayOf(
        Company::_id,
        Company::name,
        Company::foundingYMD,
        Company::country)
    )
}


