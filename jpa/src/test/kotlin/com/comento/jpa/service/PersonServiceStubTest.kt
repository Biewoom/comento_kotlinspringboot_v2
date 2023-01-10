package com.comento.jpa.service

import com.comento.jpa.domain.PersonNotFoundException
import com.comento.jpa.domain.common.enums.Gender
import com.comento.jpa.domain.person.Person
import com.comento.jpa.domain.person.PersonRepository
import com.comento.jpa.toArgumentsStream
import com.comento.jpa.utils.DYNAMIC_DUMMY
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class PersonServiceStubTest {

    private val personRepositoryMock = mockk<PersonRepository>(relaxed = true)
    private val personServiceSut = PersonService(personRepositoryMock)

    @DisplayName("findBlindDateCoupleList All 성공 케이스 테스트")
    @Test
    fun `test findBlindDateCoupleList when success`(){
        // given
        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.MALE)
        } returns listOf(
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.MALE); changeAge(10) },
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.MALE); changeAge(15) },
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.MALE); changeAge(20) }
        )
        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.FEMALE)
        } returns listOf(
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.FEMALE); changeAge(12) },
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.FEMALE); changeAge(16) },
                Person.DYNAMIC_DUMMY.apply { changeGender(Gender.FEMALE); changeAge(23) }
        )
        val expectedSize = 4
        // when
        val res = personServiceSut.findBlindDateCoupleList(3, null)
        // then
        res.size shouldBe  expectedSize
    }

    @DisplayName("findBlindDateCoupleList Korea 성공 케이스 테스트")
    @Test
    fun `test findBlindDateCoupleList in Korea when success`(){
        // given
        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.MALE)
        } returns listOf(
                Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.MALE); changeAge(10) },
                Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.MALE); changeAge(15) },
                Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.MALE); changeAge(20) }
        )
        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.FEMALE)
        } returns listOf(
                Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.FEMALE); changeAge(12) },
                Person.DYNAMIC_DUMMY.apply { updateCountry("CHINA"); changeGender(Gender.FEMALE); changeAge(16) },
                Person.DYNAMIC_DUMMY.apply { updateCountry("USA"); changeGender(Gender.FEMALE); changeAge(23) }
        )
        val expectedSize = 2
        // when
        val res = personServiceSut.findBlindDateCoupleList(3, "KOREA")
        // then
        res.size shouldBe expectedSize
    }

    @DisplayName("findBlindDateCoupleList 에러 케이스 테스트")
    @ParameterizedTest
    @MethodSource("provider_error_case")
    fun `test findBLinDateCoupleList when error`(pair: Pair<Gender, String>){
        // given
        val (gender, expectedMessage) = pair

        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.MALE)
        } returns listOf(Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.MALE); changeAge(10) })

        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(Gender.FEMALE)
        } returns listOf(Person.DYNAMIC_DUMMY.apply { updateCountry("KOREA"); changeGender(Gender.FEMALE); changeAge(10) })

        every {
            personRepositoryMock.findPeopleByGenderAndAgeNotNull(gender)
        } returns emptyList()

        // when & then
        val ex = shouldThrow<PersonNotFoundException> {
            personServiceSut.findBlindDateCoupleList(4, null)
        }

        ex.message shouldBe expectedMessage
    }

    companion object {
        @JvmStatic
        fun provider_error_case() = listOf(
                Pair(Gender.MALE, "men cannot be found"),
                Pair(Gender.FEMALE,"women cannot be found"),
        ).toArgumentsStream()
    }
}