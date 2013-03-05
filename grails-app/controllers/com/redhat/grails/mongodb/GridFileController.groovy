package com.redhat.grails.mongodb

class GridFileController {
    def gridFileService

    def serveFile(String mongoId, String bucket, Boolean save) {
        def file = gridFileService.getFileByMongoId(mongoId, bucket)

        gridFileService.serveFile(response, file, save)

    }
}
