package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.TimerTask;

public class Controller {

    private Automaton ap;
    public void setAp(){

    }

    private WritableImage wimg, wimg2;
    private BufferedImage bimg;

    private int w,h;
    private int[] paletteBinary;
    private double scale = 2;
    /*взять WALL, сделть плотность .18 и захуярить поле астероидов. мелкие и большие.*/

    public void init(){
        w = (int)(cellField.getWidth()/scale);
        h = (int)(cellField.getHeight()/scale);
        ap = new Automaton(w, h);
        ap.reset();
        paletteBinary = new int[]{0xff08249e, 0xff45ff13};
        wimg = new WritableImage(w,h);
        wimg2 = new WritableImage((int)(w*scale), (int)(h*scale));
        bimg = new BufferedImage((int)(w*scale),(int)(h*scale),BufferedImage.TYPE_INT_RGB);
        printBirthValues(null);
        printStayValues(null);
        printFillDensity(null);
    }

    @FXML
    Canvas cellField;
    @FXML
    TextField birthValuesField;
    @FXML
    TextField stayValuesField;
    @FXML
    TextField fillDensityField;

    @FXML
    private void reset(){ //resets teh field
        ap.reset();
        drawFromArray(ap.getState(), paletteBinary);
    }

    @FXML
    private void togglePause(ActionEvent e){
        togglePause();
    }

    @FXML
    private void tickOnce(ActionEvent e) {
        update();
        pause();
    }

    @FXML
    private void applyBirthValues(ActionEvent e) {
        String[] tmp = birthValuesField.getText().split(",");
        ap.setBirthValues(strArrayToIntArray(tmp));
        printBirthValues(null);
    }

    @FXML
    private void applyStayValues(ActionEvent e) {
        String[] tmp = stayValuesField.getText().split(",");
        ap.setStayValues(strArrayToIntArray(tmp));
        printStayValues(null);
    }

    @FXML
    private void applyFillDensity(ActionEvent e) {
        ap.setFillDensity(Double.parseDouble(fillDensityField.getText()));
        printFillDensity(null);
    }

    @FXML
    private void printBirthValues(ActionEvent e) {
        String birthValuesText = Arrays.toString(ap.getBirthValues());
        birthValuesField.setText(birthValuesText.substring(1, birthValuesText.length()-1));
    }

    @FXML
    private void printStayValues(ActionEvent e) {
        String stayValuesText = Arrays.toString(ap.getStayValues());
        stayValuesField.setText(stayValuesText.substring(1, stayValuesText.length() - 1));
    }

    @FXML
    private void printFillDensity(ActionEvent e) {
        fillDensityField.setText(Double.toString(ap.getFillDensity()));
    }

    @FXML
    private void captureTheField(ActionEvent e) {
        System.out.println("field capture failed successfully");
    }

    @FXML
    private void saveTheField(ActionEvent e) {
        System.out.println("world save failed successfully");
    }

    @FXML
    private void loadTheField(ActionEvent e) {
        System.out.println("world load failed successfully");
    }

    public void update() {
        ap.tick();
        drawFromArray(ap.getState(), paletteBinary);
    }

    public void drawFromArray(byte[] array, int[] palette) {
        wimg.getPixelWriter().setPixels(0,0 ,w ,h, PixelFormat.createByteIndexedInstance(palette), array,0, w);
        bimg.getGraphics().drawImage(SwingFXUtils.fromFXImage(wimg, null).getScaledInstance((int)(w*scale),(int)(h*scale),Image.SCALE_SMOOTH),0,0,null);
        wimg2 = SwingFXUtils.toFXImage(bimg, wimg2);
        Platform.runLater(new TimerTask() {
            @Override
            public void run() {
                drawBitmap(wimg2);
            }
        });

    }

    private void drawBitmap(WritableImage wimg) {
        cellField.getGraphicsContext2D().drawImage(wimg,0,0);
    }

    public int getCellCount() {
        int count = 0;
        for(byte a:ap.getState()) {
            count += a;
        }
        return count;
    }

    public void togglePause(){
        ap.togglePause();
    }

    public void pause() {
        ap.pause();
    }

    public void unpause() {
        ap.unpause();
    }

    public void setPaused(boolean b) {
        ap.setPaused(b);
    }

    public boolean getPaused() {
        return ap.getPaused();
    }

    private int[] strArrayToIntArray(String[] input) {
        int[] res = new int[input.length];
        for(int i = 0; i < input.length; i++) {
            res[i] = Integer.parseInt(input[i].trim());
        }
        return res;
    }
}
