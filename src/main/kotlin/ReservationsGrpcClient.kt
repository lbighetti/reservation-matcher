import com.exploreshackle.api.reservation.v1.ReservationService
import com.exploreshackle.api.reservation.v1.ReservationServiceOuterClass
import io.quarkus.grpc.GrpcClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.stream.Stream

@Singleton
class ReservationsGrpcClient {
    @Inject
    lateinit var reservationsRepo: ReservationsRepo

    @GrpcClient("reservation-service")
    lateinit var reservationService: ReservationService

    fun consumeStream() {
        val reservations = reservationService
            .streamReservations(ReservationServiceOuterClass.StreamReservationsRequest.newBuilder().build())

        val stream: Stream<ReservationServiceOuterClass.Reservation> = reservations.subscribe().asStream()
        stream.forEach { i -> reservationsRepo.storeReservation(i) }
    }
}