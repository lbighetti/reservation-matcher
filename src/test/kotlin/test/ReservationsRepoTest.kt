package test

import ReservationsRepo
import com.exploreshackle.api.matcher.v1.MatcherServiceOuterClass
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import test.support.reservationWithWebcode


@QuarkusTest
class ReservationsRepoTest {
    @Inject
    lateinit var reservationsRepo: ReservationsRepo

    @Test
    fun testReservationsRepo() {
        val reservationFixture = reservationWithWebcode()
        reservationsRepo.storeReservation(reservationFixture)

        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(7)
            .build()

        val searchResult = reservationsRepo.searchForReservation(
            firstName = "Dustin",
            lastName = "Weaver",
            arrivalDate = arrivalDate
        ).first()
        assert(searchResult.guestDetails.firstName.equals("Dustin"))
        assert(searchResult.guestDetails.lastName.equals("Weaver"))
        assert(searchResult.webConfirmationCode.equals("DEVL-QP2D"))
        assert(searchResult.arrivalDate == arrivalDate)
    }
}
