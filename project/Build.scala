import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play-starter"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      javaCore, javaEbean,
      "com.google.gdata" % "gdata-core" % "1.0",
      "com.google.gdata" % "gdata-photos" % "2.0",
      "com.google.gdata" % "gdata-photos-meta" % "2.0",
      "com.google.gdata" % "gdata-media" % "1.0",
      "javax.mail" % "mail" % "1.4",
      "commons-beanutils" % "commons-beanutils" % "1.8.3",
      "commons-collections" % "commons-collections" % "3.2.1"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers <+= baseDirectory { base => 
        "my" at "file:///"+base.getAbsolutePath+"/repo"
      }
    )

}
