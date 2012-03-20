import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutionException;

import com.googlecode.javacv.CanvasFrame;

import com.googlecode.javacv.cpp.opencv_objdetect;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_core.*;

public class Application {

	public static void main( String[] args ) throws InterruptedException, ExecutionException {
		
		CanvasFrame cf = new CanvasFrame("Test Frame");
		
		/* Un grabber peut être utilise si on veut faire une capture via la cam
		 * 
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber("faces.jpg");
		IplImage img = grabber.getDelayedImage();
		
		*/
		IplImage img =  cvLoadImage("res/faces.jpg");
		
		/* Classifier containing "frontal faces patterns", for detection */
		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade( cvLoad("res/casc/"+"haarcascade_frontalface_alt.xml") );
		
		/* Buffer */
		CvMemStorage mem = CvMemStorage.create();
		
		/* Let's detect some faces */
		CvSeq faces = opencv_objdetect.cvHaarDetectObjects( img, cascade, mem, 1.1, 1, 0 );
				
		/* For all faces found */
		for (int i=0; i<faces.total(); i++) {
			
			/* Get area of the face */
			CvRect r = new CvRect(cvGetSeqElem( faces, i));
			
			/* Draw red rectangle on this area */
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            cvRectangle(img, cvPoint(x, y), cvPoint(x+w, y+h), CvScalar.RED, 2, CV_AA, 0);

		}
		
		System.out.println("Faces : "+faces.total());
		
		cf.showImage(img);

		KeyEvent key = cf.waitKey(10);
		if (key != null) return;
		cf.pack();
		cf.setVisible(true);
		
	}
	
	
}
