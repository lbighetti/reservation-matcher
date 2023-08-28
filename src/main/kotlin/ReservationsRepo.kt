import com.exploreshackle.api.matcher.v1.MatcherServiceOuterClass
import com.exploreshackle.api.matcher.v1.MatcherServiceOuterClass.MatcherReservation
import com.exploreshackle.api.matcher.v1.MatcherServiceOuterClass.LocalDate
import com.exploreshackle.api.reservation.v1.ReservationServiceOuterClass.Reservation
import jakarta.inject.Singleton

@Singleton
class ReservationsRepo {
    private var reservations = mutableListOf<MatcherReservation>()

    fun storeReservation(reservation: Reservation) {
        val matcherReservation = castReservationToMatcherReservation(reservation)
        reservations.add(matcherReservation)
    }

    fun searchForReservation(firstName: String, lastName: String, arrivalDate: LocalDate): List<MatcherReservation> {
        return reservations.filter { reservation ->
            (reservation.guestDetails.firstName == firstName
                    || reservation.guestDetails.firstName.first() == firstName.first())
                    && reservation.guestDetails.lastName == lastName
                    && reservation.arrivalDate == arrivalDate
        }
    }

    fun clearReservations() {
        reservations.clear()
    }

    fun countReservations() : Int {
        return reservations.size
    }

    private fun castReservationToMatcherReservation(reservation: Reservation): MatcherReservation {
        //TODO: I feel there is certainly a smarter way to do this
        val guestDetails = MatcherServiceOuterClass.GuestDetails.newBuilder()
            .setFirstName(reservation.guestDetails.firstName)
            .setLastName(reservation.guestDetails.lastName)
            .setTitle(reservation.guestDetails.title)
            .setPhoneNumber(reservation.guestDetails.phoneNumber)
            .setAddress(reservation.guestDetails.address)
            .setCity(reservation.guestDetails.city)
            .setPostalCode(reservation.guestDetails.postalCode)
            .setCountry(reservation.guestDetails.country)
            .setCompanyName(reservation.guestDetails.companyName)

        val arrivalDate = LocalDate.newBuilder()
            .setYear(reservation.arrivalDate.year)
            .setMonth(reservation.arrivalDate.month)
            .setDay(reservation.arrivalDate.day)

        val departureDate = LocalDate.newBuilder()
            .setYear(reservation.departureDate.year)
            .setMonth(reservation.departureDate.month)
            .setDay(reservation.departureDate.day)

        return MatcherReservation.newBuilder()
            .setTimestamp(reservation.timestamp)
            .setInternalId(reservation.internalId)
            .setWebConfirmationCode(reservation.webConfirmationCode)
            .setBookingConfirmationNumber(reservation.bookingConfirmationNumber)
            .setTravelAgentConfirmationCode(reservation.travelAgentConfirmationCode)
            .setGuestDetails(guestDetails)
            .setArrivalDate(arrivalDate)
            .setDepartureDate(departureDate)
            .build()
    }
}