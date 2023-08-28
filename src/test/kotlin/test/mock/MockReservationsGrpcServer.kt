package test.mock

import com.exploreshackle.api.reservation.v1.ReservationService
import com.exploreshackle.api.reservation.v1.ReservationServiceOuterClass
import io.quarkus.grpc.GrpcService
import io.smallrye.mutiny.Multi
import test.support.reservationWithAgentConfirmationCode
import test.support.reservationWithBookingConfirmationCode
import test.support.reservationWithWebcode
import java.time.Duration

@GrpcService
class MockReservationsGrpcServer : ReservationService {
    override fun streamReservations(request: ReservationServiceOuterClass.StreamReservationsRequest?): Multi<ReservationServiceOuterClass.Reservation> {
        return Multi.createFrom().ticks().every(Duration.ofMillis(2))
            .select().first(3)
            .map { n ->
                when(n) {
                    0L -> reservationWithAgentConfirmationCode()
                    1L -> reservationWithWebcode()
                    else -> reservationWithBookingConfirmationCode()
                }
            }
    }
}
