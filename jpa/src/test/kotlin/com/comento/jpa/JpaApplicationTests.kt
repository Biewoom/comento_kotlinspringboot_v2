package com.comento.jpa

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream

internal fun List<Any?>.toArgumentsStream() = Stream.of(*this.map{ Arguments.of (it) }.toTypedArray())
internal fun String.toSingleStringWithoutSpace() = this.trimIndent().replace("\n", "").replace(" ", "")

@SpringBootTest
class JpaApplicationTests {

	@Test
	fun contextLoads() {
	}

}
