package com.comento.jpa.service

import com.comento.jpa.domain.BlindDateNotFoundException
import com.comento.jpa.domain.PersonNotFoundException
import com.comento.jpa.domain.common.enums.Gender
import com.comento.jpa.domain.person.Person
import com.comento.jpa.logger
import com.comento.jpa.presentation.BlindDateDto
import com.comento.jpa.domain.person.PersonRepository
import com.comento.jpa.presentation.PersonDto
import com.comento.jpa.presentation.ResultDto
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PersonService(
    private val personRepository: PersonRepository
) {

    fun findBlindDateCoupleList(ageDiff: Int, country: String?): List<Pair<BlindDateDto, BlindDateDto>> {

        val men = personRepository.findPeopleByGenderAndAgeNotNull(Gender.MALE)
            .filter { man -> country?.let { man.country == it } ?: true }
            .ifEmpty { throw PersonNotFoundException("men cannot be found") }
        val women = personRepository.findPeopleByGenderAndAgeNotNull(Gender.FEMALE)
            .filter { woman -> country?.let { woman.country == it } ?: true }
            .ifEmpty { throw PersonNotFoundException("women cannot be found") }

        val matchesWhichManIsOlder = men.asSequence()
            .map { man ->
                    women.filter { man.ageNotNull - ageDiff <= it.ageNotNull && it.ageNotNull <= man.ageNotNull }
                    .map { woman -> Pair(BlindDateDto.fromPerson(man), BlindDateDto.fromPerson(woman) ) } }
            .flatten()
            .toList()
            .also { logger.info { it } }

        val matchesWhichWomanIsOlder =  men.asSequence()
            .map { man ->
                    women.filter { man.ageNotNull < it.ageNotNull && it.ageNotNull <= man.ageNotNull + ageDiff  }
                    .map { woman -> Pair(BlindDateDto.fromPerson(woman), BlindDateDto.fromPerson(man)) }
            }
            .flatten()
            .toList()
            .also { logger.info { it } }

        val finalMatches = matchesWhichManIsOlder + matchesWhichWomanIsOlder

        return finalMatches.ifEmpty { throw BlindDateNotFoundException("BlindDate Candidates with ageDiff ` $ageDiff ` cannot be Found") }
    }

    @Transactional
    fun registerOrSavePersons(personRequests: List<PersonDto>): ResultDto {

        val existedPersonIds = personRequests
            .asSequence()
            .map { it.personId }
            .filter { id -> id?.let { personRepository.existsById(id) } ?: false }
            .toSet()

        logger.info { "existedPersons: $existedPersonIds" }

        val resultAndIdPairs = personRequests
            .map { personDto -> convertToPerson(personDto) }
            .map { person ->
                when (val id = personRepository.save(person).id) {
                    in existedPersonIds -> Pair(0, id)
                    else -> Pair(1, id)
                }
            }

        return ResultDto(
                resultTypes = resultAndIdPairs.map { it.first },
                personIds = resultAndIdPairs.map { it.second }
            )
    }

    private fun convertToPerson(personDto: PersonDto): Person =
        Person (
            name = personDto.name,
            gender = personDto.genderNotNull,
            country = personDto.country
        ).apply {
            height = personDto.height
            weight = personDto.weight
        }.apply {
            changeCompany(personDto.company)
            updateIsMarried(personDto.isMarriedNotNull)
         }.apply {
            personDto.personId?.let { updateId(it) }
            personDto.age?.let { changeAge(it) }
        }

}