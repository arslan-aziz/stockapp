package default

import scala.io.StdIn.readLine
import api._

object Main {

    def loop(stockClient: StockClient) {
        println("Enter a stock symbol:")
        val stockSymbolInput = readLine()
        val stockSymbolCandidates: Seq[String] = stockClient.getSymbolFuzzy(stockSymbolInput)

        if (stockSymbolCandidates.isEmpty) {
            println("No stocks found.")
        }
        else {
            val exactMatch: Option[String] = stockSymbolCandidates
                .find((x: String) => x == stockSymbolInput)
            exactMatch match {
                case Some(x) => {
                    println(s"Retrieving info for stock $stockSymbolInput")
                    stockClient.getStockPriceInfo(exactMatch).show
                }
                case None => {
                    println(s"No exact match for stock $stockSymbolInput. Try one of these:")
                    stockSymbolCandidates.map(println)
                }
            }
        }

        loop(stockClient)
    }

    def main(args: Array[String]): Unit = {
        val API_KEY = sys.env.get("API_KEY") match {
            case Some(s) => s
            case None => throw new RuntimeException("Please provide an API key.")
        }
        val stockClient: StockClient = StockClient(FinnHubClientType, API_KEY)

        loop(stockClient)
    }
}