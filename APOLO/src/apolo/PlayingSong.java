package apolo;

public  class PlayingSong implements Runnable {

    private String play_song = "";

    public PlayingSong(String songpath) {
        this.play_song = songpath;
    }

    @Override
    public void run() {
        try {
            ConvertMP32PCM.playMP3("songlist\\" + play_song + ".mp3");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
