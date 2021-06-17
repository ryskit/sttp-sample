
name := "sttp-sample"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client3" %% "core" % "3.3.6",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats-ce2" % "3.3.6",
  "org.typelevel" %% "cats-effect" % "2.5.1",
)
