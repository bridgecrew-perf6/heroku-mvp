import Dependencies._
import Settings._
import sbtassembly.MergeStrategy

name := """heroku-mvp"""
version := "1.0-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(commonSettings)
  .settings(
    name := "heroku-mvp",
    version := "0.1",
    scalaVersion := "3.1.0",
    assembly / test := Def
      .sequential(Test / test, IntegrationTest / test)
      .value,
    assembly / assemblyMergeStrategy := customMergeStrategy,
    assembly / assemblyJarName := "heroku-mvp.jar",
    scalafmtOnCompile := true,
    libraryDependencies ++= (dependencies ++ testDependencies)
  )


val customMergeStrategy: String => MergeStrategy = {
  case r if r.endsWith(".conf")            => MergeStrategy.concat
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard // https://stackoverflow.com/questions/46287789/running-an-uber-jar-from-sbt-assembly-results-in-error-could-not-find-or-load-m
  case _                                   => MergeStrategy.first
}

IntegrationTest / parallelExecution := false
