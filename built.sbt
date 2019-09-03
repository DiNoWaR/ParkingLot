name := "parkingLot"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.scala-lang" % "scala-library" % "2.12.7"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

mainClass in(Compile, packageBin) := Some("com.gojec.core.parkinglot.launcher.Launcher")