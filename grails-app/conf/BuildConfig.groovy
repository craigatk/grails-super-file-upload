grails.project.dependency.distribution = {
    remoteRepository(id: "pluginSnapshots", url: "http://buildpc:8082/nexus/content/repositories/snapshots") {
        authentication username: "admin", password: "admin123"
    }
}

grails.project.dependency.distribution = {
    remoteRepository(id: "pluginReleases", url: "http://buildpc:8082/nexus/content/repositories/releases") {
        authentication username: "admin", password: "admin123"
    }
}