import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.*;
  
public class FolderInputFaceDetection {
   public static void main (String[] args) {
  
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      
      String xmlFile = "C:/Users/e392229/Eclipse Workspace/FaceDetection/lbpcascade_frontalface.xml";
      CascadeClassifier classifier = new CascadeClassifier(xmlFile);
      
      
	  File folder = new File("C:/Users/e392229/Eclipse Workspace/FaceDetection/intern");
	  File[] listOfFiles = folder.listFiles();
      for(File files : listOfFiles) {
      String file = files.getPath();
      Mat src = Imgcodecs.imread(file);
      String latter = "";
      for(String ret: file.split("intern")) {
      latter = ret;
	}
 
      MatOfRect faceDetections = new MatOfRect();
      classifier.detectMultiScale(src, faceDetections);
      System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
      int[][] arr = new int[faceDetections.toArray().length][4];
      int j = 0;

      for (Rect rect : faceDetections.toArray()) {
         Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
        
         arr[j][0] = (rect.x);
         arr[j][1] = (rect.y + rect.height);
         arr[j][2] = (rect.x + rect.width);
         arr[j][3] = (rect.y);
         j++;
      }
      

   Imgcodecs.imwrite("C:/Users/e392229/Eclipse Workspace/FaceDetection/output" + latter, src);
     
      
      JSONObject json = new JSONObject();
      JSONArray array = new JSONArray();
      JSONObject elem = new JSONObject();
      
      json.put("Input", files);

      
      for(int i=0; i<arr.length; i++) {
    	  elem = new JSONObject();
    	  elem.put(arr[i][0], arr[i][1]);
    	  elem.put(arr[i][2], arr[i][3]);
    	  array.add(elem);
    	 
      }
      
      for(int i=0; i < arr.length; i++) {
      json.put("Face " + (i+1) + " coordinates", array.get(i));
      }
      
      
      
      
      System.out.println(json.toString());
      
     } 
   }
}