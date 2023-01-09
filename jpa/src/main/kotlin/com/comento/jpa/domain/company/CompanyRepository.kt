package com.comento.jpa.domain.company

import com.comento.jpa.domain.country.Country
import org.springframework.data.repository.CrudRepository

interface CompanyRepository: CrudRepository<Company, Long>{

    fun findAllByCountry(country: String): List<Company>

    fun findCompanyByNameAndCountry(name: String, country: Country): Company?
    fun findCompanyByName(name: String): Company?
}