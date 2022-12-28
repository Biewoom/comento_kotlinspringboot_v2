package com.comento.dbless.domain

class Persons(
    val personList: List<Person>
) {

    fun sort(sortBy: String, sortOrder: String): List<Person> {
        return when (sortOrder) {
            "desc" -> descSort(sortBy)
            "asc" -> ascSort(sortBy)
            else -> throw IllegalArgumentException()
        }
    }

    fun filter(ageCutoff: Int?, heightCutoff: Int?, except: List<String>?): List<Person> {
        var filterPersons = personList.filter { it.age > ageCutoff ?: 0 }
        filterPersons = filterPersons.filter { it.height > heightCutoff ?: 0 }
        if (except != null) {
            filterPersons = filterPersons.filter { !except.contains(it.id) }
        }
        return filterPersons
    }

    private fun descSort(sortBy: String): List<Person> {
        return when (sortBy) {
            "age" -> personList.sortedByDescending { it.age }
            "height" -> personList.sortedByDescending { it.height }
            "id" -> personList.sortedByDescending { it.id }
            "name" -> personList.sortedByDescending { it.name }
            else -> throw IllegalArgumentException()
        }
    }

    private fun ascSort(sortBy: String): List<Person> {
        return when (sortBy) {
            "age" -> personList.sortedBy { it.age }
            "height" -> personList.sortedBy { it.height }
            "id" -> personList.sortedBy { it.id }
            "name" -> personList.sortedBy { it.name }
            else -> throw IllegalArgumentException()
        }
    }

}
