package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyNewSimulation extends Simulation{

  val httpProtocol = http.baseUrl("http://marketing-event-service-01-qa.dublin.local:8032");

  object GetAllBonuses {
    val getAllBonuses = exec(http("Get Bonuses").get("/events/8754?appToken=YOUR_TOKEN"))
  }

  val scn = scenario("RecordedSimulation").exec(GetAllBonuses.getAllBonuses)

  setUp(scn.inject(
    incrementUsersPerSec(50)
      .times(5)
      .eachLevelLasting(5)
      .separatedByRampsLasting(5)
      .startingFrom(20)
  )).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(100))
}
