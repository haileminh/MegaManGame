package com.hailm.megaman.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageStore {
    public static final BufferedImage IMG_BG = getImage("megasprite.png");

    private static BufferedImage getImage(String name) {
        // String path = ImageStore.class.getResource("/res/" + name).getPath();
        // return new ImageIcon(path).getImage();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/res/"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
