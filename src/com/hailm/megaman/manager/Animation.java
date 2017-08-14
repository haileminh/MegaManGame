package com.hailm.megaman.manager;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private String name;

    private boolean isRepeated;;

    private List<FrameImage> frameImages;

    private int currentFrame;

    private List<Boolean> ignoreFrames;

    private List<Double> delayFrames;

    private long beginTime;

    private boolean drawRectFrame;

    public Animation() {
        frameImages = new ArrayList<>();
        ignoreFrames = new ArrayList<>();
        delayFrames = new ArrayList<>();
        currentFrame = 0;
        beginTime = 0;
        drawRectFrame = false;
        isRepeated = true;
    }

    public Animation(Animation animation) {
        currentFrame = animation.currentFrame;
        beginTime = animation.beginTime;
        drawRectFrame = animation.drawRectFrame;
        isRepeated = animation.isRepeated;

        frameImages = new ArrayList<FrameImage>();
        for (FrameImage f : animation.frameImages) {
            frameImages.add(new FrameImage(f));
        }

        ignoreFrames = new ArrayList<Boolean>();
        for (Boolean b : animation.ignoreFrames) {
            ignoreFrames.add(b);
        }

        delayFrames = new ArrayList<Double>();
        for (Double d : animation.delayFrames) {
            delayFrames.add(d);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean isRepeated) {
        this.isRepeated = isRepeated;
    }

    public List<FrameImage> getFrameImages() {
        return frameImages;
    }

    public void setFrameImages(List<FrameImage> frameImages) {
        this.frameImages = frameImages;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        if (currentFrame >= 0 && currentFrame < frameImages.size()) {
            this.currentFrame = currentFrame;
        } else {
            this.currentFrame = 0;
        }
    }

    public List<Boolean> getIgnoreFrames() {
        return ignoreFrames;
    }

    public void setIgnoreFrames(List<Boolean> ignoreFrames) {
        this.ignoreFrames = ignoreFrames;
    }

    public List<Double> getDelayFrames() {
        return delayFrames;
    }

    public void setDelayFrames(List<Double> delayFrames) {
        this.delayFrames = delayFrames;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public boolean getIsDrawRectFrame() {
        return drawRectFrame;
    }

    public void setDrawRectFrame(boolean drawRectFrame) {
        this.drawRectFrame = drawRectFrame;
    }

    // Check frame xem truyen vao thu tu xem frame do co bi ignore ko.
    public boolean isIgnoreFrame(int id) {
        return ignoreFrames.get(id);
    }

    // Set IgnoreFrame truyen vao vi tri can
    public void setIgnoreFrame(int id) {
        if (id >= 0 && id < ignoreFrames.size())
            ignoreFrames.set(id, true);
    }

    public void unIgnoreFrame(int id) {
        if (id >= 0 && id < ignoreFrames.size()) {
            ignoreFrames.set(id, false);
        }
    }

    public void reset() {
        currentFrame = 0;
        beginTime = 0;

        for (int i = 0; i < ignoreFrames.size(); i++) {
            ignoreFrames.set(i, false);
        }
    }

    public void add(FrameImage frameImage, double timeToNextFrame) {

        ignoreFrames.add(false);
        frameImages.add(frameImage);
        delayFrames.add(new Double(timeToNextFrame));

    }

    public BufferedImage getCurrentImage() {
        return frameImages.get(currentFrame).getImage();
    }

    public void Update(long currentTime) {
        if (beginTime == 0) {
            beginTime = currentTime;
        } else if (currentTime - beginTime > delayFrames.get(currentFrame)) {
            nextFrame();
            beginTime = currentTime;
        }
    }

    private void nextFrame() {
        if (currentFrame >= frameImages.size() - 1) {
            if (isRepeated)
                currentFrame = 0;
        } else {
            currentFrame++;
        }

        if (ignoreFrames.get(currentFrame)) {
            nextFrame();
        }
    }

    public boolean isLastFrame() {
        if (currentFrame == frameImages.size() - 1)
            return true;
        else
            return false;
    }

    public void flipImage() {
        for (int i = 0; i < frameImages.size(); i++) {
            BufferedImage image = frameImages.get(i).getImage();

            AffineTransform affineTransform = AffineTransform
                    .getScaleInstance(-1, 1);
            affineTransform.translate(-image.getWidth(), 0);

            AffineTransformOp op = new AffineTransformOp(affineTransform,
                    AffineTransformOp.TYPE_BILINEAR);

            image = op.filter(image, null);
        }
    }

    public void draw(Graphics2D graphics2d, int x, int y) {
        BufferedImage image = getCurrentImage();

        graphics2d.drawImage(image, x - image.getWidth() / 2,
                y - image.getHeight() / 2, null);

        if (drawRectFrame) {
            graphics2d.drawRect(x - image.getWidth() / 2, x - image.getWidth() / 2,
                    image.getWidth(), image.getHeight());
        }
    }
}
