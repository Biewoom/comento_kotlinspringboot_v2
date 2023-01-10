package com.comento.jpa.presentation

import com.comento.jpa.domain.BlindDateNotFoundException
import com.comento.jpa.domain.PersonNotFoundException
import com.comento.jpa.service.PersonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(
    private val personService: PersonService
) {

    @Operation(summary = "Post or Update")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Success Results",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ResultDto::class))]),
        ApiResponse(responseCode = "500", description = "UnKnown Error", content = [Content()])
    ])
    @PutMapping
    fun postPersons(@RequestBody personRequests: List<PersonDto>): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(personService.registerOrSavePersons(personRequests))
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }


    @Operation(summary = "Get BlindDate Couple List with it's age Difference")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Found BlindDate tuple",
                content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = BlindDateDto::class)))]),
        ApiResponse(responseCode = "404", description = "Not Found", content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class, example = "`ageDiff` Cannot be Found" ))])
    ])
    @GetMapping("/blind-date/{ageDiff}")
    fun getBlindDateCoupleList(@PathVariable("ageDiff") ageDiff: Int, @RequestParam(required = false) country: String?): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(personService.findBlindDateCoupleList(ageDiff, country))
        } catch (e: Exception){
            when (e){
                is PersonNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
                is BlindDateNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}