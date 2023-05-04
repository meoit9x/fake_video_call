package nat.pink.base.model;

import java.io.Serializable;

import nat.pink.base.utils.Const;

public class ObjectCalling implements Serializable {

    private String name = "";
    private String message = "";
    private String pathImage;
    private String pathVideo;
    private int timer = Const.KEY_TIME_NOW;
    private boolean isCalling;

    public boolean isCalling() {
        return isCalling;
    }

    public void setCalling(boolean calling) {
        isCalling = calling;
    }

    public ObjectCalling() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getName() {
        return name;
    }

    public int getTimer() {
        return timer;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
