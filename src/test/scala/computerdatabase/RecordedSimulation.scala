package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")

	object Search {
		val search = exec(http("request_2")
			.get("/computers?f=macbooc"))
			.pause(4)
			.exec(http("request_3")
				.get("/computers?f=macbook"))
			.pause(23)
	}

	object SelectComputer{
		val selectComputer = exec(http("request_4")
			.get("/computers/517"))
			.pause(15)
			.exec(http("request_5")
				.get("/computers"))
			.pause(13)
	}

	object CreateComputer{
		val createComputer = exec(http("request_6")
			.get("/computers/new"))
			.pause(33)
			.exec(http("request_7")
				.post("/computers")
				.formParam("name", "Razvan")
				.formParam("introduced", "2021-01-11")
				.formParam("discontinued", "2022-01-11")
				.formParam("company", "1"))
	}

	val scn = scenario("RecordedSimulation").exec(Search.search,SelectComputer.selectComputer,CreateComputer.createComputer)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}