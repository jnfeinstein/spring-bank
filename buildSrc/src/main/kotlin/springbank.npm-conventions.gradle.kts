//plugins {
//    id("lt.petuska.npm.publish")
//}
//
//npmPublishing {
//    organization = "jnfeinstein"
//
//    publications {
//        this["js"].packageJson {
//            repository {
//                url = "git://github.com/jnfeinstein/spring-bank.git"
//            }
//        }
//    }
//
//    repositories {
//        repository("github") {
//            registry = uri("https://npm.pkg.github.com")
//            authToken = System.getenv("GITHUB_AUTH_TOKEN")?.trim() ?: ""
//        }
//    }
//}
