package org.example;

import java.awt.image.BufferedImage;

public class ImageView {
    private BufferedImage image;

    public ImageView(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
