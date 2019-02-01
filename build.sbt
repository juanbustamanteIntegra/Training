name := """play-java-jpa-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += javaJpa
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"

libraryDependencies += javaWs % "test"

libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % "test"
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % "test"
testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")


// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-ses
libraryDependencies += "com.amazonaws" % "aws-java-sdk-ses" % "1.11.490"

// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-ses
//libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.438"
//libraryDependencies += "com.sun.mail" % "javax.mail" % "1.6.2"
