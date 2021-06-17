import cats.data.OptionT
import cats.data.OptionT.none
import cats.effect.{Effect, ExitCode, IO, IOApp}
import cats.implicits._
import sttp.client3._
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

object Main extends IOApp {

  def onHttpClient(implicit backend: SttpBackend[IO, Any]): IO[Option[String]] = {
    basicRequest
      .post(uri"https://httpbin.org/post?hello=world")
      .body("Hello, World!")
      .send(backend)
      .flatMap { res =>
        res.code.code match {
          case c if 400 <= c && c < 500 => IO.pure(None)
          case _ => res.body match {
            case Left(v) => IO.pure(None)
            case Right(v) => IO.pure(v.some)
          }
        }
      }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      res <- AsyncHttpClientCatsBackend[IO]().flatMap { backend => onHttpClient(backend) }
    } yield {
      println(res)
      ExitCode.Success
    }
  }
}
