package com.comento.dbless.ui

import com.comento.dbless.domain.Person
import com.comento.dbless.domain.Persons
import com.comento.dbless.ui.dto.FilterPersonRequest
import com.comento.dbless.ui.dto.SortPersonRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/persons")
class PersonController {

    @PostMapping("/sort")
    fun sortPersonApi(@RequestBody @Valid request: SortPersonRequest): ResponseEntity<List<Person>> {
        val persons = Persons(request.persons)
        val sortPersons = persons.sort(request.sortBy, request.sortOrder)

        return ResponseEntity.ok(sortPersons)
    }

    @PostMapping("/filter")
    fun filterPersonApi(@RequestBody @Valid request: FilterPersonRequest ): ResponseEntity<List<Person>> {
        val persons = Persons(request.persons)
        val filterPersons = persons.filter(request.ageCutoff, request.heightCutoff, request.except)

        return ResponseEntity.ok(filterPersons)
    }

}
