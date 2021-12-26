package api

case class StockPriceInfo(
    symbol: String,
    current_price: Double,
    day_high_price: Double,
    day_low_price: Double,
    day_open_price: Double,
    prev_close_price: Double
) {
    def show: Unit = {
        println(s"Current Price: $current_price")
        println(s"Day High Price: $day_high_price")
        println(s"Day Low Price: $day_low_price")
        println(s"Day Open Price: $day_open_price")
        println(s"Prev Close Price: $prev_close_price")
    } 
}


// StockClientType enum
sealed trait StockClientType { def name: String }
case object FinnHubClientType extends StockClientType { val name = "FinnHubClient" }
case object XyzClientType extends StockClientType {val name = "XyzClient" }


abstract class StockClient(val api_key: String) {
    /**
    * Get fuzzy-matched candidates for a stock symbol string.
    * @param symbol_guess A guess for the stock symbol.
    * @return List of potential matches.
    */
    def getSymbolFuzzy(symbol_guess: String): Seq[String]


    /**
    * Get pricing info for a stock.
    * @param stock_symbol
    * @return pricing info
    */
    def getStockPriceInfo(symbol: String): StockPriceInfo

}


// companion object to serve as factory
object StockClient {
    def apply(stockClientType: StockClientType, api_key: String): StockClient = {
        stockClientType match {
            case FinnHubClientType => new FinnHubClient(api_key)
            case XyzClientType => throw new UnsupportedOperationException()
        }
    }
}