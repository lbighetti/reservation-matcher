package test.support

import com.exploreshackle.api.reservation.v1.ReservationServiceOuterClass


fun reservationWithWebcode(): ReservationServiceOuterClass.Reservation {
    val guestDetails = ReservationServiceOuterClass.GuestDetails.newBuilder()
        .setFirstName("Dustin")
        .setLastName("Weaver")
        .setTitle("Ind.")
        .setPhoneNumber("(260)483-5842x2945")
        .setAddress("84459 Reynolds Grove Suite 591")
        .setCity("Rodriguezhaven")
        .setPostalCode("06818")
        .setCountry("Kenya")
        .setCompanyName("Pace-Thomas")

    val arrivalDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(7)

    val departureDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(9)

    return ReservationServiceOuterClass.Reservation.newBuilder()
        .setTimestamp(1693137016083)
        .setInternalId(201411)
        .setWebConfirmationCode("DEVL-QP2D")
        .setGuestDetails(guestDetails)
        .setArrivalDate(arrivalDate)
        .setDepartureDate(departureDate)
        .build()
}

fun reservationWithBookingConfirmationCode(): ReservationServiceOuterClass.Reservation {
    val guestDetails = ReservationServiceOuterClass.GuestDetails.newBuilder()
        .setFirstName("Adam")
        .setLastName("Cox")
        .setTitle("Mrs.")
        .setPhoneNumber("191.030.5510x8975")
        .setAddress("7356 Smith Stravenue Suite 733")
        .setCity("Aprilshire")
        .setPostalCode("49083")
        .setCountry("Belarus")
        .setCompanyName("Johnson-Valencia")

    val arrivalDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(9)

    val departureDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(16)

    return ReservationServiceOuterClass.Reservation.newBuilder()
        .setTimestamp(1693161383064)
        .setInternalId(201584)
        .setBookingConfirmationNumber("201584")
        .setGuestDetails(guestDetails)
        .setArrivalDate(arrivalDate)
        .setDepartureDate(departureDate)
        .build()
}

fun reservationWithAgentConfirmationCode(): ReservationServiceOuterClass.Reservation {
    val guestDetails = ReservationServiceOuterClass.GuestDetails.newBuilder()
        .setFirstName("Amy")
        .setLastName("Reid")
        .setTitle("Dr.")
        .setPhoneNumber("896.270.8304")
        .setEmail("shawnflowers@yahoo.com")
        .setAddress("17519 Whitaker Vista Suite 873")
        .setCity("Longberg")
        .setPostalCode("42628")
        .setCountry("Cyprus")
        .setCompanyName("Bush, Pierce and Miller")

    val arrivalDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(12)

    val departureDate = ReservationServiceOuterClass.LocalDate.newBuilder()
        .setYear(2023)
        .setMonth(9)
        .setDay(13)

    return ReservationServiceOuterClass.Reservation.newBuilder()
        .setTimestamp(1693161383064)
        .setInternalId(201584)
        .setTravelAgentConfirmationCode("L5CS-LHGM")
        .setGuestDetails(guestDetails)
        .setArrivalDate(arrivalDate)
        .setDepartureDate(departureDate)
        .build()
}