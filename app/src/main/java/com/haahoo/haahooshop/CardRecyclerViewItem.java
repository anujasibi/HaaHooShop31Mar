package com.haahoo.haahooshop;

public class CardRecyclerViewItem {

    // Save car name.
    private String Name;

    // Save car image resource id.
    private int ImageId;

    public CardRecyclerViewItem(String Name, int ImageId) {
        this.Name = Name;
        this.ImageId = ImageId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int ImageId) {
        this.ImageId = ImageId;
    }
}