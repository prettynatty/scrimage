import com.typesafe.sbt.SbtScalariform._
import sbt.Keys._
import sbt._

import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

object Build extends Build {

  val scrimageSettings = scalariformSettings ++ Seq(
    organization := "com.sksamuel.scrimage",
    version := "2.0.0-SNAPSHOT",
    scalaVersion := "2.11.5",
    crossScalaVersions := Seq("2.10.4", "2.11.5"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    parallelExecution in Test := false,
    scalacOptions := Seq("-unchecked", "-encoding", "utf8"),
    javacOptions ++= Seq("-source", "1.6", "-target", "1.6"),
    libraryDependencies ++= Seq(
      "org.slf4j"                     % "slf4j-api"             % "1.7.5",
      "org.imgscalr"                  % "imgscalr-lib"          % "4.2" % "test",
      "junit"                         % "junit"                 % "4.11" % "test",
      "org.scalatest"                 %% "scalatest"            % "2.1.6" % "test",
      "org.mockito"                   % "mockito-all"           % "1.9.5" % "test"
    ),
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(AlignParameters, true)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
  )

  lazy val root = Project("scrimage", file("."))
    .settings(scrimageSettings: _*)
    .settings(publishArtifact := false)
    .aggregate(core, filters)

  lazy val core = Project("scrimage-core", file("core"))
    .settings(scrimageSettings: _*)
    .settings(name := "scrimage-core")

  lazy val filters = Project("scrimage-filters", file("filters"))
    .dependsOn(core)
    .settings(scrimageSettings: _*)
    .settings(name := "scrimage-filters")
}
