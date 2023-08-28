import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject

@ApplicationScoped
class AppLifecycleBean {

    @Inject
    lateinit var reservationsGrpcService: ReservationsGrpcClient

    fun onStart(@Observes ev: StartupEvent?) {
        reservationsGrpcService.consumeStream()
    }
}