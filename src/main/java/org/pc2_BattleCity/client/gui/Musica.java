package org.pc2_BattleCity.client.gui;



import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Musica implements Runnable {
    private String filePath;

    public Musica(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Musica musica = new Musica("src/main/java/org/pc2_BattleCity/client/gui/assets/battlefield1942.wav");
        Thread musicaThread = new Thread(musica);
        musicaThread.start();
    }
}
