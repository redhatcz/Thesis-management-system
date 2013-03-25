package com.redhat.theses.util

import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class UploadUtil {

    /**
     *
     * @param file MultipartFile which will be converted into Image
     * @param close If false InputStream of given MultipartFile will be left opened
     * @return
     */
    static BufferedImage toImage(MultipartFile file, Boolean close = true) {
        BufferedImage image
        try {
            image = ImageIO.read(file.inputStream)
        } finally {
            if (close) {
                file.inputStream.close()
            }
        }
        image
    }

    static byte[] toBytes(BufferedImage image, String type = "png") {
        def bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        try {
            ImageIO.write( image, type, baos)
            baos.flush()
            bytes = baos.toByteArray()
        } finally {
            baos.close()
        }
        bytes
    }
}
