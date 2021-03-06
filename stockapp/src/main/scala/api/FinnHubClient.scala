package api

import sttp.client3._
import ujson._


class FinnHubClient(api_key: String) extends StockClient(api_key) {

    def getSymbolFuzzy(symbol_guess: String): Seq[String] = {

        val endpointURL = uri"https://finnhub.io/api/v1/search?q=$symbol_guess&token=$api_key"
        val backend = HttpURLConnectionBackend()
        val response = basicRequest
            .get(endpointURL)
            .send(backend)
        response.body match {
            case Left(error) => throw new RuntimeException(error)
            case Right(body) => {
                ujson.read(body)("result").arr.map { x: Value =>
                    x("symbol").str
                }.toList
            }
        }
    }

    def getStockPriceInfo(symbol: String): StockPriceInfo = {
        val endpointURL = uri"https://finnhub.io/api/v1/quote?symbol=$symbol&token=$api_key"
        val backend = HttpURLConnectionBackend()
        val response = basicRequest
            .get(endpointURL)
            .send(backend)
        response.body match {
            case Left(error) => throw new RuntimeException(error)
            case Right(body) => {
                val bodyParsed = ujson.read(body)
                StockPriceInfo(
                    symbol,
                    bodyParsed("c").num,
                    bodyParsed("h").num,
                    bodyParsed("l").num,
                    bodyParsed("o").num,
                    bodyParsed("pc").num,
                )
            }
        }
    }
}