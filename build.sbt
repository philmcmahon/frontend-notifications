import com.twitter.scrooge.ScroogeSBT

def env(key: String): Option[String] = Option(System.getenv(key))

scalaVersion := "2.11.8"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"


lazy val common = (project in file("./common"))
  .settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.2",
      "com.amazonaws" % "aws-java-sdk" % "1.10.20",
      "com.google.gcm" % "gcm-server" % "1.0.0",
      "com.github.etaty" %% "rediscala" % "1.6.0",
      "org.julienrf" % "play-json-derived-codecs_2.11" % "3.1",
      "com.gu" %% "content-api-client" % "7.24",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    )
  )
  .enablePlugins(PlayScala)


lazy val capiEventWorker = (project in file("./capieventworker"))
  .dependsOn(common)
  .settings(
    name := "capi-event-worker",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.2",
      "com.amazonaws" % "aws-java-sdk" % "1.10.20",
      "com.amazonaws" % "amazon-kinesis-client" % "1.6.1"
    ),
    routesGenerator := InjectedRoutesGenerator,
    packageName in Universal := normalizedName.value,
    topLevelDirectory in Universal := Some(normalizedName.value),
    riffRaffPackageType := (packageZipTarball in Universal).value,
    riffRaffUploadArtifactBucket := Option("riffraff-artifact"),
    riffRaffUploadManifestBucket := Option("riffraff-builds"),
    riffRaffManifestProjectName := "dotcom:notifications:capieventworker",
    riffRaffBuildIdentifier := env("TRAVIS_BUILD_NUMBER").getOrElse("DEV")
  )
  .enablePlugins(PlayScala, RiffRaffArtifact, UniversalPlugin)
  .settings(ScroogeSBT.newSettings: _*)
  .settings(
    scalaVersion := "2.11.7",
    scroogeThriftDependencies in Compile := Seq("content-api-client_2.11", "content-atom-model_2.11", "content-api-models_2.11", "story-packages-model_2.11"),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    resolvers += "Guardian GitHub Repository" at "http://guardian.github.io/maven/repo-releases",
    scroogeThriftSourceFolder in Compile <<= baseDirectory {
      base => base / "src/main/resources"
    },
    libraryDependencies ++= Seq(
      "com.gu" %% "content-api-client" % "7.24",
      "com.twitter" %% "scrooge-core" % "3.20.0"
    )
  )

lazy val messageWorker = (project in file("./messageworker"))
  .dependsOn(common)
  .settings(
    name := "message-worker",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.2",
      "com.amazonaws" % "aws-java-sdk" % "1.10.20"
    ),
    routesGenerator := InjectedRoutesGenerator,
    packageName in Universal := normalizedName.value,
    topLevelDirectory in Universal := Some(normalizedName.value),
    riffRaffPackageType := (packageZipTarball in Universal).value,
    riffRaffUploadArtifactBucket := Option("riffraff-artifact"),
    riffRaffUploadManifestBucket := Option("riffraff-builds"),
    riffRaffManifestProjectName := "dotcom:notifications:messageworker",
    riffRaffBuildIdentifier := env("TRAVIS_BUILD_NUMBER").getOrElse("DEV")
  )
  .enablePlugins(PlayScala, RiffRaffArtifact, UniversalPlugin)

lazy val messageDelivery = (project in file("./messagedelivery"))
  .dependsOn(common)
  .settings(
    name := "message-delivery",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.2",
      "com.amazonaws" % "aws-java-sdk" % "1.10.20"
    ),
    routesGenerator := InjectedRoutesGenerator,
    packageName in Universal := normalizedName.value,
    topLevelDirectory in Universal := Some(normalizedName.value),
    riffRaffPackageType := (packageZipTarball in Universal).value,
    riffRaffUploadArtifactBucket := Option("riffraff-artifact"),
    riffRaffUploadManifestBucket := Option("riffraff-builds"),
    riffRaffManifestProjectName := "dotcom:notifications:message-delivery",
    riffRaffBuildIdentifier := env("TRAVIS_BUILD_NUMBER").getOrElse("DEV")
  )
  .enablePlugins(PlayScala, RiffRaffArtifact, UniversalPlugin)
