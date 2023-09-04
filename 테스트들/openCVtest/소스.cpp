/*
#include<opencv2\opencv.hpp>
#include"opencv2\highgui.hpp"
#include"opencv2\imgcodecs.hpp"
#include"opencv2\imgproc.hpp"

#include<opencv2\core\types_c.h>

#include<opencv2\core\mat.hpp>



#include "opencv2/core/core.hpp"
#include "opencv2/flann/miniflann.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/photo/photo.hpp"
#include "opencv2/video/video.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/ml/ml.hpp"
#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/contrib/contrib.hpp"

#include "opencv2/core/core_c.h"
#include "opencv2/highgui/highgui_c.h"
#include "opencv2/imgproc/imgproc_c.h"

*/

/*
#include "opencv2/imgcodecs.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"


#include<iostream>


using namespace cv;
using namespace std;

void myRotate(Mat *target, double angle, double scale) {

	Point center = Point(target->cols / 2, target->rows / 2); //회전중심
	getRotationMatrix2D(center, angle, scale);
	warpAffine(*target, *target, getRotationMatrix2D(center, angle, scale), target->size());
	//->1에 3행렬곱 해서 2에 저장하는듯 / 4는 2의 사이즈 
}


int main() {
	//CommandLineParser parser(argc, argv, "{@input | lena.jpg | input image}");
	string pathh = "data.png";
	Mat src = imread(pathh);

	if (src.empty())
	{
		cout << "Could not open or find the image!\n" << endl;
		return -1;
	}
	Point2f srcTri[3];
	srcTri[0] = Point2f(0.f, 0.f);	//0, 0
	srcTri[1] = Point2f(src.cols - 1.f, 0.f);//398, 0
	srcTri[2] = Point2f(0.f, src.rows - 1.f);//0, 146
	Point2f dstTri[3];
	dstTri[0] = Point2f(0.f, src.rows*0.33f);
	dstTri[1] = Point2f(src.cols*0.85f, src.rows*0.25f);
	dstTri[2] = Point2f(src.cols*0.15f, src.rows*0.7f);

	for (int i = 0; i < 3; i++)
		cout << srcTri[i] << endl;
	for (int i = 0; i < 3; i++)
		cout << dstTri[i] << endl;

	Mat warp_mat = getAffineTransform(srcTri, dstTri); //2*3 matrix가 뽑힘(어파인 변환용 행렬)

	Mat warp_dst = Mat::zeros(src.rows, src.cols, src.type()); //src와 같은 크기의 검은화면
	warpAffine(src, warp_dst, warp_mat, warp_dst.size());

	//-> src에 warp_mat 행렬곱 해서 warp_dst에 저장하는듯






	Point center = Point(warp_dst.cols / 2, warp_dst.rows / 2); //회전중심
	double angle = -45.0;	//각도, 양수가 반시계방향임
	double scale = 0.5;		//크기 조절하는듯	
	Mat rot_mat = getRotationMatrix2D(center, angle, scale);	//회전 행렬 뽑기 -> rot_mat
	Mat warp_rotate_dst;
	warpAffine(src, warp_rotate_dst, rot_mat, warp_dst.size());	//회전행렬 행렬곱해서 변환함
																//-> rotate 과정

	imshow("Source image", src);	//원래 이미지
	imshow("Warp", warp_dst);		//어파인 변환한 이미지
	imshow("Warp + Rotate", warp_rotate_dst);	//어파인 + 회전한 이미지


	waitKey();

	return 0;

}
*/
