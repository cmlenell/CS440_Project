class Player {
    private boolean hasDoorKey;
    private boolean hasChestKey;
    Player(){
        hasChestKey = false;
        hasDoorKey = false;
    }
    public boolean HasDoorKey(){
        return hasDoorKey;
    }
    public boolean HasChestKey(){
        return hasChestKey;
    }

    void setHasDoorKey(){
        hasDoorKey = true;
    }
    void setHasChestKey(){
        hasChestKey = true;
    }
}
