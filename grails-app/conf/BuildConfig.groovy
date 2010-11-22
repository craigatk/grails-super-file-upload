grails.project.dependency.distribution = {
    remoteRepository(id: "pluginSnapshots", url: "http://buildpc:8080/nexus/content/repositories/snapshots") {
        authentication username: "admin", password: "admin123"
    }
}