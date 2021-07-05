package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import org.junit.Test
import kotlin.test.assertEquals

class MoviesLocalMapperTest {

    private val sut: MoviesLocalMapper =
        MoviesLocalMapper()

    @Test
    fun map() {
        val dto = aMovieDetailsDTO()

        val actual = sut.map(dto)

        val expected = aMovieEntity()
        assertEquals(expected, actual)
    }
}

private fun aMovieDetailsDTO(): MovieDetailsDTO =
    MovieDetailsDTO(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieEntity(): MovieEntity =
    MovieEntity(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
    )
