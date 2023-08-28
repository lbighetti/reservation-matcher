package test

import ReservationsGrpcClient
import ReservationsRepo
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@QuarkusTest
class ReservationGrpcClientTest {
    @Inject
    lateinit var reservationsGrpcClient: ReservationsGrpcClient

    @Inject
    lateinit var reservationsRepo: ReservationsRepo

    @BeforeEach
    fun setup() {
        reservationsRepo.clearReservations()
    }

    @Test
    fun testConsume() {
        reservationsGrpcClient.consumeStream()
        assert(reservationsRepo.countReservations() == 3)
    }
}
