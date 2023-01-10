package com.comento.jpa.presentation

import com.comento.jpa.OBJECT_MAPPER
import com.comento.jpa.domain.common.enums.Gender
import com.comento.jpa.toSingleStringWithoutSpace
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PersonDtoJsonTest {

    @DisplayName("Person Request 역직렬화 테스트")
    @Test
    fun `test person deserialization`(){
        // given
        val jsonString = """
             {
                  "person_id" : 1,
                  "age" : 48,
                  "height" : 196,
                  "weight" : 144,
                  "name" : "예철민",
                  "gender" : "Female",
                  "is_married" : null,
                  "company" : "DAEWO",
                  "country" : "CHINA"
            }
        """.trimIndent()
        val dto = PersonDto(
                personId = 1,
                age = 48,
                height = 196,
                weight = 144,
                name = "예철민",
                gender = Gender.FEMALE,
                isMarried = null,
                company = "DAEWO",
                country = "CHINA"
        )

        // when
        val res = OBJECT_MAPPER.readValue<PersonDto>(jsonString)

        // then
        res shouldBe dto
        res.isMarriedNotNull shouldBe false
    }

    @DisplayName("Person Result 직렬화 테스트")
    @ParameterizedTest
    @MethodSource("provider_ResultDto")
    fun `test Person Result serialization`(pair: Pair<ResultDto, String>){
        // given
        val (dto, expectedJsonString) = pair

        // when
        val res = OBJECT_MAPPER.writeValueAsString(dto)

        // then
        res shouldBe expectedJsonString
    }

    companion object {
        @JvmStatic
        fun provider_ResultDto() = listOf(
                Pair(
                        ResultDto(
                                resultTypes = listOf(1, 0, 1),
                                personIds = listOf(10L, 20L, 30L)
                        ),
                        """
                        {
                           "result_types": [1, 0, 1],
                           "person_ids": [10, 20, 30]
                        }
                        """.toSingleStringWithoutSpace()
                ),
                Pair(
                        ResultDto(
                                resultTypes = listOf(1, 1, 1, 0),
                                personIds = listOf(10L, 30L, 40L, 50L)
                        ),
                        """
                        {
                           "result_types": [1, 1, 1, 0],
                           "person_ids": [10, 30, 40, 50]
                        }  
                        """.toSingleStringWithoutSpace()
                )
        )
    }
}