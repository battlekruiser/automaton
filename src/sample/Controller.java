package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class Controller {

    private Automaton ap;
    public void setAp(){

    }

    private WritableImage wimg, wimg2;
    private BufferedImage bimg;
    private Stage primaryStage;
    private Rule rsp;
    public void setPrimaryStage(Stage s){
        primaryStage = s;
    }

    private int w,h;
    private int[] paletteBinary;
    private int tickTime = 100;
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
    TextField tickTimeField;
    @FXML
    Button buttonPause;

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
    private void applyTickTime(){
        setTicktime(Integer.parseInt(tickTimeField.getText()));
    }

    @FXML
    private void applyBirthValues(ActionEvent e) {
        ap.setBirthRule(Rule.parseRuleString(birthValuesField.getText()));
        printBirthValues(null);
    }

    @FXML
    private void applyStayValues(ActionEvent e) {
        ap.setStayRule(Rule.parseRuleString(stayValuesField.getText()));
        printStayValues(null);
    }

    @FXML
    private void applyFillDensity(ActionEvent e) {
        ap.setFillDensity(Double.parseDouble(fillDensityField.getText()));
        printFillDensity(null);
    }

    @FXML
    private void printTickTime(){
        tickTimeField.setText(Integer.toString(getTickTime()));
    }

    @FXML
    private void printBirthValues(ActionEvent e) {
        birthValuesField.setText(ap.getBirthRule().toString());
    }

    @FXML
    private void printStayValues(ActionEvent e) {
        stayValuesField.setText(ap.getStayRule().toString());
    }

    @FXML
    private void printFillDensity(ActionEvent e) {
        fillDensityField.setText(Double.toString(ap.getFillDensity()));
    }

    @FXML
    private void captureTheField(ActionEvent e) throws IOException {
        //TODO: move to FileUtils
        pause();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Lossless Image Files", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(primaryStage);
        ImageIO.write(bimg, "png", file);
        //System.out.println("Failed to fail field capture");
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

    private void updatePauseButton() {
        buttonPause.setText(getPaused()? "Play": "Pause");
    }

    public void togglePause(){
        setPaused(!getPaused());
    }

    public void pause() {
        setPaused(true);
    }

    public void unpause() {
        setPaused(false);
    }

    public void setPaused(boolean b) {
        ap.setPaused(b);
        updatePauseButton();
    }

    public boolean getPaused() {
        return ap.getPaused();
    }

    public void setTicktime(int t) {
        tickTime = t;
    }

    public int getTickTime(){
        return tickTime;
    }


}
