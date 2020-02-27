class Player {
    private boolean hasDoorKey;
    private boolean hasChestKey;
    Player(){
        hasChestKey = false;
        hasDoorKey = false;
    }
    boolean isHasDoorKey(){
        return hasDoorKey;
    }

    void setHasDoorKey(){
        hasDoorKey = true;
    }
    void setHasChestKey(){
        hasChestKey = true;
    }
}
