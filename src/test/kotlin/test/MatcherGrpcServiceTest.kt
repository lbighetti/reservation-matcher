package test

import ReservationsRepo
import com.exploreshackle.api.matcher.v1.MatcherService
import com.exploreshackle.api.matcher.v1.MatcherServiceOuterClass
import io.quarkus.grpc.GrpcClient
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import test.support.reservationWithAgentConfirmationCode
import test.support.reservationWithBookingConfirmationCode
import test.support.reservationWithWebcode


@QuarkusTest
class MatcherGrpcServiceTest {
    @GrpcClient
    lateinit var matcherService: MatcherService

    @Inject
    lateinit var reservationsRepo: ReservationsRepo

    @BeforeEach
    fun setup() {
        reservationsRepo.clearReservations()
        reservationsRepo.storeReservation(reservationWithWebcode())
        reservationsRepo.storeReservation(reservationWithBookingConfirmationCode())
        reservationsRepo.storeReservation(reservationWithAgentConfirmationCode())
    }

    @Test
    fun testSuccessfulSearchWithWebcode() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(7)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Dustin")
                .setLastName("Weaver")
                .setWebConfirmationCode("DEVL-QP2D")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.SUCCESS)
        assert(searchResult.reservation.guestDetails.firstName == "Dustin")
        assert(searchResult.reservation.guestDetails.lastName == "Weaver")
        assert(searchResult.reservation.webConfirmationCode == "DEVL-QP2D")
    }

    @Test
    fun testSuccessfulSearchWithBookingConfirmationCode() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(9)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Adam")
                .setLastName("Cox")
                .setBookingConfirmationNumber("201584")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.SUCCESS)
        assert(searchResult.reservation.guestDetails.firstName == "Adam")
        assert(searchResult.reservation.guestDetails.lastName == "Cox")
        assert(searchResult.reservation.bookingConfirmationNumber == "201584")
    }

    @Test
    fun testSuccessfulSearchWithAgentConfirmationCode() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(12)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Amy")
                .setLastName("Reid")
                .setTravelAgentConfirmationCode("L5CS-LHGM")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.SUCCESS)
        assert(searchResult.reservation.guestDetails.firstName == "Amy")
        assert(searchResult.reservation.guestDetails.lastName == "Reid")
        assert(searchResult.reservation.travelAgentConfirmationCode == "L5CS-LHGM")
    }

    @Test
    fun testSuccessfulSearchWithAbbreviatedFirstName() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(12)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("A.")
                .setLastName("Reid")
                .setTravelAgentConfirmationCode("L5CS-LHGM")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.SUCCESS)
        assert(searchResult.reservation.guestDetails.firstName == "Amy")
        assert(searchResult.reservation.guestDetails.lastName == "Reid")
        assert(searchResult.reservation.travelAgentConfirmationCode == "L5CS-LHGM")
    }

    @Test
    fun testSearchFailsWithNoConfirmationCode() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(12)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Amy")
                .setLastName("Reid")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.REQUEST_MISSING_INFORMATION)
    }

    @Test
    fun testSearchFailsWithWrongGuestDetails() {
        val arrivalDate = MatcherServiceOuterClass.LocalDate.newBuilder()
            .setYear(2023)
            .setMonth(9)
            .setDay(12)

        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Wrong")
                .setLastName("Guest")
                .setTravelAgentConfirmationCode("L5CS-LHGM")
                .setArrivalDate(arrivalDate)
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.NOT_FOUND)
    }

    @Test
    fun testSearchFailsWithMissingDate() {
        val searchResult = matcherService
            .search(MatcherServiceOuterClass.SearchRequest.newBuilder()
                .setFirstName("Amy")
                .setLastName("Reid")
                .setTravelAgentConfirmationCode("L5CS-LHGM")
                .build()
            )
            .await()
            .indefinitely()

        assert(searchResult.status == MatcherServiceOuterClass.SearchResult.SearchResultStatus.REQUEST_MISSING_INFORMATION)
    }
}
