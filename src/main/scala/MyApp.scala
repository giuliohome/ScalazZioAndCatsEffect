import catsEffectTutorial.CopyFile
import scalaz.zio.App
import scalaz.zio.console._
import scalaz.zio.IO
import scalaz.zio._


object MyApp extends App {
  val zOK = ZIO.succeed(21).map(_ * 2)
  val s1 = ZIO.succeed(42)
  def run(args: List[String]) =
    myAppLogic(args).either.map(_.fold(
      e => {
        new DefaultRuntime {}.unsafeRun( for   {
          _ <- putStrLn(s"Exception: ${e.getMessage()}")
          s1Value <- s1
          _ <-  zOK.flatMap(i => putStrLn(s"error zOK={$i}"))
          _ <- putStrLn(s"error s1=${s1Value}\n")
        } yield  ())
        1
      }
      , _ => 0))


  def myAppLogic(args: List[String]) =


    for {
      zokValue <- zOK
      s1Value <- s1
      _ <-  putStr(s"start, zOK={$zokValue)}\n")
      _ <-  s1.flatMap(i => putStrLn(s"start s1={$i}"))
      _ <- putStrLn("ciao")
      app : cats.effect.IOApp = CopyFile
      _ <- IO( app.run(args).unsafeRunSync())
      _ <-  zOK.flatMap(i => putStrLn(s"end zOK={$i}"))
      _ <- putStr(s"end s1=${s1Value}\n")
    } yield  ()

}